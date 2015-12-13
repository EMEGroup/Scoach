package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommendations extends GeneralBehavior{
	
	public static final String HELPTEXT = 
		"```"
		+ "recommend        Recommends problems to a group based on amount of users that have solved it.\n"
		+ "USAGE:\n"
		+ "recommend <groupname> --popularity <1 .. 5> --tags <comma separated list of problem types>\n"
		+ "--popularity     A number from 1 to 5 representing an range where the problem's acceptance amount must lay within.\n"
		+ "\t\t1 => 'More than 4500 users have solved this problem.'\n"
		+ "\t\t2 => 'More than 3500 users have solved this problem.'\n"
		+ "\t\t3 => 'More than 2000 users have solved this problem.'\n"
		+ "\t\t4 => 'More than 1000 users have solved this problem.'\n"
		+ "\t\t5 => 'Less than 1000 users have solved this problem.'\n"
		+ "```";
	
    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) throws Exception {
        
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		return responseProperties;
    }
    
}
