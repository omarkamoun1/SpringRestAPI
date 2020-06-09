package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class ContactNote implements java.io.Serializable {
	
	private Long	id;
	private Long	customerId;
	private Date	dateCreated;
	private Long	recruiterId;
	private Long	type;
	private String	tagUpdate;
	private String	note;
	private Long	recruiterTeamId;
	private Long	rfqId;
	private Long	emailMergeId;
	private Date	dateModified;
	private Boolean	deleted;
	private Long	candidateId;
	private Boolean	latestNotes;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public Long getType() {
		return type;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public String getTagUpdate() {
		return tagUpdate;
	}
	
	public void setTagUpdate(String tagUpdate) {
		this.tagUpdate = tagUpdate;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public Long getRecruiterTeamId() {
		return recruiterTeamId;
	}
	
	public void setRecruiterTeamId(Long recruiterTeamId) {
		this.recruiterTeamId = recruiterTeamId;
	}
	
	public Long getRfqId() {
		return rfqId;
	}
	
	public void setRfqId(Long rfqId) {
		this.rfqId = rfqId;
	}
	
	public Long getEmailMergeId() {
		return emailMergeId;
	}
	
	public void setEmailMergeId(Long emailMergeId) {
		this.emailMergeId = emailMergeId;
	}
	
	public Date getDateModified() {
		return dateModified;
	}
	
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public Boolean getLatestNotes() {
		return latestNotes;
	}
	
	public void setLatestNotes(Boolean latestNotes) {
		this.latestNotes = latestNotes;
	}
}
