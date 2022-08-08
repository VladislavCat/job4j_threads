package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        Thread two = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        first.start();
        two.start();
        while (first.getState() != Thread.State.TERMINATED || two.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + " " + first.getState() + " " + two.getName() + " " + two.getState());
        }
        System.out.println("Работа завершена");
    }
}
