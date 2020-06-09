package com.jobdiva.api.model;

public enum Timezone {
	HAWAII("(GMT-10:00) Hawaii"), //
	ALASKA("(GMT-09:00) Alaska"), //
	TIJUANA_BAJA_CALIFORNIA("(GMT-08:00) Tijuana, Baja California"), //
	PACIFIC_TIME_US_CANADA("(GMT-08:00) Pacific Time (US & Canada)"), //
	MOUNTAIN_TIME_US_CANADA("(GMT-07:00) Mountain Time (US & Canada)"), //
	ARIZONA("(GMT-07:00) Arizona"), //
	CENTRAL_TIME_US_CANADA("(GMT-06:00) Central Time (US & Canada)"), //
	EASTERN_TIME_US_CANADA("(GMT-05:00) Eastern Time (US & Canada)"), //
	ATLANTIC_STANDARD_TIME_PUERTO_RICO("(GMT-04:00) Atlantic Standard Time (Puerto Rico)"), //
	BRAZIL("(GMT-04:00) Brazil"), //
	GREENWICH_MEAN_TIME_LONDON("(GMT+00:00) Greenwich Mean Time : London"), //
	DUBLIN_EDINBURGH_LISBON_LONDON("(GMT+00:00) Dublin, Edinburgh, Lisbon, London"), //
	BRUSSELS_COPENHAGEN_MADRID_PARIS("(GMT+01:00) Brussels, Copenhagen, Madrid, Paris"), //
	BEIRUT("(GMT+02:00) Beirut"), //
	MOSCOW_ST_PETERSBURG_VOLGOGRAD("(GMT+03:00) Moscow, St. Petersburg, Volgograd"), //
	ABU_DHABI_MUSCAT("(GMT+04:00) Abu Dhabi, Muscat"), //
	ISLAMABAD_KARACHI("(GMT+05:00) Islamabad, Karachi"), //
	CHENNAI_KOLKATA_MUMBAI_NEW_DELHI("(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi"), //
	ASTANA_DHAKA("(GMT+06:00) Astana, Dhaka"), //
	YANGON_RANGOON("(GMT+06:30) Yangon (Rangoon)"), //
	BANGKOK_HANOI_JAKARTA("(GMT+07:00) Bangkok, Hanoi, Jakarta"), //
	BEIJING_CHONGQING_HONG_KONG_TAIWAN("(GMT+08:00) Beijing, Chongqing, Hong Kong, Taiwan"), //
	PERTH("(GMT+08:00) Perth"), //
	EUCLA("(GMT+08:45) Eucla"), //
	OSAKA_SAPPORO_TOKYO_SEOUL("(GMT+09:00) Osaka, Sapporo, Tokyo, Seoul"), //
	ADELAIDE("(GMT+09:30) Adelaide"), //
	HOBART("(GMT+10:00) Hobart");
	
	private String value;
	
	Timezone(String value) {
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
