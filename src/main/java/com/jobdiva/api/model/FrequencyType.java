package com.jobdiva.api.model;

public enum FrequencyType {
	RESET("RESET"), //
	BI_WEEKLY("BI_WEEKLY"), //
	MONTHLY("MONTHLY"), //
	SEMI_MONTHLY("SEMI_MONTHLY"), //
	WEEKLY("WEEKLY"), //
	WHOLE_PROJECT("WHOLE_PROJECT"), //
	MONTHLY_ENDING_WEEKEND("MONTHLY_ENDING_WEEKEND") //
	;
	
	private String value;
	
	FrequencyType(String value) {
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
