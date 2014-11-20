package rough_work;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({SearchPageToDemonstrateFail.class})

public class SearchPageToDemonstrateFail extends TestListenerAdapter{
      public String baseurl="http://www.sears.com/shc/s/CountryChooserView?storeId=10153&catalogId=12605";
      public static WebDriver driver;
      public int Count = 0;

    @BeforeTest 
      public void searchPage_openBrowser() {

          driver = new FirefoxDriver();
          driver.manage().deleteAllCookies();
          driver.get(baseurl);
      }
@Test
public void searchPage_validations_Failure() throws InterruptedException{
          driver.findElement(By.id("intselect")).sendKeys("India");
        driver.findElement(By.xpath(".//*[@id='countryChooser']/a/img")).click();
        driver.findElement(By.xpath(".//*[@id='keyword']")).sendKeys("shirts");
        driver.findElement(By.xpath(".//*[@id='goBtn']")).click();
        driver.findElement(By.xpath(".//*[@id='sortOptions']"));
        System.out.println("Recommend dropdown");

        try {
              WebElement e = driver.findElement(By.xpath(".//*[@id='content']/div[3]/div[6]/div[2]/div)[2]"));
              Assert.assertTrue(e.isDisplayed());
              String strng = e.getText();
              System.out.println(strng);
              Assert.assertEquals("Relevance", strng);
              System.out.println("Relevance value selected in sortby dropdown");
            } 
            catch(NoSuchElementException n) {
               System.out.println("Relevance value not selected in sortby dropdown");
            } catch(AssertionError a) {
               System.out.println("Asser Error:Relevance value not selected in sortby dropdown");
            }

        driver.findElement(By.xpath(".//*[@id='resultsView']/span"));
        System.out.println("View Text");
        driver.findElement(By.xpath(".//*[@id='resultsNxtBtn']"));
        System.out.println("Next page link");


        //General Validations
        driver.findElement(By.xpath(".//*[@id='changeCountry']/span"));     
        System.out.println("Change Country Link");
        driver.findElement(By.xpath(".//*[@id='footerNav']/li[7]/a")).click();
        System.out.println("Moresears sites");
        System.out.println("---------------------------------------");
        System.out.println("Search Page test case-Success");
        System.out.println("---------------------------------------");

}

public void searchPage_screenshot() 
{
      try{
     Date date=new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");        
        File scrnsht = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String NewFileNamePath=("C://Documents and Settings//vlakshm//workspace//MyTNG//test-output//Screenshots"+"//SearsINTL_"+ formatter.format(date)+".png");  
        FileUtils.copyFile(scrnsht, new File(NewFileNamePath));
        System.out.println(NewFileNamePath);
        Reporter.log("<a href=\"" + NewFileNamePath + "\">Screenshot</a>"); 

      }
      catch (IOException e) {

          e.printStackTrace();

          }

          }


@AfterTest
public void searchPage_closeBrowser() {
     driver.quit(); 
}

@Override
    public void onTestFailure(ITestResult tr) {

     Reporter.log("Fail");


            String workingDirectory = System.getProperty("user.dir");
            String fileName = workingDirectory + File.separator + "screenShots" + File.separator +  tr.getMethod().getMethodName() + "().png";//filename

            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            try {

                FileUtils.copyFile(scrFile, new File(fileName ));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                                e.printStackTrace();

                Reporter.log("error generating screenshot:" +e, true);
            }


    }

@Override
      public void onTestSkipped(ITestResult result) {
      // will be called after test will be skipped
    //searchPage_screenshot();
        Reporter.log("Skip");
      }
      @Override
      public void onTestSuccess(ITestResult result) {
      // will be called after test will pass
          //searchPage_screenshot();
     Reporter.log("Pass");
      }

}