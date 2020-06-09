package com.jobdiva.api.model.bidata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DataSet implements Serializable {
	
	private List<String>	columnNames	= new ArrayList<String>();
	private List<Record>	records		= new ArrayList<Record>();
	
	public List<String> getColumnNames() {
		return columnNames;
	}
	
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	
	public List<Record> getRecords() {
		return records;
	}
	
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	public Record addRecord() {
		Record record = new Record();
		this.records.add(record);
		return record;
	}
}
