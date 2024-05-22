public class Example10BumperSync {

    final static String format = "\033[90m[%s]\033[0m %s ";

    public static void main(String[] args) {

        TheBumper bumper = new TheBumper();

        System.out.println(bumper.title());

        for (int i = 0; i < bumper.length(); i++) {
            System.out.println(String.format(format, Thread.currentThread().getName(), bumper.next()));
        }

        System.out.println("");
    }

}
