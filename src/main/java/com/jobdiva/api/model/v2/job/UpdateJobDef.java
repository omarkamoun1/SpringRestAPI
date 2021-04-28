package com.jobdiva.api.model.v2.job;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class UpdateJobDef implements Serializable {
	
	@JsonProperty(value = "jobid", required = true)
	private Long				jobid;
	//
	@JsonProperty(value = "optionalref", required = false)
	private String				optionalref;
	//
	//
	@JsonProperty(value = "title", required = false)
	private String				title;
	//
	@JsonProperty(value = "description", required = false)
	private String				description;
	//
	@JsonProperty(value = "postingtitle", required = false)
	private String				postingtitle;
	//
	@JsonProperty(value = "postingdescription", required = false)
	private String				postingdescription;
	//
	@JsonProperty(value = "companyid", required = false)
	private Long				companyid;
	//
	@JsonProperty(value = "contacts", required = false)
	private ContactRoleType[]	contacts;
	//
	@JsonProperty(value = "users", required = false)
	private UserRole[]			users;
	//
	@JsonProperty(value = "address1", required = false)
	private String				address1;
	//
	@JsonProperty(value = "address2", required = false)
	private String				address2;
	//
	@JsonProperty(value = "city", required = false)
	private String				city;
	//
	@JsonProperty(value = "state", required = false)
	private String				state;
	//
	@JsonProperty(value = "zipcode", required = false)
	private String				zipcode;
	//
	@JsonProperty(value = "countryid", required = false)
	private String				countryid;
	//
	@JsonProperty(value = "startdate", required = false)
	private Date				startdate;
	//
	@JsonProperty(value = "enddate", required = false)
	private Date				enddate;
	//
	@JsonProperty(value = "status", required = false)
	private Integer				status;
	//
	@JsonProperty(value = "jobtype", required = false)
	private String				jobtype;
	//
	@JsonProperty(value = "priority", required = false)
	private String				priority;
	//
	@JsonProperty(value = "openings", required = false)
	private Integer				openings;
	//
	@JsonProperty(value = "fills", required = false)
	private Integer				fills;
	//
	@JsonProperty(value = "maxsubmittals", required = false)
	private Integer				maxsubmittals;
	//
	@JsonProperty(value = "hidemyclient", required = false)
	private Boolean				hidemyclient;
	//
	@JsonProperty(value = "hidemyclientaddress", required = false)
	private Boolean				hidemyclientaddress;
	//
	@JsonProperty(value = "hidemeandmycompany", required = false)
	private Boolean				hidemeandmycompany;
	//
	@JsonProperty(value = "overtime", required = false)
	private Boolean				overtime;
	//
	@JsonProperty(value = "reference", required = false)
	private Boolean				reference;
	//
	@JsonProperty(value = "travel", required = false)
	private Boolean				travel;
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
	@JsonProperty(value = "remarks", required = false)
	private String				remarks;
	//
	@JsonProperty(value = "submittalinstruction", required = false)
	private String				submittalinstruction;
	//
	@JsonProperty(value = "minbillrate", required = false)
	private Double				minbillrate;
	//
	@JsonProperty(value = "maxbillrate", required = false)
	private Double				maxbillrate;
	//
	@JsonProperty(value = "minpayrate", required = false)
	private Double				minpayrate;
	//
	@JsonProperty(value = "maxpayrate", required = false)
	private Double				maxpayrate;
	//
	@JsonProperty(value = "Userfields", required = false)
	private Userfield[]			Userfields;
	//
	@JsonProperty(value = "harvest", required = false)
	private String				harvest;
	//
	@JsonProperty(value = "resumes", required = false)
	private Integer				resumes;
	//
	@JsonProperty(value = "divisionid", required = false)
	private Long				divisionid;
	
	public Long getJobid() {
		return jobid;
	}
	
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
	
	public String getOptionalref() {
		return optionalref;
	}
	
	public void setOptionalref(String optionalref) {
		this.optionalref = optionalref;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPostingtitle() {
		return postingtitle;
	}
	
	public void setPostingtitle(String postingtitle) {
		this.postingtitle = postingtitle;
	}
	
	public String getPostingdescription() {
		return postingdescription;
	}
	
	public void setPostingdescription(String postingdescription) {
		this.postingdescription = postingdescription;
	}
	
	public Long getCompanyid() {
		return companyid;
	}
	
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public ContactRoleType[] getContacts() {
		return contacts;
	}
	
	public void setContacts(ContactRoleType[] contacts) {
		this.contacts = contacts;
	}
	
	public UserRole[] getUsers() {
		return users;
	}
	
	public void setUsers(UserRole[] users) {
		this.users = users;
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
	
	public String getCountryid() {
		return countryid;
	}
	
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	
	public Date getStartdate() {
		return startdate;
	}
	
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	public Date getEnddate() {
		return enddate;
	}
	
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getJobtype() {
		return jobtype;
	}
	
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Integer getOpenings() {
		return openings;
	}
	
	public void setOpenings(Integer openings) {
		this.openings = openings;
	}
	
	public Integer getFills() {
		return fills;
	}
	
	public void setFills(Integer fills) {
		this.fills = fills;
	}
	
	public Integer getMaxsubmittals() {
		return maxsubmittals;
	}
	
	public void setMaxsubmittals(Integer maxsubmittals) {
		this.maxsubmittals = maxsubmittals;
	}
	
	public Boolean getHidemyclient() {
		return hidemyclient;
	}
	
	public void setHidemyclient(Boolean hidemyclient) {
		this.hidemyclient = hidemyclient;
	}
	
	public Boolean getHidemyclientaddress() {
		return hidemyclientaddress;
	}
	
	public void setHidemyclientaddress(Boolean hidemyclientaddress) {
		this.hidemyclientaddress = hidemyclientaddress;
	}
	
	public Boolean getHidemeandmycompany() {
		return hidemeandmycompany;
	}
	
	public void setHidemeandmycompany(Boolean hidemeandmycompany) {
		this.hidemeandmycompany = hidemeandmycompany;
	}
	
	public Boolean getOvertime() {
		return overtime;
	}
	
	public void setOvertime(Boolean overtime) {
		this.overtime = overtime;
	}
	
	public Boolean getReference() {
		return reference;
	}
	
	public void setReference(Boolean reference) {
		this.reference = reference;
	}
	
	public Boolean getTravel() {
		return travel;
	}
	
	public void setTravel(Boolean travel) {
		this.travel = travel;
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
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSubmittalinstruction() {
		return submittalinstruction;
	}
	
	public void setSubmittalinstruction(String submittalinstruction) {
		this.submittalinstruction = submittalinstruction;
	}
	
	public Double getMinbillrate() {
		return minbillrate;
	}
	
	public void setMinbillrate(Double minbillrate) {
		this.minbillrate = minbillrate;
	}
	
	public Double getMaxbillrate() {
		return maxbillrate;
	}
	
	public void setMaxbillrate(Double maxbillrate) {
		this.maxbillrate = maxbillrate;
	}
	
	public Double getMinpayrate() {
		return minpayrate;
	}
	
	public void setMinpayrate(Double minpayrate) {
		this.minpayrate = minpayrate;
	}
	
	public Double getMaxpayrate() {
		return maxpayrate;
	}
	
	public void setMaxpayrate(Double maxpayrate) {
		this.maxpayrate = maxpayrate;
	}
	
	public Userfield[] getUserfields() {
		return Userfields;
	}
	
	public void setUserfields(Userfield[] userfields) {
		Userfields = userfields;
	}
	
	public String getHarvest() {
		return harvest;
	}
	
	public void setHarvest(String harvest) {
		this.harvest = harvest;
	}
	
	public Integer getResumes() {
		return resumes;
	}
	
	public void setResumes(Integer resumes) {
		this.resumes = resumes;
	}
	
	public Long getDivisionid() {
		return divisionid;
	}
	
	public void setDivisionid(Long divisionid) {
		this.divisionid = divisionid;
	}
}
