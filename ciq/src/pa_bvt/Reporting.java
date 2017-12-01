package pa_bvt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Utility;
	
	/**
	 * @description= BVT :- Reporting case	
	 * @author Dev Shah
	 * 
	 */
	public class Reporting {

	static ExcelDataConfig data=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
	static String baseUrl=data.getData("Variables", 1, 1);
	
	static String uname=data.getData("Variables", 11, 1);
	static String password=data.getData("Variables", 13, 1);
	static String signIn = data.getData("Variables", 15, 1);
	static String browserType = data.getData("Variables", 7, 1);
	
	//Create Reports 
	static String pf1=data.getData("BVT", 32, 1);
	static String bm1=data.getData("BVT", 33, 1);
	
	static String pf2=data.getData("BVT", 35, 1);
	static String bm2=data.getData("BVT", 36, 1);
	static String template=data.getData("BVT", 37, 1);
	
	//Create Schedules 
	static String schname=data.getData("BVT", 41, 1);
	static String schtemp=data.getData("BVT", 42, 1);
	static String schpf1=data.getData("BVT", 43, 1);
	static String schpf2=data.getData("BVT", 44, 1);
	static String freq=data.getData("BVT", 45, 1);
	static String alert_freq=data.getData("BVT", 46, 1);

	//Create templates
	static String tempname=data.getData("BVT", 50, 1);
	static String rep_period=data.getData("BVT", 51, 1);
	static String metric=data.getData("BVT", 52, 1);
	static String grouping=data.getData("BVT", 53, 1);
	
	static String tempname1=data.getData("BVT", 55, 1); 
	static String copy_temp=data.getData("BVT", 56, 1);
	
	static WebDriver driver;
	
	@BeforeTest
	public static void login() throws InterruptedException
	{
	driver=new ChromeDriver();
	driver.get(baseUrl);
	driver.manage().window().maximize();
	driver.findElement(By.id(uname)).sendKeys(data.getData("Variables", 3, 1));
	driver.findElement(By.id(password)).sendKeys(data.getData("Variables", 5, 1));
	//Thread.sleep(3000);
	driver.findElement(By.id(signIn)).click();
	driver.get("https://www.capitaliq.com/CIQDotNet/Portfolio/PortfolioList.aspx");
	}
	
	
	/**
	 * 
	 * @throws InterruptedException
	 * @description= Test Reports Center Page
	 */
	
	//@Test(priority=1)
	public static void reportCenter() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.get(baseUrl+"/CIQDotNet/DocManagement/ReportsCenter.aspx");
		driver.get(baseUrl+"/CIQDotNet/DocManagement/ReportsCenter.aspx?IsPortfolio=True");
		
		Assert.assertEquals(driver.getTitle(), "Portfolio Analytics > Reports");
		//System.out.println("Title correct");
		driver.findElement(By.xpath(".//*[@id='layout_folder_f-85238356']/table/tbody/tr/td[3]")).click();
		
		WebDriverWait w=new WebDriverWait(driver, 60);
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='_portfolioView_Excel_ReportImage_24']/img")));
		
		System.out.println("in the pf folder");
		
		driver.findElement(By.id("myReportsOnlyRadio")).click();
		
		System.out.println("in my reports");
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='_portfolioView_Excel_ReportImage_24']/img")));

		driver.findElement(By.xpath("//*[@id='_portfolioView_Excel_ReportImage_24']/img")).click();
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='_portfolioView_Excel_ReportImage_24']/img")));
	
		System.out.println("Export done");
		
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='_portfolioView_HyperLink1_1']")).click();
		
		String title= driver.findElement(By.xpath(".//*[@id='myPageHeader']/span/span[1]")).getText();
		
		Assert.assertEquals(driver.getTitle(), title+" > Reports");
	}

	/**
	 * @description= Runs two PAreports, one with a pf & another with an index (using separate templates each time)
	 * @throws InterruptedException
	 */
	//@Test(priority=2)
	public static void reportBuilder() throws InterruptedException{

		//driver.get(baseUrl+"/CIQDotNet/Portfolio/ReportBuilder.aspx");
		driver.get(baseUrl+"/CIQDotNet/Portfolio/ReportBuilder.aspx");
		
		driver.findElement(By.linkText("Search")).click();
				
		driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_ctl00__searchPortfolios")).sendKeys(pf1);
		driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_ctl00__goPortfolios")).click();
		
		WebDriverWait wait=new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(pf1)));
		
		driver.findElement(By.linkText(pf1)).click();
		
		//driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_ctl00__portfolioStartDate")).getText();
		Thread.sleep(3000);
		
		driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_ctl00__addPortfolio")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myRbo_variables_toggle_ddTemplate")));
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myRbo_variables_toggle_ddBenchmark")));
		
		Select s=new Select(driver.findElement(By.id("myRbo_variables_toggle_ddBenchmark")));
		s.selectByVisibleText(bm1);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myRbv_variables_toggle_ddReportF")));
		
		Select s1=new Select(driver.findElement(By.id("myRbv_variables_toggle_ddReportF")));
		s1.selectByVisibleText("Daily");
		
		Select s2=new Select(driver.findElement(By.id("myRbv_variables_toggle__relativeDateRange__relativeEndDate")));
		s2.selectByVisibleText("End of Last Year");
		
		driver.findElement(By.id("myRv_dsreport_btnGenerate")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Tab 2")));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='myRv_dsreport_toggle__rv_main_pContents']/table/tbody/tr[2]/td/button")));

		Assert.assertTrue(!driver.getTitle().contains("Error"));
		
		driver.findElement(By.xpath(".//*[@id='excelLink']/div/img")).click();
		
		System.out.println("Report #1 Done");
	
		//2nd Report
		driver.findElement(By.id("myRv_dsreport_edit")).click();
		driver.findElement(By.cssSelector("#myRbo_variables_ctl01_EC")).click();
		driver.findElement(By.linkText("Search")).click();		
		
		driver.findElement(By.cssSelector("#__tab_myRbo_ctl00_float_popUp__tabs_IndexTab")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myRbo_ctl00_float_popUp__tabs_IndexTab_ctl01_ctl00__indexMessage")));
		
		driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_IndexTab__searchIndex")).sendKeys(pf2);
		driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_IndexTab__goIndex")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='myRbo_ctl00_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")));
		driver.findElement(By.xpath(".//*[@id='myRbo_ctl00_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(pf2)));
		driver.findElement(By.linkText(pf2)).click();
	
		WebElement e=driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_IndexTab__indexAvailabilityMessage"));
		
		WebDriverWait waits= new WebDriverWait(driver, 30);
		waits.until(ExpectedConditions.textToBePresentInElement(e,"Start Date"));
		
		driver.findElement(By.xpath(".//*[@id='myRbo_ctl00_float_popUp__tabs_IndexTab__addIndex']")).click();
		
		//Thread.sleep(3000);
		
		//driver.findElement(By.id("myRbo_ctl00_float_popUp__tabs_IndexTab__addIndex")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myRbo_variables_toggle_ddTemplate")));

		Select temp=new Select(driver.findElement(By.id("myRbo_variables_toggle_ddTemplate")));
		temp.selectByVisibleText(template);
		
		try{
		WebDriverWait wait1=new WebDriverWait(driver,20);
		wait1.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception exc){
		Select bm=new Select(driver.findElement(By.id("myRbo_variables_toggle_ddBenchmark")));
		bm.selectByVisibleText(bm2);
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myRv_dsreport_btnGenerate")));
		driver.findElement(By.id("myRv_dsreport_btnGenerate")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='myRv_dsreport_toggle__rv_main_pContents']/table/tbody/tr[2]/td/button")));
		Assert.assertTrue(!driver.getTitle().contains("Error"));

		System.out.println("Report #2 Done... !!!");
	}
	
	//@Test(priority=3)
	public static void MyTemplates() throws InterruptedException
	{
		//To-Do Mytemplates Test case
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	driver.get(baseUrl+"/CIQDotNet/Portfolio/MyTemplates.aspx");
	
	Assert.assertEquals(driver.getTitle(), "Templates > Portfolio Analytics");
	
	driver.findElement(By.id("_filter_mytemplates")).click();
	
	try{
	WebElement e = driver.findElement(By.xpath(".//*[@id='_gv']/tbody/tr[2]/td[6]"));	
	WebDriverWait w=new WebDriverWait(driver, 10);
	w.until(ExpectedConditions.textToBePresentInElement(e, "My Template"));
	}
	catch(Exception e){	
	driver.findElement(By.linkText("Date Created")).click();
	}
	Thread.sleep(3000);
	driver.findElement(By.id("_gv_ctl01_Icon")).click();

	Assert.assertTrue(!driver.findElements(By.xpath(".//*[@id='_gv_ctl02_Panel']")).isEmpty());
	
	driver.findElement(By.linkText("Create Template")).click();
	
	WebDriverWait w1=new WebDriverWait(driver, 30);
	w1.until(ExpectedConditions.elementToBeClickable(By.id("myTbo_variables_toggle_optNew")));
	
	Assert.assertEquals(driver.getTitle(), "Create Template > Templates");
	
	}
	
	//@Test(priority=4)
	public static void CreateTemp() throws InterruptedException
	{
		
		driver.get(baseUrl+"/CIQDotNet/Portfolio/TemplateBuilder.aspx");
		driver.findElement(By.id("myTbo_variables_toggle_txtNew")).sendKeys(tempname);
		driver.findElement(By.id("myTbo_variables_toggle_chkTest")).click();
		
		Select s=new Select(driver.findElement(By.id("myRbv_variables_toggle__relativeDateRange__relativeEndDate")));
		s.selectByVisibleText(rep_period);
		
		driver.findElement(By.linkText("Add Data Items")).click();
		
		// ADD METRICS
		
		driver.findElement(By.id("floatdataItemPopup_myTbd_search")).sendKeys(metric);
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("arguments[0].sendKeys('Return');", e);
		
		Select drop=new Select(driver.findElement(By.id("floatdataItemPopup_myTbd_category")));
		drop.selectByIndex(0);
		
		Select s1=new Select(driver.findElement(By.id("floatdataItemPopup_myTbd_item")));
		s1.selectByIndex(0);

		driver.findElement(By.id("floatdataItemPopup_myTbd_myAdd")).click();
		
		WebDriverWait w=new WebDriverWait(driver, 30);
	//	w.until(ExpectedConditions.elementToBeClickable(By.id("floatdataItemPopup_myTbd_doneSelecting")));
		Thread.sleep(8000);
		driver.findElement(By.id("floatdataItemPopup_myTbd_doneSelecting")).click();	
		//driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[13]/div/table/tbody/tr/td[3]/a/img")).click();	
		
		System.out.println("METRICS DONE");
		
		// ADD GROUPING
		
		driver.findElement(By.linkText("Add Grouping Options")).click();
		
		driver.findElement(By.id("floatgroupingPopup_myTbg_search")).sendKeys(grouping);
		
		Select grp0=new Select(driver.findElement(By.id("floatgroupingPopup_myTbg_category")));
		grp0.selectByIndex(0);
		
		Select grp=new Select(driver.findElement(By.id("floatgroupingPopup_myTbg_item")));
		grp.selectByIndex(0);

		driver.findElement(By.id("floatgroupingPopup_myTbg_myAdd")).click();
		
		Thread.sleep(8000);
		
		driver.findElement(By.id("floatgroupingPopup_myTbg_doneSelecting")).click();	
		
		System.out.println("GROUPINGS DONE");

		//ADD CHART
		
		driver.findElement(By.linkText("Add Charts")).click();
		
		Select ss=new Select(driver.findElement(By.id("floatchartPopup_myTbc_topTabs_templatesPanel_category")));
		ss.selectByIndex(2);
		
		Select opt=new Select(driver.findElement(By.id("floatchartPopup_myTbc_topTabs_templatesPanel_item")));
		opt.selectByIndex(1);
		
		System.out.println("Selection done");
		
		driver.findElement(By.id("_bToChartOptions")).click();
		
		System.out.println("Move on to chart options");		
		
		//WebDriverWait w=new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("floatchartPopup_myTbc_myAdd")));
		
		driver.findElement(By.id("floatchartPopup_myTbc_myAdd")).click();
		
		System.out.println("Chart added");
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("myTbbBottom_btnSave_MenuButton")));
		
		System.out.println("DONEE !!!!!");
		
		Thread.sleep(8000);
		
		driver.findElement(By.id("myTbbBottom_btnSave_MenuButton")).click();
		driver.findElement(By.id("myTbbBottom_btnSave_ctl01_sav_txt")).click();
	
		}
	
	//@Test(priority=5)
	public static void CreateTemp1() throws InterruptedException
	{
		driver.get(baseUrl+"/CIQDotNet/Portfolio/TemplateBuilder.aspx");
		
		driver.findElement(By.id("myTbo_variables_toggle_txtNew")).sendKeys(tempname1);
		
		Select s=new Select(driver.findElement(By.id("myTbo_variables_toggle_ddTemplate")));
		s.selectByVisibleText(copy_temp);
	
		WebDriverWait w=new WebDriverWait(driver, 60);
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("myTbt_rl_0__rli0_ctl01_gv_ctl02_TemplateCustomName_100054_102532_tb")));
		
		driver.findElement(By.id("myTbbBottom_btnSave_MenuButton")).click();
		driver.findElement(By.id("myTbbBottom_btnSave_ctl01_sab_txt")).click();
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("myRbo_variables_toggle_ddPortfolio")));

		Assert.assertEquals(driver.getTitle(), "Generate Report > Reports");
	}
	
	//@Test(priority=6)
	public static void Schedules() throws InterruptedException
	{
		driver.get(baseUrl+"/CIQDotNet/Portfolio/MySchedules.aspx");
		Assert.assertEquals(driver.getTitle(), "Schedules > Portfolio Analytics");

		driver.findElement(By.id("_filter__mySchedules")).click();

		try
		{
		WebDriverWait w=new WebDriverWait(driver,10);
		w.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception e){
		
			driver.findElement(By.linkText("Schedule Name")).click();
			System.out.println("Sorting done");
			 
			driver.findElement(By.linkText("Schedule Name"));
			Thread.sleep(5000);
			
			driver.findElement(By.id("_gv_ctl01_Icon")).click();
			System.out.println("expand all");
			
			driver.findElement(By.linkText("Add Portfolio")).click();
			
			System.out.println("Add portfolio clicked");
			
			Assert.assertTrue(!driver.getTitle().contains("Error"));
			
			driver.navigate().back();
			driver.findElement(By.linkText("Create Schedule"));
					
			}
	}
		
		/*driver.findElement(By.id("myTbo_variables_toggle_txtNew")).sendKeys("AutoTemp2");
		
		Select s=new Select(driver.findElement(By.id("myTbo_variables_toggle_ddTemplate")));
		s.selectByVisibleText("Portfolio Characteristics");
	
		WebDriverWait w=new WebDriverWait(driver, 60);
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("myTbt_rl_0__rli0_ctl01_gv_ctl02_TemplateCustomName_100054_102532_tb")));
		
		driver.findElement(By.id("myTbbBottom_btnSave_MenuButton")).click();
		driver.findElement(By.id("myTbbBottom_btnSave_ctl01_sab_txt")).click();
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("myRbo_variables_toggle_ddPortfolio")));

		*/
	
		@Test(priority=7)
		public static void CreateSchedule(){
			driver.get(baseUrl+"/CIQDotNet/Portfolio/ReportScheduler.aspx");
			Assert.assertEquals(driver.getTitle(), "Schedule Report > Schedules");
			
			driver.findElement(By.id("_scheduleOptions_ctl00_toggle__scheduleName")).sendKeys(schname);
			
			//select template
			Select s = new Select(driver.findElement(By.id("_scheduleOptions_ctl00_toggle__template")));
			s.selectByVisibleText(schtemp);
	
			//select one
			driver.findElement(By.id("_scheduleOptions_ctl00_toggle__portfolioPicker__search")).sendKeys(schpf1);
			driver.findElement(By.id("_scheduleOptions_ctl00_toggle__portfolioPicker__go")).click();
			driver.findElement(By.xpath(".//*[@id='_scheduleOptions_ctl00_toggle__portfolioPicker__portfolioChooser_addAllBtn']")).click();
	
			//select two
			driver.findElement(By.id("_scheduleOptions_ctl00_toggle__portfolioPicker__search")).sendKeys(schpf2);
			driver.findElement(By.id("_scheduleOptions_ctl00_toggle__portfolioPicker__go")).click();
			driver.findElement(By.xpath(".//*[@id='_scheduleOptions_ctl00_toggle__portfolioPicker__portfolioChooser_addAllBtn']")).click();
	
			//set frequency
			Select s1 = new Select(driver.findElement(By.id("_scheduleOptions_ctl00_toggle__frequency")));
			s1.selectByVisibleText(freq);
	
			Select s2 = new Select(driver.findElement(By.id("_scheduleOptions_email_toggleEmail__alertFreq")));
			s2.selectByVisibleText(alert_freq);
		
			driver.findElement(By.id("_save")).click();
			
			Assert.assertEquals(driver.getTitle(), "Schedules > Portfolio Analytics");
			
		}
		
		@AfterMethod
		public void tearDown(ITestResult result)
		{
		 
		// Here will compare if test is failing then only it will enter into if condition
		if(ITestResult.FAILURE==result.getStatus())
		{
			Utility.captureScreenshot(driver,result.getName()+System.currentTimeMillis());
		}
		}
		
		
	@AfterClass
	public void teardown(){
		
		driver.quit();
		System.out.println("**** Reporting Test Case Completed **** ");
	}
}

