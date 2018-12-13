package utils;

import java.io.*;

public class BasicInputStream implements BaseInputStream {
    private DataInputStream dataInputStream;
    private FileInputStream fileInputStream;

    @Override
    public void open(String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        DataInputStream dis = new DataInputStream(fis);
        this.dataInputStream = dis;
        this.fileInputStream = fis;
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
}
