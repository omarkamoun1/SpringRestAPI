package com.jobdiva.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Contact implements java.io.Serializable {
	
	@JsonProperty(value = "id", index = 0)
	private Long					id;
	//
	@JsonIgnore
	private Long					teamId;
	//
	@JsonProperty(value = "last name", index = 0)
	private String					lastName;
	//
	@JsonProperty(value = "first name", index = 0)
	private String					firstName;
	//
	@JsonProperty(value = "company name", index = 0)
	private String					companyName;
	//
	@JsonProperty(value = "title", index = 0)
	private String					title;
	//
	@JsonProperty(value = "department name", index = 0)
	private String					departmentName;
	//
	@JsonProperty(value = "phone 1", index = 0)
	private String					phone1;
	//
	@JsonProperty(value = "phone 2", index = 0)
	private String					phone2;
	//
	@JsonProperty(value = "phone 3", index = 0)
	private String					phone3;
	//
	@JsonProperty(value = "phone 4", index = 0)
	private String					phone4;
	//
	@JsonProperty(value = "address 1", index = 0)
	private String					address1;
	//
	@JsonProperty(value = "address 2", index = 0)
	private String					address2;
	//
	@JsonProperty(value = "city", index = 0)
	private String					city;
	//
	@JsonProperty(value = "state", index = 0)
	private String					state;
	//
	@JsonProperty(value = "zip code", index = 0)
	private String					zipcode;
	//
	@JsonProperty(value = "country", index = 0)
	private String					country;
	//
	@JsonProperty(value = "reports to", index = 0)
	private String					reportsToName;
	//
	@JsonProperty(value = "email", index = 0)
	private String					email;
	//
	@JsonProperty(value = "alternate email", index = 0)
	private String					alternateEmail;
	//
	@JsonProperty(value = "assistant name", index = 0)
	private String					assistantName;
	//
	@JsonProperty(value = "assistant email", index = 0)
	private String					assistantEmail;
	//
	@JsonProperty(value = "assistant phone", index = 0)
	private String					assistantPhone;
	//
	@JsonProperty(value = "contact type", index = 0)
	private String					contactType;
	//
	@JsonIgnore
	private String					assistantPhoneExt;
	//
	@JsonIgnore
	private String					phoneTypes;
	//
	@JsonIgnore
	private Long					reportsto;
	//
	@JsonIgnore
	private Long					companyId;
	//
	@JsonIgnore
	private String					workPhone;
	//
	@JsonIgnore
	private String					workphoneExt;
	//
	@JsonIgnore
	private String					homePhone;
	//
	@JsonIgnore
	private String					homePhoneExt;
	//
	@JsonIgnore
	private String					cellPhone;
	//
	@JsonIgnore
	private String					cellPhoneExt;
	//
	@JsonIgnore
	private String					contactFax;
	//
	@JsonIgnore
	private String					contactFaxExt;
	//
	//
	@JsonIgnore
	private List<ContactAddress>	contactAddresses	= new ArrayList<ContactAddress>();
	//
	@JsonIgnore
	private List<ContactUDF>		contactUDFs			= new ArrayList<ContactUDF>();
	//
	@JsonIgnore
	private List<PhoneType>			phones				= new ArrayList<PhoneType>();
	//
	@JsonIgnore
	private List<Owner>				owners				= new ArrayList<Owner>();
	//
	@JsonIgnore
	private List<Skill>				skills;
	//
	@JsonIgnore
	private List<ContactRoleType>	contactRoleTypes;
	//
	@JsonIgnore
	private List<UserRole>			userRoles;
	
	//
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
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
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getPhone1() {
		return phone1;
	}
	
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	public String getPhone2() {
		return phone2;
	}
	
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public String getPhone3() {
		return phone3;
	}
	
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	
	public String getPhone4() {
		return phone4;
	}
	
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getWorkPhone() {
		return workPhone;
	}
	
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	public String getWorkphoneExt() {
		return workphoneExt;
	}
	
	public void setWorkphoneExt(String workphoneExt) {
		this.workphoneExt = workphoneExt;
	}
	
	public String getHomePhone() {
		return homePhone;
	}
	
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	public String getHomePhoneExt() {
		return homePhoneExt;
	}
	
	public void setHomePhoneExt(String homePhoneExt) {
		this.homePhoneExt = homePhoneExt;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCellPhone() {
		return cellPhone;
	}
	
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public String getCellPhoneExt() {
		return cellPhoneExt;
	}
	
	public void setCellPhoneExt(String cellPhoneExt) {
		this.cellPhoneExt = cellPhoneExt;
	}
	
	public String getContactFax() {
		return contactFax;
	}
	
	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}
	
	public String getContactFaxExt() {
		return contactFaxExt;
	}
	
	public void setContactFaxExt(String contactFaxExt) {
		this.contactFaxExt = contactFaxExt;
	}
	
	public String getPhoneTypes() {
		return phoneTypes;
	}
	
	public void setPhoneTypes(String phoneTypes) {
		this.phoneTypes = phoneTypes;
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
	
	public String getAlternateEmail() {
		return alternateEmail;
	}
	
	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}
	
	public String getAssistantName() {
		return assistantName;
	}
	
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	
	public String getAssistantEmail() {
		return assistantEmail;
	}
	
	public void setAssistantEmail(String assistantEmail) {
		this.assistantEmail = assistantEmail;
	}
	
	public String getAssistantPhone() {
		return assistantPhone;
	}
	
	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}
	
	public String getAssistantPhoneExt() {
		return assistantPhoneExt;
	}
	
	public void setAssistantPhoneExt(String assistantPhoneExt) {
		this.assistantPhoneExt = assistantPhoneExt;
	}
	
	public Long getReportsto() {
		return reportsto;
	}
	
	public void setReportsto(Long reportsto) {
		this.reportsto = reportsto;
	}
	
	public List<ContactAddress> getContactAddresses() {
		return contactAddresses;
	}
	
	public void setContactAddresses(List<ContactAddress> contactAddresses) {
		this.contactAddresses = contactAddresses;
	}
	
	public List<ContactUDF> getContactUDFs() {
		return contactUDFs;
	}
	
	public void setContactUDFs(List<ContactUDF> contactUDFs) {
		this.contactUDFs = contactUDFs;
	}
	
	public List<PhoneType> getPhones() {
		return phones;
	}
	
	public void setPhones(List<PhoneType> phones) {
		this.phones = phones;
	}
	
	public List<Owner> getOwners() {
		return owners;
	}
	
	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	public List<ContactRoleType> getContactRoleTypes() {
		return contactRoleTypes;
	}
	
	public void setContactRoleTypes(List<ContactRoleType> contactRoleTypes) {
		this.contactRoleTypes = contactRoleTypes;
	}
	
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public String getContactType() {
		return contactType;
	}
	
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	
	public String getReportsToName() {
		return reportsToName;
	}
	
	public void setReportsToName(String reportsToName) {
		this.reportsToName = reportsToName;
	}
}
