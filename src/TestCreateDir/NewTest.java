package TestCreateDir;

import java.io.File;

import org.testng.annotations.Test;

public class NewTest {
  @Test
  public void f() {
	  
	  File theDir = new File("C:\\temp\\DAMS\\creativesvcsCreative Services JobsMIS MiscellaneousMIS1303.06 Hallway_Greenroom Wall ArtM\\M");
	  if (!theDir.exists()) {
	  

	    System.out.println("creating directory: " + theDir);
	    boolean result = theDir.mkdir();  

	     if(result) {    
	       System.out.println("DIR created");  
	     }
	  }
  }
}
