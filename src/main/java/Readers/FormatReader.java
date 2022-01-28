package Readers;

import java.io.File;
import java.util.Map;

public interface FormatReader {

    /**
     * The 'read' method reads from the file and puts the data to the map.
     *
     * @param file
     * @param files
     */
    void read(File file, Map files);
}
