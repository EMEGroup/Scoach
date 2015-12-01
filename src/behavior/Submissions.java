package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Submissions extends GeneralBehavior{
	
	public static final String HELPTEXT = 
		"submissions [OPTIONS] --by <codeforces handle of the user>\n" +
		"OPTIONS:\n" +
		"\t--tags <tag>[,<tag> ...]\t\tA comma separated list of problem tags. (dp, math, greedy ...)\n" +
		"\t--oj <online judge>\t\t\tName of the online judges to search submissions for.\n" +
		"\t--verdict <verdict>\t\t\tVerdict string (AC, WA, TLE ...)\n" +
		"\t--since <time>('d'|'w'|'m'|'y')\t\tTime of the first submission to show. (days, weeks, months ...)\n" +
		"\t--count <1..1000>\t\t\tAmount of submissions to use as scope, note that this implies that\n"
		+ "\t\t\t\t\t\ta scope of a 1000 submissions filtered by any other option may\n"
		+ "\t\t\t\t\t\tresult in a fewer amount of shown submissions. Default is 1.";
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) {
		
		String handle;
		String time = null;
		int count = 1;
		Long from = null;
		String verdict = null;
		
		Map<String, String> responseProperties;
		
		if(requestProperties.get("by") != null){
			handle = requestProperties.get("by").get(0);
		}
		else{
			responseProperties = new HashMap<String, String>();
			responseProperties.put("text", HELPTEXT);
			return responseProperties;
		}
		
		if(requestProperties.get("verdict") != null){
			verdict = requestProperties.get("verdict").get(0);
		}
		
		if(requestProperties.get("count") != null){
			count = Integer.parseInt(requestProperties.get("count").get(0));
		}
		
		if(requestProperties.get("since") != null){
			time = requestProperties.get("since").get(0);
			from = Long.parseLong( time.substring(0, time.length()-1) );
			
			switch( time.charAt(time.length()-1) ){
				case 'd':
					from *= 60 * 60 * 24 * 1;
					break;
				case 'w':
					from *= 60 * 60 * 24 * 7;
					break;
				case 'm':
					from *= 60 * 60 * 24 * 30;
					break;
				case 'y':
					from *= 60 * 60 * 24 * 360;
					break;
				default:
					from = null;
					break;
			}
		}
		
		responseProperties = 
			codeforcesInfo.Methods.getSubmissions(handle, from, count, verdict, 
			requestProperties.get("tags"));
		
		String banner = "Submissions of " + handle;
		if(verdict != null)	banner += ", with verdict of " + verdict;
		if(time != null) banner += ", from " + time + " ago";
		banner += ":\n";
		
		if(!responseProperties.get("text").isEmpty()){
			responseProperties.put("text", banner + responseProperties.get("text"));
		}
		else{
			responseProperties.put("text", banner + "No results.");
		}
		
		return responseProperties;
		
	}
	
}
