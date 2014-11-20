package test;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.hsn.util.KeywordExecutor;
import com.hsn.util.Keywords;
import com.hsn.util.TestUtil;
import com.hsn.util.Xls_Reader;

@Listeners({CheckHSNOnAir.class})

public class CheckHSNOnAir extends TestListenerAdapter{
	
	Keywords k ;
	Method method[];
	String keyword_execution_result = null;
	KeywordExecutor ke;
	String str_report;
	
  @Test(dataProvider = "getData")
  
 
  public void do_CheckHSNOnAir( Hashtable <String,String> testData) throws Throwable {
	  
	  ke = new KeywordExecutor();
	  
	  System.out.println("going to run  "+this.getClass().getName()+" with data "+testData.toString());
	  ke.ExecuteKeywords("CheckHSNOnAir", testData);
	  
	  
  }
  
  @Override
  public void onTestSuccess(ITestResult result) {
	  try {
		k  =  Keywords.getKeywordsInstance();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  	  	
		  Reporter.log("TestCase : CheckHSNOnAir - "+k.currentDataSet.get(""));
  }
  
  
  @DataProvider
  
  public Object [][] getData() throws IOException{
	  
	  Object[][] data = null;
	  
	  Keywords k = Keywords.getKeywordsInstance();
	  
	  Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	  data = TestUtil.getData( "CheckHSNOnAir", xls);
	  return data;
  }
  
  @AfterTest
  
  public void close(){
	  
	  ke = null;
	  //k.close();
  }
  
  
}
