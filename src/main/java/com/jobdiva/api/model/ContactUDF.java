package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class ContactUDF implements java.io.Serializable {
	
	private Long	customerId;
	private Integer	userfieldId;
	private Long	teamid;
	private String	userfieldValue;
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Integer getUserfieldId() {
		return userfieldId;
	}
	
	public void setUserfieldId(Integer userfieldId) {
		this.userfieldId = userfieldId;
	}
	
	public Long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
	
	public String getUserfieldValue() {
		return userfieldValue;
	}
	
	public void setUserfieldValue(String userfieldValue) {
		this.userfieldValue = userfieldValue;
	}
}
