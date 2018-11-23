package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@ApplicationScoped
@ServerEndpoint("/ws")
public class WSInfoEndpoint implements Channel {

    private static Queue<Session> clientsSessions = new ConcurrentLinkedQueue<>();

    @Inject
    private Differencer differencer;

    private void sendAll(String msg) {
        try {
            if (clientsSessions.size() > 0) {
                ArrayList<Session> closedSessions = new ArrayList<>();
                for (Session session : clientsSessions) {
                    if (!session.isOpen()) {
                        log.error("Closed WS session: " + session.getId());
                        closedSessions.add(session);
                    } else {
                        session.getBasicRemote().sendText(msg);
                    }
                }
                clientsSessions.removeAll(closedSessions);
                log.info("Sending to " + clientsSessions.size() + " " + msg);
            } else {
                log.info("No clients, no send.");
            }
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
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
    public void open(Session session) {
        clientsSessions.add(session);
        log.info("New WS-session opened: " + session.getId());
    }

    @OnError
    public void error(Session session, Throwable t) {
        clientsSessions.remove(session);
        log.error("Error on WS-session " + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        clientsSessions.remove(session);
        log.info("WS-session closed: " + session.getId());
    }

    @PostConstruct
    private void init() {
        differencer.subscribe(this);
    }

    @Override
    public void update(InfoItem ii) {
        log.info("Received update: " + ii.toString());
        if (ii instanceof InfoItemNews) sendAll(((InfoItemNews) ii).getJson());
        if (ii instanceof InfoItemCurrency) sendAll(((InfoItemCurrency) ii).getJson());
    }

    @PreDestroy
    private void stop() {
        clientsSessions.clear();
    }

    public void start() {
        log.info("WS-ENDPOINT STARTED");
    }
}
