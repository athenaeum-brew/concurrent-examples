import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadFactory;

public class TheUtils {
    static final private Random r = new Random(new Date().getTime());
    static final private String format = "\033[90m[%s] %s\033[0m %s ";
    static final private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

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

    static void println(String s) {
        System.out.println(String.format(format,
                Thread.currentThread().getName(),
                "", // LocalTime.now().format(formatter),
                s));
    }

}
