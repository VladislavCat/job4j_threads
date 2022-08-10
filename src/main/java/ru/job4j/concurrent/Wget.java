package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

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
            byte[] dataBuffer = new byte[1024];
            int bytesRead = in.read(dataBuffer, 0, 1024);
            while (bytesRead != -1) {
                out.write(dataBuffer, 0, bytesRead);
                Thread.sleep(1000);
                bytesRead = in.read(dataBuffer, 0, 1024);
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
        wget.start();
        wget.join();
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
