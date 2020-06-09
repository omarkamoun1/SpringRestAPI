package com.jobdiva.api.model.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Parameter implements Serializable {
	
	private String paramName;
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
