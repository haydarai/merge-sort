package sort;

import utils.MemoryMappedOutputStream;

import java.io.File;
import java.util.Random;

public class DataGenerator {
    private String DATAIDR = "data";
    private MemoryMappedOutputStream outputStream;

//    public DataGenerator(BaseOutputStream outputStream) {
//        // Create data directory if not exists
//        File directory = new File(this.DATAIDR);
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//        this.outputStream = outputStream;
//    }

    public DataGenerator() {
        // Create data directory if not exists
        File directory = new File(this.DATAIDR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Use maximum possible size bounded by the map() of the fileChannel
        this.outputStream = new MemoryMappedOutputStream(Integer.MAX_VALUE-1);
    }

    public String generate(long size, String name) throws Exception {
        String filepath = this.DATAIDR.concat("/").concat(name).concat(".dat");
        this.outputStream.create(filepath);
        Random random = new Random();
        for (long i = 0; i < size; i++) {
            this.outputStream.write(random.nextInt());
        }
        this.outputStream.close();
        return filepath;
    }
}
