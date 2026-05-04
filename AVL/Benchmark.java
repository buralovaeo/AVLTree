package AVL;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {
    private static final int ARRAY_SIZE = 10000;
    private static final int SEARCH_COUNT = 100;
    private static final int DELETE_COUNT = 1000;
    private static final int MAX_VALUE = 100000;

    private long[] insertTimes;
    private int[] insertOps;
    private long[] searchTimes;
    private int[] searchOps;
    private long[] deleteTimes;
    private int[] deleteOps;

    private int[] originalArray;

    public Benchmark() {
        insertTimes = new long[ARRAY_SIZE];
        insertOps = new int[ARRAY_SIZE];
        searchTimes = new long[SEARCH_COUNT];
        searchOps = new int[SEARCH_COUNT];
        deleteTimes = new long[DELETE_COUNT];
        deleteOps = new int[DELETE_COUNT];
        originalArray = new int[ARRAY_SIZE];
    }

    // Пункт 2
    public void generateRandomArray() {
        Random random = new Random(42);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            originalArray[i] = random.nextInt(MAX_VALUE);
        }
    }

    // Пункт 3
    public void runInsertBenchmark() {
        AVLTree tree = new AVLTree();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            long startTime = System.nanoTime();
            int ops = tree.insertWithCounter(originalArray[i]);
            long endTime = System.nanoTime();

            insertTimes[i] = endTime - startTime;
            insertOps[i] = ops;
        }
    }

    // Пункт 4
    public void runSearchBenchmark() {
        AVLTree tree = new AVLTree();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            tree.insertWithCounter(originalArray[i]);
        }

        Random searchRandom = new Random(43);
        for (int i = 0; i < SEARCH_COUNT; i++) {
            int index = searchRandom.nextInt(ARRAY_SIZE);
            int value = originalArray[index];

            long startTime = System.nanoTime();
            int ops = tree.searchWithCounter(value);
            long endTime = System.nanoTime();

            searchTimes[i] = endTime - startTime;
            searchOps[i] = ops;
        }
    }

    // Пункт 5
    public void runDeleteBenchmark() {
        Random deleteRandom = new Random(44);

        for (int i = 0; i < DELETE_COUNT; i++) {
            AVLTree tree = new AVLTree();
            for (int j = 0; j < ARRAY_SIZE; j++) {
                tree.insertWithCounter(originalArray[j]);
            }

            int index = deleteRandom.nextInt(ARRAY_SIZE);
            int value = originalArray[index];

            long startTime = System.nanoTime();
            int ops = tree.deleteWithCounter(value);
            long endTime = System.nanoTime();

            deleteTimes[i] = endTime - startTime;
            deleteOps[i] = ops;
        }
    }

    // Пункт 6: Сохранение всех результатов в файлы
    public void saveResults() throws Exception {
        // CSV файлы с детальными замерами
        try (PrintWriter w = new PrintWriter(new FileWriter("insert_results.csv"))) {
            w.println("index,value,time_ns,operations");
            for (int i = 0; i < ARRAY_SIZE; i++) {
                w.printf("%d,%d,%d,%d%n", i, originalArray[i], insertTimes[i], insertOps[i]);
            }
        }

        try (PrintWriter w = new PrintWriter(new FileWriter("search_results.csv"))) {
            w.println("search_index,value,time_ns,operations");
            for (int i = 0; i < SEARCH_COUNT; i++) {
                w.printf("%d,%d,%d,%d%n", i, originalArray[i % ARRAY_SIZE], searchTimes[i], searchOps[i]);
            }
        }

        try (PrintWriter w = new PrintWriter(new FileWriter("delete_results.csv"))) {
            w.println("delete_index,value,time_ns,operations");
            for (int i = 0; i < DELETE_COUNT; i++) {
                w.printf("%d,%d,%d,%d%n", i, originalArray[i % ARRAY_SIZE], deleteTimes[i], deleteOps[i]);
            }
        }

        // Файл со средними значениями (для отчета)
        try (PrintWriter w = new PrintWriter(new FileWriter("averages.txt"))) {
            w.println("СРЕДНИЕ ЗНАЧЕНИЯ");
            w.printf("Вставка (n=%d): время = %.2f мкс, операций = %.1f%n",
                    ARRAY_SIZE, getAverageTime(insertTimes) / 1000, getAverageOps(insertOps));
            w.printf("Поиск (n=%d): время = %.2f мкс, операций = %.1f%n",
                    SEARCH_COUNT, getAverageTime(searchTimes) / 1000, getAverageOps(searchOps));
            w.printf("Удаление (n=%d): время = %.2f мкс, операций = %.1f%n",
                    DELETE_COUNT, getAverageTime(deleteTimes) / 1000, getAverageOps(deleteOps));
        }
    }

    // Статистические методы
    private double getAverageTime(long[] arr) {
        long sum = 0;
        for (long v : arr) sum += v;
        return (double) sum / arr.length;
    }

    private double getAverageOps(int[] arr) {
        long sum = 0;
        for (int v : arr) sum += v;
        return (double) sum / arr.length;
    }

    // Вывод в консоль
    public void printAverages() {
        System.out.println("\nСРЕДНИЕ ЗНАЧЕНИЯ");
        System.out.printf("Вставка (n=%d): время = %.2f мкс, операций = %.1f%n",
                ARRAY_SIZE, getAverageTime(insertTimes) / 1000, getAverageOps(insertOps));
        System.out.printf("Поиск (n=%d): время = %.2f мкс, операций = %.1f%n",
                SEARCH_COUNT, getAverageTime(searchTimes) / 1000, getAverageOps(searchOps));
        System.out.printf("Удаление (n=%d): время = %.2f мкс, операций = %.1f%n",
                DELETE_COUNT, getAverageTime(deleteTimes) / 1000, getAverageOps(deleteOps));
    }
}
