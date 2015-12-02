package Misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {
	
	public static void main(String[] args) {	
		TEST();
	}
	
	public static void work(Map<String, String[]> requestProperties) throws InterruptedException{
		if(requestProperties.get("text") == null)
			return;
		
		Set<Map.Entry<String, String[]>> entrySet = requestProperties.entrySet();
		Map<String, List<String>> reqProperties = 
			new HashMap<String, List<String>>();
		
		for(Map.Entry<String, String[]> entry : entrySet){
			reqProperties.put(entry.getKey(), Arrays.asList(entry.getValue()));
		}
		
		String command = reqProperties.get("text").get(0).split(" ")[0];
		
		reqProperties.get("text").set(0,
			reqProperties.get("text").get(0).replaceFirst(command+"[ \t]*", ""));
		command = command.toLowerCase();
                  
		
		if(GeneralStuff.COMMANDS.get(command) != null){
                    GeneralStuff.COMMANDS.get(command).execute(reqProperties);
		}
		else
                    GeneralStuff.COMMANDS.get("help").execute(reqProperties);
	}
	
	@SuppressWarnings("unchecked")
	private static void TEST(){
		System.out.println("SLACK COACH BOT TESTING.");
		
		String[] x = new String[1];
		Map<String, String[]> h = new HashMap<String, String[]>();

		x[0] = "submissions --by rioma_san --since 10y --all --tags dp --verdict AC";
		h.put("text", x.clone());
		
		x[0] = "bot-playground";
		h.put("channel_name", x.clone());
		
		x[0] = "C0D50765F";
		h.put("channel_id", x.clone());
		
		try {
			work(h);
		} catch (InterruptedException ex) {
			Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
