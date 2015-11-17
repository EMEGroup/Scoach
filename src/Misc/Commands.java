package Misc;

import behavior.*;
import httpHandling.HttpRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commands {
	public void help(Map<String, List<String>> requestProperties){
		Help _help = new Help();
		
		Map<String, String> result = _help.Run(requestProperties);
		
		if(result == null) 
			return;
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( forgeMessage(requestProperties) );
	}
	
	public void echo(Map<String, List<String>> requestProperties){
		Echo _echo = new Echo();
		
		Map<String, String> result = _echo.Run(requestProperties);
		
		if(result == null)
			return;
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( forgeMessage(requestProperties) );
	}
	
	public void submissions(Map<String, List<String>> requestProperties){
		Submissions _submissions = new Submissions();
		
		Map<String, String> result = 
			_submissions.Run( getArguments(requestProperties) );
		
		if(result == null)
			return;
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( forgeMessage(requestProperties) );
	}
	
	private void _sendMessage(Map<String,String> messageProperties){
		if(messageProperties == null)	return;
		
		String message = "payload={";
		
		Set<Map.Entry<String, String>> entrySet = messageProperties.entrySet();
		Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
		
		while( iterator.hasNext() ){
			Map.Entry<String, String> next = iterator.next();
			
			message += "\"" + next.getKey() + "\":";
			message += "\"" + next.getValue() + "\"";
			
			if( iterator.hasNext() )
				message += ",";
		}
		
		message += "}";
		
		System.out.println(message);	// Debug
		
		HttpRequest conn;
		
		if(messageProperties.containsKey("queryString"))
			conn = new HttpRequest(
				GeneralStuff.WebhookUrl + messageProperties.get("queryString"));
		else
			conn = new HttpRequest(GeneralStuff.WebhookUrl);
		conn.startConnection();
		conn.write(message);
		conn.disconnect();
		
	}
	
	private Map<String, String> forgeMessage(Map<String, List<String>> messageProperties){
		
		Map<String, String> result = new HashMap<String, String>();
		
		Set<Entry<String, List<String>>> entrySet = messageProperties.entrySet();
		
		for(Entry<String, List<String>> x : entrySet){
			if( result.containsKey(x.getKey()) || x.getValue().size() <= 0)
				continue;
			
			result.put( x.getKey(), x.getValue().get(0));
		}
		
		if(messageProperties.get("channel_id") != null){
			
			result.put("channel", "#" + GeneralStuff.getChannelInfo(
				messageProperties.get("channel_id").get(0)) );
		}
		else if(messageProperties.get("user_name") != null){
                    
			result.put("channel", "@" + 
				messageProperties.get("user_name").get(0));
		}
		else{
			result.put("channel", GeneralStuff.defaultChannel);
		}
		
		return result;
	}
	
	private Map<String, List<String>> getArguments(Map<String, List<String>> requestProperties){
		Map<String, List<String>> args = new HashMap<String, List<String>>();
		
		String text = "";
		
		if(requestProperties.get("text") != null){
			if(requestProperties.get("text").get(0) != null){
				text = requestProperties.get("text").get(0);
			}
		}
		
		Pattern argPat = Pattern.compile("--[^-]+");
		Matcher argsMatcher = argPat.matcher(text);
		
		String argument, key;
		String[] values;
		
		while(argsMatcher.find()){
			argument = argsMatcher.group();
			argument = argument.trim();
			
			if( !argument.contains(" ") )	continue;
			
			key = argument.substring(2, argument.indexOf(" "));
			values = argument.replaceFirst("--"+key+"[ ]+", "").split(
					"([ ]*[,]+[ ]*)+");
			
			args.put(key, Arrays.asList(values));
		}
		
		return args;
	}
}
