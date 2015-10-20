package codeForcesInfo;

/*
 * Representa una participacion de un usuario en un concurso rated.
 */

public class RatingChange {
	public int contestID;
	public String contestName;
	public int rank;
	public int ratingUpdateTimeSeconds;
	public int oldRating;
	public int newRating;
	
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
	public int getRatingUpdateTimeSeconds() {
		return ratingUpdateTimeSeconds;
	}
	public void setRatingUpdateTimeSeconds(int ratingUpdateTimeSeconds) {
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
	public RatingChange(int contestID, String contestName, int rank, int ratingUpdateTimeSeconds, int oldRating,
			int newRating) {
		super();
		this.contestID = contestID;
		this.contestName = contestName;
		this.rank = rank;
		this.ratingUpdateTimeSeconds = ratingUpdateTimeSeconds;
		this.oldRating = oldRating;
		this.newRating = newRating;
	}
	
	
	
}
