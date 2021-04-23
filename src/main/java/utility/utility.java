package utility;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

public class utility {
int i = 1 ;

public int takeSnapshot(WebDriver driver,String ScreenshotPath,String Name){

File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
// Now you can do whatever you need to do with it, for example copy somewhere
try {
Files.copy(scrFile, new File(ScreenshotPath+"\\"+i+"_"+Name+".png"));
} catch (IOException e) {
e.printStackTrace();
}
i++;
return i ;
}

public void getExplicitWait(WebDriver driver,int time,String xpath){
WebDriverWait wait = new WebDriverWait(driver,time);
wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
}

public void wait(int Time){
try {
Thread.sleep(Time);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

public void getScrollTillWebElement(WebDriver driver,WebElement Element){
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView();",Element );
}

public static String getCurrentDate(){
  DateFormat df = new SimpleDateFormat("dd-MM-yy");
      Date dateobj = new Date();
      return df.format(dateobj).toString();
}

public String getRegexString(String Regex,String value){

final Pattern pattern = Pattern.compile(Regex, Pattern.MULTILINE);
final Matcher matcher = pattern.matcher(value);


while (matcher.find()) {
    System.out.println("Full match: " + matcher.group(0).toString());
    break;
}
return matcher.group(0).toString();
   

}
}
