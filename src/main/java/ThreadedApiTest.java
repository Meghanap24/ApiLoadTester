import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadedApiTest {
    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts/1";
        int totalRequests = 20;  // total requests
        int threads = 5;         // how many will run at the same time

        for (int i = 1; i <= threads; i++) {
            Thread t = new Thread(() -> {
                for (int j = 1; j <= totalRequests / threads; j++) {
                    long startTime = System.currentTimeMillis();
                    try {
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();
                        long endTime = System.currentTimeMillis();

                        System.out.println(Thread.currentThread().getName() +
                                " | Code: " + responseCode +
                                " | Time: " + (endTime - startTime) + " ms");
                        String threadName = Thread.currentThread().getName();
                        long responseTime = endTime - startTime;

                        CsvWriterHelper.writeResult(threadName, responseCode, responseTime);

                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
                    }
                }
            });
            t.start();
        }
    }
}
