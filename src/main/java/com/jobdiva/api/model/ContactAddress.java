package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;

@ApiModel()
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class ContactAddress implements java.io.Serializable {
	
	private Long	id;
	private Long	contactId;
	private Long	teamId;
	private String	address1;
	private String	address2;
	private String	city;
	private String	state;
	private String	zipCode;
	private String	countryId;
	private Boolean	defaultAddress	= false;
	private Boolean	deleted			= false;
	private String	freeText;
	private Integer	action;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getContactId() {
		return contactId;
	}
	
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
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
	
	public String getCountryId() {
		return countryId;
	}
	
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public Boolean getDefaultAddress() {
		return defaultAddress;
	}
	
	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getFreeText() {
		return freeText;
	}
	
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	
	public Integer getAction() {
		return action;
	}
	
	public void setAction(Integer action) {
		this.action = action;
	}
}
