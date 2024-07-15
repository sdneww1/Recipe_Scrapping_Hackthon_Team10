package reciepescrapping_team10_utility;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class writeexe {

    public XSSFWorkbook workbook;
    public FileOutputStream fo;
    public FileInputStream fi;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;

    public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
        String path ="C:\\Users\\Home\\eclipse-workspace\\Recipe_Scrapping_Hackthon_Team10\\TestData\\ScrapData.xlsx";
      //  C:\Users\Home\eclipse-workspace\Recipe_Scrapping_Hackthon_Team10\TestData\ScrapData.xlsx  
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
            fo.close(); // Close the file output stream after writing the workbook
        }
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        if (workbook.getSheetIndex(sheetName) == -1)
            workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);

        if (sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        row = sheet.getRow(rownum);

        cell = row.createCell(column);
        cell.setCellValue(data);
        System.out.println("Writing data to Excel: Sheet: " + sheetName + ", Row: " + rownum + ", Column: " + column + ", Data: " + data);
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }
}

