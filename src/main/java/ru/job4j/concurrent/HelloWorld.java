package ru.job4j.concurrent;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println("World???");
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
        int i = 0;
        do {
            System.out.println(i++);
        } while (i < 10);
        System.out.println("GoodBye");
    }
}
