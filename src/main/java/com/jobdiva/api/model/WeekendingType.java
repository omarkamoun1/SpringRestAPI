package com.jobdiva.api.model;

public enum WeekendingType {
	RESET("RESET"), //
	SATURDAY("SATURDAY"), //
	SUNDAY("SUNDAY"), //
	MONDAY("MONDAY"), //
	TUESDAY("TUESDAY"), //
	WEDNESDAY("WEDNESDAY"), //
	THURSDAY("THURSDAY"), //
	FRIDAY("FRIDAY"), //
	;
	
	private String value;
	
	WeekendingType(String value) {
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
