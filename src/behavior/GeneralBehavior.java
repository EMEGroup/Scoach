package behavior;

import java.util.List;
import java.util.Map;

public abstract class GeneralBehavior extends Thread {
	
	public abstract Map<String, String> Run(Map<String, List<String>> requestProperties);
}
	
