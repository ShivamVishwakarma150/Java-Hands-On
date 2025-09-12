package Executor;

import java.util.concurrent.*;

public class _03_Practice_ThreadFactory {
    public static void main(String[] args) {

        // 1. Custom Factory
        ThreadFactory orderFactory = new ThreadFactory() {

            private int count =1;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("OrderWorker-"+count++);
                t.setDaemon(false);
                return t;
            }
        };

        BlockingQueue<Runnable> orderQueue = new ArrayBlockingQueue<>(3);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                5,TimeUnit.SECONDS,
                orderQueue,
                orderFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );

        Runnable orderTask = () -> {
            System.out.println(Thread.currentThread().getName() +" Processing Order");
            try {Thread.sleep(2000);} catch (Exception e){}
            System.out.println(Thread.currentThread().getName()+" Finished Order");
        };

        for(int i=0;i<=20;i++){
            try {
                System.out.println("Submitting order " + i);
                executor.execute(orderTask);
            } catch (RejectedExecutionException e) {
                System.out.println("❌ Order " + i + " rejected!");
            }
        }

        executor.shutdown();


    }
}
