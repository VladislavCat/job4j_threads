package ru.job4j.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)) {
            byte[] dataBuffer = content.getBytes(StandardCharsets.UTF_8);
            o.write(dataBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}