package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class NewTest {
  @Test
  public void f() throws InterruptedException {
	  
	  FirefoxDriver driver = new FirefoxDriver();
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.navigate().to("http://qa4.hsn.com/");
	  
	  Thread.sleep(4000);
	 	  
	  driver.findElement(By.xpath("//*[@id='connect-web-links']//div[@class='socialRoll'][1]/a/img")).click();
	  
	  Thread.sleep(10000);
	  
	  driver.navigate().back();
	  
	  Thread.sleep(2000);
	 
  }
}
