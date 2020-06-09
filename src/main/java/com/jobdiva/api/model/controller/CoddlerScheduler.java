package com.jobdiva.api.model.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class CoddlerScheduler implements Serializable {
	
	private Integer			id;
	//
	@JsonFormat(pattern = "HH:mm")
	private Timestamp		startTime;
	//
	@JsonFormat(pattern = "HH:mm")
	private Timestamp		endTime;
	//
	private String			strStartTime;
	//
	private String			strEndTime;
	//
	private Integer			frequency;
	//
	private Integer			modeTypeId;
	//
	private List<Parameter>	parameters;
	//
	private List<FixedTime>	fixedTimes;
	//
	private String			companyId;
	private Long			teamId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public void setStartTime(Timestamp startTime, SimpleDateFormat dateFormat) {
		this.startTime = startTime;
		//
		if (this.startTime != null)
			this.strStartTime = dateFormat.format(startTime);
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	public void setEndTime(Timestamp endTime, SimpleDateFormat dateFormat) {
		this.endTime = endTime;
		//
		if (this.endTime != null)
			this.strEndTime = dateFormat.format(endTime);
	}
	
	public String getStrStartTime() {
		return strStartTime;
	}
	
	public void setStrStartTime(String strStartTime) {
		this.strStartTime = strStartTime;
	}
	
	public String getStrEndTime() {
		return strEndTime;
	}
	
	public void setStrEndTime(String strEndTime) {
		this.strEndTime = strEndTime;
	}
	
	public Integer getFrequency() {
		return frequency;
	}
	
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public Integer getModeTypeId() {
		return modeTypeId;
	}
	
	public void setModeTypeId(Integer modeTypeId) {
		this.modeTypeId = modeTypeId;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public List<FixedTime> getFixedTimes() {
		return fixedTimes;
	}
	
	public void setFixedTimes(List<FixedTime> fixedTimes) {
		this.fixedTimes = fixedTimes;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
}
