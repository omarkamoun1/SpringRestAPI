package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class CompanyUDF implements java.io.Serializable {
	
	private Integer	userfieldId;
	private Long	teamid;
	private String	userfieldValue;
	private Date	datecreated;
	
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
	
	public Date getDatecreated() {
		return datecreated;
	}
	
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
}
