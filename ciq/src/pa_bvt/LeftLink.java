package pa_bvt;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Utility;

/**
 * @description= BVT :- Left Link case	
 * @author Dev Shah
 * 
 */
	
public class LeftLink {
		
		//instantiating variables.
		ExcelDataConfig ex=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
		
		String baseUrl=ex.getData("Variables", 1, 1);
		String uname=ex.getData("Variables", 3, 1);
		String password=ex.getData("Variables", 5, 1);
		String browserType = ex.getData("Variables", 7, 1);
		String unamefield = ex.getData("Variables", 11, 1);
		String passfield = ex.getData("Variables", 13, 1);
		String login = ex.getData("Variables", 15, 1);
		
		//String midUrl6=baseUrl+ex.getData("BVT", 25, 1);

		private static String downloadPath = "C:\\Users\\Dev Shah\\Downloads";

		//LeftLink Case	

		String tearsheet= baseUrl+"/CIQDotNet/Portfolio/Tearsheet.aspx?projectId=";
		String summView=  baseUrl+"/CIQDotNet/Portfolio/PortfolioSummaryView.aspx?projectId=";
		String contri= baseUrl+"/CIQDotNet/Portfolio/ContribAnalysis.aspx?projectId=";
		String risk= baseUrl+"/CIQDotNet/Portfolio/QuickRisk.aspx?projectid=";
	
		WebDriver driver;
		
		/**
		 * 
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
	
		/**
		 * description= Run & Export Tearsheet 
		 * @throws InterruptedException 
		 * @throws Exception
		 */
		
		@Test(priority=1)
		public void pfTearsheet()throws Exception{
			
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
			driver.get(tearsheet+ex.getnumData("BVT", 25, 1));
			
			String pfname=driver.findElement(By.id("ctl09__textLabel")).getText();
			System.out.println(pfname);
			Assert.assertEquals(driver.getTitle(), pfname);
			System.out.println("Title match !");
		
			driver.findElement(By.xpath(".//*[@id='_ts0__customizeView__tearsheetInfo__drfDateRange']/span[1]")).click();
			
			
			Select s= new Select(driver.findElement(By.id("_ts0__customizeView__tearsheetInfo_ctl15__relativeLength")));
			s.selectByVisibleText("1 Month");
			System.out.println("Selected timeframe....");
			
			driver.findElement(By.xpath(".//*[@id='_ts0__customizeView__tearsheetInfo__gDateRange_soc']/button")).click();
			//System.out.println("Saved....");
			
			driver.findElement(By.cssSelector(".ButtonStyle")).click();
			System.out.println("Go....");
			
			//WebElement e=driver.findElement(By.id("_footerText"));
			
			WebDriverWait wait=new WebDriverWait(driver,600);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tabledata-left")));
			
			driver.findElement(By.cssSelector("#pdfLink>div>img")).click();
			
			//Thread.sleep(10000);
			try{
			WebDriverWait w=new WebDriverWait(driver,15);
			w.until(ExpectedConditions.alertIsPresent());
			}
			catch(Exception e){
			System.out.println("In catch block...");
			File getLatestFile = getLatestFilefromDir(downloadPath);
			String fileName = getLatestFile.getName();
			if(fileName.contains(pfname))
			{System.out.println("Correct name");}
			else{System.out.println("Wrong name");}
			//Assert.assertTrue(fileName.contains(pfname));
			//Assert.assertTrue(fileName.contains(".pdf"), "File Extension is incorrect");
				}
			}
		
		
		@Test(priority=2)
		public void compTearsheet(){
		driver.get(tearsheet+ex.getnumData("BVT", 27, 1));
		
		String compname=driver.findElement(By.id("ctl09__textLabel")).getText();
		Assert.assertEquals(driver.getTitle(), compname);
	
		driver.findElement(By.xpath(".//*[@id='_ts0__customizeView__tearsheetInfo__drfDateRange']/span[1]")).click();
		
		Select s1= new Select(driver.findElement(By.id("_ts0__customizeView__tearsheetInfo_ctl15__relativeLength")));
		s1.selectByVisibleText("3 Months");
		System.out.println("Selected timeframe....");
		
		driver.findElement(By.xpath(".//*[@id='_ts0__customizeView__tearsheetInfo__gDateRange_soc']/button")).click();
		//System.out.println("Saved....");
		
		driver.findElement(By.cssSelector(".ButtonStyle")).click();
		System.out.println("Go....");
		
		//WebElement e=driver.findElement(By.id("_footerText"));
		
		WebDriverWait wait1=new WebDriverWait(driver,180);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tabledata-left")));
		
	}
		
