package com.hsn.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import net.sf.saxon.exslt.Date;

import org.testng.Assert;
import org.testng.SkipException;

import com.hsn.util.Keywords;
import com.hsn.util.TestUtil;
import com.hsn.util.Xls_Reader;

public class KeywordExecutor {

	Keywords k = null;
	Method method[] = null;
	String keyword_execution_result = null;
	
	public KeywordExecutor() {
		// TODO Auto-generated constructor stub
	}
	
	public String ExecuteKeywords(String testcaseName , Hashtable <String,String> testData) throws Throwable{
		
		  k  =  Keywords.getKeywordsInstance();
		  
		  // keep track of the current test case name . will be needed in reporting later.
		  
		  k.currenttestCaseName = testcaseName.trim();
		  
		  Boolean TestPassed = true;
		  
		  ArrayList<String> TestStepsStatus = new ArrayList<String>();
		  
		  //create & clear the current data set and add the current test data.
		  
		  k.currentDataSet = new Hashtable<String , String>();
		  
		    
		  //make sure you have a copy of the current data set so that it is available in the Keywords section
		  for( String s : testData.keySet()){
			  
			  k.currentDataSet.put(s, testData.get(s));
			  //System.out.println("the value of currentDataSet "+s+"is" + k.currentDataSet.get(s));
		  }
		  
		  k.CurrentBrowser = testData.get("Browser");
		  
		  method = k.getClass().getMethods();
		  
		  k.xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//"+k.suiteXLSName);
		  
		  System.out.println("################ USING THE TEST Data FROM TEST CASE STARTING FORM "+TestUtil.getFirstDataRowRunmode(testcaseName, k.xls) + " data row "+testData.get("DataRow") + "in excel "+k.suiteXLSName+"###############");
		  
		  k.currenttestHeaderrow = TestUtil.getFirstDataRowRunmode(testcaseName, k.xls);
		  
		  int current_DataRow = k.currenttestHeaderrow + Integer.parseInt(testData.get("DataRow"));
		  
		  // store the current data set index.
		  
		  k.currenttestDatarow = current_DataRow;
		  
		  int current_DataColNum = 0;
		  
		  		  
		  System.out.println("################ the current data row is "+current_DataRow+"################");

		  if(TestUtil.getRunmode(testcaseName, k.xls).equalsIgnoreCase("N") || TestUtil.getRunmode(testcaseName, k.xls).equalsIgnoreCase("NF")  )
			  
			  throw new SkipException("skipping test case: "+testcaseName+"since the Runmode for test case is "+TestUtil.getRunmode(testcaseName, k.xls));
		  
		  if(TestUtil.getRunmode(testcaseName, k.xls).equalsIgnoreCase("Y") && testData.get("DataRunmode").toString().equalsIgnoreCase("N"))
			  
			  throw new SkipException("skipping test case row since the Runmode for test data is N");
		  
		  if(TestUtil.getRunmode(testcaseName, k.xls).equalsIgnoreCase("Y") && testData.get("DataRunmode").toString().equalsIgnoreCase("Y")){   
		     	
			   //run through all the test steps
			  
			    System.out.println("the current row being run for test case"+k.currenttestCaseName+" is "+k.currentDataSet.get("DataRow"));
		     
				int testrows = k.xls.getRowCount("TestSteps");
				
				for(int row=1; row<=testrows;row++){
					
					
					
					String str_TCID = k.xls.getCellData("TestSteps", "TCID", row);
					
					if(str_TCID.equalsIgnoreCase(testcaseName)){
						
					String str_TSID = k.xls.getCellData("TestSteps", "TSID", row);
					String str_Keyword = k.xls.getCellData("TestSteps", "Keyword", row);
					String str_Object = k.xls.getCellData("TestSteps", "Object", row);
					String str_Data = k.xls.getCellData("TestSteps", "Data", row);
					
					String[] temstr = null;
					
					//Need to get correct values in the input data has col_
					str_Data = TestUtil.GetUpdatedColumnValues(str_Data);
										
					
					//execute the current keyword for the current test step
					
					for (int int_method = 0; int_method < method.length;int_method++){
				    	
						//find the matching keyword
						if(method[int_method].getName().equalsIgnoreCase(str_Keyword)){
							
							System.out.println("---TCID = "+str_TCID+" -- TSID ="+str_TSID+" -- Keyword = "+str_Keyword+" -- Object = "+str_Object+" -- Data = "+str_Data);	
							keyword_execution_result= (String)method[int_method].invoke(k ,str_Object,str_Data);
							System.out.println("keyword_execution_result for "+(String)method[int_method].getName()+" is "+keyword_execution_result);
							
							TestStepsStatus.add(keyword_execution_result);
							
							TestUtil.ProcesskeywordExecutionResult(keyword_execution_result);
							
							
						}
				    	 
				     }
					
					
					}
				}
				
				// print out all the test step status
				System.out.println("the test steps status is "+TestStepsStatus.toString());
				  
				//For the test to be marked Pass all, the steps should have passed.  
				
				for(String s : TestStepsStatus){
					
					if(!s.contains("PASS")){
					
						TestPassed = false;
						
					break;}
				}
				
				//update the "Result" column in the data sheet to the correct test status
				
				current_DataColNum = TestUtil.getDataColumNum( TestUtil.getFirstDataRowRunmode(testcaseName, k.xls) ,  "Result",  k.xls);
				
				Assert.assertTrue( k.xls.setCellData("Data",current_DataColNum , current_DataRow, TestPassed.toString()+ " Timestamp: "+Date.dateTime().toString()) , "unable to do xls.setCellData ");
				
				Assert.assertTrue(TestPassed, "Test case "+testcaseName+" has failed due to one of its steps not returning pass status");
		
		     method = null;
		
		  }
		return "";
	}

