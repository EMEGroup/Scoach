package behavior;

import Misc.GeneralStuff;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Echo extends GeneralBehavior {
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties){
		if(requestProperties == null)
				return null;
		
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		String text = "", Z = "\"";
		
		if(requestProperties.get("text") != null){
			text = requestProperties.get("text").get(0);
		}
		
		if(text.startsWith("scoach"))
			text = text.replaceFirst("[ ]*scoach", "");
		
		String msg = 
			"payload={ " +
			"\"text\": " + "\"" + text + "\"";
		
		if(requestProperties.get("channel_name") != null){
			
			if(requestProperties.get("channel_name").get(0).equals("privategroup")){
				
				String channel_name = GeneralStuff.getChannelInfo(
					requestProperties.get("channel_id").get(0));
				
				msg = msg + ", \"channel\": \"#" + channel_name + "\"";
			}
			else{
				msg = msg + ", \"channel\": \"#" +
					requestProperties.get("channel_name").get(0) + "\"";
			}
		}
		else if(requestProperties.get("user_name") != null){
			msg = msg + ", \"channel\": \"@" +
				requestProperties.get("user_name").get(0) + "\"";
		}
		
		msg += "}";
		
		System.out.println(msg);	// Debug
		
		return responseProperties;
	}
}
