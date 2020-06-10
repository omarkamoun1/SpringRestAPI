package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.ZipInfo;

public class ZipInfoRowMapper implements RowMapper<ZipInfo> {
	
	private Short getShort(ResultSet rs, String fieldName) throws SQLException {
		Integer value = rs.getInt(fieldName);
		return value != null ? value.shortValue() : null;
	}
	
	@Override
	public ZipInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ZipInfo zipInfo = new ZipInfo();
		//
		zipInfo.setCity(rs.getString("CITY"));
		zipInfo.setCountry(rs.getString("COUNTRY"));
		zipInfo.setLatitude(rs.getString("LATITUDE"));
		zipInfo.setLongitude(rs.getString("LONGITUDE"));
		zipInfo.setState(rs.getString("STATE"));
		zipInfo.setStateName(rs.getString("STATENAME"));
		zipInfo.setStates100Mile(rs.getString("STATES_100MILE"));
		zipInfo.setStates10Mile(rs.getString("STATES_10MILE"));
		zipInfo.setStates20Mile(rs.getString("STATES_20MILE"));
		zipInfo.setStates30Mile(rs.getString("STATES_30MILE"));
		zipInfo.setStates40Mile(rs.getString("STATES_40MILE"));
		zipInfo.setStates50Mile(rs.getString("STATES_50MILE"));
		zipInfo.setStates5Mile(rs.getString("STATES_5MILE"));
		zipInfo.setStates75Mile(rs.getString("STATES_75MILE"));
		zipInfo.setZipcode(rs.getString("ZIPCODE"));
		//
		return zipInfo;
	}
}
