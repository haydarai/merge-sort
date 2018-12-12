package utils;

import java.io.File;
import java.io.IOException;

public interface BaseOutputStream {
    void create(String filePath) throws IOException;
    void write(int value) throws IOException;
    void close() throws IOException;
}
