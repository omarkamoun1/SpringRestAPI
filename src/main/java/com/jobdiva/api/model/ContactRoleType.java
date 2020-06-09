package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class ContactRoleType implements java.io.Serializable {
	
	private Long	contactId;
	private Long	roleId;
	private Boolean	showOnJob;
	private Integer	action;
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Boolean getShowOnJob() {
		return showOnJob;
	}
	
	public void setShowOnJob(Boolean showOnJob) {
		this.showOnJob = showOnJob;
	}
	
	public Integer getAction() {
		return action;
	}
	
	public void setAction(Integer action) {
		this.action = action;
	}
}
