package lib;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class Utility 
	{
	public static void captureScreenshot(WebDriver driver, String name)
		{
		
		try {
		
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		File source=ts.getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(source,new File("./Screenshots/"+name+".png"));
		
		System.out.println("Screenshot Captured..");
		}
		
		catch(Exception e ){
			System.out.println("exception while taking screenshot:"+e.getMessage());
							}
			
	}	
	}
