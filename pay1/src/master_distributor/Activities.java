package master_distributor;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.Config;
import lib.Functions;
import master_distributor.PageObjects_MD;
import lib.Utility;

/**
 * @author Dev Shah
 */

public class Activities {
	
	//Create config object
	Config c=new Config();
	
	//Create page objects object
	PageObjects_MD p = new PageObjects_MD();
	
	Functions f=new Functions();
	
	private WebDriver driver;
	private WebDriverWait w;

	@BeforeTest
	public void setUp(){
		
    	//Call browser specific method and instantiate driver 
    	driver=c.browserChoice();	
    	
    	//Wait 
    	w=new WebDriverWait(driver,30);
    	
    //	Wait
    	driver.findElement(By.linkText(p.activities_link)).click();
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(p.transfer_link)));

    	/*//PARALLEL EXECUTION
		try{
		driver.get(c.baseUrl);
		}
		catch(Exception e){
			org.testng.Assert.fail("Couldn't navigate to the URL provided");
		}
		
		//maximize window
		driver.manage().window().maximize();
		
		//Wait 
		w=new WebDriverWait(driver,30);
		
		//LOGIN
		c.distLogin(driver);
		
				
		//Call image check method
		c.checkImage(driver);*/
		
		}
	
	@Test(priority=0,enabled=true)
	public void activities()
	{
		//driver.findElement(By.linkText(p.activities_link)).click();
		
		//Verify correct login
		String temp=driver.findElement(By.xpath(p.distinfo_topright_element)).getText();

		String mobile=temp.substring(temp.indexOf("+") + 4, temp.indexOf("+") + 14);

		Assert.assertEquals(mobile, Config.userMobile);
		
		//Check page breaks
		c.assertFunc(driver);
		
		//try{
			//Verify all left links of activities tab are displayed
			driver.findElement(By.linkText(p.transfer_link)).isDisplayed();
			//driver.findElement(By.linkText("Salesman Collections")).isDisplayed();
			driver.findElement(By.linkText(p.dist_list_link)).isDisplayed();
			driver.findElement(By.linkText(p.kits_transfer_link)).isDisplayed();
			driver.findElement(By.linkText(p.createdist_link)).isDisplayed();
			driver.findElement(By.linkText(p.retailer_list_link)).isDisplayed();
			driver.findElement(By.linkText(p.createrm_link)).isDisplayed();
		
			System.out.println("All left links are present");
		/*}
		catch(Exception e){
			org.testng.Assert.fail("Error while displaying activities Left links");
		}*/
	}
	
	@Test(priority=1, enabled=true)
	public void kitsTransfer()
	{
		//Navigate
		driver.findElement(By.linkText(p.kits_transfer_link)).click();
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.amount_element)));
	
		//Enter data
		driver.findElement(By.id(p.amount_element)).sendKeys("10");
		
		f.handleDropdown(driver, p.kits_distributor_dropdown, "Balaji Communication - 1199");
	
		driver.findElement(By.id(p.kits_element)).sendKeys("2");
		
		//Send
		driver.findElement(By.id(p.searchButton)).click();
		
		//Confirm
	/*	w.until(ExpectedConditions.invisibilityOfElementLocated(By.id(p.searchButton)));
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.searchButton)));*/
		w.until(ExpectedConditions.textToBePresentInElement(By.xpath("//label[contains(text(),'Amount')]/following::div[1]"), "10"));
		driver.findElement(By.id(p.searchButton)).click();
		
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.kits_element)));
	
	}
	
	
	@Test(priority=2,enabled=true)
	public void dList(){		
		//Activities re-navigation
		try{
			//Navigate
			driver.findElement(By.linkText(p.dist_list_link)).click();
			
			//Wait
			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.page_title)));
			
			}
		catch(Exception e){
				org.testng.Assert.fail("Issue occured while re-navigating to Activities page.");
			}

		//Check page breaks
		c.assertFunc(driver);
		
		//Check all column headers
		List <WebElement> list = driver.findElements(By.xpath(p.dlist_column_headers));
		
		for(WebElement x:list)
		{
			x.isDisplayed();
			System.out.println(x.getText());
		}
		
		int[] num={1,300,500,700};
		int[] data={2,3,9,14};
		
		for(int i=0;i<=num.length-1;i++)
			for(int j=0;j<=data.length-1;j++)
			{
				{
					System.out.print(driver.findElement(By.xpath(".//tbody/tr["+num[i]+"]/td["+data[j]+"]")).getText());
				}
				System.out.println("");
				System.out.println("");
			}
		
		}
	
	
	@SuppressWarnings("deprecation")
	@Test(priority=3,enabled=true)
	public void retailerList(){
		//To-Do
		
		try{
		//Click on retailers list link
		driver.findElement(By.linkText(p.retailer_list_link)).click();
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.filter)));	
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while navigating to retailers list page");	
		}
		
		//Check page breaks
		c.assertFunc(driver);
		
		for(int i=1;i<=10;i++)
		{
			f.handleDropdown(driver, "id", i);
		
			driver.findElement(By.id(p.searchButton)).click();
		
			w.until(ExpectedConditions.visibilityOfElementLocated(By.id("count")));
		
			System.out.println("Total Retailers - "+driver.findElement(By.id("count")).getText());
			System.out.println("Total Balance - "+driver.findElement(By.xpath(".//tfoot/tr/td[4]")).getText());
		}	
		
		/*	
		try{
		//Filter
		Select s2=new Select(driver.findElement(By.id(p.filter)));
		s2.selectByVisibleText("Top Transacting in last 7 days");
		
		//Display retailer count acc to filter
		w.until(ExpectedConditions.textToBePresentInElement(By.id(p.totalRetailers_count),"Top Transacting in last 7 days"));
		System.out.println(driver.findElement(By.id(p.totalRetailers_count)).getText());
		
		//Select retailer
		Select s1=new Select(driver.findElement(By.id(p.retailer_filter_dropdown)));
		s1.selectByIndex(1);

		//Search
		driver.findElement(By.id(p.searchButton)).click();		
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while enforcing search criteria on retailers limit page");	
		}
*/		//wait
		//w.until(ExpectedConditions.presenceOfElementLocated(By.id(p.totalRetailers_count)));
		
		//Display retailer count 
		//System.out.println(driver.findElement(By.id(p.totalRetailers_count)).getText());
		
		//Get all column header elements
		
		//List<WebElement> li= driver.findElements(By.xpath(".//tr[@id='head']/th"));
		
		try{
		//Verify all columns and its headers
		for(int i=1;i<=9;i++)
		{
			driver.findElement(By.xpath(".//tr[@id='head']/th["+i+"]")).isDisplayed();
			System.out.println(driver.findElement(By.xpath(".//tr[@id='head']/th["+i+"]")).getText());
		}
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while verifying all columns exist on the retailers list page");	
		}
		
	}
	

	@Test(priority=4,enabled=true)
	public void createRm(){
		
		//To-Do
		try{
		driver.findElement(By.linkText(p.createrm_link)).click();
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while navigating to the deleted retailer page");	
		}
		
		//Check page breaks
		c.assertFunc(driver);
		
		//Fill in details
		driver.findElement(By.id(p.rm_name)).sendKeys("Testing");
		driver.findElement(By.id(p.rm_number)).sendKeys("7101000699");
		
		//Go ahead
		driver.findElement(By.id(p.searchButton)).click();

		w.until(ExpectedConditions.textToBePresentInElement(By.xpath(p.rm_name_confirm_field), "Testing"));
		
		//Confirm
		driver.findElement(By.id(p.searchButton)).click();
		
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.rm_name)));
	
	}
	
	
	@AfterMethod
	public void resultCheck(ITestResult result){
		if(ITestResult.FAILURE==result.getStatus())
			{
			Utility.captureScreenshot(driver,""+result.getName());
			}
		else{
			//DO NOTHING
			} 
		
		}
	
	/*@AfterTest
    public void tearDown(){
		System.out.println("END OF ------------TRANSFER TEST-----------");
		driver.quit();
	}

	@AfterSuite
    public void tearDown(){
    		SendEmail s=new SendEmail();
 		s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");
        }*/
	
	
	}
