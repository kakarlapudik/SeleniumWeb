package rough_work;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class DatePicker {
	
	FirefoxDriver driver;
	
  @Test
  public void f() throws InterruptedException {
	  
	  driver = new FirefoxDriver();
	  driver.navigate().to("http://qa4.hsn.com/");
	  Thread.sleep(30000);
	  
	  driver.findElement(By.xpath("//*[@class='ui-datepicker-trigger']")).click();
	  System.out.println("i am done waiting ");
	  selectElementWithtext("//*[@id='ui-datepicker-div']//a[contains(@class,'ui-state')]","14");
	  
  }
  
  public WebElement selectElementWithtext (String xpath , String textValue) {
	  
	  
	  List<WebElement> allElements = driver.findElements(By.xpath(xpath));
	  
	  for(WebElement e : allElements){
		  
		  System.out.println(e.getText());
		  if(e.getText().contains(textValue.trim())){
			  e.click();
			  return e;
		  }
	  }
	  
	  return null;
	  
  }
  
  
}
