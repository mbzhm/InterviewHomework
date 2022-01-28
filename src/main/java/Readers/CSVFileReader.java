package Readers;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class CSVFileReader implements Readers.FormatReader {

    private static final String COMMA_DELIMITER = ",";

    @Override
    public void read(File file, Map files) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                getRecordFromLine(scanner.nextLine(), files);
            }
            }catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private Map getRecordFromLine(String line, Map files) {
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                int id = rowScanner.nextInt();
                String format = rowScanner.next();
                files.put(id, format);
            }
        }
        return files;
    }
}
