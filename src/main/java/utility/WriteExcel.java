
package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel
{
    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public XSSFWorkbook workbook ;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    String xlFilePath;

    public WriteExcel(String xlFilePath){
        System.out.println("*"+xlFilePath);
    try {
   
this.xlFilePath = xlFilePath;
System.out.println("**");
File F = new File(xlFilePath);
System.out.println("**");
fis = new FileInputStream(F);
System.out.println("**2");
XSSFWorkbook workbook = new XSSFWorkbook(fis);
System.out.println("**2");

fis.close();
System.out.println("**");
} catch (Exception e) {
e.printStackTrace();
}
    }
   

   

    public boolean setCellData(String sheetName, String colName, int rowNum, String value)
    {
        try
        {
            int col_Num = -1;
            sheet = workbook.getSheet(sheetName);

            row = sheet.getRow(0);
            try {
for (int i = 0; i < row.getLastCellNum(); i++) {
   try {
if (row.getCell(i).getStringCellValue().trim().equals(colName))
{
   col_Num = i;
}
} catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
} catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
            //System.out.println(col_Num);

            sheet.autoSizeColumn(col_Num);
            row = sheet.getRow(rowNum);
            if(row==null)
                row = sheet.createRow(rowNum);

            cell = row.getCell(col_Num);
            if(cell == null)
                cell = row.createCell(col_Num);

            cell.setCellValue(value);

            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  false;
        }
        return true;
    }
}

