package codeForcesInfo;

/*
 * Represents a participation of a user in a rated contest.
 */

public class RatingChange {
	public int contestID;
	public String contestName;
	public int rank;
	public long ratingUpdateTimeSeconds;
	public int oldRating;
	public int newRating;
	
	public RatingChange(int contestID, String contestName, int rank, 
			long ratingUpdateTimeSeconds, int oldRating, int newRating) {
		
		this.contestID = contestID;
		this.contestName = contestName;
		this.rank = rank;
		this.ratingUpdateTimeSeconds = ratingUpdateTimeSeconds;
		this.oldRating = oldRating;
		this.newRating = newRating;
	}
	
	public int getContestID() {
		return contestID;
	}
	public void setContestID(int contestID) {
		this.contestID = contestID;
	}
	public String getContestName() {
		return contestName;
	}
	public void setContestName(String contestName) {
		this.contestName = contestName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public long getRatingUpdateTimeSeconds() {
		return ratingUpdateTimeSeconds;
	}
	public void setRatingUpdateTimeSeconds(long ratingUpdateTimeSeconds) {
		this.ratingUpdateTimeSeconds = ratingUpdateTimeSeconds;
	}
	public int getOldRating() {
		return oldRating;
	}
	public void setOldRating(int oldRating) {
		this.oldRating = oldRating;
	}
	public int getNewRating() {
		return newRating;
	}
	public void setNewRating(int newRating) {
		this.newRating = newRating;
	}	
}
