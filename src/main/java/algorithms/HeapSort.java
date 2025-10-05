package algorithms;

import metrics.PerformanceTracker;

public final class HeapSort {

    private HeapSort() {}

    public static void heapSort(int[] a, PerformanceTracker tracker) {
        int n = a.length;
        if (n < 2) return;

        buildMaxHeap(a, n, tracker);

        for (int end = n - 1; end > 0; end--) {
            int tmp = a[0];
            a[0] = a[end];
            a[end] = tmp;

            siftDown(a, 0, end, tracker);
        }

        long depth = (long) (Math.log(n) / Math.log(2));
        for (int i = 0; i < depth; i++) tracker.increaseRecursionDepth();
    }

    private static void buildMaxHeap(int[] a, int n, PerformanceTracker tracker) {
        for (int i = (n >>> 1) - 1; i >= 0; i--) {
            siftDown(a, i, n, tracker);
        }
    }

    private static void siftDown(int[] a, int i, int heapSize, PerformanceTracker tracker) {
        int x = a[i];
        while (true) {
            int left = (i << 1) + 1;
            if (left >= heapSize) break;

            int right = left + 1;
            int child = left;

            if (right < heapSize) {
                tracker.incrementComparisonCounter();
                if (a[right] > a[left]) child = right;
            }

            tracker.incrementComparisonCounter();
            if (a[child] > x) {
                a[i] = a[child];
                i = child;
            } else break;
        }
        a[i] = x;
    }
}
