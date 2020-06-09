package com.jobdiva.api.model;

import java.util.Date;

public class CandidateHRRecord {
	
	private Long	candidateId;
	private Long	teamId;
	private Date	dateOfBirth;
	private Integer	dobMonth;
	private Integer	dobDay;
	private Integer	dobYear;
	private String	suffix;
	private String	legalFirstName;
	private String	legalLastName;
	private String	legalMiddleName;
	private Integer	maritalStatus;
	private String	ssn;
	private String	visaStatus;
	
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
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Integer getDobMonth() {
		return dobMonth;
	}
	
	public void setDobMonth(Integer dobMonth) {
		this.dobMonth = dobMonth;
	}
	
	public Integer getDobDay() {
		return dobDay;
	}
	
	public void setDobDay(Integer dobDay) {
		this.dobDay = dobDay;
	}
	
	public Integer getDobYear() {
		return dobYear;
	}
	
	public void setDobYear(Integer dobYear) {
		this.dobYear = dobYear;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getLegalFirstName() {
		return legalFirstName;
	}
	
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	
	public String getLegalLastName() {
		return legalLastName;
	}
	
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	
	public Integer getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getSsn() {
		return ssn;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getVisaStatus() {
		return visaStatus;
	}
	
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
}
