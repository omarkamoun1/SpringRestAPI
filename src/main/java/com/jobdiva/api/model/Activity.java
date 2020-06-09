package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Activity implements java.io.Serializable {
	
	private Long			id;
	private Long			jobId;
	private Long			recruiterId;
	private Long			candidateId;
	private String			notes;
	private Date			dateInterview;
	private Date			datePresented;
	private Date			dateHired;
	private String			managerFirstName;
	private String			managerLastName;
	private Long			candidateTeamId;
	private Long			recruiterTeamId;
	private Boolean			dirty;
	private Long			customerId;
	private Date			hourlyDateModified;
	private Long			hourlyRecruiterId;
	private Date			dailyDateModified;
	private Long			dailyRecruiterId;
	private Date			yearlyDatemodified;
	private Long			yearlyRecruiterId;
	private Boolean			hourlyCorporate;
	private Boolean			dailyCorporate;
	private Boolean			yearlyCorporate;
	private BigDecimal		hourly;
	private BigDecimal		daily;
	private BigDecimal		yearly;
	private BigDecimal		billHourly;
	private BigDecimal		billDaily;
	private BigDecimal		payHourly;
	private BigDecimal		payDaily;
	private BigDecimal		payYearly;
	private Long			recruiterIdCreator;
	private Date			dateCreated;
	private Date			dateEnded;
	private Date			dateUpdated;
	private Long			roleId;
	private Integer			recordType;
	private Date			dateRejected;
	private Long			rejectedBy;
	private Date			dateExtRejected;
	private Long			extRejectedBy;
	private String			payRateUnits;
	private Date			extDateRejected;
	private BigDecimal		extRejectId;
	private BigDecimal		extRejectReasonId;
	private Date			placementDate;
	private String			presentedTimeZoneId;
	private String			interviewTimeZoneId;
	private String			hiredEndTimeZoneId;
	private Date			dateTerminated;
	private Short			reasonTerminated;
	private Long			terminatorId;
	private String			noteRerminated;
	private Long			primarySalesId;
	private Boolean			lastEmployment;
	private Integer			hourlyCurrency;
	private Integer			dailyCurrency;
	private Integer			yearlyCurrency;
	private Date			dateMail2managerSubmit;
	private Date			dateMail2managerInterview;
	private Short			performanceCode;
	private Long			employType;
	private String			dbFinalBillRateUnit;
	private String			finalBillRateUnit;
	private Integer			finalBillrateCurrency;
	private Boolean			onboardIngassigned;
	private Long			onboardIngassignedBy;
	private Date			onboardIngassignedOn;
	private Date			dimDatePresented;
	private Date			interviewScheduleDate;
	private List<Userfield>	activityUDFs;
	private Integer			contract;
	private BigDecimal		fee;
	private Integer			feeType;
	private String			submittalStatus;
	//
	private String			candidateName;
	private String			candidateFirstName;
	private String			candidateLastName;
	private String			candidatePhones;
	private String			candidateAddress;
	private String			candidateEmail;
	private String			candidateCity;
	private String			candidateState;
	private String			recruiterName;
	private String			recruiterEmail;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}
	
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Date getDateInterview() {
		return dateInterview;
	}
	
	public void setDateInterview(Date dateInterview) {
		this.dateInterview = dateInterview;
	}
	
	public Date getDatePresented() {
		return datePresented;
	}
	
	public void setDatePresented(Date datePresented) {
		this.datePresented = datePresented;
	}
	
	public Date getDateHired() {
		return dateHired;
	}
	
	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}
	
	public String getManagerFirstName() {
		return managerFirstName;
	}
	
	public void setManagerFirstName(String managerFirstName) {
		this.managerFirstName = managerFirstName;
	}
	
	public String getManagerLastName() {
		return managerLastName;
	}
	
	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}
	
	public Long getCandidateTeamId() {
		return candidateTeamId;
	}
	
	public void setCandidateTeamId(Long candidateTeamId) {
		this.candidateTeamId = candidateTeamId;
	}
	
	public Long getRecruiterTeamId() {
		return recruiterTeamId;
	}
	
	public void setRecruiterTeamId(Long recruiterTeamId) {
		this.recruiterTeamId = recruiterTeamId;
	}
	
	public Boolean getDirty() {
		return dirty;
	}
	
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Date getHourlyDateModified() {
		return hourlyDateModified;
	}
	
	public void setHourlyDateModified(Date hourlyDateModified) {
		this.hourlyDateModified = hourlyDateModified;
	}
	
	public Long getHourlyRecruiterId() {
		return hourlyRecruiterId;
	}
	
	public void setHourlyRecruiterId(Long hourlyRecruiterId) {
		this.hourlyRecruiterId = hourlyRecruiterId;
	}
	
	public Date getDailyDateModified() {
		return dailyDateModified;
	}
	
	public void setDailyDateModified(Date dailyDateModified) {
		this.dailyDateModified = dailyDateModified;
	}
	
	public Long getDailyRecruiterId() {
		return dailyRecruiterId;
	}
	
	public void setDailyRecruiterId(Long dailyRecruiterId) {
		this.dailyRecruiterId = dailyRecruiterId;
	}
	
	public Date getYearlyDatemodified() {
		return yearlyDatemodified;
	}
	
	public void setYearlyDatemodified(Date yearlyDatemodified) {
		this.yearlyDatemodified = yearlyDatemodified;
	}
	
	public Long getYearlyRecruiterId() {
		return yearlyRecruiterId;
	}
	
	public void setYearlyRecruiterId(Long yearlyRecruiterId) {
		this.yearlyRecruiterId = yearlyRecruiterId;
	}
	
	public Boolean getHourlyCorporate() {
		return hourlyCorporate;
	}
	
	public void setHourlyCorporate(Boolean hourlyCorporate) {
		this.hourlyCorporate = hourlyCorporate;
	}
	
	public Boolean getDailyCorporate() {
		return dailyCorporate;
	}
	
	public void setDailyCorporate(Boolean dailyCorporate) {
		this.dailyCorporate = dailyCorporate;
	}
	
	public Boolean getYearlyCorporate() {
		return yearlyCorporate;
	}
	
	public void setYearlyCorporate(Boolean yearlyCorporate) {
		this.yearlyCorporate = yearlyCorporate;
	}
	
	public BigDecimal getHourly() {
		return hourly;
	}
	
	public void setHourly(BigDecimal hourly) {
		this.hourly = hourly;
	}
	
	public BigDecimal getDaily() {
		return daily;
	}
	
	public void setDaily(BigDecimal daily) {
		this.daily = daily;
	}
	
	public BigDecimal getYearly() {
		return yearly;
	}
	
	public void setYearly(BigDecimal yearly) {
		this.yearly = yearly;
	}
	
	public BigDecimal getBillHourly() {
		return billHourly;
	}
	
	public void setBillHourly(BigDecimal billHourly) {
		this.billHourly = billHourly;
	}
	
	public BigDecimal getBillDaily() {
		return billDaily;
	}
	
	public void setBillDaily(BigDecimal billDaily) {
		this.billDaily = billDaily;
	}
	
	public BigDecimal getPayHourly() {
		return payHourly;
	}
	
	public void setPayHourly(BigDecimal payHourly) {
		this.payHourly = payHourly;
	}
	
	public BigDecimal getPayDaily() {
		return payDaily;
	}
	
	public void setPayDaily(BigDecimal payDaily) {
		this.payDaily = payDaily;
	}
	
	public BigDecimal getPayYearly() {
		return payYearly;
	}
	
	public void setPayYearly(BigDecimal payYearly) {
		this.payYearly = payYearly;
	}
	
	public Long getRecruiterIdCreator() {
		return recruiterIdCreator;
	}
	
	public void setRecruiterIdCreator(Long recruiterIdCreator) {
		this.recruiterIdCreator = recruiterIdCreator;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getDateEnded() {
		return dateEnded;
	}
	
	public void setDateEnded(Date dateEnded) {
		this.dateEnded = dateEnded;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRecordType() {
		return recordType;
	}
	
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	
	public Date getDateRejected() {
		return dateRejected;
	}
	
	public void setDateRejected(Date dateRejected) {
		this.dateRejected = dateRejected;
	}
	
	public Long getRejectedBy() {
		return rejectedBy;
	}
	
	public void setRejectedBy(Long rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	
	public Date getDateExtRejected() {
		return dateExtRejected;
	}
	
	public void setDateExtRejected(Date dateExtRejected) {
		this.dateExtRejected = dateExtRejected;
	}
	
	public Long getExtRejectedBy() {
		return extRejectedBy;
	}
	
	public void setExtRejectedBy(Long extRejectedBy) {
		this.extRejectedBy = extRejectedBy;
	}
	
	public String getPayRateUnits() {
		return payRateUnits;
	}
	
	public void setPayRateUnits(String payRateUnits) {
		this.payRateUnits = payRateUnits;
	}
	
	public Date getExtDateRejected() {
		return extDateRejected;
	}
	
	public void setExtDateRejected(Date extDateRejected) {
		this.extDateRejected = extDateRejected;
	}
	
	public BigDecimal getExtRejectId() {
		return extRejectId;
	}
	
	public void setExtRejectId(BigDecimal extRejectId) {
		this.extRejectId = extRejectId;
	}
	
	public BigDecimal getExtRejectReasonId() {
		return extRejectReasonId;
	}
	
	public void setExtRejectReasonId(BigDecimal extRejectReasonId) {
		this.extRejectReasonId = extRejectReasonId;
	}
	
	public Date getPlacementDate() {
		return placementDate;
	}
	
	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}
	
	public String getPresentedTimeZoneId() {
		return presentedTimeZoneId;
	}
	
	public void setPresentedTimeZoneId(String presentedTimeZoneId) {
		this.presentedTimeZoneId = presentedTimeZoneId;
	}
	
	public String getInterviewTimeZoneId() {
		return interviewTimeZoneId;
	}
	
	public void setInterviewTimeZoneId(String interviewTimeZoneId) {
		this.interviewTimeZoneId = interviewTimeZoneId;
	}
	
	public String getHiredEndTimeZoneId() {
		return hiredEndTimeZoneId;
	}
	
	public void setHiredEndTimeZoneId(String hiredEndTimeZoneId) {
		this.hiredEndTimeZoneId = hiredEndTimeZoneId;
	}
	
	public Date getDateTerminated() {
		return dateTerminated;
	}
	
	public void setDateTerminated(Date dateTerminated) {
		this.dateTerminated = dateTerminated;
	}
	
	public Short getReasonTerminated() {
		return reasonTerminated;
	}
	
	public void setReasonTerminated(Short reasonTerminated) {
		this.reasonTerminated = reasonTerminated;
	}
	
	public Long getTerminatorId() {
		return terminatorId;
	}
	
	public void setTerminatorId(Long terminatorId) {
		this.terminatorId = terminatorId;
	}
	
	public String getNoteRerminated() {
		return noteRerminated;
	}
	
	public void setNoteRerminated(String noteRerminated) {
		this.noteRerminated = noteRerminated;
	}
	
	public Long getPrimarySalesId() {
		return primarySalesId;
	}
	
	public void setPrimarySalesId(Long primarySalesId) {
		this.primarySalesId = primarySalesId;
	}
	
	public Boolean getLastEmployment() {
		return lastEmployment;
	}
	
	public void setLastEmployment(Boolean lastEmployment) {
		this.lastEmployment = lastEmployment;
	}
	
	public Integer getHourlyCurrency() {
		return hourlyCurrency;
	}
	
	public void setHourlyCurrency(Integer hourlyCurrency) {
		this.hourlyCurrency = hourlyCurrency;
	}
	
	public Integer getDailyCurrency() {
		return dailyCurrency;
	}
	
	public void setDailyCurrency(Integer dailyCurrency) {
		this.dailyCurrency = dailyCurrency;
	}
	
	public Integer getYearlyCurrency() {
		return yearlyCurrency;
	}
	
	public void setYearlyCurrency(Integer yearlyCurrency) {
		this.yearlyCurrency = yearlyCurrency;
	}
	
	public Date getDateMail2managerSubmit() {
		return dateMail2managerSubmit;
	}
	
	public void setDateMail2managerSubmit(Date dateMail2managerSubmit) {
		this.dateMail2managerSubmit = dateMail2managerSubmit;
	}
	
	public Date getDateMail2managerInterview() {
		return dateMail2managerInterview;
	}
	
	public void setDateMail2managerInterview(Date dateMail2managerInterview) {
		this.dateMail2managerInterview = dateMail2managerInterview;
	}
	
	public Short getPerformanceCode() {
		return performanceCode;
	}
	
	public void setPerformanceCode(Short performanceCode) {
		this.performanceCode = performanceCode;
	}
	
	public Long getEmployType() {
		return employType;
	}
	
	public void setEmployType(Long employType) {
		this.employType = employType;
	}
	
	public String getDbFinalBillRateUnit() {
		return dbFinalBillRateUnit;
	}
	
	public void setDbFinalBillRateUnit(String dbFinalBillRateUnit) {
		this.dbFinalBillRateUnit = dbFinalBillRateUnit;
	}
	
	public String getFinalBillRateUnit() {
		return finalBillRateUnit;
	}
	
	public void setFinalBillRateUnit(String finalBillRateUnit) {
		this.finalBillRateUnit = finalBillRateUnit;
	}
	
	public Integer getFinalBillrateCurrency() {
		return finalBillrateCurrency;
	}
	
	public void setFinalBillrateCurrency(Integer finalBillrateCurrency) {
		this.finalBillrateCurrency = finalBillrateCurrency;
	}
	
	public Boolean getOnboardIngassigned() {
		return onboardIngassigned;
	}
	
	public void setOnboardIngassigned(Boolean onboardIngassigned) {
		this.onboardIngassigned = onboardIngassigned;
	}
	
	public Long getOnboardIngassignedBy() {
		return onboardIngassignedBy;
	}
	
	public void setOnboardIngassignedBy(Long onboardIngassignedBy) {
		this.onboardIngassignedBy = onboardIngassignedBy;
	}
	
	public Date getOnboardIngassignedOn() {
		return onboardIngassignedOn;
	}
	
	public void setOnboardIngassignedOn(Date onboardIngassignedOn) {
		this.onboardIngassignedOn = onboardIngassignedOn;
	}
	
	public Date getDimDatePresented() {
		return dimDatePresented;
	}
	
	public void setDimDatePresented(Date dimDatePresented) {
		this.dimDatePresented = dimDatePresented;
	}
	
	public Date getInterviewScheduleDate() {
		return interviewScheduleDate;
	}
	
	public void setInterviewScheduleDate(Date interviewScheduleDate) {
		this.interviewScheduleDate = interviewScheduleDate;
	}
	
	public List<Userfield> getActivityUDFs() {
		return activityUDFs;
	}
	
	public void setActivityUDFs(List<Userfield> activityUDFs) {
		this.activityUDFs = activityUDFs;
	}
	
	public Integer getContract() {
		return contract;
	}
	
	public void setContract(Integer contract) {
		this.contract = contract;
	}
	
	public BigDecimal getFee() {
		return fee;
	}
	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public Integer getFeeType() {
		return feeType;
	}
	
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	
	public String getSubmittalStatus() {
		return submittalStatus;
	}
	
	public void setSubmittalStatus(String submittalStatus) {
		this.submittalStatus = submittalStatus;
	}
	
	public String getCandidateName() {
		return candidateName;
	}
	
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	
	public String getCandidatePhones() {
		return candidatePhones;
	}
	
	public void setCandidatePhones(String candidatePhones) {
		this.candidatePhones = candidatePhones;
	}
	
	public String getCandidateAddress() {
		return candidateAddress;
	}
	
	public void setCandidateAddress(String candidateAddress) {
		this.candidateAddress = candidateAddress;
	}
	
	public String getCandidateEmail() {
		return candidateEmail;
	}
	
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}
	
	public String getRecruiterName() {
		return recruiterName;
	}
	
	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}
	
	public String getCandidateFirstName() {
		return candidateFirstName;
	}
	
	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}
	
	public String getCandidateLastName() {
		return candidateLastName;
	}
	
	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}
	
	public String getRecruiterEmail() {
		return recruiterEmail;
	}
	
	public void setRecruiterEmail(String recruiterEmail) {
		this.recruiterEmail = recruiterEmail;
	}
	
	public String getCandidateCity() {
		return candidateCity;
	}
	
	public void setCandidateCity(String candidateCity) {
		this.candidateCity = candidateCity;
	}
	
	public String getCandidateState() {
		return candidateState;
	}
	
	public void setCandidateState(String candidateState) {
		this.candidateState = candidateState;
	}
}