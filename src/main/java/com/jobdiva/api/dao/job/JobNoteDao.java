package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Note;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.sql.JobDivaSqlLobValue;

@Component
public class JobNoteDao extends AbstractJobDivaDao {
	
	private void checkJobIfExists(Long teamId, Long jobId) throws Exception {
		String sqlCheck = "select ID from TRFQ where ID = ? and TEAMID = ? ";
		Object[] params = new Object[] { jobId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sqlCheck, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		//
		if (list == null || list.size() == 0) {
			throw new Exception("Error: Job " + jobId + " is not found.");
		}
	}
	
	private Boolean checkRecruiterid(Long teamId, Long recruiterId) throws Exception {
		String sqlCheck = "select ID from TRECRUITER where ID = ? and GROUPID = ? ";
		Object[] params = new Object[] { recruiterId, teamId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sqlCheck, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		//
		if (list == null || list.size() == 0) {
			throw new Exception("Error: User(" + recruiterId + ") is not found or  inactive.\r\n");
		}
		return true;
	}
	
	private Integer getMaxNoteId(Long jobId) {
		//
		String sql = "SELECT MAX(NOTEID) FROM TRFQNOTES Where RFQID = ? ";
		//
		Object[] params = new Object[] { jobId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : 0;
	}
	
	public Boolean addJobNote(JobDivaSession jobDivaSession, Long jobId, Integer type, Long recruiterId, Integer shared, String note) throws Exception {
		//
		type = type != null ? type : 5;
		shared = shared != null ? shared : 0;
		Long emId = 0L;
		//
		checkJobIfExists(jobDivaSession.getTeamId(), jobId);
		//
		checkRecruiterid(jobDivaSession.getTeamId(), recruiterId);
		//
		Integer noteId = getMaxNoteId(jobId) + 1;
		//
		//
		MapSqlParameterSource paramList = new MapSqlParameterSource();
		//
		String sqlInsert = "INSERT INTO TRFQNOTES(RFQID, NOTEID, TYPE, DATECREATED, RECRUITERID, SHARED, NOTE ,EMID ) " //
				+ " VALUES " //
				+ " (:jobId , :noteId , :type , :dateCreated ,:recruiterId , :shared , :note , :emId ) ";
		//
		//
		paramList.addValue("jobId", jobId);
		paramList.addValue("noteId", noteId);
		paramList.addValue("type", type);
		paramList.addValue("dateCreated", new Timestamp(System.currentTimeMillis()));
		paramList.addValue("recruiterId", recruiterId);
		paramList.addValue("shared", shared);
		paramList.addValue("note", new JobDivaSqlLobValue(note, new DefaultLobHandler()), Types.CLOB);
		paramList.addValue("emId", emId);
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = getNamedParameterJdbcTemplate();
		jdbcTemplateObject.update(sqlInsert, paramList);
		//
		return true;
	}
	
	public List<Note> getJobNotes(JobDivaSession jobDivaSession, Long jobId) throws Exception{
		//
		String sql=" select b.datecreated, b.recruiterid, c.firstname , c.lastname, b.note from trfq a, trfqnotes b, trecruiter c "
				+ " where a.id=? and a.teamid=? and a.id = b.rfqid and b.recruiterid = c.id order by b.datecreated desc ";
		//
		Object[] params = new Object[] { jobId, jobDivaSession.getTeamId() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Note> notes = jdbcTemplate.query(sql, params, new RowMapper<Note>() {
			
			@Override
			public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
				Note noteObj = new Note();
				//
				noteObj.setId(jobId);
				noteObj.setDate(rs.getDate("DATECREATED"));
				noteObj.setContent(rs.getString("NOTE"));
				noteObj.setRecruiterId(rs.getLong("RECRUITERID"));
				noteObj.setRecruiterName(rs.getString("FIRSTNAME")+" "+rs.getString("LASTNAME"));
				//
				return noteObj;
			}
		});
		return  notes;
	}
	
}
