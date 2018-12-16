import sort.MergeSort;
import utils.*;
import benchmark.BenchmarkRunner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        BasicInputStream basicInputStream = new BasicInputStream();
        BasicOutputStream basicOutputStream = new BasicOutputStream();

        MergeSort mergeSort = new MergeSort(basicInputStream, basicOutputStream);

        try {
            // Creating sample data to sort, assume that data/ directory is created
            basicOutputStream.create("data/input.dat");
            basicOutputStream.write(42);

            basicOutputStream.write(54);
            basicOutputStream.write(642);
            basicOutputStream.write(-232);
            basicOutputStream.write(59);
            basicOutputStream.write(80);
            basicOutputStream.write(77);
            basicOutputStream.write(149);
            basicOutputStream.write(-21);
            basicOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mergeSort.sort("data/input.dat", 2);
            mergeSort.merge(2);

            basicInputStream.open("data/result.dat");
            while (!basicInputStream.endOfStream()) {
                System.out.println(basicInputStream.readNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BenchmarkRunner runner = new BenchmarkRunner("input.csv");
        runner.run();
    }
}
