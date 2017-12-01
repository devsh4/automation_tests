package lib;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utility {
	
	//No return
	public static void captureScreenshot(WebDriver driver,String screenshotName)
	{
		try 
		{
		TakesScreenshot ts=(TakesScreenshot)driver;	 
		File source=ts.getScreenshotAs(OutputType.FILE);
	 
		FileUtils.copyFile(source, new File("./Screenshots/"+screenshotName+".png"));
		System.out.println("Screenshot taken");
		
		//Reporter.log("<br> <img src=./Screenshots/"+screenshotName+".png /> <br>");
		} 
		catch (Exception e)
		{
		System.out.println("Exception while taking screenshot "+e.getMessage());
		}
		}
	

	//Return string
	public static String takeScreenShot(WebDriver driver,String screenShotName) throws Exception
	{

		/*Date now = new Date();
		DateFormat shortDf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

		String currentDate = shortDf.format(now).toString().replace("/", "_");
		currentDate = currentDate.toString().replace(" ", "_");
		currentDate = currentDate.toString().replace(":", "_");
*/
		TakesScreenshot ts = (TakesScreenshot)driver;
	
		File source = ts.getScreenshotAs(OutputType.FILE);
	
		String dest = "./Screenshots/"+screenShotName+".png";

		File destination = new File(dest);

		FileUtils.copyFile(source, destination);
		
		return dest;
	}
	
}