package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class TimesheetEntry implements java.io.Serializable {
	
	private Date	date;
	private Double	hours;
	private Date	timeIn;
	private Date	lunchIn;
	private Date	lunchOut;
	private Date	timeOut;
	
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
	
	public Date getTimeIn() {
		return timeIn;
	}
	
	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}
	
	public Date getLunchIn() {
		return lunchIn;
	}
	
	public void setLunchIn(Date lunchIn) {
		this.lunchIn = lunchIn;
	}
	
	public Date getLunchOut() {
		return lunchOut;
	}
	
	public void setLunchOut(Date lunchOut) {
		this.lunchOut = lunchOut;
	}
	
	public Date getTimeOut() {
		return timeOut;
	}
	
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}
}
