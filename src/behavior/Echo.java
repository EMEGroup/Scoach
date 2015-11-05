package behavior;

import httpHandling.HttpRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		connection.startConnection();
		
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
			"text: " + text + ",";
		
		if(requestProperties.get("channel_name") != null){
			msg = msg + "channel: #" +
				requestProperties.get("channel_name").get(0);
		}
		else if(requestProperties.get("user_name") != null){
			msg = msg + "channel: @" +
				requestProperties.get("user_name").get(0);
		}
		
		msg += "}";
		
		System.out.println(msg);
		connection.write(msg);
		
		InputStreamReader is = 
			new InputStreamReader(connection.getInputStream());
		
		char[] asdf = new char[1024];
		try {
			is.read(asdf);
		} catch (IOException ex) {
			Logger.getLogger(Echo.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println( asdf );
		
		connection.getConnection().disconnect();
	}
}
	
