package com.jobdiva.api.model.v2.job;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class AddJobNoteDef implements Serializable {
	
	@JsonProperty(value = "jobId", required = true)
	private Long	jobId;
	//
	@JsonProperty(value = "recruiterId", required = true)
	private Long	recruiterId;
	//
	@JsonProperty(value = "type", required = false)
	private Integer	type;
	//
	@JsonProperty(value = "shared", required = false)
	private Integer	shared;
	//
	@JsonProperty(value = "note", required = false)
	private String	note;
	
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
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getShared() {
		return shared;
	}
	
	public void setShared(Integer shared) {
		this.shared = shared;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
