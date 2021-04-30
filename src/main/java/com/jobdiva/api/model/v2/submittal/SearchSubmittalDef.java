package com.jobdiva.api.model.v2.submittal;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class SearchSubmittalDef implements Serializable {
	
	@JsonProperty(value = "submittalid", required = false)
	private Long	submittalid;
	//
	@JsonProperty(value = "jobid", required = false)
	private Long	jobid;
	//
	@JsonProperty(value = "joboptionalref", required = false)
	private String	joboptionalref;
	// false
	@JsonProperty(value = "companyname", required = false)
	private String	companyname;
	//
	@JsonProperty(value = "candidateid", required = false)
	private Long	candidateid;
	//
	@JsonProperty(value = "candidatefirstname", required = false)
	private String	candidatefirstname;
	//
	@JsonProperty(value = "candidatelastname", required = false)
	private String	candidatelastname;
	//
	@JsonProperty(value = "candidateemail", required = false)
	private String	candidateemail;
	//
	@JsonProperty(value = "candidatephone", required = false)
	private String	candidatephone;
	//
	@JsonProperty(value = "candidatecity", required = false)
	private String	candidatecity;
	//
	@JsonProperty(value = "candidatestate", required = false)
	private String	candidatestate;
	
	public Long getSubmittalid() {
		return submittalid;
	}
	
	public void setSubmittalid(Long submittalid) {
		this.submittalid = submittalid;
	}
	
	public Long getJobid() {
		return jobid;
	}
	
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
	
	public String getJoboptionalref() {
		return joboptionalref;
	}
	
	public void setJoboptionalref(String joboptionalref) {
		this.joboptionalref = joboptionalref;
	}
	
	public String getCompanyname() {
		return companyname;
	}
	
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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
	
	public String getCandidatephone() {
		return candidatephone;
	}
	
	public void setCandidatephone(String candidatephone) {
		this.candidatephone = candidatephone;
	}
	
	public String getCandidatecity() {
		return candidatecity;
	}
	
	public void setCandidatecity(String candidatecity) {
		this.candidatecity = candidatecity;
	}
	
	public String getCandidatestate() {
		return candidatestate;
	}
	
	public void setCandidatestate(String candidatestate) {
		this.candidatestate = candidatestate;
	}
}
