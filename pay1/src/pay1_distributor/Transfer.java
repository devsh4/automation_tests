package pay1_distributor;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import lib.Functions;
import lib.Config;
import lib.PageObjects;
import lib.SendEmail;
import lib.Utility;


	public class Transfer	{
	
		private WebDriver driver;
		private WebDriverWait w;
	
		//
		String temp;
		String ret_bal;
		String post_ret_bal;
		String temp_txn_id;
		String temp_txn_id_new;
		boolean system_used;
	
		Config c=new Config();
		PageObjects p = new PageObjects();
	
	@BeforeSuite
	public void setUp(){
		
		//Select browser method call
		driver=c.browserChoice();
	
		//URL
		try{
		driver.get(Config.baseUrl);
		}
		catch(Exception e){
			org.testng.Assert.fail("Couldn't navigate to the URL provided");
		}
		
		//maximize window
		driver.manage().window().maximize();
		
		//Create Wait object 
		w=new WebDriverWait(driver,30);
		
		//LOGIN
		c.distLogin(driver);
		
		//Wait
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(p.transfer_link)));
		
		//Call check image function
		c.checkImage(driver);
		
		//Set implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		}
	
	@Test(priority=0,enabled=true)
	public void getRetailerBalance(){
		
		try{
			//Click on retailers list link
			driver.findElement(By.linkText(p.retList_link)).click();
			
			//Wait
			w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.filter)));	
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while navigating to retailers list page");	
		}
	
		//Assert page breaks, Calls assert function
		c.assertFunc(driver);
			
		try{
			//Select retailer
			String retailernum=c.retailer.substring(c.retailer.indexOf("-")+1, c.retailer.indexOf("-") + 11);

			Select s1=new Select(driver.findElement(By.id(p.retailer_filter_dropdown)));
			
			s1.selectByVisibleText("Test("+retailernum+")");
		//	s1.selectByVisibleText("("+retailernum+")");
			
			//Search
			driver.findElement(By.id(p.searchButton)).click();

		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while enforcing search criteria on retailers limit page");	
		}
	
	
		//Confirm balance
		try{
			ret_bal=driver.findElement(By.xpath(p.retlist_balance_element)).getText();
			//System.out.println(ret_bal);
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while extracting retailer balance");	
		}

	}
	
	//TO DO
	@Test(priority=1,enabled=true)
	public void transfer(){
		
		//Get distributor balance
		temp=driver.findElement(By.xpath(p.user_balance_element)).getText();
		
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
		driver.findElement(By.id(p.transfer_amount_element)).sendKeys(c.amount);
		
		//Enter retailer
		driver.findElement(By.id(p.transfer_number_element)).sendKeys(c.retailer);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(c.retailer)));
		
		driver.findElement(By.linkText(c.retailer)).click();
		
		//Select option 1-Cash, 2-NEFT,	3-ATM,	4-Cheque
		driver.findElement(By.xpath("//div[@class='fieldLabelSpace1']/input[@id='typeRadio']["+c.txnType+"]")).click();
		
		//Txn ID
		driver.findElement(By.id(p.txn_id)).sendKeys(c.txnid);
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
		
		//re enter amount
		try{
		driver.findElement(By.id(p.confirmamount_field)).sendKeys(c.amount);
		//amount 
		
		//Confirm
		driver.findElement(By.id(p.confirmtransaction_button)).click();
		
		//Messages
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.flashmessage)));
		
		}
		catch(Exception e){
			org.testng.Assert.fail("Error occured while CONFIRMING balance transfer request");		
		}
	
		//GET TRANSACTION ID
		
		String a=driver.findElement(By.xpath(p.flashmessage)).getText();
		temp_txn_id=a.substring(60);
		
		// Fetch actual ID
		driver.findElement(By.linkText(p.reports_link)).click();
		driver.findElement(By.linkText(p.salesmanreport_link)).click();
		
		//  
		String x=driver.findElement(By.xpath("//td[contains(text(),'"+temp_txn_id+"')]")).getText();
		temp_txn_id_new=x.substring(0, x.indexOf("/")-1);
		
	}
	
	//TO DO
	
	@Test(priority=2,enabled=true)
	public void retailerBalanceCheck(){
		//Confirm retailer balance
			try{
				//Go to retailer list
				driver.get(c.baseUrl+"/shops/allRetailer");
				
				//Wait
				w.until(ExpectedConditions.visibilityOfElementLocated(By.id(p.filter)));	
			}
			catch(Exception e){
				org.testng.Assert.fail("Error occured while navigating to retailers list page");	
			}
		
					try{
						String retailernum=c.retailer.substring(c.retailer.indexOf("-")+1, c.retailer.indexOf("-") + 11);
						
						Select s1=new Select(driver.findElement(By.id(p.retailer_filter_dropdown)));
						s1.selectByVisibleText("Test("+retailernum+")");
			
						//Search
						driver.findElement(By.id(p.searchButton)).click();		
					}
					catch(Exception e)
					{
						org.testng.Assert.fail("Error occured while enforcing search criteria on retailers limit page");	
					}
					
				
					//Confirm balance
					try{
						post_ret_bal=driver.findElement(By.xpath(p.retlist_balance_element)).getText();
						
						Assert.assertFalse(ret_bal.equals(post_ret_bal));
					}
					catch(Exception e){}
		}	
		
	
		//@Test(priority=3, enabled=true)
		public void checkRequestOnPanel()
		{
			String MainWindowHandle = driver.getWindowHandle();
		
			checkPanel();
			
			//SWITCH BACK
			driver.switchTo().window(MainWindowHandle);	

		}
		
		
			@Test(priority=4, enabled=true)
			public void lastTransactions(){
				
				//Balance transfer
				driver.get(c.baseUrl+"/shops/view");
				//driver.findElement(By.linkText("Balance Transfer")).click();
				
				if(driver.findElements(By.id(p.newsys_transfer_dropdown)).isEmpty())
				{
						//Old system
						system_used=false;
				}
				else{
						//New system
						system_used=true;
				}
				
				//Type retailer to get last transactions
				driver.findElement(By.id(p.transfer_number_element)).sendKeys(c.retailer);
				
				//Click on retailer
				driver.findElement(By.linkText(c.retailer)).click();
				
				//get elements
				String temp =driver.findElement(By.xpath(p.lasttransactions_latesttxn)).getText();
						
				if(system_used=false)
					{
						try{
						//True condition
							Assert.assertTrue(temp.equalsIgnoreCase(temp_txn_id));
							System.out.println("Transaction exists:-"+temp+" "+temp_txn_id);
						}
						catch(Exception e)
						{
							org.testng.Assert.fail("Transfer entry not present in the retailer's recent transactions.");
						}
					}
					else{
						try{
							//True condition
								Assert.assertTrue(temp.equalsIgnoreCase(temp_txn_id_new));
								System.out.println("Transaction exists:-"+temp+" "+temp_txn_id_new);
							}
							catch(Exception e)
							{
								org.testng.Assert.fail("Transfer entry not present in the retailer's recent transactions.");
							}
					}
			
				}			 
	
			@Test(priority=5,enabled=true)
			//BEFORE PULLBACK
			public void mainReport(){
				
				driver.get(c.baseUrl+"/shops/mainReport/1");
				
				String topup_sold_day= driver.findElement(By.xpath(p.topup_sold)).getText();
				
				if(c.amount.equals(topup_sold_day))
				{
					System.out.println("Main Report displaying values for topup sold/day correctly");
				}
				else{
					org.testng.Assert.fail("Main report not correctly displaying the top up sold/day as per latest transaction");
				}
			
				String topup_unique=driver.findElement(By.xpath(p.topup_unique)).getText();
				if(!topup_unique.equals("0"))
				{
					System.out.println("Main Report displaying values for unique topups correctly");
				}
				else{
					org.testng.Assert.fail("Main report fails to correctly displaying the unique topup count as per latest transaction");
				}
			}
	
			//TO DO
			
			@Test(priority=6,enabled=true)
			public void pullback(){
			
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
		
		//SEARCH TXN
		/*try{
			
		//Select required retailer
		driver.findElement(By.xpath(".//option[contains(text(),'"+c.retailer+"')]")).click();
		
		//Search
		driver.findElement(By.id("sub")).click();
		
		//String s1= s.substring(s.indexOf("(") + 1, s.indexOf(")"));

		//Verify retailer is displayed
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'"+c.retailer_num+"')]")));

		driver.findElement(By.xpath("//td[contains(text(),'"+temp_txn_id+"')]")).isDisplayed();
		}
		catch(Exception e)
		{
			org.testng.Assert.fail("Error occured while SEARCHING for retailer transaction");			
		}
		*/

				//Pullback
				//try{
				driver.findElement(By.xpath("//td[contains(text(),'"+temp_txn_id+"')]/following::td[12]/a")).click();
		
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
		
	/*		}
		catch(Exception e)
		{*/
			//org.testng.Assert.fail("Error occured while carrying out pullback request");			
		//}
			
			String temp1=driver.findElement(By.xpath(p.user_balance_element)).getText();
		
			//Check balance consistency and whether is back to the same balance
			Assert.assertEquals(temp1, temp);
	
			System.out.println("Balance is intact, transaction successful");
	}

			
		//TO-DO
		@Test(priority=7, enabled=true , dependsOnMethods = {"pullback"})
		public void accHistory()
		{
			try{
			//Navigate to acc history page
			driver.findElement(By.linkText(p.acchistory_link)).click();
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
				driver.findElement(By.xpath(".//td[contains(text(),'Pullback')][contains(text(),'"+temp_txn_id+"')]/following::td[2][contains(text(),'"+c.amount+"')]")).isDisplayed();
				
				//Compare opening and closing balance
				
				//Get opening
				String opening=driver.findElement(By.xpath(".//td[contains(text(),'"+temp_txn_id+"')]/following::td[1][contains(text(),'Topup')]/following::td[3]")).getText();
				
				//Get closing
				String closing1=driver.findElement(By.xpath("//td[contains(text(),'Pullback')][contains(text(),'"+temp_txn_id+"')]/following::td[4]")).getText();
				
				//Check consistency in balancetem
				Assert.assertEquals(opening, closing1, "Discrepancy in matching/asserting balance post pullback");
			}
			catch(Exception e)
			{
				org.testng.Assert.fail("Error while confirming pullback transaction");
			}
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
	
	
		public void checkPanel()
		{
			//NEW WINDOW
			WebDriver driver1=new FirefoxDriver();
			//test.log(Status.PASS, "Open new window");
		
			//Navigate
			driver1.get(c.baseUrl);
			//test.log(Status.PASS, "Navigate to cc");
		
			//Login
			c.panelLogin(driver1);
			
			WebDriverWait w=new WebDriverWait(driver1, 30);
			w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.panel_search_element)));
		
			//Click on search
			driver1.findElement(By.xpath(p.panel_search_element)).click();
		
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try{
			JavascriptExecutor js=(JavascriptExecutor)driver1;
			js.executeScript("scroll(0,300)");
		
		
			//Enter retailer number
			driver1.findElement(By.id(p.panel_number_element)).sendKeys(c.retailer_num);
			driver1.findElement(By.id(p.panel_number_element)).sendKeys(Keys.ENTER);

			//Scroll to element
			js.executeScript("scroll(0,450)");
		
			//Print timestamp of transfer
			driver1.findElement(By.xpath("//*[contains(text(),'Amount transferred')]/following::table[1]//tr[2]/td[2]")).isDisplayed();
			System.out.println(driver1.findElement(By.xpath("//*[contains(text(),'Amount transferred')]/following::table[1]//tr[2]/td[3]")).getText());
			
			String x=driver1.findElement(By.xpath("//th[contains(text(),'Balance')]/following::td[1]")).getText();
			x=x.substring(0, x.indexOf("."));
			//Confirm balance
			Assert.assertEquals(post_ret_bal, x);
			
			driver1.quit();
			}
			catch(Exception e)
			{
				driver1.quit();
			}	
			
		}
		
		/*@AfterTest
	    public void aftertest(){
			System.out.println("END OF ------------TRANSFER TEST-----------");
		}
		
		  @AfterSuite
		    public void tearDown(){
			  
			//driver.findElement(By.xpath(p.footer)).isDisplayed();
			driver.quit();
		    	SendEmail s=new SendEmail();
			s.sendPDFReportByGMail("sender_email_id", "pwd", "receiver_email_id", "PDF Report Test", "Testing");		     
		}*/
}

