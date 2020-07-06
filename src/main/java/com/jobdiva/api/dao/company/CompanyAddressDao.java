package com.jobdiva.api.dao.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.CompanyAddress;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CompanyAddressDao extends AbstractJobDivaDao {
	
	private void assignCompanyAddressFields(CompanyAddress companyAddress, ArrayList<String> fieldList, ArrayList<Object> paramList) throws Exception {
		if (isNotEmpty(companyAddress.getAddress1())) {
			fieldList.add("ADDRESS1");
			paramList.add(companyAddress.getAddress1());
		}
		//
		if (isNotEmpty(companyAddress.getAddress2())) {
			fieldList.add("ADDRESS2");
			paramList.add(companyAddress.getAddress2());
		}
		//
		if (isNotEmpty(companyAddress.getCity())) {
			fieldList.add("CITY");
			paramList.add(companyAddress.getCity());
		}
		//
		if (isNotEmpty(companyAddress.getCountryid())) {
			fieldList.add("COUNTRYID");
			paramList.add(companyAddress.getCountryid());
		}
		//
		if (isNotEmpty(companyAddress.getEmail())) {
			fieldList.add("EMAIL");
			paramList.add(companyAddress.getEmail());
		}
		//
		if (isNotEmpty(companyAddress.getFax())) {
			fieldList.add("FAX");
			paramList.add(companyAddress.getFax());
		}
		//
		if (isNotEmpty(companyAddress.getPhone())) {
			fieldList.add("PHONE");
			paramList.add(companyAddress.getPhone());
		}
		//
		if (isNotEmpty(companyAddress.getZipcode())) {
			fieldList.add("ZIPCODE");
			paramList.add(companyAddress.getZipcode());
		}
		//
		if (isNotEmpty(companyAddress.getUrl())) {
			fieldList.add("URL");
			paramList.add(companyAddress.getUrl());
		}
		//
		if (isNotEmpty(companyAddress.getState())) {
			String lookUpState = lookupState(companyAddress.getState(), companyAddress.getCountryid());
			if (lookUpState != null) {
				fieldList.add("STATE");
				paramList.add(lookUpState);
			} else {
				throw new Exception("Error: State (" + companyAddress.getState() + ") can not be updated due to the mapping unfound.(with countryid(" + companyAddress.getCountryid() + ")) \r\n");
			}
		}
	}
	
	public void insertCompanyAddress(JobDivaSession jobDivaSession, Long companyid, CompanyAddress companyAddress, Short maxAddressId) throws Exception {
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fieldList.add("COMPANYID");
		paramList.add(companyid);
		fieldList.add("ADDRESSID");
		paramList.add(++maxAddressId);
		fieldList.add("TEAMID");
		paramList.add(jobDivaSession.getTeamId());
		//
		assignCompanyAddressFields(companyAddress, fieldList, paramList);
		//
		String sqlInsert = "INSERT INTO TCUSTOMERCOMPANYADDRESSES (" + sqlInsertFields(fieldList) + ") VALUES (" + sqlInsertParams(fieldList) + ") ";
		//
		Object[] parameters = paramList.toArray();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlInsert, parameters);
		//
	}
	
	public void updateCompanyAddress(JobDivaSession jobDivaSession, Long companyid, CompanyAddress companyAddress) throws Exception {
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		assignCompanyAddressFields(companyAddress, fieldList, paramList);
		//
		paramList.add(companyid);
		paramList.add(companyAddress.getAddressId());
		//
		String sqlUpdate = "UPDATE TCUSTOMERCOMPANYADDRESSES SET " + sqlUpdateFields(fieldList);
		sqlUpdate += " WHERE COMPANYID = ? AND ADDRESSID = ? ";
		//
		Object[] parameters = paramList.toArray();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlUpdate, parameters);
		//
	}
	
	public void deleteCompanyAddress(Long companyId, Short addressId, Long teamid) {
		String sqlDelete = "DELETE   " //
				+ " FROM TCUSTOMERCOMPANYADDRESSES " //
				+ " WHERE COMPANYID = ? and ADDRESSID = ? and TEAMID = ? ";
		Object[] params = new Object[] { companyId, addressId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
	
	public List<CompanyAddress> getCompanyAddresses(long companyId, long teamId) {
		String sql = " Select "//
				+ " ADDRESSID , "//
				+ " TEAMID, " //
				+ " ADDRESS1, " //
				+ " ADDRESS2, " //
				+ " CITY , " //
				+ " STATE, " //
				+ " ZIPCODE ," //
				+ " PHONE, " //
				+ " FAX, " //
				+ " URL, " //
				+ " EMAIL, " //
				+ " DEFAULT_ADDRESS, " //
				+ " COUNTRYID " //
				+ " FROM TCUSTOMERCOMPANYADDRESSES " //
				+ " WHERE COMPANYID = ? and TEAMID = ? ";
		//
		Object[] params = new Object[] { companyId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<CompanyAddress> list = jdbcTemplate.query(sql, params, new RowMapper<CompanyAddress>() {
			
			@Override
			public CompanyAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
				CompanyAddress companyAddress = new CompanyAddress();
				companyAddress.setCompanyId(companyId);
				companyAddress.setAddressId(rs.getShort("ADDRESSID"));
				companyAddress.setTeamid(rs.getLong("TEAMID"));
				companyAddress.setAddress1(rs.getString("ADDRESS1"));
				companyAddress.setAddress2(rs.getString("ADDRESS2"));
				companyAddress.setCity(rs.getString("CITY"));
				companyAddress.setState(rs.getString("STATE"));
				companyAddress.setZipcode(rs.getString("ZIPCODE"));
				companyAddress.setPhone(rs.getString("PHONE"));
				companyAddress.setFax(rs.getString("FAX"));
				companyAddress.setUrl(rs.getString("URL"));
				companyAddress.setEmail(rs.getString("EMAIL"));
				companyAddress.setDefaultAddress(rs.getBoolean("DEFAULT_ADDRESS"));
				companyAddress.setCountryid(rs.getString("COUNTRYID"));
				return companyAddress;
			}
		});
		return list;
	}
	
	public CompanyAddress searchForComapnyAddress(List<CompanyAddress> companyAddressList, Short addressId) {
		for (CompanyAddress address : companyAddressList) {
			if ((addressId != null && addressId.equals(address.getAddressId())) //
			)
				return address;
		}
		return null;
	}
}
