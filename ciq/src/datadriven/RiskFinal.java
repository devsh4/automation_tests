package datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;

//----------------------- FIRST RUN getIQT/Try class ------------------------- 

/**
 * @description data driven test case for risk pages.
 * @author Dev Shah
 *
 */
public class RiskFinal {
	
		WebDriver driver;
		
		//Storing  variables/locators data from excel
		ExcelDataConfig data=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Framework_1.0.xlsx");
		String baseUrl=data.getData("Variables", 1, 1);
		String browserType = data.getData("Variables", 7, 1);
		String uname=data.getData("Variables", 11, 1);
		String password=data.getData("Variables", 13, 1);
	    String signIn = data.getData("Variables", 15, 1);
	    
		String pfTitle = "Portfolios > Portfolio Analytics";
		String highc0 = "g.highcharts-grid";
		String highc1= "g.highcharts-axis-labels.highcharts-xaxis-labels";
		String highc = "rect.highcharts-background";
		static String riskUrl;
		
		/**
		 * @description Logs in the platform
		 * @throws InterruptedException
		 */
		
		@BeforeTest
		public void Login() throws InterruptedException
		{	
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
			
			if(baseUrl.equalsIgnoreCase("https://www.testciq.com")){
				uname=data.getData("Variables", 11, 2);
				password=data.getData("Variables", 13, 2);
			    signIn = data.getData("Variables", 15, 2);
			}
			else if(baseUrl.equalsIgnoreCase("https://www.stagingciq.com")){

				uname=data.getData("Variables", 11, 2);
				password=data.getData("Variables", 13, 2);
			    signIn = data.getData("Variables", 15, 2);
			}
			else if(baseUrl.equalsIgnoreCase("https://www.capitaliq.com")){

				uname=data.getData("Variables", 11, 1);
				password=data.getData("Variables", 13, 1);
			    signIn = data.getData("Variables", 15, 1);
			}
			
			//Passing login credentials from excel
			driver.findElement(By.id(uname)).sendKeys(data.getData("Variables", 3, 1));
		
			driver.findElement(By.id(password)).sendKeys(data.getData("Variables", 5, 1));
			
			driver.findElement(By.id(signIn)).click();
		
		}
		
		/**
		 * @description Tests risk decomposition page for all portfolios thoroughly
		 * @param rawValue
		 * @throws InterruptedException
		 * @throws IOException
		 */
		@Test(dataProvider="portfolios")	
		public void RiskDecompTest(String rawValue) throws InterruptedException, IOException 
		{
			if(baseUrl.equalsIgnoreCase("https://www.testciq.com")){
				riskUrl= data.getData("RiskDecomp_Test", 1, 0);
			}
			else if(baseUrl.equalsIgnoreCase("https://www.stagingciq.com")){
				riskUrl= data.getData("RiskDecomp_Test", 2, 0);
			}
			else if(baseUrl.equalsIgnoreCase("https://www.capitaliq.com")){
				riskUrl= data.getData("RiskDecomp_Test", 3, 0);
			}
			
			String str1=rawValue.substring(2);
			int temp=Integer.parseInt(str1);
			temp=temp-1;
			String str2=Integer.toString(temp);
			String Instance= riskUrl+str2;
			driver.navigate().to(Instance);
		
			Thread.sleep(3000);
			Assert.assertTrue(!driver.getTitle().contains("Error"));
			
				if(driver.findElements(By.id("_lError")).isEmpty() && driver.findElements(By.id("_lPortDirty")).isEmpty())   
				{
			
					System.out.println("");			
					WebDriverWait wait = new WebDriverWait(driver, 180);
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Portfolio Exposure")));
					try{
						Assert.assertTrue(!driver.findElements(By.cssSelector(highc)).isEmpty());
						Assert.assertTrue(driver.findElement(By.cssSelector(highc0)).isDisplayed());
						Assert.assertTrue(driver.findElement(By.cssSelector(highc1)).isDisplayed());	
						System.out.println("PASS (HighChart) -  "+Instance);				
						
					}
					catch(Exception e){System.out.println("FAIL (Corda) - "+Instance);}
					/* if(!driver.findElements(By.cssSelector(highc)).isEmpty())
							{
								
								Assert.assertTrue(driver.findElement(By.cssSelector(highc0)).isDisplayed());
								Assert.assertTrue(driver.findElement(By.cssSelector(highc1)).isDisplayed());	
								System.out.println("PASS (HighChart) -  "+Instance);								
							}
				
					 else
							{
								System.out.println("PASS (Corda) - "+Instance);
							}*/
				}
			
				else{
				System.out.println("");
				System.out.println("SKIPPED :- "+Instance);
				throw new SkipException("FAIL :- (Portfolio cannot be processed or Portfolio has no holdings !)");

				}
		
				Thread.sleep(3000);
		}
			
			/*//Navigate to portfolios TAB
			driver.findElement(By.id(tab)).click();
			Assert.assertEquals(pfTitle,driver.getTitle());
			
			//--Test Plan--
			driver.findElement(By.id(search)).clear();
			driver.findElement(By.id(search)).sendKeys(PfName);
			driver.findElement(By.id(go)).click();
			Thread.sleep(3000);
			
			driver.findElement(By.linkText(PfName)).click();
			driver.findElement(By.linkText("Risk Decomposition")).click();
			*/
			
			//To check highcharts exist		
			
		/**
		 * @description Reads all Portfolio's ID's from excel.
		 * @return
		 * @throws Exception
		 */
		//Read data from excel
		@DataProvider(name="portfolios")
		public Object[][] passData() throws Exception
		{
		
			File src=new File("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\IQT.xlsx");
			
			FileInputStream fis= new FileInputStream(src);
			
			XSSFWorkbook wb= new XSSFWorkbook(fis);
			
			XSSFSheet sh=wb.getSheet("Sheet1");

			//sheet1=wb.getSheet("Sheet1");
		
			int rows= wb.getSheet("Sheet1").getLastRowNum();
			
			Object arr[][]=new Object[rows][1];

			for(int i=0;i<rows;i++)
				{
				arr[i][0] =sh.getRow(i).getCell(0).getStringCellValue();
				}
			return arr;
			//return Instance;
	
		}
		
		/**
		 * @description Prints the following message after test suite completion
		 */
		//To do after test execution completes
		@AfterClass
		public void breakDown()
		{
		System.out.println("***************************");	
		System.out.println("-- RISK DECOMP TEST COMPLETE --");
		System.out.println("***************************");
		driver.close();
		}
	}
