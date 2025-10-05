package cli;

import algorithms.HeapSort;
import metrics.PerformanceTracker;

import java.util.*;
import java.io.File;
import java.io.IOException;

public final class BenchmarkRunner {

    private static Map<String,String> argsMap(String[] args) {
        Map<String,String> m = new LinkedHashMap<>();
        for (String a : args) {
            if (a.equals("-h") || a.equals("--help")) { m.put("help","1"); continue; }
            if (a.startsWith("--")) {
                int i = a.indexOf('=');
                if (i > 2) m.put(a.substring(2, i), a.substring(i+1));
                else m.put(a.substring(2), "true");
            }
        }
        return m;
    }

    private static int[] parseSizes(String s) {
        String[] parts = s.split(",");
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i].trim().replace("_", "");
            out[i] = Integer.parseInt(p);
        }
        return out;
    }

    private static int[] genArray(int n, String dist, long seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();

        dist = dist.toLowerCase();
        if (dist.equals("sorted")) {
            Arrays.sort(a);
        } else if (dist.equals("reversed")) {
            Arrays.sort(a);
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
        } else if (dist.equals("nearly-sorted")) {
            Arrays.sort(a);
            int swaps = Math.max(1, n / 20);
            for (int k = 0; k < swaps; k++) {
                int i = rnd.nextInt(n), j = rnd.nextInt(n);
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
        }
        return a;
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("  java -cp target/classes cli.BenchmarkRunner \\");
        System.out.println("    --sizes=100,1000,10000 \\");
        System.out.println("    --trials=3 \\");
        System.out.println("    --dist=random|sorted|reversed|nearly-sorted \\");
        System.out.println("    --seed=42 \\");
        System.out.println("    --warmup=1   # optional warmup trials per size");
    }

    public static void main(String[] args) throws IOException {
        Map<String,String> a = argsMap(args);
        if (a.isEmpty() || a.containsKey("help")) { usage(); return; }

        int[] sizes = parseSizes(a.getOrDefault("sizes", "100,1000,10000"));
        int trials = Integer.parseInt(a.getOrDefault("trials", "3"));
        int warmup = Integer.parseInt(a.getOrDefault("warmup", "0"));
        String dist = a.getOrDefault("dist", "random");
        long seed = Long.parseLong(a.getOrDefault("seed", "42"));

        new File("target").mkdirs();

        System.out.println("Config: dist=" + dist + ", trials=" + trials +
                ", warmup=" + warmup + ", seed=" + seed + ", sizes=" + Arrays.toString(sizes));

        PerformanceTracker tracker = new PerformanceTracker();

        for (int n : sizes) {
            for (int w = 0; w < warmup; w++) {
                int[] arrW = genArray(n, dist, seed + n + 1000 + w);
                HeapSort.heapSort(arrW, tracker);
                tracker.reset();
            }

            for (int t = 1; t <= trials; t++) {
                tracker.reset();

                int[] arr = genArray(n, dist, seed + n + t);
                long start = System.nanoTime();
                HeapSort.heapSort(arr, tracker);
                long dur = System.nanoTime() - start;

                String name = "HeapSort_" + dist + "_Size " + n;
                tracker.writeMetricsToCSV(dur, name);
                System.out.println("HeapSort n=" + n + " trial=" + t + " -> " + dur + " ns");
            }
        }
        System.out.println("OK -> target/metrics.csv");
    }
}

