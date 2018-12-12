package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedInputStream implements BaseInputStream {
    FileChannel fileChannel;
    MappedByteBuffer mappedByteBuffer;
    int bufferSize;
    public MemoryMappedInputStream(int bufferSize) {
        this.bufferSize = bufferSize;
    }
    @Override
    public void open(String filePath) throws IOException {
        File file = new File(filePath);
        FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,0,bufferSize);
        this.fileChannel = fileChannel;
        this.mappedByteBuffer = mappedByteBuffer;
    }

    @Override
    public int readNext() throws IOException {
        return this.mappedByteBuffer.getInt();
    }

    @Override
    public boolean endOfStream() throws IOException {
        return this.mappedByteBuffer.remaining()<=8;
    }
}