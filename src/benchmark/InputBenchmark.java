package benchmark;

import models.BenchmarkConfig;
import utils.BaseInputStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class InputBenchmark {
    private BenchmarkConfig config;
    private List<Stream> streams;
    private int n;
    private String RESULT_DIR = "results/";

    InputBenchmark(BenchmarkConfig config, List<BaseInputStream> streams, List<String> filenames, int n) {
        this.config = config;
        int length = Math.min(streams.size(), filenames.size());
        this.streams = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            this.streams.add(new Stream(streams.get(i), filenames.get(i)));
        }
        this.n = n;

        // Create RESULT_DIR if not exists
        File dataDir = new File(this.RESULT_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    void run() {
        String outputFileName = config.generateResultFilename();
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.RESULT_DIR, outputFileName),
                    true));
            final long startBenchmarkTime = System.nanoTime();
            streams
                    .parallelStream()
                    .forEach(stream -> {
                        try {
                            long startTime = System.nanoTime();
                            writer.write("Start read: " + String.valueOf(startTime) + ", ");

                            BaseInputStream inputStream = stream.getStream();
                            String filename = stream.getFilename();
                            inputStream.open(filename);
                            for (int i = 0; i < n; i++) {
                                inputStream.readNext();
                            }

                            long endTime = System.nanoTime();
                            long elapsedTime = endTime - startTime;
                            long elapsedTimeBenchmark = endTime - startBenchmarkTime;
                            writer.write("End read: " + String.valueOf(endTime) + ", ");
                            writer.write("Elapsed time (since start read): " +
                                    String.valueOf(elapsedTime) + ", ");
                            writer.write("Elapsed time (since start benchmark): " +
                                    String.valueOf(elapsedTimeBenchmark) + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            long endBenchmarkTime = System.nanoTime();
            long elapsedWholeTimeBenchmark = endBenchmarkTime - startBenchmarkTime;
            writer.write("Start benchmark: " + String.valueOf(startBenchmarkTime) + ", End benchmark: " +
                    String.valueOf(endBenchmarkTime) + ", Elapsed time (whole benchmark): " +
                    String.valueOf(elapsedWholeTimeBenchmark));
            writer.close();

            // Cleanup
            for (Stream stream: streams) {
                File file = new File(stream.getFilename());
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Stream {
        private BaseInputStream stream;
        private String filename;

        Stream(BaseInputStream stream, String filename) {
            this.stream = stream;
            this.filename = filename;
        }

        BaseInputStream getStream() {
            return stream;
        }

        String getFilename() {
            return filename;
        }
    }
}
