package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 27, 2021
 */
@SuppressWarnings("serial")
public class UpdateBillingRecordDef implements Serializable {
	
	@JsonProperty(required = false)
	private Boolean	allowEnterTimeOnPortal;
	//
	@JsonProperty(required = false)
	private Integer	approved;
	//
	@JsonProperty(required = true)
	private Double	assignmentID;
	//
	@JsonProperty(required = false)
	private Long	billingContactID;
	//
	@JsonProperty(required = false)
	private Integer	billingUnit;
	//
	@JsonProperty(required = false)
	private Double	billRate;
	//
	@JsonProperty(required = false)
	private String	billRatePer;
	//
	@JsonProperty(required = true)
	private Long	candidateID;
	//
	@JsonProperty(required = false)
	private String	customerRefNo;
	//
	@JsonProperty(required = false)
	private Long	division;
	//
	@JsonProperty(required = false)
	private Double	doubletimePer;
	//
	@JsonProperty(required = false)
	private Double	doubletimeRate;
	//
	@JsonProperty(required = false)
	private String	doubletimeRatePer;
	//
	@JsonProperty(required = false)
	private Boolean	enableTimesheet;
	//
	@JsonProperty(required = false)
	private Date	endDate;
	//
	@JsonProperty(required = false)
	private Boolean	expenseEnabled;
	//
	@JsonProperty(required = false)
	private Integer	expenseInvoices;
	//
	@JsonProperty(required = false)
	private Integer	frequency;
	//
	@JsonProperty(required = false)
	private Long	hiringManagerID;
	//
	@JsonProperty(required = false)
	private Double	hoursPerDay;
	//
	@JsonProperty(required = false)
	private Double	hoursPerHalfDay;
	//
	@JsonProperty(required = false)
	private Integer	invoiceContent;
	//
	@JsonProperty(required = false)
	private String	invoiceGroup;
	//
	@JsonProperty(required = false)
	private Integer	invoiceGroupIndex;
	//
	@JsonProperty(required = false)
	private Double	jobID;
	//
	@JsonProperty(required = false)
	private Integer	overtimeByWorkingState;
	//
	@JsonProperty(required = false)
	private Boolean	overtimeExempt;
	//
	@JsonProperty(required = false)
	private Double	overtimeRate;
	//
	@JsonProperty(required = false)
	private String	overtimeRatePer;
	//
	@JsonProperty(required = false)
	private String	paymentTerms;
	//
	@JsonProperty(required = false)
	private Long	primaryRecruiterID;
	//
	@JsonProperty(required = false)
	private Double	primaryRecruiterPercentage;
	//
	@JsonProperty(required = false)
	private Double	primarySalesPercentage;
	//
	@JsonProperty(required = false)
	private Long	primarySalesPersonID;
	//
	@JsonProperty(required = false)
	private Integer	recordID;
	//
	@JsonProperty(required = false)
	private Long	secondaryRecruiterID;
	//
	@JsonProperty(required = false)
	private Double	secondaryRecruiterPercentage;
	//
	@JsonProperty(required = false)
	private Double	secondarySalesPercentage;
	//
	@JsonProperty(required = false)
	private Long	secondarySalesPersonID;
	//
	@JsonProperty(required = false)
	private Date	startDate;
	//
	@JsonProperty(required = false)
	private Long	tertiaryRecruiterID;
	//
	@JsonProperty(required = false)
	private Double	tertiaryRecruiterPercentage;
	//
	@JsonProperty(required = false)
	private Double	tertiarySalesPercentage;
	//
	@JsonProperty(required = false)
	private Long	tertiarySalesPersonID;
	//
	@JsonProperty(required = false)
	private Long	timesheetEntryFormat;
	//
	@JsonProperty(required = false)
	private String	timesheetInstruction;
	//
	@JsonProperty(required = false)
	private String	vMSEmployeeName;
	//
	@JsonProperty(required = false)
	private String	vMSWebsite;
	//
	@JsonProperty(required = false)
	private Integer	weekEnding;
	//
	@JsonProperty(required = false)
	private String	workAddress1;
	//
	@JsonProperty(required = false)
	private String	workAddress2;
	//
	@JsonProperty(required = false)
	private String	workCity;
	//
	@JsonProperty(required = false)
	private String	workCountry;
	//
	@JsonProperty(required = false)
	private String	workState;
	//
	@JsonProperty(required = false)
	private String	workZipcode;
	
	public Boolean getAllowEnterTimeOnPortal() {
		return allowEnterTimeOnPortal;
	}
	
	public void setAllowEnterTimeOnPortal(Boolean allowEnterTimeOnPortal) {
		this.allowEnterTimeOnPortal = allowEnterTimeOnPortal;
	}
	
	public Integer getApproved() {
		return approved;
	}
	
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	
	public Double getAssignmentID() {
		return assignmentID;
	}
	
