package utils;

import java.io.DataInputStream;

abstract public class AbstractInputStream {
    abstract public DataInputStream open();
    abstract public int readNext();
    abstract public boolean end_of_stream();
}
