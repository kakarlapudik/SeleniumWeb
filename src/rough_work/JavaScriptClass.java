package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class JavaScriptClass {
  @Test
  public void f() {
	  
	  WebDriver driver = new FirefoxDriver();
	  driver.navigate().to("http://qa4.hsn.com/");
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  WebElement element = driver.findElement(By.xpath(".//*[@id='topnav-flexpay']/a"));
	  JavascriptExecutor executor = (JavascriptExecutor)driver;
	  executor.executeScript("arguments[0].click();", element);
  }
}
