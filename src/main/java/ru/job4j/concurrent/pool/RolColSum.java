package ru.job4j.concurrent.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int row = 0;
            int col = 0;
            for (int j = 0; j < matrix.length; j++) {
                row += matrix[i][j];
                col += matrix[j][i];
            }
            sums[i] = new Sums(row, col);
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
        return CompletableFuture.supplyAsync(() -> {
            int row = 0;
            int column = 0;
            for (int i = 0; i < matrix.length; i++) {
                row += matrix[defaultPos][i];
                column += matrix[i][defaultPos];
            }
            return new Sums(row, column);
        });
    }
}
