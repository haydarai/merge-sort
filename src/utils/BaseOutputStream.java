package utils;

import java.io.IOException;

public interface BaseOutputStream {
    BaseOutputStream setBufferSize(long bufferSize);
    void create(String filePath) throws IOException;

    void write(int value) throws IOException;

    void close() throws Exception;
}
