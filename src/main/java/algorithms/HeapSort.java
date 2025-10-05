package algorithms;

import metrics.PerformanceTracker;

public final class HeapSort {
    private static void buildMaxHeap(int[] a, int n, PerformanceTracker Tracker) {
        for (int i = (n >>> 1) - 1; i >= 0; i--) {
            Tracker.increaseRecursionDepth();
            try {
                siftDown(a, i, n, Tracker);
            } finally {
                Tracker.decreaseRecursionDepth();
            }
        }
    }

    public static void heapSort(int[] a, PerformanceTracker Tracker) {
        final int n = a.length;
        if (n < 2) return;

        buildMaxHeap(a, n, Tracker);

        for (int end = n - 1; end > 0; end--) {
            int tmp = a[0]; Tracker.incrementAllocationCounter();
            a[0] = a[end];  Tracker.incrementAllocationCounter();
            a[end] = tmp;   Tracker.incrementAllocationCounter();

            Tracker.increaseRecursionDepth();
            try {
                siftDown(a, 0, end, Tracker);
            } finally {
                Tracker.decreaseRecursionDepth();
            }
        }
    }

    private static void siftDown(int[] a, int i, int heapSize, PerformanceTracker Tracker) {
        int x = a[i]; Tracker.incrementAllocationCounter();
        while (true) {
            int left = (i << 1) + 1;
            if (left >= heapSize) break;

            int right = left + 1;
            int child = left;

            if (right < heapSize) {
                Tracker.incrementComparisonCounter();
                if (a[right] > a[left]) child = right;
            }

            Tracker.incrementComparisonCounter();
            if (a[child] > x) {
                a[i] = a[child]; Tracker.incrementAllocationCounter();
                i = child;
            } else break;
        }
        a[i] = x; Tracker.incrementAllocationCounter();
    }
}
