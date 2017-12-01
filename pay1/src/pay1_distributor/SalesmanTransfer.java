
package pay1_distributor;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class SalesmanTransfer {
	private WebDriver driver;
	private WebDriverWait w;
	
	//
	String temp;
	String s_bal;
	String post_s_bal;
	String temp_txn_id;
	
	Config c = new Config();
	PageObjects p = new PageObjects();
	
	@BeforeTest
	public void setUp(){
		
		//Select browser method call
		driver=c.browserChoice();
	
	/*	//URL
		try{
		driver.get(Config.baseUrl);
		}
		catch(Exception e){
			org.testng.Assert.fail("Couldn't navigate to the URL provided");
		}
		
		//maximize window
		driver.manage().window().maximize();
		*/
		//Create Wait object 
		w=new WebDriverWait(driver,30);
		/*
		//LOGIN
		c.distLogin(driver);
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Balance Transfer")));
		
		//Call check image function
		c.checkImage(driver);
		*/
		//Set implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		//Get distributor balance
		temp=driver.findElement(By.xpath(p.user_balance_element)).getText();	
		
		driver.findElement(By.linkText(p.activities_link)).click();
		}
	
	
	@Test(priority=0,enabled=true)
	public void getSalesmanBalance(){
		
		try{
			//Click on retailers list link
			driver.findElement(By.linkText(p.salesmenList_link)).click();
			
			//Wait
			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.page_title)));	
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while navigating to salesmen list page");	
		}
	
		//Assert page breaks, Calls assert function
		c.assertFunc(driver);
			
		try{
			//Get salesman balance			
			s_bal = driver.findElement(By.xpath(".//td[contains(text(),'"+c.salesman_num+"')]/following::td[2]")).getText();
			//s_bal = temp.substring(0, 2);
			
			//Print
			//System.out.println(s_bal);
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while extracting salesman balance");	
		}

	}

	@Test(priority=1,enabled=true)
	public void stransfer(){
	
		try{
			//Navigate to balance transfer
			driver.findElement(By.linkText(p.transfer_link)).click();
			
			//Wait
			w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.transfer_amount_element)));
			
			}
		catch(Exception e){
				org.testng.Assert.fail("Issue occured while navigating to Balance Transfer page.");
			}

		try{
		
		//Enter amount
		driver.findElement(By.id(p.transfer_amount_element)).sendKeys(c.s_amount);
		
		Select s=new Select(driver.findElement(By.id(p.transfer_type)));
		s.selectByVisibleText("Salesman");
		
		//Enter salesman
		driver.findElement(By.id(p.transfer_number_element)).sendKeys(c.salesman_num);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(c.salesman_num)));
		
		driver.findElement(By.partialLinkText(c.salesman_num)).click();
		
		//Select option 1-Cash, 2-NEFT,	3-ATM,	4-Cheque
		driver.findElement(By.xpath("//div[@class='fieldLabelSpace1']/input[@id='typeRadio']["+c.s_txnType+"]")).click();
		
		//Txn ID
		driver.findElement(By.id(p.txn_id)).sendKeys(c.s_txnid);
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Issue occured while entering balance transfer details");	
		}
		
		try{
		//Confirm last transactions is displayed
		driver.findElement(By.xpath(p.lasttransactions)).isDisplayed();
		}
		catch(Exception e){
			org.testng.Assert.fail("Issue occured while viewing last transactions element");	
		}
		
		if(!driver.findElements(By.xpath(p.lasttransactions_header)).isEmpty())
		{
			Assert.assertTrue(!driver.findElements(By.xpath(p.lasttransactions_rows)).isEmpty());
		}
		else{	
		//To do
			Assert.assertTrue(driver.findElements(By.xpath(p.lasttransactions_rows)).isEmpty());
		}
		
		try{
		//Submit
		driver.findElement(By.id(p.searchButton)).click();
		
		//wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.confirmtransaction_button)));
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while submitting balance transfer request");	

		}
		
		//Confirm TRANSFER
		try{
		//re enter amount
		driver.findElement(By.id(p.confirmamount_field)).sendKeys(c.s_amount);
		
		//Confirm
		driver.findElement(By.id(p.confirmtransaction_button)).click();
		
		//Messages
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.flashmessage)));
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while CONFIRMING balance transfer request");		
		}	
		
		String a=driver.findElement(By.xpath(p.flashmessage)).getText();
		
		temp_txn_id=a.substring(60);
		
		String temp1=driver.findElement(By.xpath(p.user_balance_element)).getText();
		//Check balance consistency and whether is back to the same balance
		
		if(!temp1.equals(temp))
		{
			//System.out.println("All good");
		}
		else
		{
			org.testng.Assert.fail("Error in updating balance post transfer");
		}
	}
	
	@Test(priority=2,enabled=true)
	public void verifySalesmanBalance(){
		//Confirm retailer balance
			try{
				//Click on retailers list link
				driver.findElement(By.linkText(p.salesmenList_link)).click();
				
				//Wait
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.page_title)));	
			}
			catch(Exception e){
				org.testng.Assert.fail("Error occured while navigating to salesman list page");	
			}
					//Confirm balance	
						post_s_bal=driver.findElement(By.xpath("//td[contains(text(),'"+c.salesman_num+"')]/following::td[2]")).getText();
								
						//post_s_bal=temp.substring(0, 2);
						if(s_bal.equals(post_s_bal)){
							//Fail Test case
							org.testng.Assert.fail("Salesman balance not reflecting post transaction or balance transfer");
						}
						else{
							//System.out.println("Successful Transfer");
						}
		
	}
	
	
		@Test(priority=3, enabled=true)
		public void lastTransactions(){
		
		//Balance transfer
		driver.findElement(By.linkText(p.transfer_link)).click();
		
		//Select salesman
		Select s=new Select(driver.findElement(By.id(p.transfer_type)));
		s.selectByVisibleText("Salesman");
		
		//Type salesman to get last transactions
		driver.findElement(By.id(p.transfer_number_element)).sendKeys(c.salesman_num);
		
		//Click on salesman
		driver.findElement(By.partialLinkText(c.salesman_num)).click();
		
		//get elements
		String temp =driver.findElement(By.xpath(p.lasttransactions_latesttxn)).getText();
		
		///////////////////////////***********************************************************************************
		/////////////******************************** TO DO 
	
			if(temp.equalsIgnoreCase(temp_txn_id))
			{
				System.out.println("Showing up in recent transactions");
			}
			else{
				org.testng.Assert.fail("Transfer entry not present in the salesman's recent transactions.");
			}
	
		}			
		
		@Test(priority=4,enabled=true)
		//BEFORE PULLBACK
		public void mainReport(){
			
		driver.get(Config.baseUrl+"/shops/mainReport/1");
	
		String topup_sold_day= driver.findElement(By.xpath(p.topup_sold)).getText();
		
		if(!c.s_amount.equals(topup_sold_day))
		{
			System.out.println("Main Report displaying values for topup sold/day correctly");
		}
		else{
			org.testng.Assert.fail("Main report not correctly displaying the top up sold/day as per latest transaction");
		}
	
		String topup_unique=driver.findElement(By.xpath(p.topup_unique)).getText();
		if(topup_unique.equals("0"))
		{
			System.out.println("Main Report displaying values for unique topups correctly");
		}
		else{
			org.testng.Assert.fail("Main report fails to correctly displaying the unique topup count as per latest transaction");
		}
		}
	
	
		@Test(priority=5, enabled=true, dependsOnMethods={"stransfer"})
		public void spullback(){
			
		//PULLBACK FLOW
		try{
		//Navigate to reports
		driver.findElement(By.linkText(p.reports_link)).click();
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(p.salesmanreport_link)));

		//Go to salesman report
		driver.findElement(By.linkText(p.salesmanreport_link)).click();
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while navigating to REPORTS tab");			
		}
		
		//SEARCH TXNs
		try{
			
		//Select required salesman
		driver.findElement(By.xpath("//td[contains(text(),'"+temp_txn_id+"')]/following::td[12]/a")).click();
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while SEARCHING for salesman transaction");			
		}
		
		try{
		w.until(ExpectedConditions.alertIsPresent());
		
		//Handle two alerts
		Alert a=driver.switchTo().alert();
		a.accept();
		
		w.until(ExpectedConditions.alertIsPresent());
		a.accept();
		
		//Switch to window
		driver.switchTo().defaultContent();
		
		//Refresh page
		driver.navigate().refresh();
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.pullback_message)));
		
		//Assert pullback is successful
		Assert.assertTrue(driver.findElement(By.xpath(p.pullback_message)).isDisplayed());
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while completing pullback request");			
		}
		
		String temp1=driver.findElement(By.xpath(p.user_balance_element)).getText();
		
		//Check balance consistency and whether is back to the same balance
		Assert.assertEquals(temp1, temp);
	
		System.out.println("Balance is intact, transaction successful");
	}
		
		
		//TO-DO

		@Test(priority=6, enabled=true, dependsOnMethods={"spullback"})
		public void accHistory1()
		{
			try{
			//Navigate to acc history page
			driver.get(Config.baseUrl+"/shops/accountHistory/1");
			}
			catch(Exception e){
				org.testng.Assert.fail("Error while navigating to the page");
			}
			
			//SET TODAY'S DATE
			try{
			//Navigate to date control
			driver.findElement(By.id(p.from_datepicker)).click();
			
			//Select today's date
			driver.findElement(By.xpath(p.todaydate_datepicker)).click();

			//Navigate to date control
			driver.findElement(By.id(p.to_datepicker)).click();
			
			//Select today's date
			driver.findElement(By.xpath(p.todaydate_datepicker)).click();
			
			//Search
			driver.findElement(By.id(p.searchButton)).click();
			
			}
			catch(Exception e1)
			{
				org.testng.Assert.fail("Error while searching for transaction");
			}
			
			
			//CONFIRM ENTRY
			try{
			
				//Check transaction entry
				driver.findElement(By.xpath(".//td[contains(text(),'"+temp_txn_id+"')]/following::td[1][contains(text(),'Topup')]")).isDisplayed();
				
				//Check pullback entry
				driver.findElement(By.xpath(".//td[contains(text(),'Pullback')][contains(text(),'"+temp_txn_id+"')]/following::td[2][contains(text(),'"+c.s_amount+"')]")).isDisplayed();
				
				//Compare opening and closing balance
				
				//Get opening
				String debit=driver.findElement(By.xpath(".//td[contains(text(),'"+temp_txn_id+"')]/following::td[1][contains(text(),'Topup')]/following::td[1]")).getText();
				
				//Get closing
				String credit=driver.findElement(By.xpath("//td[contains(text(),'Pullback')][contains(text(),'"+temp_txn_id+"')]/following::td[2]")).getText();
				
				//Check consistency in balancetem
				Assert.assertEquals(debit, credit, "Discrepancy while matching balances post pullback");
			}
			catch(Exception e)
			{
				org.testng.Assert.fail("Error while confirming pullback transaction");
			}
	}
		
		@Test(priority=7, enabled=true)
		public void salesman_listchanges(){
			//Navigate
			driver.get(Config.baseUrl+"/shops/salesmanListing/");
			
			//Verify salesman collecton is removed
			Assert.assertTrue(driver.findElements(By.linkText(p.salesmanCollections_link)).isEmpty());
		
			//Edit salesman
			driver.findElement(By.xpath(".//td[contains(text(),'"+c.salesman_num+"')]/following::td[3]/a")).click();
			
			//Wait
			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='smMobile']")));
			
			//Check limit field is disappeared.
			Assert.assertTrue(driver.findElements(By.xpath(p.salesman_limit_field)).isEmpty());
			
			////TO DO
			
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
		
	/*@AfterTest
	  public void tearDown(){
			System.out.println("END OF ------------SALESMAN TRANSFER TEST-----------");
	   }*/
	
	/* @AfterSuite
	   public void tearDown(){	
			driver.quit();
	    		SendEmail s=new SendEmail();
	        	s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");
	        }*/
}

