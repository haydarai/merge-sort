package utils;

import java.io.DataInputStream;

public interface InputStreamInterface {
    DataInputStream open();
    int readNext();
    boolean end_of_stream();
}
