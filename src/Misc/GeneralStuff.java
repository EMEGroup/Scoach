package Misc;

import httpHandling.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralStuff {
	
	public final static String APITOKEN = "xoxp-10522107940-10536035312-12886577955-3187f9ba9a"; // Change it !!
	public final static String WEBHOOKURL = "https://hooks.slack.com/services/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";
	public final static String SLACKAPIURL = "https://slack.com/api";
	public final static String DEFAULTCHANNEL = "#scoachtest";
	public final static Map<String, Command> COMMANDS;
	
	static {
		final Commands C = new Commands();
		
		COMMANDS = new HashMap<String, Command>();
		
		COMMANDS.put("echo", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.echo(req); }
		});
		
		COMMANDS.put("help", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.help(req); }
		});
		
		COMMANDS.put("submissions", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.submissions(req); }
		});
        
		COMMANDS.put("contestants", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ 
                        C.studentInfo(req);
                        }
                });
                COMMANDS.put("group", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.group(req); }
		});
                
		COMMANDS.put("compare", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.compare(req); }
		});
                
		COMMANDS.put("recommendations", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){
				try {
					C.recommendations(req);
				} catch (Exception ex) {
					Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
				}
		}});
	}
	
	private static GeneralStuff self = null;
	
	public static GeneralStuff Instance(){
		if(self == null)
			self = new GeneralStuff();
		
		return self;
	}
	
	public static String getChannelInfo(String channelId, boolean privateGroup) throws IOException{
		String addr = "";
			
		if(privateGroup)
			addr = SLACKAPIURL+"/groups.info?token="+APITOKEN+"&channel="+channelId;
		else
			addr = SLACKAPIURL+"/channels.info?token="+APITOKEN+"&channel="+channelId;
		
		String channel_name = "";
		
		ChannelInfo c = getResponseObject(addr, ChannelInfo.class);
		
		if(privateGroup){
			if(c.group != null)	
				channel_name = c.group.name;
		}
		else{
			if(c.channel != null)
				channel_name = c.channel.name;
		}
		
		return channel_name;
	}
	public static String getUserInfo(String channelId) throws IOException{
		String addr = SLACKAPIURL+"/users.info?token="+APITOKEN+"&user="+channelId;
		
		String user_name = "";
		
		ChannelInfo c = getResponseObject(addr, ChannelInfo.class);
			
		if(c.user != null)
			user_name = c.user.name;
		
		return user_name;
	}
	
	public static <T> T getResponseObject(String url, Class<T> responseClass) throws IOException{
		HttpRequest conn;
		JsonReader jr;
		Gson g;
		T responseObject = null;
		
		conn = new HttpRequest(url);
		conn.startConnection();
		
		g = new Gson();
		try {
			jr = new JsonReader(new InputStreamReader(conn.getInputStream()));
			responseObject = g.fromJson(jr, responseClass);
		} catch (IOException ex) {
			Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		conn.disconnect();
	
		return responseObject;
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
		
		try{
			if(messageProperties.containsKey("queryString"))
				conn = new HttpRequest(
					GeneralStuff.WEBHOOKURL + messageProperties.get("queryString"));
			else
				conn = new HttpRequest(GeneralStuff.WEBHOOKURL);
			
			conn.startConnection();
			conn.write(message);
			conn.disconnect();
			
		} catch (IOException ex) {
			Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
        
	public static  Map<String, String> _forgeMessage(Map<String, List<String>> messageProperties) throws IOException{
		
		Map<String, String> result = new HashMap<String, String>();
		
		Set<Map.Entry<String, List<String>>> entrySet = messageProperties.entrySet();
		
		for(Map.Entry<String, List<String>> x : entrySet){
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
	
	public static Map<String, List<String>> _getArguments(Map<String, List<String>> requestProperties){
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
				key = argument.substring(0, argument.length());
				values = new String[]{""};
			}else{
				key = argument.substring(0, argument.indexOf(" "));
				values = argument.replaceFirst(key+"[ ]+", "").split(
					"([ ]*[,]+[ ]*)+");
			}
			
			args.put(key, Arrays.asList(values));
		}
		
		return args;
	}
        
	public static Map<String, List<String>> _copyMap(Map<String, List<String>> requestProperties){
        Map<String, List<String>> tmpMap = new HashMap<String, List<String>>();
        
        Set<Map.Entry<String, List<String>>> entrySet = requestProperties.entrySet();
		Iterator<Map.Entry<String, List<String>>> iterator = entrySet.iterator();
		
		while( iterator.hasNext() ){
			Map.Entry<String, List<String>> next = iterator.next();
				tmpMap.put(next.getKey(), next.getValue());
        }
        
        return tmpMap;
    }
	
	class ChannelInfo{
		public String status = "";
		public String name = "";
		public ChannelInfo channel, group, user;
	}
}
