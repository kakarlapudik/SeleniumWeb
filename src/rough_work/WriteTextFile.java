package rough_work;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteTextFile
{
  public static void main (String [] args) throws IOException
  {
    File outFile = new File ("c:\\output.txt");
    FileWriter fWriter = new FileWriter (outFile);
    PrintWriter pWriter = new PrintWriter (fWriter);
    pWriter.println ("This is a line.");
    pWriter.println ("This is another line.");
    pWriter.close();
  }
}