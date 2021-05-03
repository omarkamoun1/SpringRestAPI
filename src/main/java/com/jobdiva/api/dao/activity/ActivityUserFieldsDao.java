package com.jobdiva.api.dao.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ActivityUserFieldsDao extends AbstractJobDivaDao {
	
	public Boolean existActivityUDF(JobDivaSession jobDivaSession, Long startId, Integer userfieldId) {
		//
		String sql = "SELECT * FROM TSTARTRECORD_USERFIELDS WHERE TEAMID = ? AND STARTID = ? AND USERFIELD_ID = ? ";
		Object[] params = new Object[] { jobDivaSession.getTeamId(), startId, userfieldId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Boolean> list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return true;
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : false;
	}
	
	public void deleteActivityUDF(JobDivaSession jobDivaSession, Long startId, Integer userfieldId) {
		String sql = "DELETE FROM TSTARTRECORD_USERFIELDS WHERE TEAMID = ? AND STARTID = ? AND USERFIELD_ID = ? ";
		Object[] params = new Object[] { jobDivaSession.getTeamId(), startId, userfieldId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sql, params);
	}
	
	public void updateActivityUDF(Long startid, Integer userfieldId, Long teamId, Date dateUpdated, String userfieldValue) {
		String sqlUpdate = "UPDATE TSTARTRECORD_USERFIELDS SET " //
				+ " USERFIELD_VALUE = ? , " //
				+ " DATEUPDATED = ? " //
				+ "  where STARTID = ? " //
				+ " and USERFIELD_ID = ? " //
				+ " and TEAMID = ? "; //
		Object[] params = new Object[] { userfieldValue, dateUpdated, startid, userfieldId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, params);
	}
	
	public void insertActivityUDF(Long startid, Integer userfieldId, Long teamId, Date dateCreated, String userfieldValue) {
		String sqlInsert = "INSERT INTO TSTARTRECORD_USERFIELDS " //
				+ " (STARTID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,? ) ";//
		Object[] params = new Object[] { startid, userfieldId, teamId, userfieldValue, dateCreated };
		// ,
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
