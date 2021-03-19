package com.jobdiva.api.model;

public enum Timezone {
	
	HAWAII("(GMT-10:00) Hawaii", "Etc/GMT+10"), //
	ALASKA("(GMT-09:00) Alaska", "America/Anchorage"), //
	TIJUANA_BAJA_CALIFORNIA("(GMT-08:00) Tijuana, Baja California", "America/Ensenada"), //
	PACIFIC_TIME_US_CANADA("(GMT-08:00) Pacific Time (US & Canada)", "America/Los_Angeles"), //
	MOUNTAIN_TIME_US_CANADA("(GMT-07:00) Mountain Time (US & Canada)", "America/Denver"), //
	ARIZONA("(GMT-07:00) Arizona", "America/Dawson_Creek"), //
	CENTRAL_TIME_US_CANADA("(GMT-06:00) Central Time (US & Canada)", "America/Chicago"), //
	EASTERN_TIME_US_CANADA("(GMT-05:00) Eastern Time (US & Canada)", "America/New_York"), //
	ATLANTIC_STANDARD_TIME_PUERTO_RICO("(GMT-04:00) Atlantic Standard Time (Puerto Rico)", "America/Puerto_Rico"), //
	BRAZIL("(GMT-04:00) Brazil", "America/Campo_Grande"), //
	GREENWICH_MEAN_TIME_LONDON("(GMT+00:00) Greenwich Mean Time : London", "Europe/London"), //
	DUBLIN_EDINBURGH_LISBON_LONDON("(GMT+00:00) Dublin, Edinburgh, Lisbon, London", "Europe/London"), //
	BRUSSELS_COPENHAGEN_MADRID_PARIS("(GMT+01:00) Brussels, Copenhagen, Madrid, Paris", "Europe/Brussels"), //
	BEIRUT("(GMT+02:00) Beirut", "Asia/Beirut"), //
	MOSCOW_ST_PETERSBURG_VOLGOGRAD("(GMT+03:00) Moscow, St. Petersburg, Volgograd", "Europe/Moscow"), //
	ABU_DHABI_MUSCAT("(GMT+04:00) Abu Dhabi, Muscat", "Asia/Dubai"), //
	ISLAMABAD_KARACHI("(GMT+05:00) Islamabad, Karachi", "Asia/Karachi"), //
	CHENNAI_KOLKATA_MUMBAI_NEW_DELHI("(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi", "IST"), //
	ASTANA_DHAKA("(GMT+06:00) Astana, Dhaka", "Asia/Dhaka"), //
	YANGON_RANGOON("(GMT+06:30) Yangon (Rangoon)", "Asia/Rangoon"), //
	BANGKOK_HANOI_JAKARTA("(GMT+07:00) Bangkok, Hanoi, Jakarta", "Asia/Bangkok"), //
	BEIJING_CHONGQING_HONG_KONG_TAIWAN("(GMT+08:00) Beijing, Chongqing, Hong Kong, Taiwan", "Asia/Hong_Kong"), //
	PERTH("(GMT+08:00) Perth", "Australia/Perth"), //
	EUCLA("(GMT+08:45) Eucla", "Australia/Eucla"), //
	OSAKA_SAPPORO_TOKYO_SEOUL("(GMT+09:00) Osaka, Sapporo, Tokyo, Seoul", "Asia/Tokyo"), //
	ADELAIDE("(GMT+09:30) Adelaide", "Australia/Adelaide"), //
	HOBART("(GMT+10:00) Hobart", "Australia/Hobart");
	
	private String	value;
	private String	name;
	
	Timezone(String name, String value) {
		this.value = value;
		this.name = name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
