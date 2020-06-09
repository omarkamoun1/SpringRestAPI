package com.jobdiva.api.dao.event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class EventDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao candidateDao;
	
	private void parametresCheck(String subject, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid) throws Exception {
		StringBuffer message = new StringBuffer();
		if (subject.length() == 0)
			message.append("subject cannot be empty. ");
		if (assignedtoid <= 0)
			message.append("assignedToId is invalid(" + assignedtoid + "); ");
		if (assignedbyid != null && assignedbyid <= 0)
			message.append("assignedById is invalid(" + assignedbyid + "); ");
		if (tasktype != null && tasktype < 0)
			message.append("taskType is invalid(" + tasktype + "); ");
		if (percentagecompleted != null && percentagecompleted < 0)
			message.append("percentageCompleted is invalid(" + percentagecompleted + "); ");
		if (contactid != null && contactid < 0)
			message.append("contactId is invalid(" + contactid + "); ");
		if (candidateid != null && candidateid < 0)
			message.append("candidateId is invalid(" + candidateid + "); ");
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n Error: " + message.toString());
		}
	}
	
	public Boolean createTask(JobDivaSession jobDivaSession, String subject, Date duedate, Long assignedtoid, Long assignedbyid, Integer tasktype, //
			Integer percentagecompleted, Long contactid, Long candidateid, String description, Boolean isprivate) throws Exception {
		//
		// Parameters Check
		parametresCheck(subject, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid);
		//
		StringBuffer message = new StringBuffer();
		Date eventDate1;
		Date eventDate2;
		long eventId = 0;
		String candidateName = null;
		String result = null;
		//
		//
		eventDate1 = duedate;
		Integer duration = 45;
		//
		eventDate2 = new Date(eventDate1.getTime() + duration * 60 * 1000);
		//
		// Find Candidate Name and generate RESULT column data
		if (candidateid != null && candidateid > 0) {
			Candidate candidate = candidateDao.getCandidate(jobDivaSession, candidateid);
			if (candidate != null) {
				candidateName = candidate.getFirstName() + " " + candidate.getLastName();
				result = "<can>candidateid=" + candidateid + "&docids=-1&teamid=" + jobDivaSession.getTeamId() + "</can><nam>" + candidateName + "</nam>";
			} else {
				message.append("Warning: invalid candidate ID(" + candidateid + "). Ignored. ");
				candidateid = 0L;
			}
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// Find next available Event ID
		String sql = "SELECT eventid.nextval as EVENTID FROM dual";
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("EVENTID");
			}
		});
		//
		if (list != null && list.size() > 0) {
			eventId = list.get(0);
		} else {
			throw new Exception("Fail to find next event ID. ");
		}
		//
		Integer task_completed;
		Integer optional;
		if (percentagecompleted != null) {
			task_completed = percentagecompleted;
			optional = percentagecompleted == 100 ? 4 : 2;
		} else {
			task_completed = 0;
			optional = 2;
		}
		//
		String sqlInsert = "insert into TEVENT "//
				+ " ( id, recruiterid, eventdate, dtend, eventtype, notes, priority, duration, reminder, status, result, title, " //
				+ " teamid, optional, private, leadtime, lagtime, timezone, creatorid, task_type, completed_pct, candidateid, location) " //
				+ " values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?) ";
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(eventId);
		paramList.add(assignedtoid != null ? assignedtoid : jobDivaSession.getRecruiterId());
		paramList.add(eventDate1);
		paramList.add(eventDate2);
		paramList.add(0);
		paramList.add(description);
		paramList.add(0);
		paramList.add(duration);
		paramList.add(0);
		paramList.add(null);
		paramList.add(result);
		paramList.add(subject);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(optional);
		paramList.add(isprivate != null && isprivate ? 1 : 0);
		paramList.add(null);
		paramList.add(null);
		paramList.add(null);
		paramList.add(assignedbyid != null ? assignedbyid : assignedtoid);
		paramList.add(tasktype != null ? tasktype : 0);
		paramList.add(task_completed);
		paramList.add(candidateid);
		paramList.add(null);
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlInsert, parameters);
		//
		if (contactid != null) {
			sqlInsert = "insert into TEVENT_CONTACTS(eventid, contactid, teamid) values(?,?,?) ";
			parameters = new Object[] { eventId, contactid, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsert, parameters);
		}
		//
		return true;
	}
}
