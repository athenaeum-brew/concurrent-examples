import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Example41ReentrantLock {

    static class Freezer {
        void freeze(Lock lock, boolean ignore) {
            if (ignore) {
                go();
                return;
            }
            try {
                lock.lock();
                go();
            } finally {
                lock.unlock();
            }
            // TheUtils.randomFreeze(10);
        }

        private void go() {
            TheUtils.println("<");
            TheUtils.randomFreeze(100);
            TheUtils.println(" >");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final boolean doLock = args.length > 0 ? Boolean.parseBoolean(args[0]) : true;
        final boolean fairness = args.length > 1 ? Boolean.parseBoolean(args[1]) : false;
        final int nThreads = args.length > 2 ? Integer.parseInt(args[2]) : 6;
        System.out.println(String.format("\nLock ...... %b\nFairness .. %b\nThreads ... %d\n",
                doLock,
                fairness,
                nThreads));

        final Lock lock = new ReentrantLock(fairness);
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        IntStream.range(0, nThreads).forEach(t -> {
            final Freezer f = new Freezer();
            executor.execute(() -> {
                IntStream.range(0, 7).forEach(e -> {
                    f.freeze(lock, !doLock);
                });
            });
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("");
    }
}
