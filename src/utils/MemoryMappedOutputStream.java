package utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedOutputStream implements BaseOutputStream {
    MappedByteBuffer mappedByteBuffer;
    FileChannel fileChannel;
    int bufferSize;
    public MemoryMappedOutputStream(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void create(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

        // Read 4 bytes integer
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,this.bufferSize);

        this.mappedByteBuffer = mappedByteBuffer;
        this.fileChannel = fileChannel;
    }

    @Override
    public void write(int value) throws IOException {
        this.mappedByteBuffer.putInt(value);
    }

    @Override
    public void close() throws IOException {
        this.fileChannel.close();
    }
}
