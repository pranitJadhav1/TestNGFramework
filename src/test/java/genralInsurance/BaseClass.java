package genralInsurance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import page.Endoresment.Endoresment;
import utility.ReadExcel;
import utility.utility;

//@Listeners(BaseClass.ListenerTest.class)
public class BaseClass {

	static String sUserName = "03c00150";   // Credential needs to read from the json file. It should not be visible to everyone.
	static String sPassword = "Jan@2021";
	static String sURL = "http://10.34.4.12:7001/idit-web/web-framework/login.do";
	static String driverPath = "D:\\Users\\03c00150\\Downloads\\chromedriver.exe";
	static String ScreenshotPath = "D:\\Pranit Jadhav\\Endoresment";
	static String FileName = "APIAutomationTestData.xlsx";
	static String ExcelPath = "D:\\Workspace\\DHFLAutomation";// System.getProperty("user.dir");
	WebDriver driver;

	static File Path = new File(ScreenshotPath + "\\" + utility.getCurrentDate());

	static ReadExcel PCR;
	static ReadExcel TWR;
	static ReadExcel PDFR;

	// writeExcel objWriteExcel = new writeExcel(ExcelPath+"\\"+FileName);

	@BeforeTest
	public void setup() throws Exception {

		ScreenshotPath = Path.toString();
		if (!Path.exists()) {
			if (Path.mkdir()) {
				System.out.println("Date folder is created!");
			} else {
				System.out.println("Failed to create Date folder");
			}
		}
	}

	@DataProvider(name = "D1")
	public Object[] getExecutionRowForPrivateCar() throws Exception {
		PCR = new ReadExcel(ExcelPath, FileName, "sheet1");
		Object[] objArray = getExecutionRow("sheet1", PCR);
		return objArray;
	}

	@Test(priority = 1, enabled = true, dataProvider = "D1")
	public void getPrivateCarEndorsment(int Row) throws Exception {
		System.out.println("In Private Car Endorsment");
		Invoke();
		String sheetName = "sheet1";
		int Row1 = 1;
		Endoresment E = new Endoresment(driver, ScreenshotPath, PCR, ExcelPath, FileName, sheetName);
		E.getTestData(Row1);
		E.getLoginFunction(sUserName, sPassword);
		E.getPolicySelection();
		E.getEndorsmentDetails();
		E.getIDVDetails();
		E.getTotalPremium();
		E.getPolicyResolution();
	}

	@DataProvider(name = "D2")
	public Object[] getExecutionRowForTwoWheeler() throws Exception {
		TWR = new ReadExcel(ExcelPath, FileName, "sheet2");
		Object[] objArray = getExecutionRow("sheet2", TWR);
		return objArray;
	}

	@Test(priority = 1, enabled = false, dataProvider = "D2")
	public void getTwoWheelerEndorsment(int Row) throws Exception {
		System.out.println("In TW Endorsment");
		Invoke();
		String sheetName = "sheet2";
		Endoresment E = new Endoresment(driver, ScreenshotPath, TWR, ExcelPath, FileName, sheetName);
		E.getTestData(Row);
		E.getLoginFunction(sUserName, sPassword);
		E.getPolicySelection();
		E.getEndorsmentDetails();
		E.getCoverDetails();
		E.getTotalPremium();
		E.getPolicyResolution();
	}

	@DataProvider(name = "PDFDP")
	public Object[] getPDFDP() throws Exception {
		PDFR = new ReadExcel(ExcelPath, FileName, "PDF");
		Object[] objArray = getExecutionRow("PDF", PDFR);
		return objArray;
	}

	@Test(priority = 1, enabled = false, dataProvider = "PDFDP")
	public void getPDF(int Row) throws Exception {
		Invoke();
		String sheetName = "PDF";
		Endoresment E = new Endoresment(driver, ScreenshotPath, PDFR, ExcelPath, FileName, sheetName);
		E.getTestData(Row);
		E.getLoginFunction(sUserName, sPassword);
		E.getPolicySelection1();
		E.getPDFDownload();

	}

	// @AfterTest
	public void CloseBrowser() {
		driver.quit();
	}

	public Object[] getExecutionRow(String sheetName, ReadExcel objExcelFile) throws Exception {
		List<Integer> lista = new ArrayList<Integer>();
		try {
			for (int i = 1; i < objExcelFile.getRowCount(); i++) {
				if (objExcelFile.getData(i, 0).toUpperCase().equals("Y")) {
					lista.add(i);
				}
			}
		} catch (Exception e) {
		}
		Object[][] objArray = new Object[lista.size()][];

		for (int i = 0; i < lista.size(); i++) {
			objArray[i] = new Object[1];
			objArray[i][0] = lista.get(i);
		}
		System.out.println(lista);
		objExcelFile.getCloseExcel();
		return objArray;

	}

	public void Invoke() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		ChromeOptions CO = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", System.getProperty("user.dir"));
		CO.setExperimentalOption("prefs", prefs);

		// driver1 = new ThreadLocal<WebDriver>();
		driver = new ChromeDriver(CO);
		// driver1.set(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(sURL);
		driver.manage().window().maximize();
	}
}
