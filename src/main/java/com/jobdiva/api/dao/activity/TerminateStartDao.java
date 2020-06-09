package com.jobdiva.api.dao.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.candidate.CandidateData;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.shared.CacheServer_Stub;
import com.axelon.shared.NamedServer;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;
import com.jobdiva.api.utils.DateUtils;

@Component
public class TerminateStartDao extends AbstractActivityDao {
	
	@Autowired
	ActivityDao activityDao;
	
	private void checkTerminateReason(JobDivaSession jobDivaSession, Integer reason) throws Exception {
		String sql = "SELECT id, description " //
				+ " FROM treason_termination " //
				+ " WHERE teamid = ? " //
				+ " AND active = 1";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		//
		Map<Integer, String> terminationReasonMap = new HashMap<Integer, String>();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer reasonId = rs.getInt("id");
				String description = rs.getString("description");
				terminationReasonMap.put(reasonId, description);
				return null;
			}
		});
		//
		if (!terminationReasonMap.containsKey(reason))
			throw new Exception("Error: Invalid termination reason " + reason + ". Valid reason(s): " + terminationReasonMap);
	}
	
	private void checkPerfomanceCode(JobDivaSession jobDivaSession, Integer performancecode) throws Exception {
		String sql = "SELECT id, code, code_description " //
				+ " FROM tperformance_codes_termination " //
				+ " WHERE teamid = ? " //
				+ "AND active = 1";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		//
		Map<Integer, String> perfCodeMap = new HashMap<Integer, String>();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer id = rs.getInt("id");
				String code = "";
				if (rs.getString("code") != null)
					code += rs.getString("code");
				if (rs.getString("code_description") != null)
					code += ("- " + rs.getString("code_description"));
				perfCodeMap.put(id, code);
				return null;
			}
		});
		if (!perfCodeMap.containsKey(performancecode))
			throw new Exception("Error: Invalid performance code " + performancecode + ". Valid code(s): " + perfCodeMap);
	}
	
	private void checkTerimateDate(JobDivaSession jobDivaSession, Date dateterminated, Long candidateId) throws Exception {
		String sql = " SELECT MAX(b.tdate) maxEndDate " //
				+ " FROM temployee_wed a, temployee_timesheet b " //
				+ "	WHERE a.approved >= 0 AND a.employeeid = b.employeeid AND a.recruiter_teamid = b.recruiter_teamid " //
				+ "	AND a.billing_recid = b.billing_recid AND a.weekendingdate = b.weekending " //
				+ "	AND b.projectid = 0 AND b.hoursworked > 0 AND a.employeeid = ? AND a.recruiter_teamid = ? " //
				+ "	AND a.billing_recid IN (	" //
				+ "							SELECT a.recid " //
				+ "							FROM temployee_billingrecord a LEFT OUTER JOIN temployee_billingrecord b " //
				+ "							ON a.employeeid = b.employeeid AND a.start_date < b.start_date " //
				+ "							WHERE a.employeeid = ? AND a.recruiter_teamid = ? AND b.employeeid is null " //
				+ "						  ) ";
		Object[] params = new Object[] { candidateId, jobDivaSession.getTeamId(), candidateId, jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Date> list = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDate("maxEndDate");
			}
		});
		//
		//
		if (list != null && list.size() > 0) {
			Date maxEndDate = list.get(0);
			if (maxEndDate != null && dateterminated.compareTo(maxEndDate) < 0)
				throw new Exception("The termination date of " + dateterminated != null ? dateterminated.toString() : "NULL" + " cannot be entered because this person has timesheets until " + simpleDateFormat.format(maxEndDate));
		}
	}
	
	private void updateActivityStartAssignment(JobDivaSession jobDivaSession, Long startid, Integer reason, Integer performancecode, String notes, Boolean markaspastemployee, Boolean markasavailable, Date dateterminated) {
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("date_terminate_record");
		paramList.add(new Date());
		//
		fields.add("dateterminated");
		paramList.add(dateterminated);
		//
		fields.add("reasonterminated");
		paramList.add(reason);
		//
		fields.add("terminatorid");
		paramList.add(jobDivaSession.getRecruiterId());
		//
		fields.add("noteterminated");
		paramList.add(notes);
		//
		fields.add("LASTEMPLOYMENT");
		paramList.add(0);
		//
		fields.add("mark_employee_status");
		paramList.add(markaspastemployee != null && markaspastemployee ? 1 : 0);
		//
		fields.add("mark_employee_availability");
		paramList.add(markasavailable ? 1 : 0);
		//
		if (performancecode != null) {
			fields.add("performancecode");
			paramList.add(performancecode);
		}
		String sqlUpdate = " UPDATE tinterviewschedule SET " + sqlUpdateFields(fields)//
				+ " WHERE  " //
				+ " id = ? " //
				+ " and candidate_teamid = ? "; //
		paramList.add(startid);
		paramList.add(jobDivaSession.getTeamId());
		//
		Object[] parameters = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, parameters);
	}
	
	private void checkIfAssignementsAfterTerminationDate(JobDivaSession jobDivaSession, Long jobId, Long candidateId, Date dateterminated) throws Exception {
		String sql = " SELECT count(*) as CNT " //
				+ " FROM zps_schedule " //
				+ " WHERE teamid = ? " //
				+ " AND candidateid = ? " //
				+ " AND scheduletype = 0 AND " //
				+ " jobscheduleid IN (" //
				+ "     SELECT id " //
				+ "     FROM zps_schedule " //
				+ "		WHERE teamid = ? " //
				+ "		AND rfqid = ? " //
				+ "		AND scheduletype = 3" //
				+ "  ) " //
				+ " AND dtend > ? ";
		//
		//
		Timestamp dateTerminateAdjusted = new Timestamp(DateUtils.getEndTimeSameDaySpecial(dateterminated, null).getTime());
		Object[] params = new Object[] { jobDivaSession.getTeamId(), candidateId, jobDivaSession.getTeamId(), jobId, dateTerminateAdjusted };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("CNT");
			}
		});
		if (list != null && list.size() > 0) {
			Integer count = list.get(0);
			if (count > 0)
				throw new Exception("There are assignments later than termination date");
		}
	}
	
	public Boolean terminateStart(JobDivaSession jobDivaSession, Long startid, Long candidateid, Long jobId, Date terminationdate, Integer reason, Integer performancecode, String notes, Boolean markaspastemployee, Boolean markasavailable)
			throws Exception {
		// begins...");
		try {
			//
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			//
			terminationdate = terminationdate != null ? terminationdate : new Date();
			//
			Activity activity = activityDao.getActivity(jobDivaSession, startid);
			//
			// checking
			if (activity == null || activity.getRecruiterTeamId() != jobDivaSession.getTeamId())
				throw new Exception("Error: Activity " + startid + " is not found.");
			//
			if (candidateid == null)
				candidateid = activity.getCandidateId();
			//
			checkTerminateReason(jobDivaSession, reason);
			//
			// "Verify performance code..." ;
			checkPerfomanceCode(jobDivaSession, performancecode);
			//
			// Verify termination date is after timesheet max end date if any;
			checkTerimateDate(jobDivaSession, terminationdate, candidateid);
			//
			// Update activity (start/assignment) record...;
			updateActivityStartAssignment(jobDivaSession, startid, reason, performancecode, notes, markaspastemployee, markasavailable, terminationdate);
			//
			// Test if assignment(s) after termination date
			checkIfAssignementsAfterTerminationDate(jobDivaSession, jobId, candidateid, terminationdate);
			//
			//
			if (markaspastemployee != null && markaspastemployee) {
				// Mark PAST EMPLOYEE...");
				String sqlUpdate = "UPDATE tcandidate_category " //
						+ " SET dcatid = 2, " //
						+ " dirty = 1, " //
						+ " recruiterid = ? " //
						+ "	WHERE candidateid = ? AND teamid = ? AND catid = 1 ";
				Object[] parameters = new Object[] { jobDivaSession.getRecruiterId(), candidateid, jobDivaSession.getTeamId() };
				jdbcTemplate.update(sqlUpdate, parameters);
			}
			// Adjust the most recent approved billing and pay assignment for
			// this start on this job
			long recid = 0;
			long customerid = 0;
			if (customerid == 0) {
				// Select recid from billing record...");
				String sql = "	SELECT recid FROM temployee_billingrecord " //
						+ "	WHERE recruiter_teamid = ? "//
						+ " AND employeeid = ? " //
						+ " AND rfqid = ? " //
						+ " AND interviewid = ? " //
						+ " AND nvl(closed, 0) = 0 " //
						+ "	ORDER BY start_date DESC ";
				Object[] parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobId, startid };
				//
				List<Long> list = jdbcTemplate.query(sql, parameters, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong("recid");
					}
				});
				if (list != null && list.size() > 0)
					recid = list.get(0);
			} else {
				recid = customerid;
			}
			//
			//
			if (recid > 0) {
				String sqlUpdate = "	UPDATE temployee_billingrecord " //
						+ "	SET datecreated = sysdate, " //
						+ " actualend = 1, " //
						+ " created_by = ?, " //
						+ " end_date = ? " //
						+ "	WHERE recid = ? " //
						+ " AND recruiter_teamid = ? " //
						+ " AND employeeid = ? ";
				Object[] parameters = new Object[] { jobDivaSession.getRecruiterId(), terminationdate, recid, jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlUpdate, parameters);
			}
			//
			//
			//
			recid = 0;
			// Select recid from salary record...
			String sql = "	SELECT recid " + " FROM temployee_salaryrecord " //
					+ "	WHERE recruiter_teamid = ? " + " AND employeeid = ? " + " AND rfqid = ? " + " AND interviewid = ? " + " AND nvl(closed, 0) = 0 " //
					+ "	ORDER BY effective_date DESC ";
			Object[] parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobId, startid };
			//
			List<Long> list = jdbcTemplate.query(sql, parameters, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("recid");
				}
			});
			if (list != null && list.size() > 0) {
				recid = list.get(0);
			}
			//
			//
			if (recid > 0) {
				String sqlUpdate = "UPDATE temployee_salaryrecord " //
						+ "	SET datecreated = sysdate, " //
						+ " created_by = ?, " //
						+ " end_date = ? " //
						+ "	WHERE recid = ? " //
						+ " AND recruiter_teamid = ? " //
						+ " AND employeeid = ? ";
				parameters = new Object[] { jobDivaSession.getRecruiterId(), terminationdate, recid, jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlUpdate, parameters);
			}
			//
			//
			// Adjust the LastEmployemnt flag: used in employee list report.
			String sqlUpdate = "UPDATE tinterviewschedule " //
					+ "	SET lastemployment = 0 " //
					+ "	WHERE recruiter_teamid = ? " //
					+ " AND candidateid = ? " //
					+ " AND datehired is not null " //
					+ " AND lastemployment = 1";
			parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
			jdbcTemplate.update(sqlUpdate, parameters);
			//
			int rowsupdated_1 = 0;
			int rowsupdated_2 = 0;
			//
			if (jobDivaSession.getTeamId() == 219 || jobDivaSession.getTeamId() == 22 || jobDivaSession.getTeamId() == 161 || jobDivaSession.getTeamId() == 71) {
				// Special team 219, 22, 161, 71...");
				sqlUpdate = "	UPDATE tinterviewschedule a " //
						+ "	SET a.lastemployment = 1 " //
						+ "	WHERE a.RECRUITER_TEAMID = ? " //
						+ " AND a.CANDIDATEID = ? " //
						+ " AND a.DATEHIRED is not null " //
						+ " AND a.DATETERMINATED is null " //
						+ " AND (a.REASONTERMINATED is null OR a.REASONTERMINATED = 0) ";
				//
				if (jobDivaSession.getTeamId() == 22)
					sqlUpdate += " AND EXISTS (SELECT 1 FROM trfq b WHERE b.id = a.rfqid AND b.divisionid IN (3461,7876,7877,7878)) ";
				//
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
				rowsupdated_1 = jdbcTemplate.update(sqlUpdate, parameters);
				//
				if (jobDivaSession.getTeamId() == 22) {
					sqlUpdate = "	UPDATE tinterviewschedule a "//
							+ " SET a.lastemployment = 1 "//
							+ "	WHERE a.id = (" //
							+ "					SELECT id FROM (" //
							+ "									SELECT id FROM tinterviewschedule " //
							+ "									WHERE recruiter_teamid = ? AND candidateid = ? AND datehired is not null " //
							+ "									ORDER BY datehired DESC, id DESC) "//
							+ "					WHERE rownum = 1) " //
							+ " AND NOT EXISTS (" //
							+ "					SELECT 1 FROM trfq b " //
							+ "					WHERE b.id = a.rfqid AND b.divisionid IN (3461,7876,7877,7878)) "//
							+ "	AND nvl(a.lastemployment,0) = 0 ";
					//
					parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
					rowsupdated_2 = jdbcTemplate.update(sqlUpdate, parameters);
				}
			}
			//
			if (rowsupdated_1 == 0 && rowsupdated_2 == 0) {
				sqlUpdate = "	UPDATE tinterviewschedule " //
						+ " SET lastemployment = 1 " //
						+ "	WHERE id = (" //
						+ "				SELECT id FROM (" //
						+ "								SELECT id FROM tinterviewschedule "//
						+ "								WHERE recruiter_teamid = ? AND candidateid = ? AND datehired is not null " //
						+ "								ORDER BY datehired DESC, id DESC) " //
						+ "				WHERE rownum = 1) ";
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlUpdate, parameters);
			}
			//
			// Update TASSIGNMENT table...");
			String sqlDelete = " DELETE FROM tassignment WHERE candidateid = ? AND recruiter_teamid = ? ";
			parameters = new Object[] { candidateid, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlDelete, parameters);
			//
			//
			int tassignment_updated = 0;
			if (jobDivaSession.getTeamId() == 219 || jobDivaSession.getTeamId() == 22 || jobDivaSession.getTeamId() == 161 || jobDivaSession.getTeamId() == 71) {
				// update TASSIGNMENT table for special
				// teams 219, 22, 161, 71...");
				String sqlInsert = " INSERT INTO tassignment (id, candidateid, dcatid, rfqid, recruiterid, recruiter_teamid, ";
				sqlInsert += " customerid, companyid, datehired, date_ended, dateavailable, dateterminated) ";
				sqlInsert += " (SELECT t1.id, t0.candidateid, t0.dcatid, t1.rfqid, t1.recruiterid,t0.teamid, ";
				sqlInsert += " 	t4.customerid, t4.companyid, t1.datehired,t1.date_ended, COALESCE(t2.dateavailable, t3.dateavailable) dateavailable, t1.dateterminated ";
				sqlInsert += " 	FROM ";
				sqlInsert += " 	tcandidate_category t0, ";
				sqlInsert += " 	tinterviewschedule t1, ";
				sqlInsert += " 	tcandidate_unreachable t2, ";
				sqlInsert += " 	tcandidate_unreachable_archive t3, ";
				sqlInsert += " 	trfq t4 ";
				sqlInsert += " 	WHERE t0.teamid = ? AND t0.candidateid = ? AND t0.catid = 1 AND t0.dirty <> 2 ";
				if (jobDivaSession.getTeamId() == 22)
					sqlInsert += " AND t4.divisionid IN (3461,7876,7877,7878) ";
				sqlInsert += " 	AND t0.candidateid = t1.candidateid(+) AND t0.teamid = t1.recruiter_teamid(+) ";
				sqlInsert += " 	AND t0.candidateid = t2.candidateid(+) AND t0.teamid = t2.teamid(+) ";
				sqlInsert += " 	AND t0.candidateid = t3.candidateid(+) AND t0.teamid = t3.teamid(+) ";
				sqlInsert += " 	AND t1.rfqid = t4.id (+) ";
				sqlInsert += " 	AND (t1.id is null OR (t1.datehired is not null AND t1.DATETERMINATED is null AND (t1.REASONTERMINATED is null OR t1.REASONTERMINATED=0))) ";
				sqlInsert += ") ";
				//
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlUpdate, parameters);
				//
				if (jobDivaSession.getTeamId() == 22) {
					sqlInsert = " INSERT INTO tassignment (id, candidateid, dcatid, rfqid, recruiterid, recruiter_teamid, ";
					sqlInsert += " customerid, companyid, datehired, date_ended, dateavailable, dateterminated) ";
					sqlInsert += " (SELECT t1.id, t0.candidateid, t0.dcatid, t1.rfqid, t1.recruiterid, t0.teamid, ";
					sqlInsert += " t4.customerid, t4.companyid, t1.datehired, t1.date_ended, COALESCE(t2.dateavailable, t3.dateavailable) dateavailable, t1.dateterminated ";
					sqlInsert += " FROM ";
					sqlInsert += " tcandidate_category t0, ";
					sqlInsert += " tinterviewschedule t1, ";
					sqlInsert += " tcandidate_unreachable t2, ";
					sqlInsert += " tcandidate_unreachable_archive t3, ";
					sqlInsert += " trfq t4 ";
					sqlInsert += " WHERE t0.teamid = ? AND t0.candidateid = ? AND t0.catid = 1 AND t0.dirty <> 2 ";
					sqlInsert += " AND (t1.id = (SELECT id FROM ( ";
					sqlInsert += " 								SELECT id FROM tinterviewschedule ";
					sqlInsert += "								WHERE recruiter_teamid=? AND candidateid=? AND datehired is not null ";
					sqlInsert += "								ORDER BY datehired DESC, id DESC ";
					sqlInsert += "								) ";
					sqlInsert += "				WHERE rownum = 1";
					sqlInsert += "				)";
					sqlInsert += "	  )";
					sqlInsert += " AND t0.candidateid = t1.candidateid(+) AND t0.teamid = t1.recruiter_teamid(+) ";
					sqlInsert += " AND t0.candidateid = t2.candidateid(+) AND t0.teamid = t2.teamid(+) ";
					sqlInsert += " AND t0.candidateid = t3.candidateid(+) AND t0.teamid = t3.teamid(+) ";
					sqlInsert += " AND t1.rfqid = t4.id (+) ";
					sqlInsert += " AND t4.divisionid NOT IN (3461,7876,7877,7878)) ";
					//
					parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobDivaSession.getTeamId(), candidateid };
					jdbcTemplate.update(sqlInsert, parameters);
				}
				//
				//
				sql = "SELECT * FROM tassignment WHERE recruiter_teamid = ? AND candidateid = ?";
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid };
				//
				List<Boolean> listBoolean = jdbcTemplate.query(sql, parameters, new RowMapper<Boolean>() {
					
					@Override
					public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
						return true;
					}
				});
				if (listBoolean != null && listBoolean.size() > 0) {
					tassignment_updated = 1;
				}
			}
			//
			//
			if (tassignment_updated == 0) {
				String sqlInsert = " INSERT INTO tassignment (id, candidateid, dcatid, rfqid, recruiterid, recruiter_teamid, ";
				sqlInsert += " customerid, companyid, datehired, date_ended, dateavailable, dateterminated) ";
				sqlInsert += " (SELECT t1.id, t0.candidateid, t0.dcatid, t1.rfqid, t1.recruiterid, t0.teamid, ";
				sqlInsert += " t4.customerid, t4.companyid, t1.datehired,t1.date_ended, COALESCE(t2.dateavailable, t3.dateavailable) dateavailable, t1.dateterminated ";
				sqlInsert += " FROM ";
				sqlInsert += " tcandidate_category t0, ";
				sqlInsert += " tinterviewschedule t1, ";
				sqlInsert += " tcandidate_unreachable t2, ";
				sqlInsert += " tcandidate_unreachable_archive t3, ";
				sqlInsert += " trfq t4 ";
				sqlInsert += " WHERE t0.teamid = ? AND t0.candidateid = ? AND t0.catid = 1 AND t0.dirty <> 2 ";
				sqlInsert += " AND (t1.id is null OR t1.id = (";
				sqlInsert += " 								SELECT id FROM (";
				sqlInsert += "												SELECT id FROM tinterviewschedule ";
				sqlInsert += "												WHERE recruiter_teamid = ? AND candidateid = ? AND datehired is not null ";
				sqlInsert += "												ORDER BY datehired DESC, id DESC";
				sqlInsert += "												) ";
				sqlInsert += "								WHERE rownum = 1";
				sqlInsert += "								)";
				sqlInsert += "	  ) ";
				sqlInsert += " AND t0.candidateid = t1.candidateid(+) AND t0.teamid = t1.recruiter_teamid(+) ";
				sqlInsert += " AND t0.candidateid = t2.candidateid(+) AND t0.teamid = t2.teamid(+) ";
				sqlInsert += " AND t0.candidateid = t3.candidateid(+) AND t0.teamid = t3.teamid(+) ";
				sqlInsert += " AND t1.rfqid = t4.id (+)) ";
				//
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlInsert, parameters);
				//
			}
			//
			String sqlInsert = "INSERT INTO tassignment (id, candidateid, dcatid, rfqid, recruiterid, recruiter_teamid, ";
			sqlInsert += "customerid, companyid, datehired, date_ended, dateavailable,dateterminated) ";
			sqlInsert += "( ";
			sqlInsert += "SELECT distinct null, t0.candidateid, t0.dcatid, null,null,t0.teamid, null, null, null,null, COALESCE(t2.dateavailable, t3.dateavailable) dateavailable, null ";
			sqlInsert += "FROM ";
			sqlInsert += "tcandidate_category t0, ";
			sqlInsert += "tcandidate_unreachable t2, ";
			sqlInsert += "tcandidate_unreachable_archive t3 ";
			sqlInsert += "WHERE t0.teamid = ? AND t0.candidateid = ? AND t0.catid = 1 AND t0.dirty <> 2 ";
			sqlInsert += "AND EXISTS (SELECT candidateid FROM tinterviewschedule WHERE recruiter_teamid = ? AND candidateid = ?) ";
			sqlInsert += "AND NOT EXISTS (SELECT candidateid FROM tinterviewschedule WHERE recruiter_teamid=? AND candidateid=? AND datehired is not null) ";
			sqlInsert += "AND t0.candidateid = t2.candidateid(+) AND t0.teamid = t2.teamid(+) ";
			sqlInsert += "AND t0.candidateid = t3.candidateid(+) AND t0.teamid = t3.teamid(+) ";
			sqlInsert += ") ";
			//
			parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobDivaSession.getTeamId(), candidateid, jobDivaSession.getTeamId(), candidateid };
			jdbcTemplate.update(sqlInsert, parameters);
			//
			//
			if (jobDivaSession.getTeamId() == 219 || jobDivaSession.getTeamId() == 22 || jobDivaSession.getTeamId() == 161 || jobDivaSession.getTeamId() == 71) {
				//
				sqlDelete = "DELETE FROM tassignment_term a WHERE a.candidateid = ? AND a.recruiter_teamid = ? ";
				if (jobDivaSession.getTeamId() == 22)
					sqlDelete += " AND EXISTS (SELECT 1 FROM trfq b WHERE b.id = a.rfqid AND b.divisionid IN (3461,7876,7877,7878)) ";
				//
				parameters = new Object[] { candidateid, jobDivaSession.getTeamId() };
				jdbcTemplate.update(sqlDelete, parameters);
				//
				//
				sqlInsert = "INSERT INTO tassignment_term (id, candidateid, dcatid, rfqid, recruiterid, recruiter_teamid, ";
				sqlInsert += "customerid, companyid, datehired, date_ended, dateavailable, dateterminated) ";
				sqlInsert += "(SELECT t1.id, t0.candidateid, t0.dcatid, t1.rfqid, t1.recruiterid, t0.teamid, ";
				sqlInsert += "t4.customerid, t4.companyid, t1.datehired,t1.date_ended, COALESCE(t2.dateavailable, t3.dateavailable) dateavailable, t1.dateterminated ";
				sqlInsert += "FROM ";
				sqlInsert += "tcandidate_category t0, ";
				sqlInsert += "tinterviewschedule t1, ";
				sqlInsert += "tcandidate_unreachable t2, ";
				sqlInsert += "tcandidate_unreachable_archive t3, ";
				sqlInsert += "trfq t4 ";
				sqlInsert += "WHERE t0.teamid = ? AND t0.candidateid = ? AND t0.catid = 1 AND t0.dirty <> 2 ";
				sqlInsert += "AND t0.candidateid = t1.candidateid AND t0.teamid = t1.recruiter_teamid ";
				sqlInsert += "AND t0.candidateid = t2.candidateid(+) AND t0.teamid = t2.teamid(+) ";
				sqlInsert += "AND t0.candidateid = t3.candidateid(+) AND t0.teamid = t3.teamid(+) ";
				sqlInsert += "AND t1.rfqid = t4.id (+) ";
				sqlInsert += "AND t1.datehired is not null AND t1.dateterminated is not null ";
				sqlInsert += "AND t1.dateterminated >= add_months(sysdate, -3)  ";
				sqlInsert += "AND t1.id NOT IN (SELECT id FROM tassignment WHERE recruiter_teamid = ? AND candidateid = ? AND id > 0)) ";
				//
				parameters = new Object[] { jobDivaSession.getTeamId(), candidateid, jobDivaSession.getTeamId(), candidateid };
				jdbcTemplate.update(sqlInsert, parameters);
			}
			//
			//
			String str_reason = "";
			//
			str_reason = "Terminated as of " + terminationdate + ". ";
			if (notes != null)
				str_reason += "<br>Notes: " + terminationdate + ".";
			Long noteid = null;
			//
			sql = "SELECT CANDIDATENOTEID.nextval  AS noteid  FROM dual";
			List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("noteid");
				}
			});
			if (listLong != null && listLong.size() > 0) {
				noteid = listLong.get(0);
			}
			//
			//
			sqlInsert = "	INSERT INTO tcandidatenotes (candidateid, noteid, recruiterid, rfqid, teamid, recruiter_teamid," //
					+ "	note_clob, datecreated, type, auto, shared )" //
					+ "	VALUES (?,?,?,?,?,?,?,sysdate,-1,3,0)";
			//
			parameters = new Object[] { candidateid, noteid, jobDivaSession.getRecruiterId(), jobId, jobDivaSession.getTeamId(), jobDivaSession.getTeamId(), str_reason };
			jdbcTemplate.update(sqlInsert, parameters);
			//
			//
			// send termination email...
			// Thread termination...
			if (markasavailable != null && markasavailable) {
				// Update unreachability...");
				try {
					CandidateData candData2 = new CandidateData(candidateid);
					candData2.teamid = jobDivaSession.getTeamId();
					candData2.action_code = 1; // get unreachability information
					//
					ServletRequestData data = new ServletRequestData(0L, candData2);
					//
					Object retObj2 = ServletTransporter.callServlet(getUnreachableCandidateServlet(), data);
					candData2 = (CandidateData) retObj2;
					int oldflag = 0;
					if ((candData2.znoletter & 0x2) == 0x2)
						oldflag = 2;
					if ((candData2.znoletter & 0x4) == 0x4)
						oldflag = 4;
					//
					//
					CandidateData candData = new CandidateData(candidateid);
					candData.teamid = jobDivaSession.getTeamId();
					candData.recruiterid = jobDivaSession.getRecruiterId();
					candData.znoletter = 4;
					candData.emailmerge_flag = candData2.emailmerge_flag;
					candData.date_received = jobDivaSession.getRecruiterId(); // used
																				// to
																				// transport
					// the recruiter ID
					// in CandidateData
					candData.action_code = 2; // save unreachability information
					candData.date_available = terminationdate.getTime();
					candData.owner_teamid = jobDivaSession.getTeamId();
					candData.owner_docid = 1;
					candData.dateuntil_recruiter = candData2.dateuntil_recruiter;
					candData.dateuntil_candidate = candData2.dateuntil_candidate;
					candData.confidential = -1;
					String tempreason = "";
					if (candData.date_available != candData2.date_available) {
						tempreason += "Marked candidate unavailable ";
						if (candData.date_available != 0)
							tempreason += "until " + simpleDateFormat.format(new Date(candData.date_available)) + "\n<br>";
						else
							tempreason += "\n<br>";
						tempreason += "Reason: End Date\n<br>";
					}
					candData.password = tempreason;
					Object retObj10 = null;
					if (oldflag != 4 || candData.date_available != candData2.date_available) {
						try {
							Object obj = new com.axelon.oc4j.ServletRequestData(0L, candData);
							retObj10 = ServletTransporter.callServlet(getSaveUnreachabilityServlet(), obj);
							Boolean retBool = (Boolean) retObj10;
							if (retBool.booleanValue() == true) {
								// inform SearchResultServer
								String envType = getEnvironmentType();
								@SuppressWarnings("unused")
								CacheServer_Stub cacheserver = (CacheServer_Stub) NamedServer.findService("CacheServer", envType);
								NamedServer.addUnreachableCandidate(candidateid, jobDivaSession.getTeamId(), terminationdate.getTime(), envType);
								//
								// cache server UnreachableCandidate added
							}
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}
		return true;
	}
}
