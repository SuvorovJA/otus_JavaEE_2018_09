package ru.otus.sua.L07.infosystem;

public interface StorageCacheAndQueue {
    <E extends InfoItem> E take();
    <E extends InfoItem> void put(E e);
    <E extends InfoItem> boolean isNew(E e);
}
