package datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lib.WriteExcel;

public class getIQT {
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//System.out.println("In main method");
		Login();
		//Read();
	}
	
	public static void Login() throws InterruptedException
	{	
		driver=new ChromeDriver();
		driver.get("https://www.capitaliq.com");
		//driver.get("https://www.testciq.com");
		//driver.get("https://www.stagingciq.com");
		driver.manage().window().maximize();
		
		//Locators for test/staging :- username, password, myLoginButton
		driver.findElement(By.id("myLogin_myUsername")).sendKeys("devshah@capitaliq.com");
		driver.findElement(By.id("myLogin_myPassword")).sendKeys("P@ssword1");
		driver.findElement(By.id("myLogin_myLoginButton")).click();
		
		driver.navigate().to("https://www.capitaliq.com/CIQDotNet/Portfolio/PortfolioList.aspx");
		//driver.findElement(By.linkText("View All")).click();
		//Thread.sleep(3000);
	
		driver.findElement(By.id("_gv_ctl01_Icon")).click();
		Thread.sleep(5000);
		
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
		
		WriteExcel wr=new WriteExcel("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\New.xlsx","Sheet1");
		
		//ArrayList<String>  arr= new ArrayList();

		int counter=0;
		for(WebElement text:id){
			
			String str=text.getText();
			wr.writeData("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\New.xlsx","Sheet1", str, counter);	
			counter++;
		}
	
		System.out.println("Wrote IQT's in excel");
		
		driver.close();
		
	}
	
		

}
