package behavior;

import httpHandling.HttpRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Echo extends Thread {
	
	private HttpRequest connection;
	private Map<String, List<String>> properties;
	private String host, method, query;
	
	public Echo(String hostAddress, String methodString, String queryString){
		host = hostAddress;
		method = methodString;
		query = queryString;
	}
	
	public void Run(Map<String, List<String>> requestProperties){
		connection = new HttpRequest(host, method, query);
		connection.openConnection();
		
		if(requestProperties == null)
				return;
		
		String text = "";
		
		if(requestProperties.get("text") != null){
			text = requestProperties.get("text").get(0);
		}
		
		if(text.startsWith("scoach"))
			text = text.replaceFirst("[ ]*scoach", "");
		
		String msg = 
			"payload={ " +
			"\"text\": " + "\""+text+"\",";
		
		if(requestProperties.get("channel") == null){
			msg = msg + "\"channel\":" + 
				"\"@"+requestProperties.get("user_name").get(0)+"\"";
		}
		else{
			msg = msg + "\"channel\":" + 
				"\"#"+requestProperties.get("channel_name").get(0)+"\"";
		}
		
		msg += "}";
	}
}
	
