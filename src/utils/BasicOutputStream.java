package utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasicOutputStream implements BaseOutputStream {
    DataOutputStream dataOutputStream;
    FileOutputStream fileOutputStream;
    @Override
    public void create(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        this.fileOutputStream = fos;
        this.dataOutputStream = dos;
    }

    @Override
    public void write(int value) throws IOException {
        this.dataOutputStream.writeInt(value);
    }

    @Override
    public void close() throws IOException {
        this.dataOutputStream.close();
        this.fileOutputStream.close();
    }
}
