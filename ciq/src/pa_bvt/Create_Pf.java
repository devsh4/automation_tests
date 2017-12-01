package pa_bvt;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

import lib.ExcelDataConfig;
import lib.Utility;

/**
 * @description= BVT :- Create Case	
 * @author Dev Shah
 * 
 */
	
public class Create_Pf {
		
		//instantiating variables.
		ExcelDataConfig ex=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
		
		String baseUrl=ex.getData("Variables", 1, 1);
		String uname=ex.getData("Variables", 3, 1);
		String password=ex.getData("Variables", 5, 1);
		String browserType = ex.getData("Variables", 7, 1);
		String unamefield = ex.getData("Variables", 11, 1);
		String passfield = ex.getData("Variables", 13, 1);
		String login = ex.getData("Variables", 15, 1);

		//Create Case
		String pfname= ex.getData("BVT", 3, 1);
		String style = ex.getData("BVT", 5, 1);
		String bm = ex.getData("BVT", 7, 1);
		String Filepath = ex.getData("BVT", 9, 1);
		
		WebDriver driver;
		
		/**
		 * 
		 * @description= Logs in the platform
		 */
		@BeforeTest
		public void Signin()throws Exception{
			
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
		 * @description= ---> Checks entire portfolios page
		 * @throws InterruptedException 
		 */
		@Test(priority=1)
		public void MyPortfolios() throws InterruptedException{

				//Navigate to portfolios Tab
				driver.get(baseUrl+"/CIQDotNet/Portfolio/PortfolioList.aspx");
				driver.get(baseUrl+"/CIQDotNet/Portfolio/PortfolioList.aspx");

				Assert.assertEquals(driver.getTitle(),"Portfolios > Portfolio Analytics");
				
				//Export
				driver.findElement(By.id("Excel_ReportImage")).click();
				//Thread.sleep(3000);
				
				//multi edit
				driver.findElement(By.xpath(".//*[@id='_gv_ctl02_chk']")).click();
				driver.findElement(By.xpath(".//*[@id='_gv_ctl03_chk']")).click();

				driver.findElement(By.cssSelector("#_portfolioFilter_portfolioTabSection__options_MenuButton")).click();
				
				driver.findElement(By.xpath(".//*[@id='_portfolioFilter_portfolioTabSection__options_ctl01__multiEditStyle']/span")).click();
				
				Select s=new Select(driver.findElement(By.id("float_alterStyle_ddStyle")));
				s.selectByVisibleText("Market Neutral");
				
				driver.findElement((By.id("float_alterStyle__styleSaveCancel__saveBtn"))).click();
				
				//Switch to All portfolios
			
				try{WebDriverWait w=new WebDriverWait(driver,10);
				w.until(ExpectedConditions.alertIsPresent());	
				}
				
				catch(Exception e){
				driver.findElement(By.id("_portfolioFilter_portfolioTabSection__allportfolios")).click();
				}
				//Sort by Last Modified column		
				driver.findElement(By.linkText("Last Modified")).click();
		}
		
		/**
		 * @description= ---> Creates portfolio
		 */
		@Test(priority=2)
		public void PfCreate() throws InterruptedException{
		
		Thread.sleep(3000);
		//WebDriverWait w=new WebDriverWait(driver, 10);
		//w.until(ExpectedConditions.elementToBeClickable(By.linkText("Create Portfolio")));
		
		driver.findElement(By.linkText("Create Portfolio")).click();
		
		Assert.assertTrue(driver.getTitle().contains("Portfolio"));

		WebElement pname=driver.findElement(By.id("summarySection_Toggle1_portfolioName"));
		pname.sendKeys(pfname);
		
		Select s=new Select(driver.findElement(By.id("summarySection_Toggle1_ddStyle")));
		s.selectByVisibleText(style);
		
		driver.findElement(By.linkText("Search")).click();
		driver.findElement(By.id("summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__searchIndex")).sendKeys(bm);
		driver.findElement(By.id("summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__goIndex")).click();
		
		WebDriverWait waitforres = new WebDriverWait(driver, 30);
		waitforres.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")));
		
		driver.findElement(By.xpath(".//*[@id='summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(bm)));
		
		driver.findElement(By.linkText(bm)).click();
		
		WebElement e=driver.findElement(By.id("summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__indexAvailabilityMessage"));
		
		WebDriverWait waits= new WebDriverWait(driver, 30);
		waits.until(ExpectedConditions.textToBePresentInElement(e,"Start Date"));
		
		driver.findElement(By.xpath(".//*[@id='summarySection_Toggle1_ctl00_float_popUp__tabs_IndexTab__addIndex']")).click();
		
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.elementToBeClickable(By.id("summarySection_Toggle1_ddRiskModel")));
		Select s1=new Select(driver.findElement(By.id("summarySection_Toggle1_ddRiskModel")));
		s1.selectByVisibleText("Global Fundamental Short Term");
		
		driver.findElement(By.id("bbBottom_mySave__saveBtn")).click();
		}
		
		/**
		 * @description= ---> Uploads holdings file
		 */

		@Test(priority=3)
		public void PfUpload(){
		//Upload Holdings File
		
				driver.findElement(By.id("_uploadBottomLink")).click();
				Assert.assertTrue(driver.getTitle().contains(pfname));

				driver.switchTo().frame(driver.findElement(By.id("float_uploaderPopup__uploader_uploadFrame")));
				driver.findElement(By.id("_upload")).sendKeys(Filepath);
				driver.findElement(By.id("_next")).click();
				
				driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
				driver.findElement(By.id("_resultsGrid__finishBtn")).click();
		
		}
		
		/**
		 * @description= ---> Deletes portfolio
		 * @throws InterruptedException 
		 */

		@Test(priority=4)
		public void PfDelete() throws InterruptedException{
		//Delete
					
				driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
				driver.findElement(By.linkText("Portfolios")).click();
				
				driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

				driver.findElement(By.id("_portfolioFilter_portfolioTabSection__search")).sendKeys(pfname);	
				driver.findElement(By.id("_portfolioFilter_portfolioTabSection__go")).click();
				
				WebDriverWait wait=new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.textToBePresentInElement(By.xpath(".//*[@id='_gv']/tbody/tr[2]/td[3]/div[1]/div"), pfname));
				//TimeUnit.SECONDS.sleep(5);
				driver.findElement(By.cssSelector("#_gv_ctl02_chk")).click();
				
				driver.findElement(By.xpath(".//*[@id='_portfolioFilter_portfolioTabSection__options_MenuButton']")).click();
				driver.findElement(By.xpath(".//*[@id='_portfolioFilter_portfolioTabSection__options_ctl01__multiDelete']/span")).click();
				
				Thread.sleep(2000);
				Alert alert=driver.switchTo().alert();
				//System.out.println(""+ alert.getText());
				alert.accept();
				
				driver.switchTo().defaultContent();
				
		}
	/*
		@Test(priority=5)
		public void CreateComposite() throws InterruptedException{
		
		//Navigate to Composites page
		driver.get(baseUrl);
		driver.navigate().to(baseUrl+"/CIQDotNet/Portfolio/MyComposites.aspx");
		
		Assert.assertEquals(driver.getTitle(),"Composites > Portfolio Analytics");
		
		//Create
		driver.findElement(By.linkText("Create Composite")).click();

		WebDriverWait w = new WebDriverWait(driver, 120);
		w.until(ExpectedConditions.presenceOfElementLocated(By.id("_basicInfoSection_Toggle__basicInfo__name")));
		
		Assert.assertEquals(driver.getTitle(),"Create Composite > Composites");
		
		driver.findElement(By.id("_basicInfoSection_Toggle__basicInfo__name")).sendKeys("BC");
		
		Select s1=new Select(driver.findElement(By.id("_basicInfoSection_Toggle__basicInfo__type")));
		s1.selectByVisibleText("Simple Composite");
		
		driver.findElement(By.id("ctl02_ctl03_EC")).click();
		driver.findElement(By.linkText("Search")).click();
		
		driver.findElement(By.cssSelector("#__tab_ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab")).click();

		driver.findElement(By.cssSelector("#ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__searchIndex")).sendKeys(bm);
		driver.findElement(By.cssSelector("#ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__goIndex")).click();

		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")));
		
		driver.findElement(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")).click();
		
		w.until(ExpectedConditions.elementToBeClickable(By.linkText(bm1)));
		
		driver.findElement(By.linkText(bm1)).click();
		
		w.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__addIndex']")));
		driver.findElement(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__addIndex']")).click();
				
		}
		
		@Test(priority=6)
		public void Addconstituents() throws InterruptedException{
			
		driver.findElement(By.id("_addConstituentDisplaySection_Toggle__addConstituent__search")).sendKeys(const1);
		driver.findElement(By.id("_addConstituentDisplaySection_Toggle__addConstituent__go")).click();
		
		WebDriverWait w=new WebDriverWait(driver, 120);
		w.until(ExpectedConditions.textToBePresentInElementLocated((By.xpath(".//*[@id='_addConstituentDisplaySection_Toggle__addConstituent__constituents']/option")), "US Generic"));
		
		//double click
		WebElement e=driver.findElement(By.xpath(".//*[@id='_addConstituentDisplaySection_Toggle__addConstituent__constituents']/option"));

		Actions action=new Actions(driver);
		action.doubleClick(e).perform();
		
		//Add funds
		driver.findElement(By.cssSelector("#_addConstituentDisplaySection_Toggle__addConstituent__funds")).click();
		
		Thread.sleep(5000);
		
		driver.findElement(By.id("_addConstituentDisplaySection_Toggle__addConstituent__search")).clear();
		driver.findElement(By.id("_addConstituentDisplaySection_Toggle__addConstituent__search")).sendKeys(const2);
		driver.findElement(By.id("_addConstituentDisplaySection_Toggle__addConstituent__go")).click();
		
		//WebDriverWait w1=new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.textToBePresentInElementLocated((By.xpath(".//*[@id='_addConstituentDisplaySection_Toggle__addConstituent__constituents']/option")), "FMAG.X"));
		
		//double click
		WebElement element=driver.findElement(By.cssSelector("#_addConstituentDisplaySection_Toggle__addConstituent__constituents>option"));

		Actions action1=new Actions(driver);
		action1.doubleClick(element).perform();

		//Type in weights
		
		driver.findElement(By.cssSelector(".qeFrag.editHover.qeNonIE6")).click();	
		WebDriverWait w2=new WebDriverWait(driver,20);
		w2.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ctl03_Toggle__constituents__gv_ctl03_textBox4']")));
		driver.findElement(By.xpath(".//*[@id='ctl03_Toggle__constituents__gv_ctl03_textBox4']")).sendKeys("60");
			
		Keyboard keyboard=((HasInputDevices) driver).getKeyboard();
		keyboard.pressKey(Keys.ENTER);
	
		driver.findElement(By.xpath(".//*[@id='ctl03_Toggle__constituents__gv']/tbody/tr[4]/td[4]/span")).click();
		WebDriverWait w3=new WebDriverWait(driver,20);
		w3.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ctl03_Toggle__constituents__gv_ctl04_textBox4']")));
		driver.findElement(By.xpath(".//*[@id='ctl03_Toggle__constituents__gv_ctl04_textBox4']")).sendKeys("40");
		
		Press enter
		Keyboard keyboard0=((HasInputDevices) driver).getKeyboard();
		keyboard0.pressKey(Keys.ENTER);
		
		driver.findElement(By.id("_save")).click();
		System.out.println("Composite saved");
		WebDriverWait w1=new WebDriverWait(driver,15);
		
		
		//try{
		//w1.until(ExpectedConditions.alertIsPresent());
		//}
		//catch(Exception e1)
		//{
		//System.out.println("in catch block");
		w1.until(ExpectedConditions.elementToBeClickable(By.linkText("Composite Name")));
		
		driver.findElement(By.linkText("Composite Name")).click();
		
		driver.findElement(By.cssSelector("#ReportImage1>div>img"));
		
		driver.findElement(By.id("_filter__search")).sendKeys(compname);
		driver.findElement(By.id("_filter__go")).click();
		
		Assert.assertTrue(driver.getTitle().contains("Composites"));
		
		System.out.println("search results are up");
		//WebDriverWait w1=new WebDriverWait(driver, 30);
		
		w.until(ExpectedConditions.elementToBeClickable(By.linkText(compname)));

		driver.findElement(By.cssSelector("#_gv_ctl02_ctl00")).click();
		
		driver.findElement(By.id("_filter__delete")).click();
		System.out.println("1");
		
		System.out.println("comp deleted");
		
		Alert alert=driver.switchTo().alert();
		//System.out.println(""+ alert.getText());
		alert.accept();
		
		driver.switchTo().defaultContent();		
		//w.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		
		}*/
		
		
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
		
			System.out.println("**** PF Creation Finished ****");
			this.driver.quit();
		}
		
		
	
	}
		
		
		


