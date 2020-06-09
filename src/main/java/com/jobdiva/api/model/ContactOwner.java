package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class ContactOwner implements java.io.Serializable {
	
	private Long	customerId;
	private Long	recruiterId;
	private Long	teamId;
	private Boolean	isPrimaryOwner;
	private Boolean	insertMode;
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Boolean getIsPrimaryOwner() {
		return isPrimaryOwner;
	}
	
	public void setIsPrimaryOwner(Boolean isPrimaryOwner) {
		this.isPrimaryOwner = isPrimaryOwner;
	}
	
	public Boolean getInsertMode() {
		return insertMode;
	}
	
	public void setInsertMode(Boolean insertMode) {
		this.insertMode = insertMode;
	}
}
