package rough_work;

import org.testng.annotations.Test;

public class testStrings {
  @Test
  public void f() {
	  
	  String str1 = "RatingLowtoHigh"; 
	  String str2 = "RatingLowtoHigh"; //"Rating Low to High"
	  
	  if( str1.equals(str2))
		  System.out.println("str1 contains str2");
	  else
		  System.out.println("not contains");
  }
}
