package benchmark;

import com.google.gson.Gson;
import models.BenchmarkConfig;
import models.BenchmarkResult;
import utils.BaseInputStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class InputBenchmark {
    private BenchmarkConfig config;
    private List<Stream> streams;
    private long n;
    private String RESULT_DIR = "results/";
    private Gson gson;

    InputBenchmark(BenchmarkConfig config, List<BaseInputStream> streams, List<String> filenames, long n) {
        this.config = config;
        int length = Math.min(streams.size(), filenames.size());
        this.streams = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            this.streams.add(new Stream(streams.get(i), filenames.get(i)));
        }
        this.n = n;
        this.gson = new Gson();

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
            BenchmarkResult.Summary streamSummary = new BenchmarkResult.Summary();
            streamSummary.setStart(System.nanoTime());
            BenchmarkResult result = new BenchmarkResult();
            streams
                    .parallelStream()
                    .forEach(stream -> {
                        BenchmarkResult.Stream streamResult = new BenchmarkResult.Stream();
                        streamResult.setStart(System.nanoTime());
                        try {
                            BaseInputStream inputStream = stream.getStream();
                            String filename = stream.getFilename();

                            int pos = 0;
                            inputStream.open(filename);
                            for (int i = 0; i < n; i++) {
                                if (pos >= config.getBufferSize()) {
                                    inputStream = inputStream.getClass().newInstance().setBufferSize(config.getBufferSize()).open(filename).skip(pos);
                                    pos = 0;
                                }
                                inputStream.readNext();
                                pos += 1;
                            }

                            streamResult.setEnd(System.nanoTime());
                            streamResult.setElapsed(streamResult.getEnd() - streamResult.getStart());
                            result.addStream(streamResult);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    });
            streamSummary.setEnd(System.nanoTime());
            streamSummary.setElapsed(streamSummary.getEnd() - streamSummary.getStart());
            result.setSummary(streamSummary);
            writer.write(gson.toJson(result));
            writer.close();

            // Cleanup
            for (Stream stream : streams) {
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
