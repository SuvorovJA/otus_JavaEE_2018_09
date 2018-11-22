package ru.otus.sua.L07.infosystem;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    private UpdateAgency updateAgency;
    private Sheduler shedulerNews;
    private Sheduler shedulerCurrency;


    public void subscribe(Channel channel) {
        updateAgency.addObserver(channel);
    }

    @PostConstruct()
    private void init() {
        updateAgency = new UpdateAgency();
        shedulerNews = new Sheduler(this::sheduledDiffNews);
        shedulerCurrency = new Sheduler(this::sheduledDiffCurrency);
    }

    private Integer sheduledDiffCurrency() {
        InfoItemCurrency itemCurrency = storageCurrency.take();
        if (storageCurrency.isEmptyLast() || !storageCurrency.asLast(itemCurrency)) {
            storageCurrency.setLast(itemCurrency);
            updateAgency.sendUpdate(itemCurrency);
        }
        return 0;
    }

    private Integer sheduledDiffNews() {
        InfoItemNews itemNews = storageNews.take();
        if (storageNews.isEmptyLast() || !storageNews.asLast(itemNews)) {
            storageNews.setLast(itemNews);
            updateAgency.sendUpdate(itemNews);
        }
        return 0;
    }

    private class Sheduler implements Runnable {

        private Supplier<Integer> supplier;

        private ScheduledExecutorService service;

        public Sheduler(Supplier<Integer> supplier) {
            this.supplier = supplier;
            service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(this, 1, 8, TimeUnit.SECONDS);
        }

        @PreDestroy
        private void stop() {
            service.shutdown();
        }

        @Override
        public void run() {
            supplier.get();
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

        public void sendUpdate(InfoItem ii) {
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
