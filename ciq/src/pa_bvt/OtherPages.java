package pa_bvt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.handler.ImplicitlyWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sun.jna.platform.win32.WinDef.BYTE;

import lib.ExcelDataConfig;
import lib.Utility;

public class OtherPages {

	static ExcelDataConfig data=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
	static String baseUrl=data.getData("Variables", 1, 1);
	
	static String uname=data.getData("Variables", 11, 1);
	static String password=data.getData("Variables", 13, 1);
	static String signIn = data.getData("Variables", 15, 1);
	static String browserType = data.getData("Variables", 7, 1);
	
	//eq listings variables
	static String listing1=baseUrl+"/CIQDotNet/Securities/EquityListings.aspx?CompanyId="; //112350
	static String currency=data.getData("BVT", 61, 1);
	static String currency_ticker=data.getData("BVT", 62, 1);
	static String eq1_primary=data.getData("BVT", 63, 1);
	static String eq1_sec=data.getData("BVT", 64, 1);
	
	//
	static String listing2=baseUrl+"/CIQDotNet/Securities/EquityListings.aspx?CompanyId="; //682078
	static String currency1=data.getData("BVT", 67, 1);
	static String currency1_ticker=data.getData("BVT", 68, 1);
	static String eq2_primary=data.getData("BVT", 69, 1);
	static String eq2_sec=data.getData("BVT", 70, 1);
	
	
	//pf exposure
	static String exposure1=baseUrl+"/CIQDotNet/Portfolio/PortfolioExposure.aspx?CompanyId=";
	static String exposure_curr=data.getData("BVT", 72, 1);
	static String exposure_view=data.getData("BVT", 73, 1);

	//
	static String exposure2=baseUrl+"/CIQDotNet/Portfolio/PortfolioExposure.aspx?CompanyId=";
	static String exposure_curr1=data.getData("BVT", 75, 1);
	static String exposure_view1=data.getData("BVT", 76, 1);
			
