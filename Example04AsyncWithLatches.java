import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Example04AsyncWithLatches {
    static Random r = new Random(new Date().getTime());

    static void async(final int n, CountDownLatch previous, CountDownLatch current) {
        new Thread(() -> {
            try {
                previous.await(); // Wait for the previous task to complete
                Thread.sleep(r.nextLong(0, 10));
                System.out.println("Task " + n);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                current.countDown(); // Signal that this task is complete
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        int c = 10;
        while (c-- > 0) {
            System.out.println("---");

            // Create latches for each task
            CountDownLatch latch1 = new CountDownLatch(1);
            CountDownLatch latch2 = new CountDownLatch(1);
            CountDownLatch latch3 = new CountDownLatch(1);

            // Run tasks in order, ensuring they complete in sequence
            async(1, new CountDownLatch(0), latch1); // Start immediately, no need to wait
            async(2, latch1, latch2); // Wait for task 1 to complete
            async(3, latch2, latch3); // Wait for task 2 to complete

            // Ensure all tasks complete before continuing
            latch3.await();
        }
    }
}
