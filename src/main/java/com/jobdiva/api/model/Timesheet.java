package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class Timesheet implements java.io.Serializable {
	
	private Date	date;
	private Double	hours;
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getHours() {
		return hours;
	}
	
	public void setHours(Double hours) {
		this.hours = hours;
	}
}
