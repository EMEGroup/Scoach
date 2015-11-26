package Misc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bot {
	
	public static void main(String[] args) {	
		TEST();
	}
	
	public static void work(Map<String, String[]> requestProperties){
		
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
		
		if(GeneralStuff.commands.get(command) != null)
			GeneralStuff.commands.get(command).execute(reqProperties);
		else
			GeneralStuff.commands.get("help").execute(reqProperties);
	}
	
	@SuppressWarnings("unchecked")
	private static void TEST(){
		System.out.println("SLACK COACH BOT TESTING.");
		
		String[] x = new String[1];
		Map<String, String[]> h = new HashMap<String, String[]>();
		
		x[0] = "submissions --by rioma_san --count 10";
		h.put("text", x.clone());
		
		x[0] = "privategroup";
		h.put("channel_name", x.clone());
		
		x[0] = "C0D50765C";
		h.put("channel_id", x.clone());
		
		work(h);
	}
	
}
