package compare;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import lib.ExcelDataConfig;

public class Eqlistings {

	static ExcelDataConfig ex=new ExcelDataConfig("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\Template.xlsx");
	
	static String uname=ex.getData("Variables", 3, 1);
	static String password=ex.getData("Variables", 5, 1);
	
	static String unamefield = ex.getData("Variables", 11, 1);
	static String passfield = ex.getData("Variables", 13, 1);
	static String login = ex.getData("Variables", 15, 1);
	static WebDriver driver;
	
	public static void main(String []args) throws Exception{
		
		compare();
		laststep();
	}
	
	public static void compare()throws Exception{
		
	/*	if(browserType.equalsIgnoreCase("Firefox")) {
	         driver = new FirefoxDriver();
	         }

	         else if (browserType.equalsIgnoreCase("Chrome")) { 

	          System.setProperty("webdriver.chrome.driver","C:\\Users\\Dev Shah\\Downloads\\chromedriver.exe");
	          driver = new ChromeDriver();        
	          }

	         else if (browserType.equalsIgnoreCase("IE")) { 

	              {System.setProperty("webdriver.ie.driver","C:\\Users\\Dev Shah\\Downloads\\IEDriverServer.exe");}
	              driver = new InternetExplorerDriver(); 
	       
	         }*/
	
		driver=new ChromeDriver();
		
		driver.get("https://www.capitaliq.com");

		driver.manage().window().maximize();
		
		//Pass values to login & password field
		driver.findElement(By.id(unamefield)).sendKeys(uname);
		driver.findElement(By.id(passfield)).sendKeys("P@ssword1");
		driver.findElement(By.id(login)).click();		
	
		System.out.println("Logged In");
		
		driver.get("https://www.capitaliq.com/CIQDotNet/Securities/EquityListingDetails.aspx?companyId=112350&tradingItemId=2621697&returnText=Equity+Listings&returnUrl=%2fCIQDotNet%2fSecurities%2fEquityListings.aspx%3fcompanyId%3d112350%26stateKey%3d8dbc0e011dbb4769a600842d60ebd032&currencyId=160");
		driver.get("https://www.capitaliq.com/CIQDotNet/Securities/EquityListingDetails.aspx?companyId=112350&tradingItemId=2621697&returnText=Equity+Listings&returnUrl=%2fCIQDotNet%2fSecurities%2fEquityListings.aspx%3fcompanyId%3d112350%26stateKey%3d8dbc0e011dbb4769a600842d60ebd032&currencyId=160");

		//HashMap<String, Double> quote=new HashMap<>();
		
		String[] prod = new String[20];
		
		int i=1;
				
		for(int j=0;j<prod.length;j++)
		{
		
		prod[j]=driver.findElement(By.xpath("//td[contains(text(),'Last')]/following::span["+i+"]")).getText();
		i++;
		}
				
		
		for(int k=0;k<prod.length;k++)
		{
			System.out.println("Prod values :"+prod[k]);
			System.out.println("");
		}
			
			
		//Get values from staging	
		driver.get("https://www.stagingciq.com");

		driver.findElement(By.id(unamefield)).sendKeys(uname);
		driver.findElement(By.id(passfield)).sendKeys("Login@12");
		driver.findElement(By.id(login)).click();		
		System.out.println("**************");
		
		driver.get("https://www.stagingciq.com/CIQDotNet/Securities/EquityListingDetails.aspx?companyId=112350&tradingItemId=2621697&returnText=Equity+Listings&returnUrl=%2fCIQDotNet%2fSecurities%2fEquityListings.aspx%3fcompanyId%3d112350%26stateKey%3d17dfac9c146f4012a69343d4c5fe72fc&currencyId=160");
		driver.get("https://www.stagingciq.com/CIQDotNet/Securities/EquityListingDetails.aspx?companyId=112350&tradingItemId=2621697&returnText=Equity+Listings&returnUrl=%2fCIQDotNet%2fSecurities%2fEquityListings.aspx%3fcompanyId%3d112350%26stateKey%3d17dfac9c146f4012a69343d4c5fe72fc&currencyId=160");

		String[] test = new String[20];
				
		int i0=1;
		for(int j=0;j<test.length;j++)
		{
		
		test[j]=driver.findElement(By.xpath("//td[contains(text(),'Last')]/following::span["+i0+"]")).getText();
		i0++;
		}
				
		for(int k=0;k<test.length;k++)
		{
			System.out.println("Staging values"+test[k]);
		}
		
		
		
	
	}

	
	public static void laststep(){
		System.out.println("Test completed.");	
		driver.quit();

	}
	
}
