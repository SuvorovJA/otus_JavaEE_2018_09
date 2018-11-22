package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L07.servlets.CurrencyServlet;

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
public class CheckerCurrency implements Checker {

    private ScheduledExecutorService service;

    @Inject
    private StorageCacheAndQueueCurrency itemCurrencyStorage;

    @Inject
    private CurrencyServlet currencyServlet;

    @PostConstruct()
    private void init() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    private void stop() {
        service.shutdown();
    }

    @Override
    public void sheduledCheck() {
        try {
            itemCurrencyStorage.put(new InfoItemCurrency(currencyServlet.getCurrency()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
