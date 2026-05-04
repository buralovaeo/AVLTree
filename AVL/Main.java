package AVL;

public class Main {
    public static void main(String[] args) throws Exception {
        Benchmark bm = new Benchmark();

        bm.generateRandomArray();      // пункт 2
        bm.runInsertBenchmark();       // пункт 3
        bm.runSearchBenchmark();       // пункт 4
        bm.runDeleteBenchmark();       // пункт 5
        bm.saveResults();              // пункт 6
        bm.printAverages();            // пункт 6
    }
}
