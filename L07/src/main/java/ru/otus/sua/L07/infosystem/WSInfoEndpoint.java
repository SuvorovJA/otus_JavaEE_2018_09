package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L07.listeners.SessionsCounterListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@ApplicationScoped
@ServerEndpoint("/ws")
public class WSInfoEndpoint implements Channel {

    private static final Queue<Session> clientsSessions = new ConcurrentLinkedQueue<>();
    private static InfoItemCurrency lastCachedCurrency;
    private static InfoItemNews lastCachedNews;
    private final Queue<Session> closedSessions = new ConcurrentLinkedQueue<>();
    private Deferred deferredCurrency;
    private Deferred deferredNews;


    @Inject
    private Differencer differencer;

    @Inject
    private SessionsCounterListener counterListener;

    public void start() {
        log.info("WS-ENDPOINT STARTED");
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            log.info("Received msg " + msg + " from " + session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session session) throws IOException {
        log.info("New WS-session opened: id" + session.getId());
        clientsSessions.add(session);
        //TODO wat about mem leaks?
        if (lastCachedNews != null && lastCachedNews.getJson() != null) {
            deferredNews = new Deferred();
            deferredNews.sendSingle(lastCachedNews.getJson(), session);
        }
        if (lastCachedCurrency != null && lastCachedCurrency.getJson() != null) {
            deferredCurrency = new Deferred();
            deferredCurrency.sendSingle(lastCachedCurrency.getJson(), session);
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        clientsSessions.remove(session);
        log.error("Error on WS-session: id" + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        clientsSessions.remove(session);
        log.info("WS-session closed: id" + session.getId());
    }

    @PostConstruct
    private void init() {
        differencer.subscribe(this);
        counterListener.subscribe(this);
    }

    @Override
    public void update(InfoItem infoItem) {
        setCache(infoItem);
        log.info("Received update: " + infoItem.toString());
        if (infoItem instanceof InfoItemNews) sendAll(lastCachedNews.getJson());
        if (infoItem instanceof InfoItemCurrency) sendAll(lastCachedCurrency.getJson());
        if (infoItem instanceof InfoItemSessions) sendAll(((InfoItemSessions)infoItem).getJson());
    }

    @PreDestroy
    private void stop() {
        clientsSessions.clear();
        closedSessions.clear();
        if (deferredCurrency != null) deferredNews.shutdown();
        if (deferredCurrency != null) deferredCurrency.shutdown();
    }

    private synchronized void sendAll(String msg) {
        try {
            log.info("clients: " + clientsSessions.size());
            if (clientsSessions.size() > 0) {
                for (Session session : clientsSessions) {
                    sendSingle(msg, session);
                }
                removeClosed();
                log.info("Sending to " + clientsSessions.size() + " clients, " + msg);
            } else {
                log.info("No clients, no send.");
            }
        } catch (Throwable e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private synchronized void sendSingle(String msg, Session session) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(msg);
        } else {
            log.error("Closed WS session: id" + session.getId());
            closedSessions.add(session);
        }
    }

    private synchronized void removeClosed() {
        clientsSessions.removeAll(closedSessions);
        closedSessions.clear();
    }

    private void setCache(InfoItem ii) {
        if (ii instanceof InfoItemCurrency) lastCachedCurrency = (InfoItemCurrency) ii;
        if (ii instanceof InfoItemNews) lastCachedNews = (InfoItemNews) ii;
    }


    private class Deferred {
        private ExecutorService executor = Executors.newSingleThreadExecutor();

        public void shutdown(){
            executor.shutdown();
        }

        public Future<Integer> sendSingle(String msg, Session session) {
            return executor.submit(() -> {
                if (session.isOpen()) {
                    log.info("Send cached value to new session: id{}",session.getId());
                    session.getBasicRemote().sendText(msg);
                } else {
                    log.error("Closed WS session: id{}",session.getId());
                    closedSessions.add(session);
                }
                return 0;
            });
        }
    }

}

