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
	
	public final static String APIToken = "xoxp-10522107940-10536035312-12886577955-3187f9ba9a"; // Change it !!
	public final static String WebhookUrl = "https://hooks.slack.com/services/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";
	public final static String SlackApiURL = "https://slack.com/api";
	public final static String defaultChannel = "#privategroup";
	public final static Map<String, Command> commands;
	
	static {
		final Commands C = new Commands();
		
		commands = new HashMap<String, Command>();
		
		commands.put("echo", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.echo(req); }
		});
		
		commands.put("submissions", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.submissions(req); }
		});
		
		commands.put("help", new Command(){
			@Override
			public void execute(Map<String, List<String>> req){ C.help(req); }
		});
	}
	
	private static GeneralStuff self = null;
	
	public static GeneralStuff Instance(){
		if(self == null)
			self = new GeneralStuff();
		
		return self;
	}
	
	public static String getChannelInfo(String channelId){
		HttpRequest req = new HttpRequest(
			SlackApiURL+"/channels.info?token="+APIToken+"&channel="+channelId);
		req.startConnection();
		
		String channel_name = "";
		
		try {
			JsonReader j = 
				new JsonReader( new InputStreamReader(req.getInputStream()) );
			j.setLenient(true);
			
			Gson g = new Gson();
			ChannelInfo c = g.fromJson(j, ChannelInfo.class);
			
			channel_name = c.channel.name;
		} catch (IOException ex) {
			Logger.getLogger(GeneralStuff.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		req.getConnection().disconnect();
		return channel_name;
	}
	
	class ChannelInfo{
		public String status;
		public String name;
		public ChannelInfo channel;
	}
}
