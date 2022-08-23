package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleBlockingQueueTest {

    @Test
    public void whenAddTwoValueAndGetTwoValue() throws InterruptedException {
        var sbq = new SimpleBlockingQueue<Integer>();
        var rsl = new ArrayList<Integer>();
        Thread first = new Thread(() -> {
            sbq.offer(1);
            sbq.offer(2);
        });
        Thread second = new Thread(() -> {
            rsl.add(sbq.poll());
            rsl.add(sbq.poll());
        });
        second.start();
        first.start();
        second.join();
        first.join();
        assertThat(rsl).isEqualTo(List.of(1, 2));
    }
}