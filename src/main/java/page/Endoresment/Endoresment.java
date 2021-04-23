package page.Endoresment;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.ReadExcel;
import utility.WriteExcel;
import utility.utility;

public class Endoresment {
	utility utility = new utility();
	WebDriver driver;
	private String ScreenshotPath;
	private HashMap<String, String> TD;
	ReadExcel R;
	WriteExcel W;
	private String sheetName;
	private int CurrentRow;

	@FindBy(xpath = "//input[@id='UserName']")
	WebElement UserName;

	@FindBy(xpath = "//input[@id='Password']")
	WebElement Password;

	@FindBy(xpath = "//button[@id='Login']")
	WebElement LoginButton;

/////// Contact Selection ////////

	@FindBy(xpath = "//input[@id='IDITForm@firstName']")
	WebElement FirstName;

	@FindBy(xpath = "//input[@id='IDITForm@name']")
	WebElement LastName;

	@FindBy(xpath = "//button[@title='Find' and @id='homepageButtonsB']")
	WebElement FindButton;

//////  Policy Selection  ////////

	@FindBy(xpath = "//label[@title='Client Type']//preceding::b[@role='presentation'][1]")
	WebElement ContactDD;

	@FindBy(xpath = "/html/body/div[7]/ul/li[2]/div")
	WebElement PolicyOptionD;

	@FindBy(xpath = "//div[@class='showHiedAllFieldsButton']") // can add fol::i nw letency
	WebElement ShowAllFields;

	@FindBy(xpath = "//header[@title='Search by Policy/Proposal Details']")
	WebElement SearchPolicyHeader;

	@FindBy(xpath = "//input[@name='IDITForm@policyNumber']")
	WebElement InputPolicyNo;

	@FindBy(xpath = "//a[@id='createGeneralChange_Link']")
	WebElement GenralEndorsment;

	@FindBy(xpath = "//button[@id='DialogOK']")
	WebElement OkButton;

	@FindBy(xpath = "//a[@name='ViewEntityCorrespondences']")
	WebElement More;

	@FindBy(xpath = "//a[@id='dmsDocumentsListView|Download_file']")
	WebElement DownloadDFile;

////////  Maintain Policy Endorsement Details ////////

	@FindBy(xpath = "//input[@id='IDITForm@endorsementRequestDate']")
	WebElement EndorsementRequestDate;

	@FindBy(xpath = "//a[@title='Add']")
	WebElement AddReason;

	@FindBy(xpath = "//label[@title='Reason']//following::b[1]")
	WebElement EndoReason;

	@FindBy(xpath = "//textarea[@id='endorsmentReasonVORowVO@description']")
	WebElement EndoReasonDesc;

	@FindBy(xpath = "//button[@id='Next']")
	WebElement Next;

	@FindBy(xpath = "//button[@title='Update']")
	WebElement Update;

///////  IDV Details  ////////

	@FindBy(xpath = "//input[@id='idvOneOfTheVehicleStyleId']")
	WebElement IDVValue;

///////  Cover Details  ///////  

	@FindBy(xpath = "//a[text()='Cover Details']")
	WebElement CoverDetailsTab;

	@FindBy(xpath = "//input[@name='IDITForm@numberOfPersonsPaidDriver']")
	WebElement NoOfPerson;

	@FindBy(xpath = "//input[@name='IDITForm@siForPaidDriver']")
	WebElement SIForPaidDriver;

	@FindBy(xpath = "//button[@title='Save']")
	WebElement Save;

	@FindBy(xpath = "//button[@title='Finish']")
	WebElement Finish;

/////// Total Premium ///////

	@FindBy(xpath = "//span[text()='Total Premium']//following::td[7]")
	WebElement TotalPremium;

	@FindBy(xpath = "//button[@name='endCoversSelection']")
	WebElement EndCoverButton;

////// Policy Resolution //////

	@FindBy(xpath = "//input[@id='IDITForm@premiumForCollPresentationValueINR']")
	WebElement TotalWritten;

