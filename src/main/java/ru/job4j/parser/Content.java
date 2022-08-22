package ru.job4j.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public final class Content implements GetContent {
    private final File file;

    public Content(File file) {
        this.file = file;
    }

    @Override
    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int data;
            while ((data = i.read(dataBuffer, 0, 1024)) != -1) {
                if (filter.test((char) data)) {
                    output.append(new String(dataBuffer, StandardCharsets.UTF_8));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
