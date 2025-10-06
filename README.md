# ASSIGNMENT 2: ALGORITHMIC ANALYSIS AND PEER CODE REVIEW
Comparison with Partner’s Algorithm: Shell Sort vs Heap Sort

1. ***Theoretical Complexity Comparison***

| Aspect | Shell Sort (Sedgewick Gaps) | Heap Sort |
|---------|-----------------------------|-----------|
| Algorithm Type | Incremental improvement over Insertion Sort using gap-based passes | Binary Heap–based selection algorithm |
| Approach | Performs a series of gapped insertion sorts, gradually reducing the gap | Builds a max heap and repeatedly extracts the largest element |
| Time Complexity | Best: O(n log n) <br> Average: O(n¹·²⁵ – n¹·³) <br> Worst: O(n⁴⁄³) | Best: O(n log n) <br> Average: O(n log n) <br> Worst: O(n log n) |
| Stability | Not stable | Not stable |

⸻

2. Empirical Results

Experimental Setup

Parameter  Value
Input Sizes  10, 100, 1,000, 10,000, 100,000
Trials per Size  5
Metrics Recorded  Runtime (ns), Comparisons, Assignments, Max Depth

⸻

***Performance Plots (Time vs Input Size)***

If plotted on a log–log graph, both algorithms demonstrate growth close to n log n.

Shell Sort’s line lies slightly below Heap Sort for smaller datasets, confirming its smaller constant factors.

Time vs Input Size: Shell Sort grows sub-quadratically, Heap Sort follows log-linear scaling.

Comparisons vs Input Size: Heap Sort is steady at O(n log n), Shell Sort grows slightly faster due to multiple passes.

Assignments vs Input Size: Shell Sort performs more writes (shifts), Heap Sort performs fewer but larger swaps.

⸻
3. Validation of Theoretical Complexity

Empirical performance validates the theoretical expectations:
•  Shell Sort’s measured runtime grows faster than O(n log n) but remains below O(n²), confirming the expected O(n¹·²⁵)–O(n¹·³) range.
•  Heap Sort’s measurements align closely with O(n log n).
•  On small and medium datasets (n ≤ 10,000), Shell Sort consistently runs faster due to better cache locality.
•  On large datasets (n ≥ 100,000), Heap Sort’s asymptotic advantage dominates, becoming slightly faster overall.

⸻

4. Conclusion

Shell Sort achieves sub-quadratic performance (around O(n¹·²⁵)) and is highly efficient for small to medium inputs.

Heap Sort guarantees O(n log n) complexity and scales more reliably for large datasets.

Empirical results confirm the theory: Shell Sort is faster for moderate data sizes, while Heap Sort is asymptotically optimal.

Both algorithms use O(1) extra space and are suitable for in-place sorting.