import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example12BumperSynchronized {

    final static String format = "\033[90m[%s]\033[0m %s ";

    public static void main(String[] args) throws InterruptedException {
        TheBumper bumper = new TheBumper();
        int nThreads = bumper.length();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        System.out.println(bumper.title());

        for (int i = 0; i < nThreads; i++) {
            executor.execute(() -> {
                synchronized (bumper) {
                    TheUtils.randomFreeze(10);
                    System.out.println(String.format(format, Thread.currentThread().getName(), bumper.next()));
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("");
    }

}
