package Executor;

import java.util.Random;
import java.util.concurrent.*;

public class _06_Practice_Completeble_Future {

    static String fetchUser(){
        sleep(500);
        return "👤 User: Shivam";
    }

    static String fetchMenuItem() {
        sleep(800);
        return "🍔 Menu: Chicken Burger";
    }

    static String processPayment(){
        sleep(600);
        if (new Random().nextBoolean()) {  // sometimes fail
            throw new RuntimeException("💳 Payment failed!");
        }
        return "✅ Payment Successful";
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 1. Fetch user (async with return)
        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(_06_Practice_Completeble_Future::fetchUser, executor);

        // 2. Fetch menu item (async with return)
        CompletableFuture<String> menuFuture =
                CompletableFuture.supplyAsync(_06_Practice_Completeble_Future::fetchMenuItem, executor);

        // 3. Combine user + menu
        CompletableFuture<String> orderDetails =
                userFuture.thenCombine(menuFuture, (user, menu) -> user + " | " + menu);

        // 4. Payment process with exception handling
        CompletableFuture<String> paymentFuture =
                CompletableFuture.supplyAsync(_06_Practice_Completeble_Future::processPayment, executor)
                        .exceptionally(ex -> "❌ Payment Error: " + ex.getMessage());

        // 5. Combine everything (compose style)
        CompletableFuture<String> finalOrder =
                orderDetails.thenCombine(paymentFuture, (details, payment) -> details + " | " + payment);

        // 6. Run something after all tasks
        CompletableFuture<Void> allDone =
                CompletableFuture.allOf(userFuture, menuFuture, paymentFuture, finalOrder)
                        .thenRun(() -> System.out.println("📦 Order pipeline completed"));

        // 7. Print final order
        finalOrder.thenAccept(order -> System.out.println("📝 Final Order Summary: " + order));

        // Block main thread till done
        try {
            allDone.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }

}
