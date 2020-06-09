package com.jobdiva.api.dao.job;

import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobStatusLogDao extends AbstractJobDivaDao {
	
	public void addJobStatusLog(Long teamid, Long jobid, Integer status, Long modifier, Integer prevStatus) {
		//
		String sqlInsert = "INSERT INTO TRFQ_STATUSLOG(TEAMID, RFQID, CUR_STATUS, DATECREATED , MODIFIER , PREV_STATUS  ) " //
				+ " VALUES " //
				+ " ( ? , ? , ? , ? ,? ,? ) ";
		//
		Object[] params = new Object[] { teamid, jobid, status, new Timestamp(System.currentTimeMillis()), modifier, prevStatus };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
		//
	}
}
