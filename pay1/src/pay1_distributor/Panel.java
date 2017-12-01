
package pay1_distributor;
/**
 * @author Dev Shah

 */
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.Config;
import lib.PageObjects;
import lib.SendEmail;
import lib.Utility;

public class Panel {
		private WebDriver driver;
		//private WebDriverWait w;
		Config c=new Config();
		PageObjects p = new PageObjects();
		
	/*@BeforeTest
	public void setUp(){
		
		//Select browser choice method
		driver=c.browserChoice();
		
		//Navigate to website
		try{
		driver.get(Config.baseUrl);
		}
		catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to the site");	
		}
		
		//maximize window
		driver.manage().window().maximize();
		
		//Login
		c.panelLogin(driver);
		
		//Call check image method
		c.checkImage(driver);
		
		//Set implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}
*/
	@Test(priority=1)
	public void panel(){
	
	//Check for page breaks
	Assert.assertEquals(driver.getTitle(), Config.title);
		
		//Print all modules existence and name
	//List<WebElement> list=driver.findElements(By.xpath(".//*[@class='floating-box']"));
	
	try{
		
	for(int i=1;i<=47;i++)
		{
			//Get text
			driver.findElement(By.xpath(".//*[@class='floating-box']["+i+"]")).getText();

			//Click
			driver.findElement(By.xpath(".//*[@class='floating-box']["+i+"]")).click();
			
			//Go back
			driver.navigate().back();
		}
	}
	catch(Exception e){
		org.testng.Assert.fail("Exception occured while verifying elements/modules are present on the panels page");	
	}
	
	//Assert the notice marquee exist
	driver.findElement(By.id(p.marquee_element)).isDisplayed();
	
	//Verify the change password link exist
	driver.findElement(By.linkText(p.changepassword_link)).isDisplayed();
	
	}
	
	
	
	@AfterMethod
	public void resultCheck(ITestResult result){
		if(ITestResult.FAILURE==result.getStatus())
		{
			Utility.captureScreenshot(driver,""+result.getName()+System.currentTimeMillis());
		}
		else{
			//DO NOTHING
		} 
		
	}
	
	@AfterTest
    public void tearDown(){
		System.out.println("END OF ------------PANEL TEST-----------");
	}

	/* @AfterSuite
	    public void tearDown(){
		
	    	driver.quit();
			
	    	SendEmail s=new SendEmail();
		s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");	        
	}
	*/
}
