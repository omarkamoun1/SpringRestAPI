package com.jobdiva.api.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
@ApiModel()
public class BillingRecord implements java.io.Serializable {
	
	private Long	candidateID;
	private Long	assignmentID;
	private Long	jobID;
	private Integer	recordID;
	private Long	createdByID;
	private Boolean	approved;
	private Date	startDate;
	private Date	endDate;
	private String	customerRefNo;
	private Long	hiringManagerID;
	private Long	billingContactID;
	private Long	division;
	private Integer	invoiceGroupIndex;
	private String	invoiceGroup;
	private String	vMSWebsite;
	private String	vMSEmployeeName;
	private Integer	invoiceContent;
	private Integer	expenseInvoices;
	private Boolean	enableTimesheet;
	private Boolean	allowEnterTimeOnPortal;
	private String	timesheetInstruction;
	private Boolean	expenseEnabled;
	private Double	billRate;
	private String	billRatePer;
	private Boolean	overtimeExempt;
	private Long	timesheetEntryFormat;
	private Integer	frequency;
	private Integer	overtimeByWorkingState;
	private Double	overtimeRate;
	private String	overtimeRatePer;
	private Double	doubletimeRate;
	private String	doubletimePer;
	private Integer	billingUnit;
	private Integer	weekEnding;
	private Double	hoursPerDay;
	private Double	hoursPerHalfDay;
	private String	workAddress1;
	private String	workAddress2;
	private String	workCity;
	private String	workState;
	private String	workZipcode;
	private String	workCountry;
	private Integer	paymentTerms;
	private Long	primarySalesPersonID;
	private Double	primarySalesPercentage;
	private Long	secondarySalesPersonID;
	private Double	secondarySalesPercentage;
	private Long	tertiarySalesPersonID;
	private Double	tertiarySalesPercentage;
	private Long	primaryRecruiterID;
	private Double	primaryRecruiterPercentage;
	private Long	secondaryRecruiterID;
	private Double	secondaryRecruiterPercentage;
	private Long	tertiaryRecruiterID;
	private Double	tertiaryRecruiterPercentage;
	
	public Long getCandidateID() {
		return candidateID;
	}
	
	public void setCandidateID(Long candidateID) {
		this.candidateID = candidateID;
	}
	
	public Long getAssignmentID() {
		return assignmentID;
	}
	
