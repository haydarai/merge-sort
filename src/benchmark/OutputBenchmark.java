package benchmark;

import com.google.gson.Gson;
import models.BenchmarkConfig;
import models.BenchmarkResult;
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
            BenchmarkResult.Summary streamSummary = new BenchmarkResult.Summary();
            streamSummary.setStart(System.nanoTime());
            BenchmarkResult result = new BenchmarkResult();
            streams
                    .parallelStream()
                    .forEach(stream -> {
                        BenchmarkResult.Stream streamResult = new BenchmarkResult.Stream();
                        streamResult.setStart(System.nanoTime());
                        String filename = String.valueOf(UUID.randomUUID()) + ".dat";
                        try {
                            stream.create(filename);
                            for (int i = 0; i < n; i++) {
                                stream.write(random.nextInt());
                            }
                            stream.close();

                            streamResult.setEnd(System.nanoTime());
                            streamResult.setElapsed(streamResult.getEnd() - streamResult.getStart());
                            result.addStream(streamResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            File file = new File(filename);
                            file.delete();
                        }
                    });
            streamSummary.setEnd(System.nanoTime());
            streamSummary.setElapsed(streamSummary.getEnd() - streamSummary.getStart());
            Gson gson = new Gson();
            writer.write(gson.toJson(result));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
