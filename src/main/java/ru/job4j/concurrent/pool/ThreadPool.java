package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        notifyAll();
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public void createThreads() throws InterruptedException {
        for (int i = 0; i <= size; i++) {
            threads.add(new Thread(tasks.poll()));
        }
    }

    public void startTreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }
}