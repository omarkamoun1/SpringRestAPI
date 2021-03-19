package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.CandidateUDF;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.webhook.WebhookUDF;

@Component
public class CandidateUDFDao extends AbstractJobDivaDao {
	
	public List<CandidateUDF> getContactUDF(Long candidateId, Long teamId) {
		String sql = " Select "//
				+ " TEAMID , "//
				+ " CANDIDATEID, " //
				+ " USERFIELD_ID , " //
				+ " USERFIELD_VALUE ," //
				+ " DATECREATED " //
				+ " FROM TCANDIDATE_USERFIELDS " //
				+ " WHERE CANDIDATEID = ?  AND TEAMID = ? ";
		//
		Object[] params = new Object[] { candidateId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<CandidateUDF> list = jdbcTemplate.query(sql, params, new RowMapper<CandidateUDF>() {
			
			@Override
			public CandidateUDF mapRow(ResultSet rs, int rowNum) throws SQLException {
				CandidateUDF contactUDF = new CandidateUDF();
				contactUDF.setCandidateId(rs.getLong("CANDIDATEID"));
				contactUDF.setTeamid(rs.getLong("TEAMID"));
				contactUDF.setUserFieldId(rs.getInt("USERFIELD_ID"));
				contactUDF.setUserfieldValue(rs.getString("USERFIELD_VALUE"));
				contactUDF.setDateCreated(rs.getDate("DATECREATED"));
				return contactUDF;
			}
		});
		return list;
	}
	
	public List<WebhookUDF> getContactWebhookUDF(Long candidateId, Long teamId) {
		String sql = " Select "//
				+ " TEAMID , "//
				+ " CANDIDATEID, " //
				+ " USERFIELD_ID , " //
				+ " USERFIELD_VALUE ," //
				+ " DATECREATED " //
				+ " FROM TCANDIDATE_USERFIELDS " //
				+ " WHERE CANDIDATEID = ?  AND TEAMID = ? ";
		//
		Object[] params = new Object[] { candidateId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<WebhookUDF> list = jdbcTemplate.query(sql, params, new RowMapper<WebhookUDF>() {
			
			@Override
			public WebhookUDF mapRow(ResultSet rs, int rowNum) throws SQLException {
				WebhookUDF contactUDF = new WebhookUDF();
				contactUDF.setUserFieldId(rs.getInt("USERFIELD_ID"));
				contactUDF.setUserfieldValue(rs.getString("USERFIELD_VALUE"));
				contactUDF.setDateCreated(rs.getDate("DATECREATED"));
				return contactUDF;
			}
		});
		return list;
	}
	
	public void deletCandidateUDF(JobDivaSession jobDivaSession, Long candidateid) {
		String sqlDelete = "DELETE FROM TCANDIDATE_USERFIELDS Where CANDIDATEID = ? AND TEAMID = ? ";
		Object[] params = new Object[] { candidateid, jobDivaSession.getTeamId() };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
	
	public void deletCandidateUDF(JobDivaSession jobDivaSession, Long candidateid, Integer userfieldId) {
		String sqlDelete = "DELETE FROM TCANDIDATE_USERFIELDS Where CANDIDATEID = ? AND USERFIELD_ID = ?  AND TEAMID = ? ";
		Object[] params = new Object[] { candidateid, userfieldId, jobDivaSession.getTeamId() };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
}
