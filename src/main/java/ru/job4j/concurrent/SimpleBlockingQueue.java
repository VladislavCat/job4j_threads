package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue;
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
        queue = new LinkedList<>();
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (size == queue.size()) {
            this.wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
                this.wait();
        }
        T rsl = queue.poll();
        notify();
        return rsl;
    }
}
