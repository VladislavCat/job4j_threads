package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            char[] chars = {'\\', '|', '/'};
            int index = 0;
            while (!Thread.currentThread().isInterrupted()) {
                index = index == chars.length ? 0 : index;
                System.out.print("\r load" + chars[index]);
                index++;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(1000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
