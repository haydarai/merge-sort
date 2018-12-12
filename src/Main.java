import utils.BasicInputStream;
import utils.BasicOutputStream;
import utils.FileUtil;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        try {
//            FileUtil.write(3, "./input.data");
//            int input = FileUtil.read("./input.data");
//            System.out.println(input);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*
            Example of using BasicInputStream and BasicOutputStream. Data will be written in bytes, so the BasicInputStream
            can only read correctly what is written by basicOutputStream.
         */
        BasicInputStream basicInputStream = new BasicInputStream();
        BasicOutputStream basicOutputStream = new BasicOutputStream();
        try {
            // Example of writing data to file
            basicOutputStream.create("tmp.dat");
            basicOutputStream.write(42);
            basicOutputStream.write(149);
            basicOutputStream.close();

            // Example of reading previously written data
            basicInputStream.open("tmp.dat");
            while (!basicInputStream.endOfStream()){
                System.out.println(basicInputStream.readNext());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cleanup
            File file = new File("tmp.dat");
            file.delete();
        }

    }
}
