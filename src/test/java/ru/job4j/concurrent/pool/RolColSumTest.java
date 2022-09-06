package ru.job4j.concurrent.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class RolColSumTest {
    @Test
    public void serialSumRolCol() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] rsl = RolColSum.sum(matrix);
        Sums[] expected = {new Sums(6, 12), new Sums(15, 15), new Sums(24, 18)};
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void asyncSumRolCol() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] rsl = RolColSum.asyncSum(matrix);
        Sums[] expected = {new Sums(6, 12), new Sums(15, 15), new Sums(24, 18)};
        assertThat(rsl).isEqualTo(expected);
    }
}
