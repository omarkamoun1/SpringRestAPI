package com.jobdiva.api.model.v2.candidate;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.PhoneType;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class UpdateCandidateProfileDef implements Serializable {
	
	//
	@JsonProperty(value = "candidateid", required = true) //
	private Long		candidateid;
	//
	@JsonProperty(value = "firstName", required = false) //
	private String		firstName;
	//
	@JsonProperty(value = "lastName", required = false) //
	private String		lastName;
	//
	@JsonProperty(value = "email", required = false) //
	private String		email;
	//
	@JsonProperty(value = "alternateemail", required = false) //
	private String		alternateemail;
	//
	@JsonProperty(value = "address1", required = false) //
	private String		address1;
	//
	@JsonProperty(value = "address2", required = false) //
	private String		address2;
	//
	@JsonProperty(value = "city", required = false) //
	private String		city;
	//
	@JsonProperty(value = "state", required = false) //
	private String		state;
	//
	@JsonProperty(value = "zipCode", required = false) //
	private String		zipCode;
	//
	@JsonProperty(value = "countryid", required = false) //
	private String		countryid;
	//
	@JsonProperty(value = "phones", required = false) //
	private PhoneType[]	phones;
	//
	@JsonProperty(value = "currentsalary", required = false) //
	private Double		currentsalary;
	//
	@JsonProperty(value = "currentsalaryunit", required = false) //
	private String		currentsalaryunit;
	//
	@JsonProperty(value = "preferredsalary", required = false) //
	private Double		preferredsalary;
	//
	@JsonProperty(value = "preferredsalaryunit", required = false) //
	private String		preferredsalaryunit;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
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
	
	public PhoneType[] getPhones() {
		return phones;
	}
	
	public void setPhones(PhoneType[] phones) {
		this.phones = phones;
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
}
