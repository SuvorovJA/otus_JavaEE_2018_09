package ru.otus.sua.L07.listeners;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L07.infosystem.Channel;
import ru.otus.sua.L07.infosystem.InfoItem;
import ru.otus.sua.L07.infosystem.InfoItemSessions;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@WebListener
@Slf4j
public class SessionsCounterListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private static final AtomicInteger activeSessions = new AtomicInteger();
    private static UpdateAgency updateAgency;

    // Public constructor is required by servlet spec
    public SessionsCounterListener() {
        updateAgency = new UpdateAgency();
        log.info("SESSIONS LISTENER START");
    }

    public void subscribe(Channel channel) {
        updateAgency.addObserver(channel);
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        log.info("ADD SESSION");
        activeSessions.getAndIncrement();
        updateAgency.sendUpdate(new InfoItemSessions(getAsJson()));
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("REMOVE SESSION");
        activeSessions.getAndDecrement();
        updateAgency.sendUpdate(new InfoItemSessions(getAsJson()));
    }

    private String getAsJson() {
        return "{\"sessions\":" + activeSessions.intValue() + "}";
    }

    private class UpdateAgency {
        private ConcurrentLinkedQueue<Channel> channels = new ConcurrentLinkedQueue<>();

        public void addObserver(Channel channel) {
            this.channels.add(channel);
        }

        public void removeObserver(Channel channel) {
            this.channels.remove(channel);
        }

        public synchronized void sendUpdate(InfoItem ii) {
            log.info("UPDATE CHANNELS WITH: {}", ii.toString());
            for (Channel channel : this.channels) {
                channel.update(ii);
            }
        }

        @PreDestroy
        private void stop() {
            channels.clear();
        }
    }

}
