import utils.*;

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
        System.out.println("Using BasicInputStream and BasicOutputStream");
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


        /*
            Example of using FInputStream and FOutputStream. Data will be written in bytes, so the BasicInputStream
            can only read correctly what is written by basicOutputStream.
         */
        FInputStream fInputStream = new FInputStream();
        FOutputStream fOutputStream = new FOutputStream();
        System.out.println("Using FInputStream and FOutputStream");
        try {
            // Example of writing data to file
            fOutputStream.create("tmp.dat");
            fOutputStream.write(42);
            fOutputStream.write(149);
            fOutputStream.close();

            // Example of reading previously written data
            fInputStream.open("tmp.dat");
            while (!fInputStream.endOfStream()){
                System.out.println(fInputStream.readNext());
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

        /*
            Example of using MemoryMappedInputStream and MemoryMappedOutputStream. We set bufferSize to 16 in order to write
            two integers to avoid BufferUnderflowException

            docs java.nio.ByteBuffer:
                Throws: BufferUnderflowException - If there are fewer than eight bytes remaining in this buffer
         */
        MemoryMappedInputStream memoryMappedInputStream = new MemoryMappedInputStream(16);
        MemoryMappedOutputStream memoryMappedOutputStream = new MemoryMappedOutputStream(16);
        System.out.println("Using MemoryMapped");
        try {
            // Example of writing data to file
            memoryMappedOutputStream.create("tmp.dat");
            memoryMappedOutputStream.write(42);
            memoryMappedOutputStream.write(149);
            memoryMappedOutputStream.close();

            // Example of reading previously written data
            memoryMappedInputStream.open("tmp.dat");
            while (!memoryMappedInputStream.endOfStream()){
                System.out.println(memoryMappedInputStream.readNext());
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