	public void setAssignmentID(Long assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	public Long getJobID() {
		return jobID;
	}
	
	public void setJobID(Long jobID) {
		this.jobID = jobID;
	}
	
	public Integer getRecordID() {
		return recordID;
	}
	
	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	
	public Long getCreatedByID() {
		return createdByID;
	}
	
	public void setCreatedByID(Long createdByID) {
		this.createdByID = createdByID;
	}
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
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
	
	public String getCustomerRefNo() {
		return customerRefNo;
	}
	
	public void setCustomerRefNo(String customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	
	public Long getHiringManagerID() {
		return hiringManagerID;
	}
	
	public void setHiringManagerID(Long hiringManagerID) {
		this.hiringManagerID = hiringManagerID;
	}
	
	public Long getBillingContactID() {
		return billingContactID;
	}
	
	public void setBillingContactID(Long billingContactID) {
		this.billingContactID = billingContactID;
	}
	
	public Long getDivision() {
		return division;
	}
	
	public void setDivision(Long division) {
		this.division = division;
	}
	
	public Integer getInvoiceGroupIndex() {
		return invoiceGroupIndex;
	}
	
	public void setInvoiceGroupIndex(Integer invoiceGroupIndex) {
		this.invoiceGroupIndex = invoiceGroupIndex;
	}
	
	public String getInvoiceGroup() {
		return invoiceGroup;
	}
	
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}
	
	public String getvMSWebsite() {
		return vMSWebsite;
	}
	
	public void setvMSWebsite(String vMSWebsite) {
		this.vMSWebsite = vMSWebsite;
	}
	
	public String getvMSEmployeeName() {
		return vMSEmployeeName;
	}
	
	public void setvMSEmployeeName(String vMSEmployeeName) {
		this.vMSEmployeeName = vMSEmployeeName;
	}
	
	public Integer getInvoiceContent() {
		return invoiceContent;
	}
	
	public void setInvoiceContent(Integer invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	public Integer getExpenseInvoices() {
		return expenseInvoices;
	}
	
	public void setExpenseInvoices(Integer expenseInvoices) {
		this.expenseInvoices = expenseInvoices;
	}
	
	public Boolean getEnableTimesheet() {
		return enableTimesheet;
	}
	
	public void setEnableTimesheet(Boolean enableTimesheet) {
		this.enableTimesheet = enableTimesheet;
	}
	
	public Boolean getAllowEnterTimeOnPortal() {
		return allowEnterTimeOnPortal;
	}
	
	public void setAllowEnterTimeOnPortal(Boolean allowEnterTimeOnPortal) {
		this.allowEnterTimeOnPortal = allowEnterTimeOnPortal;
	}
	
	public String getTimesheetInstruction() {
		return timesheetInstruction;
	}
	
	public void setTimesheetInstruction(String timesheetInstruction) {
		this.timesheetInstruction = timesheetInstruction;
	}
	
	public Boolean getExpenseEnabled() {
		return expenseEnabled;
	}
	
	public void setExpenseEnabled(Boolean expenseEnabled) {
		this.expenseEnabled = expenseEnabled;
	}
	
	public Double getBillRate() {
		return billRate;
	}
	
	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}
	
	public String getBillRatePer() {
		return billRatePer;
	}
	
	public void setBillRatePer(String billRatePer) {
		this.billRatePer = billRatePer;
	}
	
	public Boolean getOvertimeExempt() {
		return overtimeExempt;
	}
	
	public void setOvertimeExempt(Boolean overtimeExempt) {
		this.overtimeExempt = overtimeExempt;
	}
	
	public Long getTimesheetEntryFormat() {
		return timesheetEntryFormat;
	}
	
	public void setTimesheetEntryFormat(Long timesheetEntryFormat) {
		this.timesheetEntryFormat = timesheetEntryFormat;
	}
	
	public Integer getFrequency() {
		return frequency;
	}
	
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public Integer getOvertimeByWorkingState() {
		return overtimeByWorkingState;
	}
	
	public void setOvertimeByWorkingState(Integer overtimeByWorkingState) {
		this.overtimeByWorkingState = overtimeByWorkingState;
	}
	
	public Double getOvertimeRate() {
		return overtimeRate;
	}
	
	public void setOvertimeRate(Double overtimeRate) {
		this.overtimeRate = overtimeRate;
	}
	
	public String getOvertimeRatePer() {
		return overtimeRatePer;
	}
	
	public void setOvertimeRatePer(String overtimeRatePer) {
		this.overtimeRatePer = overtimeRatePer;
	}
	
	public Double getDoubletimeRate() {
		return doubletimeRate;
	}
	
	public void setDoubletimeRate(Double doubletimeRate) {
		this.doubletimeRate = doubletimeRate;
	}
	
	public String getDoubletimePer() {
		return doubletimePer;
	}
	
	public void setDoubletimePer(String doubletimePer) {
		this.doubletimePer = doubletimePer;
	}
	
	public Integer getBillingUnit() {
		return billingUnit;
	}
	
	public void setBillingUnit(Integer billingUnit) {
		this.billingUnit = billingUnit;
	}
	
	public Integer getWeekEnding() {
		return weekEnding;
	}
	
	public void setWeekEnding(Integer weekEnding) {
		this.weekEnding = weekEnding;
	}
	
	public Double getHoursPerDay() {
		return hoursPerDay;
	}
	
	public void setHoursPerDay(Double hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}
	
	public Double getHoursPerHalfDay() {
		return hoursPerHalfDay;
	}
	
	public void setHoursPerHalfDay(Double hoursPerHalfDay) {
		this.hoursPerHalfDay = hoursPerHalfDay;
	}
	
	public String getWorkAddress1() {
		return workAddress1;
	}
	
	public void setWorkAddress1(String workAddress1) {
		this.workAddress1 = workAddress1;
	}
	
	public String getWorkAddress2() {
		return workAddress2;
	}
	
	public void setWorkAddress2(String workAddress2) {
		this.workAddress2 = workAddress2;
	}
	
	public String getWorkCity() {
		return workCity;
	}
	
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	
	public String getWorkState() {
		return workState;
	}
	
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	
	public String getWorkZipcode() {
		return workZipcode;
	}
	
	public void setWorkZipcode(String workZipcode) {
		this.workZipcode = workZipcode;
	}
	
	public String getWorkCountry() {
		return workCountry;
	}
	
	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
	}
	
