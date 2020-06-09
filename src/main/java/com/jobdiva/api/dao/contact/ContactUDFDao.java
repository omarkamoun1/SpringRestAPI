package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ContactUDF;

@Component
public class ContactUDFDao extends AbstractJobDivaDao {
	
	public List<ContactUDF> getContactUDF(Long contactId, Long teamId) {
		String sql = " Select "//
				+ " CUSTOMERID , "//
				+ " USERFIELD_ID, " //
				+ " TEAMID , " //
				+ " USERFIELD_VALUE " //
				+ " FROM TCUSTOMER_USERFIELDS " //
				+ " WHERE CUSTOMERID = ?  AND TEAMID = ? ";
		//
		Object[] params = new Object[] { contactId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<ContactUDF> list = jdbcTemplate.query(sql, params, new RowMapper<ContactUDF>() {
			
			@Override
			public ContactUDF mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContactUDF contactUDF = new ContactUDF();
				contactUDF.setCustomerId(rs.getLong("CUSTOMERID"));
				contactUDF.setUserfieldId(rs.getInt("USERFIELD_ID"));
				contactUDF.setTeamid(rs.getLong("TEAMID"));
				contactUDF.setUserfieldValue(rs.getString("USERFIELD_VALUE"));
				return contactUDF;
			}
		});
		return list;
	}
}
