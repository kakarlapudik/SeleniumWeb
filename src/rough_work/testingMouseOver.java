package rough_work;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class testingMouseOver {

	
  @Test
  public void f() throws InterruptedException {
	  FirefoxDriver driver;
	  driver = new FirefoxDriver();
	  driver.navigate().to("http://www.hsn.com/shop/hand-care/bs0039");
	  
	  //*[@id='template-product-grid']/div[@data-preview-url]
	  
		Actions action = new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id='template-product-grid']/div[@data-preview-url]")));
 		action.build().perform();
 		Thread.sleep(10000);
 		
 		//*[@class='preview']
 		
 		driver.findElement(By.xpath("//*[@class='preview']")).click();
  }

}
