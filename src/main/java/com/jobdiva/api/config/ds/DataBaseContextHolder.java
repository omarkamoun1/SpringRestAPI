package com.jobdiva.api.config.ds;

import lombok.experimental.UtilityClass;

/**
 * @author Joseph Chidiac
 *
 */
@UtilityClass
public class DataBaseContextHolder {
	
	private static final ThreadLocal<String> dbContextHolder = new ThreadLocal<>();
	
	public static void setCurrentDb(String dbType) {
		dbContextHolder.set(dbType);
	}
	
	public static String getCurrentDb() {
		return dbContextHolder.get();
	}
	
	public void clear() {
		dbContextHolder.remove();
	}
}