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
    protected long lastLoggedTime = 0;

    public void incrementComparisonCounter() {
        comparisonCounter.increment();
    }

    public void incrementAllocationCounter() {
        allocationCounter.increment();
    }

    public void increaseRecursionDepth() {
        currentRecursionDepth.increment();
        maxRecursionDepth.add(currentRecursionDepth.sum());
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
        long currentTime = System.nanoTime();

        try (FileWriter fileWriter = new FileWriter("targets/metrics.csv", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("%s, %d, %d, %d, %d\n",
                    algorithmName,
                    timeTaken,
                    comparisonCounter.sum(),
                    allocationCounter.sum(),
                    maxRecursionDepth.sum());
            lastLoggedTime = currentTime;
        }
    }

    public long getTimeBeforeExecution() {
        return System.nanoTime();
    }

    public long getTimeAfterExecution(long startTime) {
        return System.nanoTime() - startTime;
    }
}
