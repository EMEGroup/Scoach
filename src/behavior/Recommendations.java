package behavior;

import Misc.BD;
import Misc.GeneralStuff;
import codeforcesInfo.Methods;
import codeforcesInfo.Methods.ProblemVerdict;
import codeforcesInfo.Problem;
import codeforcesInfo.ProblemStatistics;
import codeforcesInfo.Submission;
import java.io.IOException;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.mail.MessagingException;
import netHandling.smtpMailSend;

public class Recommendations extends GeneralBehavior{
	
	public static final String HELPTEXT = 
		"```"
		+ "recommend        Recommends problems to a group based on amount of users that have solved it.\n"
		+ "USAGE:\n"
		+ "recommend <groupname> [--amount <number>] --popularity <1 .. 5> --tags <comma separated list of problem types>\n"
		+ "--popularity    <1..5>  A number from 1 to 5 representing an range where the problem's acceptance amount must lay within.\n"
		+ "\t\t1 => 'More than 4500 users have solved this problem.'\n"
		+ "\t\t2 => 'More than 3500 users have solved this problem.'\n"
		+ "\t\t3 => 'More than 2000 users have solved this problem.'\n"
		+ "\t\t4 => 'More than 1000 users have solved this problem.'\n"
		+ "\t\t5 => 'Less than 1000 users have solved this problem.'\n"
		+ "--amount        <amount> Tells the bot to try to recommend at least <amount> problems. Tries to recommend 3 by default.\n"
		+ "```";
	
	private static final String POPULARITYERRORMSG = "```"
		+ "The popularity number passed is not valid, expected an integer "
		+ "between 1 and 5."
		+ "```";
	private static final String GROUPNAMEERRORMSG = "```"
		+ "The groupname passed seems to not exist in the database, "
		+ "check it was correctly spelled and mind the case of the letters too."
		+ "```";
	private static final String TOOSPECIFICMSG = "```"
		+ "No problems were found to recommend, that could mean the group specified "
		+ "is so damn strong that they have solved every single problem in codeforces.com "
		+ "that fits the popularity and tags specified and that no member of the "
		+ "team has already solved ... but we know this could not the case so try "
		+ "being a bit more flexible with the restrictions next time and "
		+ "check out your tags, ok ? :)"
		+ "```";
	private static final String NOPROBLEMSMSG = "```"
		+ "No problems were found to recommend, i believe it's codeforces fault "
		+ "so you may be proud of yourself :). Well back to business, you may be "
		+ "able to issue your command and recieve some results later ... but who knows."
		+ "```";
	private static final String AMOUNTINVALIDMSG = "```"
		+ "It seems you have passed an invalid amount of problems to recommend, "
		+ "i could have ignored that and still have done my work, but you know  ..."
		+ "i decided it's better to notify you about this, have a nice day btw. :)"
		+ "```";
	private static final String NOCONTESTANTSMSG = "```"
		+ "Sorry but the passed groupname, even existing, does not contain or reference "
		+ "any contestant, in other words it is an empty group, please wolve this "
		+ "out and try again."
		+ "```";
	
	private class ProblemStatsInfo implements Comparable<ProblemStatsInfo>{
		private Problem problem;
		private ProblemStatistics problemStatistics;

		public ProblemStatsInfo(Problem problem, ProblemStatistics problemStatistics) {
			this.problem = problem;
			this.problemStatistics = problemStatistics;
		}
		
		public Problem getProblem() {
			return problem;
		}
		public void setProblem(Problem problem) {
			this.problem = problem;
		}
		public ProblemStatistics getProblemStatistics() {
			return problemStatistics;
		}
		public void setProblemStatistics(ProblemStatistics problemStatistics) {
			this.problemStatistics = problemStatistics;
		}

