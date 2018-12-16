package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBufferInputStream implements BaseInputStream {
    private int bufferSize;
    private ByteBuffer byteBuffer;
    private FileChannel fileChannel;

    public DirectBufferInputStream(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public DirectBufferInputStream open(String filePath) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.bufferSize);

        fileChannel.read(byteBuffer);

        // Use flip() to set the position of this byteBuffer to 0
        byteBuffer.flip();

        this.byteBuffer = byteBuffer;
        this.fileChannel = fileChannel;

        return this;
    }

    @Override
    public int readNext() {
        int value = this.byteBuffer.getInt();
        return value;
    }

    @Override
    public boolean endOfStream() throws IOException {
        // Stream should end when the position of byteBuffer reach the fileChannel's size
        boolean isEndOfStream = this.fileChannel.size() == byteBuffer.position();
        return isEndOfStream;
    }

    @Override
    public DirectBufferInputStream skip(int n) throws IOException {
        this.byteBuffer.position(this.byteBuffer.position()+4*n);
        return this;
    }
}
