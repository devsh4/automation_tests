package pa_bvt;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Utility;

/**
 * @description= BVT :- Other leftlinks case	
 * @author Dev Shah
 * 
 */
	
public class OtherLeftLinks {
		
		//instantiating variables.
		ExcelDataConfig ex=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
		
		String baseUrl=ex.getData("Variables", 1, 1);
		String uname=ex.getData("Variables", 3, 1);
		String password=ex.getData("Variables", 5, 1);
		String browserType = ex.getData("Variables", 7, 1);
		String unamefield = ex.getData("Variables", 11, 1);
		String passfield = ex.getData("Variables", 13, 1);
		String login = ex.getData("Variables", 15, 1);
		String pfname = ex.getData("BVT", 25, 2);
		String compname = ex.getData("BVT", 27, 2);
		String membername =ex.getData("BVT", 29, 1);
		//String midUrl6=baseUrl+ex.getData("BVT", 25, 1);

		private static String downloadPath = "C:\\Users\\Dev Shah\\Downloads";

		//Links URL

		String ciqEstimates= baseUrl+"/CIQDotNet/Lists/Estimates/Consensus.aspx?projectid=";
		String thompsEstimates= baseUrl+"/CIQDotNet/Lists/Estimates/Consensus.aspx?projectid=";
		String transaction= baseUrl+"/CIQDotNet/Transactions/TransactionSummary.aspx?projectid=";
		String transaction1= baseUrl+"/CIQDotNet/Transactions/TransactionSummary.aspx?projectid=";
		String news= baseUrl+"/CIQDotNet/news/news.aspx?projectid=";
		String events = baseUrl+"/CIQDotNet/KeyDevs/EventCalendar.aspx?projectid=";
		String keydevs = baseUrl+"/CIQDotNet/KeyDevs/KeyDevelopments.aspx?projectid=";
		String filings= baseUrl+"/CIQDotNet/Filings/FilingsAnnualReports.aspx?projectid=";
		String ir=baseUrl+"/CIQDotNet/Research/InvestmentResearch.aspx?projectid=";
		String profile=	baseUrl+"/CIQDotNet/Lists/Profile.aspx?projectid=";	
		String team=baseUrl+"/CIQDotNet/Portfolio/PortfolioTeam.aspx?projectId=";		
		
		WebDriver driver;
		
		/**
		 * @description= Logs in the platform
		 */
		@BeforeTest
		public void signIn()throws Exception{
			
			if(browserType.equalsIgnoreCase("Firefox")) {
		         driver = new FirefoxDriver();
		         }

		         else if (browserType.equalsIgnoreCase("Chrome")) { 

		          System.setProperty("webdriver.chrome.driver","C:\\Users\\Dev Shah\\Downloads\\chromedriver.exe");
		          driver = new ChromeDriver();        
		          }

		         else if (browserType.equalsIgnoreCase("IE")) { 

		              {System.setProperty("webdriver.ie.driver","C:\\Users\\Dev Shah\\Downloads\\IEDriverServer.exe");}
		              driver = new InternetExplorerDriver(); 
		       
		         }
		
			driver.get(baseUrl);
	
			driver.manage().window().maximize();
			
			//Pass values to login & password field
			driver.findElement(By.id(unamefield)).sendKeys(uname);
			driver.findElement(By.id(passfield)).sendKeys(password);
			driver.findElement(By.id(login)).click();		
		}
		
		@Test(priority=1)
		public void estimatesPages()
		{
			//CIQEstimates
			driver.get("https://www.capitaliq.com");
			driver.get(ciqEstimates+ex.getnumData("BVT", 25, 1)+"&dataVendorId=1");
			// String pfname=driver.findElement(By.xpath(".//*[@id='ctl02__textLabel']")).getText();
			Assert.assertEquals(driver.getTitle(), pfname+" > "+"Capital IQ Estimates > Consensus");
		
			//Thompson
			driver.get(thompsEstimates+ex.getnumData("BVT", 25, 1)+"&dataVendorId=314");
			Assert.assertEquals(driver.getTitle(), pfname+ " > "+"Thomson Estimates > Consensus");
		}
		
