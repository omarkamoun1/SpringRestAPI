package com.jobdiva.api.model.v2.candidate;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class CandidateReferenceDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true) //
	private Long	candidateid;
	//
	@JsonProperty(value = "contactid", required = true) //
	private Long	contactid;
	//
	@JsonProperty(value = "createdByRecruiterid", required = true) //
	private Long	createdByRecruiterid;
	//
	@JsonProperty(value = "checkedByRecruiterid", required = false) //
	private Long	checkedByRecruiterid;
	//
	@JsonProperty(value = "dateChecked", required = false) //
	private Date	dateChecked;
	//
	@JsonProperty(value = "notes", required = false) //
	private String	notes;
	//
	@JsonProperty(value = "standardQuestions", required = false) //
	private String	standardQuestions;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Long getContactid() {
		return contactid;
	}
	
	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}
	
	public Long getCreatedByRecruiterid() {
		return createdByRecruiterid;
	}
	
	public void setCreatedByRecruiterid(Long createdByRecruiterid) {
		this.createdByRecruiterid = createdByRecruiterid;
	}
	
	public Long getCheckedByRecruiterid() {
		return checkedByRecruiterid;
	}
	
	public void setCheckedByRecruiterid(Long checkedByRecruiterid) {
		this.checkedByRecruiterid = checkedByRecruiterid;
	}
	
	public Date getDateChecked() {
		return dateChecked;
	}
	
	public void setDateChecked(Date dateChecked) {
		this.dateChecked = dateChecked;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getStandardQuestions() {
		return standardQuestions;
	}
	
	public void setStandardQuestions(String standardQuestions) {
		this.standardQuestions = standardQuestions;
	}
}
