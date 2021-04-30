package com.jobdiva.api.model.v2.candidate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class CreateCandidatesNoteDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true)
	private List<Long>	candidateids;
	//
	@JsonProperty(value = "note", required = true)
	private String		note;
	//
	@JsonProperty(value = "recruiterid", required = false)
	private Long		recruiterid;
	//
	@JsonProperty(value = "action", required = false)
	private String		action;
	//
	@JsonProperty(value = "actionDate", required = false)
	private Date		actionDate;
	//
	@JsonProperty(value = "link2AnOpenJob", required = false)
	private Long		link2AnOpenJob;
	//
	@JsonProperty(value = "link2AContact", required = false)
	private Long		link2AContact;
	//
	@JsonProperty(value = "setAsAuto", required = false)
	private Boolean		setAsAuto;
	
	public List<Long> getCandidateids() {
		return candidateids;
	}
	
	public void setCandidateids(List<Long> candidateids) {
		this.candidateids = candidateids;
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
	
	public Long getLink2AContact() {
		return link2AContact;
	}
	
	public void setLink2AContact(Long link2aContact) {
		link2AContact = link2aContact;
	}
	
	public Boolean getSetAsAuto() {
		return setAsAuto;
	}
	
	public void setSetAsAuto(Boolean setAsAuto) {
		this.setAsAuto = setAsAuto;
	}
}
