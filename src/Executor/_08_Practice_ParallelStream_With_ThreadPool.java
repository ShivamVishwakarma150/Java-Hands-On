package Executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class _08_Practice_ParallelStream_With_ThreadPool {
    public static void main(String[] args) {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Shivam Vishwakarma\\Documents\\Projects\\Spring-Boot-in-Detailed-Way-master\\Session - 04\\Java Practice\\src\\Executor\\sample.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] words = content.split("\\W+");
        System.out.println("Total words: "+words.length);

        // 3. Sequential stream processing
        long startSeq = System.currentTimeMillis();
        long countSeq = Arrays.stream(words)
                .filter(w -> w.length() > 5)
                .count();
        long endSeq = System.currentTimeMillis();
        System.out.println("Sequential count: " + countSeq + " | Time: " + (endSeq - startSeq) + " ms");

        // 4. Parallel stream processing
        long startPar = System.currentTimeMillis();
        long countPar = Arrays.stream(words)
                .parallel()
                .filter(w -> w.length() > 5)
                .count();
        long endPar = System.currentTimeMillis();
        System.out.println("Parallel count: " + countPar + " | Time: " + (endPar - startPar) + " ms");

        // 5. Custom ForkJoinPool example (limit threads to 2)
        int n=12; // in my case
        ForkJoinPool pool = new ForkJoinPool(n);
        try {
            long startCustom = System.currentTimeMillis();
            long countCustom = pool.submit(() ->
                    Arrays.stream(words)
                            .parallel()
                            .filter(w -> w.length() > 5)
                            .count()
            ).get();
            long endCustom = System.currentTimeMillis();
            System.out.println("CustomPool("+n+") count: " + countCustom + " | Time: " + (endCustom - startCustom) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
