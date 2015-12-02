package behavior;

import java.io.IOException;
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
		"\t--show <amount>\t\t\tAmount of submissions to show\n"
		+ "--all\t\t\t\tShow all submissions, overrides the --show argument.";
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) throws IOException{
		
		String handle;
		String time = null;
		Integer show = 10;
		Long from = null;
		String verdict = null;
		
		Map<String, String> responseProperties;
		
		if(requestProperties.get("--by") != null){
			handle = requestProperties.get("--by").get(0);
		}
		else{
			responseProperties = new HashMap<String, String>();
			responseProperties.put("text", HELPTEXT);
			return responseProperties;
		}
		
		if(requestProperties.get("--verdict") != null){
			verdict = requestProperties.get("--verdict").get(0);
		}
		
		if(requestProperties.get("--show") != null){
			if( !requestProperties.get("--show").get(0).isEmpty() )
				show = Integer.parseInt(requestProperties.get("--show").get(0));
		}
		
		if(requestProperties.get("--all") != null){
			show = null;
		}
		
		if(requestProperties.get("--since") != null){
			time = requestProperties.get("--since").get(0);
			from = Long.parseLong( time.substring(0, time.length()-1) );
			
			switch( Character.toLowerCase( time.charAt(time.length()-1) ) ){
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
			
			from = System.currentTimeMillis() / 1000 - from;
		}
		
		responseProperties = 
			codeforcesInfo.Methods.getSubmissions(handle, from, show, verdict, 
			requestProperties.get("--tags"));
		
		String banner = "Submissions of " + handle;
		if(verdict != null)	banner += ", with verdict of " + verdict;
		if(time != null) banner += ", from " + time + " ago";
		if(requestProperties.get("--tags") != null && requestProperties.get("--tags").isEmpty() == false){
			banner += ", with tags of ";
			
			for(int i = 0; i < requestProperties.get("--tags").size(); i++){
				banner += requestProperties.get("--tags").get(i);
				if( i < requestProperties.get("--tags").size() - 1 )
					banner += ", ";
			}
		}
		
		banner += ":\n";
		
		if( responseProperties.get("text").isEmpty() ){
			responseProperties.put("text", banner + "No results.");
		}
		else{
			responseProperties.put("text", banner + responseProperties.get("text"));
		}
		
		return responseProperties;
		
	}
	
}
