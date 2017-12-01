package pay1_distributor;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.Config;
import lib.JyperionListener;
import lib.PageObjects;
import lib.SendEmail;
import lib.Utility;


/**
 * @author Dev Shah
 */

public class Support {

	private WebDriver driver;
	private WebDriverWait w;

	Config c=new Config();
	PageObjects p = new PageObjects();
	
    @BeforeTest
	public void setUp(){
			
    	//Call browser specific method and instantiate driver 
    	driver=c.browserChoice();	
    	/*	
		//URL
    	try{
		driver.get(Config.baseUrl);
    	}	catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to the site");	
		}
		//maximize window
		driver.manage().window().maximize();
	
		//LOGIN1
		c.distLogin(driver);
		*/
		//Wait
		w=new WebDriverWait(driver,30);
	/*	w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Balance Transfer")));*/
		
		//Set implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		//Call image check method
		/*c.checkImage(driver);*/
		
		}
    
    @Test(priority=1, enabled=true)
	public void support(){
    	
    	try{
    	//Click on support link
    	//driver.findElement(By.linkText(p.support_link)).click();
    	driver.get(c.baseUrl+"/shops/bankDetails");
    	
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='appTitle alignCenter']")));
    	}
    	catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to support page");	
		}
    	
		//Cross check page title
		Assert.assertEquals(driver.getTitle(), Config.title);
		Assert.assertTrue(!driver.getCurrentUrl().contains("error"));
		Assert.assertTrue(!driver.getCurrentUrl().contains("404"));
		Assert.assertTrue(!driver.getCurrentUrl().contains("er404"));

		//Bank forms should be visible
		try{
		driver.findElement(By.xpath(".//*[@id='innerDiv']/form/fieldset[1]"));
		driver.findElement(By.xpath(".//*[@id='innerDiv']/form/fieldset[2]"));
		driver.findElement(By.xpath(".//*[@id='innerDiv']/form/fieldset[3]"));
		driver.findElement(By.xpath(".//*[@id='innerDiv']/form/fieldset[4]"));
		
		//All bank names are accurately displayed
		driver.findElement(By.xpath("//*[contains(text(),'State Bank of India')]")).isDisplayed();
		driver.findElement(By.xpath("//*[contains(text(),'ICICI')]")).isDisplayed();
		driver.findElement(By.xpath("//*[contains(text(),'Bank of Maharashtra')]")).isDisplayed();
		driver.findElement(By.xpath("//*[contains(text(),'Axis')]")).isDisplayed();
		}
		catch(Exception e){
			org.testng.Assert.fail("Exception occured while verifying all elements exist on Support page");	
		}
		//List<WebElement> l=driver.findElements(By.xpath(".//*[contains(text(),'Account Name')]/following::div[1]"));
	
	}
    
    @Test(priority=2, enabled=true)
	public void otherPages(){
    	
    	try{
    	//DISTRIBUTORS HELP DESK
    	driver.findElement(By.linkText("Distributors Help Desk")).click();
    	}	catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to Distributors Help Desk page");	
		}
    	
    	//Check page breaks
    	c.assertFunc(driver);

		Assert.assertEquals(driver.findElement(By.xpath(p.page_title)).getText(), "Distributor Help Desk");
		
		//Limit department no.
		try{
    	driver.findElement(By.linkText("Limit Department No.")).click();
		}catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to Limit Department No. page");	
		}
    	
		Assert.assertEquals(driver.getTitle(), Config.title);
		Assert.assertEquals(driver.findElement(By.xpath(p.page_title)).getText(), "Limit Department");

		//Customer Care
		try{
    	driver.findElement(By.linkText("Customer Care No.(For Retail Partners)")).click();
		}catch(Exception e){
			org.testng.Assert.fail("Exception occured while navigating to Customer Care page");	
		}
    	
		Assert.assertEquals(driver.getTitle(), Config.title);
		
		//Assert.assertEquals(driver.findElement(By.xpath("//div[@class='header']")).getText(), "Customer Support");

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
    public void aftertest(){
		driver.quit();
	}

	 /* @AfterSuite
	    public void tearDown(){
		  
			driver.findElement(By.xpath(p.footer)).isDisplayed();

			driver.quit();
			
	    	SendEmail s=new SendEmail();
	        s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");
	        
	        JyperionListener j = new JyperionListener();
	        j.closedoc();
	        
	        System.out.println("Cool!!!!!!!!");
	  }*/
}
