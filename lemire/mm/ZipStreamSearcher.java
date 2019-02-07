//beginning of file ZipStreamSearcher.java

import java.util.zip.*;
import java.io.*;


/****************
*  (c) 2000 Daniel Lemire
*   http://www.techelements.com/lemire/
*   http://www.ondelette.com/lemire/
*   http://cafe.rapidus.net/danilemi/
*
*
*   You can use and modify this file at will as long as
*   you give me credit.
*
*   Goal : this small java program will look inside
*   a file (say events.lod from Might and Magic VI and VII)
*   for deflated components. It will read them one by on
*   and then output the concatened files to a text file.
*
*   Usage :
*     java ZipStreamSearcher events.lod eventscontent.txt
*
*   That's it.
*
*   Have fun!
*********************************************************/
public class ZipStreamSearcher {

  public ZipStreamSearcher(InputStream is, OutputStream os) throws IOException  {
    System.out.println("Scanning file for deflated components.");
    PrintWriter pw = new PrintWriter (os);
    while(is.available() > 0) {
      int r;
      while((r = is.read()) != 120) {
        is.mark(10);
      }
//      System.out.println((char) r +" " + r);
      int newr = is.read();
//      System.out.println( (char) (newr = is.read())+" " + newr);
      if(newr == 156) {
        is.reset();
        try {
          InflaterInputStream iis = new InflaterInputStream(is, new Inflater(false));
          int first = iis.read();
          System.out.println("====ok, zip stream detected====");
          StringBuffer s = new StringBuffer();
          s.append((char) first);
          while(iis.available() > 0)
            s.append((char) iis.read());
          pw.println(s.toString());
          pw.println("=========END OF FILE=========");
        } catch (ZipException ze) {}
      }
    }
    pw.flush();
    pw.close();
    os.close();
    is.close();
  }

  public static void main (String arg[]) throws IOException {
    new ZipStreamSearcher(new BufferedInputStream(new FileInputStream(arg[0])),new BufferedOutputStream(new FileOutputStream(arg[1])));
  }
}
//end of file ZipStreamSearcher.java
