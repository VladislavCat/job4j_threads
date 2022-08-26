package ru.job4j.concurrent.casoperation;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (key, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("Версии не равны.");
            }
            return new Base(model.getId(), model.getVersion() + 1, model.getName());
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int key) {
        return memory.get(key);
    }
}
