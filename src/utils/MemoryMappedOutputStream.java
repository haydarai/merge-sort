package utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedOutputStream implements BaseOutputStream {
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;
    private long bufferSize;
    private String filePath;

    public MemoryMappedOutputStream(long bufferSize) {
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
        this.fileChannel = new RandomAccessFile(file, "rw").getChannel();;

        this.mappedByteBuffer =  fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, this.bufferSize);;
    }

    @Override
    public BaseOutputStream open() throws IOException {
        File file = new File(this.filePath);
        this.fileChannel = new RandomAccessFile(file, "rw").getChannel();

        this.mappedByteBuffer =  fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, this.bufferSize);;

        return this;
    }

    @Override
    public void write(int value) {
        this.mappedByteBuffer.putInt(value);
    }

    @Override
    public void close() throws IOException {
        this.fileChannel.close();
    }
}
