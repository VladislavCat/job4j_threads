package ru.job4j.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        count.compareAndSet(count.get(), count.get() + 1);
    }

    public int get() {
        return count.get();
    }
}
