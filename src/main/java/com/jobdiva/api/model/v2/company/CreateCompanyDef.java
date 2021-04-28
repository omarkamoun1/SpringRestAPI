package com.jobdiva.api.model.v2.company;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Owner;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class CreateCompanyDef implements Serializable {
	
	@JsonProperty(value = "companyname", required = true) //
	private String		companyname;
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
	@JsonProperty(value = "zipcode", required = false) //
	private String		zipcode;
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
	@JsonProperty(value = "email", required = false) //
	private String		email;
	//
	@JsonProperty(value = "url", required = false) //
	private String		url;
	//
	@JsonProperty(value = "parentcompany", required = false) //
	private String		parentcompany;
	//
	@JsonProperty(value = "companytypes", required = false) //
	private String[]	companytypes;
	//
	@JsonProperty(value = "owners", required = false) //
	private Owner[]		owners;
	//
	@JsonProperty(value = "salespipeline", required = false) //
	private String		salespipeline;
	
	public String getCompanyname() {
		return companyname;
	}
	
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public String[] getCompanytypes() {
		return companytypes;
	}
	
	public void setCompanytypes(String[] companytypes) {
		this.companytypes = companytypes;
	}
	
	public Owner[] getOwners() {
		return owners;
	}
	
	public void setOwners(Owner[] owners) {
		this.owners = owners;
	}
	
	public String getSalespipeline() {
		return salespipeline;
	}
	
	public void setSalespipeline(String salespipeline) {
		this.salespipeline = salespipeline;
	}
}
