package com.jobdiva.api.model.v2.company;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class SearchCompanyDef implements Serializable {
	
	@JsonProperty(value = "companyid", required = false) //
	private Long		companyid;
	//
	@JsonProperty(value = "company", required = false) //
	private String		company;
	//
	@JsonProperty(value = "address", required = false) //
	private String		address;
	//
	@JsonProperty(value = "city", required = false) //
	private String		city;
	//
	@JsonProperty(value = "state", required = false) //
	private String		state;
	//
	@JsonProperty(value = "zip", required = false) //
	private String		zip;
	//
	@JsonProperty(value = "country", required = false) //
	private String		country;
	//
	@JsonProperty(value = "phone", required = false) //
	private String		phone;
	//
	@JsonProperty(value = "fax", required = false) //
	private String		fax;
	//
	@JsonProperty(value = "url", required = false) //
	private String		url;
	//
	@JsonProperty(value = "parentcompany", required = false) //
	private String		parentcompany;
	//
	@JsonProperty(value = "showall", required = false) //
	private Boolean		showall;
	//
	@JsonProperty(value = "types", required = false) //
	private String[]	types;
	//
	@JsonProperty(value = "ownerids", required = false) //
	private Long		ownerids;
	//
	@JsonProperty(value = "division", required = false) //
	private String		division;
	//
	@JsonProperty(value = "salespipeline", required = false) //
	private String		salespipeline;
	
	public Long getCompanyid() {
		return companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
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
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
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
	
	public String getParentcompany() {
		return parentcompany;
	}
	
	public void setParentcompany(String parentcompany) {
		this.parentcompany = parentcompany;
	}
	
	public Boolean getShowall() {
		return showall;
	}
	
	public void setShowall(Boolean showall) {
		this.showall = showall;
	}
	
	public String[] getTypes() {
		return types;
	}
	
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	public Long getOwnerids() {
		return ownerids;
	}
	
	public void setOwnerids(Long ownerids) {
		this.ownerids = ownerids;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String getSalespipeline() {
		return salespipeline;
	}
	
	public void setSalespipeline(String salespipeline) {
		this.salespipeline = salespipeline;
	}
}
