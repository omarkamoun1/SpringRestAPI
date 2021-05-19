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
	
	@JsonProperty(value = "employeeId", required = true) //
	private Long		employeeId;
	//
	@JsonProperty(value = "vmsEmployeeId", required = false) //
	private String		vmsEmployeeId;
	//
	@JsonProperty(value = "jobId", required = false) //
	private Long		jobId;
	//
	@JsonProperty(value = "weekendingdate", required = true) //
	private Date		weekendingdate;
	//
	@JsonProperty(value = "approved", required = true) //
	private Boolean		approved;
	//
	// @JsonProperty(value = "timesheetId", required = true) //
	// private Long timesheetId;
	//
	@JsonProperty(value = "externalId", required = false) //
	private String		externalId;
	//
	@JsonProperty(value = "activityId", required = false) //
	private Long		activityId;
	//
	@JsonProperty(value = "approverId", required = false) //
	private Long		approverId;
	//
	@JsonProperty(value = "timesheet") //
	private Timesheet[]	timesheet;
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getVmsEmployeeId() {
		return vmsEmployeeId;
	}
	
	public void setVmsEmployeeId(String vmsEmployeeId) {
		this.vmsEmployeeId = vmsEmployeeId;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
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
	// public Long getTimesheetId() {
	// return timesheetId;
	// }
	//
	// public void setTimesheetId(Long timesheetId) {
	// this.timesheetId = timesheetId;
	// }
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public Long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	public Long getApproverId() {
		return approverId;
	}
	
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	
	public Timesheet[] getTimesheet() {
		return timesheet;
	}
	
	public void setTimesheet(Timesheet[] timesheet) {
		this.timesheet = timesheet;
	}
}
