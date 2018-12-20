package sort;

import utils.BaseOutputStream;
import utils.MemoryMappedOutputStream;

import java.io.File;
import java.util.Random;

public class DataGenerator {
    private String DATAIDR = "data";
    private BaseOutputStream baseOutputStream;

    public DataGenerator(BaseOutputStream baseOutputStream) {
        // Create data directory if not exists
        File directory = new File(this.DATAIDR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.baseOutputStream = baseOutputStream;
    }

    public DataGenerator(long maximumMemory) {
        // Create data directory if not exists
        File directory = new File(this.DATAIDR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.baseOutputStream = new MemoryMappedOutputStream(maximumMemory);
    }

    public String generate(long size, String name) throws Exception {
        String filepath = this.DATAIDR.concat("/").concat(name).concat(".dat");
        this.baseOutputStream.create(filepath);
        Random random = new Random();
        for (long i = 0; i < size; i++) {
            this.baseOutputStream.write(random.nextInt());
        }
        this.baseOutputStream.close();
        return filepath;
    }
}
