package com.jobdiva.api.model.chatbot;

@SuppressWarnings("serial")
public class ChatbotRecruiterData implements java.io.Serializable {
	
	private long	id;
	//
	private String	firstName;
	//
	private String	lastName;
	//
	private String	email;
	//
	private long	teamid;
	
	public long getId() {
		return id;
	}
	
	public void setId(long l) {
		this.id = l;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(long teamid) {
		this.teamid = teamid;
	}
}
