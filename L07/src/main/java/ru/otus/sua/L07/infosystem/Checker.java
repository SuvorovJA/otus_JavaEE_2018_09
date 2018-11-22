package ru.otus.sua.L07.infosystem;

public interface Checker extends Runnable {
    void sheduledCheck();
    default void run() {
        this.sheduledCheck();
    }
}
