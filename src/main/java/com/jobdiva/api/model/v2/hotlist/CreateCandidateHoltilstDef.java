package com.jobdiva.api.model.v2.hotlist;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class CreateCandidateHoltilstDef implements Serializable {
	
	@JsonProperty(value = "name", required = true)
	private String		name;
	//
	@JsonProperty(value = "linkToJobId", required = false)
	private Long		linkToJobId;
	//
	@JsonProperty(value = "linkToHiringManagerId", required = false)
	private Long		linkToHiringManagerId;
	//
	@JsonProperty(value = "description", required = true)
	private String		description;
	//
	@JsonProperty(value = "userIds", required = false)
	private List<Long>	userIds;
	//
	@JsonProperty(value = "groupIds", required = false)
	private List<Long>	groupIds;
	//
	@JsonProperty(value = "divisionIds", required = false)
	private List<Long>	divisionIds;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getLinkToJobId() {
		return linkToJobId;
	}
	
	public void setLinkToJobId(Long linkToJobId) {
		this.linkToJobId = linkToJobId;
	}
	
	public Long getLinkToHiringManagerId() {
		return linkToHiringManagerId;
	}
	
	public void setLinkToHiringManagerId(Long linkToHiringManagerId) {
		this.linkToHiringManagerId = linkToHiringManagerId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Long> getUserIds() {
		return userIds;
	}
	
	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
	
	public List<Long> getGroupIds() {
		return groupIds;
	}
	
	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}
	
	public List<Long> getDivisionIds() {
		return divisionIds;
	}
	
	public void setDivisionIds(List<Long> divisionIds) {
		this.divisionIds = divisionIds;
	}
}
