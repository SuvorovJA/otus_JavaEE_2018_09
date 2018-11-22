package ru.otus.sua.L07.infosystem;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@Slf4j
@ApplicationScoped
public class StorageCacheAndQueueCurrency implements StorageCacheAndQueue {

    private BlockingQueue<InfoItemCurrency> mainQueue;
    private int lastSendHash;

    public StorageCacheAndQueueCurrency() {
        mainQueue = new ArrayBlockingQueue<>(3);
        lastSendHash = 0;
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

    @Override
    public boolean isEmptyLast() {
        return lastSendHash == 0;
    }

    @Override
    public <E extends InfoItem> void setLast(E e) {
        lastSendHash =  e.hashCode();
    }

    @Override
    public <E extends InfoItem> boolean asLast(E e) {
        return (e.hashCode() == lastSendHash);
    }

    @PreDestroy
    private void stop(){
        mainQueue.clear();
    }
}
