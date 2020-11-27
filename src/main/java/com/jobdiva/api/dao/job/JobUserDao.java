package com.jobdiva.api.dao.job;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobUserDao extends AbstractJobDivaDao {
	
	public void insertJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "INSERT INTO TRECRUITERRFQ " //
				+ " (RFQID, RECRUITERID, TEAMID, REC_EMAIL, LEAD_RECRUITER, SALES, LEAD_SALES, RECRUITER, JOBSTATUS ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,? , ? ,? ,? , ? ) ";//
		Object[] params = new Object[] { jobid, userId, teamid, rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void updateJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "UPDATE  TRECRUITERRFQ " //
				+ " SET REC_EMAIL = ? , LEAD_RECRUITER = ? , SALES = ? , LEAD_SALES = ?, RECRUITER = ? , JOBSTATUS = ?   " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status, jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void deletJobuser(Long jobid, Long userId, Long teamid) {
		String sqlInsert = "DELETE FROM  TRECRUITERRFQ " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
