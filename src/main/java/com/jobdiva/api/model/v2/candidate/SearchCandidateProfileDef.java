package com.jobdiva.api.model.v2.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.CandidateQual;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class SearchCandidateProfileDef implements java.io.Serializable {
	
	//
	@JsonProperty(value = "firstName", required = false, index = 0)
	String			firstName;
	//
	@JsonProperty(value = "lastName", required = false, index = 1)
	String			lastName;
	//
	@JsonProperty(value = "email", required = false, index = 2)
	String			email;
	//
	@JsonProperty(value = "phone", required = false, index = 3)
	String			phone;
	//
	@JsonProperty(value = "city", required = false, index = 4)
	String			city;
	//
	@JsonProperty(value = "state", required = false, index = 5)
	String			state;
	//
	@JsonProperty(value = "address", required = false, index = 6)
	String			address;
	//
	@JsonProperty(value = "zipCode", required = false, index = 7)
	String			zipCode;
	//
	@JsonProperty(value = "country", required = false, index = 8)
	String			country;
	//
	@JsonProperty(value = "candidateQuals", required = false, index = 9)
	CandidateQual[]	candidateQuals;
	//
	@JsonProperty(value = "maxreturned", required = false, index = 10)
	Integer			maxreturned;
	
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
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
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
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public CandidateQual[] getCandidateQuals() {
		return candidateQuals;
	}
	
	public void setCandidateQuals(CandidateQual[] candidateQuals) {
		this.candidateQuals = candidateQuals;
	}
	
	public Integer getMaxreturned() {
		return maxreturned;
	}
	
	public void setMaxreturned(Integer maxreturned) {
		this.maxreturned = maxreturned;
	}//
}