	public Integer getPaymentTerms() {
		return paymentTerms;
	}
	
	public void setPaymentTerms(Integer paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	public Long getPrimarySalesPersonID() {
		return primarySalesPersonID;
	}
	
	public void setPrimarySalesPersonID(Long primarySalesPersonID) {
		this.primarySalesPersonID = primarySalesPersonID;
	}
	
	public Double getPrimarySalesPercentage() {
		return primarySalesPercentage;
	}
	
	public void setPrimarySalesPercentage(Double primarySalesPercentage) {
		this.primarySalesPercentage = primarySalesPercentage;
	}
	
	public Long getSecondarySalesPersonID() {
		return secondarySalesPersonID;
	}
	
	public void setSecondarySalesPersonID(Long secondarySalesPersonID) {
		this.secondarySalesPersonID = secondarySalesPersonID;
	}
	
	public Double getSecondarySalesPercentage() {
		return secondarySalesPercentage;
	}
	
	public void setSecondarySalesPercentage(Double secondarySalesPercentage) {
		this.secondarySalesPercentage = secondarySalesPercentage;
	}
	
	public Long getTertiarySalesPersonID() {
		return tertiarySalesPersonID;
	}
	
	public void setTertiarySalesPersonID(Long tertiarySalesPersonID) {
		this.tertiarySalesPersonID = tertiarySalesPersonID;
	}
	
	public Double getTertiarySalesPercentage() {
		return tertiarySalesPercentage;
	}
	
	public void setTertiarySalesPercentage(Double tertiarySalesPercentage) {
		this.tertiarySalesPercentage = tertiarySalesPercentage;
	}
	
	public Long getPrimaryRecruiterID() {
		return primaryRecruiterID;
	}
	
	public void setPrimaryRecruiterID(Long primaryRecruiterID) {
		this.primaryRecruiterID = primaryRecruiterID;
	}
	
	public Double getPrimaryRecruiterPercentage() {
		return primaryRecruiterPercentage;
	}
	
	public void setPrimaryRecruiterPercentage(Double primaryRecruiterPercentage) {
		this.primaryRecruiterPercentage = primaryRecruiterPercentage;
	}
	
	public Long getSecondaryRecruiterID() {
		return secondaryRecruiterID;
	}
	
	public void setSecondaryRecruiterID(Long secondaryRecruiterID) {
		this.secondaryRecruiterID = secondaryRecruiterID;
	}
	
	public Double getSecondaryRecruiterPercentage() {
		return secondaryRecruiterPercentage;
	}
	
	public void setSecondaryRecruiterPercentage(Double secondaryRecruiterPercentage) {
		this.secondaryRecruiterPercentage = secondaryRecruiterPercentage;
	}
	
	public Long getTertiaryRecruiterID() {
		return tertiaryRecruiterID;
	}
	
	public void setTertiaryRecruiterID(Long tertiaryRecruiterID) {
		this.tertiaryRecruiterID = tertiaryRecruiterID;
	}
	
	public Double getTertiaryRecruiterPercentage() {
		return tertiaryRecruiterPercentage;
	}
	
	public void setTertiaryRecruiterPercentage(Double tertiaryRecruiterPercentage) {
		this.tertiaryRecruiterPercentage = tertiaryRecruiterPercentage;
	}
}
