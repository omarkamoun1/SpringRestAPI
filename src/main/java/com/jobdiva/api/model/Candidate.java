package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Candidate implements java.io.Serializable {
	
	private Long							id;
	private Long							teamId;
	private String							firstName;
	private String							lastName;
	private String							address1;
	private String							address2;
	private String							city;
	private String							state;
	private String							zipCode;
	private String							province;
	private String							country;
	private String							homePhone;
	private String							workPhone;
	private String							cellPhone;
	private String							email;
	private String							sysEmail;
	private String							privateName;
	private String							privateAddress;
	private String							privatePhone;
	private String							privateEmail;
	private Date							dateReceived;
	private Date							dateUpdated;
	private Boolean							available;
	private Date							dateAvailable;
	private BigDecimal						currentSalary;
	private String							currrentSalaryPer;
	private BigDecimal						preferredSalaryMin;
	private BigDecimal						preferredSalaryMax;
	private String							preferredSalaryPer;
	private String							password;
	private Boolean							privateSalary;
	private String							middleInitial;
	private Boolean							znoltr;
	private String							fax;
	private String							beeper;
	private Boolean							retagRequired;
	private Byte							recentDocid;
	private Date							dateupdatedManual;
	private Boolean							confidential;
	private Date							dateUpdatedCandidate;
	private String							workphoneExt;
	private String							alternateEmail;
	private Boolean							availabilityType;
	private Boolean							eeoSent;
	private Date							lastemailRcvd;
	private Boolean							licEmailAlert;
	private Integer							currentSalaryCurrency;
	private Integer							preferredSalaryCurrency;
	private Boolean							pipelineUrlExpired;
	private Boolean							eeoSentStart;
	private String							homephoneExt;
	private String							cellphoneExt;
	private String							faxExt;
	private String							phoneTypes;
	private Date							timePwchanged;
	private List<CandidateUDF>				candidateUDFs;
	private List<TitleSkillCertification>	certifications;
	private List<CandidateQual>				candidateQuals;
	private List<QualificationType>			qualificationTypes;
	
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
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getHomePhone() {
		return homePhone;
	}
	
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	public String getWorkPhone() {
		return workPhone;
	}
	
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	public String getCellPhone() {
		return cellPhone;
	}
	
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSysEmail() {
		return sysEmail;
	}
	
	public void setSysEmail(String sysEmail) {
		this.sysEmail = sysEmail;
	}
	
	public String getPrivateName() {
		return privateName;
	}
	
	public void setPrivateName(String privateName) {
		this.privateName = privateName;
	}
	
	public String getPrivateAddress() {
		return privateAddress;
	}
	
	public void setPrivateAddress(String privateAddress) {
		this.privateAddress = privateAddress;
	}
	
	public String getPrivatePhone() {
		return privatePhone;
	}
	
	public void setPrivatePhone(String privatePhone) {
		this.privatePhone = privatePhone;
	}
	
	public String getPrivateEmail() {
		return privateEmail;
	}
	
	public void setPrivateEmail(String privateEmail) {
		this.privateEmail = privateEmail;
	}
	
	public Date getDateReceived() {
		return dateReceived;
	}
	
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	public Boolean getAvailable() {
		return available;
	}
	
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	public Date getDateAvailable() {
		return dateAvailable;
	}
	
	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
	}
	
	public BigDecimal getCurrentSalary() {
		return currentSalary;
	}
	
	public void setCurrentSalary(BigDecimal currentSalary) {
		this.currentSalary = currentSalary;
	}
	
	public String getCurrrentSalaryPer() {
		return currrentSalaryPer;
	}
	
	public void setCurrrentSalaryPer(String currrentSalaryPer) {
		this.currrentSalaryPer = currrentSalaryPer;
	}
	
	public BigDecimal getPreferredSalaryMin() {
		return preferredSalaryMin;
	}
	
	public void setPreferredSalaryMin(BigDecimal preferredSalaryMin) {
		this.preferredSalaryMin = preferredSalaryMin;
	}
	
	public BigDecimal getPreferredSalaryMax() {
		return preferredSalaryMax;
	}
	
	public void setPreferredSalaryMax(BigDecimal preferredSalaryMax) {
		this.preferredSalaryMax = preferredSalaryMax;
	}
	
	public String getPreferredSalaryPer() {
		return preferredSalaryPer;
	}
	
	public void setPreferredSalaryPer(String preferredSalaryPer) {
		this.preferredSalaryPer = preferredSalaryPer;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getPrivateSalary() {
		return privateSalary;
	}
	
	public void setPrivateSalary(Boolean privateSalary) {
		this.privateSalary = privateSalary;
	}
	
	public String getMiddleInitial() {
		return middleInitial;
	}
	
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	public Boolean getZnoltr() {
		return znoltr;
	}
	
	public void setZnoltr(Boolean znoltr) {
		this.znoltr = znoltr;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getBeeper() {
		return beeper;
	}
	
	public void setBeeper(String beeper) {
		this.beeper = beeper;
	}
	
	public Boolean getRetagRequired() {
		return retagRequired;
	}
	
	public void setRetagRequired(Boolean retagRequired) {
		this.retagRequired = retagRequired;
	}
	
	public Byte getRecentDocid() {
		return recentDocid;
	}
	
	public void setRecentDocid(Byte recentDocid) {
		this.recentDocid = recentDocid;
	}
	
	public Date getDateupdatedManual() {
		return dateupdatedManual;
	}
	
	public void setDateupdatedManual(Date dateupdatedManual) {
		this.dateupdatedManual = dateupdatedManual;
	}
	
	public Boolean getConfidential() {
		return confidential;
	}
	
	public void setConfidential(Boolean confidential) {
		this.confidential = confidential;
	}
	
	public Date getDateUpdatedCandidate() {
		return dateUpdatedCandidate;
	}
	
	public void setDateUpdatedCandidate(Date dateUpdatedCandidate) {
		this.dateUpdatedCandidate = dateUpdatedCandidate;
	}
	
	public String getWorkphoneExt() {
		return workphoneExt;
	}
	
	public void setWorkphoneExt(String workphoneExt) {
		this.workphoneExt = workphoneExt;
	}
	
	public String getAlternateEmail() {
		return alternateEmail;
	}
	
	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}
	
	public Boolean getAvailabilityType() {
		return availabilityType;
	}
	
	public void setAvailabilityType(Boolean availabilityType) {
		this.availabilityType = availabilityType;
	}
	
	public Boolean getEeoSent() {
		return eeoSent;
	}
	
	public void setEeoSent(Boolean eeoSent) {
		this.eeoSent = eeoSent;
	}
	
	public Date getLastemailRcvd() {
		return lastemailRcvd;
	}
	
	public void setLastemailRcvd(Date lastemailRcvd) {
		this.lastemailRcvd = lastemailRcvd;
	}
	
	public Boolean getLicEmailAlert() {
		return licEmailAlert;
	}
	
	public void setLicEmailAlert(Boolean licEmailAlert) {
		this.licEmailAlert = licEmailAlert;
	}
	
	public Integer getCurrentSalaryCurrency() {
		return currentSalaryCurrency;
	}
	
	public void setCurrentSalaryCurrency(Integer currentSalaryCurrency) {
		this.currentSalaryCurrency = currentSalaryCurrency;
	}
	
	public Integer getPreferredSalaryCurrency() {
		return preferredSalaryCurrency;
	}
	
	public void setPreferredSalaryCurrency(Integer preferredSalaryCurrency) {
		this.preferredSalaryCurrency = preferredSalaryCurrency;
	}
	
	public Boolean getPipelineUrlExpired() {
		return pipelineUrlExpired;
	}
	
	public void setPipelineUrlExpired(Boolean pipelineUrlExpired) {
		this.pipelineUrlExpired = pipelineUrlExpired;
	}
	
	public Boolean getEeoSentStart() {
		return eeoSentStart;
	}
	
	public void setEeoSentStart(Boolean eeoSentStart) {
		this.eeoSentStart = eeoSentStart;
	}
	
	public String getHomephoneExt() {
		return homephoneExt;
	}
	
	public void setHomephoneExt(String homephoneExt) {
		this.homephoneExt = homephoneExt;
	}
	
	public String getCellphoneExt() {
		return cellphoneExt;
	}
	
	public void setCellphoneExt(String cellphoneExt) {
		this.cellphoneExt = cellphoneExt;
	}
	
	public String getFaxExt() {
		return faxExt;
	}
	
	public void setFaxExt(String faxExt) {
		this.faxExt = faxExt;
	}
	
	public String getPhoneTypes() {
		return phoneTypes;
	}
	
	public void setPhoneTypes(String phoneTypes) {
		this.phoneTypes = phoneTypes;
	}
	
	public Date getTimePwchanged() {
		return timePwchanged;
	}
	
	public void setTimePwchanged(Date timePwchanged) {
		this.timePwchanged = timePwchanged;
	}
	
	public List<CandidateUDF> getCandidateUDFs() {
		return candidateUDFs;
	}
	
	public void setCandidateUDFs(List<CandidateUDF> candidateUDFs) {
		this.candidateUDFs = candidateUDFs;
	}
	
	public List<TitleSkillCertification> getCertifications() {
		return certifications;
	}
	
	public void setCertifications(List<TitleSkillCertification> certifications) {
		this.certifications = certifications;
	}
	
	public List<CandidateQual> getCandidateQuals() {
		return candidateQuals;
	}
	
	public void setCandidateQuals(List<CandidateQual> candidateQuals) {
		this.candidateQuals = candidateQuals;
	}
	
	public List<QualificationType> getQualificationTypes() {
		return qualificationTypes;
	}
	
	public void setQualificationTypes(List<QualificationType> qualificationTypes) {
		this.qualificationTypes = qualificationTypes;
	}
}
