package com.jobdiva.api.model;

import java.util.Calendar;

@SuppressWarnings("serial")
public class WeekEndingRecord implements java.io.Serializable{

	
	private Integer 	approved;
	private Long    	approvedBy;
	private Calendar    approvedOn;
	private Long 		candidateid;
	private String  	comments;
	private String  	workingstate;
	private String  	billrateunit;
	private String  	remark;
	private Integer     entryformat;
	private Long    	billrecordid;
	private Calendar    weekendingDate;
	
	
	public Calendar getWeekendingDate() {
		return weekendingDate;
	}
	public void setWeekendingDate(Calendar weekendingDate) {
		this.weekendingDate = weekendingDate;
	}
	public String getBillrateunit() {
		return billrateunit;
	}
	public void setBillrateunit(String billrateunit) {
		this.billrateunit = billrateunit;
	}
	public Integer getEntryformat() {
		return entryformat;
	}
	public void setEntryformat(Integer entryformat) {
		this.entryformat = entryformat;
	}
	public Long getBillrecordid() {
		return billrecordid;
	}
	public void setBillrecordid(Long billrecordid) {
		this.billrecordid = billrecordid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkingstate() {
		return workingstate;
	}
	public void setWorkingstate(String workingstate) {
		this.workingstate = workingstate;
	}
	public Long getCandidateid() {
		return candidateid;
	}
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Calendar getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Calendar approvedOn) {
		this.approvedOn = approvedOn;
	}
	public Integer getApproved() {
		return approved;
	}
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	
}
