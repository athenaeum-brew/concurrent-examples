public class Example01Async {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("Task 1")).start();
        new Thread(() -> System.out.println("Task  2")).start();
        new Thread(() -> System.out.println("Task   3")).start();
    }
}
