package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class JobContact implements java.io.Serializable {
	
	private Long	rfqId;
	private Long	customerId;
	private Long	teamId;
	private Integer	roleId;
	private String	role;
	private Boolean	showonJob;
	private Integer	action;
	private String	fullName;
	
	public Long getRfqId() {
		return rfqId;
	}
	
	public void setRfqId(Long rfqId) {
		this.rfqId = rfqId;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Boolean getShowonJob() {
		return showonJob;
	}
	
	public void setShowonJob(Boolean showonJob) {
		this.showonJob = showonJob;
	}
	
	public Integer getAction() {
		return action;
	}
	
	public void setAction(Integer action) {
		this.action = action;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
