import Readers.CSVFileReader;
import Readers.FormatReader;
import Readers.GZIPFileReader;
import Readers.XLSXFileReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        //multithreading initialization
        ExecutorService executor = Executors.newFixedThreadPool(10);


        String path = "C:\\Users\\Марал\\Downloads\\BeelineHomework\\src\\main\\resources\\";//path to the file

        //files initialization
        File file1 = new File(path + "File1.xlsx");
        File file2 = new File(path + "File2.csv");
        File file3 = new File(path + "File3.csv.gz");

        //initialization of readers
        FormatReader reader1 = new XLSXFileReader();
        FormatReader reader2 = new CSVFileReader();
        FormatReader reader3 = new GZIPFileReader();

        //map that will contain the data from the files
        Map<Integer, String> files = new ConcurrentHashMap<>();

        //tasks for multithreading
        /*reader1.read(file1, files);
        reader2.read(file2, files);
        reader3.read(file3, files);*/

        executor.submit(new ThreadReading(reader1, file1, files));
        executor.submit(new ThreadReading(reader2, file2, files));
        executor.submit(new ThreadReading(reader3, file3, files));

        //multithreading shutdown
        executor.shutdown();

        //this shutdown method was recommended from the Internet
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //initialization of the database application
        DatabaseApp db = new DatabaseApp();

        //inserting the data from the map to the database
        for(Map.Entry<Integer, String> entry : files.entrySet()){
            db.insert(entry.getKey(), entry.getValue());
        }

        //exporting the data from the database to the CSV file
        String result = db.exportToCSV("test2");
        System.out.println(result);

    }
}
