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
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runInput(BenchmarkConfig config) {
        int n = config.getRunNumber();
        int k = config.getStreamNumber();
        int b = config.getBufferSize();
        String kind = config.getKind();

        List<BaseInputStream> inputStreams = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        DataGenerator generator;
        if (kind.equalsIgnoreCase("Basic")) {
            generator = new DataGenerator(new BasicOutputStream());
        } else if (kind.equalsIgnoreCase("File")) {
            generator = new DataGenerator(new FOutputStream());
        } else if (kind.equalsIgnoreCase("Memory")) {
            generator = new DataGenerator(new MemoryMappedOutputStream(b));
        } else if (kind.equalsIgnoreCase("Buffer")) {
            generator = new DataGenerator(new DirectBufferOutputStream(b));
        } else {
            generator = new DataGenerator();
        }

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
            try {
                fileNames.add(generator.generate(0, String.valueOf(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputBenchmark inputBenchmark = new InputBenchmark(config, inputStreams, fileNames, n);
        inputBenchmark.run();
    }

    private void runOutput(BenchmarkConfig config) {
        int n = config.getRunNumber();
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
}
