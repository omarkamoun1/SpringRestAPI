package com.jobdiva.api.dao.candidate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.DateUtils;

@Component
public class CandidateAvailabilityDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao			candidateDao;
	//
	@Autowired
	CandidateNoteDao		candidateNoteDao;
	//
	@Autowired
	CandidateUnreachableDao	candidateUnreachableDao;
	
	public Boolean updateCandidateAvailability(JobDivaSession jobDivaSession, Long candidateid, Boolean availablenow, //
			Boolean unavailableindef, Boolean unavailableuntil, Date unavailableuntildate, String reason) throws Exception {
		//
		//
		int optionsSet = 0;
		if (availablenow != null && availablenow)
			optionsSet++;
		if (unavailableindef != null && unavailableindef)
			optionsSet++;
		if (unavailableuntil != null && unavailableuntil)
			optionsSet++;
		if (optionsSet != 1)
			throw new Exception("Error: One and only option in (\'availablenow\', \'unavailableindef\', \'unavailableuntil\') should be set to update candidate availability. \r\n");
		//
		if (unavailableuntildate != null) {
			if ((availablenow != null && availablenow) || (unavailableindef != null && unavailableindef))
				throw new Exception("Error: No date should be set for either \'availablenow\' or \'unavailableindef\'. \r\n ");
			//
			if ((unavailableuntil != null && unavailableuntil) && unavailableuntildate.before(new Date()))
				throw new Exception("Error: Please choose a date in the future. \r\n");
		}
		//
		if ((availablenow == null || !availablenow) && (isEmpty(reason) || reason.length() == 0))
			throw new Exception("Error: Please provide an explanation for this change. \r\n");
		//
		//
		//
		Boolean existCandidate = candidateDao.existCandidate(jobDivaSession, candidateid);
		if (!existCandidate) {
			throw new Exception("Error: Candidate(" + candidateid + ") is not found.");
		}
		//
		//
		StringBuffer notestr = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Timestamp currentdate = new Timestamp(System.currentTimeMillis());
		//
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Timestamp midnight = new Timestamp(now.getTimeInMillis());
		//
		if (availablenow != null) {
			notestr.append("Marked candidate available as of ");
			notestr.append(sdf.format(midnight) + " \n<br>");
		} else if (unavailableindef != null) {
			notestr.append("Marked candidate unavailable indefinitely \n<br>");
		} else if (unavailableuntil != null) {
			notestr.append("Marked candidate unavailable ");
			if (unavailableuntildate != null) {
				notestr.append(" until " + unavailableuntildate);
			}
			notestr.append(" \n<br>");
		}
		if (isNotEmpty(reason))
			notestr.append("Reason: " + reason + " \n<br>");
		//
		// insert candidate note
		candidateNoteDao.addContactNote(candidateid, 0, jobDivaSession.getRecruiterId(), 0L, currentdate, jobDivaSession.getTeamId(), 3, notestr.toString());
		//
		Date dateAvailable = null;
		int flag = 4;
		if (availablenow != null && availablenow)
			dateAvailable = midnight;
		else if (unavailableuntil != null && unavailableuntil) {
			if (unavailableuntildate != null)
				dateAvailable = unavailableuntildate;
			else
				dateAvailable = new Timestamp(sdf.parse("12/31/1969").getTime());
		} else if (unavailableindef != null && unavailableindef) {
			flag = 2;
		}
		//
		candidateUnreachableDao.insertUpdateCandidateUnreachable(jobDivaSession, candidateid, flag, dateAvailable, new Timestamp(System.currentTimeMillis()));
		//
		//
		// update tassignment
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("DATEAVAILABLE");
		paramList.add(dateAvailable);
		//
		String sqlUpdate = "UPDATE TASSIGNMENT SET  " + sqlUpdateFields(fields) + " WHERE CANDIDATEID = ? and RECRUITER_TEAMID = ? ";
		paramList.add(candidateid);
		paramList.add(jobDivaSession.getTeamId());
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlUpdate, parameters);
		//
		//
		long teamid = jobDivaSession.getTeamId();
		if (teamid == 219 || teamid == 22 || teamid == 161) {
			sqlUpdate = "update TASSIGNMENT_TERM set DATEAVAILABLE = ? where CANDIDATEID = ? and RECRUITER_TEAMID = ? ";
			//
			paramList = new ArrayList<Object>();
			paramList.add(dateAvailable);
			paramList.add(candidateid);
			paramList.add(teamid);
			if (teamid == 22) {
				sqlUpdate += "and rfqid in (select id from trfq where teamid = ? and divisionid=3461) ";
				paramList.add(teamid);
			}
			parameters = paramList.toArray();
			jdbcTemplate.update(sqlUpdate, parameters);
		}
		//
		// update TCANDIDATEDOCUMENT_HEADER
		if (dateAvailable != null && flag != 2) {
			//
			sqlUpdate = "update TCANDIDATEDOCUMENT_HEADER set DATECREATED = ? where TEAMID = ? and CANDIDATEID = ?";
			//
			paramList = new ArrayList<Object>();
			paramList.add(availablenow != null && availablenow ? DateUtils.trimTime(dateAvailable) : dateAvailable);
			paramList.add(teamid);
			paramList.add(candidateid);
			parameters = paramList.toArray();
			jdbcTemplate.update(sqlUpdate, parameters);
		}
		//
		return true;
	}
}
