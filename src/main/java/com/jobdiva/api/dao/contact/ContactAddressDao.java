package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ContactAddress;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ContactAddressDao extends AbstractJobDivaDao {
	
	public List<ContactAddress> getContactAddresses(long contactId, long teamId, Boolean deleted) {
		String sql = " Select DISTINCT "//
				+ " ID, " //
				+ " CONTACTID, " //
				+ " TEAMID, "//
				+ " ADDRESS1, " //
				+ " ADDRESS2, " //
				+ " CITY , " //
				+ " STATE, " //
				+ " ZIPCODE ," //
				+ " COUNTRYID, " //
				+ " DEFAULT_ADDRESS, " //
				+ " DELETED, " //
				+ " FREETEXT " //
				+ " FROM TCUSTOMERADDRESS " //
				+ " WHERE CONTACTID = ? and TEAMID = ? AND ROWNUM <= 100 ORDER BY ID";
		//
		Object[] params = null;
		if (deleted != null) {
			sql += " AND DELETED = ? ";
			params = new Object[] { contactId, teamId, deleted };
		} else {
			params = new Object[] { contactId, teamId };
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<ContactAddress> list = jdbcTemplate.query(sql, params, new RowMapper<ContactAddress>() {
			
			@Override
			public ContactAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContactAddress contactAddress = new ContactAddress();
				contactAddress.setId(rs.getLong("ID"));
				contactAddress.setContactId(rs.getLong("CONTACTID"));
				contactAddress.setTeamId(rs.getLong("TEAMID"));
				contactAddress.setAddress1(rs.getString("ADDRESS1"));
				contactAddress.setAddress2(rs.getString("ADDRESS2"));
				contactAddress.setCity(rs.getString("CITY"));
				contactAddress.setState(rs.getString("STATE"));
				contactAddress.setZipCode(rs.getString("ZIPCODE"));
				contactAddress.setCountryId(rs.getString("COUNTRYID"));
				contactAddress.setDefaultAddress(rs.getBoolean("DEFAULT_ADDRESS"));
				contactAddress.setDeleted(rs.getBoolean("DELETED"));
				contactAddress.setFreeText(rs.getString("FREETEXT"));
				return contactAddress;
			}
		});
		return list;
	}
	
	public List<ContactAddress> getContactAddresses(long contactId, long teamId) {
		return getContactAddresses(contactId, teamId, null);
	}
	
	public void deleteContactAddress(Long teamId, Long id, Long contactId) {
		String sqlDelete = "DELETE FROM TCUSTOMERADDRESS WHERE ID = ? AND CONTACTID = ? and TEAMID = ? ";
		Object[] params = new Object[] { id, contactId, teamId };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
	
	public void insertUpdateContactAddress(JobDivaSession jobDivaSession, ContactAddress contactAddress) throws Exception {
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		if (contactAddress.getId() == null || contactAddress.getId().intValue() == 0) {
			//
			fields.add("CONTACTID");
			paramList.add(contactAddress.getContactId());
			//
			fields.add("TEAMID");
			paramList.add(contactAddress.getTeamId());
		}
		//
		//
		if (isNotEmpty(contactAddress.getAddress1())) {
			fields.add("ADDRESS1");
			paramList.add(contactAddress.getAddress1());
		}
		//
		if (isNotEmpty(contactAddress.getAddress2())) {
			fields.add("ADDRESS2");
			paramList.add(contactAddress.getAddress2());
		}
		//
		if (isNotEmpty(contactAddress.getCity())) {
			fields.add("CITY");
			paramList.add(contactAddress.getCity());
		}
		//
		if (isNotEmpty(contactAddress.getZipCode())) {
			fields.add("ZIPCODE");
			paramList.add(contactAddress.getZipCode());
		}
		//
		if (isNotEmpty(contactAddress.getCountryId())) {
			fields.add("COUNTRYID");
			paramList.add(contactAddress.getCountryId());
		}
		//
		if (isNotEmpty(contactAddress.getState())) {
			//
			if (!isNotEmpty(contactAddress.getCountryId()))
				contactAddress.setCountryId("US");
			//
			String lookUpState = lookupState(contactAddress.getState(), contactAddress.getCountryId());
			if (lookUpState != null) {
				fields.add("STATE");
				paramList.add(lookUpState);
			} else {
				throw new Exception("Error: State (" + contactAddress.getState() + ") can not be identified.(with countryid(" + contactAddress.getCountryId() + ")) \r\n");
			}
		} else if (!fields.contains("COUNTRYID")) {
			fields.add("COUNTRYID");
			paramList.add("US");
		}
		//
		fields.add("DELETED");
		paramList.add(contactAddress.getDeleted());
		//
		fields.add("DEFAULT_ADDRESS");
		paramList.add(contactAddress.getDefaultAddress());
		//
		if (isNotEmpty(contactAddress.getFreeText())) {
			fields.add("FREETEXT");
			paramList.add(contactAddress.getFreeText());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		try {
			if (contactAddress.getId() != null && contactAddress.getId() > 0) {
				String sqlUpdate = " UPDATE TCUSTOMERADDRESS SET " + sqlUpdateFields(fields) + " WHERE ID = ?  and TEAMID = ? ";
				paramList.add(contactAddress.getId());
				paramList.add(jobDivaSession.getTeamId());
				Object[] params = paramList.toArray();
				jdbcTemplate.update(sqlUpdate, params);
			} else {
				//
				String sqlInsert = "INSERT INTO TCUSTOMERADDRESS (" + sqlInsertFields(fields) + ") VALUES (" + sqlInsertParams(fields) + ") ";
				//
				Object[] parameters = paramList.toArray();
				jdbcTemplate.update(sqlInsert, parameters);
				//
			}
		} catch (Exception e) {
			logger.debug("insertUpdateContactAddress " + e.getMessage());
			throw new Exception(e);
		}
	}
}
