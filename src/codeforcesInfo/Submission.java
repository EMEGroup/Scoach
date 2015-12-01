package codeforcesInfo;

import codeforcesInfo.Methods.ProblemVerdict;

/*
 * Codeforces submission's data structure.
 */

public class Submission implements Comparable<Submission>{
	
	private int id;
	private int contestId;
	private long creationTimeSeconds;
	private long relativeTimeSeconds;
	private Problem problem;
	private Party author;
	private String programmingLanguage;
	private ProblemVerdict verdict;
	private int passedTestCount;
	private int timeConsumedMillis;
	private int memoryConsumedBytes;
	
	public Submission(int id, int contestId, long creationTimeSeconds, long relativeTimeSeconds, Problem problem,
			Party author, String programmingLanguage, ProblemVerdict verdict, int passedTestCount, int timeConsumedMillis,
			int memoryConsumedBytes) {
		
		this.id = id;
		this.contestId = contestId;
		this.creationTimeSeconds = creationTimeSeconds;
		this.relativeTimeSeconds = relativeTimeSeconds;
		this.problem = problem;
		this.author = author;
		this.programmingLanguage = programmingLanguage;
		this.verdict = verdict;
		this.passedTestCount = passedTestCount;
		this.timeConsumedMillis = timeConsumedMillis;
		this.memoryConsumedBytes = memoryConsumedBytes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContestId() {
		return contestId;
	}
	public void setContestId(int contestId) {
		this.contestId = contestId;
	}
	public long getCreationTimeSeconds() {
		return creationTimeSeconds;
	}
	public void setCreationTimeSeconds(long creationTimeSeconds) {
		this.creationTimeSeconds = creationTimeSeconds;
	}
	public long getRelativeTimeSeconds() {
		return relativeTimeSeconds;
	}
	public void setRelativeTimeSeconds(long relativeTimeSeconds) {
		this.relativeTimeSeconds = relativeTimeSeconds;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public Party getAuthor() {
		return author;
	}
	public void setAuthor(Party author) {
		this.author = author;
	}
	public String getProgrammingLanguage() {
		return programmingLanguage;
	}
	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}
	public ProblemVerdict getVerdict() {
		return verdict;
	}
	public void setVerdict(ProblemVerdict verdict) {
		this.verdict = verdict;
	}
	public int getPassedTestCount() {
		return passedTestCount;
	}
	public void setPassedTestCount(int passedTestCount) {
		this.passedTestCount = passedTestCount;
	}
	public int getTimeConsumedMillis() {
		return timeConsumedMillis;
	}
	public void setTimeConsumedMillis(int timeConsumedMillis) {
		this.timeConsumedMillis = timeConsumedMillis;
	}
	public int getMemoryConsumedBytes() {
		return memoryConsumedBytes;
	}
	public void setMemoryConsumedBytes(int memoryConsumedBytes) {
		this.memoryConsumedBytes = memoryConsumedBytes;
	}	

	@Override
	public int compareTo(Submission t) {
		if(this.creationTimeSeconds < t.creationTimeSeconds)
			return -1;
		else if(this.creationTimeSeconds == t.creationTimeSeconds)
			return 0;
		else
			return 1;
	}
}
