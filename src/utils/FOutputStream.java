package utils;

import java.io.*;

public class FOutputStream implements BaseOutputStream {
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private DataOutputStream dataOutputStream;
    private String filePath;

    @Override
    public BaseOutputStream setBufferSize(long bufferSize) {
        return this;
    }

    @Override
    public void create(String filePath) throws IOException {
        this.filePath = filePath;
        File file = new File(this.filePath);
        file.createNewFile();

        this.fileOutputStream = new FileOutputStream(file);
        this.bufferedOutputStream = new BufferedOutputStream(this.fileOutputStream);
        this.dataOutputStream = new DataOutputStream(this.bufferedOutputStream);
    }

    @Override
    public BaseOutputStream open() throws IOException {
        File file = new File(this.filePath);

        this.fileOutputStream = new FileOutputStream(file);
        this.bufferedOutputStream = new BufferedOutputStream(this.fileOutputStream);
        this.dataOutputStream = new DataOutputStream(this.bufferedOutputStream);

        return this;
    }

    @Override
    public void write(int value) throws IOException {
        this.dataOutputStream.writeInt(value);
    }

    public FOutputStream() {
    }

    @Override
    public void close() throws IOException {
        this.dataOutputStream.close();
        this.bufferedOutputStream.close();
        this.fileOutputStream.close();
    }
}
