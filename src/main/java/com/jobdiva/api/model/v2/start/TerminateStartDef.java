package com.jobdiva.api.model.v2.start;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class TerminateStartDef implements Serializable {
	
	@JsonProperty(value = "startid", required = true)
	private Long	startid;
	//
	@JsonProperty(value = "candidateid", required = false)
	private Long	candidateid;
	//
	@JsonProperty(value = "jobId", required = false)
	private Long	jobId;
	//
	@JsonProperty(value = "terminationdate", required = false)
	private Date	terminationdate;
	//
	@JsonProperty(value = "reason", required = false)
	private Integer	reason;
	//
	@JsonProperty(value = "performancecode", required = false)
	private Integer	performancecode;
	//
	@JsonProperty(value = "notes", required = false)
	private String	notes;
	//
	@JsonProperty(value = "markaspastemployee", required = false)
	private Boolean	markaspastemployee;
	//
	@JsonProperty(value = "markasavailable", required = false)
	private Boolean	markasavailable;
	//
	
	public Long getStartid() {
		return startid;
	}
	
	public void setStartid(Long startid) {
		this.startid = startid;
	}
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public Date getTerminationdate() {
		return terminationdate;
	}
	
	public void setTerminationdate(Date terminationdate) {
		this.terminationdate = terminationdate;
	}
	
	public Integer getReason() {
		return reason;
	}
	
	public void setReason(Integer reason) {
		this.reason = reason;
	}
	
	public Integer getPerformancecode() {
		return performancecode;
	}
	
	public void setPerformancecode(Integer performancecode) {
		this.performancecode = performancecode;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Boolean getMarkaspastemployee() {
		return markaspastemployee;
	}
	
	public void setMarkaspastemployee(Boolean markaspastemployee) {
		this.markaspastemployee = markaspastemployee;
	}
	
	public Boolean getMarkasavailable() {
		return markasavailable;
	}
	
	public void setMarkasavailable(Boolean markasavailable) {
		this.markasavailable = markasavailable;
	}
}
