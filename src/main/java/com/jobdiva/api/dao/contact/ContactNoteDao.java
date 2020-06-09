package com.jobdiva.api.dao.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.ContactNote;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.sql.JobDivaSqlLobValue;

@Component
public class ContactNoteDao extends AbstractJobDivaDao {
	
	public List<ContactNote> getContactNotes(long contactId) {
		String sql = " Select "//
				+ " NOTEID," //
				+ " CUSTOMERID," //
				+ " DATECREATED," //
				+ " RECRUITERID," //
				+ " TAG_UPDATE," //
				+ " NOTE," //
				+ " RECRUITER_TEAMID," //
				+ " RFQID," //
				+ " EMAILMERGEID," //
				+ " DATEMODIFIED," //
				+ " DELETED," //
				+ " CANDIDATEID," //
				+ " LATESTNOTES "//
				+ " FROM TCUSTOMERNOTES " //
				+ " WHERE CUSTOMERID = ?  ";
		//
		Object[] params = new Object[] { contactId };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<ContactNote> list = jdbcTemplate.query(sql, params, new RowMapper<ContactNote>() {
			
			@Override
			public ContactNote mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContactNote contactNote = new ContactNote();
				contactNote.setId(rs.getLong("NOTEID"));
				contactNote.setCustomerId(rs.getLong("CUSTOMERID"));
				contactNote.setDateCreated(rs.getDate("DATECREATED"));
				contactNote.setRecruiterId(rs.getLong("RECRUITERID"));
				contactNote.setTagUpdate(rs.getString("TAG_UPDATE"));
				contactNote.setRecruiterTeamId(rs.getLong("RECRUITER_TEAMID"));
				contactNote.setRfqId(rs.getLong("RFQID"));
				contactNote.setEmailMergeId(rs.getLong("EMAILMERGEID"));
				contactNote.setDateModified(rs.getDate("DATEMODIFIED"));
				contactNote.setDeleted(rs.getBoolean("DELETED"));
				contactNote.setCandidateId(rs.getLong("CANDIDATEID"));
				contactNote.setLatestNotes(rs.getBoolean("LATESTNOTES"));
				//
				contactNote.setNote(clobToString(rs.getClob("NOTE")));
				//
				//
				return contactNote;
			}
		});
		return list;
	}
	
	public void insertUpdateContactNote(JobDivaSession jobDivaSession, ContactNote contactNote) throws Exception {
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		if (contactNote.getCustomerId() != null) {
			fields.put("CUSTOMERID", "contactid");
			parameterSource.addValue("contactid", contactNote.getCustomerId());
		}
		//
		if (contactNote.getDateCreated() != null) {
			fields.put("DATECREATED", "dateCreated");
			parameterSource.addValue("dateCreated", new Timestamp(contactNote.getDateCreated().getTime()));
		}
		//
		if (contactNote.getRecruiterId() != null) {
			fields.put("RECRUITERID", "recruiterId");
			parameterSource.addValue("recruiterId", contactNote.getRecruiterId());
		}
		//
		if (contactNote.getType() != null) {
			fields.put("TYPE", "type");
			parameterSource.addValue("type", contactNote.getType());
		}
		//
		if (contactNote.getTagUpdate() != null) {
			fields.put("TAG_UPDATE", "tagUpdate");
			parameterSource.addValue("tagUpdate", contactNote.getTagUpdate());
		}
		//
		if (contactNote.getRecruiterTeamId() != null) {
			fields.put("RECRUITER_TEAMID", "RECRUITER_TEAMID");
			parameterSource.addValue("RECRUITER_TEAMID", contactNote.getRecruiterTeamId());
		}
		//
		if (contactNote.getRfqId() != null) {
			fields.put("RFQID", "RFQID");
			parameterSource.addValue("RFQID", contactNote.getRfqId());
		}
		//
		if (contactNote.getEmailMergeId() != null) {
			fields.put("EMAILMERGEID", "EMAILMERGEID");
			parameterSource.addValue("EMAILMERGEID", contactNote.getEmailMergeId());
		}
		//
		if (contactNote.getDateModified() != null) {
			fields.put("DATEMODIFIED", "DATEMODIFIED");
			parameterSource.addValue("DATEMODIFIED", new Timestamp(contactNote.getDateModified().getTime()));
		}
		//
		if (contactNote.getDeleted() != null) {
			fields.put("DELETED", "DELETED");
			parameterSource.addValue("DELETED", contactNote.getDeleted());
		}
		//
		if (contactNote.getCandidateId() != null) {
			fields.put("CANDIDATEID", "CANDIDATEID");
			parameterSource.addValue("CANDIDATEID", contactNote.getCandidateId());
		}
		//
		if (contactNote.getLatestNotes() != null) {
			fields.put("LATESTNOTES", "LATESTNOTES");
			parameterSource.addValue("LATESTNOTES", contactNote.getLatestNotes());
		}
		//
		if (isNotEmpty(contactNote.getNote())) {
			fields.put("NOTE", "note");
			parameterSource.addValue("note", new JobDivaSqlLobValue(contactNote.getNote(), new DefaultLobHandler()), Types.CLOB);
		}
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		try {
			if (contactNote.getId() != null) {
				String sqlUpdate = " UPDATE TCUSTOMERNOTES SET " + sqlUpdateFields(fields) + " WHERE NOTEID = :id  ";
				parameterSource.addValue("id", contactNote.getId());
				//
				NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
				jdbcTemplateObject.update(sqlUpdate, parameterSource);
			} else {
				//
				String sqlInsert = "INSERT INTO TCUSTOMERNOTES (NOTEID, " + sqlInsertFields(new ArrayList<String>(fields.keySet())) + ") VALUES (CUSTOMERNOTEID.nextval," + sqlInsertValues(fields) + ") ";
				//
				NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
				jdbcTemplateObject.update(sqlInsert, parameterSource);
				//
			}
		} catch (Exception e) {
			logger.debug("insertUpdateContactAddress -> " + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean createContactNote(JobDivaSession jobDivaSession, Long contactid, String note, Long recruiterid, String action, Date actionDate, Long link2AnOpenJob, Long link2aCandidate) throws Exception {
		//
		//
		if (isEmpty(action) && actionDate != null) {
			throw new Exception("Error: 'actionDate' is invalid unless 'action' is set.");
		}
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		fields.put("CUSTOMERID", "contactid");
		parameterSource.addValue("contactid", contactid);
		//
		fields.put("RECRUITER_TEAMID", "teamId");
		parameterSource.addValue("teamId", jobDivaSession.getTeamId());
		//
		fields.put("DATEMODIFIED", "dateModified");
		parameterSource.addValue("dateModified", new Timestamp(System.currentTimeMillis()));
		//
		fields.put("NOTE", "note");
		parameterSource.addValue("note", new JobDivaSqlLobValue(note, new DefaultLobHandler()), Types.CLOB);
		//
		fields.put("RECRUITERID", "recruiterid");
		parameterSource.addValue("recruiterid", recruiterid != null ? recruiterid : jobDivaSession.getRecruiterId());
		//
		if (isNotEmpty(action)) {
			String sql = "select id, name from tactiontype where teamid = ? and active <> 0 and nvl(deleted, 0) <> 1";
			Object[] params = new Object[] { jobDivaSession.getTeamId() };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					String name = rs.getString("name");
					if (action.equals(name)) {
						Long typeId = rs.getLong("id");
						return typeId;
					}
					//
					return null;
				}
			});
			Long typeId = 0L;
			for (Long value : list) {
				if (value != null)
					typeId = value;
				break;
			}
			//
			if (typeId == 0L)
				try {
					throw new Exception("Error: Invalid Action Type(" + action + ").");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			//
			fields.put("TYPE", "typeId");
			parameterSource.addValue("typeId", typeId);
			//
		} else {
			fields.put("TYPE", "typeId");
			parameterSource.addValue("typeId", 0L);
		}
		//
		if (actionDate != null) {
			fields.put("DATECREATED", "datecreated");
			parameterSource.addValue("datecreated", actionDate);
		} else {
			fields.put("DATECREATED", "datecreated");
			parameterSource.addValue("datecreated", new Timestamp(System.currentTimeMillis()));
		}
		//
		String sqlInsert = "INSERT INTO TCUSTOMERNOTES (NOTEID," + sqlInsertFields(new ArrayList<String>(fields.keySet())) + ") VALUES (CUSTOMERNOTEID.nextval ," + sqlInsertValues(fields) + ") ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcTemplateObject.update(sqlInsert, parameterSource);
		//
		return true;
	}
}
