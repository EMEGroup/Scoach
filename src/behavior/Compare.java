package behavior;

import codeforcesInfo.Submission;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compare extends GeneralBehavior {
	
	public static final String HELPTEXT = 
		"```"
		+ "Compare:  Displays a brief report of the performance of a list of contestants.\n"
		+ "USAGE:\n"
		+ "compare [--since <TIME_AGO> ] (--team <TEAM_NAME> | --nicks <USER_NAME, USER_NAME, ...>)\n"
		+ "USER_NAME           then name of one contestant\n"
		+ "TEAM_NAME           the name of the team\n"
		+ "TIME_AGO            a number followed by a letter describing the amount of time"
		+ " used as the starting point for the comparison, e.g: '1d' for one day, '7m' for seven months, etc ... supports days('d'), weeks('w'), months('m'), years('y').\n"
		+ "NOTE: Months are being treated as 30 days intervals."
		+ "```";
	
	private final static String[] VERDICTS = 
		{"AC", "PE", "WA", "TLE", "RTE", "MLE", "CE", "HACKED"};
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) throws IOException {
		
		int usernameMaxSize = 25;
		ArrayList<String> handles = null;
		String verdict = null;
		String time = null;
		Long startingTime = null;
		Integer count = null;
		List<String> tags = null;
		Map<String, String> returnObject = null;
		
		returnObject = new HashMap<String, String>();
		
		// Get handles/nicknames by the name of the team: NOT IMPLEMENTED
		// This way must check in the database for the members of a team
		
		// Get handles/nicknames from the --nick argument
		if( requestProperties.get("--nicks") != null ){
			handles = new ArrayList<String>(requestProperties.get("--nicks"));
			
			// Make sure each handle is unique
			for(int i = 0; i < handles.size(); i++){
				for(int j = i+1; j < handles.size(); j++){
					if( handles.get(i).equals( handles.get(j) ) == true ){
						handles.remove(j);
						j--;
					}
				}
			}
		}
		
		// Get the starting time for the comparison
		if(requestProperties.get("--since") != null){
			time = requestProperties.get("--since").get(0);
			startingTime = Math.abs(
				Long.parseLong( time.substring(0, time.length()-1) ));
			
			int DayInSeconds = 60 * 60 * 24;
			switch( Character.toLowerCase( time.charAt(time.length()-1) ) ){
				case 'd':
					startingTime *= DayInSeconds * 1;
					break;
				case 'w':
					startingTime *= DayInSeconds * 7;
					break;
				case 'm':
					startingTime *= DayInSeconds * 30;
					break;
				case 'y':
					startingTime *= DayInSeconds * 360;
					break;
				default:
					startingTime = null;
					break;
			}
			
			startingTime = System.currentTimeMillis() / 1000 - startingTime;
		}
		
		String text = "```Comparing ";	// Monospaced block of code or text
		
		for(int i = 0; i < handles.size(); i++){
			text += handles.get(i);
			if( i < handles.size()-1 )
				 text += ", ";
		}
		
		if( time != null){
			text += " for every submission since " + time + " ago:\n";
		} else{
			text += " for all of their submissions:\n";
		}
		
		text += "Username ";
		
		// String formatting, just beautiness
		for(int i = 0; i < usernameMaxSize-"Username".length(); i++)
			text += " ";
		
		text += "Submissions     ";
		
		for(String v : VERDICTS){
			text += v;
			
			for(int i = 0; i < 11 - v.length(); i++)
				text += " ";
		}
		text += "\n";
		
		for(int i = 0; i < 132; i++) text += "-";	// Title line
		text += "\n";
		
		for(String handle : handles){
			List<Submission> submissionsList = codeforcesInfo.Methods.getSubmissions(
				handle, startingTime, count, verdict, tags);
			
			Map<String, Integer> summary = _summary(submissionsList);
			
			text += handle;
			
			for(int i = 0; i < usernameMaxSize-handle.length(); i++)
				text += " ";
			text += " ";
			
			String subs = String.valueOf(submissionsList.size());
			text += subs;
			
			for(int i = 0; i < "Submissions    ".length() - subs.length(); i++)
				text += " ";
			text += " ";
			
			for(String ver : VERDICTS){
				
				String tmp = String.valueOf(summary.get(ver));
				
				text += tmp;
				
				for(int j = 0; j < 11 - tmp.length(); j++)
					text += " ";
			}
			
			text += "\n";
			
		}
		
		text += "```";		// End of monospaced block of code or text
		
		returnObject.put("text", text);
		return returnObject;
	}
	
	private Map<String, Integer> _summary(List<Submission> submissionList){
		
		Map<String, Integer> returnObject = new HashMap<String, Integer>();
		
		// Setting all expected return values to 0 to avoid null values further
		for(String v : VERDICTS)
			returnObject.put(v, 0);
		
		for(Submission e : submissionList){
			if( returnObject.get(e.getVerdict().getValue()) == null )
				returnObject.put(e.getVerdict().getValue(), 0);
			
			returnObject.put(e.getVerdict().getValue(), 
				returnObject.get(e.getVerdict().getValue()) + 1);
		}
		
		return returnObject;
	}
	
}
