/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package codeforcesInfo;

import codeforcesInfo.Methods.HackVerdict;

public class Hack {
	
	private int id;
	private long creationTime;
	private Party hacker;
	private Party defender;
	private HackVerdict verdict;
	private Problem problem;
	private String test;
	private JudgeProtocolResult judgeProtocol;
	
	public Hack(int id, long creationTime, Party hacker, Party defender, HackVerdict verdict, Problem problem, 
			String test, JudgeProtocolResult judgeProtocol){
		this.id = id;
		this.creationTime = creationTime;
		this.hacker = hacker;
		this.defender = defender;
		this.verdict = verdict;
		this.problem = problem;
		this.test = test;
		this.judgeProtocol = judgeProtocol;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	public Party getHacker() {
		return hacker;
	}
	public void setHacker(Party hacker) {
		this.hacker = hacker;
	}
	public Party getDefender() {
		return defender;
	}
	public void setDefender(Party defender) {
		this.defender = defender;
	}
	public HackVerdict getVerdict() {
		return verdict;
	}
	public void setVerdict(HackVerdict verdict) {
		this.verdict = verdict;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public JudgeProtocolResult getJudgeProtocol() {
		return judgeProtocol;
	}
	public void setJudgeProtocol(JudgeProtocolResult judgeProtocol) {
		this.judgeProtocol = judgeProtocol;
	}
	
	
	private class JudgeProtocolResult{
		public boolean manual;
		public String protocol, verdict;
	}
}
