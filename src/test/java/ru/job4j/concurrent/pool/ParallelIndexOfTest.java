package ru.job4j.concurrent.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelIndexOfTest {
    @Test
    public void whenTestFirstPackArray() {
        Integer[] arr = new Integer[30];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelIndexOf.find(1, arr)).isEqualTo(1);
    }

    @Test
    public void whenTestTwoPackArray() {
        Integer[] arr = new Integer[30];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelIndexOf.find(13, arr)).isEqualTo(13);
    }

    @Test
    public void whenTestThreePackArray() {
        Integer[] arr = new Integer[30];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelIndexOf.find(19, arr)).isEqualTo(19);
    }

    @Test
    public void whenTestFourPackArray() {
        Integer[] arr = new Integer[30];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        assertThat(ParallelIndexOf.find(28, arr)).isEqualTo(28);
    }
}
