package com.jobdiva.api.model.v2.contact;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class SearchContactsDef implements Serializable {
	
	//
	@JsonProperty(value = "contactId", required = false)
	private Long		contactId;
	//
	@JsonProperty(value = "firstname", required = false)
	private String		firstname;
	//
	@JsonProperty(value = "lastname", required = false)
	private String		lastname;
	//
	@JsonProperty(value = "company", required = false)
	private String		company;
	//
	@JsonProperty(value = "email", required = false)
	private String		email;
	//
	@JsonProperty(value = "title", required = false)
	private String		title;
	//
	@JsonProperty(value = "address", required = false)
	private String		address;
	//
	@JsonProperty(value = "city", required = false)
	private String		city;
	//
	@JsonProperty(value = "state", required = false)
	private String		state;
	//
	@JsonProperty(value = "zipCode", required = false)
	private String		zipCode;
	//
	@JsonProperty(value = "country", required = false)
	private String		country;
	//
	@JsonProperty(value = "phone", required = false)
	private String		phone;
	//
	@JsonProperty(value = "division", required = false)
	private String		division;
	//
	@JsonProperty(value = "types", required = false)
	private String[]	types;
	//
	@JsonProperty(value = "showPrimary", required = false)
	private Boolean		showPrimary;
	//
	@JsonProperty(value = "ownerIds", required = false)
	private Long		ownerIds;
	//
	@JsonProperty(value = "showInactive", required = false)
	private Boolean		showInactive;
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
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
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String[] getTypes() {
		return types;
	}
	
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	public Boolean getShowPrimary() {
		return showPrimary;
	}
	
	public void setShowPrimary(Boolean showPrimary) {
		this.showPrimary = showPrimary;
	}
	
	public Long getOwnerIds() {
		return ownerIds;
	}
	
	public void setOwnerIds(Long ownerIds) {
		this.ownerIds = ownerIds;
	}
	
	public Boolean getShowInactive() {
		return showInactive;
	}
	
	public void setShowInactive(Boolean showInactive) {
		this.showInactive = showInactive;
	}
}
