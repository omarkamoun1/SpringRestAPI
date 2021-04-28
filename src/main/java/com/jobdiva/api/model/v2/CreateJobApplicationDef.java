package com.jobdiva.api.model.v2;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class CreateJobApplicationDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true)
	private Long	candidateid;
	//
	@JsonProperty(value = "jobid", required = true)
	private Long	jobid;
	//
	@JsonProperty(value = "dateapplied", required = false)
	private Date	dateapplied;
	//
	@JsonProperty(value = "resumesource", required = false)
	private Integer	resumesource;
	//
	@JsonProperty(value = "globalid", required = false)
	private String	globalid;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Long getJobid() {
		return jobid;
	}
	
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
	
	public Date getDateapplied() {
		return dateapplied;
	}
	
	public void setDateapplied(Date dateapplied) {
		this.dateapplied = dateapplied;
	}
	
	public Integer getResumesource() {
		return resumesource;
	}
	
	public void setResumesource(Integer resumesource) {
		this.resumesource = resumesource;
	}
	
	public String getGlobalid() {
		return globalid;
	}
	
	public void setGlobalid(String globalid) {
		this.globalid = globalid;
	}
}
