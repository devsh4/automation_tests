package pa_bvt;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Utility;

public class ManagementPages {
	
	public static WebDriver driver;
		
	//Storing data points in variables from excel
	static ExcelDataConfig data=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
	static String baseUrl=data.getData("Variables", 1, 1);
	 
	String uname=data.getData("Variables", 11, 1);
	String password=data.getData("Variables", 13, 1);
	String signIn = data.getData("Variables", 15, 1);
	String browserType = data.getData("Variables", 7, 1);
	
	static String holding_audit_link = baseUrl+"/CIQDotNet/Portfolio/HoldingsAudits.aspx?projectid=";
	static String portfolio = data.getData("BVT", 81, 1);
	static String portfolio_id = data.getnumData("BVT", 82, 1);
	
	static String pf_security_link =data.getData("BVT", 83, 1);
	static String pf_security_replace =data.getData("BVT", 84, 1);
	static String pf_security_replacewith =data.getData("BVT", 85, 1);
	
	static String filePath ="C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\PortfolioTemplatePropData_Auto.xls";
	
	/**
	 * @description Logs into the platform
	 */
	@BeforeTest
	public void Login()
	{	
		
		if(browserType.equalsIgnoreCase("Firefox")) {
	         driver = new FirefoxDriver();
	         }

	         else if (browserType.equalsIgnoreCase("Chrome")) { 
	        	 

	          System.setProperty("webdriver.chrome.driver","C:\\Users\\Dev Shah\\Downloads\\chromedriver.exe");
	          driver = new ChromeDriver();        
	          }

	         else if (browserType.equalsIgnoreCase("IE")) { 

	 
	           System.setProperty("webdriver.ie.driver","C:\\Users\\Dev Shah\\Downloads\\IEDriverServer.exe");
	            	  
	              driver = new InternetExplorerDriver(); 
	       
	         }
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
		driver.manage().window().maximize();
		
		//Passing login credentials from excel
		driver.findElement(By.id(uname)).sendKeys(data.getData("Variables", 3, 1));
		driver.findElement(By.id(password)).sendKeys(data.getData("Variables", 5, 1));
		driver.findElement(By.id(signIn)).click();
	}
	