		@Test(priority=2)
		public void transactionPages()
		{
			//M&A
			driver.get(transaction+ex.getnumData("BVT", 25, 1)+"&transactionViewType=1");
			
			//String title=driver.findElement(By.xpath(".//*[@id='ctl14__textLabel']")).getText();
			Assert.assertEquals(driver.getTitle(), "Transaction Summary > M&A/Private Placements");
			
			//Public Offerings
			driver.get(transaction1+ex.getnumData("BVT", 25, 1)+"&transactionViewType=2");
			
			//String title1=driver.findElement(By.xpath(".//*[@id='ctl14__textLabel']")).getText();
			Assert.assertEquals(driver.getTitle(), "Transaction Summary > Public Offerings");
			
		}
		
		@Test(priority=3)
		public void news()
		{
			//News
			driver.get(news+ex.getnumData("BVT", 25, 1));
	//	String title=driver.findElement(By.xpath(".//*[@id='ctl22__textLabel']")).getText();
			Assert.assertEquals(driver.getTitle(), pfname);
		}
		
		@Test(priority=4)
		public void events(){
			//Events
			driver.get(events+ex.getnumData("BVT", 25, 1));
			Assert.assertEquals(driver.getTitle(), "Events Calendar");
		}
		
		@Test(priority=5)
		public void filings()	
		{
			//Filings
			driver.get(filings+ex.getnumData("BVT", 25, 1));
			
			//String pfname=driver.findElement(By.xpath(".//*[@id='ctl12__textLabel']")).getText();

			Assert.assertEquals(driver.getTitle(), pfname);
		}
		
		@Test(priority=6)
		public void keydevs()
		{
			//Keydevs
			
			driver.get(keydevs+ex.getnumData("BVT", 25, 1));
			Assert.assertEquals(driver.getTitle(), pfname);
		}
		
		@Test(priority=7)
		public void invresearch()
		{	//IR
			driver.get(ir+ex.getnumData("BVT", 25, 1)+"&pagemode=11");
				
			Assert.assertEquals(driver.getTitle(), pfname+" Investment Research");
		}
		
		@Test(priority=8)
		public void watchlist()
			{
				//Profile
			driver.get(profile+ex.getnumData("BVT", 25, 1));
						
			Assert.assertEquals(driver.getTitle(), pfname+" > Portfolio Profile");
			}
		
		@Test(priority=9)
		public void team() throws InterruptedException
		{
			//team
			driver.get("https://www.capitaliq.com");

			driver.get(team+ex.getnumData("BVT", 25, 1));
			Thread.sleep(5000);
		//	Assert.assertEquals(driver.getTitle(), pfname);
		
			driver.findElement(By.xpath(".//*[@id='teamcontrol_teamSection_users_optionsList']/option[1]")).click();
			
			Keyboard k=((HasInputDevices) driver).getKeyboard();
			k.sendKeys(membername);
			
			driver.findElement(By.xpath(".//*[@id='teamcontrol_teamSection_users_addBtn']")).click();

			Select s=new Select(driver.findElement(By.id("teamcontrol_teamSection_rolelist")));
			s.selectByVisibleText("Other");
			
			driver.findElement(By.id("save")).click();			
			
			Thread.sleep(5000);
			
			driver.navigate().refresh();
			
			
		/* *********To Do - Delete a pf member 
		
		*********************************************************************************************
		*********************************************************************************************
		*/
			driver.findElement(By.xpath("//table[@id='summarySection_ctl03_team']//tr/td[2]//a[contains(text(), membername)]/ancestor::td[1]/preceding::td[1]//input")).click();
			Thread.sleep(5000);
			System.out.println("checkbox checked");
			
			driver.findElement(By.id("summarySection_optionsButton_MenuButton")).click();
			System.out.println("options done");
			
			driver.findElement(By.linkText("Remove")).click();
			
			Alert al = driver.switchTo().alert();
			al.accept();

			Thread.sleep(5000);
			System.out.println("PASS Sirji!");
		}
			//driver.findElement(By.xpath("//a[contains(text(),'"+membername+"')]/preceding-sibling::input[@name='summarySection$ctl03$team$ctl03$r0c0']")).click();		
		
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
		public void AfterClass(){
			
			System.out.println("**** OtherLeftLinks Test Case Completed ****");
			this.driver.quit();
		}
}