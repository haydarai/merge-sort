package benchmark;

import models.BenchmarkConfig;
import sort.DataGenerator;
import utils.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {
    private String filepath;

    public BenchmarkRunner(String filepath) {
        this.filepath = filepath;
    }

    public void run() {
        try {
            List<BenchmarkConfig> configs = BenchmarkInputReader.readCSV(filepath);
            for (BenchmarkConfig config: configs) {
                String kind = config.getKind();
                if (kind.equalsIgnoreCase("Merge-Sort")) {
                    runMergeSort(config);
                } else {
                    String type = config.getType();
                    switch (type) {
                        case "Input": {
                            runInput(config);
                            break;
                        }
                        case "Output": {
                            runOutput(config);
                            break;
                        }
                        default: {
                            runMergeSort(config);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runInput(BenchmarkConfig config) {
        long n = config.getRunNumber();
        int k = config.getStreamNumber();
        int b = config.getBufferSize();
        String kind = config.getKind();

        List<BaseInputStream> inputStreams = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        DataGenerator generator = new DataGenerator();
        try {
            String sharedFileName = generator.generate(config.getRunNumber(), "SharedFileInput");
            for (int i = 0; i < k; i++) {
                if (kind.equalsIgnoreCase("Basic")) {
                    inputStreams.add(new BasicInputStream());
                } else if (kind.equalsIgnoreCase("File")) {
                    inputStreams.add(new FInputStream());
                } else if (kind.equalsIgnoreCase("Memory")) {
                    inputStreams.add(new MemoryMappedInputStream(b));
                } else if (kind.equalsIgnoreCase("Buffer")) {
                    inputStreams.add(new DirectBufferInputStream(b));
                } else {
                    continue;
                }
                fileNames.add(sharedFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        InputBenchmark inputBenchmark = new InputBenchmark(config, inputStreams, fileNames, n);
        inputBenchmark.run();
    }

    private void runOutput(BenchmarkConfig config) {
        long n = config.getRunNumber();
        int k = config.getStreamNumber();
        int b = config.getBufferSize();
        String kind = config.getKind();

        List<BaseOutputStream> outputStreams = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (kind.equalsIgnoreCase("Basic")) {
                outputStreams.add(new BasicOutputStream());
            } else if (kind.equalsIgnoreCase("File")) {
                outputStreams.add(new FOutputStream());
            } else if (kind.equalsIgnoreCase("Memory")) {
                outputStreams.add(new MemoryMappedOutputStream(b));
            } else if (kind.equalsIgnoreCase("Buffer")) {
                outputStreams.add(new DirectBufferOutputStream(b));
            }
        }

        OutputBenchmark outputBenchmark = new OutputBenchmark(config, outputStreams, n);
        outputBenchmark.run();
    }

    private void runMergeSort(BenchmarkConfig config) {
        MergeSortBenchmark benchmark = new MergeSortBenchmark(config);
        benchmark.run();
    }
}
