package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CandidateUserFieldsDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao	candidateDao;
	//
	@Autowired
	CandidateUDFDao	candidateUDFDao;
	
	public Boolean updateCandidateUserfields(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, Userfield[] userfields) throws Exception {
		//
		Boolean existCandidate = candidateDao.existCandidate(jobDivaSession, candidateid);
		if (!existCandidate) {
			throw new Exception("Error: Candidate(" + candidateid + ") is not found.");
		}
		//
		if (overwrite != null && overwrite) {
			candidateUDFDao.deletCandidateUDF(jobDivaSession, candidateid);
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		validateUserFields(jobDivaSession, candidateid, userfields, UDF_FIELDFOR_CANDIDATE);
		//
		if (userfields != null) {
			for (int i = 0; i < userfields.length; i++) {
				// \
				Userfield userfield = userfields[i];
				//
				if (isEmpty(userfield.getUserfieldValue())) {
					candidateUDFDao.deletCandidateUDF(jobDivaSession, candidateid, userfield.getUserfieldId());
				} else {
					String sql = "SELECT USERFIELD_VALUE from TCANDIDATE_USERFIELDS Where CANDIDATEID = ? AND USERFIELD_ID = ? and TEAMID = ?  ";
					//
					Object[] params = new Object[] { candidateid, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
					//
					//
					List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
						
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString("USERFIELD_VALUE");
						}
					});
					//
					Timestamp currentDt = new Timestamp(System.currentTimeMillis());
					//
					if (list == null || list.isEmpty()) {
						String sqlInsert = "INSERT INTO TCANDIDATE_USERFIELDS(CANDIDATEID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED) " //
								+ "VALUES " //
								+ "(? , ?, ?, ?, ?) ";
						params = new Object[] { candidateid, userfield.getUserfieldId(), jobDivaSession.getTeamId(), userfield.getUserfieldValue(), currentDt };
						jdbcTemplate.update(sqlInsert, params);
					} else {
						String sqlUpdate = "Update TCANDIDATE_USERFIELDS SET USERFIELD_VALUE = ?, DATECREATED = ? " //
								+ "WHERE CANDIDATEID = ? AND USERFIELD_ID = ? and TEAMID = ?   ";//
						params = new Object[] { userfield.getUserfieldValue(), currentDt, candidateid, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
						jdbcTemplate.update(sqlUpdate, params);
					}
					//
				}
			}
		}
		//
		return true;
	}
}
