package com.jobdiva.api.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
@ApiModel()
public class PaRecord implements java.io.Serializable {
	
	private Long	candidateID;
	private Long	assignmentID;
	private Long	jobID;
	private Integer	recordID;
	private Boolean	approved;
	private Long	createdByID;
	private Date	effectiveDate;
	private Date	endDate;
	private Integer	status;
	private String	TaxID;
	private String	paymentTerms;
	private Long	subcontractCompanyID;
	private Boolean	payOnRemittance;
	private Double	salary;
	private String	salaryPer;
	private Double	perDiem;
	private String	perDiemPer;
	private Double	otherExpenses;
	private String	otherExpensesPer;
	private Double	outsideCommission;
	private String	outsideCommissionPer;
	private Double	overtimeRate;
	private String	overtimeRatePer;
	private Double	doubletimeRate;
	private String	doubletimeRatePer;
	private Boolean	overtimeExempt;
	private String	fileNo;
	private String	aDPCOCODE;
	private String	aDPPAYFREQUENCY;
	
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
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public Long getCreatedByID() {
		return createdByID;
	}
	
	public void setCreatedByID(Long createdByID) {
		this.createdByID = createdByID;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getTaxID() {
		return TaxID;
	}
	
	public void setTaxID(String taxID) {
		TaxID = taxID;
	}
	
	public String getPaymentTerms() {
		return paymentTerms;
	}
	
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	public Long getSubcontractCompanyID() {
		return subcontractCompanyID;
	}
	
	public void setSubcontractCompanyID(Long subcontractCompanyID) {
		this.subcontractCompanyID = subcontractCompanyID;
	}
	
	public Boolean getPayOnRemittance() {
		return payOnRemittance;
	}
	
	public void setPayOnRemittance(Boolean payOnRemittance) {
		this.payOnRemittance = payOnRemittance;
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
	
	public String getDoubletimeRatePer() {
		return doubletimeRatePer;
	}
	
	public void setDoubletimeRatePer(String doubletimeRatePer) {
		this.doubletimeRatePer = doubletimeRatePer;
	}
	
	public Boolean getOvertimeExempt() {
		return overtimeExempt;
	}
	
	public void setOvertimeExempt(Boolean overtimeExempt) {
		this.overtimeExempt = overtimeExempt;
	}
	
	public String getFileNo() {
		return fileNo;
	}
	
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	
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
}
