package rough_work;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class Emailtest {
  @Test
  public void f() throws InterruptedException {
	  
	   System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		//caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);  
		//caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
		caps.setCapability("ignoreZoomSetting", true);
		WebDriver driver = new InternetExplorerDriver(caps);
		
	  
	  driver.navigate().to("http://hotmail.com/"); 
	  
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	  System.out.println("found elemets count "+driver.findElements(By.xpath("//*[@id='idDiv_PWD_UsernameExample']")).size());
	  driver.findElement(By.xpath("//*[@id='idDiv_PWD_UsernameExample']")).sendKeys("hsn_test@hotmail.com");
	  Thread.sleep(5000);
	  System.out.println("found elemets count "+driver.findElements(By.xpath("//*[@id='idDiv_PWD_PasswordExample']")).size());
	  driver.findElement(By.xpath("//*[@id='idDiv_PWD_PasswordExample']")).click();
	  driver.findElement(By.xpath("//*[@id='idDiv_PWD_PasswordExample']")).sendKeys("Stpete13");
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//*[@id='idSIButton9']")).click();
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//*[@id='c_search_psb_box']")).click();
	  driver.findElement(By.xpath("//*[@id='c_search_psb_box']")).sendKeys("304154");
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//*[@id='c_search_psb_go']")).click();
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//*[@class='InboxTableBody MlSrch']//li[1]")).click();
	  
	  String itemIdTest = driver.findElement(By.xpath("//*[contains(text(),'Item: ')]")).getText();
	  
	  System.out.println("my item is"+itemIdTest);
	  
	  Assert.assertFalse("I am happy i found it", itemIdTest.equals("304154"));

 
}
  }
