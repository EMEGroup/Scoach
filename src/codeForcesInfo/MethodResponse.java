package codeForcesInfo;

import java.util.List;

public class MethodResponse {
	
	public enum ProblemType{
		PROGRAMMING,
		QUESTION
	}
	public enum ProblemVerdict{
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
	public enum ContestType{
		CF,
		IOI,
		ICPC
	}
	public enum ContestPhase{
		BEFORE,
		CODING,
		PENDING_SYSTEM_TEST,
		SYSTEM_TEST,
		FINISHED
	}
	public enum HackVerdict{
		HACK_SUCCESFUL,
		HACK_UNSUCCESFUL,
		INVALID_INPUT,
		GENERATOR_INCOMPILABLE,
		GENERATOR_CRASHED,
		IGNORED,
		TESTING,
		OTHER
	}
	public enum PartyType{
		CONTESTANT,
		PRACTICE,
		VIRTUAL,
		MANAGER,
		OUT_OF_COMPETITION
	}
	
	private class defaultResponse{
		public String status = "";
		public String comment = "";
	}
	
	private class contest_hacks extends defaultResponse{
		public List<Hack> result;
	}
	
	private class contest_list extends defaultResponse{
		public List<Contest> result;
	}
	
	private class contest_standings extends defaultResponse{
		public Standings result;
		
		private class Standings{
			public Contest contest;
			public List<Problem> problems;
			public List<RanklistRow> rows;
		}
	}
	
	private class contest_status extends defaultResponse{
		public List<Submission> result;
	}
	
	private class problemset_problems extends defaultResponse{
		public ProblemsInfo result;
		
		private class ProblemsInfo{
			public List<Problem> problems;
			public List<ProblemStatistics> problemStatistics;
		}
	}
	
	private class problemset_recentstatus extends defaultResponse{
		public List<Submission> result;
	}
	
	private class user_info extends defaultResponse{
		public List<User> result;
	}
	
	private class user_ratedlist extends defaultResponse{
		public List<User> result;
	}
	
	private class user_rating extends defaultResponse{
		public List<RatingChange> result;
	}
	
	private class user_status extends defaultResponse{
		public List<Submission> result;
	}
}
