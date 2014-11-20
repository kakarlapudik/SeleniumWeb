package rough_work;

import org.testng.annotations.Test;

public class StringOperaions {
  @Test
  public void f() {
	  
	  
	  String[] strarr= {"col_column1","col_column2"} ;
	  String s1 = strarr[0].split("col_")[1];
	  
	  System.out.println("string is "+ s1);
  }
}
