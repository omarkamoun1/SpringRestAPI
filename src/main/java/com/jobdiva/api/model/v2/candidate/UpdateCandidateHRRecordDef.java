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
public class UpdateCandidateHRRecordDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true)
	private Long	candidateid;
	//
	@JsonProperty(value = "dateofbirth", required = false)
	private Date	dateofbirth;
	//
	@JsonProperty(value = "legalfirstname", required = false)
	private String	legalfirstname;
	//
	@JsonProperty(value = "legallastname", required = false)
	private String	legallastname;
	//
	@JsonProperty(value = "legalmiddlename", required = false)
	private String	legalmiddlename;
	//
	@JsonProperty(value = "suffix", required = false)
	private String	suffix;
	//
	@JsonProperty(value = "maritalstatus", required = false)
	private Integer	maritalstatus;
	//
	@JsonProperty(value = "ssn", required = false)
	private String	ssn;
	//
	@JsonProperty(value = "visastatus", required = false)
	private String	visastatus;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public Date getDateofbirth() {
		return dateofbirth;
	}
	
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
	public String getLegalfirstname() {
		return legalfirstname;
	}
	
	public void setLegalfirstname(String legalfirstname) {
		this.legalfirstname = legalfirstname;
	}
	
	public String getLegallastname() {
		return legallastname;
	}
	
	public void setLegallastname(String legallastname) {
		this.legallastname = legallastname;
	}
	
	public String getLegalmiddlename() {
		return legalmiddlename;
	}
	
	public void setLegalmiddlename(String legalmiddlename) {
		this.legalmiddlename = legalmiddlename;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Integer getMaritalstatus() {
		return maritalstatus;
	}
	
	public void setMaritalstatus(Integer maritalstatus) {
		this.maritalstatus = maritalstatus;
	}
	
	public String getSsn() {
		return ssn;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getVisastatus() {
		return visastatus;
	}
	
	public void setVisastatus(String visastatus) {
		this.visastatus = visastatus;
	}
}
