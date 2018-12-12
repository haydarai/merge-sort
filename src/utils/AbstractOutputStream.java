package utils;

import java.io.File;

abstract public class AbstractOutputStream {
    abstract public File create();
    abstract public void write();
    abstract public void close();
}
