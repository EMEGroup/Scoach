package Misc;

import httpHandling.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		
		COMMANDS.put("submissions", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.submissions(req); }
		});
		
		COMMANDS.put("help", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.help(req); }
		});
        
		COMMANDS.put("student", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ 
				try {
					C.studentInfo(req);
				} catch (IOException ex) {
					Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
				} catch (InterruptedException ex) {
					Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
				}
}
		});
	}
	
	private static GeneralStuff self = null;
	
	public static GeneralStuff Instance(){
		if(self == null)
			self = new GeneralStuff();
		
		return self;
	}
	
	public static String getChannelInfo(String channelId, boolean privateGroup){
		HttpRequest req;
			
		if(privateGroup)
			req = new HttpRequest(	SLACKAPIURL+"/groups.info?token="+APITOKEN+"&channel="+channelId);
		else
			req = new HttpRequest(	SLACKAPIURL+"/channels.info?token="+APITOKEN+"&channel="+channelId);
		req.startConnection();
		
		String channel_name = "";
		
		try {
			JsonReader j = 
				new JsonReader( new InputStreamReader(req.getInputStream()) );
			j.setLenient(true);
			
			Gson g = new Gson();
			ChannelInfo c = g.fromJson(j, ChannelInfo.class);
			
			if(privateGroup){
				if(c.group != null)	
					channel_name = c.group.name;
			}
			else{
				if(c.channel != null)
					channel_name = c.channel.name;
			}
			
		} catch (IOException ex) {
			Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		req.getConnection().disconnect();
		return channel_name;
	}
	public static String getUserInfo(String channelId){
		HttpRequest req = new HttpRequest(	SLACKAPIURL+"/users.info?token="+APITOKEN+"&user="+channelId);
		req.startConnection();
		
		String user_name = "";
		
		try {
			JsonReader j = 
				new JsonReader( new InputStreamReader(req.getInputStream()) );
			j.setLenient(true);
			
			Gson g = new Gson();
			ChannelInfo c = g.fromJson(j, ChannelInfo.class);
			
			if(c.user != null)
				user_name = c.user.name;
		} catch (IOException ex) {
			Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		req.getConnection().disconnect();
		return user_name;
	}
	public static <T> T getResponseObject(String url, Class<T> responseClass){
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
	
	class ChannelInfo{
		public String status = "";
		public String name = "";
		public ChannelInfo channel, group, user;
	}
}
