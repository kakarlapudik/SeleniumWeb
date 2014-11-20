package rough_work;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

public class TimeFormats {
  @Test
  public void f() {
	  
	  Date dNow = new Date( );
      SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyy hh:mm:ss");
      String timeStamp = "TIME STAMP: "+ft.format(dNow);
  	 
      String str_report= "";
      str_report =  str_report + timeStamp + "\n";
      System.out.println(str_report);
  }
}
