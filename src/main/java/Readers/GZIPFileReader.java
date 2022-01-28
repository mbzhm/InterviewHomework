package Readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class GZIPFileReader implements FormatReader {
    @Override
    public void read(File file, Map files) {
        try{
        InputStream is = Files.newInputStream(Paths.get(file.getPath()));
        GZIPInputStream gis = new GZIPInputStream(is);
        InputStreamReader isReader = new InputStreamReader(gis, StandardCharsets.UTF_8);

            BufferedReader br = new BufferedReader(isReader);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] splitted_line = line.split(",");
                int id = Integer.parseInt(splitted_line[0]);
                files.put(id, splitted_line[1]);
            }

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
