package utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedInputStream implements BaseInputStream {
    private FileChannel fileChannel;
    private MappedByteBuffer mappedByteBuffer;
    private int bufferSize;

    public MemoryMappedInputStream(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public MemoryMappedInputStream() {
    }

    @Override
    public BaseInputStream setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    @Override
    public MemoryMappedInputStream open(String filePath) throws IOException {
        File file = new File(filePath);
        FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, bufferSize);
        this.fileChannel = fileChannel;
        this.mappedByteBuffer = mappedByteBuffer;

        return this;
    }

    @Override
    public int readNext() {
        return this.mappedByteBuffer.getInt();
    }

    @Override
    public boolean endOfStream() {
        int remaining = this.mappedByteBuffer.remaining();
        return remaining <= 0;
    }

    @Override
    public MemoryMappedInputStream skip(int n) throws IOException {
        this.mappedByteBuffer=this.fileChannel.map(FileChannel.MapMode.READ_ONLY, 4*n, bufferSize);
        return this;
    }
}
