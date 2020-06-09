package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;

@ApiModel()
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "PhoneType", "ext", "type", "action" })
@SuppressWarnings("serial")
public class PhoneType implements java.io.Serializable {
	
	private String	phone;
	private String	ext;
	private String	type;
	private Integer	action;
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getExt() {
		return ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getAction() {
		return action;
	}
	
	public void setAction(int action) {
		this.action = action;
	}
}
