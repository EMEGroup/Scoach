package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help extends GeneralBehavior{

	public static final String HELPTEXT =
			"Available commands:\n"
			+ "    echo                                 Repeats what you write.\n"
			+ "    submissions                          Show submissions of an user.\n"
                        + "    contestants                          Get information about a student\n"
			+ "    help                                 Displays this help text.\n"
			+ "For more info about a specific command, run help <command>.";
	
	private static final Map<String, String> commandsHelp;
	
	static{
		commandsHelp = new HashMap<String, String>();
		
		commandsHelp.put("echo", Echo.HELPTEXT);
		commandsHelp.put("help", Help.HELPTEXT);
		commandsHelp.put("submissions", Submissions.HELPTEXT);
		commandsHelp.put("student", StudentInfo.HELPTEXT);
	}
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) {
		
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		String command = null;
		
		if(requestProperties != null && requestProperties.get("text") != null && 
			requestProperties.get("text").size() > 0){
			
			command = requestProperties.get("text").get(0);
			command = command.split(" ")[0];
			
			if(commandsHelp.containsKey(command)){
				responseProperties.put("text", commandsHelp.get(command));
				return responseProperties;
			}
		}
		
		responseProperties.put("text", HELPTEXT);
		return responseProperties;
	}

}
