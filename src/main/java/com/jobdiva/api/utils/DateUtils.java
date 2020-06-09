package com.jobdiva.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	
	public static Date strToDate(String formatDate, String strDate) throws Exception {
		//
		if (strDate != null && !strDate.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
			//
			return sdf.parse(strDate);
			//
		}
		//
		return null;
	}
	
	public static String formatDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date trimTime(Date datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static int compareDateIgnoringTime(Date t1, Date t2) throws Exception {
		if (t1 == null && t2 == null)
			throw new Exception("Either date, (" + t1 + "; " + t2 + ") is empty. ");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(t1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(t2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		int comp = c1.compareTo(c2);
		return comp;
	}
	
	public static Date getEndTimeSameDaySpecial(java.util.Date date, String tzStr) {
		if (tzStr == null || tzStr.length() == 0)
			tzStr = "America/New_York";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone(tzStr));
		Calendar cal = java.util.Calendar.getInstance(TimeZone.getTimeZone(tzStr));
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
