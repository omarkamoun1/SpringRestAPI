package com.jobdiva.api.model.v2.contact;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class CreateContactDef implements Serializable {
	
	@JsonProperty(value = "company", required = false)
	private String				company;
	//
	@JsonProperty(value = "firstname", required = true)
	private String				firstname;
	//
	@JsonProperty(value = "lastname", required = true)
	private String				lastname;
	//
	@JsonProperty(value = "title", required = false)
	private String				title;
	//
	@JsonProperty(value = "department", required = false)
	private String				department;
	//
	@JsonProperty(value = "phones", required = false)
	private PhoneType[]			phones;
	//
	@JsonProperty(value = "addresses", required = false)
	private ContactAddress[]	addresses;
	//
	@JsonProperty(value = "email", required = false)
	private String				email;
	//
	@JsonProperty(value = "alternateemail", required = false)
	private String				alternateemail;
	//
	@JsonProperty(value = "types", required = true)
	private String[]			types;
	//
	@JsonProperty(value = "owners", required = false)
	private Owner[]				owners;
	//
	@JsonProperty(value = "reportsto", required = false)
	private Long				reportsto;
	//
	@JsonProperty(value = "primary", required = false)
	private Boolean				primary;
	//
	@JsonProperty(value = "assistantname", required = false)
	private String				assistantname;
	//
	@JsonProperty(value = "assistantemail", required = false)
	private String				assistantemail;
	//
	@JsonProperty(value = "assistantphone", required = false)
	private String				assistantphone;
	//
	@JsonProperty(value = "assistantphoneextension", required = false)
	private String				assistantphoneextension;
	//
	@JsonProperty(value = "subguidelines", required = false)
	private String				subguidelines;
	//
	@JsonProperty(value = "maxsubmittals", required = false)
	private Integer				maxsubmittals;
	//
	@JsonProperty(value = "references", required = false)
	private Boolean				references;
	//
	@JsonProperty(value = "drugtest", required = false)
	private Boolean				drugtest;
	//
	@JsonProperty(value = "backgroundcheck", required = false)
	private Boolean				backgroundcheck;
	//
	@JsonProperty(value = "securityclearance", required = false)
	private Boolean				securityclearance;
	//
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public PhoneType[] getPhones() {
		return phones;
	}
	
	public void setPhones(PhoneType[] phones) {
		this.phones = phones;
	}
	
	public ContactAddress[] getAddresses() {
		return addresses;
	}
	
	public void setAddresses(ContactAddress[] addresses) {
		this.addresses = addresses;
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
	
	public String[] getTypes() {
		return types;
	}
	
	public void setTypes(String[] types) {
		this.types = types;
	}
	
	public Owner[] getOwners() {
		return owners;
	}
	
	public void setOwners(Owner[] owners) {
		this.owners = owners;
	}
	
	public Long getReportsto() {
		return reportsto;
	}
	
	public void setReportsto(Long reportsto) {
		this.reportsto = reportsto;
	}
	
	public Boolean getPrimary() {
		return primary;
	}
	
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	
	public String getAssistantname() {
		return assistantname;
	}
	
	public void setAssistantname(String assistantname) {
		this.assistantname = assistantname;
	}
	
	public String getAssistantemail() {
		return assistantemail;
	}
	
	public void setAssistantemail(String assistantemail) {
		this.assistantemail = assistantemail;
	}
	
	public String getAssistantphone() {
		return assistantphone;
	}
	
	public void setAssistantphone(String assistantphone) {
		this.assistantphone = assistantphone;
	}
	
	public String getAssistantphoneextension() {
		return assistantphoneextension;
	}
	
	public void setAssistantphoneextension(String assistantphoneextension) {
		this.assistantphoneextension = assistantphoneextension;
	}
	
	public String getSubguidelines() {
		return subguidelines;
	}
	
	public void setSubguidelines(String subguidelines) {
		this.subguidelines = subguidelines;
	}
	
	public Integer getMaxsubmittals() {
		return maxsubmittals;
	}
	
	public void setMaxsubmittals(Integer maxsubmittals) {
		this.maxsubmittals = maxsubmittals;
	}
	
	public Boolean getReferences() {
		return references;
	}
	
	public void setReferences(Boolean references) {
		this.references = references;
	}
	
	public Boolean getDrugtest() {
		return drugtest;
	}
	
	public void setDrugtest(Boolean drugtest) {
		this.drugtest = drugtest;
	}
	
	public Boolean getBackgroundcheck() {
		return backgroundcheck;
	}
	
	public void setBackgroundcheck(Boolean backgroundcheck) {
		this.backgroundcheck = backgroundcheck;
	}
	
	public Boolean getSecurityclearance() {
		return securityclearance;
	}
	
	public void setSecurityclearance(Boolean securityclearance) {
		this.securityclearance = securityclearance;
	}
}
