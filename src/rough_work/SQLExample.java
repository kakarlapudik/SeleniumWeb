package rough_work;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hsn.util.Keywords;
import com.hsn.util.TestUtil;
import com.hsn.util.Xls_Reader;


public class SQLExample {
	
	Keywords k ;
	Method method[];
	String keyword_execution_result = null;
	
  @Test(dataProvider = "getData")
  
 
  public void do_SQLExample( Hashtable <String,String> testData) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
	  
      k = Keywords.getKeywordsInstance();
      
      k.currentDataSet = new Hashtable<String , String>();
      
      //make sure you have a copy of the current data set so that it is available for preparing a updates sql statement
	  for( String s : testData.keySet()){
		  
		  k.currentDataSet.put(s, testData.get(s));
		  System.out.println("the value of "+s+"is" + testData.get(s));
	  }
	  
	  //k.currentDataSet.put("name", "manju");
	  
	  System.out.println("my current data set is"+k.currentDataSet.toString());
	  
	  k.executeSQL("SQL_1", "");
  }
  
  
  @DataProvider
  
  public Object [][] getData(){
	  
	  Object[][] data = null;
	  Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//Smoke.xlsx");;
	  data = TestUtil.getData( "SQLExample", xls);
	  return data;
  }
  
  @AfterTest
  
  public void close(){
	  //k.close();
  }
  
  
}
