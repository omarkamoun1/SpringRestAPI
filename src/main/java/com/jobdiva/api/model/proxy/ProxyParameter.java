package com.jobdiva.api.model.proxy;

import io.swagger.annotations.ApiModel;

@SuppressWarnings("serial")
@ApiModel()
public class ProxyParameter implements java.io.Serializable {
	
	private String	name;
	private String	value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
