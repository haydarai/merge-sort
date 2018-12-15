package sort;

import utils.BaseInputStream;
import utils.BaseOutputStream;

import java.io.File;
import java.util.*;

import static java.util.Arrays.sort;

/*
    Both availableMemory and fileLength is measured in the number of 32-bit integer.
 */
public class MergeSort {
    BaseInputStream inputStream;
    BaseOutputStream outputStream;

    private String TMPDIR = "data/tmp/";
    private String DATADIR = "data/";

    public MergeSort(BaseInputStream inputStream, BaseOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;


        File dataDir = new File(this.DATADIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        File tmpDir = new File(this.TMPDIR);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
    }

    public void doSort(String filePath, int availableMemory) throws Exception {
        File inputFile = new File(filePath);

        // WARNING: This assume that each int consists of 4 bytes (hence, it's a 32-bit integers
        long fileLength = inputFile.length() / 4;

        // open stream
        inputStream.open(filePath);
        int position = 0;

        // TODO: Parallelize the internal memory sorting process to as many as filePath/availableMemory
        while (position < fileLength) {
            // read content of file as int
            ArrayList<Integer> bucket = new ArrayList<>();
            for (int i = 0; i < availableMemory && !inputStream.endOfStream(); i++) {
                int toBeAddedToBucket = inputStream.readNext();
                bucket.add(toBeAddedToBucket);
            }

            // sort the array
            sort(bucket.toArray());

            outputStream.create(this.TMPDIR.concat(String.valueOf(java.util.UUID.randomUUID())).concat(".dat"));
            // write the output to intermediate file
            for (int i = 0; i < availableMemory & i < bucket.size(); i++) {
                outputStream.write(bucket.get(i));
            }
            position += availableMemory;
        }
        outputStream.close();
    }

    public void doMerge(int numberOfStreamToMerge) throws Exception {
        // list all intermediate files
        File dir = new File(this.TMPDIR);
        File[] listOfFiles = dir.listFiles();

        // open an outputstream
        this.outputStream.create(this.DATADIR.concat("result.dat"));

        // create a priority queue with size of numberOfStreamToMerge
        // pack the stream with it's readNext() value
        PriorityQueue<Map.Entry<BaseInputStream, Integer>> pq = new PriorityQueue<>(numberOfStreamToMerge,
                Comparator.comparingInt(Map.Entry::getValue));

        // for all streams opened, add stream.readNext() to pq
        assert listOfFiles != null;
        for (int i = 0; i < listOfFiles.length; i++) {
            BaseInputStream inputStreamToAdd = inputStream.getClass().newInstance();
            inputStreamToAdd.open(listOfFiles[i].getPath());

            int valToAdd = inputStreamToAdd.readNext();
            pq.add(new AbstractMap.SimpleEntry<>(inputStreamToAdd, valToAdd));
        }

        // while pq is not empty
        while (!pq.isEmpty()) {
            // write pq.remove() to outputstream
            Map.Entry<BaseInputStream, Integer> removedEntry = pq.peek();
            outputStream.write(pq.remove().getValue());

            // insert removed pq stream to pq
            if (!removedEntry.getKey().endOfStream()) {
                pq.add(new AbstractMap.SimpleEntry<>(removedEntry.getKey(), removedEntry.getKey().readNext()));
            }
        }
        // close the outstream
        outputStream.close();
        // delete tempFiles
        for (File tmpFile : listOfFiles) {
            tmpFile.delete();
        }

    }
}