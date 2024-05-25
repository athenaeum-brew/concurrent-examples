import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Example51CompletableFuture {

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Asynchronous HTTP request
            try {
                URI uri = new URI("https://questioneer.cthiebaud.com/hello.txt");
                URL url = uri.toURL();
                TheUtils.println("Connecting to " + url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read response
                StringBuilder response = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                }
                return response.toString();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                return "Error occurred";
            }
        });

        // Attach a callback to the CompletableFuture
        CompletableFuture<Void> future2 = future.thenAccept(result -> TheUtils.println("Response: " + result));

        // Start a dot printing loop in the main thread
        while (!future.isDone()) {
            System.out.print(".");
            try {
                TimeUnit.MILLISECONDS.sleep(500); // Sleep for 500 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Print a newline character when future.isDone() is true
        TheUtils.println("");

        // Shutdown the CompletableFuture's executor
        future2.join(); // This will block until the CompletableFuture completes
    }
}
