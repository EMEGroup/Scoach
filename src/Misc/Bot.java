package Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot {
	
	public static void main(String[] args) {	TEST();	}
	
	public static void work(Map<String, List<String>> requestProperties){
		
		if(requestProperties.get("text") == null)
			return;
		
		String command = requestProperties.get("text").get(0).split(" ")[0];
		
		requestProperties.get("text").set(0, 
			requestProperties.get("text").get(0).replaceFirst(command, ""));
		command = command.toLowerCase();
		
		if(GeneralStuff.commands.get(command) != null)
			GeneralStuff.commands.get(command).execute(requestProperties);
	}
	
	@SuppressWarnings("unchecked")
	private static void TEST(){
		System.out.println("SLACK COACH BOT TESTING.");
		
		ArrayList<String> x = new ArrayList<String>();
		Map<String, List<String>> h = new HashMap<String, List<String>>();
		
		x.add("EcHO Buenas tardes, scoach");
		h.put("text", (List<String>) x.clone());
		x.remove(0);
		
		x.add("privategroup");
		h.put("channel_name", (List<String>) x.clone());
		x.remove(0);
		
		x.add("C0AFK0UJD");
		h.put("channel_id", (List<String>) x.clone());
		x.remove(0);
		
		work(h);
	}
	
}
