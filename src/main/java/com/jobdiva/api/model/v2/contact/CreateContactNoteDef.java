package com.jobdiva.api.model.v2.contact;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class CreateContactNoteDef implements Serializable {
	
	@JsonProperty(value = "contactid", required = true)
	private Long	contactid;
	//
	@JsonProperty(value = "note", required = true)
	private String	note;
	//
	@JsonProperty(value = "recruiterid", required = false)
	private Long	recruiterid;
	//
	@JsonProperty(required = false, value = "action")
	private String	action;
	//
	@JsonProperty(required = false, value = "actionDate")
	private Date	actionDate;
	//
	@JsonProperty(required = false, value = "link2AnOpenJob")
	private Long	link2AnOpenJob;
	//
	@JsonProperty(required = false, value = "link2ACandidate")
	private Long	link2ACandidate;
	
	public Long getContactid() {
		return contactid;
	}
	
	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public Long getRecruiterid() {
		return recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Date getActionDate() {
		return actionDate;
	}
	
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
	public Long getLink2AnOpenJob() {
		return link2AnOpenJob;
	}
	
	public void setLink2AnOpenJob(Long link2AnOpenJob) {
		this.link2AnOpenJob = link2AnOpenJob;
	}
	
	public Long getLink2ACandidate() {
		return link2ACandidate;
	}
	
	public void setLink2ACandidate(Long link2aCandidate) {
		link2ACandidate = link2aCandidate;
	}
}
