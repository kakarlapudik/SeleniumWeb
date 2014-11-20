package rough_work;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

public class PropertiesUsage {
  @Test
  public void f() throws IOException {
	  
	  
	    
		Properties CONFIG = new Properties();
		FileInputStream fs1 = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//config.properties");
		CONFIG.load(fs1);
		
		String env = CONFIG.getProperty("ENV");
		
		Properties OR = new Properties();
		FileInputStream fs2 = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//or.properties");
		OR.load(fs2);
		
		System.out.println("the xpath for JustAddedPage_Checkoutis "+ OR.getProperty("JustAddedPage_Checkout"));
		System.out.println("the env is "+ env);
  }
}
