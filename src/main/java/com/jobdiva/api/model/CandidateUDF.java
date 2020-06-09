package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class CandidateUDF implements java.io.Serializable {
	
	private Long	teamid;
	private Long	candidateId;
	private Integer	userFieldId;
	private String	userfieldValue;
	private Date	dateCreated;
	
	public Long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public Integer getUserFieldId() {
		return userFieldId;
	}
	
	public void setUserFieldId(Integer userFieldId) {
		this.userFieldId = userFieldId;
	}
	
	public String getUserfieldValue() {
		return userfieldValue;
	}
	
	public void setUserfieldValue(String userfieldValue) {
		this.userfieldValue = userfieldValue;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
