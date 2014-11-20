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

@Listeners({OrderStatusDrawer.class})

public class OrderStatusDrawer extends TestListenerAdapter{
	
	Keywords k ;
	Method method[];
	String keyword_execution_result = null;
	
  @Test(dataProvider = "getData")
  
 
  public void do_OrderStatusDrawer( Hashtable <String,String> testData) throws Throwable {
	  
	  KeywordExecutor ke = new KeywordExecutor();
	  
	  System.out.println("going to run  "+this.getClass().getName()+" with data "+testData.toString());
	  ke.ExecuteKeywords("OrderStatusDrawer", testData);
	  
	  ke = null;
  }
  
  
  // for custom reporting after test has passed
  @Override
  public void onTestSuccess(ITestResult result) {
	  try {
		k  =  Keywords.getKeywordsInstance();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  	  
	  		System.out.println("\n TEST CASE: OrderStatusDrawer -  "+k.currenttestReportStmnt);
		    Reporter.log("\n TEST CASE: OrderStatusDrawer -  "+k.currenttestReportStmnt );
  }
  
  
  @DataProvider
  
  public Object [][] getData() throws IOException{
	  
	  Object[][] data = null;
	  
	  Keywords k = Keywords.getKeywordsInstance();
	  
	  Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
	  data = TestUtil.getData( "OrderStatusDrawer", xls);
	  return data;
  }
  
  @AfterTest
  
  public void close(){
	  //k.close();
  }
  
  
}