package features.dbpedia;

import java.net.*;
import java.io.*;
import java.util.regex.*;


public class ConnectDBP {

	public static int DBPRate(String keyword, String candidate) throws Exception {
		String urllink = "http://dbpedia.org/data/"+ keyword +".ntriples";
	//	String urllink = "http://dbpedia.org/page/"+ keyword;
	//	System.out.println(urllink);
		URL url = new URL(urllink);   
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		Pattern p = Pattern.compile(candidate); 
		Matcher m;
		String str = "";
		while (br.readLine() != null) {
			str += br.readLine();
			
		}
		m = p.matcher(str);
		int counter = 0;
		while (m.find())
			counter++;
		return counter;
	}

}
