package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L07.servlets.CurrencyServlet;
import ru.otus.sua.L07.servlets.NewsServlet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Slf4j
public class CheckerNews implements Checker {

    private ScheduledExecutorService service;

    @Inject
    private StorageCacheAndQueueNews itemNewsStorage;

    @Inject
    private NewsServlet newsServlet;

    @PostConstruct()
    private void init() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    private void stop(){
        service.shutdown();
    }

    @Override
    public void sheduledCheck() {
        try {
            itemNewsStorage.put(new InfoItemNews(newsServlet.getNews()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
