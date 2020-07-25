package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ContactOwner;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ContactOwnerDao extends AbstractJobDivaDao {
	
	// public ContactOwner getContactOwnerByOnerId()
	public List<ContactOwner> getContactOwners(long contactId, Long teamId) {
		String sql = " Select "//
				+ " RECRUITERID," //
				+ " CUSTOMERID," //
				+ " TEAMID," //
				+ " ISPRIMARYOWNER  "//
				+ " FROM TCUSTOMER_OWNERS " //
				+ " WHERE CUSTOMERID = ? and TEAMID = ?  ";
		//
		Object[] params = new Object[] { contactId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<ContactOwner> list = jdbcTemplate.query(sql, params, new RowMapper<ContactOwner>() {
			
			@Override
			public ContactOwner mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContactOwner contactOwner = new ContactOwner();
				contactOwner.setCustomerId(rs.getLong("CUSTOMERID"));
				contactOwner.setRecruiterId(rs.getLong("RECRUITERID"));
				contactOwner.setTeamId(rs.getLong("TEAMID"));
				contactOwner.setIsPrimaryOwner(rs.getBoolean("ISPRIMARYOWNER"));
				//
				return contactOwner;
			}
		});
		return list;
	}
	
	public void insertcontactOwner(JobDivaSession jobDivaSession, ContactOwner contactOwner) {
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("CUSTOMERID");
		paramList.add(contactOwner.getCustomerId());
		//
		fields.add("RECRUITERID");
		paramList.add(contactOwner.getRecruiterId());
		//
		fields.add("TEAMID");
		paramList.add(contactOwner.getTeamId());
		//
		fields.add("ISPRIMARYOWNER");
		paramList.add(contactOwner.getIsPrimaryOwner());
		//
		//
		String sqlInsert = "INSERT INTO TCUSTOMER_OWNERS (" + sqlInsertFields(fields) + ") VALUES (" + sqlInsertParams(fields) + ") ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlInsert, parameters);
	}
	
	public void updatecontactOwner(JobDivaSession jobDivaSession, ContactOwner contactOwner) {
		//
		//
		String sqlInsert = "UPDATE  TCUSTOMER_OWNERS SET  ISPRIMARYOWNER = ? WHERE  CUSTOMERID = ? AND  RECRUITERID = ? and TEAMID = ?  ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		Object[] parameters = new Object[] { contactOwner.getIsPrimaryOwner(), contactOwner.getCustomerId(), contactOwner.getRecruiterId(), contactOwner.getTeamId() };
		jdbcTemplate.update(sqlInsert, parameters);
	}
}
