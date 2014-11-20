package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Exceptiontesting {
  @Test
  public void runtest() {
	  
	  
	  WebDriver driver = new FirefoxDriver();
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	 
	  try {
		  
		   driver.navigate().to("http://qa4.hsn.com/");
		   driver.findElement(By.xpath("//*[@id='search-input']_")).sendKeys("hello");
	} catch (Exception e) {
		// TODO Auto-generated catch block
				  
		e.printStackTrace();
		System.out.println("need to close the driver");
		driver.quit();
	}
	  
	 
  }
}
