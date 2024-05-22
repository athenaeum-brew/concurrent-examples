public final class TheBumper {
    private final String title = "\nA programmer had a problem. He thought to himself, \"I know, I'll solve it with threads!\".\n";
    private final String[] array = new String[] { "Now,", "he", "has", "two", "problems." };
    private int count = 0;

    public final String next() {
        return array[increment()];
    }

    private final int increment() {
        return count++;
    }

    public final int length() {
        return array.length;
    }

    public final String title() {
        return title;
    }
}
