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
public class UpdateCandidateAvailabilityDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true) //
	private Long	candidateid;
	//
	@JsonProperty(value = "availablenow", required = false) //
	private Boolean	availablenow;
	//
	@JsonProperty(value = "unavailableindef", required = false) //
	private Boolean	unavailableindef;
	//
	@JsonProperty(value = "unavailableuntil", required = false) //
	private Boolean	unavailableuntil;
	//
	@JsonProperty(value = "unavailableuntildate", required = false) //
	private Date	unavailableuntildate;
	//
	@JsonProperty(value = "reason", required = false) //
	private String	reason;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Boolean getAvailablenow() {
		return availablenow;
	}
	
	public void setAvailablenow(Boolean availablenow) {
		this.availablenow = availablenow;
	}
	
	public Boolean getUnavailableindef() {
		return unavailableindef;
	}
	
	public void setUnavailableindef(Boolean unavailableindef) {
		this.unavailableindef = unavailableindef;
	}
	
	public Boolean getUnavailableuntil() {
		return unavailableuntil;
	}
	
	public void setUnavailableuntil(Boolean unavailableuntil) {
		this.unavailableuntil = unavailableuntil;
	}
	
	public Date getUnavailableuntildate() {
		return unavailableuntildate;
	}
	
	public void setUnavailableuntildate(Date unavailableuntildate) {
		this.unavailableuntildate = unavailableuntildate;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
