package pay1_distributor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.Config;
import lib.Functions;
import lib.PageObjects;
import lib.ReportCharts;
import lib.Utility;


/**
 * @author Dev Shah
 */
public class Reports {
		
	private WebDriver driver;
	private WebDriverWait w;

	//Create object
	Config c=new Config();
	PageObjects p=new PageObjects();
	Functions f	=	new Functions();
	
    @BeforeTest
	public void setUp(){
		
    	//Select browser function
    	driver=c.browserChoice();
    	w=new WebDriverWait(driver,30);
    	
		/*//URL for parallel execution
    	try{
		driver.get(Config.baseUrl);
    	}
    	catch(Exception e)
    	{
			org.testng.Assert.fail("Couldn't navigate to the URL provided");
    	}
    	
		//maximize window
		driver.manage().window().maximize();
	
		//LOGIN
		c.distLogin(driver);
		
		//Wait
		
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(p.transfer_link)));
		
		//Set implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		//Call image check method
		c.checkImage(driver);*/
		
		//Navigate to Reports
    	driver.get(c.baseUrl+"/shops/mainReport");
		}
    
    //@Test(priority=1,enabled=true)
    public void mainReport(){
    	
    	//Wait
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(p.mainreport_link)));
    	
    	//Navigate to Main Report
    	driver.findElement(By.linkText(p.mainreport_link)).click();
    	
    	//Assert page is not breaking
		c.assertFunc(driver);
		
    	//Verify all columns exist
    	driver.findElement(By.xpath("//*[contains(text(),'Today')]")).isDisplayed();
    	driver.findElement(By.xpath("//*[contains(text(),'Yesterday')]")).isDisplayed();
    	driver.findElement(By.xpath("//*[contains(text(),'Last 7 days')]")).isDisplayed();
    	driver.findElement(By.xpath("//*[contains(text(),'Last 30 days')]")).isDisplayed();
    	
    	//Wait until graph loads
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.mainreport_charts)));

    	//Verify all graphs are present
    	driver.findElement(By.xpath(p.mainreport_charts)).isDisplayed();
    	
    	//List
    	List<WebElement> e=	driver.findElements(By.xpath(p.mainreport_charts));
    	
    	//Assert size is 4, i.e. 4 charts exist for distributor
    	//Assert.assertEquals(4, e.size());	

    }
    
    
  //  @Test(priority=2, enabled=true)
    public void accountHistory(){    	
    	
    	try{
    	//Navigate to Reports
    	driver.findElement(By.linkText(p.acchistory_link)).click();
    	
    	//Wait
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.from_datepicker)));
    	}
    	catch(Exception e)
    	{
    		org.testng.Assert.fail("Issue occured while navigating to account history");
    	}
    	
    	//Call Page breaks method
    	c.assertFunc(driver);
		
    	//Store title name
    	String title_msg=driver.findElement(By.xpath(p.page_title)).getText();
    	
    	try{
    	//Navigate to date control
		driver.findElement(By.id(p.from_datepicker)).click();
		
		//Select today's date
		driver.findElement(By.xpath(p.todaydate_datepicker)).click();

		//Navigate to date control
		driver.findElement(By.id(p.to_datepicker)).click();
		
		//Select today's date
		driver.findElement(By.xpath(p.todaydate_datepicker)).click();
		
    	//Click on Search
    	driver.findElement(By.id(p.searchButton)).click();
    	}
    	catch(Exception e)
    	{
    		org.testng.Assert.fail("Issue occured while selecting date range");
    	}
    	//wait
    	w.until(ExpectedConditions.invisibilityOfElementLocated(By.id(p.loader_element)));	
    		
    	try{
    	//Verify if all table columns are displayed
    	driver.findElement(By.xpath(p.acchistory_txnid_column)).isDisplayed();
    	driver.findElement(By.xpath(p.acchistory_particulars_column)).isDisplayed();
    	driver.findElement(By.xpath(p.acchistory_debit_column)).isDisplayed();
    	driver.findElement(By.xpath(p.acchistory_credit_column)).isDisplayed();
    	driver.findElement(By.xpath(p.acchistory_opening_column)).isDisplayed();
    	driver.findElement(By.xpath(p.acchistory_closing_column)).isDisplayed();
    	}
    	catch(Exception e)
    	{
    		org.testng.Assert.fail("Issue occured while verifying presence of columns");	
    	}
    	
    	try{
    	//Rows presence check
    	driver.findElement(By.xpath(p.acchistory_rows)).isDisplayed();	
    	}catch(Exception e){
    		org.testng.Assert.fail("Error occured while viewing entries in account history");
    	}
    	
    	//To cross check the title is displaying the period along with the title.
    	String title_msg1=driver.findElement(By.xpath(p.page_title)).getText();
    	
    	//Assert the table is updated with entries
    	Assert.assertTrue("Page title not matching", !title_msg.equals(title_msg1));
    	
    	//Get last closing from latest record.
    	String closing_bal=driver.findElement(By.xpath(p.acchistory_closingbal)).getText();
    	String temp = driver.findElement(By.xpath(p.user_balance_element)).getText();  	
    	String current_bal = temp.substring(temp.indexOf(":")+2);
    	
    	
    	Assert.assertEquals("Balance not matching as per to latest acc record", current_bal, closing_bal);
    }
    
    
    @Test(priority=3, enabled=true)
    public void buyReport(){
    	    	
    	driver.findElement(By.linkText("Reports")).click();
    	
    	//Navigate to Reports
    	driver.findElement(By.linkText(p.buyreport_link)).click();

    	//Wait
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.from_datepicker)));
    	
    	//Call - Check page breaks function
    	c.assertFunc(driver);
		
    	f.handleDatePicker(driver, p.fromDate_choice1, p.toDate_choice1);
		
    	//Wait
    	//w.until(ExpectedConditions.visibilityOfElementLocated(By.id("loader2")));
    	w.until(ExpectedConditions.invisibilityOfElementLocated(By.id(p.loader_element)));
    	
    	//Check whether data/records exist or not and print accordingly
    	if(driver.findElements(By.xpath(p.noresults_message)).isEmpty())
    	{
    		System.out.println("Latest top ups are displayed");
    	}
    	else{
    		System.out.println("No Results Found");
    	}
    
    }
    
    
    @Test(priority=4, enabled=true)
    public void retailerSale(){
    	
    	//Navigate to retailerSale
    	driver.findElement(By.linkText(p.retailersalereport_link)).click();
    	
    	//Wait
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.page_title)));
    	
    	//Check page breaks
    	c.assertFunc(driver);
    	
    	//TO DO
    	driver.findElement(By.id(p.searchButton)).click();
    	
    	//Verify all columns exist
    
    	for(int i=1;i<=6;i++)
    	{
    		driver.findElement(By.xpath(".//*[@id='innerDiv']/fieldset/table[2]/tbody/tr[1]/td["+i+"]")).isDisplayed();
    	}
    	

    	for(int i=2;i<=6;i++)
    	{
    		System.out.println(driver.findElement(By.xpath(".//*[@id='innerDiv']/fieldset/table[2]/tbody/tr[2]/td["+i+"]")).getText());
    	}
    	
    	
    	//entries if any
    	
    }
    
    
   // @Test(priority=5, enabled=true)
    public void invoiceHistory(){
    	
    	//TO-DO
    	
    }
    
    //@Test(priority=6, enabled=true)
    public void salesmanReport(){
	
    //
    driver.findElement(By.linkText(p.salesmanreport_link)).click();
    
    //Check page breaks
	c.assertFunc(driver);
	
    //Select required retailer
	driver.findElement(By.xpath(".//option[contains(text(),'"+c.retailer_num+"')]"));
	
	//Search
	driver.findElement(By.id(p.searchButton));
	
	
	//Download old data
	driver.findElement(By.xpath("//*[@id='innerDiv']/div[1]/a")).click();
	
	//Check if any active transfer is present or not
	if(!driver.findElements(By.xpath(".//fieldset/span")).isEmpty())
	{
		//No active transfer present
		System.out.println("NO active transfer or transaction present");
	}
	else{
		//Active trans present so check if pullback link is present
		driver.findElement(By.linkText(p.pullback_link));
		System.out.println("Active transactions present");
		}
    }

    
   // @Test(priority=7, enabled=true)
    public void retailerTransactions(){
    	
    	//Navigate to retailerSale
    	driver.findElement(By.linkText(p.retailertransaction_link)).click();
    	
    	//Wait
    	w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.page_title)));
    	
    	//Check page breaks
    	c.assertFunc(driver);
    
    	//CHECK UI
    	driver.findElement(By.id("ret_id")).isDisplayed();
    	driver.findElement(By.id("opr_id")).isDisplayed();
    	driver.findElement(By.id("sub")).isDisplayed();

    	driver.findElement(By.xpath(".//b[contains(text(),'Date')]")).isDisplayed();
    	driver.findElement(By.xpath(".//b[contains(text(),'Number')]")).isDisplayed();
    	driver.findElement(By.xpath(".//b[contains(text(),'Operator')]")).isDisplayed();
    	driver.findElement(By.xpath(".//b[contains(text(),'Amount')]")).isDisplayed();
    	driver.findElement(By.xpath(".//b[contains(text(),'Status')]")).isDisplayed();
    	
    	driver.findElement(By.xpath(".//a[contains(text(),'Next')]")).isDisplayed();
    	
    	//TO DO AFTER DATA IS AVAILABLE
    	
        }
    
    
    //@Test(priority=8, enabled=true)
    public void checkChart(){
    	
    	ReportCharts rc=new ReportCharts();
    	rc.navigate(driver);
    	//rc.chart1(driver);
    	rc.chart2(driver);
    	rc.chart3(driver);
    	rc.chart4(driver);
    	rc.chart5(driver);
    	
    }

    
    @AfterMethod
	public void resultCheck(ITestResult result){
		if(ITestResult.FAILURE == result.getStatus())
		{
			Utility.captureScreenshot(driver,""+result.getName()+System.currentTimeMillis());
		}
		else{
			//DO NOTHING
		} 
		
	}
    
    /*   @AfterTest
    public void tearDown(){
		System.out.println("END OF ------------REPORTS TEST-----------");
		
		//Footer check
		driver.findElement(By.xpath(p.footer)).isDisplayed();
		
		driver.quit();
	}
 
   @AfterSuite
    public void tearDown(){
      		SendEmail s=new SendEmail();
		s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");        
 	}*/
}
