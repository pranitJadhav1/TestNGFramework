package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
static File file ;
static FileInputStream inputStream ;
static Workbook Workbook ;
static Sheet Sheet;

public ReadExcel(String filePath,String fileName,String sheetName){

try {
file = new File(filePath + "\\" + fileName);
inputStream = new FileInputStream(file);
Workbook = null;
String fileExtensionName = fileName.substring(fileName.indexOf("."));
if (fileExtensionName.equals(".xlsx")) {
Workbook = new XSSFWorkbook(inputStream);
} else if (fileExtensionName.equals(".xls")) {
Workbook = new HSSFWorkbook(inputStream);
}
Sheet = Workbook.getSheet(sheetName);
} catch (Exception e) {
e.printStackTrace();
}
}

   public String getData(int Row,int Col) throws IOException{
    String data = null;
try {
data = Sheet.getRow(Row).getCell(Col).getStringCellValue();
} catch (Exception e) { }
    return data ;
   }
   
   public int getColumnCount(){
    int colCount = Sheet.getRow(0).getPhysicalNumberOfCells();
    return colCount;
   }
   
   public int getRowCount(){
    int RowCount = Sheet.getPhysicalNumberOfRows();
    return RowCount;
   }
   
   public void getCloseExcel(){
    try {
Workbook.close();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
   }
   
   }  
