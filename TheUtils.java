import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

public class TheUtils {
    static final private Random r = new Random(new Date().getTime());

    static void randomFreeze(int max) {
        try {
            Thread.sleep(r.nextLong(0, max));
        } catch (InterruptedException e) {
        }
    }

    static class NamedThreadFactory implements ThreadFactory {
        private int count;

        public NamedThreadFactory() {
            this.count = 0;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, String.format("t-%02d", count++));
            return thread;
        }
    }

}
