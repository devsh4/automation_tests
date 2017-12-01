package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//Unfinished file
public class BalanceTransferPage {
	
	@FindBy(id = balanceTransferField_ID)
    private WebElement balanceTransferField;
	
	@FindBy(id = typeDropdown_ID)
    private WebElement typeDropdown;
	
	
	private final static String balanceTransferField_ID = "amount";
	private final static String typeDropdown_ID = "label";
	
	

}
