import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

public class Example71Continuation {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        executor.submit(() -> {
            System.out.println("Task started");
            // Simulate a pause (continuation)
            Continuation continuation = Continuation.of(() -> {
                System.out.println("Part 1");
                Continuation.yield(); // Pause here
                System.out.println("Part 2");
            });

            while (continuation.run()) {
                System.out.println("Continuation resumed");
            }

            System.out.println("Task completed");
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}

class Continuation {
    private final Runnable task;
    private boolean done = false;

    private Continuation(Runnable task) {
        this.task = task;
    }

    public static Continuation of(Runnable task) {
        return new Continuation(task);
    }

    public boolean run() {
        if (done) return false;
        task.run();
        done = true;
        return true;
    }

    public static void yield() {
        // Simulate a yield in continuation (conceptual)
    }
}
