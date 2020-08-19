package com.jobdiva.api.dao.candidate;

import static java.net.URLDecoder.decode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.model.CandidateNote;
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.sql.JobDivaSqlLobValue;

@Component
public class CandidateNoteDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao	candidateDao;
	//
	@Autowired
	JobDao			jobDao;
	//
	@Autowired
	ContactDao		contactDao;
	
	public Long saveCandidateNote(JobDivaSession jobDivaSession, CandidateNote candidateNote) {
		//
		//
		//
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		// if (candidateNote.getNoteId() != null) {
		// fields.put("NOTEID", "NOTEID");
		// parameterSource.addValue("NOTEID", candidateNote.getNoteId());
		// }
		//
		fields.put("CANDIDATEID", "CANDIDATEID");
		parameterSource.addValue("CANDIDATEID", candidateNote.getCandidateId());
		//
		fields.put("TEAMID", "TEAMID");
		parameterSource.addValue("TEAMID", candidateNote.getTeamId());
		//
		fields.put("TYPE", "TYPE");
		parameterSource.addValue("TYPE", candidateNote.getType());
		//
		fields.put("RECRUITERID", "RECRUITERID");
		parameterSource.addValue("RECRUITERID", candidateNote.getRecruiterId());
		//
		if (candidateNote.getAuto() != null) {
			fields.put("AUTO", "AUTO");
			parameterSource.addValue("AUTO", candidateNote.getAuto());
		}
		//
		fields.put("RFQID", "RFQID");
		parameterSource.addValue("RFQID", candidateNote.getRfqId());
		//
		//
		//
		fields.put("RECRUITER_TEAMID", "RECRUITER_TEAMID");
		parameterSource.addValue("RECRUITER_TEAMID", candidateNote.getRecruiterTeamId());
		//
		//
		if (candidateNote.getDirty() != null) {
			fields.put("DIRTY", "DIRTY");
			parameterSource.addValue("DIRTY", candidateNote.getDirty());
		}
		//
		if (candidateNote.getDeleted() != null) {
			fields.put("DELETED", "DELETED");
			parameterSource.addValue("DELETED", candidateNote.getDeleted());
		}
		//
		if (candidateNote.getShared() != null) {
			fields.put("SHARED", "SHARED");
			parameterSource.addValue("SHARED", candidateNote.getShared());
		}
		//
		//
		//
		//
		if (candidateNote.getDirtyAttribute() != null) {
			fields.put("DIRTY_ATTRIBUTE", "DIRTY_ATTRIBUTE");
			parameterSource.addValue("DIRTY_ATTRIBUTE", candidateNote.getDirtyAttribute());
		}
		//
		if (isNotEmpty(candidateNote.getNote())) {
			fields.put("NOTE", "NOTE");
			parameterSource.addValue("NOTE", candidateNote.getNote());
		}
		//
		if (isNotEmpty(candidateNote.getNoteClob())) {
			DefaultLobHandler defaultLobHandler = new DefaultLobHandler();
			fields.put("NOTE_CLOB", "NOTE_CLOB");
			parameterSource.addValue("NOTE_CLOB", new JobDivaSqlLobValue(candidateNote.getNoteClob(), defaultLobHandler));
		}
		//
		if (candidateNote.getContactId() != null) {
			fields.put("CONTACTID", "CONTACTID");
			parameterSource.addValue("CONTACTID", candidateNote.getContactId());
		}
		//
		if (candidateNote.getDimDateCreate() != null) {
			fields.put("DIM_DATECREATED", "DIM_DATECREATED");
			parameterSource.addValue("DIM_DATECREATED", new Timestamp(candidateNote.getDimDateCreate().getTime()), Types.TIMESTAMP);
		}
		//
		if (candidateNote.getLatestContactNotes() != null) {
			fields.put("LATESTCONTACTNOTES", "LATESTCONTACTNOTES");
			parameterSource.addValue("LATESTCONTACTNOTES", candidateNote.getLatestContactNotes());
		}
		//
		if (candidateNote.getDateCreated() != null) {
			fields.put("DATECREATED", "dateCreated");
			parameterSource.addValue("dateCreated", candidateNote.getDateCreated());
		}
		//
		if (candidateNote.getDateAction() != null) {
			fields.put("DATEACTION", "dateAction");
			parameterSource.addValue("dateAction", candidateNote.getDateAction());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sql = "SELECT CANDIDATENOTEID.nextval  AS noteid  FROM dual";
		List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("noteid");
			}
		});
		Long noteid = 0L;
		if (listLong != null && listLong.size() > 0) {
			noteid = listLong.get(0);
		}
		//
		//
		String sqlInsert = " INSERT INTO TCANDIDATENOTES (NOTEID, " + sqlInsertFields(new ArrayList<String>(fields.keySet())) + ") VALUES (" + noteid + ", " + sqlInsertValues(fields) + ") ";
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcTemplateObject.update(sqlInsert, parameterSource);
		//
		return noteid;
	}
	
	public Long createCandidateNote(JobDivaSession jobDivaSession, Long candidateid, String note, Long recruiterid, String action, //
			Date actionDate, Long link2AnOpenJob, Long link2aContact, Boolean setAsAuto, Date createDate) throws Exception {
		//
		if (isEmpty(action)) {
			if (actionDate != null) {
				throw new Exception("Error: 'actionDate' is invalid unless 'action' is set.");
			}
		}
		//
		Boolean existCandidate = candidateDao.existCandidate(jobDivaSession, candidateid);
		if (!existCandidate) {
			logger.info("Error: Candidate(" + candidateid + ") is not found For Team (" + jobDivaSession.getTeamId() + ").");
			throw new Exception("Error: Candidate(" + candidateid + ") is not found.");
		}
		//
		if (note != null) {
			note = decode(note, "UTF-8");
		}
		//
		// set note clob, recruiter id ...
		Timestamp currentDt = createDate != null ? new Timestamp(createDate.getTime()) : new Timestamp(System.currentTimeMillis());
		int auto = setAsAuto != null && setAsAuto ? 3 : 0; //
		//
		CandidateNote candidateNote = new CandidateNote();
		candidateNote.setCandidateId(candidateid);
		candidateNote.setDateCreated(currentDt);
		candidateNote.setTeamId(jobDivaSession.getTeamId());
		candidateNote.setRecruiterTeamId(jobDivaSession.getTeamId());
		candidateNote.setAuto(auto);
		candidateNote.setDirty(true);
		candidateNote.setNoteClob(note);
		candidateNote.setRecruiterId(recruiterid != null && !recruiterid.equals(0L) ? recruiterid : jobDivaSession.getRecruiterId());
		// verify action type and set type id, action date
		if (isNotEmpty(action)) {
			String sql = "select id, name " //
					+ " from tactiontype_candidate "//
					+ " where teamid = ? "//
					+ " and (active <> 0 OR id < 0) and deleted <> 1";
			Object[] params = new Object[] { jobDivaSession.getTeamId() };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long typeId = rs.getLong("id");
					String typeaction = rs.getString("name");
					return (action.equalsIgnoreCase(typeaction)) ? typeId : null;
				}
			});
			//
			//
			Long typeId = null;
			for (Long value : list) {
				if (value != null) {
					typeId = value;
					break;
				}
			}
			//
			if (typeId == null)
				throw new Exception("Error: Invalid Action Type(" + action + ").");
			//
			candidateNote.setType(typeId);
		} else {
			candidateNote.setType(0L);
		}
		//
		//
		candidateNote.setDateAction(actionDate != null ? actionDate : new Date());
		//
		//
		// verify job id and set rfqid
		if (link2AnOpenJob != null) {
			//
			List<Job> searchJobs = jobDao.searchJobs(jobDivaSession, link2AnOpenJob);
			//
			Job job = searchJobs != null && searchJobs.size() > 0 ? searchJobs.get(0) : null;
			//
			if (job == null) {
				logger.info("Error: Invalid Job Id(" + link2AnOpenJob + "). For Team (" + jobDivaSession.getTeamId() + ").");
				throw new Exception("Error: Invalid Job Id(" + link2AnOpenJob + ").");
			}
			//
			candidateNote.setRfqId(link2AnOpenJob);
		} else {
			candidateNote.setRfqId(new Long(0L));
		}
		//
		//
		// verify contact id and set contactid
		if (link2aContact != null) {
			//
			List<Contact> contacts = contactDao.searchContacts(jobDivaSession, jobDivaSession.getTeamId(), link2aContact, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false);
			Contact contact = contacts != null && contacts.size() > 0 ? contacts.get(0) : null;
			//
			if (contact == null)
				throw new Exception("Error: Invalid Contact Id(" + link2aContact + "). For Team " + jobDivaSession.getTeamId());
			//
			candidateNote.setContactId(link2aContact);
		} else {
			candidateNote.setContactId(new Long(0L));
		}
		//
		//
		Long noteId = saveCandidateNote(jobDivaSession, candidateNote);
		//
		//
		return noteId;
	}
}
