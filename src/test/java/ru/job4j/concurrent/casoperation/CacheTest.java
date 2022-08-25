package ru.job4j.concurrent.casoperation;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

    @Test
    public void whenAddInMap() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.get(1)).isEqualTo(base);
        assertThat(cache.add(base)).isEqualTo(false);
    }

    @Test
    public void whenUpdateModel() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        assertThat(cache.update(new Base(1, 2))).isEqualTo(true);
        assertThat(cache.get(1)).isEqualTo(new Base(1, 2));
    }

    @Test
    public void whenDeleteModel() {
        Base base = new Base(1, 1);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(base);
        assertThat(cache.get(1)).isNull();
    }
}
