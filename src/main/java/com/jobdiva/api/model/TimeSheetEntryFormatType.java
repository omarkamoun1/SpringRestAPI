package com.jobdiva.api.model;

public enum TimeSheetEntryFormatType {
	RESET("RESET"), //
	MINUTE("MINUTE"), //
	QUARTER("QUARTER"), //
	TIME_IN_TIME_OUT("TIME_IN_TIME_OUT"), //
	DEFAULT_WHEN_BILLING_UNIT_IS_DAILY("DEFAULT_WHEN_BILLING_UNIT_IS_DAILY") //
	;
	
	private String value;
	
	TimeSheetEntryFormatType(String value) {
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
