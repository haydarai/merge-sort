package utils;

import java.io.IOException;

public interface BaseInputStream {
    BaseInputStream setBufferSize(int bufferSize);

    BaseInputStream open(String filePath) throws IOException;

    int readNext() throws IOException;

    boolean endOfStream() throws IOException;

    BaseInputStream skip(int n) throws IOException;
}
