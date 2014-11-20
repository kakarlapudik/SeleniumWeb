package rough_work;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TextValidation {
  @Test
  public void f() {
	  
	  WebDriver driver = new FirefoxDriver();
	  driver.navigate().to("http://172.21.2.183/status");
	  System.out.println(driver.getPageSource().toString()); 
	  System.out.println(" checking " + driver.getPageSource().contains("OKSTATUS")); 

  }
}
