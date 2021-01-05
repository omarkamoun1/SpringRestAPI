package com.jobdiva.api.model.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotAnswer implements java.io.Serializable {
	
	@JsonProperty(value = "questionid", index = 0)
	private Integer	questionID;
	//
	@JsonProperty(value = "answer", index = 1)
	private String	answer;
	//
	@JsonProperty(value = "teamid", index = 2)
	private long	teamid;
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(long teamid) {
		this.teamid = teamid;
	}
	
	public Integer getQuestionID() {
		return questionID;
	}
	
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
}
