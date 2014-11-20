package rough_work;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.cookie.Cookie;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class GetMyCookie {
	
	@Test
	
	public void goForIt() throws IOException{
		
		
		
		Properties OR = new Properties();
		FileInputStream fs2 = new FileInputStream(System.getProperty("user.dir")+"//src//com//hsn//config//or.properties");
		OR.load(fs2);
		
		
		FirefoxDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.navigate().to("http://www.hsn.com");
		
		driver.findElementByXPath(OR.getProperty("HomePage_SignIn_Register_link")).click();
		driver.findElementByXPath(OR.getProperty("HomePage_username_inputField")).sendKeys("qahsntestaccount_1@hsn.com");
		driver.findElementByXPath(OR.getProperty("HomePage_password_inputField")).sendKeys("password123");
		driver.findElementByXPath(OR.getProperty("HomePage_SignInButton")).click();
		
		org.openqa.selenium.Cookie myCookie = driver.manage().getCookieNamed(".P");
		System.out.println("my custID is "+myCookie.getValue());
		
	}

}
