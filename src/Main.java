import sort.MergeSort;
import utils.*;

public class Main {

    public static void main(String[] args) {

        // 36 is the 4 times total length of the input file
        MemoryMappedInputStream inputStream = new MemoryMappedInputStream(36);
        MemoryMappedOutputStream outputStream = new MemoryMappedOutputStream(36);

        MergeSort mergeSort = new MergeSort(inputStream, outputStream);

        try {
            // Creating sample data to sort, assume that data/ directory is created
            outputStream.create("data/input.dat");
            outputStream.write(42);
            outputStream.write(54);
            outputStream.write(-232);
            outputStream.write(59);
            outputStream.write(81);
            outputStream.write(77);
            outputStream.write(149);
            outputStream.write(-21);
            outputStream.write(642);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mergeSort.sort("data/input.dat", 6);
            mergeSort.merge(2);

            inputStream.open("data/result.dat");
            while (!inputStream.endOfStream()) {
                System.out.println(inputStream.readNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
