package com.jobdiva.api.model.onboard;

import java.io.Serializable;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class HireType implements Serializable {
	
	private Long	id;
	private String	name;
	private String	packageType;
	
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
	
	public String getPackageType() {
		return packageType;
	}
	
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
}