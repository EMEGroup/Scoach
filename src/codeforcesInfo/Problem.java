package codeforcesInfo;

import codeforcesInfo.Methods.ProblemType;
import java.util.List;

public class Problem{
	
	private int contestId;
	private String index;
	private String name;
	private ProblemType type;
	private float points;
	private List<String> tags;
	
	public Problem(int contestID, String index, String name, ProblemType type, float points,
			List<String> tags) {
		
		this.contestId = contestID;
		this.index = index;
		this.name = name;
		this.type = type;
		this.points = points;
		this.tags = tags;
	}
	
	public int getContestId() {
		return contestId;
	}
	public void setContestId(int contestId) {
		this.contestId = contestId;
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
	public String getProblemLink(){
		return "";
	}
	
	public String forgeLink(){
		return Methods.PROBLEMSURL + contestId + "/" + index;
	}
	
	@Override
	public String toString(){
		
		// |     Contest ID  |   Index   | Problem Name | Link
		
		String str = 
			"|"+ contestId +"|"+ index +"|"+ name +"| "+ forgeLink() +" |";
		
		return str;
	}
}
