package rough_work;

import java.io.File;
import java.util.ArrayList;

import org.testng.annotations.Test;

import com.hsn.util.Xls_Reader;

public class SplitAndShortenPath {
  @Test
  public void f() {
	  
	  Xls_Reader xlsr = new Xls_Reader("C://temp//DamsLongPaths.xlsx");
	  int int_longPathCount = xlsr.getRowCount("LongPaths");
	  int topLevelIndex = 12;
	  String longPath;
	  String validShortPath;
	  File tempFile;
	  
	  System.out.println("number of longfiles is"+int_longPathCount);
	  
	  for ( int j = 2; j < int_longPathCount ; j++){
		  
		  longPath = xlsr.getCellData("LongPaths", 0, j).trim();
		  
		  if(longPath.length() > 0){
			  validShortPath = generateValidShortPath(longPath,topLevelIndex);
			  xlsr.setCellData("LongPaths", 1, j, validShortPath);
		  }
	  }
	  
	 	
	  
  }
  
  public String generateValidShortPath(String longPath, int topLevelIndex){
	  
	  String newshortPath = longPath;
	  String newshortPathuptoIndex = "";
	  
	  int numFolder = longPath.split("\\\\").length;
	  
	  for(int j = 2; j < numFolder-topLevelIndex ;j++){
		  
		  if(newshortPath.length() < 254){
		
			  break;
			  
		  }   else{
			  
			  newshortPath = shortenAtIndex(newshortPath,numFolder-j);

		  }// end of else
	  }// end of for loop
	  
	  //even after trimming to top index if the 
	  if(newshortPath.length()>254){
		  
		  newshortPath = getpathUptoIndex(newshortPath , topLevelIndex);
		  newshortPath = newshortPath+"\\LONGFILES\\" ;
		  
		  String[] fileNamearr = longPath.split("\\\\");
		  String fileName = fileNamearr[fileNamearr.length-1];
		  
		  System.out.println("actual file is "+fileName);
		  
		  String[] fileextnArr = fileName.split("\\.");
		  int fileExtnLen = fileextnArr[fileextnArr.length - 1].length();
		  
		  int maxPermittedfileLength = 252 - ( newshortPath.length() +  fileExtnLen );
		  
		  if((fileName.length()-fileExtnLen )>maxPermittedfileLength)
			  newshortPath = newshortPath + fileName.subSequence(0, maxPermittedfileLength)+"."+fileextnArr[fileextnArr.length - 1];
		  else
			  newshortPath = newshortPath + fileName.subSequence(0, fileName.length()-fileExtnLen-1)+"."+fileextnArr[fileextnArr.length - 1];			  
	  }
	  
	  System.out.println("the final shortened valid path is "+newshortPath);
	  return newshortPath;

	  
  }
  
  public String getpathUptoIndex(String inputPath , int topIndex){
	  
	  String[] strArr = inputPath.split("\\\\");
	  String newArr ="";
	  
	  for(int i = 1; i < topIndex; i++){
		  
		  newArr = newArr+"\\"+strArr[i];
	  }
	  
	  //System.out.println("the value of path upto index "+topIndex+" for path = "+inputPath+ " is "+newArr);
	  
	  return newArr;
  }
  
  public String shortenAtIndex(String inputPath , int index){
	  
	  String shortenedIndexpartailpath = "";
	  
	  String[] strArr = inputPath.split("\\\\");
	  
	  //System.out.println("no of array elements after splitting = "+ strArr.length );
	  
	  String newArr ="";
	  index =index;
	  
	  for(int i = 0; i < strArr.length; i++){
		  //System.out.println("the "+i+" th element is "+strArr[i]);
	  }
	  
	  if(strArr.length>4 && (index < strArr.length-1)){
		  
		  newArr = "\\\\"+strArr[2];
		  
		  for(int i = 3; i < strArr.length -1 ; i++){
			  
			  if(i==index){
				  
				  newArr = newArr+"\\"+strArr[i].substring(0, 1);
				  
				  // we want to capture the path upto index just once
				  if(shortenedIndexpartailpath.equals(""))
				  shortenedIndexpartailpath = newArr;  
			  }
			  else
				  newArr = newArr+"\\"+strArr[i];
			  
		  }
		  
		  newArr = newArr+"\\" + strArr[strArr.length-1];
		  
	  }
	  
	  //System.out.println("input ="+inputPath+". After shortening Folder at index ="+index+". we have new path as "+newArr);
	  return newArr;
	  
  }
  
 
}
