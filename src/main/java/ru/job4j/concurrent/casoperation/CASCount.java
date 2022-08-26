package ru.job4j.concurrent.casoperation;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int tmpCount;
        do {
            tmpCount = count.get();
        } while (!count.compareAndSet(tmpCount, tmpCount + 1));
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        System.out.println(casCount.get());
    }
}
