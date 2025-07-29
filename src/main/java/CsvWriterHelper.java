import java.io.FileWriter;
import java.io.IOException;

public class CsvWriterHelper {

    // Synchronized to prevent multiple threads writing at same time
    public static synchronized void writeResult(String threadName, int code, long time) {
        try (FileWriter writer = new FileWriter("results.csv", true)) {
            writer.write(threadName + "," + code + "," + time + " ms\n");
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }
}

