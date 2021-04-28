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
public class CandidateEmailMergeDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true)
	private Long	candidateid;
	//
	@JsonProperty(value = "backonemailmerge", required = false)
	private Boolean	backonemailmerge;
	//
	@JsonProperty(value = "requestoffemailindef", required = false)
	private Boolean	requestoffemailindef;
	//
	@JsonProperty(value = "requestoffemailuntil", required = false)
	private Boolean	requestoffemailuntil;
	//
	@JsonProperty(value = "requestoffemailuntildate", required = false)
	private Date	requestoffemailuntildate;
	//
	@JsonProperty(value = "reason", required = false) //
	private String	reason;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Boolean getBackonemailmerge() {
		return backonemailmerge;
	}
	
	public void setBackonemailmerge(Boolean backonemailmerge) {
		this.backonemailmerge = backonemailmerge;
	}
	
	public Boolean getRequestoffemailindef() {
		return requestoffemailindef;
	}
	
	public void setRequestoffemailindef(Boolean requestoffemailindef) {
		this.requestoffemailindef = requestoffemailindef;
	}
	
	public Boolean getRequestoffemailuntil() {
		return requestoffemailuntil;
	}
	
	public void setRequestoffemailuntil(Boolean requestoffemailuntil) {
		this.requestoffemailuntil = requestoffemailuntil;
	}
	
	public Date getRequestoffemailuntildate() {
		return requestoffemailuntildate;
	}
	
	public void setRequestoffemailuntildate(Date requestoffemailuntildate) {
		this.requestoffemailuntildate = requestoffemailuntildate;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
