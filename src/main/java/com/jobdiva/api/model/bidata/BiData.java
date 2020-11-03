package com.jobdiva.api.model.bidata;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class BiData implements IBiData, Serializable {
	
	@ApiModelProperty(notes = "Message")
	private String				message;
	//
	@ApiModelProperty(notes = "DataSet")
	private Vector<String[]>	data;
	//
	//
	@ApiModelProperty(notes = "DataSet")
	private DataSet				dataSet	= new DataSet();
	
	public BiData() {
		super();
	}
	
	public BiData(String message) {
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
	
	public Vector<String[]> getData() {
		return data;
	}
	
	public void setData(Vector<String[]> data) {
		this.data = data;
	}
	
	public void assignColumnNames(List<String> columnNames) {
		this.dataSet.setColumnNames(columnNames);
	}
	
	public Record addRecord() {
		return this.dataSet.addRecord();
	}
}
