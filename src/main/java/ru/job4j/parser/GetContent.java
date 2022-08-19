package ru.job4j.parser;

import java.util.function.Predicate;

public interface GetContent {
    String content(Predicate<Character> filter);
}
