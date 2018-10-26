package ru.otus.sua.shared;

import java.io.Serializable;


public class MyPair<L, R> implements Serializable {

    private L left;
    private R right;

    public MyPair() {
    }

    public MyPair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
