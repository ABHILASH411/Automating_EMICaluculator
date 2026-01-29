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
    public static CellStyle style;

    public static int getRowCount(String xlfile,String xlsheet) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        int rowcount=ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowcount;
    }


    public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        int cellcount=row.getLastCellNum();
        wb.close();
        fi.close();
        return cellcount;
    }


    public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);

        // Check if the row exists
        if (row == null) {
            wb.close();
            fi.close();
            return "";
        }

        cell = row.getCell(colnum);

        // Check if the cell exists
        if (cell == null) {
            wb.close();
            fi.close();
            return "";
        }

        String data;
        try {
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }

        wb.close();
        fi.close();
        return data;
    }



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

    public static void fillGreenColor(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();

        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo=new FileOutputStream(xlfile);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }


    public static void fillRedColor(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();

        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo=new FileOutputStream(xlfile);
        wb.write(fo);
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
