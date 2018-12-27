package utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DirectBufferOutputStream implements BaseOutputStream {
    private long bufferSize;
    private RandomAccessFile randomAccessFile;
    private ByteBuffer byteBuffer;
    private String filePath;

    public DirectBufferOutputStream(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public BaseOutputStream setBufferSize(long bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    @Override
    public void create(String filePath) throws IOException {
        this.filePath = filePath;
        File file = new File(this.filePath);
        file.createNewFile();
        this.randomAccessFile = new RandomAccessFile(file, "rw");

        this.byteBuffer = ByteBuffer.allocateDirect((int) this.bufferSize);
        this.byteBuffer.clear();
    }

    @Override
    public BaseOutputStream open() throws IOException {
        File file = new File(this.filePath);
        this.randomAccessFile = new RandomAccessFile(file, "rw");

        this.byteBuffer = ByteBuffer.allocateDirect((int) this.bufferSize);
        this.byteBuffer.clear();

        return this;
    }

    @Override
    public void write(int value) {
        this.byteBuffer.putInt(value);
    }

    @Override
    public void close() throws Exception {
        // Write to data to file after all input is put to the byteBuffer
        this.byteBuffer.flip();
        byte[] data = new byte[this.byteBuffer.limit()];
        this.byteBuffer.get(data);
        this.randomAccessFile.write(data);
        this.byteBuffer.clear();

        this.randomAccessFile.close();

    }
}
