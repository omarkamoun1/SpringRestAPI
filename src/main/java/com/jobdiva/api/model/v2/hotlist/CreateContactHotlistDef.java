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
public class CreateContactHotlistDef implements Serializable {
	
	@JsonProperty(value = "name", required = true)
	private String		name;
	//
	@JsonProperty(value = "active", required = false)
	private Boolean		active;
	//
	@JsonProperty(value = "isPrivate", required = false)
	private Boolean		isPrivate;
	//
	@JsonProperty(value = "description", required = false)
	private String		description;
	//
	@JsonProperty(value = "sharedWithIds", required = false)
	private List<Long>	sharedWithIds;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Long> getSharedWithIds() {
		return sharedWithIds;
	}
	
	public void setSharedWithIds(List<Long> sharedWithIds) {
		this.sharedWithIds = sharedWithIds;
	}
}
