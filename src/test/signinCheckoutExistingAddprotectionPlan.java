package test;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hsn.util.KeywordExecutor;
import com.hsn.util.Keywords;
import com.hsn.util.TestUtil;
import com.hsn.util.Xls_Reader;


public class signinCheckoutExistingAddprotectionPlan {
	
	Keywords k ;
	Method method[];
	String keyword_execution_result = null;
	
  @Test(dataProvider = "getData")
  
 
  public void do_signinCheckoutExistingAddprotectionPlan( Hashtable <String,String> testData) throws Throwable {
	  
	  KeywordExecutor ke = new KeywordExecutor();
	  
	  System.out.println("going to run  "+this.getClass().getName()+" with data "+testData.toString());
	  ke.ExecuteKeywords("signinCheckoutExistingAddprotectionPlan", testData);
	  
	  ke = null;
  }
  
  
  @DataProvider
  
  public Object [][] getData() throws IOException{
	  
	  Object[][] data = null;
	  
	  Keywords k = Keywords.getKeywordsInstance();
	  
	  Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	  data = TestUtil.getData( "signinCheckoutExistingAddprotectionPlan", xls);
	  return data;
  }
  
  @AfterTest
  
  public void close(){
	  //k.close();
  }
  
  
}
