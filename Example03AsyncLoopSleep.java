import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

public class Example03AsyncLoopSleep {

    static void syncTask(final int n) {
        randomFreeze(); // wait for some random duration
        System.out.println(String.format("Task %" + n + "d", n));
    }

    static void asyncTask(final int n) {
        new Thread(() -> syncTask(n)).start();
    }

    public static void main(String[] args) {
        IntStream.range(0, 3).forEach(c -> {
            asyncTask(1);
            asyncTask(2);
            asyncTask(3);
        });
    }

    static final Random r = new Random(new Date().getTime());

    static void randomFreeze() {
        try {
            Thread.sleep(r.nextLong(0, 10));
        } catch (InterruptedException e) {
        }
    }
}
