package com.jobdiva.api.dao.job;

import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobUserFieldsDao extends AbstractJobDivaDao {
	
	public void addJobUserFieldsDao(long jobid, Integer userfieldId, long teamid, Date dateIssued, String userfieldValue) {
		String sqlInsert = "INSERT INTO TRFQ_USERFIELDS " //
				+ " (RFQID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED, DATEISSUED ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,?   ) ";//
		Object[] params = new Object[] { jobid, userfieldId, teamid, userfieldValue, new Date(), dateIssued };
		// ,
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void deleteJobUserFieldsDao(Long jobid, Integer userfieldId) {
		String sqldelete = "DELETE FROM TRFQ_USERFIELDS where RFQID = ? and USERFIELD_ID = ? ";//
		Object[] params = new Object[] { jobid, userfieldId };
		// ,
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqldelete, params);
	}
	
	public void updateJobUserFieldsDao(Long jobid, Integer userfieldId, Long teamid, Date dateIssued, String userfieldValue, Date dateUpdated) {
		String sqlUpdate = "UPDATE TRFQ_USERFIELDS SET USERFIELD_VALUE = ? , DATEISSUED = ? , DATEUPDATED = ? " //
				+ "  where RFQID = ? and USERFIELD_ID = ?   AND TEAMID = ? "; //
		Object[] params = new Object[] { userfieldValue, dateIssued, dateUpdated, jobid, userfieldId, teamid };
		// ,
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, params);
	}
}