	@Test(priority=1)
	public void unlinkHoldingsMaster() throws InterruptedException
	{
		WebDriverWait w1=new WebDriverWait(driver, 180);

		driver.get(baseUrl);
		driver.get(baseUrl+"/CIQDotNet/Portfolio/HoldingsMaster.aspx");
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_filter_ds_T__portfolios")));	

		Assert.assertEquals(driver.getTitle(), "Holdings Master");
		
		Select s=new Select(driver.findElement(By.id("_filter_ds_T__portfolios")));
		s.selectByVisibleText(portfolio);

		driver.findElement(By.id("_filter_ds_T__searchText")).sendKeys(pf_security_link);
		
		driver.findElement(By.id("_filter_ds_T__go")).click();
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.linkText(pf_security_link)));
		
		//html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[1]/div
		driver.findElement(By.xpath(".//*[@id='_gv']/tbody/tr[2]/td[6]/div[1]/div")).click();
		
		//driver.findElement(By.xpath("//a[contains(text(),'NYSE:XOM')]/parent::div")).click();
		
		System.out.println("1");
		//*[@id='_gv_ctl02_ctl09']/span[1]
		//html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[3]/a/span[1]

		driver.findElement(By.xpath("//a[contains(text(),'NYSE:XOM')]/following::div[4]/a/span[1]")).click();
		
		System.out.println("2");

		driver.findElement(By.xpath("//a[contains(text(),'NYSE:XOM')]/following::a[1]/span")).click();

		System.out.println("Clicked");
		
		Alert a1= driver.switchTo().alert();
		a1.accept();
		
		System.out.println("Alert accepted");

		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gv_UpdateProgressBG")));

		driver.switchTo().defaultContent();
		//Assert.assertTrue(driver.findElements(By.linkText(pf_security_link)).isEmpty());
		//System.out.println("To next test case");
		//Thread.sleep(5000);

	}
	
	//@Test(priority=2)
	public void linkHoldingsMaster() throws InterruptedException{
		
		//driver.get(baseUrl);
		//driver.get(baseUrl+"/CIQDotNet/Portfolio/HoldingsMaster.aspx");
		 WebDriverWait w1=new WebDriverWait(driver, 180);

		 w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_filter_ds_T__portfolios")));	
		
		//Select s=new Select(driver.findElement(By.id("_filter_ds_T__portfolios")));
		//s.selectByVisibleText(portfolio);

		driver.findElement(By.id("_filter_ds_T__searchText")).clear();
		driver.findElement(By.id("_filter_ds_T__go")).click();

		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[1]")).click();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[3]/a/span[1]")).click();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[2]/a/span")).click();
				
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchTxtBox")).sendKeys(pf_security_link);
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchBtn")).click();
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='_securitySearchPopup_float_securitySearchPopupPanel__resultListBox']/option")));
		
		driver.findElement(By.xpath(".//*[@id='_securitySearchPopup_float_securitySearchPopupPanel__resultListBox']/option")).click();
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__selectSecurityBtn")).click();
		
		Alert a1= driver.switchTo().alert();
		a1.accept();
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.linkText(pf_security_link)));
			
		//--Export to csv--
		driver.findElement(By.cssSelector("#_csvLink>div>img")).click();
		//System.out.println("Exported");
		Assert.assertEquals(driver.getTitle(), "Holdings Master");
		
		//--Holdings Audit--
		driver.navigate().to(holding_audit_link+portfolio_id);
		//System.out.println("to the audit page");
		
		driver.findElement(By.linkText("Date/Time")).click();
		//System.out.println("Sorting");
		
		Assert.assertEquals(driver.getTitle(), portfolio);
		
	}
	
	//@Test(priority=3)
	public void replaceHoldingsMaster() throws InterruptedException{
		
		driver.get(baseUrl);
		WebDriverWait w1=new WebDriverWait(driver, 180);

		driver.get(baseUrl+"/CIQDotNet/Portfolio/HoldingsMaster.aspx");	
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_filter_ds_T__portfolios")));
		
		//--Selects portfolio from dropdown--
		Select s=new Select(driver.findElement(By.id("_filter_ds_T__portfolios")));
		s.selectByVisibleText(portfolio);
		
		//--Enters security ticker in search field
		driver.findElement(By.id("_filter_ds_T__searchText")).sendKeys(pf_security_replace);
		
		//--Clicks on Go button--
		driver.findElement(By.id("_filter_ds_T__go")).click();
		
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.linkText(pf_security_replace)));

		w1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[1]")));

		//--Clicks on the said security's caret--
		
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[1]")).click();
		
		//--Hovers/Clicks on 'Change holding' option--
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[3]/a/span[1]")).click();
		
		//--Clicks on 'search & replace'--
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[2]/a[2]/span")).click();
		//System.out.println("To the popup again !!!");
		
		//--Selecting the primary security first-- 
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchTxtBox")).sendKeys(pf_security_replacewith);
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchBtn")).click();
		
		//--Click on 'show securities' to see secondary security--
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='_securitySearchPopup_float_securitySearchPopupPanel__resultListBox']/option")));

		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__showSecuritiesBtn")).click();
		
		
		//--Waits and then Selects the security to replace within the popup--
		w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_securitySearchPopup_float_securitySearchPopupPanel__securitiesListBox")));
	
		Select security=new Select(driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__securitiesListBox")));
		security.selectByVisibleText(pf_security_replacewith+" - Common Stock");

		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__selectSecurityBtn")).click();
		
		WebDriverWait wait=new WebDriverWait(driver, 8);
		
		try{
		wait.until(ExpectedConditions.alertIsPresent());
		
		}
		catch(Exception e){
		driver.findElement(By.id("_portfolioChooser_float_popup__selectPortfolios")).click();
		}
		
		try{
			wait.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception e){
		driver.findElement(By.id("_portfolioChooser_float_popup__updateSelected")).click();
		}
		
		System.out.println("Replaced NYSE:IBM with LSE:IBM");
		
		//WebDriverWait w2=new WebDriverWait(driver, 30);
	
		//Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class,StaleElementReferenceException.class);

		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gv_UpdateProgressBG")));
		
		driver.findElement(By.id("_filter_ds_T__searchText")).clear();

		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gv_UpdateProgressBG")));

		driver.findElement(By.id("_filter_ds_T__searchText")).sendKeys(pf_security_replacewith);

		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gv_UpdateProgressBG")));
		
		
		driver.findElement(By.id("_filter_ds_T__go")).click();
		
		//to wait 
		try{
			wait.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception ex){
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[1]/div")).click();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[3]/a/span[1]")).click();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[7]/div[2]/table/tbody/tr[2]/td[6]/div[2]/a[2]/span")).click();
		}
	//	System.out.println("1");

		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchTxtBox")).sendKeys(pf_security_replace);
		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__searchBtn")).click();
	
		w1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[8]/div/table[3]/tbody/tr[2]/td/div/select/option")));

		driver.findElement(By.id("_securitySearchPopup_float_securitySearchPopupPanel__selectSecurityBtn")).click();
		
	//	System.out.println("2");

		if(driver.findElement(By.id("_portfolioChooser_float_popup__selectPortfolios")).isEnabled())
		 {
		//	System.out.println("3");
			driver.findElement(By.id("_portfolioChooser_float_popup__selectPortfolios")).click();
		 }
		else{
			
			try{
			//	System.out.println("4");
			wait.until(ExpectedConditions.alertIsPresent());		
				}
			
			catch(Exception e){
			//	System.out.println("5");
				driver.findElement(By.id("_portfolioChooser_float_popup__selectPortfolios")).click();
				}
		}
		

		if(driver.findElement(By.id("_portfolioChooser_float_popup__updateSelected")).isEnabled())
		{
			driver.findElement(By.id("_portfolioChooser_float_popup__updateSelected")).click();
		}
		else{
			
			try{
			wait.until(ExpectedConditions.alertIsPresent());
				}
			
			catch(Exception e){
			driver.findElement(By.id("_portfolioChooser_float_popup__updateSelected")).click();
				}
		}
		
		System.out.println("DONE");
		
		w1.until(ExpectedConditions.elementToBeClickable(By.linkText("View Holdings Audits")));
		
	}
	  
		//else block
 	 
	
	//@Test(priority=4)
	public static void OtherManagementPages()
	{
		//Feed History test case
		 WebDriverWait w1=new WebDriverWait(driver, 180);
		 
		driver.get(baseUrl+"/CIQDotNet/Portfolio/FeedHistory.aspx");
		Assert.assertEquals(driver.getTitle(), "Feed History > Portfolio Analytics");
		
		//Report Debug View test case
		driver.get(baseUrl+"/CIQDotNet/Portfolio/ReportDebugView.aspx");
		Assert.assertEquals(driver.getTitle(), "Report Debug View");
		
		//Clarifi Queue View test case
		driver.get(baseUrl+"/CIQDotNet/Portfolio/ClarifiQueueView.aspx");
		Assert.assertEquals(driver.getTitle(), "ClariFI Queue View");
		
		//FTP History test case
		driver.get(baseUrl+"/CIQDotNet/Portfolio/FTPHistory.aspx");
		Assert.assertEquals(driver.getTitle(), "FTP History > Portfolio Analytics");
		
		Select dropdown=new Select(driver.findElement(By.id("_filter_DisplaySection1_Toggle__dateRange_PeriodMenu")));
		dropdown.selectByVisibleText("Last 7 Days");
		
		driver.findElement(By.id("_filter_DisplaySection1_Toggle__go")).click();
	
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		driver.findElement(By.cssSelector("#_excelLink>div>img")).click();
		
		//************
		//Client Data test case
		driver.get(baseUrl+"/CIQDotNet/Portfolio/PropData.aspx");
		Assert.assertEquals(driver.getTitle(), "Client Data > Proprietary Data");
		
		Select pf=new Select(driver.findElement(By.id("_portfolios")));
		pf.selectByVisibleText("All Portfolios");
		Select data=new Select(driver.findElement(By.id("_dataItems")));
		data.selectByVisibleText("All Data Items");
		
		driver.findElement(By.id("_from")).sendKeys("1/1/2016");
		driver.findElement(By.id("_go")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		driver.findElement(By.id("_export")).click();
		
		driver.findElement(By.linkText("As Of Date")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
	
	}
	
	//@Test(priority=5)
	public static void PropDataEditor()
	{

		//driver.get(baseUrl);
		driver.get(baseUrl+"/CIQDotNet/Portfolio/PropDataEditor.aspx");
		WebDriverWait w1=new WebDriverWait(driver, 180);
		 
		Select select=new Select(driver.findElement(By.id("_filter_ds_T__viewOptions")));
		select.selectByVisibleText("Holdings By Portfolio");
		
		w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_filter_ds_T__portfolios")));
		
		Select s=new Select(driver.findElement(By.id("_filter_ds_T__portfolios")));
		s.selectByVisibleText("LongShort");
		
		driver.findElement(By.id("_filter_ds_T__columns_removeAllBtn")).click();
		
		s=new Select(driver.findElement(By.id("_filter_ds_T__columns_optionsList")));
		s.selectByIndex(0);
		s.selectByIndex(1);
		
		driver.findElement(By.id("_filter_ds_T__columns_addBtn")).click();
		
		//driver.findElement(By.id("_filter_ds_T__columns_addAllBtn")).click();
		driver.findElement(By.id("_filter_ds_T__go")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		driver.findElement(By.linkText("Edit")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));

		driver.findElement(By.id("_editor_ctl02_textBox5")).sendKeys("Auto Prop Input");
		
		driver.findElement(By.id("_editor_ctl02_ctl06")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));

		driver.findElement(By.cssSelector("#_csvLink>div>img")).click();
		
		driver.findElement(By.linkText("Upload New Proprietary Data")).click();
		
		WebElement uploadPopup=driver.findElement(By.id("_filter_ds_T_float_uploaderPopup__uploader_uploadFrame"));
		
		driver.switchTo().frame(uploadPopup);
		
		System.out.println("Switched to popup");
		
		//************************* FILE LOCATION*****************************************
		//********	****************
		
		driver.findElement(By.id("_upload")).sendKeys(filePath);
		
		driver.findElement(By.id("_next")).click();
		
		System.out.println("File selected");
		
	//	w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_filter_ds_T_float_uploaderPopup")));
		System.out.println("next popup");
		
		try{
			
		driver.findElement(By.cssSelector("#_fieldMapper__mapping__match_0"));
		
		//System.out.println("if loop");
		Select s1=new Select(driver.findElement(By.xpath("//span[contains(text(),'AUTO Data Point')]/following::td[1]/select")));
		s1.selectByVisibleText("Custom");
		
		driver.findElement(By.id("_nextBtn")).click();
		w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_resultsGrid__finishBtn")));
		driver.findElement(By.id("_resultsGrid__finishBtn")).click();
		
		w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		System.out.println("Prop Data Done !");
		
		}
		catch(Exception e)
		{
			System.out.println("In else loop");
			
			driver.findElement(By.id("_resultsGrid__finishBtn")).click();
			
			w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
			
			System.out.println("Prop Data Done !!");
/*			
 * 				if(driver.findElement(By.id("_resultsGrid__backBtn")).isDisplayed()){
				
				driver.findElement(By.id("_nextBtn")).click();
				w1.until(ExpectedConditions.presenceOfElementLocated(By.id("_resultsGrid__backBtn")));
				
				driver.findElement(By.id("_resultsGrid__finishBtn")).click();
				w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
				
				System.out.println("Prop Data Done !!");
				
		}
			else{
				w1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
				System.out.println("Prop Data Done !!!");

				}*/
		}

	
		}
	
	
	//@AfterMethod
	public void tearDown(ITestResult result)
	{
	 
	// Here will compare if test is failing then only it will enter into if condition
	if(ITestResult.FAILURE==result.getStatus())
	{
		Utility.captureScreenshot(driver,result.getName()+System.currentTimeMillis());
	}
	
	 
	 
	}

	@AfterClass
	public static void breakdown()
	{
		driver.quit();
		System.out.println("******** Management Pages Test Case completed ! ********");
	}
}