	@FindBy(xpath = "//button[@id='Find_Receipts']")
	WebElement FindReceipts;

	@FindBy(xpath = "//label[@for='transactionPVOList|1@isSelected']")
	WebElement IsSelect;

	@FindBy(xpath = "//input[@id='transactionPVOList|1@allocatedAmount']")
	WebElement AllocateAmt;

	@FindBy(xpath = "//button[@id='OK']")
	WebElement OK;

	@FindBy(xpath = "//div[@class='ConfirmationPageMessageDiv']")
	WebElement ConfirmationMsg;

	public Endoresment(WebDriver driver, String ScreenshotPath, ReadExcel objExcelFile, String filePath, String fileName,
			String sheetName) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.ScreenshotPath = ScreenshotPath;
		this.R = objExcelFile;
//this.W=objWriteExcel;
		this.sheetName = sheetName;

	}

	public void getTestData(int Row) throws Exception {
		this.CurrentRow = Row;
		TD = new LinkedHashMap<String, String>();
		for (int i = 0; i < R.getColumnCount(); i++) {
			TD.put(R.getData(0, i), R.getData(CurrentRow, i));
		}
		R.getCloseExcel();
		System.out.println(TD);

		ScreenshotPath = ScreenshotPath + "\\" + TD.get("ProductName");
		System.out.println(ScreenshotPath);
		File file = new File(ScreenshotPath);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

	}

	public void getLoginFunction(String userName, String password) throws Exception {
		UserName.clear();
		Password.clear();
		utility.takeSnapshot(driver, ScreenshotPath, "BeforeLogin");
		UserName.sendKeys(userName);
		Password.sendKeys(password);
		utility.takeSnapshot(driver, ScreenshotPath, "AfterLogin");
		LoginButton.click();
		utility.getExplicitWait(driver, 20, "//div[@id='showHiedAllFields']");

	}

	public void getSelectContact() throws Exception {
		FirstName.sendKeys(TD.get("FirstName"));
		LastName.sendKeys(TD.get("LastName"));
		FindButton.click();
		Thread.sleep(10000);
	}

	public void getPolicySelection1() throws Exception {
		ContactDD.click();
		Thread.sleep(2000);
		PolicyOptionD.click();
		ShowAllFields.click();
		SearchPolicyHeader.click();
		InputPolicyNo.sendKeys(TD.get("PolicyNo"));
		utility.takeSnapshot(driver, ScreenshotPath, "InputPolicyNo");
		FindButton.click();
		utility.getExplicitWait(driver, 10, "//a[@id='createGeneralChange_Link']");
		utility.takeSnapshot(driver, ScreenshotPath, "PostPolicySelection");
		More.click();
	}

	public void getPDFDownload() throws Exception {
		driver.findElements(By.xpath("//td[@title='19/09/2019']")).get(1).click();
		Thread.sleep(3000);
		String parent = driver.getWindowHandle();
		DownloadDFile.click();
		Thread.sleep(3000);
		Set<String> H = driver.getWindowHandles();
		Iterator<String> I1 = H.iterator();
		while (I1.hasNext()) {

			String child_window = I1.next();

			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
			}
		}
		Thread.sleep(12000);
		Runtime.getRuntime().exec("D:\\Pranit\\autoit-v3\\PDFDownload.exe");

		try {
			driver.switchTo().alert();
			driver.findElement(By.id("userID")).sendKeys("03c00150");
			driver.findElement(By.id("password")).sendKeys("Prja@8291");
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPolicySelection() throws Exception {
		ContactDD.click();
		Thread.sleep(2000);
		PolicyOptionD.click();
		ShowAllFields.click();
		SearchPolicyHeader.click();
		InputPolicyNo.sendKeys(TD.get("PolicyNo"));
		utility.takeSnapshot(driver, ScreenshotPath, "InputPolicyNo");
		FindButton.click();
		utility.getExplicitWait(driver, 10, "//a[@id='createGeneralChange_Link']");
		utility.takeSnapshot(driver, ScreenshotPath, "PostPolicySelection");
		GenralEndorsment.click();
		try {
			OkButton.click();
		} catch (Exception e) {
		}
		Thread.sleep(2000);
		try {
			OkButton.click();
		} catch (Exception e) {
		}
//Thread.sleep(10000);
		System.out.println(driver.getTitle());
		try {
			GenralEndorsment.click();
		} catch (Exception e) {
		}
	}

	public void getEndorsmentDetails() {
		utility.getExplicitWait(driver, 5, "//input[@id='IDITForm@endorsementRequestDate']");
		utility.takeSnapshot(driver, ScreenshotPath, "PreEndorsmentDetails");
		EndorsementRequestDate.clear();
		EndorsementRequestDate.sendKeys(TD.get("EndorsmentRequestDate"));
		AddReason.click();
		utility.wait(500);
		EndoReason.click();
		utility.wait(500);
		driver.findElement(By.xpath("//div[text()='" + TD.get("EndorsmentReason") + "']")).click();
		EndoReasonDesc.sendKeys("Description");
		utility.takeSnapshot(driver, ScreenshotPath, "PostEndorsmentDetails");
		Update.click();
		Next.click();
	}

	public void getIDVDetails() {
		utility.getScrollTillWebElement(driver, IDVValue);
		IDVValue.clear();
		IDVValue.sendKeys("606060\t");
		utility.takeSnapshot(driver, ScreenshotPath, "IDVValue");
		utility.wait(2000);
		Next.click();
	}

	public void getCoverDetails() throws Exception {
		utility.getExplicitWait(driver, 10, "//a[text()='Cover Details']");
		CoverDetailsTab.click();
		utility.takeSnapshot(driver, ScreenshotPath, "PreCoverDetails");
		NoOfPerson.clear();
		NoOfPerson.sendKeys(TD.get("NoOfPerson"));
		SIForPaidDriver.clear();
		SIForPaidDriver.sendKeys(TD.get("SIForPaidDriver"));
		utility.takeSnapshot(driver, ScreenshotPath, "PostCoverDetails");
		Thread.sleep(3000);
		Next.click();
	}

	public void getTotalPremium() {
//utility.getExplicitWait(driver, 5, "");
		utility.getScrollTillWebElement(driver, TotalPremium);
		String sTotalPremium = TotalPremium.getAttribute("title").split(" ")[0].trim();
		System.out.println(sTotalPremium);
//W.setCellData(sheetName, "TotalPremium", CurrentRow, sTotalPremium);
		utility.takeSnapshot(driver, ScreenshotPath, "TotalPremium");
		Next.click();
		utility.takeSnapshot(driver, ScreenshotPath, "EndCover");
		EndCoverButton.click();
	}

	public void getPolicyResolution() {
		String sTotalWritten = TotalWritten.getAttribute("value").split(" ")[0].trim();
		utility.takeSnapshot(driver, ScreenshotPath, "PolicyResolution");
		System.out.println(sTotalWritten);
// W.setCellData(sheetName, "TotalWritten", CurrentRow, sTotalWritten);
		FindReceipts.click();
		utility.takeSnapshot(driver, ScreenshotPath, "PreFindRecipt");
		IsSelect.click();
		AllocateAmt.clear();
		AllocateAmt.sendKeys(sTotalWritten);
		utility.takeSnapshot(driver, ScreenshotPath, "PostFindRecipt");
		OK.click();
		Next.click();
		utility.takeSnapshot(driver, ScreenshotPath, "PolicyContacts");
		Finish.click();
		utility.takeSnapshot(driver, ScreenshotPath, "MaintainEvent");
		Finish.click();
		System.out.println(ConfirmationMsg.getText());
// W.setCellData(sheetName, "FinalPolicyNo",CurrentRow,utility.getRegexString("[0-9]{11}\\/[0-9]{2}\\/[0-9]{6}\\/[0-9]{1}", ConfirmationMsg.getText()));
		utility.takeSnapshot(driver, ScreenshotPath, "ConfirmationMessage");
	}

}
