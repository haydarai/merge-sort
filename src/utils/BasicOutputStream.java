package utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasicOutputStream implements BaseOutputStream {
    private DataOutputStream dataOutputStream;
    private FileOutputStream fileOutputStream;
    private String filePath;

    @Override
    public BaseOutputStream setBufferSize(long bufferSize) {
        return this;
    }

    @Override
    public void create(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        this.fileOutputStream = fos;
        this.dataOutputStream = dos;
        this.filePath = filePath;
    }

    @Override
    public BaseOutputStream open() throws IOException {
        File file = new File(this.filePath);

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        this.fileOutputStream = fos;
        this.dataOutputStream = dos;

        return this;
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
