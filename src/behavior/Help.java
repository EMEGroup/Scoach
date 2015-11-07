package behavior;

import Misc.GeneralStuff;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help extends GeneralBehavior{

	public static final String helpText =
			"Available commands:\n"
			+ "    echo                                 Repeats what you write.\n"
			+ "    submissions                          Show submissions of an user.\n"
			+ "    help                                 Displays this help text.\n"
			+ "For more info about a specific command, run help <command>.";
	
	private static final Map<String, String> commandsHelp;
	
	static{
		commandsHelp = new HashMap<String, String>();
		
		commandsHelp.put("echo", Echo.helpText);
		commandsHelp.put("help", Help.helpText);
		commandsHelp.put("submissions", Submissions.helpText);
	}
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties) {
		
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		String command = null;
		
		if(requestProperties.get("text") != null && requestProperties.get("text").size() > 0){
			command = requestProperties.get("text").get(0);
			command = command.split(" ")[0];
		}
		
		if(command != null){
			if(commandsHelp.containsKey(command)){
				responseProperties.put("text", commandsHelp.get(command));
				return responseProperties;
			}
		}
		
		responseProperties.put("text", helpText);
		return responseProperties;
	}

}
