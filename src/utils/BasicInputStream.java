package utils;

import java.io.*;

public class BasicInputStream implements BaseInputStream {
    private DataInputStream dataInputStream;
    private FileInputStream fileInputStream;

    @Override
    public BaseInputStream setBufferSize(int bufferSize) {
        return this;
    }

    public BasicInputStream() {
    }

    @Override
    public BasicInputStream open(String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        this.dataInputStream = new DataInputStream(fis);
        this.fileInputStream = fis;

        return this;
    }

    @Override
    public int readNext() throws IOException {
        return this.dataInputStream.readInt();
    }

    @Override
    public boolean endOfStream() throws IOException {
        boolean isEndOfStream = this.dataInputStream.available() == 0;
        if (isEndOfStream) {
            this.dataInputStream.close();
            this.fileInputStream.close();
        }
        return isEndOfStream;
    }

    @Override
    public BasicInputStream skip(int n) throws IOException {
        this.dataInputStream.skipBytes(4*n);
        return this;
    }
}
