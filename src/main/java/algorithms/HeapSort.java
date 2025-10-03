package algorithms;

import metrics.PerformanceTracker;

public class HeapSort {
    public static void heapSort(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, tracker);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            tracker.incrementComparisonCounter();
            tracker.incrementAllocationCounter();
            tracker.increaseRecursionDepth();

            heapify(arr, i, 0, tracker);
            tracker.decreaseRecursionDepth();
        }
    }

    private static void heapify(int[] arr, int n, int i, PerformanceTracker tracker) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
            tracker.incrementComparisonCounter();
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
            tracker.incrementComparisonCounter();
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            tracker.incrementAllocationCounter();
            tracker.incrementComparisonCounter();

            heapify(arr, n, largest, tracker);
        }
    }
}