	public void setAssignmentID(Double assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	public Long getBillingContactID() {
		return billingContactID;
	}
	
	public void setBillingContactID(Long billingContactID) {
		this.billingContactID = billingContactID;
	}
	
	public Integer getBillingUnit() {
		return billingUnit;
	}
	
	public void setBillingUnit(Integer billingUnit) {
		this.billingUnit = billingUnit;
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
	
	public Long getCandidateID() {
		return candidateID;
	}
	
	public void setCandidateID(Long candidateID) {
		this.candidateID = candidateID;
	}
	
	public String getCustomerRefNo() {
		return customerRefNo;
	}
	
	public void setCustomerRefNo(String customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	
	public Long getDivision() {
		return division;
	}
	
	public void setDivision(Long division) {
		this.division = division;
	}
	
	public Double getDoubletimePer() {
		return doubletimePer;
	}
	
	public void setDoubletimePer(Double doubletimePer) {
		this.doubletimePer = doubletimePer;
	}
	
	public Double getDoubletimeRate() {
		return doubletimeRate;
	}
	
	public void setDoubletimeRate(Double doubletimeRate) {
		this.doubletimeRate = doubletimeRate;
	}
	
	public String getDoubletimeRatePer() {
		return doubletimeRatePer;
	}
	
	public void setDoubletimeRatePer(String doubletimeRatePer) {
		this.doubletimeRatePer = doubletimeRatePer;
	}
	
	public Boolean getEnableTimesheet() {
		return enableTimesheet;
	}
	
	public void setEnableTimesheet(Boolean enableTimesheet) {
		this.enableTimesheet = enableTimesheet;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Boolean getExpenseEnabled() {
		return expenseEnabled;
	}
	
	public void setExpenseEnabled(Boolean expenseEnabled) {
		this.expenseEnabled = expenseEnabled;
	}
	
	public Integer getExpenseInvoices() {
		return expenseInvoices;
	}
	
	public void setExpenseInvoices(Integer expenseInvoices) {
		this.expenseInvoices = expenseInvoices;
	}
	
	public Integer getFrequency() {
		return frequency;
	}
	
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public Long getHiringManagerID() {
		return hiringManagerID;
	}
	
	public void setHiringManagerID(Long hiringManagerID) {
		this.hiringManagerID = hiringManagerID;
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
	
	public Integer getInvoiceContent() {
		return invoiceContent;
	}
	
	public void setInvoiceContent(Integer invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	public String getInvoiceGroup() {
		return invoiceGroup;
	}
	
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}
	
	public Integer getInvoiceGroupIndex() {
		return invoiceGroupIndex;
	}
	
	public void setInvoiceGroupIndex(Integer invoiceGroupIndex) {
		this.invoiceGroupIndex = invoiceGroupIndex;
	}
	
	public Double getJobID() {
		return jobID;
	}
	
	public void setJobID(Double jobID) {
		this.jobID = jobID;
	}
	
	public Integer getOvertimeByWorkingState() {
		return overtimeByWorkingState;
	}
	
	public void setOvertimeByWorkingState(Integer overtimeByWorkingState) {
		this.overtimeByWorkingState = overtimeByWorkingState;
	}
	
	public Boolean getOvertimeExempt() {
		return overtimeExempt;
	}
	
	public void setOvertimeExempt(Boolean overtimeExempt) {
		this.overtimeExempt = overtimeExempt;
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
	
	public String getPaymentTerms() {
		return paymentTerms;
	}
	
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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
	
	public Double getPrimarySalesPercentage() {
		return primarySalesPercentage;
	}
	
	public void setPrimarySalesPercentage(Double primarySalesPercentage) {
		this.primarySalesPercentage = primarySalesPercentage;
	}
	
	public Long getPrimarySalesPersonID() {
		return primarySalesPersonID;
	}
	
	public void setPrimarySalesPersonID(Long primarySalesPersonID) {
		this.primarySalesPersonID = primarySalesPersonID;
	}
	
	public Integer getRecordID() {
		return recordID;
	}
	
	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
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
	
	public Double getSecondarySalesPercentage() {
		return secondarySalesPercentage;
	}
	
	public void setSecondarySalesPercentage(Double secondarySalesPercentage) {
		this.secondarySalesPercentage = secondarySalesPercentage;
	}
	
	public Long getSecondarySalesPersonID() {
		return secondarySalesPersonID;
	}
	
	public void setSecondarySalesPersonID(Long secondarySalesPersonID) {
		this.secondarySalesPersonID = secondarySalesPersonID;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	
	public Double getTertiarySalesPercentage() {
		return tertiarySalesPercentage;
	}
	
	public void setTertiarySalesPercentage(Double tertiarySalesPercentage) {
		this.tertiarySalesPercentage = tertiarySalesPercentage;
	}
	
	public Long getTertiarySalesPersonID() {
		return tertiarySalesPersonID;
	}
	
	public void setTertiarySalesPersonID(Long tertiarySalesPersonID) {
		this.tertiarySalesPersonID = tertiarySalesPersonID;
	}
	
	public Long getTimesheetEntryFormat() {
		return timesheetEntryFormat;
	}
	
	public void setTimesheetEntryFormat(Long timesheetEntryFormat) {
		this.timesheetEntryFormat = timesheetEntryFormat;
	}
	
	public String getTimesheetInstruction() {
		return timesheetInstruction;
	}
	
	public void setTimesheetInstruction(String timesheetInstruction) {
		this.timesheetInstruction = timesheetInstruction;
	}
	
	public String getvMSEmployeeName() {
		return vMSEmployeeName;
	}
	
	public void setvMSEmployeeName(String vMSEmployeeName) {
		this.vMSEmployeeName = vMSEmployeeName;
	}
	
	public String getvMSWebsite() {
		return vMSWebsite;
	}
	
	public void setvMSWebsite(String vMSWebsite) {
		this.vMSWebsite = vMSWebsite;
	}
	
	public Integer getWeekEnding() {
		return weekEnding;
	}
	
	public void setWeekEnding(Integer weekEnding) {
		this.weekEnding = weekEnding;
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
	
	public String getWorkCountry() {
		return workCountry;
	}
	
	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
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
}
