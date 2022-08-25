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
        Base stored = memory.get(model.getId());
        BiFunction<Integer, Base, Base> biFunction = (storedVersion, modelBase) -> {
            if (storedVersion + 1 != model.getVersion()) {
                throw new OptimisticException("Версии не равны.");
            }
            return new Base(stored.getId(), model.getVersion());
        };
        return memory.computeIfPresent(model.getId(), biFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int key) {
        return memory.get(key);
    }
}
