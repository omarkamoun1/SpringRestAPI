package com.jobdiva.api.model;

public enum BillingUnitType {
	RESET("RESET"), //
	HOURLY("HOURLY"), //
	DAILY_BASED_ON_HOURS("DAILY_BASED_ON_HOURS"), //
	DAILY_HALF_DAY("DAILY_HALF_DAY"), //
	DAILY_HALF_DAY_OT("DAILY_HALF_DAY_OT"), //
	DAILY_BILL_WHOLE_DAY("DAILY_BILL_WHOLE_DAY"), //
	DAILY_BILL_WHOLE_DAY_OT("DAILY_BILL_WHOLE_DAY_OT"), //
	;
	
	private String value;
	
	BillingUnitType(String value) {
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
