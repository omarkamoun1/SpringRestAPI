package com.jobdiva.api.model.onboard;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class HireType implements Serializable {
	
	private Long	id;
	private String	name;
	private String	description;
	private String	packageType;
	private Date	lastUpdatedOn;
	private Long	lastUpdatedBy;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPackageType() {
		return packageType;
	}
	
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
