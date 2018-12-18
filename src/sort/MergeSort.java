package sort;

import utils.BaseInputStream;
import utils.BaseOutputStream;

import java.io.File;
import java.util.*;

/*
    Both availableMemory and fileLength is measured in the number of 32-bit integer.
 */
public class MergeSort {
    BaseInputStream inputStream;
    BaseOutputStream outputStream;

    private String TMPDIR = "data/tmp/";
    private String DATADIR = "data/";
    private ArrayList<Thread> worksers;

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

        // make sure that tmp directory is empty
        File[] listOfFiles = tmpDir.listFiles();
        for (File file:listOfFiles) {
            file.delete();
        }

    }

    public void sort(String filePath, int availableMemory) throws Exception {
        File unsortedFile = new File(filePath);
        // WARNING: This assume that each int consists of 4 bytes (hence, it's a 32-bit integers
        long fileLength = unsortedFile.length() / 4;

        // open stream
        inputStream.open(filePath);
        int position = 0;

        ArrayList<Thread> workers = new ArrayList<>();

        while (position < fileLength) {
            int bufferSize;
            if((fileLength - position) < availableMemory){
                bufferSize = (int) (fileLength - position);
            } else {
                bufferSize = availableMemory;
            }
            bufferSize *= 4;

            // instantiate new inputStream and skip to the position
            BaseInputStream nextInStream = this.inputStream.getClass().newInstance().setBufferSize(bufferSize).open(filePath).skip(position);

            // instantiate outputStream to write intermediate files
            BaseOutputStream nextOutStream = this.outputStream.getClass().newInstance().setBufferSize(bufferSize);

            // instantiate sortWorker to sort particular area of the input
            Thread nextWorker = new Thread(new SortWorker(nextInStream, nextOutStream, availableMemory, this.TMPDIR));
            nextWorker.start();

            // keep all threads in an arrayList so that all of them can be joined later
            workers.add(nextWorker);

            position += availableMemory;
        }
        this.worksers = workers;
    }

    public void merge(int numberOfStreamToMerge) throws Exception {
        // wait for all workers to finish
        for(Thread worker:this.worksers){
            worker.join();
        }

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
            int cFileLength = (int) listOfFiles[i].length();
            BaseInputStream inputStreamToAdd = this.inputStream.getClass().newInstance().setBufferSize(cFileLength);
            inputStreamToAdd.open(listOfFiles[i].getPath());

            int valToAdd = inputStreamToAdd.readNext();
            pq.add(new AbstractMap.SimpleEntry<>(inputStreamToAdd, valToAdd));
        }

        // while pq is not empty
        while (!pq.isEmpty()) {
            // write pq.remove() to outputstream
            Map.Entry<BaseInputStream, Integer> removedEntry = pq.peek();
            this.outputStream.write(pq.remove().getValue());

            // insert removed pq stream to pq
            if (!removedEntry.getKey().endOfStream()) {
                int entryVal = removedEntry.getKey().readNext();
                pq.add(new AbstractMap.SimpleEntry<>(removedEntry.getKey(), entryVal));
            }
        }
        // close the outstream
        this.outputStream.close();
        // delete tempFiles
        for (File tmpFile : listOfFiles) {
            tmpFile.delete();
        }

    }
}