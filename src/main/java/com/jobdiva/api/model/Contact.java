package com.jobdiva.api.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Contact implements java.io.Serializable {
	
	private Long					id;
	private Long					teamId;
	private String					firstName;
	private String					lastName;
	private String					companyName;
	private String					departmentName;
	private String					workPhone;
	private String					workphoneExt;
	private String					homePhone;
	private String					homePhoneExt;
	private String					email;
	private Long					companyId;
	private String					title;
	private String					cellPhone;
	private String					cellPhoneExt;
	private String					contactFax;
	private String					contactFaxExt;
	private String					phoneTypes;
	private String					address1;
	private String					address2;
	private String					city;
	private String					state;
	private String					zipcode;
	private String					country;
	private String					alternateEmail;
	private String					assistantName;
	private String					assistantEmail;
	private String					assistantPhone;
	private String					assistantPhoneExt;
	private Long					reportsto;
	//
	private List<ContactAddress>	contactAddresses	= new ArrayList<ContactAddress>();
	private List<ContactUDF>		contactUDFs			= new ArrayList<ContactUDF>();
	private List<PhoneType>			phones				= new ArrayList<PhoneType>();
	private List<Owner>				owners				= new ArrayList<Owner>();
	private List<Skill>				skills;
	private List<ContactRoleType>	contactRoleTypes;
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
}
