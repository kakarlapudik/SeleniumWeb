package rough_work;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

import com.hsn.util.Xls_Reader;

public class CopyFilesExample {

	public static void main(String[] args) throws InterruptedException,
			IOException {

	
		long start = System.nanoTime();
		long end;
		File source;
		File dest;
		String strSource;
		String strDest;
		
		
		 Xls_Reader xlsr = new Xls_Reader("C://temp//DamsLongPaths.xlsx");
		 int int_longPathCOunt = xlsr.getRowCount("LongPaths");
		 
		 for(int i = 2; i <= 2 ; i++){
			 
			 strSource = xlsr.getCellData("LongPaths", 0, i);
			 strDest = xlsr.getCellData("LongPaths", 1, i);
			 createPathdirs(strDest);
			
			 
			 System.out.println("moving from Source "+strSource+" to destination "+strDest);
			 
			 try {
				 source = new File(strSource.trim());
				 dest = new File(strDest.trim());
				 start = System.currentTimeMillis();
				 copyFileUsingFileChannels(source, dest);
				 end = System.currentTimeMillis();
				 System.out.println("file#"+ (i-1) + "Time taken by FileChannels Copy in milliseconds= " + (end - start));
				 xlsr.setCellData("LongPaths", 2, i, "PASS");
				 source.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				xlsr.setCellData("LongPaths", 2, i, "FAIL");
				e.printStackTrace();
			}
			 
			 

		 }
		
		
		
	}

	
	

	private static void copyFileUsingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public static void WriteTextFile(String fileName, String data) throws IOException{
    	//System.out.println("wrting to file"+fileName);
        File outFile = new File (fileName);
        FileWriter fWriter = new FileWriter (outFile,true);
        PrintWriter pWriter = new PrintWriter (fWriter);
        pWriter.println (data);
        pWriter.close();
    } 
	
	 public static String getpathUptoIndex(String inputPath , int topIndex){
	  	  
	  	  String[] strArr = inputPath.split("\\\\");
	  	  String newArr ="";
	  	  
	  	  for(int i = 1; i < topIndex; i++){
	  		  
	  		  newArr = newArr+"\\"+strArr[i];
	  	  }
	  	  
	  	  //System.out.println("the value of path upto index "+topIndex+" for path = "+inputPath+ " is "+newArr);
	  	  
	  	  return newArr;
	    }
	    
	
	
	
	public static String createPathdirs(String inputPath ) throws IOException{
	  	  
	  	  String[] strArr = inputPath.split("\\\\");
	  	  String currentPath;
	  	  
	  	  
	  	  
	  	  for(int i = 3; i < strArr.length; i++){
	  		  
	  		  currentPath = getpathUptoIndex(inputPath,i);
	  		  File theDir = new File(currentPath.trim());
	  		  
	  		  System.out.println("the current dir is "+currentPath);
			  if (!theDir.exists()) {
			  
			    	    
			    boolean result = theDir.mkdir();  

			     if(result) {    
			    	 WriteTextFile("C://temp//newDirs.txt",theDir.toString());	 
			         System.out.println("DIR created"+currentPath);  
			     }
			  }
	  	  }
	  	  
	  	    	  
	  	  return "";
	    }

}
