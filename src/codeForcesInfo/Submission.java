package codeForcesInfo;
/*
 * Estructura del dato submission de CodeForces.
 */

public class Submission {
	public enum verdictEnum{
		FAILED,
		OK,
		PARTIAL,
		COMPILATION_ERROR,
		RUNTIME_ERROR,
		WRONG_ANSWER,
		PRESENTATION_ERROR,
		TIME_LIMIT_EXCEEDED,
		MEMORY_LIMIT_EXCEEDED,
		IDLENESS_LIMIT_EXCEEDED,
		SECURITY_VIOLATED,
		CRASHED,
		INPUT_PREPARATION_CRASHED,
		CHALLENGED,
		SKIPPED,
		TESTING,
		REJECTED
	}
	private int id;
	private int contestId;
	private int creationTimeSeconds;
	private int relativeTimeSeconds;
	private Problem problem;
	private Party author;
	private String programmingLanguage;
	private verdictEnum verdict;
	private int passedTestCount;
	private int timeConsumedMillis;
	private int memoryConsumedBytes;
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
	public int getCreationTimeSeconds() {
		return creationTimeSeconds;
	}
	public void setCreationTimeSeconds(int creationTimeSeconds) {
		this.creationTimeSeconds = creationTimeSeconds;
	}
	public int getRelativeTimeSeconds() {
		return relativeTimeSeconds;
	}
	public void setRelativeTimeSeconds(int relativeTimeSeconds) {
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
	public verdictEnum getVerdict() {
		return verdict;
	}
	public void setVerdict(verdictEnum verdict) {
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
	public Submission(int id, int contestId, int creationTimeSeconds, int relativeTimeSeconds, Problem problem,
			Party author, String programmingLanguage, verdictEnum verdict, int passedTestCount, int timeConsumedMillis,
			int memoryConsumedBytes) {
		super();
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
	
	
	
}
