package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Job implements java.io.Serializable {
	
	private Long					id;
	private Long					recruiterId;
	private String					department;
	private String					address1;
	private String					address2;
	private String					city;
	private String					state;
	private String					zipcode;
	private String					province;
	private Date					dateIssued;
	private Date					deadLine;
	private Date					startDate;
	private Date					endDate;
	private Short					positions;
	private Boolean					travel;
	private Boolean					overtime;
	private BigDecimal				rateMin;
	private BigDecimal				rateMax;
	private Character				rateper;
	private String					rateUnit;
	private String					rfqTitle;
	private Integer					experience;
	private String					jobDescription;
	private Integer					jobStatus;
	private String					url;
	private String					rfqRefNo;
	private Boolean					privateAddress;
	private Boolean					privateCompanyName;
	private Boolean					privateExpiryDate;
	private Boolean					privateJobStartDate;
	private Boolean					privateJobEndDate;
	private Boolean					privateSalary;
	private Long					customerId;				// contactid
	private String					firstName;
	private String					lastName;
	private String					skills;
	private Long					teamid;
	private String					rfqNoTeam;
	private Boolean					criteriaChanged;
	private String					criteriaState;
	private String					criteriaAreacode;
	private String					criteriaMajor;
	private BigDecimal				criteriaSalaryFrom;
	private BigDecimal				criteriaSalaryTo;
	private String					criteriaSalaryPer;
	private String					criteriaDegree;
	private Date					dateLastUpdated;
	private Date					dateStatusUpdated;
	private Boolean					syncRequired;
	private String					notSkills;
	private String					notCriteriaState;
	private String					notCriteriaAreacode;
	private String					notCriteriaDegree;
	private String					notCriteriaMajor;
	private BigDecimal				notCriteriaSalaryFrom;
	private BigDecimal				notCriteriaSalaryTo;
	private String					notCriteriaSalaryPer;
	private String					criteriaCategories;
	private String					notCriteriaCategories;
	private Boolean					privateMyCompanyName;
	private String					externalJobId;
	private Date					datePriorityUpdated;
	private Short					resumesNo;
	private Short					maxResumesNo;
	private Integer					contract;
	private String					priority;
	private Boolean					jobdivaPost;
	private Boolean					harvestEnable;
	private Integer					jobPriority;
	private BigDecimal				billRateMin;
	private BigDecimal				billRateMax;
	private Long					divisionId;
	private String					division;
	private String					instruction;
	private String					criteriaAttributes;
	private Long					searchId;
	private String					criteriaZipCode;
	private Short					criteriaZipCodeMiles;
	private String					postingTitle;
	private String					postingDescription;
	private Character				billRatePer;
	private String					billRateUnit;
	private Short					fills;
	private Long					jobCatalogid;
	private Boolean					criteriaSubmitted;
	private Boolean					refCheck;
	private Boolean					drugTest;
	private Boolean					backCheck;
	private Short					maxSubmitals;
	private Short					curSubmittals;
	private String					criteriaCity;
	private String					notCriteriaCity;
	private Boolean					secClearance;
	private Long					scheduleFacilityid;
	private String					scheduleColor;
	private BigDecimal				suppPayRateMin;
	private BigDecimal				suppPayRateMax;
	private String					suppPayRatePer;
	private String					suppComments;
	private Boolean					approvedStatus;
	private String					subInstruction;
	private Date					dateApproved;
	private String					country;
	private Date					jobdivaPostDate;
	private Integer					billrateCurrency;
	private Integer					payrateCurrency;
	private Boolean					suppSubGuideline;
	private Long					companyId;
	private String					criteriaTitles;
	private Boolean					myFlag;
	private Date					dimDateIssued;
	private Date					postingDate;
	private Date					portaldate;
	private Boolean					showPayRate;
	private Short					portalJobCatId;
	//
	private List<JobUDF>			jobUDFs;
	private List<JobContact>		jobContacts;
	private List<JobUser>			jobUsers;
	private List<Attachment>		attachments;
	private List<CandidateNote>		candidateNotes;
	private List<JobNote>			notes;
	private List<TimesheetEntry>	timesheetEntries;
	private List<ExpenseEntry>		expenseEntries;
	
	//
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
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
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public Date getDateIssued() {
		return dateIssued;
	}
	
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	
	public Date getDeadLine() {
		return deadLine;
	}
	
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Short getPositions() {
		return positions;
	}
	
	public void setPositions(Short positions) {
		this.positions = positions;
	}
	
	public Boolean getTravel() {
		return travel;
	}
	
	public void setTravel(Boolean travel) {
		this.travel = travel;
	}
	
	public Boolean getOvertime() {
		return overtime;
	}
	
	public void setOvertime(Boolean overtime) {
		this.overtime = overtime;
	}
	
	public BigDecimal getRateMin() {
		return rateMin;
	}
	
	public void setRateMin(BigDecimal rateMin) {
		this.rateMin = rateMin;
	}
	
	public BigDecimal getRateMax() {
		return rateMax;
	}
	
	public void setRateMax(BigDecimal rateMax) {
		this.rateMax = rateMax;
	}
	
	public Character getRateper() {
		return rateper;
	}
	
	public void setRateper(Character rateper) {
		this.rateper = rateper;
	}
	
	public String getRateUnit() {
		return rateUnit;
	}
	
	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}
	
	public String getRfqTitle() {
		return rfqTitle;
	}
	
	public void setRfqTitle(String rfqTitle) {
		this.rfqTitle = rfqTitle;
	}
	
	public Integer getExperience() {
		return experience;
	}
	
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	
	public String getJobDescription() {
		return jobDescription;
	}
	
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	public Integer getJobStatus() {
		return jobStatus;
	}
	
	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getRfqRefNo() {
		return rfqRefNo;
	}
	
	public void setRfqRefNo(String rfqRefNo) {
		this.rfqRefNo = rfqRefNo;
	}
	
	public Boolean getPrivateAddress() {
		return privateAddress;
	}
	
	public void setPrivateAddress(Boolean privateAddress) {
		this.privateAddress = privateAddress;
	}
	
	public Boolean getPrivateCompanyName() {
		return privateCompanyName;
	}
	
	public void setPrivateCompanyName(Boolean privateCompanyName) {
		this.privateCompanyName = privateCompanyName;
	}
	
	public Boolean getPrivateExpiryDate() {
		return privateExpiryDate;
	}
	
	public void setPrivateExpiryDate(Boolean privateExpiryDate) {
		this.privateExpiryDate = privateExpiryDate;
	}
	
	public Boolean getPrivateJobStartDate() {
		return privateJobStartDate;
	}
	
	public void setPrivateJobStartDate(Boolean privateJobStartDate) {
		this.privateJobStartDate = privateJobStartDate;
	}
	
	public Boolean getPrivateJobEndDate() {
		return privateJobEndDate;
	}
	
	public void setPrivateJobEndDate(Boolean privateJobEndDate) {
		this.privateJobEndDate = privateJobEndDate;
	}
	
	public Boolean getPrivateSalary() {
		return privateSalary;
	}
	
	public void setPrivateSalary(Boolean privateSalary) {
		this.privateSalary = privateSalary;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	
	public String getSkills() {
		return skills;
	}
	
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public Long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}
	
	public String getRfqNoTeam() {
		return rfqNoTeam;
	}
	
	public void setRfqNoTeam(String rfqNoTeam) {
		this.rfqNoTeam = rfqNoTeam;
	}
	
	public Boolean getCriteriaChanged() {
		return criteriaChanged;
	}
	
	public void setCriteriaChanged(Boolean criteriaChanged) {
		this.criteriaChanged = criteriaChanged;
	}
	
	public String getCriteriaState() {
		return criteriaState;
	}
	
	public void setCriteriaState(String criteriaState) {
		this.criteriaState = criteriaState;
	}
	
	public String getCriteriaAreacode() {
		return criteriaAreacode;
	}
	
	public void setCriteriaAreacode(String criteriaAreacode) {
		this.criteriaAreacode = criteriaAreacode;
	}
	
	public String getCriteriaMajor() {
		return criteriaMajor;
	}
	
	public void setCriteriaMajor(String criteriaMajor) {
		this.criteriaMajor = criteriaMajor;
	}
	
	public BigDecimal getCriteriaSalaryFrom() {
		return criteriaSalaryFrom;
	}
	
	public void setCriteriaSalaryFrom(BigDecimal criteriaSalaryFrom) {
		this.criteriaSalaryFrom = criteriaSalaryFrom;
	}
	
	public BigDecimal getCriteriaSalaryTo() {
		return criteriaSalaryTo;
	}
	
	public void setCriteriaSalaryTo(BigDecimal criteriaSalaryTo) {
		this.criteriaSalaryTo = criteriaSalaryTo;
	}
	
	public String getCriteriaSalaryPer() {
		return criteriaSalaryPer;
	}
	
	public void setCriteriaSalaryPer(String criteriaSalaryPer) {
		this.criteriaSalaryPer = criteriaSalaryPer;
	}
	
	public String getCriteriaDegree() {
		return criteriaDegree;
	}
	
	public void setCriteriaDegree(String criteriaDegree) {
		this.criteriaDegree = criteriaDegree;
	}
	
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	
	public Date getDateStatusUpdated() {
		return dateStatusUpdated;
	}
	
	public void setDateStatusUpdated(Date dateStatusUpdated) {
		this.dateStatusUpdated = dateStatusUpdated;
	}
	
	public Boolean getSyncRequired() {
		return syncRequired;
	}
	
	public void setSyncRequired(Boolean syncRequired) {
		this.syncRequired = syncRequired;
	}
	
	public String getNotSkills() {
		return notSkills;
	}
	
	public void setNotSkills(String notSkills) {
		this.notSkills = notSkills;
	}
	
	public String getNotCriteriaState() {
		return notCriteriaState;
	}
	
	public void setNotCriteriaState(String notCriteriaState) {
		this.notCriteriaState = notCriteriaState;
	}
	
	public String getNotCriteriaAreacode() {
		return notCriteriaAreacode;
	}
	
	public void setNotCriteriaAreacode(String notCriteriaAreacode) {
		this.notCriteriaAreacode = notCriteriaAreacode;
	}
	
	public String getNotCriteriaDegree() {
		return notCriteriaDegree;
	}
	
	public void setNotCriteriaDegree(String notCriteriaDegree) {
		this.notCriteriaDegree = notCriteriaDegree;
	}
	
	public String getNotCriteriaMajor() {
		return notCriteriaMajor;
	}
	
	public void setNotCriteriaMajor(String notCriteriaMajor) {
		this.notCriteriaMajor = notCriteriaMajor;
	}
	
	public BigDecimal getNotCriteriaSalaryFrom() {
		return notCriteriaSalaryFrom;
	}
	
	public void setNotCriteriaSalaryFrom(BigDecimal notCriteriaSalaryFrom) {
		this.notCriteriaSalaryFrom = notCriteriaSalaryFrom;
	}
	
	public BigDecimal getNotCriteriaSalaryTo() {
		return notCriteriaSalaryTo;
	}
	
	public void setNotCriteriaSalaryTo(BigDecimal notCriteriaSalaryTo) {
		this.notCriteriaSalaryTo = notCriteriaSalaryTo;
	}
	
	public String getNotCriteriaSalaryPer() {
		return notCriteriaSalaryPer;
	}
	
	public void setNotCriteriaSalaryPer(String notCriteriaSalaryPer) {
		this.notCriteriaSalaryPer = notCriteriaSalaryPer;
	}
	
	public String getCriteriaCategories() {
		return criteriaCategories;
	}
	
	public void setCriteriaCategories(String criteriaCategories) {
		this.criteriaCategories = criteriaCategories;
	}
	
	public String getNotCriteriaCategories() {
		return notCriteriaCategories;
	}
	
	public void setNotCriteriaCategories(String notCriteriaCategories) {
		this.notCriteriaCategories = notCriteriaCategories;
	}
	
	public Boolean getPrivateMyCompanyName() {
		return privateMyCompanyName;
	}
	
	public void setPrivateMyCompanyName(Boolean privateMyCompanyName) {
		this.privateMyCompanyName = privateMyCompanyName;
	}
	
	public String getExternalJobId() {
		return externalJobId;
	}
	
	public void setExternalJobId(String externalJobId) {
		this.externalJobId = externalJobId;
	}
	
	public Date getDatePriorityUpdated() {
		return datePriorityUpdated;
	}
	
	public void setDatePriorityUpdated(Date datePriorityUpdated) {
		this.datePriorityUpdated = datePriorityUpdated;
	}
	
	public Short getResumesNo() {
		return resumesNo;
	}
	
	public void setResumesNo(Short resumesNo) {
		this.resumesNo = resumesNo;
	}
	
	public Short getMaxResumesNo() {
		return maxResumesNo;
	}
	
	public void setMaxResumesNo(Short maxResumesNo) {
		this.maxResumesNo = maxResumesNo;
	}
	
	public Integer getContract() {
		return contract;
	}
	
	public void setContract(Integer contract) {
		this.contract = contract;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Boolean getJobdivaPost() {
		return jobdivaPost;
	}
	
	public void setJobdivaPost(Boolean jobdivaPost) {
		this.jobdivaPost = jobdivaPost;
	}
	
	public Boolean getHarvestEnable() {
		return harvestEnable;
	}
	
	public void setHarvestEnable(Boolean harvestEnable) {
		this.harvestEnable = harvestEnable;
	}
	
	public Integer getJobPriority() {
		return jobPriority;
	}
	
	public void setJobPriority(Integer jobPriority) {
		this.jobPriority = jobPriority;
	}
	
	public BigDecimal getBillRateMin() {
		return billRateMin;
	}
	
	public void setBillRateMin(BigDecimal billRateMin) {
		this.billRateMin = billRateMin;
	}
	
	public BigDecimal getBillRateMax() {
		return billRateMax;
	}
	
	public void setBillRateMax(BigDecimal billRateMax) {
		this.billRateMax = billRateMax;
	}
	
	public Long getDivisionId() {
		return divisionId;
	}
	
	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	
	public String getDivision() {
		return division;
	}
	
	public void setDivision(String division) {
		this.division = division;
	}
	
	public String getInstruction() {
		return instruction;
	}
	
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getCriteriaAttributes() {
		return criteriaAttributes;
	}
	
	public void setCriteriaAttributes(String criteriaAttributes) {
		this.criteriaAttributes = criteriaAttributes;
	}
	
	public Long getSearchId() {
		return searchId;
	}
	
	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}
	
	public String getCriteriaZipCode() {
		return criteriaZipCode;
	}
	
	public void setCriteriaZipCode(String criteriaZipCode) {
		this.criteriaZipCode = criteriaZipCode;
	}
	
	public Short getCriteriaZipCodeMiles() {
		return criteriaZipCodeMiles;
	}
	
	public void setCriteriaZipCodeMiles(Short criteriaZipCodeMiles) {
		this.criteriaZipCodeMiles = criteriaZipCodeMiles;
	}
	
	public String getPostingTitle() {
		return postingTitle;
	}
	
	public void setPostingTitle(String postingTitle) {
		this.postingTitle = postingTitle;
	}
	
	public String getPostingDescription() {
		return postingDescription;
	}
	
	public void setPostingDescription(String postingDescription) {
		this.postingDescription = postingDescription;
	}
	
	public Character getBillRatePer() {
		return billRatePer;
	}
	
	public void setBillRatePer(Character billRatePer) {
		this.billRatePer = billRatePer;
	}
	
	public String getBillRateUnit() {
		return billRateUnit;
	}
	
	public void setBillRateUnit(String billRateUnit) {
		this.billRateUnit = billRateUnit;
	}
	
	public Short getFills() {
		return fills;
	}
	
	public void setFills(Short fills) {
		this.fills = fills;
	}
	
	public Long getJobCatalogid() {
		return jobCatalogid;
	}
	
	public void setJobCatalogid(Long jobCatalogid) {
		this.jobCatalogid = jobCatalogid;
	}
	
	public Boolean getCriteriaSubmitted() {
		return criteriaSubmitted;
	}
	
	public void setCriteriaSubmitted(Boolean criteriaSubmitted) {
		this.criteriaSubmitted = criteriaSubmitted;
	}
	
	public Boolean getRefCheck() {
		return refCheck;
	}
	
	public void setRefCheck(Boolean refCheck) {
		this.refCheck = refCheck;
	}
	
	public Boolean getDrugTest() {
		return drugTest;
	}
	
	public void setDrugTest(Boolean drugTest) {
		this.drugTest = drugTest;
	}
	
	public Boolean getBackCheck() {
		return backCheck;
	}
	
	public void setBackCheck(Boolean backCheck) {
		this.backCheck = backCheck;
	}
	
	public Short getMaxSubmitals() {
		return maxSubmitals;
	}
	
	public void setMaxSubmitals(Short maxSubmitals) {
		this.maxSubmitals = maxSubmitals;
	}
	
	public Short getCurSubmittals() {
		return curSubmittals;
	}
	
	public void setCurSubmittals(Short curSubmittals) {
		this.curSubmittals = curSubmittals;
	}
	
	public String getCriteriaCity() {
		return criteriaCity;
	}
	
	public void setCriteriaCity(String criteriaCity) {
		this.criteriaCity = criteriaCity;
	}
	
	public String getNotCriteriaCity() {
		return notCriteriaCity;
	}
	
	public void setNotCriteriaCity(String notCriteriaCity) {
		this.notCriteriaCity = notCriteriaCity;
	}
	
	public Boolean getSecClearance() {
		return secClearance;
	}
	
	public void setSecClearance(Boolean secClearance) {
		this.secClearance = secClearance;
	}
	
	public Long getScheduleFacilityid() {
		return scheduleFacilityid;
	}
	
	public void setScheduleFacilityid(Long scheduleFacilityid) {
		this.scheduleFacilityid = scheduleFacilityid;
	}
	
	public String getScheduleColor() {
		return scheduleColor;
	}
	
	public void setScheduleColor(String scheduleColor) {
		this.scheduleColor = scheduleColor;
	}
	
	public BigDecimal getSuppPayRateMin() {
		return suppPayRateMin;
	}
	
	public void setSuppPayRateMin(BigDecimal suppPayRateMin) {
		this.suppPayRateMin = suppPayRateMin;
	}
	
	public BigDecimal getSuppPayRateMax() {
		return suppPayRateMax;
	}
	
	public void setSuppPayRateMax(BigDecimal suppPayRateMax) {
		this.suppPayRateMax = suppPayRateMax;
	}
	
	public String getSuppPayRatePer() {
		return suppPayRatePer;
	}
	
	public void setSuppPayRatePer(String suppPayRatePer) {
		this.suppPayRatePer = suppPayRatePer;
	}
	
	public String getSuppComments() {
		return suppComments;
	}
	
	public void setSuppComments(String suppComments) {
		this.suppComments = suppComments;
	}
	
	public Boolean getApprovedStatus() {
		return approvedStatus;
	}
	
	public void setApprovedStatus(Boolean approvedStatus) {
		this.approvedStatus = approvedStatus;
	}
	
	public String getSubInstruction() {
		return subInstruction;
	}
	
	public void setSubInstruction(String subInstruction) {
		this.subInstruction = subInstruction;
	}
	
	public Date getDateApproved() {
		return dateApproved;
	}
	
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Date getJobdivaPostDate() {
		return jobdivaPostDate;
	}
	
	public void setJobdivaPostDate(Date jobdivaPostDate) {
		this.jobdivaPostDate = jobdivaPostDate;
	}
	
	public Integer getBillrateCurrency() {
		return billrateCurrency;
	}
	
	public void setBillrateCurrency(Integer billrateCurrency) {
		this.billrateCurrency = billrateCurrency;
	}
	
	public Integer getPayrateCurrency() {
		return payrateCurrency;
	}
	
	public void setPayrateCurrency(Integer payrateCurrency) {
		this.payrateCurrency = payrateCurrency;
	}
	
	public Boolean getSuppSubGuideline() {
		return suppSubGuideline;
	}
	
	public void setSuppSubGuideline(Boolean suppSubGuideline) {
		this.suppSubGuideline = suppSubGuideline;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getCriteriaTitles() {
		return criteriaTitles;
	}
	
	public void setCriteriaTitles(String criteriaTitles) {
		this.criteriaTitles = criteriaTitles;
	}
	
	public Boolean getMyFlag() {
		return myFlag;
	}
	
	public void setMyFlag(Boolean myFlag) {
		this.myFlag = myFlag;
	}
	
	public Date getDimDateIssued() {
		return dimDateIssued;
	}
	
	public void setDimDateIssued(Date dimDateIssued) {
		this.dimDateIssued = dimDateIssued;
	}
	
	public Date getPostingDate() {
		return postingDate;
	}
	
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}
	
	public Date getPortaldate() {
		return portaldate;
	}
	
	public void setPortaldate(Date portaldate) {
		this.portaldate = portaldate;
	}
	
	public List<JobUDF> getJobUDFs() {
		return jobUDFs;
	}
	
	public Boolean getShowPayRate() {
		return showPayRate;
	}
	
	public void setShowPayRate(Boolean showPayRate) {
		this.showPayRate = showPayRate;
	}
	
	public Short getPortalJobCatId() {
		return portalJobCatId;
	}
	
	public void setPortalJobCatId(Short portalJobCatId) {
		this.portalJobCatId = portalJobCatId;
	}
	
	public void setJobUDFs(List<JobUDF> jobUDFs) {
		this.jobUDFs = jobUDFs;
	}
	
	public List<JobContact> getJobContacts() {
		return jobContacts;
	}
	
	public void setJobContacts(List<JobContact> jobContacts) {
		this.jobContacts = jobContacts;
	}
	
	public List<JobUser> getJobUsers() {
		return jobUsers;
	}
	
	public void setJobUsers(List<JobUser> jobUsers) {
		this.jobUsers = jobUsers;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public List<CandidateNote> getCandidateNotes() {
		return candidateNotes;
	}
	
	public void setCandidateNotes(List<CandidateNote> candidateNotes) {
		this.candidateNotes = candidateNotes;
	}
	
	public List<JobNote> getNotes() {
		return notes;
	}
	
	public void setNotes(List<JobNote> notes) {
		this.notes = notes;
	}
	
	public List<TimesheetEntry> getTimesheetEntries() {
		return timesheetEntries;
	}
	
	public void setTimesheetEntries(List<TimesheetEntry> timesheetEntries) {
		this.timesheetEntries = timesheetEntries;
	}
	
	public List<ExpenseEntry> getExpenseEntries() {
		return expenseEntries;
	}
	
	public void setExpenseEntries(List<ExpenseEntry> expenseEntries) {
		this.expenseEntries = expenseEntries;
	}
}