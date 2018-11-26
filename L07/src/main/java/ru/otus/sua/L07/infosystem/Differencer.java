package ru.otus.sua.L07.infosystem;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@NoArgsConstructor
@ApplicationScoped
@Slf4j
public class Differencer {

    @Inject
    private StorageCacheAndQueueCurrency storageCurrency;

    @Inject
    private StorageCacheAndQueueNews storageNews;

    private static UpdateAgency updateAgency;
    private static Sheduler shedulerNews;
    private static Sheduler shedulerCurrency;


    public void subscribe(Channel channel) {
        updateAgency.addObserver(channel);
    }

    @PostConstruct()
    private void init() {
        updateAgency = new UpdateAgency();
        shedulerNews = new Sheduler(this::sheduledDiffNews);
        shedulerCurrency = new Sheduler(this::sheduledDiffCurrency);
    }

    private void sheduledDiffCurrency() {
        InfoItemCurrency itemCurrency = storageCurrency.take();
        if (storageCurrency.isNew(itemCurrency)) {
            updateAgency.sendUpdate(itemCurrency);
        }
    }

    private void sheduledDiffNews() {
        InfoItemNews itemNews = storageNews.take();
        if (storageNews.isNew(itemNews)) {
            updateAgency.sendUpdate(itemNews);
        }
    }

    private class Sheduler implements Runnable {

        private Runnable runnable;

        private ScheduledExecutorService service;

        public Sheduler(Runnable runnable) {
            this.runnable = runnable;
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(this, 5, 10, TimeUnit.SECONDS);
        }

        @PreDestroy
        private void stop() {
            service.shutdown();
        }

        @Override
        public void run() {
            runnable.run();
        }
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
