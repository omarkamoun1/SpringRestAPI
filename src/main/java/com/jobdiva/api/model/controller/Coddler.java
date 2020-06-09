package com.jobdiva.api.model.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Coddler implements Serializable {
	
	private Integer					machineId;
	private Long					teamId;
	private Integer					id;
	private Integer					coddlerTypeId;
	private String					name;
	private String					executableFile;
	private Boolean					active				= false;
	private Integer					createdById;
	private Date					creationDate;
	private Integer					updatedById;
	private Date					updateDate;
	private String					timezone;
	//
	private List<Parameter>			globalParameters;
	//
	private List<CoddlerScheduler>	schedulers;
	//
	private Boolean					enableCleanLogs		= true;
	private Integer					logExpDays			= 7;
	private Boolean					enableCleanImages	= true;
	private Integer					imageExpDays		= 7;
	
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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCoddlerTypeId() {
		return coddlerTypeId;
	}
	
	public void setCoddlerTypeId(Integer coddlerTypeId) {
		this.coddlerTypeId = coddlerTypeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExecutableFile() {
		return executableFile;
	}
	
	public void setExecutableFile(String executableFile) {
		this.executableFile = executableFile;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Integer getCreatedById() {
		return createdById;
	}
	
	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Integer getUpdatedById() {
		return updatedById;
	}
	
	public void setUpdatedById(Integer updatedById) {
		this.updatedById = updatedById;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public List<Parameter> getGlobalParameters() {
		return globalParameters;
	}
	
	public void setGlobalParameters(List<Parameter> globalParameters) {
		this.globalParameters = globalParameters;
	}
	
	public List<CoddlerScheduler> getSchedulers() {
		return schedulers;
	}
	
	public void setSchedulers(List<CoddlerScheduler> schedulers) {
		this.schedulers = schedulers;
	}
	
	public String getTimezone() {
		return timezone;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public Boolean getEnableCleanLogs() {
		return enableCleanLogs;
	}
	
	public void setEnableCleanLogs(Boolean enableCleanLogs) {
		this.enableCleanLogs = enableCleanLogs;
	}
	
	public Integer getLogExpDays() {
		return logExpDays;
	}
	
	public void setLogExpDays(Integer logExpDays) {
		this.logExpDays = logExpDays;
	}
	
	public Boolean getEnableCleanImages() {
		return enableCleanImages;
	}
	
	public void setEnableCleanImages(Boolean enableCleanImages) {
		this.enableCleanImages = enableCleanImages;
	}
	
	public Integer getImageExpDays() {
		return imageExpDays;
	}
	
	public void setImageExpDays(Integer imageExpDays) {
		this.imageExpDays = imageExpDays;
	}
}
