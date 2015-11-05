package codeForcesInfo;
/*
 * This class does model an user's data structure.
 */

public class User {
	private String handle;
	private String email;
	private String vkld;
	private String openId;
	private String firstName;
	private String lastName;
	private String country;
	private String city;
	private String organization;
	private int contribution;
	private String rank;
	private int rating;
	private String maxRank;
	private int maxRating;
	private long lastOnlineTimeSeconds; // Last time online
	private long registrationTimeSeconds; // Time since registration
	
	public User(String handle, String email, String vkld, String openId, String firstName, String lastName,
			String country, String city, String organization, int contribution, String rank, int rating, String maxRank,
			int maxRating, long lastOnlineTimeSeconds, long registrationTimeSeconds) {
		
		this.handle = handle;
		this.email = email;
		this.vkld = vkld;
		this.openId = openId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.organization = organization;
		this.contribution = contribution;
		this.rank = rank;
		this.rating = rating;
		this.maxRank = maxRank;
		this.maxRating = maxRating;
		this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
		this.registrationTimeSeconds = registrationTimeSeconds;
	}
	
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVkld() {
		return vkld;
	}
	public void setVkld(String vkld) {
		this.vkld = vkld;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public int getContribution() {
		return contribution;
	}
	public void setContribution(int contribution) {
		this.contribution = contribution;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getMaxRank() {
		return maxRank;
	}
	public void setMaxRank(String maxRank) {
		this.maxRank = maxRank;
	}
	public int getMaxRating() {
		return maxRating;
	}
	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}
	public long getLastOnlineTimeSeconds() {
		return lastOnlineTimeSeconds;
	}
	public void setLastOnlineTimeSeconds(long lastOnlineTimeSeconds) {
		this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
	}
	public long getRegistrationTimeSeconds() {
		return registrationTimeSeconds;
	}
	public void setRegistrationTimeSeconds(long registrationTimeSeconds) {
		this.registrationTimeSeconds = registrationTimeSeconds;
	}
}
