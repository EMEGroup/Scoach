package codeforcesInfo;

import Misc.GeneralStuff;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
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
		FAILED("FAILED"),
		OK("AC"),
		PARTIAL("PARTIAL"),
		COMPILATION_ERROR("CE"),
		RUNTIME_ERROR("RE"),
		WRONG_ANSWER("WA"),
		PRESENTATION_ERROR("PE"),
		TIME_LIMIT_EXCEEDED("TLE"),
		MEMORY_LIMIT_EXCEEDED("MLE"),
		IDLENESS_LIMIT_EXCEEDED("IDLENESS_LIMIT_EXCEEDED"),
		SECURITY_VIOLATED("SECURITY_VIOLATED"),
		CRASHED("CRASHED"),
		INPUT_PREPARATION_CRASHED("INPUT_PREPARATION_CRASHED"),
		CHALLENGED("HACKED"),
		SKIPPED("SKIPPED"),
		TESTING("TESTING"),
		REJECTED("REJECTED");
		
		private final String name;
		private ProblemVerdict(String name){
			this.name = name;
		}
		
		public String getValue(){
			return this.name;
		}
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
	
	public static final String CODEFORCESURL;
	public static final String METHODSURL;
	public static final String PROBLEMSURL;
	
	static{
		CODEFORCESURL = "https://codeforces.com/";
		METHODSURL = CODEFORCESURL + "api/";
		PROBLEMSURL = CODEFORCESURL + "problemset/problem/";
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
	
	public static Map<String, String> getSubmissionsReport(String handle, 
		Long startingTime, Integer count, String verdict, List<String> tags) throws IOException{
		
		Map<String, String> returnObject = new HashMap<String,String>();
		String submissionsUrl;
		
		if(handle == null || handle.isEmpty())	return null;
		if(count != null && count <= 0)	return null;	// count >= 1 is ok
		
		if(verdict != null)
			verdict = verdict.toUpperCase();
		
		submissionsUrl = METHODSURL + "user.status?" + 	"handle=" + handle;
		
		// Check if the verdict is a valid one
		if( verdict != null ){
			boolean flag = false;
			
			for( ProblemVerdict e : ProblemVerdict.values() ){
				if( e.getValue().equals(verdict) == true ){
					flag = true;
					break;
				}
			}
			
			if( flag == false ){
				returnObject.put("text", 
					"The passed VERDICT does not exist in codeforces or is not valid.");
				return returnObject;
			}
		}
		
		// Test with a request to codeforces, validating handle and stuff
		user_status test = 
			GeneralStuff.getResponseObject(submissionsUrl, user_status.class);
		
		// A problem ocurred with the last request
		if( test.status.equals("OK") == false ){
			String comment = test.comment;
			String cause = comment.split("[^a-zA-Z]")[0];
			
			if( cause.equals("handle") ){
				returnObject.put("text", 
					"The passed handle/nickname does not exist in codeforces.");
				return returnObject;
			}
		}
		
		if(startingTime != null){
			startingTime = Math.abs(startingTime);
		}
		
		List<Submission> submissionsInRange = 
			getSubmissions(handle, startingTime, count, verdict, tags);
		
		class CollapsedSubmission{
			// This class is a work around due to the java's lack of a 
			// general and simple Pair class or object ... 
			public Integer quantity;
			public Submission submission;
			public CollapsedSubmission(Integer _quantity, Submission _submission){
				this.quantity = _quantity;
				this.submission = _submission;
			}
		}
		
		// This map holds the info ready to be returned/printed
		EnumMap<ProblemVerdict, List<CollapsedSubmission>> summarizedMap = 
			new EnumMap<ProblemVerdict, 
				List<CollapsedSubmission>>(ProblemVerdict.class);
		
		// *** Group by verdict ***
		EnumMap<ProblemVerdict, List<Submission>> agrupationMap = 
			new EnumMap<ProblemVerdict, List<Submission>>(ProblemVerdict.class);
		
		// Ensure there's no null list inside the EnumMaps
		for(ProblemVerdict pv : ProblemVerdict.values()){
			agrupationMap.put(pv, new ArrayList<Submission>());
			summarizedMap.put(pv, new ArrayList<CollapsedSubmission>());
		}
		
		// Add every submission to it's corresponding verdict
		for(Submission sub : submissionsInRange)
			agrupationMap.get(sub.getVerdict()).add(sub);
		
		// *** Collapse the submissions by problem and count them ***
		// For each verdict, sort from newest to oldest, count the amount of
		// submissions of each problem and keep only the newest submission.
		
		Iterator<ProblemVerdict> it = agrupationMap.keySet().iterator();
		
		while( it.hasNext() ){
			ProblemVerdict pv = it.next();
			List<Submission> tmpList = agrupationMap.get(pv);
			
			// *** Sort by the list from newest to oldest ***
			Collections.sort(tmpList);
			
			for(int i = 0; i < tmpList.size(); i++){
				int cant = 1;
				
				for(int j = i+1; j < tmpList.size(); j++){
					String FirstSubName = 
						tmpList.get(i).getProblem().getName();
					String SecondSubName = 
						tmpList.get(j).getProblem().getName();
					
					if( FirstSubName.equals(SecondSubName) ){
						cant++;
						tmpList.remove(j);
						j--;
					}
					
				}
				
				// Add the now summarized submission to the final map,
				// no need since it was sorted before and the iteration
				// follows the natural order 
				summarizedMap.get(pv).add( 
					new CollapsedSubmission(cant, tmpList.get(i)) );
				
			}
			
		}
		
		// *** Print the verdict and the count of each submissions ***
		// Assemble the response message if there are submissions to return ...
		
		String text = "";
		if( submissionsInRange.isEmpty() == false ){
			
			String columnIDs = "QUANT | " + "PROBLEM NAME ";
			
			// String formatting stuff ...
			for(int i = 0; i < 30 - "PROBLEM NAME ".length(); i++)
				columnIDs += " ";
			
			columnIDs += "| PROBLEM INDEX | " + "TAGS | " + "LINK\n";
			
			for(int i = 0; i < 70; i++)	
				columnIDs += "-";	// Title divisor line
			columnIDs += "\n";
			
			String textBody = "";
			for(ProblemVerdict pv : summarizedMap.keySet()){
				if( summarizedMap.get(pv).isEmpty() )	continue;
				
				// Print the verdict
				for(int i = 0; i < 55; i++) textBody += "*";
				textBody += " " + pv.getValue() + " ";
				
				for(int i = 0; i < 55 - pv.getValue().length(); i++) 
					textBody += "*";
				textBody += "\n";
				
				// Print it's submissions
				for( CollapsedSubmission cs : summarizedMap.get(pv) ){

					textBody +=
						"[" + cs.quantity + "]";
					
					// String formatting stuff ...
					for(int x = 0; x < 5 - cs.quantity.toString().length(); x++)
						textBody += " ";
					textBody += " ";
					
					textBody += "[" + cs.submission.getProblem().getName() + "]";
						
					// Some string formatting stuff ...
					for(int i = 0; i < 30 - cs.submission.getProblem().getName().length(); i++)
						textBody += " ";
					
					textBody +=
						"[" + cs.submission.getProblem().getIndex() + "] ";

					textBody += "[";
					List<String> _tags = cs.submission.getProblem().getTags();
					for(int j = 0; j < _tags.size(); j++){
						String tag = _tags.get(j);
						textBody += tag;

						if( j < _tags.size()-1 )
							textBody += ", ";
					}
					textBody += "] ";

					// Add link
					textBody += "[" + cs.submission.getProblem().forgeLink() + "]";

					textBody += "\n";
				}
			}
			
			textBody += "END OF LIST.";
			
			text = columnIDs + textBody;		
		}

		returnObject.put("text", text);
		return returnObject;
	}
	
	public static List<Submission> getSubmissions(String handle, 
		Long startingTime, Integer count, String verdict, List<String> tags) throws IOException{
		
		int bufferStep = 1000;	// How many submissions to query every request
		List<Submission> submissionsList = new ArrayList<Submission>();
		String submissionsUrl = 
			METHODSURL + "user.status?" + 	"handle=" + handle;
		
		if( tags == null )
			tags = new ArrayList<String>();
		
		// Go fetching submissions chunks of 1000 submissions each to get all
		// submissions from a user since the time specified by the caller
		if(startingTime != null){
			user_status tmpSubmissions;
			int startingId = 1;
			
			while(true){
				
				tmpSubmissions = GeneralStuff.getResponseObject(
					submissionsUrl + "&count=" + bufferStep + "&from=" + startingId, 
					user_status.class);
				
				while(tmpSubmissions.status.equals("FAILED") && 
					tmpSubmissions.comment.equals("Call limit exceeded")){
					
					try {
						Thread.sleep(250);
					} catch (InterruptedException ex) {
						Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					tmpSubmissions = GeneralStuff.getResponseObject(
						submissionsUrl + "&count=" + bufferStep + "&from=" + startingId, 
						user_status.class);
				}
				
				// There's no more submissions
				if(tmpSubmissions.result.isEmpty()){
					break;
				}else{
					submissionsList.addAll( tmpSubmissions.result );
				}
				
				startingId = submissionsList.size()+1;
				
				if(tmpSubmissions.result.get(tmpSubmissions.result.size()-1).getCreationTimeSeconds() < startingTime )
					break;
			}
		}
		// If no starting time set by the user then fetch everything at once
		else{
			user_status tmpSubmissions;
			int bufferCount = (count == null) ? bufferStep : 
				Math.max(bufferStep, count);
			int startingId = 1;
			
			while(true){
				
				if( count != null ){
					if(startingId > count) break;
				}
				
				tmpSubmissions = GeneralStuff.getResponseObject(
					submissionsUrl + "&count=" + bufferCount +
					"&from=" + startingId, 
					user_status.class);
				
				while(tmpSubmissions.status.equals("FAILED") && 
					tmpSubmissions.comment.equals("Call limit exceeded")){
					
					try {
						Thread.sleep(250);
					} catch (InterruptedException ex) {
						Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
					}
					
					tmpSubmissions = GeneralStuff.getResponseObject(
						submissionsUrl + "&count=" + bufferCount +
						"&from=" + startingId, 
						user_status.class);
				}
				
				if( tmpSubmissions.result != null ){
					submissionsList.addAll(tmpSubmissions.result);
				
				// There's no more submissions 
				if(tmpSubmissions.result.isEmpty())	break;
				
				startingId += tmpSubmissions.result.size();
				bufferCount = (count == null) ? bufferStep : 
					Math.min(bufferStep, count - startingId + 1);
				}
				else{
					break;
				}
				
				// This is the rest of submissions, no more to be found.
				if(tmpSubmissions.result.size() < bufferStep)	break;
			}
		}
		
		// Sort based on the Submission.compareTo method.
		// The submissions will be arranged from newest to oldest
		Collections.sort(submissionsList);
		
		if(startingTime != null){
			// Find the very first submission within the time range
			int firstIndex = 0;
			
			Comparator<Submission> comparator = new Comparator<Submission>() {
				@Override
				public int compare(Submission t1, Submission t2) {
					if( t1.getCreationTimeSeconds() > t2.getCreationTimeSeconds())
						return -1;
					else if(t1.getCreationTimeSeconds() == t2.getCreationTimeSeconds())
						return 0;
					else
						return 1;
				}
			};
			
			if( submissionsList.isEmpty() == false ){
				Submission fakeSubmissionStoringTime;
				
				fakeSubmissionStoringTime = submissionsList.get(0);
				fakeSubmissionStoringTime.setCreationTimeSeconds(startingTime);
				
				firstIndex = 
					GeneralStuff._lowerBound(submissionsList, 
						fakeSubmissionStoringTime, comparator);
			}
			
			// Get rid of the submissions laying outside of the time range
			submissionsList = submissionsList.subList(0, firstIndex);
			
			// Reverse the order so you start at the oldest one inside the time range
			Collections.reverse(submissionsList);
		}
		
		// Filter by verdict, tags, etc ...
		for(int i = 0; i < submissionsList.size(); ){
			
			// Check for verdict
			if(verdict != null){
				if(submissionsList.get(i).getVerdict().getValue().equals(verdict) == false){
					submissionsList.remove(i);
					continue;
				}
			}

			// Check for tags
			if( !submissionsList.get(i).getProblem().getTags().containsAll(tags) ){
				submissionsList.remove(i);
				continue;
			}
			
			i++;
		}
		
		// Return only the last <count> submissions, since those are the 
		// required by the caller, omit if --all was passed (count == null)
		
		if( count != null )
			submissionsList = submissionsList.subList(0, 
				Math.max(0, Math.min(submissionsList.size(), count)));
		
		return submissionsList;
	}
	
	public static List<SimpleEntry<Problem, ProblemStatistics> > getProblems(List<String> tags) throws IOException{
		
		List<SimpleEntry<Problem, ProblemStatistics>> returnObject = 
			new ArrayList<SimpleEntry<Problem, ProblemStatistics>>();
		
		String problemsURL = METHODSURL + "problemset.problems" ;
		
		if( tags != null && tags.isEmpty() == false ){
			problemsURL += "?tags=";
			
			for(String t : tags)
				problemsURL += t + ";";
		}
		
		problemset_problems problems = GeneralStuff.getResponseObject(problemsURL, problemset_problems.class);
		
		if( problems == null )
			return null;
		else if(problems.result == null)
			return null;
		else if(problems.result.problems == null)
			return null;
		
		for(int i = 0; i < problems.result.problems.size(); i++){
			Problem pr = problems.result.problems.get(i);
			ProblemStatistics ps = problems.result.problemStatistics.get(i);
			
			returnObject.add(
				new SimpleEntry<Problem, ProblemStatistics>(pr, ps) );
		}
		
		return returnObject;
	}
	
}
