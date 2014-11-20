package rough_work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.hsn.util.Xls_Reader;
 
public class UTF8JExample {
	public static void main(String[] args){
		
		Xls_Reader xlsr = new Xls_Reader("C://temp//DamsLongPaths.xlsx");
		int currRow = 2;
		
	try {
		File fileDir = new File("c:\\temp\\test.txt");
 
		BufferedReader in = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(fileDir), "UTF8"));
 
		String str;
 
		while ((str = in.readLine()) != null) {
		    System.out.println("my string is "+str);
		    xlsr.setCellData("LongPaths", "LONG_PATH", currRow++, str);
		}
 
                in.close();
	    } 
	    catch (UnsupportedEncodingException e) 
	    {
			System.out.println(e.getMessage());
	    } 
	    catch (IOException e) 
	    {
			System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
			System.out.println(e.getMessage());
	    }
	}
}