package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help extends GeneralBehavior{

	public static final String HELPTEXT =
		"```"
		+ "Available commands:\n"
		+ "    echo                                 Repeats what you write.\n"
		+ "    submissions                          Show submissions of an user.\n"
                + "    contestants                          Search, add and delete contestants' information\n"
                + "    group                                Make groups contastants for a quicker search\n"
		+ "    help                                 Displays this help text.\n"
		+ "    compare                              Displays a brief report of the performance of a list of contestants.\n"
		+ "    recommend                            Recommends problems not solved by a group standing some diffuculty criteria.\n"
		+ "For more info about a specific command, run help <command>."
		+ "```";
	
	private static final Map<String, String> COMMANDSHELP;
	
	static{
		COMMANDSHELP = new HashMap<String, String>();
		
		COMMANDSHELP.put("echo", Echo.HELPTEXT);
		COMMANDSHELP.put("help", Help.HELPTEXT);
		COMMANDSHELP.put("submissions", Submissions.HELPTEXT);
		COMMANDSHELP.put("contestants", StudentInfo.HELPTEXT);
		COMMANDSHELP.put("compare", Compare.HELPTEXT);
		COMMANDSHELP.put("group", Group.HELPTEXT);
		COMMANDSHELP.put("recommend", Recommendations.HELPTEXT);
	}
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) {
		
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		String command = null;
		
		if(requestProperties != null && requestProperties.get("text") != null && 
			requestProperties.get("text").size() > 0){
			
			command = requestProperties.get("text").get(0);
			command = command.split(" ")[0];
			
			if(COMMANDSHELP.containsKey(command)){
				responseProperties.put("text", COMMANDSHELP.get(command));
				return responseProperties;
			}
		}
		
		responseProperties.put("text", HELPTEXT);
		return responseProperties;
	}

}