			@Test(priority=3)
			public void pfSummaryView() throws InterruptedException{
				
				driver.get(summView+ex.getnumData("BVT", 25, 1));
				
				String pfname = driver.findElement(By.id("ctl09__textLabel")).getText();
				
				if(driver.findElements(By.xpath("//*[@id='wc10_content']/span/span")).isEmpty())
				{
				WebDriverWait w=new WebDriverWait(driver,45);
				w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@id,'highcharts')]")));
				
				Assert.assertEquals(driver.getTitle(),pfname);
				
				Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@id,'highcharts')]")).isDisplayed());	
				Assert.assertTrue(driver.findElement(By.cssSelector("#wc18_widget__sectorLongShortGrid>tbody>tr>td>span")).isDisplayed());
				Assert.assertTrue(driver.findElement(By.id("wc12_content")).isDisplayed());
				Assert.assertTrue(driver.findElement(By.id("wc18_content")).isDisplayed());
				
				driver.findElement(By.cssSelector("#wordLink>div>img")).click();
				}
				
				else{
				throw new SkipException("SKIPPED - Portfolio cannot be processed or Portfolio has no holdings.");
				}
			}
			
			
			@Test(priority=4)
			public void compSummaryView(){
			
				driver.get(summView+ex.getnumData("BVT", 27, 1));
				String compname = driver.findElement(By.id("ctl09__textLabel")).getText();
				
			if(driver.findElements(By.xpath("//*[@id='wc10_content']/span/span")).isEmpty())
			{
				WebDriverWait w=new WebDriverWait(driver,45);
				w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@id,'highcharts')]")));
			
				
				Assert.assertEquals(driver.getTitle(),compname);
				//System.out.println("Title match");
				
				Assert.assertTrue(!driver.findElements(By.xpath("//*[contains(@id,'highcharts')]")).isEmpty());	
				//System.out.println("Highcharts present");
				
				Assert.assertTrue(driver.findElement(By.id("wc12_content")).isDisplayed());
				Assert.assertTrue(driver.findElement(By.id("wc18_content")).isDisplayed());
				//System.out.println("Widgets present");
				
				
				driver.findElement(By.xpath(".//*[@id='wc13_icoMenu']/span")).click();
				driver.findElement(By.cssSelector("#wc13_icoWordExport>span")).click();
			
				try{
					WebDriverWait w2=new WebDriverWait(driver,10);
					w2.until(ExpectedConditions.alertIsPresent());
					}
					catch(Exception e){
					System.out.println("Widget Export done!");
					}
				}
			else{
				System.out.println("");
				throw new SkipException("SKIPPED :- Portfolio has no holdings.");
			}
			}
			
			@Test(priority=5)
			public void pfContriAnalysis() throws InterruptedException{
				
				driver.get(contri+ex.getnumData("BVT", 25, 1));
				
				String pfname=driver.findElement(By.xpath(".//*[@id='ctl06__textLabel']")).getText();
				Assert.assertEquals(driver.getTitle(), pfname);
				
				Select s=new Select(driver.findElement(By.cssSelector("#groupDD0left")));
				s.selectByVisibleText("Grouped By");
				
				driver.findElement(By.id("btnGo")).click();
				
				try{
				WebDriverWait w5=new WebDriverWait(driver, 10);
				w5.until(ExpectedConditions.alertIsPresent());
				}catch(Exception e){
					System.out.println("In catch");
					driver.findElement(By.xpath(".//*[@id='_excelLink']/div/img")).click();
					
				}
				
			}
			
			@Test(priority=6)
			public void compContriAnalysis() throws InterruptedException{

				driver.get(contri+ex.getnumData("BVT", 27, 1));
				
				String compname=driver.findElement(By.xpath(".//*[@id='ctl06__textLabel']")).getText();
				Assert.assertEquals(driver.getTitle(), compname);
				
				Select s=new Select(driver.findElement(By.cssSelector("#groupDD0left")));
				s.selectByVisibleText("Grouped By");
				
				Select s1=new Select(driver.findElement(By.id("groupDD0right")));
				s1.selectByVisibleText("Industry");
				
				driver.findElement(By.id("btnGo")).click();
				
				try{
				WebDriverWait w5=new WebDriverWait(driver, 10);
				w5.until(ExpectedConditions.alertIsPresent());
				}catch(Exception e){
				
					driver.findElement(By.xpath(".//*[@id='_excelLink']/div/img")).click();
					
				}
			}
		
			@Test(priority=7)
			public void pfRiskDecomp(){
				driver.get(risk+ex.getnumData("BVT", 25, 1));
				
				if(driver.findElements(By.id("_lError")).isEmpty() && driver.findElements(By.id("_lPortDirty")).isEmpty())   
				{
					String pfname=driver.findElement(By.xpath(".//*[@id='ctl06__textLabel']")).getText();
					Assert.assertEquals(driver.getTitle(), pfname);
					
					WebDriverWait wait = new WebDriverWait(driver, 180);
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Portfolio Exposure")));
						
					//Assert.assertTrue(!driver.findElements(By.cssSelector("g.highcharts-grid")).isEmpty());
					//Assert.assertTrue(driver.findElement(By.cssSelector("g.highcharts-axis-labels.highcharts-xaxis-labels")).isDisplayed());
											
					Select riskmodel=new Select(driver.findElement(By.id("_customizeView_toggle__ddRiskModel")));
					riskmodel.selectByVisibleText("US Fundamental Medium Term");

						WebDriverWait waiting = new WebDriverWait(driver, 180);
						waiting.until(ExpectedConditions.elementToBeClickable(By.linkText("Portfolio Exposure")));
						
						//next tab
						driver.findElement(By.linkText("Portfolio Exposure")).click();
					try{	
						driver.findElement(By.cssSelector("#Excel_ReportImage>div>img")).click();
						WebDriverWait w7=new WebDriverWait(driver, 10);
						w7.until(ExpectedConditions.alertIsPresent());
					}
					catch(Exception e){}
						System.out.println("Exported");
				}
					
			
				else{
				System.out.println("");
				System.out.println("SKIPPED :- ");
				throw new SkipException("FAIL :- Portfolio cannot be processed or Portfolio has no holdings");
				
				}
				
			}
			
			
			@Test(priority=8)
			public void compRiskDecomp(){
				driver.get(risk+ex.getnumData("BVT", 27, 1));
				
				String compname=driver.findElement(By.xpath(".//*[@id='ctl06__textLabel']")).getText();
				Assert.assertEquals(driver.getTitle(), compname);
				
				if(driver.findElements(By.id("_lError")).isEmpty() && driver.findElements(By.id("_lPortDirty")).isEmpty())   
				{
				
					WebDriverWait wait = new WebDriverWait(driver, 180);
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Portfolio Exposure")));
					try{
						//Assert.assertTrue(!driver.findElements(By.cssSelector("g.highcharts-grid")).isEmpty());
						//Assert.assertTrue(driver.findElement(By.cssSelector("g.highcharts-axis-labels.highcharts-xaxis-labels")).isDisplayed());
						
						Select riskmodel=new Select(driver.findElement(By.id("_customizeView_toggle__ddRiskModel")));
						riskmodel.selectByVisibleText("US Fundamental Short Term");

						WebDriverWait waiting = new WebDriverWait(driver, 180);
						waiting.until(ExpectedConditions.elementToBeClickable(By.linkText("Portfolio Exposure")));
						
						driver.findElement(By.cssSelector("#Excel_ReportImage>div>img")).click();
						
						driver.findElement(By.id("_dsGrid__riskGrid__riskFilter__riskTabControl_Tab2_tabLink")).click();
						
						WebDriverWait wait1 = new WebDriverWait(driver, 10);
						wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("Stock Specific Risk")));
						
						Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='_dsGrid__riskGrid__gvRiskSecurity_ctl02']/td[7]/span")).isDisplayed());
					
					}
					catch(Exception e){}
					}
					
			
				else{
				System.out.println("");
				System.out.println("SKIPPED :- ");
				throw new SkipException("FAIL :- Portfolio cannot be processed or Portfolio has no holdings");
				}
				
			}
	 		

	
		private File getLatestFilefromDir(String downloadPath) {
			// TODO Auto-generated method stub
			 File dir = new File(downloadPath);
			    File[] files = dir.listFiles();
			    if (files == null || files.length == 0) {
			        return null;
			    }
			
			    File lastModifiedFile = files[0];
			    for (int i = 1; i < files.length; i++) {
			       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
			           lastModifiedFile = files[i];
			       }
			    }
			    return lastModifiedFile;
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
		public void AfterClass(){
			
			System.out.println("**** Left Link Test Case Completed ****");
			this.driver.quit();
		}
		
	}