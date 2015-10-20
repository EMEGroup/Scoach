package codeForcesInfo;

import java.util.*;

public class Problem {
	public enum type{
		PROGRAMMING,
		QUESTION
	}
	private int contestID;
	private String index;
	private String name;
	private type type;
	private float points;
	private List<String> tags;
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
	public type getType() {
		return type;
	}
	public void setType(type type) {
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
	public Problem(int contestID, String index, String name, codeForcesInfo.Problem.type type, float points,
			List<String> tags) {
		super();
		this.contestID = contestID;
		this.index = index;
		this.name = name;
		this.type = type;
		this.points = points;
		this.tags = tags;
	}
	
	
}
