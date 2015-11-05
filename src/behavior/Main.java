package behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("SLACK COACH BOT TESTING.");
		Echo e = new Echo("scoach-scoach.rhcloud.com", "uri", null);
		
		ArrayList<String> x = new ArrayList<String>();
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		
		x.clear();
		x.add("Buenas tardes, scoach");
		h.put("text", x);
		
		x.clear();
		x.add("bot-playground");
		h.put("channel_name", x);
		
		e.Run(h);
	}

}
