package com.jobdiva.api.model;

/**
 * @author Joseph Chidiac
 *
 */
public enum EventNotification {
	
	NONE(0, ""), //
	FIVE_MINS(5, "5 mins"), //
	TEN_MINS(10, "10 mins"), //
	FIFTEEN_MINS(15, "15 mins"), //
	THIRTY_MINS(30, "30 mins"), //
	ONE_HOUR(60, "1 hour"), //
	TOW_HOURS(120, "2 hours"), //
	FOUR_HOURS(240, "4 hours"), //
	EIGHT_HOURS(480, "8 hours"), //
	TWELVE_HOURS(720, "0.5 day"), //
	ONE_DAY(1440, "1 day"), //
	TOW_DAYS(2880, "2 days"), //
	ONE_WEEK(10080, "1 week");
	
	private String	name;
	private Integer	value;
	
	EventNotification(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
