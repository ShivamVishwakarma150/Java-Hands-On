package Executor;

import java.util.*;
import java.util.concurrent.*;

class Order {
    int id;
    String description;
    Order(int id, String desc) {
        this.id = id;
        this.description = desc;
    }
}

class ValidateOrderTask implements Runnable {
    private Order order;
    ValidateOrderTask(Order order) { this.order = order; }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Validating order " + order.id);
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }
}

class PaymentTask implements Callable<String> {
    private Order order;
    PaymentTask(Order order) { this.order = order; }
    public String call() throws Exception {
        if (order.id == 3) throw new RuntimeException("Payment failed for order " + order.id);
        Thread.sleep(800);
        return Thread.currentThread().getName() + " - Payment done for order " + order.id;
    }
}

class PackagingTask implements Runnable {
    private Order order;
    PackagingTask(Order order) { this.order = order; }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - Packaging order " + order.id);
        try { Thread.sleep(400); } catch (InterruptedException e) {}
    }
}

class ShippingTask implements Callable<String> {
    private Order order;
    ShippingTask(Order order) { this.order = order; }
    public String call() throws Exception {
        Thread.sleep(600);
        return Thread.currentThread().getName() + " - Shipped order " + order.id;
    }
}

public class _01_Practice_MiniOrderProcessing {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Order> orders = Arrays.asList(
                new Order(1, "Laptop"),
                new Order(2, "Phone"),
                new Order(3, "Headphones"),
                new Order(4, "Keyboard"),
                new Order(5, "Monitor")
        );

        List<Future<String>> paymentFutures = new ArrayList<>();
        List<Future<String>> shippingFutures = new ArrayList<>();

        for (Order order : orders) {
            // validate (Runnable via execute)
            executor.execute(new ValidateOrderTask(order));

            // payment (Callable via submit, collect Future)
            paymentFutures.add(executor.submit(new PaymentTask(order)));

            // packaging (Runnable via execute)
            executor.execute(new PackagingTask(order));

            // shipping (Callable via submit)
            shippingFutures.add(executor.submit(new ShippingTask(order)));
        }

        // Collect payment results
        for (Future<String> f : paymentFutures) {
            try {
                System.out.println("Result: " + f.get());
            } catch (ExecutionException e) {
                System.out.println("Payment Exception: " + e.getCause());
            }
        }

        // Collect shipping results
        for (Future<String> f : shippingFutures) {
            System.out.println("Result: " + f.get());
        }

        // Graceful shutdown
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        System.out.println("All tasks finished.");
    }
}
