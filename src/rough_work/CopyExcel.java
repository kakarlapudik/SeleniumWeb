package rough_work;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.testng.annotations.Test;

public class CopyExcel {
  @Test
  public void f() throws IOException {
	  
	  String str_pathprocessorExcelName = "C://temp//longfilenames140305053822.xlsx";
	  File source = new File("C://temp//DamsLongPaths.xlsx");
      File dest = new File(str_pathprocessorExcelName.trim());
	  copyFileUsingFileChannels(source, dest);
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
}
