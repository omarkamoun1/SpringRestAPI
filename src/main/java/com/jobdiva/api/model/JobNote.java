package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class JobNote implements java.io.Serializable {
	
	private Long	id;
	private Long	jobId;
	private Long	recruiterId;
	private String	recruiterName;
	private Date	dateCreated;
	private String	note;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public String getRecruiterName() {
		return recruiterName;
	}
	
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
