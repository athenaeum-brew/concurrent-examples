import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Example40ReentrantLock {

    static class Freezer {
        void freeze(Lock lock) {
            try {
                lock.lock();
                TheUtils.println("<");
                TheUtils.randomFreeze(100);
                TheUtils.println(" >");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock(true);

        ExecutorService executor = Executors.newFixedThreadPool(6);

        IntStream.range(0, 6).forEach(t -> {
            final Freezer f = new Freezer();
            executor.execute(() -> {
                IntStream.range(0, 3).forEach(e -> {
                    f.freeze(lock);
                });
            });
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("");
    }
}
