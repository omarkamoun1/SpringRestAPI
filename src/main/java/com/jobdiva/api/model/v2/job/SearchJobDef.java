package com.jobdiva.api.model.v2.job;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class SearchJobDef implements Serializable {
	
	@JsonProperty(value = "jobId", required = false)
	private Long		jobId;
	//
	@JsonProperty(value = "jobdivaref", required = false)
	private String		jobdivaref;
	//
	@JsonProperty(value = "optionalref", required = false)
	private String		optionalref;
	//
	@JsonProperty(value = "city", required = false)
	private String		city;
	//
	@JsonProperty(value = "state", required = false)
	private String[]	state;
	//
	@JsonProperty(value = "title", required = false)
	private String		title;
	//
	@JsonProperty(value = "contactid", required = false)
	private Long		contactid;
	//
	@JsonProperty(value = "companyId", required = false)
	private Long		companyId;
	//
	@JsonProperty(value = "companyname", required = false)
	private String		companyname;
	//
	@JsonProperty(value = "status", required = false)
	private Integer		status;
	//
	@JsonProperty(value = "jobtype", required = false)
	private String[]	jobtype;
	//
	@JsonProperty(value = "issuedatefrom", required = false)
	private Date		issuedatefrom;
	//
	@JsonProperty(value = "issuedateto", required = false)
	private Date		issuedateto;
	//
	@JsonProperty(value = "startdatefrom", required = false)
	private Date		startdatefrom;
	//
	@JsonProperty(value = "startdateto", required = false)
	private Date		startdateto;
	//
	@JsonProperty(value = "zipcode", required = false)
	private String		zipcode;
	//
	@JsonProperty(value = "zipcodeRadius", required = false)
	private Integer		zipcodeRadius;
	//
	@JsonProperty(value = "countryId", required = false)
	private String		countryId;
	//
	@JsonProperty(value = "ismyjob", required = false)
	private Boolean		ismyjob;
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public String getJobdivaref() {
		return jobdivaref;
	}
	
	public void setJobdivaref(String jobdivaref) {
		this.jobdivaref = jobdivaref;
	}
	
	public String getOptionalref() {
		return optionalref;
	}
	
	public void setOptionalref(String optionalref) {
		this.optionalref = optionalref;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String[] getState() {
		return state;
	}
	
	public void setState(String[] state) {
		this.state = state;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getContactid() {
		return contactid;
	}
	
	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyname() {
		return companyname;
	}
	
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String[] getJobtype() {
		return jobtype;
	}
	
	public void setJobtype(String[] jobtype) {
		this.jobtype = jobtype;
	}
	
	public Date getIssuedatefrom() {
		return issuedatefrom;
	}
	
	public void setIssuedatefrom(Date issuedatefrom) {
		this.issuedatefrom = issuedatefrom;
	}
	
	public Date getIssuedateto() {
		return issuedateto;
	}
	
	public void setIssuedateto(Date issuedateto) {
		this.issuedateto = issuedateto;
	}
	
	public Date getStartdatefrom() {
		return startdatefrom;
	}
	
	public void setStartdatefrom(Date startdatefrom) {
		this.startdatefrom = startdatefrom;
	}
	
	public Date getStartdateto() {
		return startdateto;
	}
	
	public void setStartdateto(Date startdateto) {
		this.startdateto = startdateto;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Integer getZipcodeRadius() {
		return zipcodeRadius;
	}
	
	public void setZipcodeRadius(Integer zipcodeRadius) {
		this.zipcodeRadius = zipcodeRadius;
	}
	
	public String getCountryId() {
		return countryId;
	}
	
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public Boolean getIsmyjob() {
		return ismyjob;
	}
	
	public void setIsmyjob(Boolean ismyjob) {
		this.ismyjob = ismyjob;
	}
}