	//index
	static String index=baseUrl+"CIQDotNet/Index/IndexTearsheet.aspx?companyId=";
	static String index1=baseUrl+"/CIQDotNet/company.aspx?companyId=";
			
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

		}

	@Test(priority=1)
	public static void eqListing(){
		driver.get(baseUrl);

		driver.get(listing1+data.getnumData("BVT", 60, 1));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		String title = driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
		Assert.assertEquals(driver.getTitle(), title+" > Equity Listings");
		
		driver.findElement(By.id("_main__filter__tradeDate")).sendKeys("1/1/2015");
		
		Select drop=new Select(driver.findElement(By.id("_main__filter__currency")));
		drop.selectByVisibleText(currency);
		
		driver.findElement(By.id("_main__filter__showInactive")).click();
		driver.findElement(By.id("_main__filter__go")).click();
		
		WebDriverWait w=new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Currency")));
		
		Assert.assertEquals(driver.getTitle(), title+" > Equity Listings");
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/div[1]/ul/li[2]/a/div/img")).click();	
		
		//driver.findElement(By.cssSelector("a[<style>='font-weight:bold']")).click();
		
		driver.findElement(By.linkText(eq1_primary)).click();
		String eqlistings=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
		Assert.assertEquals(driver.getTitle(), eqlistings+" > Equity Listing Details");

		//export
		driver.findElement(By.cssSelector("#Word_ReportImage>div>img")).click();
		
		//to check whether index membership, charts exist
		Assert.assertTrue(driver.findElement(By.cssSelector(".cTblSectHeaderTxt")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl04")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl05")).isDisplayed());		
	
		driver.findElement(By.linkText("Amount")).click();
		driver.findElement(By.linkText("Pay Date")).click();
		
		String pticker=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/div/table/tbody/tr[3]/td[2]/span")).getText();
		Assert.assertEquals(pticker, eq1_primary);
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle>div>table>tbody>tr>td>table>tbody>tr>td")).isDisplayed());	

		//to cross check if currency on eq list details page matches with the one selected on eq listings page 
		String curr=driver.findElement(By.id("_stockQuoteAndChart__disp__headerLabel")).getText();
		Assert.assertEquals(curr, "Stock Quote And Chart (Currency: "+currency_ticker+")");
		
		//back to eq listings page
		driver.findElement(By.linkText("Back to Equity Listings")).click();
	
		//secondary security 
		driver.findElement(By.linkText(eq1_sec)).click();
		
		driver.findElement(By.cssSelector("#Word_ReportImage>div>img")).click();

		String eqlistings1=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
		Assert.assertEquals(driver.getTitle(), eqlistings1+" > Equity Listing Details");
		
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl04")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl05")).isDisplayed());		
		
		String sticker=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/div/table/tbody/tr[3]/td[2]/span")).getText();
		Assert.assertEquals(sticker, eq1_sec);
	
		Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle>div>table>tbody>tr>td>table>tbody>tr>td")).isDisplayed());	
		
	}
	
	@Test(priority=2)
	public static void eqListing1()
	{
	
	driver.get(listing2+data.getnumData("BVT", 66, 1));
	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	
	String title = driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
	Assert.assertEquals(driver.getTitle(), title+" > Equity Listings");
	
	driver.findElement(By.id("_main__filter__tradeDate")).sendKeys("1/1/2016");
	
	Select drop=new Select(driver.findElement(By.id("_main__filter__currency")));
	drop.selectByVisibleText(currency1);
	
	driver.findElement(By.id("_main__filter__showInactive")).click();
	driver.findElement(By.id("_main__filter__go")).click();
	
	WebDriverWait w=new WebDriverWait(driver, 30);
	w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Currency")));
	
	Assert.assertEquals(driver.getTitle(), title+" > Equity Listings");
	driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/div[1]/ul/li[2]/a/div/img")).click();	
	
	//to eq listings details page
	
	driver.findElement(By.linkText(eq2_primary)).click();
	String eqlistings=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
	Assert.assertEquals(driver.getTitle(), eqlistings+" > Equity Listing Details");

	//export
	driver.findElement(By.cssSelector("#Word_ReportImage>div>img")).click();
	
	//to check whether index membership, charts exist
	Assert.assertTrue(driver.findElement(By.cssSelector(".cTblSectHeaderTxt")).isDisplayed());
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl04")).isDisplayed());
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl05")).isDisplayed());		

	driver.findElement(By.linkText("Amount")).click();
	driver.findElement(By.linkText("Pay Date")).click();
	
	String pticker=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/div/table/tbody/tr[3]/td[2]/span")).getText();
	Assert.assertEquals(pticker, eq2_primary);
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle>div>table>tbody>tr>td>table>tbody>tr>td")).isDisplayed());	
	
	String curr=driver.findElement(By.id("_stockQuoteAndChart__disp__headerLabel")).getText();
	Assert.assertEquals(curr, "Stock Quote And Chart (Currency: "+currency1_ticker+")");
	
	//back to eq listings page
	driver.findElement(By.linkText("Back to Equity Listings")).click();

	//secondary security 
	driver.findElement(By.linkText(eq2_sec)).click();
	
	driver.findElement(By.cssSelector("#Word_ReportImage>div>img")).click();

	String eqlistings1=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[3]/span/span[1]")).getText();
	Assert.assertEquals(driver.getTitle(), eqlistings1+" > Equity Listing Details");
	
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl04")).isDisplayed());
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle_ctl00_ctl05")).isDisplayed());		
	
	String sticker=driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/div/table/tbody/tr[3]/td[2]/span")).getText();
	Assert.assertEquals(sticker, eq2_sec);
	
	Assert.assertTrue(driver.findElement(By.cssSelector("#_stockQuoteAndChart__disp__toggle>div>table>tbody>tr>td>table>tbody>tr>td")).isDisplayed());	
}

	@Test(priority=3)
	public static void portfolioExposure() throws InterruptedException{
		
		
		driver.get(exposure1+data.getnumData("BVT", 74, 1));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		String title=driver.findElement(By.cssSelector("#_PageHeader_PageHeaderLabel>span")).getText();
		Assert.assertEquals(driver.getTitle(), title+" Portfolio Exposure");
		
		System.out.println("No error");
		
		Select s=new Select(driver.findElement(By.id("ds_T__customize__view")));
		s.selectByVisibleText(exposure_view);
		
		Select s1=new Select(driver.findElement(By.id("ds_T__customize__currency")));
		s1.selectByVisibleText(exposure_curr);
	
		driver.findElement(By.id("ds_T__customize__cbMarketValue")).click();
		driver.findElement(By.id("ds_T__customize__cbShares")).click();
		
		driver.findElement(By.id("ds_T__go")).click();
		System.out.println("Go");
		
		try{
		WebDriverWait w=new WebDriverWait(driver, 15);
		w.until(ExpectedConditions.alertIsPresent());
		}
		catch(Exception e)
		{
		driver.findElement(By.cssSelector("#excelLink>div>img")).click();
		
		//System.out.println("Exported");
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/table[2]/tbody/tr/td/div/div/table/tbody/tr[1]/th[1]/img"));
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/table[2]/tbody/tr/td/div/div/table/tbody/tr[1]/th[1]/img")).click();
		
		//System.out.println("Expanded");
		s1.selectByVisibleText("Euro");
		
		driver.findElement(By.id("ds_T__go")).click();
		//System.out.println("Go");
		try{
		WebDriverWait w1=new WebDriverWait(driver, 15);
		w1.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Market Value")));
		}
		catch(Exception exc){
		driver.findElement(By.linkText("Market Value")).click();
		//System.out.println("Sorted");
		driver.findElement(By.id("ds_T__customize__cbMarketValue"));
		Assert.assertEquals(driver.getTitle(), title+" Portfolio Exposure");
		
		}
		}
		
		}
	
	@Test(priority=4)
	public static void portfolioExposure1() throws InterruptedException{
		
		driver.get(exposure2+data.getnumData("BVT", 77, 1));
	
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		String title=driver.findElement(By.cssSelector("#_PageHeader_PageHeaderLabel>span")).getText();
		Assert.assertEquals(driver.getTitle(), title+" Portfolio Exposure");
		
		Select s=new Select(driver.findElement(By.id("ds_T__customize__view")));
		s.selectByVisibleText(exposure_view1);
		
		Select s1=new Select(driver.findElement(By.id("ds_T__customize__currency")));
		s1.selectByVisibleText(exposure_curr1);
		
		driver.findElement(By.id("ds_T__customize__cbMarketValue")).click();
		driver.findElement(By.id("ds_T__customize__cbShares")).click();
		
		driver.findElement(By.id("ds_T__go")).click();
		
		try
		{
			WebDriverWait w=new WebDriverWait(driver, 15);
			w.until(ExpectedConditions.alertIsPresent());
		} 
		
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			
			driver.findElement(By.cssSelector("#excelLink>div>img")).click();
			
			driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/table[2]/tbody/tr/td/div/div/table/tbody/tr[1]/th[1]/img"));
			driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[4]/div/form/div[5]/table/tbody/tr/td/table[2]/tbody/tr/td/div/div/table/tbody/tr[1]/th[1]/img")).click();
			
			s1.selectByVisibleText("British Pound");
			
			driver.findElement(By.id("ds_T__go")).click();
		
			try{
			WebDriverWait wait=new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Market Value")));
			}
			catch(Exception excep)
			{
			driver.findElement(By.linkText("Market Value")).click();
			driver.findElement(By.id("ds_T__customize__cbMarketValue"));
			Assert.assertEquals(driver.getTitle(), title+" Portfolio Exposure");
			}
			}
		
		Thread.sleep(3000);
		}
		
	//@Test(priority=5)
	 public static void Index(){
		
		driver.get(index+data.getData("BVT", 89, 1));
		
		String title=driver.findElement(By.id("_pageTitle_PageHeaderLabel")).getText();
		Assert.assertEquals(driver.getTitle(), title);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("path.highcharts-tracker")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("g.highcharts-series.highcharts-tracker")).isDisplayed());
		
		driver.findElement(By.cssSelector("#ReportImage1>div>img")).click();
		
		driver.findElement(By.linkText("Constituents"));

		String title1=driver.findElement(By.id("_pageTitle_PageHeaderLabel")).getText();
		Assert.assertEquals(driver.getTitle(), title1);
				
		driver.findElement(By.cssSelector("#ReportImage2>div>img")).click();
		System.out.println("Exported, case finished");
		
		driver.get(index1+data.getData("BVT", 90, 1));
		
		String title2=driver.findElement(By.id("_pageTitle_PageHeaderLabel")).getText();
		Assert.assertEquals(driver.getTitle(), title2);
		
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='frmMain']/div[6]/table/tbody/tr/td/table[2]/tbody/tr[2]/td[1]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='highcharts-0']/svg/rect")).isDisplayed());
		
		driver.findElement(By.cssSelector("#ReportImage1>div>img")).click();
		
		driver.findElement(By.linkText("Constituents"));
		
		Assert.assertTrue(!driver.getTitle().contains("Error"));
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
	public static void breakdown()
	{	
		System.out.println("**** Other Pages Test Case Completed ****");
		driver.quit();	
	}
	
}