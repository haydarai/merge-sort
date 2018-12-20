package models;

import java.time.Instant;

public class BenchmarkConfig {
    private String type;
    private String kind;
    private int n;
    private int k;
    private int b;
    private int m;
    private int d;
    private String resultFileName;

    public BenchmarkConfig(String kind, String type, int runNumber, int streamNumber) {
        this.kind = kind;
        this.type = type;
        this.n = runNumber;
        this.k = streamNumber;
        this.b = 0;
    }

    public BenchmarkConfig(String kind, String type, int runNumber, int streamNumber, int bufferSize) {
        this.kind = kind;
        this.type = type;
        this.n = runNumber;
        this.k = streamNumber;
        this.b = bufferSize;
    }

    public BenchmarkConfig(String kind, int runNumber, int memoryAvailable, int wayMerge) {
        this.kind = kind;
        this.n = runNumber;
        this.m = memoryAvailable;
        this.d = wayMerge;
    }

    public String generateResultFilename() {
        if (type != null) {
            resultFileName = String.format("%d_%s_%s_%d_%d_%d.json", Instant.now().getEpochSecond(), type, kind, n, k, b);
        } else {
            resultFileName = String.format("%d_%s_%d_%d_%d.json", Instant.now().getEpochSecond(), kind, n, m, d);
        }
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

    public int getAvailableMemory() {
        return m;
    }

    public void setAvailableMemory(int availableMemory) {
        this.m = availableMemory;
    }

    public int getDWayMerge() {
        return d;
    }

    public void setDWayMerge(int dWayMerge) {
        this.d = dWayMerge;
    }
}
