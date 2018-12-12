package utils;

import java.io.*;

public class FOutputStream implements BaseOutputStream {
    FileOutputStream fileOutputStream;
    BufferedOutputStream bufferedOutputStream;
    DataOutputStream dataOutputStream;

    @Override
    public void create(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);

        this.fileOutputStream = fos;
        this.bufferedOutputStream = bos;
        this.dataOutputStream = dos;


    }

    @Override
    public void write(int value) throws IOException {
        this.dataOutputStream.writeInt(value);
    }

    @Override
    public void close() throws IOException {
        this.dataOutputStream.close();
        this.bufferedOutputStream.close();
        this.fileOutputStream.close();
    }
}
