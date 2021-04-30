package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.ExpenseEntry;
import com.jobdiva.api.model.TimesheetEntry;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class UploadTimesheetAssignmentDef implements Serializable {
	
	@JsonProperty(value = "employeeid", required = true)
	private Long				employeeid;
	//
	@JsonProperty(value = "jobid", required = false)
	private Long				jobid;
	//
	@JsonProperty(value = "weekendingdate", required = true)
	private Date				weekendingdate;
	//
	@JsonProperty(value = "payrate", required = true)
	private Double				payrate;
	//
	@JsonProperty(value = "overtimepayrate", required = false)
	private Double				overtimepayrate;
	//
	@JsonProperty(value = "doubletimepayrate", required = false)
	private Double				doubletimepayrate;
	//
	@JsonProperty(value = "billrate", required = true)
	private Double				billrate;
	//
	@JsonProperty(value = "overtimebillrate", required = false)
	private Double				overtimebillrate;
	//
	@JsonProperty(value = "doubletimebillrate", required = false)
	private Double				doubletimebillrate;
	//
	@JsonProperty(value = "location", required = false)
	private String				location;
	//
	@JsonProperty(value = "title", required = true)
	private String				title;
	//
	@JsonProperty(value = "rolenumber", required = false)
	private String				rolenumber;
	//
	@JsonProperty(value = "timesheetid", required = false)
	private Long				timesheetid;
	//
	@JsonProperty(value = "externalid", required = false)
	private String				externalid;
	//
	@JsonProperty(value = "compcode", required = false)
	private String				compcode;
	//
	@JsonProperty(value = "timesheetEntry", required = true)
	private TimesheetEntry[]	timesheetEntry;
	//
	@JsonProperty(value = "expenses", required = false)
	private ExpenseEntry[]		expenses;
	//
	@JsonProperty(value = "emailrecipients", required = false)
	private String[]			emailrecipients;
	
	public Long getEmployeeid() {
		return employeeid;
	}
	
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	
	public Long getJobid() {
		return jobid;
	}
	
	public void setJobid(Long jobid) {
		this.jobid = jobid;
	}
	
	public Date getWeekendingdate() {
		return weekendingdate;
	}
	
	public void setWeekendingdate(Date weekendingdate) {
		this.weekendingdate = weekendingdate;
	}
	
	public Double getPayrate() {
		return payrate;
	}
	
	public void setPayrate(Double payrate) {
		this.payrate = payrate;
	}
	
	public Double getOvertimepayrate() {
		return overtimepayrate;
	}
	
	public void setOvertimepayrate(Double overtimepayrate) {
		this.overtimepayrate = overtimepayrate;
	}
	
	public Double getDoubletimepayrate() {
		return doubletimepayrate;
	}
	
	public void setDoubletimepayrate(Double doubletimepayrate) {
		this.doubletimepayrate = doubletimepayrate;
	}
	
	public Double getBillrate() {
		return billrate;
	}
	
	public void setBillrate(Double billrate) {
		this.billrate = billrate;
	}
	
	public Double getOvertimebillrate() {
		return overtimebillrate;
	}
	
	public void setOvertimebillrate(Double overtimebillrate) {
		this.overtimebillrate = overtimebillrate;
	}
	
	public Double getDoubletimebillrate() {
		return doubletimebillrate;
	}
	
	public void setDoubletimebillrate(Double doubletimebillrate) {
		this.doubletimebillrate = doubletimebillrate;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRolenumber() {
		return rolenumber;
	}
	
	public void setRolenumber(String rolenumber) {
		this.rolenumber = rolenumber;
	}
	
	public Long getTimesheetid() {
		return timesheetid;
	}
	
	public void setTimesheetid(Long timesheetid) {
		this.timesheetid = timesheetid;
	}
	
	public String getExternalid() {
		return externalid;
	}
	
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	
	public String getCompcode() {
		return compcode;
	}
	
	public void setCompcode(String compcode) {
		this.compcode = compcode;
	}
	
	public TimesheetEntry[] getTimesheetEntry() {
		return timesheetEntry;
	}
	
	public void setTimesheetEntry(TimesheetEntry[] timesheetEntry) {
		this.timesheetEntry = timesheetEntry;
	}
	
	public ExpenseEntry[] getExpenses() {
		return expenses;
	}
	
	public void setExpenses(ExpenseEntry[] expenses) {
		this.expenses = expenses;
	}
	
	public String[] getEmailrecipients() {
		return emailrecipients;
	}
	
	public void setEmailrecipients(String[] emailrecipients) {
		this.emailrecipients = emailrecipients;
	}
}
