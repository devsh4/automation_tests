package lib;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	private static ExtentReports extent;
	private static ExtentTest	test;
	private static ExtentHtmlReporter htmlReporter;
	public static String filePath="D:\\ExtentReport\\Report.html";
	
	public static ExtentReports getExtent(){
		
		//Create object
		extent = new ExtentReports();
		
		//Attach html reporter
		extent.attachReporter(getHtmlReporter());
		
		//Return
		return extent;
	}
	
	
	public static ExtentHtmlReporter getHtmlReporter()
	{
		//Create object
		htmlReporter = new ExtentHtmlReporter(filePath);

		//Show charts in report
		htmlReporter.config().getChartVisibilityOnOpen();
		htmlReporter.config().setDocumentTitle("Automation report");
		htmlReporter.config().setReportName("Demo Test");
		
		//return
		return htmlReporter;	
	}
	
	
	public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}


}
