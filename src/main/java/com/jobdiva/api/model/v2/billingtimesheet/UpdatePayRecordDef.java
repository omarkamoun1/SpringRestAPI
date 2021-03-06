package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Userfield;

/**
 * @author Joseph Chidiac
 *
 *         Apr 27, 2021
 */
@SuppressWarnings("serial")
public class UpdatePayRecordDef implements Serializable {
	
	@JsonProperty(required = false)
	private String		aDPCOCODE;
	//
	//
	@JsonProperty(required = false)
	private String		aDPPAYFREQUENCY;
	//
	//
	@JsonProperty(required = false)
	private Boolean		approved;
	//
	//
	@JsonProperty(required = true)
	private Double		assignmentID;
	//
	//
	@JsonProperty(required = true)
	private Long		candidateID;
	//
	//
	@JsonProperty(required = false)
	private Double		doubletimeRate;
	//
	//
	@JsonProperty(required = false)
	private String		doubletimeRatePer;
	//
	//
	@JsonProperty(required = false)
	private Date		startDate;
	//
	//
	@JsonProperty(required = false)
	private Date		endDate;
	//
	//
	@JsonProperty(required = false)
	private String		fileNo;
	//
	//
	@JsonProperty(required = false)
	private Double		otherExpenses;
	//
	//
	@JsonProperty(required = false)
	private String		otherExpensesPer;
	//
	//
	@JsonProperty(required = false)
	private Double		outsideCommission;
	//
	//
	@JsonProperty(required = false)
	private String		outsideCommissionPer;
	//
	//
	@JsonProperty(required = false)
	private Boolean		overtimeExempt;
	//
	//
	@JsonProperty(required = false)
	private Double		overtimeRate;
	//
	//
	@JsonProperty(required = false)
	private String		overtimeRatePer;
	//
	//
	@JsonProperty(required = false)
	private String		paymentTerms;
	//
	//
	@JsonProperty(required = false)
	private Boolean		payOnRemittance;
	//
	//
	@JsonProperty(required = false)
	private Double		perDiem;
	//
	//
	@JsonProperty(required = false)
	private String		perDiemPer;
	//
	//
	@JsonProperty(required = false)
	private Integer		recordID;
	//
	//
	@JsonProperty(required = false)
	private Double		salary;
	//
	//
	@JsonProperty(required = false)
	private String		salaryPer;
	//
	//
	@JsonProperty(required = false)
	private Integer		salaryPerCurrency;
	//
	//
	@JsonProperty(required = false)
	private Integer		status;
	//
	//
	@JsonProperty(required = false)
	private Long		subcontractCompanyID;
	//
	//
	@JsonProperty(required = false)
	private String		taxID;
	//
	@JsonProperty(value = "Userfields", required = false) //
	private Userfield[]	userfields;
	
	public String getaDPCOCODE() {
		return aDPCOCODE;
	}
	
	public void setaDPCOCODE(String aDPCOCODE) {
		this.aDPCOCODE = aDPCOCODE;
	}
	
	public String getaDPPAYFREQUENCY() {
		return aDPPAYFREQUENCY;
	}
	
	public void setaDPPAYFREQUENCY(String aDPPAYFREQUENCY) {
		this.aDPPAYFREQUENCY = aDPPAYFREQUENCY;
	}
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public Double getAssignmentID() {
		return assignmentID;
	}
	
	public void setAssignmentID(Double assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	public Long getCandidateID() {
		return candidateID;
	}
	
	public void setCandidateID(Long candidateID) {
		this.candidateID = candidateID;
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
	
	public String getFileNo() {
		return fileNo;
	}
	
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
	public Double getOtherExpenses() {
		return otherExpenses;
	}
	
	public void setOtherExpenses(Double otherExpenses) {
		this.otherExpenses = otherExpenses;
	}
	
	public String getOtherExpensesPer() {
		return otherExpensesPer;
	}
	
	public void setOtherExpensesPer(String otherExpensesPer) {
		this.otherExpensesPer = otherExpensesPer;
	}
	
	public Double getOutsideCommission() {
		return outsideCommission;
	}
	
	public void setOutsideCommission(Double outsideCommission) {
		this.outsideCommission = outsideCommission;
	}
	
	public String getOutsideCommissionPer() {
		return outsideCommissionPer;
	}
	
	public void setOutsideCommissionPer(String outsideCommissionPer) {
		this.outsideCommissionPer = outsideCommissionPer;
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
	
	public Boolean getPayOnRemittance() {
		return payOnRemittance;
	}
	
	public void setPayOnRemittance(Boolean payOnRemittance) {
		this.payOnRemittance = payOnRemittance;
	}
	
	public Double getPerDiem() {
		return perDiem;
	}
	
	public void setPerDiem(Double perDiem) {
		this.perDiem = perDiem;
	}
	
	public String getPerDiemPer() {
		return perDiemPer;
	}
	
	public void setPerDiemPer(String perDiemPer) {
		this.perDiemPer = perDiemPer;
	}
	
	public Integer getRecordID() {
		return recordID;
	}
	
	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public String getSalaryPer() {
		return salaryPer;
	}
	
	public void setSalaryPer(String salaryPer) {
		this.salaryPer = salaryPer;
	}
	
	public Integer getSalaryPerCurrency() {
		return salaryPerCurrency;
	}
	
	public void setSalaryPerCurrency(Integer salaryPerCurrency) {
		this.salaryPerCurrency = salaryPerCurrency;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getSubcontractCompanyID() {
		return subcontractCompanyID;
	}
	
	public void setSubcontractCompanyID(Long subcontractCompanyID) {
		this.subcontractCompanyID = subcontractCompanyID;
	}
	
	public String getTaxID() {
		return taxID;
	}
	
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	
	public Userfield[] getUserfields() {
		return userfields;
	}
	
	public void setUserfields(Userfield[] userfields) {
		this.userfields = userfields;
	}
}
