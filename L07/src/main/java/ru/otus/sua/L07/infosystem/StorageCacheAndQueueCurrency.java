package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Slf4j
@ApplicationScoped
public class StorageCacheAndQueueCurrency implements StorageCacheAndQueue {

    private BlockingQueue<InfoItemCurrency> mainQueue = new LinkedBlockingQueue<>();
    private int lastSendHash = 0;

    @Override
    public <E extends InfoItem> boolean isNew(E e) {
        if ((e.hashCode() != lastSendHash)) {
            lastSendHash = e.hashCode();
            return true;
        }
        return false;
    }

    @PreDestroy
    private void stop() {
        mainQueue.clear();
    }

    @Override
    public InfoItemCurrency take() {
        try {
            return mainQueue.take();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public <E extends InfoItem> void put(E e) {
        InfoItemCurrency ii = (InfoItemCurrency) e;
        try {
            mainQueue.put(ii);
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
    }


}
