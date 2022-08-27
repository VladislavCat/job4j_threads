package ru.job4j.concurrent.pool;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexOf extends RecursiveTask<Integer> {
    private static final int FALSERSL = -1;
    private final int value;
    private final int[] array;
    private final boolean flag;

    public ParallelIndexOf(int value, int[] array, boolean flag) {
        this.value = value;
        this.array = array;
        this.flag = flag;
    }

    public int simpleIndexOf() {
        if (array.length == 0) {
            return FALSERSL;
        }
        int rsl = FALSERSL;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        int rsl = 0;
        if (array.length <= 10) {
            int rslSIO = simpleIndexOf();
            return rslSIO != -1 ? (flag ? rslSIO + array.length - 1 : rslSIO) : 0;
        } else if (flag) {
            rsl += array.length;
        }
        int mid = array.length >> 1;
        ParallelIndexOf leftSort = new ParallelIndexOf(value, Arrays.copyOf(array, mid), false);
        ParallelIndexOf rightSort = new ParallelIndexOf(value, Arrays.copyOfRange(array, mid, array.length), true);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        rsl += right - mid <= 0 ? left : right;
        return rsl >= 0 ? rsl : -1;
    }

    public static int find(int value, int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexOf(value, array, false));
    }
}
