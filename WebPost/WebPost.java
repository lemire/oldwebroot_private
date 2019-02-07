import java.net.*;
import java.util.*;

/*
* Basically visit a list of urls, keeping track of
* cookies!
*/
public class WebPost() {
	List mCommandList;
	
	public WebPost(String[] commandfiles) {
		List commands = new ArrayList();
		for (int k = 0; k < s.length; ++k) {
			BufferedReader br = new BufferedReader(new
			FileInputStream(s[k]));
			String commandLine = br.readLine();
			while (commandLine != null) {
				if(commandLine.size() == 0) || 
					commandLine.startsWidth("#") ||
					commandLine.startsWidth("%") ||
					commandLine.startsWidth("//")) {
					// this is a comment
					// for now, we ignore!
				} else {
					commands.add(commandLine);
				}
				commandLine = br.readLine();
			}
			br.close();
		}
		this(commands);
	}
	
	public WebPost(List commands) {
		mCommandList = commands;
	}
	
	public void runAll() {
		ListIterator iter = mCommandList.listIterator();
		while(iter.hasNext()) {
			String CommandLine =  (String) iter.next();
			run(CommandLine);
		}
	}
	
	public void run(String CommandLine) {
		URL url = new URL(CommandLine);
		URLConnection UrlConnObj = url.openConnection();
		UrlConnObj.setUseCaches(false);
		UrlConnObj.setDefaultUseCaches(false);	
		String cookies = UrlConnObj.getHeaderField("set-cookie");		
		//
		int index = cookies.indexOf(";");
		String firstcookie = cookies.substring(0,index);
		System.out.println("firstcookie="+firstcookie);

	}
	
	public static void main(String[] s) {
		if(s.length == 0) {
			System.err.println("You need a configuration file!");
		}
		WebPost wb = new WebPost(s);
		wb.runAll();
		
	}
}


 try
{
URL url = new URL("http://dormin:8086/login.jsp");
URLConnection UrlConnObj = url.openConnection();
System.out.println("Got httpConObj");
HttpURLConnection huc = (HttpURLConnection)UrlConnObj;
UrlConnObj.setUseCaches(false);//Is this required???
UrlConnObj.setDefaultUseCaches(false);
}
String cookie = UrlConnObj.getHeaderField("set-cookie");
int index = cookie.indexOf(";");
String firstcookie = cookie.substring(0,index);
System.out.println("firstcookie="+firstcookie);



URL url1 = new URL("http://dormin:8086/login.jsp?
Username=admin2&Password=admin2"); //Sending NameValue Pair with
URL itself
URLConnection UrlConnObj1 = url1.openConnection();
HttpURLConnection huc1 = (HttpURLConnection)UrlConnObj1;
huc1.setDoInput(true);
huc1.setUseCaches(false);
huc1.setDefaultUseCaches(false);
huc1.setRequestProperty("Content-type","application/x-www-
form-urlencoded");
huc1.setRequestProperty("Cookie",firstcookie);//This
works fine

String cookie1 = UrlConnObj1.getHeaderField("set-cookie");
int index1 = cookie1.indexOf(";");
String firstcookie1 = cookie1.substring(0,index1);
System.out.println("firstcookie1="+firstcookie1);

in = new BufferedInputStream(UrlConnObj1.getInputStream
());
sb = new StringBuffer();
while((c=in.read()) != -1)
sb.append((char)c);
in.close();
System.out.println(sb.toString());
}
catch(Exception e)
{
System.out.println("Exception is"+e);
}

