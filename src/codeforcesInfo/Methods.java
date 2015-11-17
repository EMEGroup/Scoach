package codeforcesInfo;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import httpHandling.HttpRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Methods {
	
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
	
	private static final String CodeforcesURL;
	private static final String MethodsUrl;
	private static final String ProblemsURL;
	
	static{
		CodeforcesURL = "https://codeforces.com/";
		MethodsUrl = CodeforcesURL + "api/";
		ProblemsURL = CodeforcesURL + "problemset/problem/";
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
	
	private static ProblemVerdict translateVerdict( String verdict){
		if(verdict == null)	return null;
		
		switch( verdict ){
			case "AC":
				return ProblemVerdict.OK;
			case "WA":
				return ProblemVerdict.WRONG_ANSWER;
			case "TLE":
				return ProblemVerdict.TIME_LIMIT_EXCEEDED;
			case "MLE":
				return ProblemVerdict.MEMORY_LIMIT_EXCEEDED;
			case "CE":
				return ProblemVerdict.COMPILATION_ERROR;
			case "RTE":
				return ProblemVerdict.RUNTIME_ERROR;
			case "PE":
				return ProblemVerdict.PRESENTATION_ERROR;
			case "HACKED":
				return ProblemVerdict.CHALLENGED;
			default:
				return null;
		}
	}
	
	public static Map<String, String> getSubmissions(String handle, 
		Long startingTime, int count, String verdict, List<String> tags){
		
		if(handle == null || handle.isEmpty())	return null;
		
		count = Math.min( Math.abs(count), 1000 );
		if(tags == null)	tags = new ArrayList<String>();
		if(startingTime != null)
			startingTime = Math.abs(startingTime);
		
		Integer startingID = null;
		
		Map<String, String> _submissions = new HashMap<String, String>();
		
		// Do a binary search 
		if(startingTime != null){
			startingID = Math.max(binarySearch(handle, count, startingTime), 1);
		}
		
		httpHandling.HttpRequest conn;
		
		if(startingTime != null){
			conn = new HttpRequest(MethodsUrl + "user.status?" + 
				"handle=" + handle + "&count=" + count +
				"&from=" + startingID);
		}
		else{
			conn = new HttpRequest(MethodsUrl + "user.status?" + 
				"handle=" + handle + "&count=" + count);
		}
		
		conn.startConnection();
		
		try {
			Gson g = new Gson();
			JsonReader j = new JsonReader(
				new InputStreamReader(conn.getInputStream()) );
			
			user_status results = g.fromJson(j, user_status.class);
			
			// filter
			for(int i = 0; i < results.result.size(); i++){
				
				// check for verdict
				if(verdict != null){
					if(results.result.get(i).getVerdict() != translateVerdict(verdict)){
						results.result.remove(i);
						i--;
						continue;
					}
				}
				// check for creation time
				if(startingTime != null){
					if(results.result.get(i).getCreationTimeSeconds() < startingTime){
						results.result.remove(i);
						i--;
						continue;
					}
				}
				
				// check for tags
				if( !results.result.get(i).getProblem().getTags().containsAll(tags) ){
					results.result.remove(i);
					i--;
				}
			}
			
			// Sort
			results.result.sort(null);
			
			String text = "";
			
			for( Submission sub : results.result ){
				text +=
					"[" + sub.getProblem().getName() + "] " + 
					"[" + sub.getProblem().getIndex() + "] " +
					"[" + sub.getTimeConsumedMillis() + "ms] " +
					"[" + sub.getMemoryConsumedBytes() + "B] ";
				
				// This is a good part to test out the database stuff to avoid 
				// calling codeforces for info about the same contest many times.
				httpHandling.HttpRequest conn2 = new HttpRequest(
					MethodsUrl + "contest.standings?" +
						"contestId="+sub.getContestId() +
						"&from=1&count=1");
				conn2.startConnection();
				
				JsonReader j2 = new JsonReader(
					new InputStreamReader(conn2.getInputStream()));
				contest_standings result = g.fromJson(j2, contest_standings.class);
				
				if(result.result.contest.getWebsiteUrl() != null)
					text += "[" + result.result.contest.getWebsiteUrl() + "]";
				
				text += "\n";
			}
			
			_submissions.put("text", text);
			return _submissions;
			
		} catch (IOException ex) {
			Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
	}
	
	private static int binarySearch(String handle, int count, long secondsBack){
		
		try {
			HttpRequest conn;
			Gson g = new Gson();
			JsonReader j;
			user_status lastSubmission;
			
			int id = 1;
			long lastTime = System.currentTimeMillis()/1000 - secondsBack;
			
			while(true){
				conn = new HttpRequest(MethodsUrl+"user.status?"+
					"handle=" + handle + "&count=1000&from=" + id);
				conn.startConnection();
				
				j = new JsonReader(new InputStreamReader(conn.getInputStream()));
				lastSubmission = g.fromJson(j, user_status.class);
				
				if(lastSubmission.status.equals("FAILED") && lastSubmission.comment.equals("Call limit exceeded")){
					Thread.sleep(1000);
					
					conn = new HttpRequest(MethodsUrl+"user.status?"+
						"handle=" + handle + "&count=1000&from=" + id);
					conn.startConnection();

					j = new JsonReader(new InputStreamReader(conn.getInputStream()));
					lastSubmission = g.fromJson(j, user_status.class);
				}
				
				conn.disconnect();
				
				if(lastSubmission.result.size() <= 0){
					id -= 1000;
					break;
				}
				
				id += lastSubmission.result.size();
				
				if(lastSubmission.result.get( lastSubmission.result.size()-1).getCreationTimeSeconds() < lastTime )
					break;
			}
			
			return _binarySearch(handle, count, lastTime, 1, id);
			
		} catch (IOException ex) {
			Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
			// Handle bad usernames and stuff alike
		} catch (InterruptedException ex) {
			Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return 1;
	}
	
	private static int _binarySearch(String handle, int count, long from, int l, int r) throws IOException, InterruptedException{
		
		if(l >= r)
			return r - count;
		
		int mid = (l + r) / 2;
		
		//
		HttpRequest conn;
		Gson g;
		JsonReader j;
		user_status sub;
		
		conn = new HttpRequest(MethodsUrl + "user.status?handle=" + handle + "&count=" + count +"&from=" + mid);
		conn.startConnection();
		g = new Gson();
		j = new JsonReader(new InputStreamReader(conn.getInputStream()));
		sub = g.fromJson(j, user_status.class);
		
		if(sub.status.equals("FAILED") && sub.comment.equals("Call limit exceeded")){
			Thread.sleep(1000);
			System.out.println("DELAY");
			
			conn = new HttpRequest(MethodsUrl + "user.status?handle=" + handle + "&count=" + count +"&from=" + mid);
			conn.startConnection();
			g = new Gson();
			j = new JsonReader(new InputStreamReader(conn.getInputStream()));
			sub = g.fromJson(j, user_status.class);
		}
		
		if(sub.result == null || sub.result.size() <= 0){
			return _binarySearch(handle, count, from, l, mid-1);
		}
		
		long tmp = sub.result.get( 0 ).getCreationTimeSeconds();
		//
		
		if(tmp < from){
			return _binarySearch(handle, count, from, l, mid-1);
		}
		else{
			return _binarySearch(handle, count, from, mid+1, r);
		}
		
	}
}
