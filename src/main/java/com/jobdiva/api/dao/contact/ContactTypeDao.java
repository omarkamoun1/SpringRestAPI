package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ContactType;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ContactTypeDao extends AbstractJobDivaDao {
	
	public List<ContactType> getContactTypes(long contactId) {
		//
		String sql = " Select DISTINCT a.TYPEID, b.TYPENAME, b.ISDELETED  "//
				+ " FROM TCUSTOMER_TYPE a ,  tcustomertype b " //
				+ " WHERE a.TYPEID = b.TYPEID " //
				+ " and a.CUSTOMERID = ? "; //
		//
		Object[] params = new Object[] { contactId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<ContactType> list = jdbcTemplate.query(sql, params, new RowMapper<ContactType>() {
			
			@Override
			public ContactType mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				//
				ContactType contactType = new ContactType();
				contactType.setId(rs.getLong("TYPEID"));
				contactType.setName(rs.getString("TYPENAME"));
				contactType.setDeleted(rs.getInt("ISDELETED"));
				//
				return contactType;
			}
		});
		return list;
	}
	
	public void addcontactType(JobDivaSession jobDivaSession, Long contactId, Long typeId) {
		String sqlInsert = " INSERT INTO TCUSTOMER_TYPE( CUSTOMERID, TYPEID,  TEAMID ) VALUES (?,?,?)  ";
		Object[] params = new Object[] { contactId, typeId, jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
