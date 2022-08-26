package ru.job4j.concurrent.casoperation;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int tmpCount;
        do {
            tmpCount = count.get();
        } while (count.compareAndSet(tmpCount, tmpCount + 1));
    }

    public int get() {
        return count.get();
    }
}
