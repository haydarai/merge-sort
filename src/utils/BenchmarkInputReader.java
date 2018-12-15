package utils;

import models.BenchmarkConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BenchmarkInputReader {

    public static List<BenchmarkConfig> readCSV(String path) throws FileNotFoundException {
        List<List<String>> dataFrame = new ArrayList<>();
        Scanner rowScanner = new Scanner(new File(path));
        rowScanner.useDelimiter("\n");
        while (rowScanner.hasNext()) {
            Scanner colScanner = new Scanner(rowScanner.next());
            colScanner.useDelimiter(",");

            List<String> currentRow = new ArrayList<>();

            while (colScanner.hasNext()) {
                currentRow.add(colScanner.next());
            }
            dataFrame.add(currentRow);
        }
        rowScanner.close();

        List<BenchmarkConfig> benchmarkConfigs = new ArrayList<>();
        for (List<String> row : dataFrame) {
            try {
                String kind = row.get(0);
                String type = row.get(1);
                int n = Integer.parseInt(row.get(2));
                int k = Integer.parseInt(row.get(3));
                if (row.size() == 5) {
                    int b = Integer.parseInt(row.get(4));
                    benchmarkConfigs.add(new BenchmarkConfig(kind, type, n, k, b));
                } else {
                    benchmarkConfigs.add(new BenchmarkConfig(kind, type, n, k));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return benchmarkConfigs;
    }
}
