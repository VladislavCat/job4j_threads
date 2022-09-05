package ru.job4j.concurrent.pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            CompletableFuture<Sums> completableFuture = getTask(matrix, i);
            sums[i] = completableFuture.get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int defaultPos) {
        return CompletableFuture.supplyAsync(() -> getSums(matrix, defaultPos));
    }

    private static Sums getSums(int[][] matrix, int defaultPos) {
        int row = 0;
        int col = 0;
        for (int j = 0; j < matrix.length; j++) {
            row += matrix[defaultPos][j];
            col += matrix[j][defaultPos];
        }
        return new Sums(row, col);
    }
}
