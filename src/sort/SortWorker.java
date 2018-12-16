package sort;

import javafx.print.Collation;
import utils.BaseInputStream;
import utils.BaseOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static java.util.Arrays.sort;

class SortWorker implements Runnable{
    private BaseInputStream inputStream;
    private BaseOutputStream outputStream;
    private int availableMemory;
    private String tmpDir;

    public SortWorker(BaseInputStream inputStream, BaseOutputStream outputStream, int availableMemory, String tmpDir) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.availableMemory = availableMemory;
        this.tmpDir = tmpDir;
    }

    @Override
    public void run() {
        try {
            // read content of file as int
            ArrayList<Integer> bucket = new ArrayList<>();
            for (int i = 0; i < this.availableMemory && !this.inputStream.endOfStream(); i++) {
                int toBeAddedToBucket = this.inputStream.readNext();
                bucket.add(toBeAddedToBucket);
            }

            // sort the array
            Collections.sort(bucket);

            outputStream.create(this.tmpDir.concat(String.valueOf(UUID.randomUUID())).concat(".dat"));
            // write the output to intermediate file
            for (int i = 0; i < this.availableMemory & i < bucket.size(); i++) {
                outputStream.write(bucket.get(i));
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