	public String GetUpdatedColumnValues1(String str_DataIn) throws Throwable{
		
		//if the data row contains a config_ prefix , retrieve the info from ENV file	
		String[] temstr; 
		String str_Data = str_DataIn;
		if(str_Data.startsWith("config_"))
		{
			temstr = str_Data.split("config_");
			System.out.println("gettign env property "+temstr[1]);
			str_Data = k.getEnvProperties().getProperty(temstr[1]);
			if(str_Data.isEmpty()){
				
				System.out.println("no env property found for "+temstr[1]);
			}
		}else if //if the data row contains a col_ prefix , retrieve the info from testData		
			(str_Data.startsWith("col_"))
			{
			    //IF the input is a comma separated array of col values, get the full array
			
			    String[] str_arr = str_Data.split(","); // this holds the array of input col_names
			    String[] colvals = new String[5]; // this should hold the array of upto 5 actual col_values
			    
			    str_Data = "";
			    						    
			    // iterate through each of the comma separated col_ strings 		    
			    for(int l = 0; l < str_arr.length ; l++){
			    
			    	colvals[l] = str_arr[l].split("col_")[1];
			    
			    	if(colvals[l].length() > 0){
			    		
			    	
				    	//System.out.println("gettign column value for "+colvals[1]);
					
						int datacolNum = TestUtil.getDataColumNum( TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, k.xls) ,  colvals[l].trim(),  k.xls);
						colvals[l] = k.xls.getCellData("Data", datacolNum, k.currenttestDatarow);
						//System.out.println("colvals["+l+"] = "+colvals[l]+" taken form current datarow "+k.currenttestDatarow);
						
						// add code to get value form common sheet if value is @key_CommonAccountEmail1
						if(colvals[l].contains("key_")){
							
							int rowNumSharedDataForAllTestCases = k.xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", colvals[l].split("key_")[1]);
							System.out.println("need to retrieve value from rownum "+rowNumSharedDataForAllTestCases);
							
							colvals[l] = k.xls.getCellData("SharedDataForAllTestCases", 1, rowNumSharedDataForAllTestCases);
													
						}
						
						if(l==0){
							str_Data = str_Data+ colvals[l];
						}else
						{
							str_Data = str_Data  + " , " + colvals[l] ;
						}
					
			    	}
				
			    }//end of for loop
				

			}//end of (str_Data.startsWith("col_"))
		
		//System.out.println("FUNCTION : GetUpdatedColumnValues The raw input was "+str_DataIn+" the processed output was "+str_Data);
		return str_Data;
		
	}
		    

	public void ProcesskeywordExecutionResult1(String keyword_execution_result) throws Throwable{
	
	 if(keyword_execution_result.contains("col|")){
			
		    //System.out.println("Need to put data into row currentTestDataSetID);
			String[] strArr = keyword_execution_result.split("\\|");
			String dataInputColName = strArr[1].split(" ")[0];
			System.out.println("dataInputColName = "+dataInputColName);
			String dataInputValue =  strArr[1].split("= ")[1];
			System.out.println("dataInputValue = "+dataInputValue);
			int current_DataColNum = TestUtil.getDataColumNum( TestUtil.getFirstDataRowRunmode(k.currenttestCaseName, k.xls) ,  dataInputColName,  k.xls);
			
			// if the current currentDataVal has something like @key_CommonAccount1_Email, the value needs to saved
			// in the appropriate column in SharedDataForAllTestCases sheet and not in data sheet
			String currentDataVal = k.xls.getCellData("Data", current_DataColNum, k.currenttestDatarow);
			if(currentDataVal.contains("@key_")){
				
				int rowNumSharedDataForAllTestCases = k.xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", currentDataVal.split("@key_")[1]);
				System.out.println("need to store value to rownum "+rowNumSharedDataForAllTestCases);
				Assert.assertTrue( k.xls.setCellData("SharedDataForAllTestCases", 1, rowNumSharedDataForAllTestCases, dataInputValue) , "unable to do xls.setCellData ");
			}else
			Assert.assertTrue( k.xls.setCellData("Data", current_DataColNum, k.currenttestDatarow, dataInputValue) , "unable to do xls.setCellData ");
			Thread.sleep(1000);
			//System.out.println(" FUNCTION: ProcesskeywordExecutionResult The raw input was "+keyword_execution_result);
     }
	
}

}
