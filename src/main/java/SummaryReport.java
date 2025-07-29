import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SummaryReport {
    public static void main(String[] args) {
        String csvFile = "results.csv";
        String line;
        long totalTime = 0;
        long fastest = Long.MAX_VALUE;
        long slowest = Long.MIN_VALUE;
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Response time is in third column (remove " ms")
                long time = Long.parseLong(data[2].replace(" ms", "").trim());

                totalTime += time;
                if (time < fastest) fastest = time;
                if (time > slowest) slowest = time;
                count++;
            }

            if (count > 0) {
                double average = (double) totalTime / count;
                System.out.println("📊 Summary Report");
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
