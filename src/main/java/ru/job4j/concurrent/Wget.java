package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
            Date timeStart = new Date();
            long secondIterate = 1;
            byte[] dataBuffer = new byte[speed];
            int downloadData = 0;
            int bytesRead = in.read(dataBuffer, 0, speed);
            while (bytesRead != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    if (new Date().getTime() < timeStart.getTime() + ONESECONDINMSECOND * secondIterate) {
                        secondIterate++;
                        downloadData = 0;
                        Thread.sleep((timeStart.getTime() + ONESECONDINMSECOND * secondIterate - new Date().getTime()));
                    }
                }
                out.write(dataBuffer, 0, bytesRead);
                bytesRead = in.read(dataBuffer, 0, speed);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
