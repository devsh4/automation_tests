package lib;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DemoTest {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		testone();
		
	}

	
	public	static	void	testone(){
		//System.setProperty("webdriver.chrome.driver", "./Dependencies/chromedriver.exe");
		WebDriver	driver	=	new	FirefoxDriver();
		
		driver.manage().window().maximize();
		
		driver.get("http://www.google.com");
		
		driver.get("http://www.facebook.com");

		driver.quit();
	}
}
