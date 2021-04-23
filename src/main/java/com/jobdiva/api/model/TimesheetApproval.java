package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class TimesheetApproval implements java.io.Serializable  {

	
	 private Long    employeeid;
     private Date    weekendingDate;
     private Long    billrecordid;
     private String  employeeName;
     private Integer approved;
     private Date    approvedOn;
     private String  remark;
     private String  billrateper;
     private Integer entryformat;
     private Long    approvedBy;
     private String  comments;
     private String  workingstate;
     
     
    
	public Long getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	public Date getWeekendingDate() {
		return weekendingDate;
	}
	public void setWeekendingDate(Date weekendingDate) {
		this.weekendingDate = weekendingDate;
	}
	public Long getBillrecordid() {
		return billrecordid;
	}
	public void setBillrecordid(Long billrecordid) {
		this.billrecordid = billrecordid;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getApproved() {
		return approved;
	}
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBillrateper() {
		return billrateper;
	}
	public void setBillrateper(String billrateper) {
		this.billrateper = billrateper;
	}
	public Integer getEntryformat() {
		return entryformat;
	}
	public void setEntryformat(Integer entryformat) {
		this.entryformat = entryformat;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getWorkingstate() {
		return workingstate;
	}
	public void setWorkingstate(String workingstate) {
		this.workingstate = workingstate;
	}
     
   
    
	
	
}
