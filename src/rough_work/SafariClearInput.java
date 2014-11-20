package rough_work;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

public class SafariClearInput {
  @Test
  public void f() throws InterruptedException {
	  
	  
	  SafariDriver driver = new SafariDriver();
	  
	  driver.navigate().to("http://qa1.hsn.com");
	  
	  driver.findElement(By.xpath("//*[@id='search-input']")).sendKeys("junk");
	  
	  Thread.sleep(5000);
	  
	  driver.findElement(By.xpath("//*[@id='search-input']")).sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END)," ");
	  System.out.println("safari clean input field");
	  driver.findElement(By.xpath("//*[@id='search-input']")).sendKeys("suprt");
	  
  }
}
