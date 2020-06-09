package com.jobdiva.api.dao.job;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobContactDao extends AbstractJobDivaDao {
	
	public void saveJobContact(Long teamid, Long jobid, Long cutomerId, Boolean showOnJob, Integer roleid) {
		String sqlInsert = "INSERT INTO TRFQ_CUSTOMERS " //
				+ " (TEAMID, RFQID, CUSTOMERID, SHOWONJOB, ROLEID ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,?   ) ";//
		Object[] params = new Object[] { teamid, jobid, cutomerId, showOnJob, roleid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void updateJobContact(Long teamid, Long jobid, Long contactId, Boolean showOnJob, Integer roleid) {
		String sqlInsert = "UPDATE TRFQ_CUSTOMERS " //
				+ " SET SHOWONJOB = ? , " //
				+ " ROLEID = ? " //
				+ " WHERE TEAMID = ? " //
				+ " AND RFQID = ? " //
				+ " AND CUSTOMERID = ? "; //
		Object[] params = new Object[] { showOnJob, roleid, teamid, jobid, contactId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void deleteJobContact(Long teamid, Long jobid, Long contactId) {
		String sqlInsert = "DELETE FROM  TRFQ_CUSTOMERS " //
				+ " WHERE TEAMID = ? " //
				+ " AND RFQID = ? " //
				+ " AND CUSTOMERID = ? "; //
		Object[] params = new Object[] { teamid, jobid, contactId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
