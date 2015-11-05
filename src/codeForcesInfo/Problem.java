package codeForcesInfo;

import codeForcesInfo.MethodResponse.ProblemType;
import java.util.List;

public class Problem {
	
	private int contestID;
	private String index;
	private String name;
	private ProblemType type;
	private float points;
	private List<String> tags;
	
	public Problem(int contestID, String index, String name, ProblemType type, float points,
			List<String> tags) {
		
		this.contestID = contestID;
		this.index = index;
		this.name = name;
		this.type = type;
		this.points = points;
		this.tags = tags;
	}
	
	public int getContestID() {
		return contestID;
	}
	public void setContestID(int contestID) {
		this.contestID = contestID;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProblemType getType() {
		return type;
	}
	public void setType(ProblemType type) {
		this.type = type;
	}
	public float getPoints() {
		return points;
	}
	public void setPoints(float points) {
		this.points = points;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
