package rough_work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.hsn.util.Xls_Reader;

public class LongFilePathExtractor {

    public void walk( String path ) throws IOException {
    	
    	Xls_Reader xlsr = new Xls_Reader("C://temp//DamsLongPaths1.xlsx");
    	PrintWriter output = new PrintWriter(new FileWriter("C://longpaths1.txt"),true);
    	String tempPath = "";
    	
    	{
    	    output.printf("%s\r\n", "NEWLINE");
    	} 

    	int startLongPathRow=2;
    	
    	File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                //System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                //System.out.println( "File:" + f.getAbsoluteFile() );
                
                //@check the path to see if it greater than 300 and only then save it to the excel
            	
            	  if(f.getAbsoluteFile().toString().length() > 3 ){
	                xlsr.setCellData("Sheet1", 0, startLongPathRow, f.getAbsoluteFile().toString());
	                output.println(f.getAbsoluteFile().toString());
	                System.out.println( "Long File: #"+startLongPathRow +" is " + f.getAbsoluteFile().toString() );
	                startLongPathRow = startLongPathRow + 1;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        LongFilePathExtractor fw = new LongFilePathExtractor();
        fw.walk("\\\\ddn-cifs.hsn.net\\Broadcast01" );
        //fw.walk("C:\\temp\\DAMS" );
    }
    
    public static void WriteTextFile(String fileName, String data) throws IOException{
    	System.out.println("wrting to file"+fileName);
        File outFile = new File (fileName);
        FileWriter fWriter = new FileWriter (outFile,true);
        PrintWriter pWriter = new PrintWriter (fWriter);
        pWriter.println (data);
        pWriter.close();
    } 
}