package com.jobdiva.api.model;

import java.util.Date;

@SuppressWarnings("serial")
public class TitleSkillCertification implements java.io.Serializable {
	
	private String	titleskillcertification;
	private Date	startDate;
	private Date	endDate;
	private Integer	years;
	
	public String getTitleskillcertification() {
		return titleskillcertification;
	}
	
	public void setTitleskillcertification(String titleskillcertification) {
		this.titleskillcertification = titleskillcertification;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getYears() {
		return years;
	}
	
	public void setYears(Integer years) {
		this.years = years;
	}
}
