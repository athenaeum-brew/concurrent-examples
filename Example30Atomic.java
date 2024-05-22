import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Example30Atomic {

    static class Holder {
        int i = 0;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        long accrual = 0;
    }

    public static void main(String[] args) throws InterruptedException {
        Holder holder = new Holder();
        int iterations = 100_000;
        long iterations_as_long = iterations;
        long expected = iterations_as_long * (iterations_as_long + 1) / 2L;
        int cpus = Runtime.getRuntime().availableProcessors();

        ExecutorService service = Executors.newFixedThreadPool(cpus);

        IntStream.range(0, iterations)
                .forEach(c -> service.submit(() -> {
                    synchronized (holder) {
                        holder.accrual += holder.atomicInteger.incrementAndGet();
                    }
                    ++holder.i;
                }));

        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println(String.format("\n%14s: %14d\n%14s: %14d\n%14s: %14d\n%14s: %14d\n",
                "int",
                holder.i,
                "atomicInteger",
                holder.atomicInteger.get(),
                "accrual",
                holder.accrual,
                "expected",
                expected));
    }
}
