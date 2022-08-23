package ru.job4j.concurrent.parser;

import java.io.*;
import java.util.function.Predicate;

public final class Content implements GetContent {
    private final File file;

    public Content(File file) {
        this.file = file;
    }

    @Override
    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader i = new BufferedReader(new FileReader(file))) {
            for (String str : i.lines().toList()) {
                str.chars()
                        .mapToObj(c -> (char) c)
                        .filter(filter)
                        .forEach(output::append);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
