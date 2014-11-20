package rough_work;

import java.io.IOException;

import org.testng.annotations.Test;

public class CCleaner {
  @Test
  public void f() throws IOException, InterruptedException {
	  
		System.out.println("tying cc cleaner");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//drivers//LaunchCCCleaner.exe");
		Thread.sleep(90000);
		System		.out.println("Java is done with cc cleaner");
		//return "PASS";
  }
}
