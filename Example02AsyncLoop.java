public class Example02AsyncLoop {
    public static void main(String[] args) {
        int c = 3;
        while (c-- > 0 ) {
            new Thread(() -> System.out.println("Task 1")).start();
            new Thread(() -> System.out.println("Task  2")).start();
            new Thread(() -> System.out.println("Task   3")).start();
        }
    }
}
