package extentreports;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import lib.Config;
import lib.ExtentManager;
import lib.Utility;

public class ReportTest {
	
		//ExtentReports extent;
		//ExtentTest test;
		WebDriver driver;
		
		@BeforeTest
		public void setup()
		{
			//extent = ExtentManager.getExtent();
			
			driver = new FirefoxDriver();
		}
		
		
		@Test(priority=1)
		public void test1() throws InterruptedException{
			
			//Get url
			driver.get("http://www.yahoo.com");
			
			String MainWindowHandle = driver.getWindowHandle();
			
			
			//NEW WINDOW
			WebDriver driver1=new FirefoxDriver();
			//test.log(Status.PASS, "Open new window");
			
			driver1.get("http://www.google.com");
			//test.log(Status.PASS, "Navigate to cc");
		
			Thread.sleep(5000);			
			System.out.println("Okay");
			
			
			//SWITCH BACK
			driver.switchTo().window(MainWindowHandle);
			
			driver.get("http://www.gmail.com");
			
			//test.log(Status.PASS, "Back to old window");
		}
		
		@AfterSuite
		public void after(){
			
			//extent.flush();
			
			//driver.get(ExtentManager.filePath);
		}
		
}
