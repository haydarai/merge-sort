package utils;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface BaseInputStream {
    void open(String filePath) throws FileNotFoundException;
    int readNext() throws IOException;
    boolean endOfStream() throws IOException;
}
