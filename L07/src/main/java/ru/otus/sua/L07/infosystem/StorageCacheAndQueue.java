package ru.otus.sua.L07.infosystem;

public interface StorageCacheAndQueue {
    <E extends InfoItem> E take();
    <E extends InfoItem> void put(E e);
    boolean isEmptyLast();
    <E extends InfoItem> void setLast(E e);
    <E extends InfoItem> boolean asLast(E e);
}
