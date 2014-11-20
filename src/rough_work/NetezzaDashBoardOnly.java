package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class NetezzaDashBoardOnly {
	
  @Test(invocationCount = 2500 , threadPoolSize = 10 )
  public void dashboard() throws InterruptedException {
	    
	  
	    FirefoxDriver driver = new FirefoxDriver();
	    
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	    
	    driver.navigate().to("http://hsdlbow001:8080/BOE/OpenDocument/opendoc/openDocument.jsp?sIDType=CUID&iDocID=AZlnzM5xJxdErvKmtVrk3VI");
	    
	    Thread.sleep(5000);
	    
	    driver.findElementByXPath("//*[@id='_id0:logon:USERNAME']").sendKeys("dashboard");
	    
	    driver.findElementByXPath("//*[@id='_id0:logon:PASSWORD']").sendKeys("test123");
	    
	    long start = System.currentTimeMillis();
	    
	    driver.findElementByXPath("//*[@id='_id0:logon:logonButton']").click();
	    
	    long finish = System.currentTimeMillis();
	    long totalTime = finish - start; 
	    System.out.println("Total Time for page load - "+totalTime); 
	    
	    Thread.sleep(90000);
	    
	    driver.quit();
	    
	  }
}
