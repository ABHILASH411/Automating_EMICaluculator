package utilites;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;


    public static void setCellData(String xlfile, String xlsheet, int rownum, int colnum, String data) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);

        // Check if the row exists; if not, create it
        row = ws.getRow(rownum);
        if (row == null) {
            row = ws.createRow(rownum);
        }

        // Create the cell and set the value
        cell = row.createCell(colnum);
        cell.setCellValue(data);

        // Write the changes back to the file
        fo = new FileOutputStream(xlfile);
        wb.write(fo);

        // Always close resources to prevent memory leaks or file locking
        wb.close();
        fi.close();
        fo.close();
    }



    public static String[][] getSheetDataAsString(String xlfile, String xlsheet, boolean includeHeader) throws IOException {
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream(xlfile);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(xlsheet);
            if (sheet == null) return new String[0][0];

            DataFormatter formatter = new DataFormatter();
            int lastRowNum = sheet.getLastRowNum();
            XSSFRow headerRow = sheet.getRow(0);

            // DYNAMIC COLUMN COUNTING: Stop at the first empty header
            int maxCols = 0;
            if (headerRow != null) {
                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    String cellVal = formatter.formatCellValue(headerRow.getCell(i));
                    if (cellVal != null && !cellVal.trim().isEmpty()) {
                        maxCols++;
                    } else {
                        break;
                    }
                }
            }

            if (maxCols == 0 || lastRowNum < 0) return new String[0][0];

            int startRow = includeHeader ? 0 : 1;
            int rowsToRead = (lastRowNum + 1) - startRow;
            if (rowsToRead <= 0) return new String[0][0];

            String[][] data = new String[rowsToRead][maxCols];
            int destRow = 0;
            for (int r = startRow; r <= lastRowNum; r++) {
                XSSFRow currentRow = sheet.getRow(r);
                for (int c = 0; c < maxCols; c++) {
                    String value = "";
                    if (currentRow != null) {
                        value = formatter.formatCellValue(currentRow.getCell(c));
                    }
                    data[destRow][c] = (value != null) ? value : "";
                }
                destRow++;
            }
            return data;
        } finally {
            if (workbook != null) workbook.close();
            if (fis != null) fis.close();
        }
    }



}
