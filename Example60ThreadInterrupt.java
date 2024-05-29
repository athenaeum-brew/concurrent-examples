public class Example60ThreadInterrupt {

    public static void main(String[] args) {
        // Create a new thread
        Thread thread = new Thread(new RunnableTask());

        // Start the thread
        thread.start();

        // Sleep for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Handle interruption during sleep
            e.printStackTrace();
        }

        // Interrupt the thread
        thread.interrupt();
    }

    static class RunnableTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                // Check if the thread is interrupted
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread was interrupted, stopping...");
                    break;
                }

                // Simulate some work
                System.out.println("Thread is running...");

                try {
                    // Sleep for a while
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // If interrupted during sleep, set the interrupt flag and break the loop
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted during sleep, stopping...");
                    break;
                }
            }
        }
    }
}
