package com.jobdiva.api.dao.job;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobStatusLogDao extends AbstractJobDivaDao {
	
	public void addJobStatusLog(Long teamid, Long jobid, Integer status, Long modifier, Integer prevStatus) {
		//
		String sqlInsert = "INSERT INTO TRFQ_STATUSLOG(ID,TEAMID, RFQID, CUR_STATUS, UPDATEDDATE , MODIFIER , PREV_STATUS  ) " //
				+ " VALUES " //
				+ " ( RFQ_LOG_SEQ.nextval,? , ? , ? , sysdate ,? ,? ) ";
		//
		Object[] params = new Object[] { teamid, jobid, status, modifier, prevStatus };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
		//
	}
}
