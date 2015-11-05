package Misc;

import behavior.Echo;
import httpHandling.HttpRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Commands {
	public void echo(Map<String, List<String>> requestProperties){
		Echo echo = new Echo();
		
		Map<String, String> result = echo.Run(requestProperties);
		
		httpHandling.HttpRequest conn = 
			new HttpRequest(GeneralStuff.WebhookUrl);
		
		Set<Map.Entry<String, String>> entrySet = result.entrySet();
		Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
		
		while(iterator.hasNext()){
			Map.Entry<String, String> next = iterator.next();
			
			conn.getConnection().setRequestProperty(
				next.getKey(), next.getValue());
		}
	}
}

interface Command{
	public abstract void execute(Map<String, List<String>> requestProperties);
}
