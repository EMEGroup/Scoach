package codeForcesInfo;

/*
 * Clase que modela la estructura de datos Contest.
 */
public class Contest {
	
	public enum type{
	CF,
	IOI,
	ICPC
	}
	
	public enum phase{
		BEFORE,
		CODING,
		PENDING_SYSTEM_TEST,
		SYSTEM_TEST,
		FINISHED
	}
	
	private int ID;
	private String name;
	private type type;
	private phase phase;
	private boolean frozen;
	private int durationSeconds;
	private String description;
	private int difficulty;
	private String kind;
	private String country;
	private String city;
	private String season;
	
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
	public type getType() {
		return type;
	}
	public void setType(type type) {
		this.type = type;
	}
	public phase getPhase() {
		return phase;
	}
	public void setPhase(phase phase) {
		this.phase = phase;
	}
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	public int getDurationSeconds() {
		return durationSeconds;
	}
	public void setDurationSeconds(int durationSeconds) {
		this.durationSeconds = durationSeconds;
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
