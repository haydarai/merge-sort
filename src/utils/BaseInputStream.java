package utils;

import java.io.IOException;

public interface BaseInputStream {
    void open(String filePath) throws IOException;
    int readNext() throws IOException;
    boolean endOfStream() throws IOException;
}
