package Misc;

import behavior.*;
import httpHandling.HttpRequest;
import java.io.IOException;
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
		
		_sendMessage( _forgeMessage(requestProperties) );
	}
	
	public void echo(Map<String, List<String>> requestProperties){
		Echo _echo = new Echo();
		
		Map<String, String> result = _echo.Run(requestProperties);
		
		if(result == null)
			return;
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( _forgeMessage(requestProperties) );
	}
	
	public void submissions(Map<String, List<String>> requestProperties){
		Submissions _submissions = new Submissions();
		
		Map<String, String> result = 
			_submissions.Run( _getArguments(requestProperties) );
		
		if(result == null)
			return;
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( _forgeMessage(requestProperties) );
	}
	
	public void studentInfo(Map<String, List<String>> requestProperties) throws IOException, InterruptedException{
		StudentInfo _studentInfo= new StudentInfo();
                
		Map<String, String> result = 
			_studentInfo.Run(requestProperties);
		
		if(result == null) 
			return;
		
		String text= result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		_sendMessage( _forgeMessage(requestProperties) );
	}
	
	public static void _sendMessage(Map<String,String> messageProperties){
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
		
		HttpRequest conn;
		
		if(messageProperties.containsKey("queryString"))
			conn = new HttpRequest(
				GeneralStuff.WEBHOOKURL + messageProperties.get("queryString"));
		else
			conn = new HttpRequest(GeneralStuff.WEBHOOKURL);
		conn.startConnection();
		conn.write(message);
		conn.disconnect();
		
	}
	
	public static  Map<String, String> _forgeMessage(Map<String, List<String>> messageProperties){
		
		Map<String, String> result = new HashMap<String, String>();
		
		Set<Entry<String, List<String>>> entrySet = messageProperties.entrySet();
		
		for(Entry<String, List<String>> x : entrySet){
			if( result.containsKey(x.getKey()) || x.getValue().size() <= 0)
				continue;
			
			result.put( x.getKey(), x.getValue().get(0));
		}
		
		String channel = "";
		if(messageProperties.get("channel_id") != null){
			boolean privateGroup = false;
			
			if(messageProperties.get("channel_name") != null && 
				messageProperties.get("channel_name").get(0).equals("privategroup"))
				privateGroup = true;
			
			channel = GeneralStuff.getChannelInfo(
				messageProperties.get("channel_id").get(0), privateGroup);
			
			if( channel.isEmpty() == false )
				channel = "#" + channel;
		}
		
		if(messageProperties.get("user_id") != null && channel.isEmpty() ){
            
			channel = GeneralStuff.getUserInfo(
				messageProperties.get("user_id").get(0));
			
			if(channel.isEmpty() == false)
				channel = "@" + channel;
		}
		
		if( channel.isEmpty() ){
			channel = GeneralStuff.DEFAULTCHANNEL;
		}
		
		result.put("channel", channel);
		
		return result;
	}
	
	public Map<String, List<String>> _getArguments(Map<String, List<String>> requestProperties){
		Map<String, List<String>> args = new HashMap<String, List<String>>();
		
		String text = "";
		
		if(requestProperties.get("text") != null){
			if(requestProperties.get("text").get(0) != null){
				text = requestProperties.get("text").get(0);
			}
		}
		
		Pattern argPat = Pattern.compile("-{2}((?!-{2}).)+");
		Matcher argsMatcher = argPat.matcher(text);
		
		String argument, key;
		String[] values;
		
		while(argsMatcher.find()){
			argument = argsMatcher.group();
			argument = argument.trim();
			
			if( !argument.contains(" ") ){
				key = argument.substring(2, argument.length());
				values = new String[]{""};
			}else{
				key = argument.substring(2, argument.indexOf(" "));
				values = argument.replaceFirst("--"+key+"[ ]+", "").split(
					"([ ]*[,]+[ ]*)+");
			}
			
			args.put(key, Arrays.asList(values));
		}
		
		return args;
	}
        
      public static Map<String, List<String>> _copyMap(Map<String, List<String>> requestProperties){
        Map<String, List<String>> tmpMap = new HashMap<>();
        
        Set<Map.Entry<String, List<String>>> entrySet = requestProperties.entrySet();
	Iterator<Map.Entry<String, List<String>>> iterator = entrySet.iterator();
		
	while( iterator.hasNext() ){
        Map.Entry<String, List<String>> next = iterator.next();
            tmpMap.put(next.getKey(), next.getValue());
        }
        
        return tmpMap;
    }
	
}
