import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiLoadTester {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Take input from user
        System.out.print("Enter API URL: ");
        String apiUrl = scanner.nextLine();

        System.out.print("Enter total number of requests: ");
        int totalRequests = scanner.nextInt();

        System.out.print("Enter number of threads: ");
        int threads = scanner.nextInt();

        // Clear previous results.csv
        try (PrintWriter writer = new PrintWriter(new File("results.csv"))) {
            writer.println("Thread,StatusCode,ResponseTime(ms)");
        } catch (FileNotFoundException e) {
            System.out.println("Error creating CSV file.");
        }

        // 2️⃣ Start Load Test
        System.out.println("\n🚀 Starting Load Test...\n");

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
                        long responseTime = endTime - startTime;

                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + " | Code: " + responseCode + " | Time: " + responseTime + " ms");

                        // Save to CSV
                        writeResult(threadName, responseCode, responseTime);

                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
                    }
                }
            });
            t.start();
        }

        // Wait for all threads to finish
        Thread.sleep(3000); // Adjust if needed

        // 3️⃣ Show Summary
        showSummary();
    }

    // Method to write result to CSV
    public static synchronized void writeResult(String threadName, int code, long time) {
        try (FileWriter writer = new FileWriter("results.csv", true)) {
            writer.write(threadName + "," + code + "," + time + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }

    // Method to read CSV and show summary
    public static void showSummary() {
        String csvFile = "results.csv";
        String line;
        long totalTime = 0;
        long fastest = Long.MAX_VALUE;
        long slowest = Long.MIN_VALUE;
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                long time = Long.parseLong(data[2].trim());

                totalTime += time;
                if (time < fastest) fastest = time;
                if (time > slowest) slowest = time;
                count++;
            }

            if (count > 0) {
                double average = (double) totalTime / count;
                System.out.println("\n📊 Summary Report");
                System.out.println("-------------------------");
                System.out.println("Total Requests: " + count);
                System.out.println("Average Time: " + average + " ms");
                System.out.println("Fastest Time: " + fastest + " ms");
                System.out.println("Slowest Time: " + slowest + " ms");
            } else {
                System.out.println("No data found in results.csv");
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
    }
}

