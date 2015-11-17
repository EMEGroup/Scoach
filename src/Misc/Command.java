package Misc;

import java.util.List;
import java.util.Map;

public interface Command {
	public abstract void execute(Map<String, List<String>> requestProperties);
}
