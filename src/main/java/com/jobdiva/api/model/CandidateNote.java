package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class CandidateNote implements java.io.Serializable {
	
	private Long	noteId;
	private Long	candidateId;
	private Long	teamId;
	private Long	type;
	private Long	recruiterId;
	private Long	rfqId;
	private Date	dateCreated;
	private Long	recruiterTeamId;
	private Integer	auto;
	private Boolean	dirty;
	private Boolean	deleted;
	private Boolean	shared;
	private Date	dateAction;
	private Boolean	dirtyAttribute;
	private String	note;
	private String	noteClob;
	private Long	contactId;
	private Date	dimDateCreate;
	private Boolean	latestContactNotes;
	
	public Long getNoteId() {
		return noteId;
	}
	
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Long getType() {
		return type;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public Long getRfqId() {
		return rfqId;
	}
	
	public void setRfqId(Long rfqId) {
		this.rfqId = rfqId;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Long getRecruiterTeamId() {
		return recruiterTeamId;
	}
	
	public void setRecruiterTeamId(Long recruiterTeamId) {
		this.recruiterTeamId = recruiterTeamId;
	}
	
	public Integer getAuto() {
		return auto;
	}
	
	public void setAuto(Integer auto) {
		this.auto = auto;
	}
	
	public Boolean getDirty() {
		return dirty;
	}
	
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public Boolean getShared() {
		return shared;
	}
	
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	
	public Date getDateAction() {
		return dateAction;
	}
	
	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}
	
	public Boolean getDirtyAttribute() {
		return dirtyAttribute;
	}
	
	public void setDirtyAttribute(Boolean dirtyAttribute) {
		this.dirtyAttribute = dirtyAttribute;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNoteClob() {
		return noteClob;
	}
	
	public void setNoteClob(String noteClob) {
		this.noteClob = noteClob;
	}
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public Date getDimDateCreate() {
		return dimDateCreate;
	}
	
	public void setDimDateCreate(Date dimDateCreate) {
		this.dimDateCreate = dimDateCreate;
	}
	
	public Boolean getLatestContactNotes() {
		return latestContactNotes;
	}
	
	public void setLatestContactNotes(Boolean latestContactNotes) {
		this.latestContactNotes = latestContactNotes;
	}
}
