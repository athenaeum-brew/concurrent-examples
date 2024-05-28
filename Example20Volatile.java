public class Example20Volatile {

    static class Toggler {
        private /* volatile */  boolean flag = false;

        public void toggleFlag() {
            flag = !flag;
        }

        public boolean isFlag() {
            return flag;
        }
    }

    public static void main(String[] args) {
        Toggler example = new Toggler();

        System.out.println("");

        // Thread 1: Modifies the flag
        new Thread(() -> {
            TheUtils.randomFreeze(100);
            example.toggleFlag();
            System.out.println("Flag set to true.");
        }).start();

        // Thread 2: Reads the flag
        new Thread(() -> {
            while (!example.isFlag()) {
                // Busy wait until flag becomes true
                // System.out.print(".");
            }
            System.out.println("Flag is now true.");
        }).start();

    }
}
