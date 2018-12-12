package utils;

import java.io.*;

public class BasicInputStream implements BaseInputStream {
    DataInputStream dataInputStream;
    @Override
    public void open(String filePath) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(filePath));
        this.dataInputStream = new DataInputStream(is);
    }

    @Override
    public int readNext() throws IOException {
        return this.dataInputStream.readInt();
    }

    @Override
    public boolean endOfStream() throws IOException {
        boolean isEndOfStream =  this.dataInputStream.available()==0;
        if(isEndOfStream){
            this.dataInputStream.close();
        }
        return isEndOfStream;
    }
}
