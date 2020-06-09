package com.jobdiva.api.model;

import io.swagger.annotations.ApiModel;

@SuppressWarnings("serial")
@ApiModel()
public class Userfield implements java.io.Serializable {
	
	private Integer	userfieldId;
	//
	private String	userfieldValue;
	
	public Integer getUserfieldId() {
		return userfieldId;
	}
	
	public void setUserfieldId(Integer userfieldId) {
		this.userfieldId = userfieldId;
	}
	
	public String getUserfieldValue() {
		return userfieldValue;
	}
	
	public void setUserfieldValue(String userfieldValue) {
		this.userfieldValue = userfieldValue;
	}
}