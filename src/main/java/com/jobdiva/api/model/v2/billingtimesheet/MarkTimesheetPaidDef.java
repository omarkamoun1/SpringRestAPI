package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class MarkTimesheetPaidDef implements Serializable {
	
	//
	@JsonProperty(value = "employeeid", required = true)
	private Long	employeeid;
	//
	@JsonProperty(value = "salaryrecordid", required = true)
	private Integer	salaryrecordid;
	//
	@JsonProperty(value = "datepaid", required = true)
	private Date	datepaid;
	//
	@JsonProperty(value = "timesheetdate", required = true)
	private Date[]	timesheetdate;
	
	public Long getEmployeeid() {
		return employeeid;
	}
	
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	
	public Integer getSalaryrecordid() {
		return salaryrecordid;
	}
	
	public void setSalaryrecordid(Integer salaryrecordid) {
		this.salaryrecordid = salaryrecordid;
	}
	
	public Date getDatepaid() {
		return datepaid;
	}
	
	public void setDatepaid(Date datepaid) {
		this.datepaid = datepaid;
	}
	
	public Date[] getTimesheetdate() {
		return timesheetdate;
	}
	
	public void setTimesheetdate(Date[] timesheetdate) {
		this.timesheetdate = timesheetdate;
	}
}
