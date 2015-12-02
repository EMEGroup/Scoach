package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Echo extends GeneralBehavior {
	
	public static final String HELPTEXT = "echo <text>\t\t\tRepeats what you write.";
	
	@Override
	public Map<String, String> Run(Map<String, List<String>> requestProperties){
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		responseProperties.put("text", HELPTEXT);
		
		if(requestProperties != null && requestProperties.get("text") != null){
			responseProperties.put("text", requestProperties.get("text").get(0));
		}

		return responseProperties;
	}
}
