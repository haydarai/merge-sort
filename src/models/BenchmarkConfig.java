package models;

import java.time.Instant;

public class BenchmarkConfig {
    private String type;
    private String kind;
    private int n;
    private int k;
    private int b;
    private String resultFileName;

    public BenchmarkConfig(String type, String kind, int runNumber, int streamNumber) {
        this.type = type;
        this.kind = kind;
        this.n = runNumber;
        this.k = streamNumber;
        this.b = 0;
    }

    public BenchmarkConfig(String type, String kind, int runNumber, int streamNumber, int bufferSize) {
        this.type = type;
        this.kind = kind;
        this.n = runNumber;
        this.k = streamNumber;
        this.b = bufferSize;
    }

    public String generateResultFilename() {
        resultFileName = String.format("%d_%s_%s_%d_%d_%d.dat", Instant.now().getEpochSecond(), type, kind, n, k, b);
        return resultFileName;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public int getRunNumber() {
        return n;
    }

    public int getStreamNumber() {
        return k;
    }

    public int getBufferSize() {
        return b;
    }

    public String getResultFileName() {
        return resultFileName;
    }
}
