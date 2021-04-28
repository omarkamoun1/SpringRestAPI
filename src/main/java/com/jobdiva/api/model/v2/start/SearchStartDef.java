package com.jobdiva.api.model.v2.start;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 27, 2021
 */
@SuppressWarnings("serial")
public class SearchStartDef implements Serializable {
	
	@JsonProperty(value = "jobId", required = false) //
	private Long	jobId;
	//
	@JsonProperty(value = "optionalref", required = false) //
	private String	optionalref;
	//
	@JsonProperty(value = "jobdivaref", required = false) //
	private String	jobdivaref;
	//
	@JsonProperty(value = "candidateid", required = false) //
	private Long	candidateid;
	//
	@JsonProperty(value = "candidatefirstname", required = false) //
	private String	candidatefirstname;
	//
	@JsonProperty(value = "candidatelastname", required = false) //
	private String	candidatelastname;
	//
	@JsonProperty(value = "candidateemail", required = false) //
	private String	candidateemail;
	//
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public String getOptionalref() {
		return optionalref;
	}
	
	public void setOptionalref(String optionalref) {
		this.optionalref = optionalref;
	}
	
	public String getJobdivaref() {
		return jobdivaref;
	}
	
	public void setJobdivaref(String jobdivaref) {
		this.jobdivaref = jobdivaref;
	}
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public String getCandidatefirstname() {
		return candidatefirstname;
	}
	
	public void setCandidatefirstname(String candidatefirstname) {
		this.candidatefirstname = candidatefirstname;
	}
	
	public String getCandidatelastname() {
		return candidatelastname;
	}
	
	public void setCandidatelastname(String candidatelastname) {
		this.candidatelastname = candidatelastname;
	}
	
	public String getCandidateemail() {
		return candidateemail;
	}
	
	public void setCandidateemail(String candidateemail) {
		this.candidateemail = candidateemail;
	}
}
