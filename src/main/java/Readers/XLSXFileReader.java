package Readers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;

public class XLSXFileReader implements FormatReader {

    @Override
    public void read(File file, Map files) {
        try{
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb=new XSSFWorkbook(fis);
            XSSFSheet sheet=wb.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell id_cell = cellIterator.next();
                    int id = (int) id_cell.getNumericCellValue();

                    Cell format_cell = cellIterator.next();
                    String format = format_cell.getStringCellValue();
                    System.out.println("xlsx");
                    //files.put(id, format);
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
