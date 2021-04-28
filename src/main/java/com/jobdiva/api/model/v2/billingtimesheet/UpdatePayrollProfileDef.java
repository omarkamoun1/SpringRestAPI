package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class UpdatePayrollProfileDef implements Serializable {
	
	@JsonProperty(value = "employeeid", required = true)
	private Long	employeeid;
	//
	@JsonProperty(value = "salaryrecid", required = false)
	private Long	salaryrecid;
	//
	@JsonProperty(value = "generationsuffix", required = false)
	private String	generationsuffix;
	//
	@JsonProperty(value = "reasonforhire", required = false)
	private String	reasonforhire;
	//
	@JsonProperty(value = "gender", required = false)
	private String	gender;
	//
	@JsonProperty(value = "companycode", required = false)
	private String	companycode;
	//
	@JsonProperty(value = "country", required = false)
	private String	country;
	//
	@JsonProperty(value = "address1", required = false)
	private String	address1;
	//
	@JsonProperty(value = "address2", required = false)
	private String	address2;
	//
	@JsonProperty(value = "address3", required = false)
	private String	address3;
	//
	@JsonProperty(value = "city", required = false)
	private String	city;
	//
	@JsonProperty(value = "state", required = false)
	private String	state;
	//
	@JsonProperty(value = "county", required = false)
	private String	county;
	//
	@JsonProperty(value = "zipcode", required = false)
	private String	zipcode;
	//
	@JsonProperty(value = "telephone", required = false)
	private Long	telephone;
	//
	@JsonProperty(value = "jobtitle", required = false)
	private String	jobtitle;
	//
	@JsonProperty(value = "workercategory", required = false)
	private String	workercategory;
	//
	@JsonProperty(value = "manageposition", required = false)
	private Boolean	manageposition;
	//
	@JsonProperty(value = "businessunit", required = false)
	private String	businessunit;
	//
	@JsonProperty(value = "homedepartment", required = false)
	private String	homedepartment;
	//
	@JsonProperty(value = "codedhomedepartment", required = false)
	private String	codedhomedepartment;
	//
	@JsonProperty(value = "benefitseligibilityclass", required = false)
	private String	benefitseligibilityclass;
	//
	@JsonProperty(value = "naicsworkerscomp", required = false)
	private String	naicsworkerscomp;
	//
	@JsonProperty(value = "standardhours", required = false)
	private Long	standardhours;
	//
	@JsonProperty(value = "federalmaritalstatus", required = false)
	private String	federalmaritalstatus;
	//
	@JsonProperty(value = "federalexemptions", required = false)
	private Integer	federalexemptions;
	//
	@JsonProperty(value = "workedstatetaxcode", required = false)
	private String	workedstatetaxcode;
	//
	@JsonProperty(value = "statemaritalstatus", required = false)
	private String	statemaritalstatus;
	//
	@JsonProperty(value = "stateexemptions", required = false)
	private Integer	stateexemptions;
	//
	@JsonProperty(value = "livedstatetaxcode", required = false)
	private String	livedstatetaxcode;
	//
	@JsonProperty(value = "suisditaxcode", required = false)
	private String	suisditaxcode;
	//
	@JsonProperty(value = "bankdepositdeductioncode", required = false)
	private String	bankdepositdeductioncode;
	//
	@JsonProperty(value = "bankfulldepositflag", required = false)
	private Boolean	bankfulldepositflag;
	//
	@JsonProperty(value = "bankdepositdeductionamount", required = false)
	private Double	bankdepositdeductionamount;
	//
	@JsonProperty(value = "bankdeposittransitoraba", required = false)
	private String	bankdeposittransitoraba;
	//
	@JsonProperty(value = "bankdepositaccountnumber", required = false)
	private String	bankdepositaccountnumber;
	//
	@JsonProperty(value = "prenotificationmethod", required = false)
	private String	prenotificationmethod;
	//
	@JsonProperty(value = "bankdepositprenotedate", required = false)
	private Date	bankdepositprenotedate;
	//
	@JsonProperty(value = "allowedtakencode", required = false)
	private String	allowedtakencode;
	//
	@JsonProperty(value = "email", required = false)
	private String	email;
	//
	@JsonProperty(value = "resetyear", required = false)
	private Integer	resetyear;
	//
	@JsonProperty(value = "donotcaltaxfederalincome", required = false)
	private Boolean	donotcaltaxfederalincome;
	//
	@JsonProperty(value = "donotcaltaxmedicare", required = false)
	private Boolean	donotcaltaxmedicare;
	//
	@JsonProperty(value = "donotcaltaxsocialsecurity", required = false)
	private Boolean	donotcaltaxsocialsecurity;
	//
	@JsonProperty(value = "donotcaltaxstate", required = false)
	private Boolean	donotcaltaxstate;
	//
	@JsonProperty(value = "retirementplan", required = false)
	private String	retirementplan;
	//
	@JsonProperty(value = "federalextrataxtype", required = false)
	private String	federalextrataxtype;
	//
	@JsonProperty(value = "federalextratax", required = false)
	private Double	federalextratax;
	//
	@JsonProperty(value = "localtaxcode", required = false)
	private String	localtaxcode;
	//
	@JsonProperty(value = "workedlocaltaxcode", required = false)
	private String	workedlocaltaxcode;
	//
	@JsonProperty(value = "livedlocaltaxcode", required = false)
	private String	livedlocaltaxcode;
	//
	@JsonProperty(value = "localschooldistricttaxcode", required = false)
	private String	localschooldistricttaxcode;
	//
	@JsonProperty(value = "localtaxcode4", required = false)
	private String	localtaxcode4;
	//
	@JsonProperty(value = "localtaxcode5", required = false)
	private String	localtaxcode5;
	//
	@JsonProperty(value = "healthcoveragecode", required = false)
	private String	healthcoveragecode;
	//
	@JsonProperty(value = "payfrequency", required = false)
	private String	payfrequency;
	//
	@JsonProperty(value = "paygroup", required = false)
	private String	paygroup;
	//
	@JsonProperty(value = "w4deductions", required = false)
	private Double	w4deductions;
	//
	@JsonProperty(value = "w4dependents", required = false)
	private Double	w4dependents;
	//
	@JsonProperty(value = "w4multiplejobs", required = false)
	private Boolean	w4multiplejobs;
	//
	@JsonProperty(value = "w4otherincome", required = false)
	private Double	w4otherincome;
	
	public Long getEmployeeid() {
		return employeeid;
	}
	
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	
	public Long getSalaryrecid() {
		return salaryrecid;
	}
	
	public void setSalaryrecid(Long salaryrecid) {
		this.salaryrecid = salaryrecid;
	}
	
	public String getGenerationsuffix() {
		return generationsuffix;
	}
	
	public void setGenerationsuffix(String generationsuffix) {
		this.generationsuffix = generationsuffix;
	}
	
	public String getReasonforhire() {
		return reasonforhire;
	}
	
	public void setReasonforhire(String reasonforhire) {
		this.reasonforhire = reasonforhire;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getCompanycode() {
		return companycode;
	}
	
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
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
	
	public String getAddress3() {
		return address3;
	}
	
	public void setAddress3(String address3) {
		this.address3 = address3;
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
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Long getTelephone() {
		return telephone;
	}
	
	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}
	
	public String getJobtitle() {
		return jobtitle;
	}
	
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	
	public String getWorkercategory() {
		return workercategory;
	}
	
	public void setWorkercategory(String workercategory) {
		this.workercategory = workercategory;
	}
	
	public Boolean getManageposition() {
		return manageposition;
	}
	
	public void setManageposition(Boolean manageposition) {
		this.manageposition = manageposition;
	}
	
	public String getBusinessunit() {
		return businessunit;
	}
	
	public void setBusinessunit(String businessunit) {
		this.businessunit = businessunit;
	}
	
	public String getHomedepartment() {
		return homedepartment;
	}
	
	public void setHomedepartment(String homedepartment) {
		this.homedepartment = homedepartment;
	}
	
	public String getCodedhomedepartment() {
		return codedhomedepartment;
	}
	
	public void setCodedhomedepartment(String codedhomedepartment) {
		this.codedhomedepartment = codedhomedepartment;
	}
	
	public String getBenefitseligibilityclass() {
		return benefitseligibilityclass;
	}
	
	public void setBenefitseligibilityclass(String benefitseligibilityclass) {
		this.benefitseligibilityclass = benefitseligibilityclass;
	}
	
	public String getNaicsworkerscomp() {
		return naicsworkerscomp;
	}
	
	public void setNaicsworkerscomp(String naicsworkerscomp) {
		this.naicsworkerscomp = naicsworkerscomp;
	}
	
	public Long getStandardhours() {
		return standardhours;
	}
	
	public void setStandardhours(Long standardhours) {
		this.standardhours = standardhours;
	}
	
	public String getFederalmaritalstatus() {
		return federalmaritalstatus;
	}
	
	public void setFederalmaritalstatus(String federalmaritalstatus) {
		this.federalmaritalstatus = federalmaritalstatus;
	}
	
	public Integer getFederalexemptions() {
		return federalexemptions;
	}
	
	public void setFederalexemptions(Integer federalexemptions) {
		this.federalexemptions = federalexemptions;
	}
	
	public String getWorkedstatetaxcode() {
		return workedstatetaxcode;
	}
	
	public void setWorkedstatetaxcode(String workedstatetaxcode) {
		this.workedstatetaxcode = workedstatetaxcode;
	}
	
	public String getStatemaritalstatus() {
		return statemaritalstatus;
	}
	
	public void setStatemaritalstatus(String statemaritalstatus) {
		this.statemaritalstatus = statemaritalstatus;
	}
	
	public Integer getStateexemptions() {
		return stateexemptions;
	}
	
	public void setStateexemptions(Integer stateexemptions) {
		this.stateexemptions = stateexemptions;
	}
	
	public String getLivedstatetaxcode() {
		return livedstatetaxcode;
	}
	
	public void setLivedstatetaxcode(String livedstatetaxcode) {
		this.livedstatetaxcode = livedstatetaxcode;
	}
	
	public String getSuisditaxcode() {
		return suisditaxcode;
	}
	
	public void setSuisditaxcode(String suisditaxcode) {
		this.suisditaxcode = suisditaxcode;
	}
	
	public String getBankdepositdeductioncode() {
		return bankdepositdeductioncode;
	}
	
	public void setBankdepositdeductioncode(String bankdepositdeductioncode) {
		this.bankdepositdeductioncode = bankdepositdeductioncode;
	}
	
	public Boolean getBankfulldepositflag() {
		return bankfulldepositflag;
	}
	
	public void setBankfulldepositflag(Boolean bankfulldepositflag) {
		this.bankfulldepositflag = bankfulldepositflag;
	}
	
	public Double getBankdepositdeductionamount() {
		return bankdepositdeductionamount;
	}
	
	public void setBankdepositdeductionamount(Double bankdepositdeductionamount) {
		this.bankdepositdeductionamount = bankdepositdeductionamount;
	}
	
	public String getBankdeposittransitoraba() {
		return bankdeposittransitoraba;
	}
	
	public void setBankdeposittransitoraba(String bankdeposittransitoraba) {
		this.bankdeposittransitoraba = bankdeposittransitoraba;
	}
	
	public String getBankdepositaccountnumber() {
		return bankdepositaccountnumber;
	}
	
	public void setBankdepositaccountnumber(String bankdepositaccountnumber) {
		this.bankdepositaccountnumber = bankdepositaccountnumber;
	}
	
	public String getPrenotificationmethod() {
		return prenotificationmethod;
	}
	
	public void setPrenotificationmethod(String prenotificationmethod) {
		this.prenotificationmethod = prenotificationmethod;
	}
	
	public Date getBankdepositprenotedate() {
		return bankdepositprenotedate;
	}
	
	public void setBankdepositprenotedate(Date bankdepositprenotedate) {
		this.bankdepositprenotedate = bankdepositprenotedate;
	}
	
	public String getAllowedtakencode() {
		return allowedtakencode;
	}
	
	public void setAllowedtakencode(String allowedtakencode) {
		this.allowedtakencode = allowedtakencode;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getResetyear() {
		return resetyear;
	}
	
	public void setResetyear(Integer resetyear) {
		this.resetyear = resetyear;
	}
	
	public Boolean getDonotcaltaxfederalincome() {
		return donotcaltaxfederalincome;
	}
	
	public void setDonotcaltaxfederalincome(Boolean donotcaltaxfederalincome) {
		this.donotcaltaxfederalincome = donotcaltaxfederalincome;
	}
	
	public Boolean getDonotcaltaxmedicare() {
		return donotcaltaxmedicare;
	}
	
	public void setDonotcaltaxmedicare(Boolean donotcaltaxmedicare) {
		this.donotcaltaxmedicare = donotcaltaxmedicare;
	}
	
	public Boolean getDonotcaltaxsocialsecurity() {
		return donotcaltaxsocialsecurity;
	}
	
	public void setDonotcaltaxsocialsecurity(Boolean donotcaltaxsocialsecurity) {
		this.donotcaltaxsocialsecurity = donotcaltaxsocialsecurity;
	}
	
	public Boolean getDonotcaltaxstate() {
		return donotcaltaxstate;
	}
	
	public void setDonotcaltaxstate(Boolean donotcaltaxstate) {
		this.donotcaltaxstate = donotcaltaxstate;
	}
	
	public String getRetirementplan() {
		return retirementplan;
	}
	
	public void setRetirementplan(String retirementplan) {
		this.retirementplan = retirementplan;
	}
	
	public String getFederalextrataxtype() {
		return federalextrataxtype;
	}
	
	public void setFederalextrataxtype(String federalextrataxtype) {
		this.federalextrataxtype = federalextrataxtype;
	}
	
	public Double getFederalextratax() {
		return federalextratax;
	}
	
	public void setFederalextratax(Double federalextratax) {
		this.federalextratax = federalextratax;
	}
	
	public String getLocaltaxcode() {
		return localtaxcode;
	}
	
	public void setLocaltaxcode(String localtaxcode) {
		this.localtaxcode = localtaxcode;
	}
	
	public String getWorkedlocaltaxcode() {
		return workedlocaltaxcode;
	}
	
	public void setWorkedlocaltaxcode(String workedlocaltaxcode) {
		this.workedlocaltaxcode = workedlocaltaxcode;
	}
	
	public String getLivedlocaltaxcode() {
		return livedlocaltaxcode;
	}
	
	public void setLivedlocaltaxcode(String livedlocaltaxcode) {
		this.livedlocaltaxcode = livedlocaltaxcode;
	}
	
	public String getLocalschooldistricttaxcode() {
		return localschooldistricttaxcode;
	}
	
	public void setLocalschooldistricttaxcode(String localschooldistricttaxcode) {
		this.localschooldistricttaxcode = localschooldistricttaxcode;
	}
	
	public String getLocaltaxcode4() {
		return localtaxcode4;
	}
	
	public void setLocaltaxcode4(String localtaxcode4) {
		this.localtaxcode4 = localtaxcode4;
	}
	
	public String getLocaltaxcode5() {
		return localtaxcode5;
	}
	
	public void setLocaltaxcode5(String localtaxcode5) {
		this.localtaxcode5 = localtaxcode5;
	}
	
	public String getHealthcoveragecode() {
		return healthcoveragecode;
	}
	
	public void setHealthcoveragecode(String healthcoveragecode) {
		this.healthcoveragecode = healthcoveragecode;
	}
	
	public String getPayfrequency() {
		return payfrequency;
	}
	
	public void setPayfrequency(String payfrequency) {
		this.payfrequency = payfrequency;
	}
	
	public String getPaygroup() {
		return paygroup;
	}
	
	public void setPaygroup(String paygroup) {
		this.paygroup = paygroup;
	}
	
	public Double getW4deductions() {
		return w4deductions;
	}
	
	public void setW4deductions(Double w4deductions) {
		this.w4deductions = w4deductions;
	}
	
	public Double getW4dependents() {
		return w4dependents;
	}
	
	public void setW4dependents(Double w4dependents) {
		this.w4dependents = w4dependents;
	}
	
	public Boolean getW4multiplejobs() {
		return w4multiplejobs;
	}
	
	public void setW4multiplejobs(Boolean w4multiplejobs) {
		this.w4multiplejobs = w4multiplejobs;
	}
	
	public Double getW4otherincome() {
		return w4otherincome;
	}
	
	public void setW4otherincome(Double w4otherincome) {
		this.w4otherincome = w4otherincome;
	}
}
