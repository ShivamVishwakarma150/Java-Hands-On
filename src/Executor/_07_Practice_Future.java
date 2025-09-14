package Executor;

import java.util.Random;
import java.util.concurrent.*;

public class _07_Practice_Future {

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

        // Submit tasks
        Future<String> userFuture = executor.submit(_07_Practice_Future::fetchUser);
        Future<String> menuFuture = executor.submit(_07_Practice_Future::fetchMenuItem);
        Future<String> paymentFuture = executor.submit(_07_Practice_Future::processPayment);

        // Combine results (blocking calls)
        try {
            String user = userFuture.get();     // blocking
            String menu = menuFuture.get();     // blocking
            String payment = paymentFuture.get(); // blocking

            System.out.println("📝 Final Order Summary: " + user + " | " + menu + " | " + payment);

        } catch (ExecutionException | InterruptedException ex) {
            System.out.println("❌ Error: " + ex.getCause().getMessage());
        }

        executor.shutdown();

    }
}
