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
        String output = "";
        try (InputStream i = new FileInputStream(file)) {
            byte[] dataBuffer = new byte[i.available()];
            int data;
            while ((data = i.read(dataBuffer, 0, i.available())) > 0) {
                if (filter.test((char) data)) {
                    output = new String(dataBuffer, StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
