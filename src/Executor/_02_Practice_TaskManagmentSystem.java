package Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class _02_Practice_TaskManagmentSystem {
    public static void main(String[] args) {

        Runnable heavyTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Heavy task started");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName() + " - Heavy task finished");
        };

        Runnable shortTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Short task done");
        };

        Runnable logTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Writing log entry");
        };

        // 1. FixedThreadPool -> Heavy tasks
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            fixedPool.execute(heavyTask);
        }

        // 2. CachedThreadPool -> Short burst tasks
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            cachedPool.execute(shortTask);
        }

        // 3. SingleThreadExecutor -> Logging sequentially
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            singlePool.execute(logTask);
        }

        // 4. ScheduledThreadPool -> Monitoring every 3 sec
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() ->
                        System.out.println("Monitoring system health at " + System.currentTimeMillis()),
                1, 3, TimeUnit.SECONDS);

        // shutdown sequence
        fixedPool.shutdown();
        cachedPool.shutdown();
        singlePool.shutdown();

        // run monitoring only for 10 sec
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scheduler.shutdown();


    }
}
