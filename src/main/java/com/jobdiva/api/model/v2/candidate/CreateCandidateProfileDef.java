package com.jobdiva.api.model.v2.candidate;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.TitleSkillCertification;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class CreateCandidateProfileDef implements java.io.Serializable {
	
	@JsonProperty(value = "firstName", required = true)
	String						firstName;
	//
	@JsonProperty(value = "lastName", required = true)
	String						lastName;
	//
	@JsonProperty(value = "email", required = false)
	String						email;
	//
	@JsonProperty(value = "alternateemail", required = false)
	String						alternateemail;
	//
	@JsonProperty(value = "address1", required = false)
	String						address1;
	//
	@JsonProperty(value = "address2", required = false)
	String						address2;
	//
	@JsonProperty(value = "city", required = false)
	String						city;
	//
	@JsonProperty(value = "state", required = false)
	String						state;
	//
	@JsonProperty(value = "zipCode", required = false)
	String						zipCode;
	//
	@JsonProperty(value = "countryid", required = false)
	String						countryid;
	//
	@JsonProperty(value = "homephone", required = false)
	String						homephone;
	//
	@JsonProperty(value = "workphone", required = false)
	String						workphone;
	//
	@JsonProperty(value = "cellphone", required = false)
	String						cellphone;
	//
	@JsonProperty(value = "fax", required = false)
	String						fax;
	//
	@JsonProperty(value = "currentsalary", required = false)
	Double						currentsalary;
	//
	@JsonProperty(value = "currentsalaryunit", required = false)
	String						currentsalaryunit;
	//
	@JsonProperty(value = "preferredsalary", required = false)
	Double						preferredsalary;
	//
	@JsonProperty(value = "preferredsalaryunit", required = false)
	String						preferredsalaryunit;
	//
	@JsonProperty(value = "narrative", required = false)
	String						narrative;
	//
	@JsonProperty(value = "titleskillcertifications", required = false)
	TitleSkillCertification[]	titleskillcertifications;
	//
	@JsonProperty(value = "titleskillcertification", required = false)
	String						titleskillcertification;
	//
	@JsonProperty(value = "startdate", required = false)
	Date						startdate;
	//
	@JsonProperty(value = "enddate", required = false)
	Date						enddate;
	//
	@JsonProperty(value = "years", required = false)
	Integer						years;
	//
	@JsonProperty(value = "resumeSource", required = false)
	Integer						resumeSource;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAlternateemail() {
		return alternateemail;
	}
	
	public void setAlternateemail(String alternateemail) {
		this.alternateemail = alternateemail;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCountryid() {
		return countryid;
	}
	
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	
	public String getHomephone() {
		return homephone;
	}
	
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	
	public String getWorkphone() {
		return workphone;
	}
	
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public Double getCurrentsalary() {
		return currentsalary;
	}
	
	public void setCurrentsalary(Double currentsalary) {
		this.currentsalary = currentsalary;
	}
	
	public String getCurrentsalaryunit() {
		return currentsalaryunit;
	}
	
	public void setCurrentsalaryunit(String currentsalaryunit) {
		this.currentsalaryunit = currentsalaryunit;
	}
	
	public Double getPreferredsalary() {
		return preferredsalary;
	}
	
	public void setPreferredsalary(Double preferredsalary) {
		this.preferredsalary = preferredsalary;
	}
	
	public String getPreferredsalaryunit() {
		return preferredsalaryunit;
	}
	
	public void setPreferredsalaryunit(String preferredsalaryunit) {
		this.preferredsalaryunit = preferredsalaryunit;
	}
	
	public String getNarrative() {
		return narrative;
	}
	
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	
	public TitleSkillCertification[] getTitleskillcertifications() {
		return titleskillcertifications;
	}
	
	public void setTitleskillcertifications(TitleSkillCertification[] titleskillcertifications) {
		this.titleskillcertifications = titleskillcertifications;
	}
	
	public String getTitleskillcertification() {
		return titleskillcertification;
	}
	
	public void setTitleskillcertification(String titleskillcertification) {
		this.titleskillcertification = titleskillcertification;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public Integer getYears() {
		return years;
	}
	
	public void setYears(Integer years) {
		this.years = years;
	}
	
	public Integer getResumeSource() {
		return resumeSource;
	}
	
	public void setResumeSource(Integer resumeSource) {
		this.resumeSource = resumeSource;
	}
}
