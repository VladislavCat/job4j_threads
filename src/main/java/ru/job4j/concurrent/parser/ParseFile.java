package ru.job4j.concurrent.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter o = new BufferedWriter(new FileWriter(file))) {
            o.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}