package codeforcesInfo;

import codeforcesInfo.Methods.PartyType;
import java.util.List;

public class Party {
	
	private int contestID;
	private List<Member> members;
	private PartyType participantType;
	private int teamID;
	private String teamName;
	private boolean ghost;
	private int room;
	private long startTimeSeconds;
	
	public Party(int contestID, List<Member> members, PartyType participantType, int teamID, String teamName, boolean ghost,
			int room, long startTimeSeconds) {
		
		this.contestID = contestID;
		this.members = members;
		this.participantType = participantType;
		this.teamID = teamID;
		this.teamName = teamName;
		this.ghost = ghost;
		this.room = room;
		this.startTimeSeconds = startTimeSeconds;
	}
	
	public int getContestID() {
		return contestID;
	}
	public void setContestID(int contestID) {
		this.contestID = contestID;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public PartyType getParticipantType() {
		return participantType;
	}
	public void setParticipantType(PartyType participantType) {
		this.participantType = participantType;
	}
	public int getTeamID() {
		return teamID;
	}
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public long getStartTimeSeconds() {
		return startTimeSeconds;
	}
	public void setStartTimeSeconds(long startTimeSeconds) {
		this.startTimeSeconds = startTimeSeconds;
	}
}
