package utils;

import java.io.File;

public interface OutputStreamInterace {
    File create();
    void write();
    void close();
}