		@Override
		public int compareTo(ProblemStatsInfo t) {
			int v1 = this.getProblemStatistics().getSolvedCount();
			int v2 = t.getProblemStatistics().getSolvedCount();
			
			// This sorts the problems from hardest to easiest
			if( v1 < v2)	return -1;
			else if( v1 == v2 )	return 0;
			else	return 1;
		}
		
	}
	
    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) throws MessagingException, IOException, SQLException, ClassNotFoundException{
        
		Map<String, String> responseProperties = new HashMap<String, String>();
		
		String groupName = "";
		int amount = 3;
		int popularity = 1;
		List<String> tags = null;
		List<String> members = null;
		
		BD databaseInstance = new BD();
		
		/*
		TODO:
		O Conseguir nombres de estudiantes dado un grupo.
		O Conseguir todos los problemas de codeforces.
		O Filtar los problemas que quedan por los tags dados.
		O Ignorar todos los problemas resueltos entre el conjunto del grupo.
		X Determinar el peor de los miembros del equipo.
		O Ordenar por dificultad. (Cantidad de gente que lo ha resuelto)
		O Escoger alguno(s) ...
		O Enviar mensajes a los integrantes del grupo (permitir al usuario no hacerlo)
		O Descomentar la salida a Slack
		*/
		
		if( requestProperties.get("--groupname") != null ){
			groupName = requestProperties.get("--groupname").get(0);
		}
		
		if( requestProperties.get("--popularity") != null ){
			popularity = Integer.parseInt(
				requestProperties.get("--popularity").get(0) );
		}
		
		if( requestProperties.get("--tags") != null ){
			tags = requestProperties.get("--tags");
		}
		
		if( requestProperties.get("--amount") != null ){
			amount = Integer.parseInt(
				requestProperties.get("--amount").get(0) );
		}
			
		// Get all problems
		List<SimpleEntry<Problem, ProblemStatistics>> problemsByTag = 
			Methods.getProblems(tags);
		
		List<ProblemStatsInfo> problemsList = new ArrayList<ProblemStatsInfo>();
		
		for(SimpleEntry<Problem, ProblemStatistics> entry : problemsByTag){
			problemsList.add( 
				new ProblemStatsInfo(entry.getKey(), entry.getValue()) );
		}
		
		// Sanity check
		if (popularity > 5 || popularity < 1)
			return forgeErrorMessage(POPULARITYERRORMSG);
		else if ( databaseInstance.GroupExists(groupName) == false )
			return forgeErrorMessage(GROUPNAMEERRORMSG);
		else if ( amount < 0 )
			return forgeErrorMessage(AMOUNTINVALIDMSG);
		else if ( problemsByTag == null || problemsByTag.isEmpty() )
			return forgeErrorMessage(NOPROBLEMSMSG);
		
		// Get the contestant's nicknames
		members = databaseInstance.getNicksInGroup(groupName, "codeforces");
		if( members.isEmpty() )
			return forgeErrorMessage(NOCONTESTANTSMSG);
		
		// Wipe out the problems already solved by the contestants
		for(String handle : members){
			
			List<Submission> subs = Methods.getSubmissions(handle, null, null, null, null);
			
			// Keep only the accepted ones
			for(int i = 0; i < subs.size(); i++){
				if( subs.get(i).getVerdict() != ProblemVerdict.OK ){
					subs.remove(i);
					i--;
				}
			}
			
			List<Problem> probs = new ArrayList<Problem>();
			
			// Get a list of the problems not repeating them more than once
			for(Submission sub : subs){
				if( probs.contains(sub.getProblem()) == false )
					probs.add(sub.getProblem());
			}
			
			// Remove the solved ones from the potential recommendations
			problemsList.removeAll(probs);
			
		}
		
		// Sort by the amount of users that have solved those problems
		Collections.sort(problemsList);
		
		// Choose some problem ... search for the minimun popularity needed
		
		int popularityNumber = getMinimunAmount(popularity);
		
		Problem pr = problemsList.get(0).getProblem();
		ProblemStatistics ps = problemsList.get(0).getProblemStatistics();
		
		ps.setSolvedCount(popularityNumber);
		
		ProblemStatsInfo mockObject = new ProblemStatsInfo(pr, ps);
		
		int firstIndex = // First index of the popularity range minus 1
			GeneralStuff._lowerBound(problemsList, mockObject);
		
		if( popularity < 5 ){	// At least propularityNumber
			
			// The next easier popularity number
			ps.setSolvedCount( getMinimunAmount(popularity-1) );
			
			mockObject.setProblemStatistics(ps);
			
			int lastIndex = // The last problem within the popularity range
				GeneralStuff._lowerBound(problemsList, mockObject);
			
			problemsList = problemsList.subList(firstIndex+1, lastIndex);
			
		} else{	// Up to popularityNumber
			problemsList = problemsList.subList(0, firstIndex+1);
		}
		
		// When all the filtering is done, if no problems to recommend ... :/
		if( problemsList.isEmpty() )
			return forgeErrorMessage(TOOSPECIFICMSG);
		
		// Return some info about this problem to the Slack and to the users emails
		// so the contestants can identify the problem and find it with ease.
		// return a link too, use the forgeLink method in Problems.
		
		int randomIndex;
		Random randomInstance = new Random( System.currentTimeMillis() );
		List<Problem> choosenOnes = new ArrayList<Problem>();
		
		for(int i = 0; i < amount && problemsList.size() > 0; i++){
			randomIndex = randomInstance.nextInt( problemsList.size() );
			choosenOnes.add(problemsList.get(randomIndex).getProblem());
			problemsList.remove( randomIndex );
		}
		
		String text = "I've found that this problems could be recommended to " 
			+ groupName +" :\n";
		
		// Add the choosen problems
		String problemsSummary = "";
		for(Problem problem : choosenOnes)
			problemsSummary += problem.toString() + "\n";
		
		responseProperties.put("text", "```" + text + problemsSummary + "```");
		// Send an email to the contestants with the suggested problems
		
		String[] recipients = new String[]{"ScoachBot@openmailbox.org"};
		String sender = GeneralStuff.EMAILSENDERNAME;
		String subject = "Here are some new problems for you to try out !";
		String msgTxt =  "Hi, your coach has asked me to recommend you some "
			+ "problems, here go my suggestions:\n\n" + problemsSummary
			+ "\nHave a nice day :^]";
		
		smtpMailSend mailSender = new smtpMailSend(recipients, sender, subject, msgTxt);
		
		try {
			mailSender.postMail();
		} catch( Exception e ){	// If an exception ocurred, try one more time
			
			try{
				mailSender.postMail();
			} catch (Exception ex){	// If no luck ... notify the elders
				throw new MessagingException("An error ocurred while sending "
					+ "the messages to the contestants.");
			}
			finally{
				return responseProperties;
			}
		}
		
		return responseProperties;
    }
	
	private int getMinimunAmount( int popularity ){
		
		switch( popularity ){
			// This serves for the lower bound to find the next difficulty range
			// so we can choose within from everything before it.
			case 0:		return Integer.MAX_VALUE;	
			case 1:		return 4500;
			case 2:		return 3500;
			case 3:		return 2000;
			case 4:		return 1000;
			case 5:		return 1000;
		}
		
		return 0;	// else return 0, this will take every single problem
	}
	
	private Map<String, String> forgeErrorMessage(String errorMessage){
		
		Map<String, String> returnObject = new HashMap<String, String>();
		returnObject.put("text", errorMessage);
		
		return returnObject;
	}
	
}
