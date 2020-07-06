package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class CompanyAddress implements java.io.Serializable {
	
	//
	//
	@JsonIgnore
	private Long	companyId;
	//
	//
	@JsonIgnore
	private Long	teamid;
	//
	private Short	addressId;
	//
	private Boolean	defaultAddress;
	//
	private String	address1;
	//
	private String	address2;
	//
	private String	city;
	//
	private String	state;
	//
	private String	zipcode;
	//
	private String	phone;
	//
	private String	fax;
	//
	private String	url;
	//
	private String	email;
	//
	private String	countryid;
	//
	private Integer	action;
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Short getAddressId() {
		return addressId;
	}
	
	public void setAddressId(Short addressId) {
		this.addressId = addressId;
	}
	
	public Long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
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
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getDefaultAddress() {
		return defaultAddress;
	}
	
	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
	
	public String getCountryid() {
		return countryid;
	}
	
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	
	public Integer getAction() {
		return action;
	}
	
	public void setAction(Integer action) {
		this.action = action;
	}
}
