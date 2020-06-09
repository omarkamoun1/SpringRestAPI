package com.jobdiva.api.model.bidata;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Record extends LinkedHashMap<String, String> {
	
	public void addValue(String columnName, String columnValue) {
		this.put(columnName, columnValue);
	}
}
