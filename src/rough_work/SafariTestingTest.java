package rough_work;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

import com.hsn.util.PowerShellCommandExecutor;

public class SafariTestingTest {

  @Test
  public void main() throws Exception {
	  
	for (int i = 0; i < 5;i++){  
		
		WebDriver driver = new SafariDriver();
		driver.navigate().to("http://qa1.hsn.com/");
		//throw new RuntimeException("Test not implemented");
		driver.quit();
		PowerShellCommandExecutor.executePSCommand("Stop-Process -processname chromedriver*");
		PowerShellCommandExecutor.executePSCommand("Stop-Process -processname IEDriverServer*");
		PowerShellCommandExecutor.executePSCommand("Stop-Process -processname Safari*");
	}
  }
}
