package com.jobdiva.api.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Joseph Chidiac
 *
 */
public interface IActivity {
	
	public Long getId();
	
	public void setId(Long id);
	
	public Long getJobId();
	
	public void setJobId(Long jobId);
	
	public Long getRecruiterId();
	
	public void setRecruiterId(Long recruiterId);
	
	public Long getCandidateId();
	
	public void setCandidateId(Long candidateId);
	
	public String getNotes();
	
	public void setNotes(String notes);
	
	public Date getDateInterview();
	
	public void setDateInterview(Date dateInterview);
	
	public Date getDatePresented();
	
	public void setDatePresented(Date datePresented);
	
	public Date getDateHired();
	
	public void setDateHired(Date dateHired);
	
	public String getManagerFirstName();
	
	public void setManagerFirstName(String managerFirstName);
	
	public String getManagerLastName();
	
	public void setManagerLastName(String managerLastName);
	
	public Long getCandidateTeamId();
	
	public void setCandidateTeamId(Long candidateTeamId);
	
	public Long getRecruiterTeamId();
	
	public void setRecruiterTeamId(Long recruiterTeamId);
	
	public Boolean getDirty();
	
	public void setDirty(Boolean dirty);
	
	public Long getCustomerId();
	
	public void setCustomerId(Long customerId);
	
	public Date getHourlyDateModified();
	
	public void setHourlyDateModified(Date hourlyDateModified);
	
	public Long getHourlyRecruiterId();
	
	public void setHourlyRecruiterId(Long hourlyRecruiterId);
	
	public Date getDailyDateModified();
	
	public void setDailyDateModified(Date dailyDateModified);
	
	public Long getDailyRecruiterId();
	
	public void setDailyRecruiterId(Long dailyRecruiterId);
	
	public Date getYearlyDatemodified();
	
	public void setYearlyDatemodified(Date yearlyDatemodified);
	
	public Long getYearlyRecruiterId();
	
	public void setYearlyRecruiterId(Long yearlyRecruiterId);
	
	public Boolean getHourlyCorporate();
	
	public void setHourlyCorporate(Boolean hourlyCorporate);
	
	public Boolean getDailyCorporate();
	
	public void setDailyCorporate(Boolean dailyCorporate);
	
	public Boolean getYearlyCorporate();
	
	public void setYearlyCorporate(Boolean yearlyCorporate);
	
	public BigDecimal getHourly();
	
	public void setHourly(BigDecimal hourly);
	
	public BigDecimal getDaily();
	
	public void setDaily(BigDecimal daily);
	
	public BigDecimal getYearly();
	
	public void setYearly(BigDecimal yearly);
	
	public BigDecimal getBillHourly();
	
	public void setBillHourly(BigDecimal billHourly);
	
	public BigDecimal getBillDaily();
	
	public void setBillDaily(BigDecimal billDaily);
	
	public BigDecimal getPayHourly();
	
	public void setPayHourly(BigDecimal payHourly);
	
	public BigDecimal getPayDaily();
	
	public void setPayDaily(BigDecimal payDaily);
	
	public BigDecimal getPayYearly();
	
	public void setPayYearly(BigDecimal payYearly);
	
	public Long getRecruiterIdCreator();
	
	public void setRecruiterIdCreator(Long recruiterIdCreator);
	
	public Date getDateCreated();
	
	public void setDateCreated(Date dateCreated);
	
	public Date getDateEnded();
	
	public void setDateEnded(Date dateEnded);
	
	public Date getDateUpdated();
	
	public void setDateUpdated(Date dateUpdated);
	
	public Long getRoleId();
	
	public void setRoleId(Long roleId);
	
	public Integer getRecordType();
	
	public void setRecordType(Integer recordType);
	
	public Date getDateRejected();
	
	public void setDateRejected(Date dateRejected);
	
	public Long getRejectedBy();
	
	public void setRejectedBy(Long rejectedBy);
	
	public Date getDateExtRejected();
	
	public void setDateExtRejected(Date dateExtRejected);
	
	public Long getExtRejectedBy();
	
	public void setExtRejectedBy(Long extRejectedBy);
	
	public String getPayRateUnits();
	
	public void setPayRateUnits(String payRateUnits);
	
	public Date getExtDateRejected();
	
	public void setExtDateRejected(Date extDateRejected);
	
	public BigDecimal getExtRejectId();
	
	public void setExtRejectId(BigDecimal extRejectId);
	
