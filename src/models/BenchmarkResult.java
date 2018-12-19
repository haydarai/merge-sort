package models;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkResult {
    private List<Stream> streams;
    private Summary summary;

    public BenchmarkResult() {
        this.streams = new ArrayList<>();
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public void addStream(Stream stream) {
        this.streams.add(stream);
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public static class Stream {
        private long start;
        private long end;

        public Stream() {
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public long getElapsed() {
            return elapsed;
        }

        public void setElapsed(long elapsed) {
            this.elapsed = elapsed;
        }

        private long elapsed;
    }

    public static class Summary {
        private long start;
        private long end;
        private long elapsed;

        public Summary() {
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public long getElapsed() {
            return elapsed;
        }

        public void setElapsed(long elapsed) {
            this.elapsed = elapsed;
        }
    }
}
