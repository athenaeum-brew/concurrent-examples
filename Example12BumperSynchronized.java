import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example12BumperSynchronized {

    final static String format = "\033[90m[%s]\033[0m %s ";

    public static void main(String[] args) {
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
        while (!executor.isTerminated()) {
            ; // Wait for all threads to finish
        }

        System.out.println("");
    }

}
