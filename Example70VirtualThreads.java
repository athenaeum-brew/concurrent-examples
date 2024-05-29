/*
   javac --enable-preview --release 22 Example60VirtualThreads.java
   java --enable-preview Example60VirtualThreads
 */

public class Example70VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        // Create and start a virtual thread
        Thread vThread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from virtual thread!");
        });

        // Wait for the virtual thread to finish
        vThread.join();

        // Create and start multiple virtual threads
        int numThreads = 100;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = Thread.ofVirtual().start(() -> {
                System.out.println("Thread " + Thread.currentThread().getId() + " is running.");
            });
        }

        // Wait for all virtual threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("All virtual threads have finished.");
    }
}
