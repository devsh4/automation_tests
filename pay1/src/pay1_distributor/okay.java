package pay1_distributor;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class okay {	
	
	private static WebDriver driver;
	static double a		=	Math.random()*100;
	static double b 	=	Math.random()*100;
	
	public static void main(String[] args)
	{
		
		/*System.out.println(a+" and " +b);
		System.out.println(" ");*/

		driver= new FirefoxDriver();
		driver.get("http://www.moneycontrol.com");
		System.out.println("Stocks Opened!");
		
		
		/*sum(a,b);
		subtract(a,b);
		divide(a, b);
		multiply(a, b);*/
	}

	/*public static void sum(double x, double y ){
		
		//System.out.println("One");
		double sum = x+y;
		System.out.println(sum);
		System.out.println("");
	}
	
	
	public static void subtract(double x, double y){
		
		double sum; 
		//System.out.println("Two");
		if(x>y)
		{
			sum = x-y;
		}
		else{
			sum = y-x;
		}
		
		System.out.println(sum);
		System.out.println("");
	}
	
	public static void divide(double x, double y){
		
		//System.out.println("Two");
		
		double sum = x/y;
		System.out.println(sum);
		System.out.println("");
	}
	
	public static void multiply(double x, double y){
		
		//System.out.println("Two");
		
		double sum = x*y;
		System.out.println(sum);
	}*/

	
	
}
