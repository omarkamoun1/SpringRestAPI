package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class Experience implements java.io.Serializable {
	
	private String	title;
	private String	fromDate;
	private String	toDate;
	private Integer	years;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	public String getToDate() {
		return toDate;
	}
	
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public Integer getYears() {
		return years;
	}
	
	public void setYears(Integer years) {
		this.years = years;
	}
}
