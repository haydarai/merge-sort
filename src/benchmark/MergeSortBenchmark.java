package benchmark;

import com.google.gson.Gson;
import models.BenchmarkConfig;
import models.BenchmarkResult;
import sort.MergeSort;
import utils.MemoryMappedInputStream;
import utils.MemoryMappedOutputStream;
import utils.RandomInteger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

class MergeSortBenchmark {

    private BenchmarkConfig config;
    private int bufferSize;
    private RandomInteger random;
    private String RESULT_DIR = "results/";
    private Gson gson;

    MergeSortBenchmark(BenchmarkConfig config) {
        this.config = config;
        this.bufferSize = config.getRunNumber() * 4; // 1 integer is 4 bytes
        this.random = new RandomInteger();
        this.gson = new Gson();

        // Create RESULT_DIR if not exists
        File dataDir = new File(this.RESULT_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    void run() {
        MemoryMappedInputStream inputStream = new MemoryMappedInputStream(this.bufferSize);
        MemoryMappedOutputStream outputStream = new MemoryMappedOutputStream(this.bufferSize);
        MergeSort mergeSort = new MergeSort(inputStream, outputStream);
        try {
            // Creating sample data to sort, assume that data/ directory is created
            outputStream.create("data/input.dat");
            for (int i = 0; i < config.getRunNumber(); i++) {
                outputStream.write(random.nextInt());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String outputFileName = config.generateResultFilename();
        BenchmarkResult.Summary streamSummary = new BenchmarkResult.Summary();
        streamSummary.setStart(System.nanoTime());
        try {
            mergeSort.sort("data/input.dat", config.getAvailableMemory());
            mergeSort.merge(config.getDWayMerge());

            inputStream.open("data/result.dat");
            while (!inputStream.endOfStream()) {
                inputStream.readNext();
            }

            streamSummary.setEnd(System.nanoTime());
            streamSummary.setElapsed(streamSummary.getEnd() - streamSummary.getStart());
            final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.RESULT_DIR, outputFileName),
                    true));
            BenchmarkResult result = new BenchmarkResult();
            result.setSummary(streamSummary);
            writer.write(gson.toJson(result));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
