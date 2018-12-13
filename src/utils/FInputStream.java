package utils;

import java.io.*;

public class FInputStream implements BaseInputStream {
    private FileInputStream fileInputStream;
    private DataInputStream dataInputStream;
    private BufferedInputStream bufferedInputStream;

    @Override
    public void open(String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File(filePath));

        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);

        this.fileInputStream = fis;
        this.bufferedInputStream = bis;
        this.dataInputStream = dis;
    }

    @Override
    public int readNext() throws IOException {
        return this.dataInputStream.readInt();
    }

    @Override
    public boolean endOfStream() throws IOException {
        boolean isEndOfStream = this.dataInputStream.available() == 0;
        if (isEndOfStream) {
            this.fileInputStream.close();
            this.bufferedInputStream.close();
            this.dataInputStream.close();
        }
        return isEndOfStream;
    }
}
