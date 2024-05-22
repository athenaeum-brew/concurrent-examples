import java.util.Date;
import java.util.Random;

public class Example02SyncAndAsync {
    static final Random r = new Random(new Date().getTime());

    static void randomSleep() {
        try {
            Thread.sleep(r.nextLong(0, 10));
        } catch (InterruptedException e) {
        }
    }

    static void asyncTask(final int n) {
        new Thread(() -> syncTask(n)).start();
    }

    static void syncTask(final int n) {
        randomSleep();
        System.out.println(String.format("Task %" + n + "d", n));
    }

    public static void main(String[] args) {
        int c = 3;
        while (c-- > 0) {
            System.out.println("---");
            asyncTask(1);
            asyncTask(2);
            asyncTask(3);
        }
    }
}
