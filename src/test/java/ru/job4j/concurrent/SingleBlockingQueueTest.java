package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleBlockingQueueTest {

    @Test
    public void whenAddTwoValueAndGetTwoValue() throws InterruptedException {
        var sbq = new SimpleBlockingQueue<Integer>(2);
        var rsl = new ArrayList<Integer>();
        Thread first = new Thread(() -> {
            try {
                sbq.offer(1);
                sbq.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread second = new Thread(() -> {
            try {
                rsl.add(sbq.poll());
                rsl.add(sbq.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        second.start();
        first.start();
        second.join();
        first.join();
        assertThat(rsl).isEqualTo(List.of(1, 2));
    }
}