package com.jobdiva.api.model.controller;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class Configuration implements Serializable {
	
	private Integer		machineId;
	//
	private Long		teamId;
	//
	private Integer		day;
	//
	@JsonFormat(pattern = "HH:mm")
	private Timestamp	restartMachine;
	//
	private String		strRestartMachine;
	
	public Integer getMachineId() {
		return machineId;
	}
	
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
	public Timestamp getRestartMachine() {
		return restartMachine;
	}
	
	public void setRestartMachine(Timestamp restartMachine) {
		this.restartMachine = restartMachine;
	}
	
	public String getStrRestartMachine() {
		return strRestartMachine;
	}
	
	public void setStrRestartMachine(String strRestartMachine) {
		this.strRestartMachine = strRestartMachine;
	}
}
