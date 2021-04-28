package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Timesheet;

/**
 * @author Joseph Chidiac
 *
 *         Apr 15, 2021
 */
@SuppressWarnings("serial")
public class UploadTimesheetDef implements Serializable {
	
	@JsonProperty(value = "employeeid", required = true) //
	private Long		employeeid;
	//
	@JsonProperty(value = "jobid", required = false) //
	private Long		jobid;
	//
	@JsonProperty(value = "weekendingdate", required = true) //
	private Date		weekendingdate;
	//
	@JsonProperty(value = "approved", required = true) //
	private Boolean		approved;
	//
	@JsonProperty(value = "timesheetId", required = true) //
	private Long		timesheetId;
	//
	@JsonProperty(value = "externalId", required = true) //
	private String		externalId;
	//
	@JsonProperty(value = "timesheet") //
	private Timesheet[]	timesheet;
	
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
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public Long getTimesheetId() {
		return timesheetId;
	}
	
	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public Timesheet[] getTimesheet() {
		return timesheet;
	}
	
	public void setTimesheet(Timesheet[] timesheet) {
		this.timesheet = timesheet;
	}
}
