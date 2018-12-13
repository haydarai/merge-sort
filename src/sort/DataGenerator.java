package sort;

import utils.BaseOutputStream;

import java.io.File;
import java.util.Random;

public class DataGenerator {
    private String DATAIDR = "data";
    private BaseOutputStream baseOutputStream;

    public DataGenerator(BaseOutputStream baseOutputStream) {
        // create data directory if not exists
        File directory = new File(this.DATAIDR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.baseOutputStream = baseOutputStream;
    }

    public void genereate(int size, String name) throws Exception {
        this.baseOutputStream.create(this.DATAIDR.concat("/").concat(name).concat(".dat"));
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            this.baseOutputStream.write(random.nextInt());
        }
        this.baseOutputStream.close();
    }
}
