package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

public class MobileLogin {
  @Test
  public void f() throws InterruptedException {
	  
	  WebDriver driver = new SafariDriver();
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.navigate().to("http://mqa2.hsn.com/m/preview");
	  driver.findElement(By.xpath("//*[@id='Form_Submit']")).click();
	  driver.findElement(By.xpath("//*[@id='myHSNDropdown']")).click();
	  driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
	  
	  // input user name to //*[@id='txtUserEmail']
	  driver.findElement(By.xpath("//*[@id='txtUserEmail']")).sendKeys("ovc140202011231@qa1.com");
	  
	  // input password to //*[@id='txtUserPwd']
	  driver.findElement(By.xpath("//*[@id='txtUserPwd']")).sendKeys("password123");
	  
	   // select signin button  //*[@id='btnSignIn']
	  driver.findElement(By.xpath("//*[@id='btnSignIn']")).click();
	  
	  //search for 6554721 in serch field in //*[@id='txtSearch']
	  
	  Thread.sleep(5000);

	  driver.findElement(By.xpath("//*[@id='txtSearch']")).sendKeys("6554721");
	  
	  Thread.sleep(3000);
	  
	  driver.findElement(By.xpath("//*[@id='btnSearch']")).click();
	  
	  String prodDescription = driver.findElement(By.xpath("//*[@id='H1productName']/span")).getText();
	  
	  System.out.println("the captured prod was"+prodDescription);
	  
	  
	
	  
	
	  
	  
  }
}