	public BigDecimal getExtRejectReasonId();
	
	public void setExtRejectReasonId(BigDecimal extRejectReasonId);
	
	public Date getPlacementDate();
	
	public void setPlacementDate(Date placementDate);
	
	public String getPresentedTimeZoneId();
	
	public void setPresentedTimeZoneId(String presentedTimeZoneId);
	
	public String getInterviewTimeZoneId();
	
	public void setInterviewTimeZoneId(String interviewTimeZoneId);
	
	public String getHiredEndTimeZoneId();
	
	public void setHiredEndTimeZoneId(String hiredEndTimeZoneId);
	
	public Date getDateTerminated();
	
	public void setDateTerminated(Date dateTerminated);
	
	public Short getReasonTerminated();
	
	public void setReasonTerminated(Short reasonTerminated);
	
	public Long getTerminatorId();
	
	public void setTerminatorId(Long terminatorId);
	
	public String getNoteRerminated();
	
	public void setNoteRerminated(String noteRerminated);
	
	public Long getPrimarySalesId();
	
	public void setPrimarySalesId(Long primarySalesId);
	
	public Boolean getLastEmployment();
	
	public void setLastEmployment(Boolean lastEmployment);
	
	public Integer getHourlyCurrency();
	
	public void setHourlyCurrency(Integer hourlyCurrency);
	
	public Integer getDailyCurrency();
	
	public void setDailyCurrency(Integer dailyCurrency);
	
	public Integer getYearlyCurrency();
	
	public void setYearlyCurrency(Integer yearlyCurrency);
	
	public Date getDateMail2managerSubmit();
	
	public void setDateMail2managerSubmit(Date dateMail2managerSubmit);
	
	public Date getDateMail2managerInterview();
	
	public void setDateMail2managerInterview(Date dateMail2managerInterview);
	
	public Short getPerformanceCode();
	
	public void setPerformanceCode(Short performanceCode);
	
	public Long getEmployType();
	
	public void setEmployType(Long employType);
	
	public String getDbFinalBillRateUnit();
	
	public void setDbFinalBillRateUnit(String dbFinalBillRateUnit);
	
	public String getFinalBillRateUnit();
	
	public void setFinalBillRateUnit(String finalBillRateUnit);
	
	public Integer getFinalBillrateCurrency();
	
	public void setFinalBillrateCurrency(Integer finalBillrateCurrency);
	
	public Boolean getOnboardIngassigned();
	
	public void setOnboardIngassigned(Boolean onboardIngassigned);
	
	public Long getOnboardIngassignedBy();
	
	public void setOnboardIngassignedBy(Long onboardIngassignedBy);
	
	public Date getOnboardIngassignedOn();
	
	public void setOnboardIngassignedOn(Date onboardIngassignedOn);
	
	public Date getDimDatePresented();
	
	public void setDimDatePresented(Date dimDatePresented);
	
	public Date getInterviewScheduleDate();
	
	public void setInterviewScheduleDate(Date interviewScheduleDate);
	
	public List<Userfield> getActivityUDFs();
	
	public void setActivityUDFs(List<Userfield> activityUDFs);
	
	public Integer getContract();
	
	public void setContract(Integer contract);
	
	public BigDecimal getFee();
	
	public void setFee(BigDecimal fee);
	
	public Integer getFeeType();
	
	public void setFeeType(Integer feeType);
	
	public String getSubmittalStatus();
	
	public void setSubmittalStatus(String submittalStatus);
	
	public String getCandidateName();
	
	public void setCandidateName(String candidateName);
	
	public String getCandidatePhones();
	
	public void setCandidatePhones(String candidatePhones);
	
	public String getCandidateAddress();
	
	public void setCandidateAddress(String candidateAddress);
	
	public String getCandidateEmail();
	
	public void setCandidateEmail(String candidateEmail);
	
	public String getRecruiterName();
	
	public void setRecruiterName(String recruiterName);
	
	public String getCandidateFirstName();
	
	public void setCandidateFirstName(String candidateFirstName);
	
	public String getCandidateLastName();
	
	public void setCandidateLastName(String candidateLastName);
	
	public String getRecruiterEmail();
	
	public void setRecruiterEmail(String recruiterEmail);
	
	public String getCandidateCity();
	
	public void setCandidateCity(String candidateCity);
	
	public String getCandidateState();
	
	public void setCandidateState(String candidateState);
}
