package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    public static final int ONESECONDINMSECOND = 1000;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        char separatorURL = '/';
        try (BufferedInputStream in
                     = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out =
                     new FileOutputStream(url.substring(url.lastIndexOf(separatorURL) + 1))) {
            long timeStart = new Date().getTime();
            byte[] dataBuffer = new byte[1024];
            int downloadData = 0;
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    downloadData = 0;
                    long deltaTime = new Date().getTime() - timeStart;
                    if (deltaTime < ONESECONDINMSECOND) {
                        Thread.sleep(deltaTime);
                    }
                    timeStart = new Date().getTime();
                }
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        Date startTime = new Date();
        wget.start();
        wget.join();
        System.out.println((new Date().getTime() - startTime.getTime()) / 1000 + " секунд.");
    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Указано неверное количество аргументов");
        } else if (args[0].length() == 0 || (!args[0].contains("."))) {
            throw new IllegalArgumentException("URL указан неверно");
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Указано не число");
        }
    }
}
