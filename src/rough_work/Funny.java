package rough_work;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;

public class Funny {

	public Funny() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		FirefoxDriver driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
		
		
		driver.navigate().to("http://qa1.hsn.com/arcade/today-special-jigsaw/28");
		
		WebElement element = driver.findElement(By.xpath("//*[@id='flash_game']"));
		
		Actions action = new Actions(driver);
		
		Thread.sleep(1000);
		
		action.moveToElement(element, 578, 440);
		
		Thread.sleep(1000);
		
		action.click();
		
		Thread.sleep(1000);
		
		action.build();
		action.perform();
		
	}

}
