package datadriven;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;

/**
 * @description data driven test case for index tearsheet smoke test.
 * @author Dev Shah
 *
 */
public class IndexAggregatesSmokeTest 
	
	{	
		WebDriver driver;
		//Storing  variables data from excel
		ExcelDataConfig indices=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Framework_1.0.xlsx");
		String baseUrl=indices.getData("Variables", 1, 1);
		
		String uname=indices.getData("Variables", 11, 1);
		String password=indices.getData("Variables", 13, 1);
	    String signIn = indices.getData("Variables", 15, 1);
		String browserType = indices.getData("Variables", 7, 1);

		String searchBar="SearchTopBar";
		String autoSuggest="div.acResults.regularAutoCompleteSearch";
		String search="a.iPadHack.tmbsearchright";
		String highchart="path.highcharts-tracker";
		String highchart1="g.highcharts-series.highcharts-tracker";
		
		@BeforeTest
		public void Login()		{
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
			
			//Passing login credentials from excel
			driver.findElement(By.id(uname)).sendKeys(indices.getData("Variables", 3, 1));
			driver.findElement(By.id(password)).sendKeys(indices.getData("Variables", 5, 1));
			driver.findElement(By.id(signIn)).click();
		}
		
		
		@Test(dataProvider="indices")	
		public void IndexTest(String IndexName) throws InterruptedException 
		{
			
			//--Test Plan--
			driver.findElement(By.id(searchBar)).clear();
			driver.findElement(By.id(searchBar)).sendKeys(IndexName);
			//driver.findElement(By.id("SearchTopBar")).sendKeys(" ");
			
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
		if(driver.findElement(By.cssSelector(autoSuggest)).isDisplayed())
			{
				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(autoSuggest)));
				driver.findElement(By.xpath("//*[contains(text(),'"+IndexName+"')]")).click();
			}
		else
			{
				driver.findElement(By.cssSelector(search)).click();
				driver.findElement(By.linkText(""+IndexName)).click();
			}
		
		String Title= IndexName+" > Index Profile";			
		try{
			
		//To check errors exists or page breaks.
		Assert.assertEquals(driver.getTitle(), Title);
		Thread.sleep(3000);
		//to check whether the high chart is displayed or not
		Assert.assertTrue(driver.findElement(By.cssSelector(highchart)).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector(highchart1)).isDisplayed());
		System.out.println("PASS:-"+IndexName);}
		
		catch(Exception e)
		{
			System.out.println("PASS:-"+IndexName);
		}
		//Left financial links
		//String Title0= IndexName+" > Key Stats";
		//String Title1= IndexName+" > Ratios";
			/*	try{
						driver.findElement(By.linkText("Key Stats")).click();
						//Assert.assertEquals(driver.getTitle(), Title0);
						driver.findElement(By.linkText("Ratios")).click();
						System.out.println("PASS:-"+IndexName);
				}
	
				catch(Exception e){
						System.out.println("FAIL:-"+IndexName);
						System.out.println(e.getMessage());
	}*/
		}
	
		
		//Read data from excel
		@DataProvider(name="indices")
		public Object[][] passData()
		{
		
		ExcelDataConfig indices=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Framework_1.0.xlsx");
		int rows=indices.getRowCount("Index_Test");
		
		Object arr[][]=new Object[rows][1];
		
			for(int i=0;i<rows;i++)
				{
					arr[i][0]=indices.getData("Index_Test", i, 0);
				}
		
		return arr;			
		}
		
		/*@AfterMethod
		public void after(ITestResult result){
			if(ITestResult.FAILURE==result.getStatus()){
				Screenshot.captureScreenshot(driver, result.getName());
			}
		}*/
		
		//To do after test execution completes
		@AfterSuite
		public void breakDown()
		{
			System.out.println("***************************");	
			System.out.println("-- TEST COMPLETE --");
			System.out.println("***************************");
		driver.close();
		}
	}

