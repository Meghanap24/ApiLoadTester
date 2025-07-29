import java.net.HttpURLConnection;
import java.net.URL;

public class LoopApiTest {
    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/1";

        // Loop to send 10 requests
        for (int i = 1; i <= 10; i++) {
            long startTime = System.currentTimeMillis();
            try {
                // Connect to API
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Get response code
                int responseCode = connection.getResponseCode();
                long endTime = System.currentTimeMillis();

                // Print request number, code, and time
                System.out.println("Request " + i + " | Code: " + responseCode +
                        " | Time: " + (endTime - startTime) + " ms");

            } catch (Exception e) {
                System.out.println("Request " + i + " failed: " + e.getMessage());
            }
        }
    }
}
