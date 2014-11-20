package rough_work;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;



public class JQuerifyExampleTest {

  private static final String JQUERY_LOAD_SCRIPT = System.getProperty("user.dir")+"//src//com//hsn//util//queryify.js" ;

  @Test
  public void main() throws IOException {
	  
	  WebDriver driver = new FirefoxDriver();
	  driver.navigate().to("http://qa4.hsn.com");
	  
	   
	  String jQueryLoader = readFile(JQUERY_LOAD_SCRIPT);
	   
	  // give jQuery time to load asynchronously
	  driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeAsyncScript(jQueryLoader);
	  String Myscript = "$(\"#search-input\").val(\"harry\")";
	  js.executeScript(Myscript);  
      System.out.println("tesing jquery in this example"+Myscript);
      
    
  }
  
  // helper method
  private static String readFile(String file) throws IOException {
  Charset cs = Charset.forName("UTF-8");
  FileInputStream stream = new FileInputStream(file);
  try {
  Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
  StringBuilder builder = new StringBuilder();
  char[] buffer = new char[8192];
  int read;
  while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
  builder.append(buffer, 0, read);
  }
  return builder.toString();
  }
  finally {
  stream.close();
  }
  }

}
