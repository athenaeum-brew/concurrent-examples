import java.io.Closeable;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Example31Atomic {
    static public final int iterations = 1_000_000;

    public static void main(String[] args) throws InterruptedException, IOException {
        int cpus = Runtime.getRuntime().availableProcessors();

        try (Incrementor incrementor = new AtomicIncrementor();
                ExecutorService service = Executors.newFixedThreadPool(cpus)) {

            IntStream.range(0, iterations).forEach(count -> service.submit(incrementor::increment));

            service.shutdown();
            service.awaitTermination(10, TimeUnit.SECONDS);
        }

    }

    static interface Incrementor extends Closeable {
        int get();

        void increment();
    }

    static class Spy {
        final static Set<String> threadNames = ConcurrentHashMap.newKeySet();

        final static String format = "\033[90m[%s]\033[0m %s";

        void spy(int i) {
            if (threadNames.add(Thread.currentThread().getName())) {
                System.out.println(String.format(format, Thread.currentThread().getName(), i));
            }
        }
    }

    static abstract class AbstractIncrementor extends Spy implements Incrementor {
        final static String formatRed = "\033[31m%s\033[0m\n";
        final static String formatGreen = "\033[32m%s\033[0m\n";

        @Override
        public void close() throws IOException {
            if (get() == iterations) {
                System.out.println(String.format(formatGreen, Integer.toString(get())));
            } else {
                System.out.println(String.format(formatRed, Integer.toString(get())));
            }
        }
    }

    static class BasicIncrementor extends AbstractIncrementor {
        int sum = 0;

        BasicIncrementor() {
            System.out.println("\n" + this.getClass().getSimpleName());
        }

        @Override
        public /* synchronized */ void increment() {
            sum++;
            spy(sum);
        }

        @Override
        public int get() {
            return sum;
        }

    }

    static class AtomicIncrementor extends AbstractIncrementor {
        private AtomicInteger sum = new AtomicInteger(0);

        AtomicIncrementor() {
            System.out.println("\n" + this.getClass().getSimpleName());
        }

        @Override
        public void increment() {
            int current = sum.getAndIncrement();
            spy(current);
        }

        @Override
        public int get() {
            return sum.get();
        }
    }

}
