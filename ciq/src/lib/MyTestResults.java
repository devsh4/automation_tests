package lib;

public class MyTestResults {

	int passed;
	int failed;
	int skipped;
	
	public int getPassed(){
		return this.passed;
	}
	
	public int failed(){
		return this.failed;
	}
	
	public int skipped(){
		return this.skipped;
	}
	
	public int totalTests(){
		return (this.skipped + this.passed + this.failed);
	}
	
	
	
}
