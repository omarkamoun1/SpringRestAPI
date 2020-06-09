package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CandidateUnreachableDao extends AbstractJobDivaDao {
	
	public void insertUpdateCandidateUnreachable(JobDivaSession jobDivaSession, Long candidateid, Integer flag, Date dateAvailable, Timestamp dateCreated) {
		//
		String sqlExists = " SELECT CANDIDATEID FROM TCANDIDATE_UNREACHABLE WHERE TEAMID = ? and CANDIDATEID = ? ";
		Object[] params = new Object[] { jobDivaSession.getTeamId(), candidateid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sqlExists, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("CANDIDATEID");
			}
		});
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("TEAMID");
		paramList.add(jobDivaSession.getTeamId());
		//
		fields.add("CANDIDATEID");
		paramList.add(candidateid);
		//
		fields.add("FLAG");
		paramList.add(flag);
		//
		fields.add("DATEAVAILABLE");
		paramList.add(dateAvailable);
		//
		fields.add("DATE_CREATED");
		paramList.add(dateCreated);
		//
		Boolean insertMode = list == null || list.size() == 0;
		//
		if (insertMode) {
			//
			String sqlInsert = "INSERT INTO TCANDIDATE_UNREACHABLE (" + sqlInsertFields(fields) + ") VALUES (" + sqlInsertParams(fields) + ") ";
			//
			Object[] parameters = paramList.toArray();
			jdbcTemplate.update(sqlInsert, parameters);
		} else {
			//
			String sqlUpdate = "UPDATE TCANDIDATE_UNREACHABLE SET  " + sqlUpdateFields(fields) + " WHERE TEAMID = ? and CANDIDATEID = ? ";
			paramList.add(jobDivaSession.getTeamId());
			paramList.add(candidateid);
			//
			Object[] parameters = paramList.toArray();
			jdbcTemplate.update(sqlUpdate, parameters);
		}
	}
}
