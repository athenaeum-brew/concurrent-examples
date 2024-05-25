import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Example50CompletableFuture {

    public static void main(String[] args) {
        final Object printLock = new Object();

        // Start the CompletableFuture in another thread
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // Simulate some asynchronous operation
            try {
                TimeUnit.SECONDS.sleep(2); // Simulating a delay of 2 seconds
                synchronized (printLock) {
                    TheUtils.lnprintln("Future is coming!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start a dot printing loop in the main thread
        while (!future.isDone()) {
            synchronized (printLock) {
                System.out.print(".");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500); // Sleep for 500 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print a newline character when the CompletableFuture completes
        synchronized (printLock) {
            TheUtils.println("Future has come!");
        }

        // Shutdown the CompletableFuture's executor
        future.join(); // This will block until the CompletableFuture completes
    }
}
