package com.jobdiva.api.model.bidata;

import java.io.Serializable;
import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Record extends LinkedHashMap<String, String> implements Serializable {
	
	public void addValue(String columnName, String columnValue) {
		this.put(columnName, columnValue);
	}
}
