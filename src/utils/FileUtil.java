package utils;

import java.io.*;

public class FileUtil {
    public static int read(String pathname) throws IOException {
        InputStream is = new FileInputStream(new File(pathname));
        DataInputStream ds = new DataInputStream(is);
        return ds.readInt();
    }

    public static void write(int content, String pathName) throws IOException {
        OutputStream os = new FileOutputStream(new File(pathName));
        DataOutputStream ds = new DataOutputStream(os);
        ds.writeInt(content);
    }

    public static int fread(String pathname) throws IOException {
        InputStream is = new FileInputStream(new File(pathname));
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInput ds = new DataInputStream(bis);
        return ds.readInt();
    }

    public static void fwrite(int content, String pathname) throws IOException {
        OutputStream os = new FileOutputStream(new File(pathname));
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutput ds = new DataOutputStream(bos);
        ds.writeInt(content);
    }
}
