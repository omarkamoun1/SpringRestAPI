package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class JobUDF implements java.io.Serializable {
	
	private Long	teamId;
	private Long	rfqId;
	private Integer	userfieldId;
	private String	userfieldValue;
	private Date	dateIssued;
	private Date	dateUpdated;
	private Date	dateCreated;
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Long getRfqId() {
		return rfqId;
	}
	
	public void setRfqId(Long rfqId) {
		this.rfqId = rfqId;
	}
	
	public Integer getUserfieldId() {
		return userfieldId;
	}
	
	public void setUserfieldId(Integer userfieldId) {
		this.userfieldId = userfieldId;
	}
	
	public String getUserfieldValue() {
		return userfieldValue;
	}
	
	public void setUserfieldValue(String userfieldValue) {
		this.userfieldValue = userfieldValue;
	}
	
	public Date getDateIssued() {
		return dateIssued;
	}
	
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
