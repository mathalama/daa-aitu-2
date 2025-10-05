import algorithms.HeapSort;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {
    private static final String METRICS_FILE_NAME = "target/metrics.csv";

    @BeforeAll
    static void clearMetricsFile() {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(METRICS_FILE_NAME));
            if (deleted) {
                System.out.println("Metrics file deleted successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error while trying to delete metrics file: " + e.getMessage());
        }
    }

    @BeforeAll
    static void warmUpJvm() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int[] dummyArray = new int[1];
            for (int j = 0; j < dummyArray.length; j++) {
                dummyArray[j] = random.nextInt();
            }
            HeapSort.heapSort(dummyArray, new PerformanceTracker());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000, 100000})
    public void testHeapSortWithDifferentArraySizes(int size) throws IOException {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }

        PerformanceTracker metrics = new PerformanceTracker();

        long startTime = System.nanoTime();

        HeapSort.heapSort(arr, metrics);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Sorting " + size + " elements took: " + duration + " nanoseconds.");

        metrics.writeMetricsToCSV(duration, "HeapSort_Size " + size);

        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array should be sorted in ascending order.");
        }
    }
}
