package com.jobdiva.api.model.bidata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class NewBiData implements IBiData, Serializable {
	
	@ApiModelProperty(notes = "Message")
	private String			message;
	//
	@ApiModelProperty(notes = "data")
	private List<Record>	data	= new ArrayList<Record>();
	
	public NewBiData() {
		super();
	}
	
	public NewBiData(String message) {
		this();
		//
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Record> getData() {
		return data;
	}
	
	public void setData(List<Record> data) {
		this.data = data;
	}
	
	public Record addRecord() {
		Record record = new Record();
		this.data.add(record);
		return record;
	}
}
