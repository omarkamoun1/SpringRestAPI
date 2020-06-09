package com.jobdiva.api.model;

public enum ShowOnInvoiceType {
	RESET("RESET"), //
	ADDRESS("ADDRESS"), //
	EMAIL("EMAIL"), //
	DO_NOT_SEND("DO_NOT_SEND");
	
	private String value;
	
	ShowOnInvoiceType(String value) {
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
