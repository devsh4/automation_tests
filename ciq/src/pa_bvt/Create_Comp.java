package pa_bvt;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Utility;

public class Create_Comp {

	WebDriver driver;
	
	ExcelDataConfig ex=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
	
	String baseUrl=ex.getData("Variables", 1, 1);
	String uname=ex.getData("Variables", 3, 1);
	String password=ex.getData("Variables", 5, 1);
	String browserType = ex.getData("Variables", 7, 1);
	String unamefield = ex.getData("Variables", 11, 1);
	String passfield = ex.getData("Variables", 13, 1);
	String login = ex.getData("Variables", 15, 1);

	//Create Case
	String compname= ex.getData("BVT", 14, 1);
	String bm = ex.getData("BVT", 16, 1); 
	String const1 = ex.getData("BVT", 18, 1);
	String const2 = ex.getData("BVT", 20, 1);
	
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
		
		@Test(priority=1)
		public void CreateComposite() throws InterruptedException{
		
		//Navigate to Composites page
		driver.get(baseUrl);
		driver.navigate().to(baseUrl+"/CIQDotNet/Portfolio/MyComposites.aspx");
		Assert.assertTrue(driver.getTitle().contains("Composites"));
		
		//Create
		driver.findElement(By.linkText("Create Composite")).click();
		Assert.assertTrue(driver.getTitle().contains("Composites"));

		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='_basicInfoSection_Toggle__basicInfo__name']")));
		driver.findElement(By.xpath(".//*[@id='_basicInfoSection_Toggle__basicInfo__name']")).sendKeys(compname);
		
		Select s1=new Select(driver.findElement(By.id("_basicInfoSection_Toggle__basicInfo__type")));
		s1.selectByVisibleText("Simple Composite");
		
		driver.findElement(By.id("ctl02_ctl03_EC")).click();
		driver.findElement(By.linkText("Search")).click();
		
		driver.findElement(By.cssSelector("#__tab_ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab")).click();

		driver.findElement(By.cssSelector("#ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__searchIndex")).sendKeys(bm);
		driver.findElement(By.cssSelector("#ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__goIndex")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")));
		
		driver.findElement(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__indexTree']/ul/li[1]/ins")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(bm)));
		
		driver.findElement(By.linkText(bm)).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__addIndex']")));
		driver.findElement(By.xpath(".//*[@id='ctl02_Toggle__additionalInfo__benchmark__bs_float_popUp__tabs_IndexTab__addIndex']")).click();
				
		}
		
		@Test(priority=2)
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
		
		/*driver.findElement(By.cssSelector(".qeFrag.editHover.qeNonIE6")).click();	
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
		keyboard0.pressKey(Keys.ENTER);*/
		
		driver.findElement(By.id("_save")).click();
		//System.out.println("Composite saved");
		WebDriverWait w1=new WebDriverWait(driver,10);
		try{
		w1.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception e1)
		{
		//System.out.println("in catch block");
	
		driver.findElement(By.linkText("Composite Name")).click();
		
		w.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		
		driver.findElement(By.cssSelector("#ReportImage1>div>img")).click();

		driver.findElement(By.id("_filter__search")).sendKeys(compname);
		driver.findElement(By.id("_filter__go")).click();
		
		System.out.println("search results are up");
		//WebDriverWait w1=new WebDriverWait(driver, 30);
		
		w.until(ExpectedConditions.elementToBeClickable(By.linkText(compname)));
				
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#_gv_ctl02_ctl00")).click();
		
		driver.findElement(By.id("_filter__delete")).click();
		//System.out.println("1");
		
		//System.out.println("comp deleted");
		
		Alert alert=driver.switchTo().alert();
		//System.out.println(""+ alert.getText());
		alert.accept();
		
		driver.switchTo().defaultContent();		
		//w.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".UpdateProgressBackground")));
		}
		
		}
		
		@AfterMethod
		public void teardown(ITestResult result)
		{
		if(ITestResult.FAILURE==result.getStatus())
		{
			Utility.captureScreenshot(driver,result.getName()+System.currentTimeMillis());
		}
		}
		
		@AfterClass 
		public void AfterClass(){
			
			System.out.println("**** Composite Creation Finished ****");
			this.driver.quit();
		}


}
	

