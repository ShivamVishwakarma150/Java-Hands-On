package Executor;
import java.util.concurrent.*;
import java.util.*;


class MergeSortTask extends RecursiveTask<int[]> {
    private int[] arr;

    public MergeSortTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected int[] compute() {
        if (arr.length <= 5) { // base case
            Arrays.sort(arr);
            return arr;
        }

        int mid = arr.length / 2;

        // Split into two halves
        MergeSortTask leftTask = new MergeSortTask(Arrays.copyOfRange(arr, 0, mid));
        MergeSortTask rightTask = new MergeSortTask(Arrays.copyOfRange(arr, mid, arr.length));

        // fork & join style
        leftTask.fork();
        int[] rightResult = rightTask.compute();
        int[] leftResult = leftTask.join();

        // merge results
        return merge(leftResult, rightResult);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
        return result;
    }
}

// Logger using RecursiveAction
class ArrayLogger extends RecursiveAction {
    private int[] arr;

    public ArrayLogger(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        System.out.println(Thread.currentThread().getName() + " processed part: " + Arrays.toString(arr));
    }
}


public class _05_Practice_ForkJoinPool {
    public static void main(String[] args) {
        int size = 20;
        int[] arr = new Random().ints(size, 1, 100).toArray();
        System.out.println("Original Array: " + Arrays.toString(arr));

        ForkJoinPool pool = new ForkJoinPool(4); // custom parallelism = 4

        // 1. Run parallel merge sort
        long start = System.currentTimeMillis();
        int[] sorted = pool.invoke(new MergeSortTask(arr));
        long end = System.currentTimeMillis();

        System.out.println("Sorted Array (Parallel MergeSort): " + Arrays.toString(sorted));
        System.out.println("Time (Parallel): " + (end - start) + " ms");

        // 2. Run normal sort for comparison
        int[] copy = Arrays.copyOf(arr, arr.length);
        start = System.currentTimeMillis();
        Arrays.sort(copy);
        end = System.currentTimeMillis();
        System.out.println("Sorted Array (Normal Sort): " + Arrays.toString(copy));
        System.out.println("Time (Normal): " + (end - start) + " ms");

        // 3. Logger demo (invokeAll)
        ArrayLogger log1 = new ArrayLogger(Arrays.copyOfRange(arr, 0, arr.length / 2));
        ArrayLogger log2 = new ArrayLogger(Arrays.copyOfRange(arr, arr.length / 2, arr.length));

        // ✅ ForkJoinTask ke liye invokeAll()
        ForkJoinTask.invokeAll(log1, log2);

        pool.shutdown();

    }
}
