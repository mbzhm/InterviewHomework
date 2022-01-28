import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseApp {

    private Connection connect(){
        final String url = "jdbc:sqlite:/C:\\SQLite\\sqlite-tools-win32-x86-3370200\\files.db";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * Inserts the key-value pair (id, format) to the database.
     *
     * @param id
     * @param format
     */
    public void insert(int id, String format){
        String sql = "INSERT INTO files(id, format) VALUES(?,?)";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, format);
            pstmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects all the records from the database ordered by id, creates new CSV file with the
     * given name, and writes all the sorted records to this file.
     *
     * @param filename
     * @return
     */
    public String exportToCSV(String filename){

        String sqlSelect =
                "SELECT id, format " +
                        "FROM files " +
                        "ORDER BY id";

        try{
            Connection conn = this.connect();
            Statement pstmt = conn.createStatement();
            ResultSet results = pstmt.executeQuery(sqlSelect);

            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename + ".csv"));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader(results.getMetaData()).withQuoteMode(QuoteMode.ALL));

            while (results.next()) {

                csvPrinter.printRecord(
                        results.getInt(1),
                        results.getString(2)
                );
            }
            csvPrinter.flush();
            csvPrinter.close();

            return "Export is successful.";


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return "Export is unsuccessful.";
    }
}
