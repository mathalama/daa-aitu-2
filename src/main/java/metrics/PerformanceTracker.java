package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.LongAdder;

public class PerformanceTracker {
    private final LongAdder comparisonCounter = new LongAdder();
    private final LongAdder allocationCounter = new LongAdder();
    private final LongAdder currentRecursionDepth = new LongAdder();
    private final LongAdder maxRecursionDepth = new LongAdder();

    public void incrementComparisonCounter() {
        comparisonCounter.increment();
    }

    public void incrementAllocationCounter() {
        allocationCounter.increment();
    }

    public void increaseRecursionDepth() {
        currentRecursionDepth.increment();
        long currentDepth = currentRecursionDepth.sum();
        if (currentDepth > maxRecursionDepth.sum()) {
            maxRecursionDepth.reset();
            maxRecursionDepth.add(currentDepth);
        }
    }

    public void decreaseRecursionDepth() {
        currentRecursionDepth.decrement();
    }

    public void reset() {
        comparisonCounter.reset();
        allocationCounter.reset();
        currentRecursionDepth.reset();
        maxRecursionDepth.reset();
    }

    public void writeMetricsToCSV(long timeTaken, String algorithmName) throws IOException {
        try (FileWriter fileWriter = new FileWriter("target/metrics.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("%s,%d,%d,%d,%d%n",
                    algorithmName,
                    timeTaken,
                    comparisonCounter.sum(),
                    allocationCounter.sum(),
                    maxRecursionDepth.sum());
        }
    }

    public long getComparisonCount() { return comparisonCounter.sum(); }
    public long getAllocationCount() { return allocationCounter.sum(); }
    public long getMaxRecursionDepth() { return maxRecursionDepth.sum(); }
}
