 package com.hsn.util;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;

public class TestUtil {
	static Keywords k1;
	// gets the data set for a test into a hash map array
	public static Object[][]  getData(String testCase, Xls_Reader xls){
		
		// get num of the row where the test case is starting
		// get number of rows of data
		// get all column data
		// extract 
		
		// int where the test starts
		
		Object[][] testData = null;
		int testStartNum = 1;
		int currtestrowNum = 1;
		int maxtestDataRowNum = 0;
		int maxtestDataColNum = 0;
		Hashtable<String,String> table = null;
		
		while(! (xls.getCellData("Data", 0, testStartNum).equals(testCase))){
			
			testStartNum++;
		}
		
		//@@@@@@@@@@ Holds the starting row number of test Data in the data sheet @@@@@@@@@@@@@//
		currtestrowNum = testStartNum+1;
		
		System.out.println("test data header starts on row"+(testStartNum));

		
		while(! (xls.getCellData("Data",maxtestDataColNum, testStartNum+1).equals(""))){
			maxtestDataColNum++;
		}
		
		System.out.println("the number of colums for "+testCase+"is "+maxtestDataColNum);
		
		while(! (xls.getCellData("Data", 0, currtestrowNum+1).equals(""))){
			maxtestDataRowNum++;
			currtestrowNum++;
		}
		
		System.out.println("the number of data rows for "+testCase+"is "+maxtestDataRowNum);
		
		testData = new Object[maxtestDataRowNum][1];
		
		//iterate over rows
		
		int index = 0;
		
		for(int row = 1 ; row <=  maxtestDataRowNum ; row++ ){
			
			table = new Hashtable<String,String>();
			
			for(int col = 0; col < maxtestDataColNum; col++ ){
				
				String key = xls.getCellData("Data", col, testStartNum+1);
				String value = xls.getCellData("Data", col, testStartNum+1+row);
				table.put(key, value);
			
			}
			
			testData[index][0] = table;
			index ++;
				
		}
		
		return testData;
	}

	
	public static String getRunmode(String testCaseName , Xls_Reader xls){
	
		int row = 1; //This is the starting row in the TestCases sheet.
		int testCaseTotalNum = 0;
		
		while(! (xls.getCellData("TestCases", "TCID", row).equals(""))){
			
			row++;
			testCaseTotalNum++;
		}
		
		// By the end of above loop, we have the total number of test cases
		
		String currTestCaseName = null;
		String currTestCaseRunMode = null;
		
		for (row = 2; row <= testCaseTotalNum; row++ )
		{
			
			 currTestCaseName = xls.getCellData("TestCases", "TCID", row);
			 currTestCaseRunMode = xls.getCellData("TestCases", "Runmode", row);
			
			
			if (currTestCaseName.equals(testCaseName)){
				//System.out.println("the runmode for testcase "+currTestCaseName+" is "+currTestCaseRunMode);

				 return currTestCaseRunMode;

			}
		}
		
		System.out.println(" ERROR: the testcase "+currTestCaseName+" was not found");
		return "NF";
	}


	public static int getFirstDataRowRunmode(String testCaseName , Xls_Reader xls){
	
	int testStartrowNum = 0;
	while(! (xls.getCellData("Data", 0, testStartrowNum).equals(testCaseName))){
		
		testStartrowNum++;
	}
	
	testStartrowNum = testStartrowNum+1;
	
	//System.out.println("test case starts on row"+(testStartrowNum));
	return testStartrowNum;
}

	public static int getDataColumNum(int testDataHeaderRowNum , String dataColumnName, Xls_Reader xls){
	
	Boolean foundColNum = false;
	int testDataColumnCount = 0;
	
	while((xls.getCellData("Data", testDataColumnCount, testDataHeaderRowNum).length()>0)){
		
		testDataColumnCount++;
	}
	
	//System.out.println("looking for column Name "+dataColumnName);
	
	for(int colNum = 0; colNum < testDataColumnCount; colNum++){
		
		//System.out.println(" data in row "+testDataHeaderRowNum+" column "+colNum+ " = "+xls.getCellData("Data", colNum, testDataHeaderRowNum));
		if (xls.getCellData("Data", colNum, testDataHeaderRowNum).trim().equals(dataColumnName.trim())){
			
			foundColNum = true;
			return colNum;
		}
		else{
			//System.out.println("looking for @@"+dataColumnName+"@@ but found @@"+xls.getCellData("Data", colNum, testDataHeaderRowNum)+"@@");
		}
	}
	
	Assert.assertTrue(foundColNum, "not able to find "+dataColumnName);
	
	return 0;
}
	
	public static String GetUpdatedColumnValues(String str_DataIn) throws IOException{
		Keywords k  =  Keywords.getKeywordsInstance();	
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

	public static void ProcesskeywordExecutionResult(String keyword_execution_result) throws Throwable{
	
		Keywords k  =  Keywords.getKeywordsInstance();
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
	
	public static String getDataColumVal234(String testcaseName , String dataColumnName) throws IOException{
		
		k1 = Keywords.getKeywordsInstance();
		int datacolNum = TestUtil.getDataColumNum( k1.currenttestHeaderrow ,  dataColumnName, k1.xls);
		int DataRow = k1.currenttestDatarow ;
		
		String str_Data = k1.xls.getCellData(Constants.DATA, datacolNum, DataRow);
				
		if(str_Data.contains("key_")){
			
			int rowNumSharedDataForAllTestCases = k1.xls.getCellRowNum("SharedDataForAllTestCases", "Common_Key_Name", str_Data.split("key_")[1]);
			System.out.println("need to retrieve value from rownum "+rowNumSharedDataForAllTestCases);
			str_Data = k1.xls.getCellData("SharedDataForAllTestCases", 1, rowNumSharedDataForAllTestCases);
											
		}
		
		System.out.println("the value of colum "+dataColumnName+" for current test case "+testcaseName+" is "+str_Data);
		return str_Data;
		
		}



}

