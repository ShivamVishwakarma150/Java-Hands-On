package Executor;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class _04_Practice_ScheduleExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        // 1. Heartbeat Task (FixedRate)
        Runnable heartbeat = () -> {
            try {
                System.out.println("❤️ Heartbeat - System Alive at " + new Date());
            } catch (Exception e) {
                System.out.println("Heartbeat failed: " + e.getMessage());
            }
        };

        ScheduledFuture<?> hbFuture = scheduler.scheduleAtFixedRate(heartbeat,0,2, TimeUnit.SECONDS);

        // 2. Metrics Collector (FixedDelay)
        Runnable metrics = () -> {
            try {
                double cpuLoad = new Random().nextDouble() * 100;
                System.out.println("📊 CPU Usage: " + String.format("%.2f", cpuLoad) + "% at " + new Date());
                Thread.sleep(2000); // simulate heavy work
            } catch (Exception e) {
                System.out.println("Metrics collection failed: " + e.getMessage());
            }
        };

        scheduler.scheduleWithFixedDelay(metrics,1,3,TimeUnit.SECONDS);

        // 3. One-time Warning Task
        scheduler.schedule(()->{
            System.out.println("⚠️ Warning: High memory usage detected at " + new Date());
        },5,TimeUnit.SECONDS);

        // 4. Cleanup Task (FixedDelay)
        Runnable cleanup = () -> {
            try {
                System.out.println("🧹 Cleanup old logs at " + new Date());
            } catch (Exception e) {
                System.out.println("Cleanup failed: " + e.getMessage());
            }
        };
        scheduler.scheduleWithFixedDelay(cleanup, 2, 4, TimeUnit.SECONDS);


        // 5. Cancel Heartbeat after 15 sec
        scheduler.schedule(() -> {
            System.out.println("❌ Stopping Heartbeat at " + new Date());
            hbFuture.cancel(false);
        }, 15, TimeUnit.SECONDS);

        // Shutdown after 20 sec
        scheduler.schedule(() -> {
            System.out.println("🔴 Shutting down scheduler...");
            scheduler.shutdown();
        }, 20, TimeUnit.SECONDS);
    }
}
