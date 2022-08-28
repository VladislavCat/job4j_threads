package ru.job4j.concurrent.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexOf<T> extends RecursiveTask<Integer> {
    private static final int FALSERSL = -1;
    private final T value;
    private final T[] array;
    private final int indexFrom;
    private final int indexTo;

    public ParallelIndexOf(T value, T[] array, int indexFrom, int indexTo) {
        this.value = value;
        this.array = array;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    public int simpleIndexOf() {
        if (array.length == 0) {
            return FALSERSL;
        }
        int rsl = FALSERSL;
        for (int i = indexFrom; i < indexTo; i++) {
            if (array[i] == value) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (indexTo - indexFrom <= 10) {
            int rslSIO = simpleIndexOf();
            return rslSIO != -1 ? rslSIO : 0;
        }
        int mid = (indexFrom + indexTo) / 2;
        ParallelIndexOf<T> leftSort = new ParallelIndexOf<>(value, array, 0, mid);
        ParallelIndexOf<T> rightSort = new ParallelIndexOf<>(value, array, mid + 1, indexTo);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return left == right ? left : right + left;
    }

    public static int find(int value, Integer[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexOf<>(value, array, 0, array.length));
    }
}
