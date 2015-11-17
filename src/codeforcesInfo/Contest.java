package codeforcesInfo;

import codeforcesInfo.Methods.ContestPhase;
import codeforcesInfo.Methods.ContestType;

/*
 * This class does model a contest's data structure.
 */
public class Contest {
	
	private int ID;
	private String name;
	private ContestType type;
	private ContestPhase phase;
	private boolean frozen;
	private long durationSeconds;
	private String websiteUrl;
	private String description;
	private int difficulty;
	private String kind;
	private String country;
	private String city;
	private String season;
	
	public Contest(int ID, String name, ContestType type, ContestPhase phase, boolean frozen, long durationSeconds, 
			String websiteUrl, String description, int difficulty, String kind, String country, String city, String season){
		
		this.ID = ID;
		this.name = name;
		this.type = type;
		this.phase = phase;
		this.frozen = frozen;
		this.durationSeconds = durationSeconds;
		this.websiteUrl = websiteUrl;
		this.description = description;
		this.difficulty = difficulty;
		this.kind = kind;
		this.country = country;
		this.city = city;
		this.season = season;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ContestType getType() {
		return type;
	}
	public void setType(ContestType type) {
		this.type = type;
	}
	public ContestPhase getPhase() {
		return phase;
	}
	public void setPhase(ContestPhase phase) {
		this.phase = phase;
	}
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	public long getDurationSeconds() {
		return durationSeconds;
	}
	public void setDurationSeconds(long durationSeconds) {
		this.durationSeconds = durationSeconds;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
}
