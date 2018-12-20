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

    public MemoryMappedOutputStream(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public MemoryMappedOutputStream() {
    }

    @Override
    public BaseOutputStream setBufferSize(long bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    @Override
    public void create(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, this.bufferSize);

        this.mappedByteBuffer = mappedByteBuffer;
        this.fileChannel = fileChannel;
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
