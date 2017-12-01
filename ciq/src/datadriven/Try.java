package datadriven;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lib.WriteExcel;

public class Try {
		static WebDriver driver;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		one();
		//two();
		three();
		four();
		//five();
		//six();
	
	}
	
	public static void one() throws InterruptedException
	{

		driver=new ChromeDriver();
		driver.get("https://www.capitaliq.com");

		driver.manage().window().maximize();
		
		driver.findElement(By.id("myLogin_myUsername")).sendKeys("devshah@capitaliq.com");
		driver.findElement(By.id("myLogin_myPassword")).sendKeys("P@ssword1");
		driver.findElement(By.id("myLogin_myLoginButton")).click();
		
		driver.navigate().to("https://www.capitaliq.com/CIQDotNet/Portfolio/PortfolioList.aspx");
	
		driver.findElement(By.xpath("//*[@id='_portfolioFilter_portfolioTabSection__allportfolios']")).click();
	
		Thread.sleep(3000);
	
		driver.findElement(By.linkText(">")).click();
	
		Thread.sleep(3000);
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(4000);
		
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(4000);
		
		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(4000);

		driver.findElement(By.linkText(">")).click();
		
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();

		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
		
		//int counter=0;
		for(WebElement text:id){
			
			System.out.println(text.getText());
			//String str=text.getText();
			//wr.writeData("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\New.xlsx","Sheet1", str, counter);	
			//counter++;
		
						}
	}

	public static void two() throws InterruptedException
	{
		//driver.findElement(By.linkText("26-50")).click();
		driver.findElement(By.linkText("1,026-1,050")).click();

		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();
		
		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
	
		//int counter=0;
		for(WebElement text:id){
			
			System.out.println(text.getText());
			
					}
	}


	public static void three() throws InterruptedException
	{
		driver.findElement(By.linkText("1,151-1,175")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();

		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
	
		//WriteExcel wr=new WriteExcel("C:\\Users\\Dev Shah\\Downloads\\Selenium\\Files\\New.xlsx","Sheet1");
		
		//int counter=0;
		for(WebElement text:id){
			
			System.out.println(text.getText());
			
							}
	}
	
	public static void four() throws InterruptedException
	{
		driver.findElement(By.linkText("1,176-1,200")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();

		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
		
		for(WebElement text:id){
			
			System.out.println(text.getText());
			}
	}
	public static void five() throws InterruptedException
	{
		driver.findElement(By.linkText("1,101-1,125")).click();
		
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();

		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
		
		for(WebElement text:id){
			
			System.out.println(text.getText());
			}
	}
	
	public static void six() throws InterruptedException
	{
		driver.findElement(By.linkText("926-950")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//*[@id='_gv_ctl01_Icon']")).click();

		Thread.sleep(8000);
		
		List<WebElement> id=driver.findElements(By.id("ctl00_Label2"));
		
		for(WebElement text:id){
			
			System.out.println(text.getText());
			}
	}
	
}	
		


