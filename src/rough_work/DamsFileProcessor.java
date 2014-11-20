package rough_work;

import java.util.ArrayList;
import java.util.Hashtable;
import org.testng.annotations.Test;
import com.hsn.util.Xls_Reader;

public class DamsFileProcessor {
  @Test
  public void f() {
	  
	  
	  //@@@@@@@@@@@@@@@@ Add All the Mappings from DamsMappings to a hash Map table @@@@@@@@@@@@@@@@@@@@@@@/
	  Hashtable<String,String> mapOldToNewPath = new Hashtable<String,String>();
		 
	  Xls_Reader xlsr2 = new Xls_Reader("C://temp//DamsMappings.xlsx");
	  int mapCount = xlsr2.getRowCount("Maps") - 1;
	  
	  System.out.println("# of maps to use "+mapCount);
	  
	  String longPath = "";
	  String shortPath = "";
	  String tempStrArr[] = null;
	  
	 
	  
	//@@@@@@@@@@@@@@@@@@@@ Start reading all string from the Long DamsLongPaths.xlsx @@@@@@@@@@@@@@@@@@/
	  
	  Xls_Reader xlsr = new Xls_Reader("C://temp//DamsLongPaths.xlsx");
	  int longPathsCOunt = xlsr.getRowCount("LongPaths");
	  
	  System.out.println("# of paths to proc "+longPathsCOunt);
	  
	 for (int i = 2; i < longPathsCOunt ; i++){
			  
			  		longPath = xlsr.getCellData("LongPaths", 0, i);
					System.out.println("longPath "+longPath);
		  
					 //@@@@@@@ take care of replacements @@@@@@@@@@@@@//
					
					 for(int j = 1; j<= mapCount ; j++){
						 
						 System.out.println("replacing"+xlsr2.getCellData("Maps", 0, j).trim()+" with "+xlsr2.getCellData("Maps", 1, j).trim());
						 longPath = longPath.replace(xlsr2.getCellData("Maps", 0, j).trim(), xlsr2.getCellData("Maps", 1, j).trim());
						  
					  }
					 
					 shortPath = longPath;
					 
					//@@@@@@@ take care of adding top directory till length is < 250 @@@@@@@@@@@@@//
					 
					 tempStrArr = shortPath.split("\\\\");
					 int arrSize = tempStrArr.length;
					 
					 
					 for(int k=0;k<arrSize;k++){
						 System.out.println("the "+k+"th substring is "+tempStrArr[k]);
					 }
					 
					 String shortPathePrefix = "\\\\"+tempStrArr[2];
					 String potentialShortpathprefix = "";
					 
					 String shortPathePostfix = "\\"+tempStrArr[arrSize-1];
					 
					 String potentailfinalShortpath = "";
					 String finalShortpath = "";
											 
					 
					 if(arrSize > 4){
					 
						
						 potentialShortpathprefix = shortPathePrefix ;
						 potentailfinalShortpath = potentialShortpathprefix + shortPathePostfix;
						 
						 finalShortpath = potentailfinalShortpath;
						 System.out.println("smallest string will be "+finalShortpath);
						 
							 for(int l=3;l<arrSize-1;l++){
								 
								 
								 potentialShortpathprefix = potentialShortpathprefix + "\\"+ tempStrArr[l];
								 
								 potentailfinalShortpath = potentialShortpathprefix + shortPathePostfix;
								 
								 if(potentailfinalShortpath.length() <240)
									 finalShortpath = potentailfinalShortpath;
								 else
									 break;
							 }
							 
							 
						 }// if(arrSize > 4){
					 
					 	System.out.println("trimmed short path is"+finalShortpath);
					 	xlsr.setCellData("LongPaths", 1, i, finalShortpath);
					 	System.out.println("################################################");
					 
			 }//for (int i = 1; i < 2 ; i++)
  }//void f()
  
}//end of class

		 

