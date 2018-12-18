package benchmark;

import models.BenchmarkConfig;
import utils.BaseOutputStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

class OutputBenchmark {
    private BenchmarkConfig config;
    private List<BaseOutputStream> streams;
    private int n;
    private Random random;

    OutputBenchmark(BenchmarkConfig config, List<BaseOutputStream> streams, int n) {
        this.config = config;
        this.streams = streams;
        this.n = n;
        this.random = new Random();
    }

    void run() {
        String outputFileName = config.generateResultFilename();
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(new File("results", outputFileName),
                    true));
            final long startBenchmarkTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                streams
                        .parallelStream()
                        .forEach(stream -> {
                            String filename = String.valueOf(UUID.randomUUID()) + ".dat";
                            try {
                                long startTime = System.nanoTime();
                                writer.write("Start write: " + String.valueOf(startTime) + ", ");

                                stream.create(filename);
                                stream.write(random.nextInt());
                                stream.close();

                                long endTime = System.nanoTime();
                                long elapsedTime = endTime - startTime;
                                long elapsedTimeBenchmark = endTime - startBenchmarkTime;
                                writer.write("End write: " + String.valueOf(endTime) + ", ");
                                writer.write("Elapsed time (since start write): " +
                                        String.valueOf(elapsedTime) + ", ");
                                writer.write("Elapsed time (since start benchmark): " +
                                        String.valueOf(elapsedTimeBenchmark) + "\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                File file = new File(filename);
                                file.delete();
                            }
                        });
            }
            long endBenchmarkTime = System.nanoTime();
            long elapsedWholeTimeBenchmark = endBenchmarkTime - startBenchmarkTime;
            writer.write("Start benchmark: " + String.valueOf(startBenchmarkTime) + ", End benchmark: " +
                    String.valueOf(endBenchmarkTime) + ", Elapsed time (whole benchmark): " +
                    String.valueOf(elapsedWholeTimeBenchmark));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
