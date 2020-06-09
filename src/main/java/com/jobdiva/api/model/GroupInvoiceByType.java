package com.jobdiva.api.model;

public enum GroupInvoiceByType {
	RESET("RESET"), //
	JOB("JOB"), //
	COMPANY("COMPANY"), //
	PO_NUMBER("PO_NUMBER");
	
	private String value;
	
	GroupInvoiceByType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
