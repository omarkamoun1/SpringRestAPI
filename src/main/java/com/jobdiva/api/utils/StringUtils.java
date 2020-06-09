package com.jobdiva.api.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
	
	public static String escapeSql(String sql) { // escape String literal per
													// SQL rule
		if (sql == null)
			return "";
		HashMap<String, String> escapeRules = new HashMap<String, String>();
		escapeRules.put("'", "''");
		String escapedSql = sql;
		for (Map.Entry<String, String> entry : escapeRules.entrySet()) {
			escapedSql = escapedSql.replaceAll(entry.getKey(), entry.getValue());
		}
		return escapedSql;
	}
	
	public static String escapeQuote(String value) {
		if (value != null && !value.isEmpty()) {
			if (value.startsWith("'") && value.endsWith("'")) {
				return value.substring(1, value.length() - 1);
			}
		}
		return value;
	}
	
	public static boolean validateStrArr(String[] str_arr) {
		boolean valid = false;
		//
		if (str_arr == null || str_arr.length == 0)
			return valid;
		//
		for (int i = 0; i < str_arr.length;) {
			if (str_arr[i] != null && str_arr[i].length() > 0) {
				valid = true;
			}
			break;
		}
		return valid;
	}
	
	public static String deNull(String str) {
		if (str == null || str.equals("null"))
			return "";
		else
			return str;
	}
	
	public static String get50(String in) {
		String out = "";
		if (in != null && in.length() > 0) {
			out = in;
			if (out.length() > 50)
				out = out.substring(0, 50);
		}
		return out;
	}
	
	public static String fnc_EscapeHTML(String strInput) {
		if (strInput == null)
			strInput = "";
		return strInput.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
	}
}
