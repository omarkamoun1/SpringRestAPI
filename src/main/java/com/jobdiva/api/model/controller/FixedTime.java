package com.jobdiva.api.model.controller;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class FixedTime implements Serializable {
	
	//
	@JsonFormat(pattern = "HH:mm")
	private Timestamp	value;
	//
	private String		strValue;
	
	public Timestamp getValue() {
		return value;
	}
	
	public void setValue(Timestamp value) {
		this.value = value;
	}
	
	public String getStrValue() {
		return strValue;
	}
	
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
}
