Java API Load Testing Tool
--------------------------------
A beginner-friendly Java-based API Load Testing Tool to simulate multiple concurrent API requests, 
measure response times, and log results into a CSV file with a summary report.

Features:
--------------------------------
- Simulates concurrent API requests using Java threads
- Logs status codes and response times to results.csv
- Generates summary report (average, fastest, slowest times)
- Simple CLI: API URL, total requests, number of threads

Tech Stack:
--------------------------------
- Java 17
- Core Java (HttpURLConnection, Threads)
- CSV Writing (FileWriter)

Project Structure:
--------------------------------
ApiLoadTester/
  src/main/java/
    ApiLoadTester.java       - Main combined tool
    CsvWriterHelper.java     - CSV writer utility
    SimpleApiTest.java       - Single API request test
    LoopApiTest.java         - Multiple requests loop test
    ThreadedApiTest.java     - Multithreaded API testing
    SummaryReport.java       - Summary results generator
