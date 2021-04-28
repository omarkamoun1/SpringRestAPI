package com.jobdiva.api.dao.activity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class ActivityDao extends AbstractActivityDao {
	
	@Autowired
	CandidateDao	candidateDao;
	//
	@Autowired
	JobDao			jobDao;
	
	public Activity getActivity(JobDivaSession jobDivaSession, Long activityId) {
		LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
		//
		String sql = " SELECT a.*, " //
				+ " b.FIRSTNAME, b.LASTNAME, b.EMAIL, b.PHONE_TYPES, b.HOMEPHONE, b.HOMEPHONE_EXT, b.WORKPHONE, b.WORKPHONE_EXT, b.CELLPHONE, b.CELLPHONE_EXT, b.FAX, b.FAX_EXT ," //
				+ " b.ADDRESS1, b.ADDRESS2, b.CITY, b.STATE, b.ZIPCODE, " //
				+ " c.FIRSTNAME as RecruiterFIRSTNAME, c.LASTNAME as RecruiterLASTNAME " //
				+ " FROM  TINTERVIEWSCHEDULE a " //
				+ " LEFT JOIN TCANDIDATE b ON b.ID = a.CANDIDATEID AND b.TEAMID = :teamid  " //
				+ " LEFT JOIN TRECRUITER c ON c.ID = a.RECRUITERID " //
				+ " where   " //
				+ " a.ID = :id " //
				+ " CANDIDATE_TEAMID = :teamid " //
				+ " and RECRUITER_TEAMID = :teamid ";
		//
		paramList.put("id", activityId);
		paramList.put("teamid", jobDivaSession.getTeamId());
		//
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
		//
		List<Activity> list = namedParameterJdbcTemplate.query(sql, paramList, new ActivityRowMapper(this));
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public List<Activity> searchStart(JobDivaSession jobDivaSession, Long jobId, String optionalref, String jobdivaref, Long candidateid, String candidatefirstname, String candidatelastname, String candidateemail) {
		//
		LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
		//
		String sql = " SELECT a.*, " //
				+ " b.FIRSTNAME, b.LASTNAME, b.EMAIL, b.PHONE_TYPES, b.HOMEPHONE, b.HOMEPHONE_EXT, b.WORKPHONE, b.WORKPHONE_EXT, b.CELLPHONE, b.CELLPHONE_EXT, b.FAX, b.FAX_EXT ," //
				+ " b.ADDRESS1, b.ADDRESS2, b.CITY, b.STATE, b.ZIPCODE, " //
				+ " c.FIRSTNAME as RecruiterFIRSTNAME, c.LASTNAME as RecruiterLASTNAME " //
				+ " FROM  TINTERVIEWSCHEDULE a " //
				+ " LEFT JOIN TCANDIDATE b ON b.ID = a.CANDIDATEID AND b.TEAMID = :teamid  " //
				+ " LEFT JOIN TRECRUITER c ON c.ID = a.RECRUITERID " //
				+ " where " //
				+ " CANDIDATE_TEAMID = :teamid " //
				+ " and RECRUITER_TEAMID = :teamid " //
				+ " and DATEHIRED is not null ";
		//
		paramList.put("teamid", jobDivaSession.getTeamId());
		//
		// set candidateid
		if (candidateid != null) {
			// If has candidate id passed in, query by candidate id only
			sql += " and candidateid = :candidateid ";
			paramList.put("candidateid", candidateid);
		} else if (isNotEmpty(candidatefirstname) || isNotEmpty(candidatelastname) || isNotEmpty(candidateemail)) {
			// No candidate id passed in. Query by candidate first name, last
			// name, email
			List<Long> candidates = candidateDao.getCandidateIds(jobDivaSession, candidatefirstname, candidatelastname, candidateemail);
			//
			if (candidates != null && candidates.size() > 0) {
				sql += " and candidateid IN ( :candidateid ) ";
				paramList.put("candidateid", candidates);
			} else {
				sql += "  and 1 = 2 ";
			}
		}
		//
		if (jobId != null) {
			sql += " and RFQID = :rfqid";
			paramList.put("rfqid", jobId);
		} else {
			if (!(isEmpty(jobdivaref) && isEmpty(optionalref))) {
				List<Long> jobs = jobDao.getJobIds(jobDivaSession, jobdivaref, optionalref);
				if (jobs != null && jobs.size() > 0) {
					sql += " and RFQID IN ( :jobs ) ";
					paramList.put("jobs", jobs);
				} else {
					sql += "  and 1 = 2 ";
				}
			}
		}
		//
		sql += " AND ROWNUM <= 201 ";
		//
		assignCurrencyRate(jobDivaSession);
		//
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
		//
		List<Activity> list = namedParameterJdbcTemplate.query(sql, paramList, new ActivityRowMapper(this));
		return list;
		//
	}
	
	public Long insertOrUpdate(JobDivaSession jobDivaSession, Activity activity) {
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		if (activity.getJobId() != null) {
			fieldList.add("RFQID");
			paramList.add(activity.getJobId());
		}
		//
		if (activity.getRecruiterId() != null) {
			fieldList.add("RECRUITERID");
			paramList.add(activity.getRecruiterId());
		} else {
			fieldList.add("RECRUITERID");
			paramList.add(jobDivaSession.getRecruiterId());
		}
		//
		if (activity.getCandidateId() != null) {
			fieldList.add("CANDIDATEID");
			paramList.add(activity.getCandidateId());
		}
		//
		if (activity.getNotes() != null) {
			fieldList.add("NOTES");
			paramList.add(activity.getNotes());
		}
		//
		if (activity.getDateInterview() != null) {
			fieldList.add("DATEINTERVIEW");
			paramList.add(activity.getDateInterview());
		}
		//
		if (activity.getDatePresented() != null) {
			fieldList.add("DATEPRESENTED");
			paramList.add(activity.getDatePresented());
		}
		//
		if (activity.getDateHired() != null) {
			fieldList.add("DATEHIRED");
			paramList.add(activity.getDateHired());
		}
		//
		if (activity.getManagerFirstName() != null) {
			fieldList.add("MANAGERFIRSTNAME");
			paramList.add(activity.getManagerFirstName());
		}
		//
		if (activity.getManagerLastName() != null) {
			fieldList.add("MANAGERLASTNAME");
			paramList.add(activity.getManagerLastName());
		}
		//
		if (activity.getCandidateTeamId() != null) {
			fieldList.add("CANDIDATE_TEAMID");
			paramList.add(activity.getCandidateTeamId());
		}
		//
		if (activity.getId() == null && activity.getRecruiterTeamId() != null) {
			fieldList.add("RECRUITER_TEAMID");
			paramList.add(activity.getRecruiterTeamId());
		}
		//
		if (activity.getDirty() != null) {
			fieldList.add("DIRTY");
			paramList.add(activity.getDirty());
		}
		//
		if (activity.getCustomerId() != null) {
			fieldList.add("CUSTOMERID");
			paramList.add(activity.getCustomerId());
		}
		//
		if (activity.getHourlyDateModified() != null) {
			fieldList.add("HOURLY_DATEMODIFIED");
			paramList.add(activity.getHourlyDateModified());
		}
		//
		if (activity.getHourlyRecruiterId() != null) {
			fieldList.add("HOURLY_RECRUITERID");
			paramList.add(activity.getHourlyRecruiterId());
		}
		//
		if (activity.getDailyDateModified() != null) {
			fieldList.add("DAILY_DATEMODIFIED");
			paramList.add(activity.getDailyDateModified());
		}
		//
		if (activity.getDailyRecruiterId() != null) {
			fieldList.add("DAILY_RECRUITERID");
			paramList.add(activity.getDailyRecruiterId());
		}
		//
		if (activity.getYearlyDatemodified() != null) {
			fieldList.add("YEARLY_DATEMODIFIED");
			paramList.add(activity.getYearlyDatemodified());
		}
		//
		if (activity.getYearlyRecruiterId() != null) {
			fieldList.add("YEARLY_RECRUITERID");
			paramList.add(activity.getYearlyRecruiterId());
		}
		//
		if (activity.getHourlyCorporate() != null) {
			fieldList.add("HOURLY_CORPORATE");
			paramList.add(activity.getHourlyCorporate());
		}
		//
		if (activity.getDailyCorporate() != null) {
			fieldList.add("DAILY_CORPORATE");
			paramList.add(activity.getDailyCorporate());
		}
		//
		if (activity.getYearlyCorporate() != null) {
			fieldList.add("YEARLY_CORPORATE");
			paramList.add(activity.getYearlyCorporate());
		}
		//
		if (activity.getHourly() != null) {
			fieldList.add("HOURLY");
			paramList.add(activity.getHourly());
		}
		//
		if (activity.getDaily() != null) {
			fieldList.add("DAILY");
			paramList.add(activity.getDaily());
		}
		//
		if (activity.getYearly() != null) {
			fieldList.add("YEARLY");
			paramList.add(activity.getYearly());
		}
		//
		if (activity.getBillHourly() != null) {
			fieldList.add("BILL_HOURLY");
			paramList.add(activity.getBillHourly());
		}
		//
		if (activity.getBillDaily() != null) {
			fieldList.add("BILL_DAILY");
			paramList.add(activity.getBillDaily());
		}
		//
		if (activity.getPayHourly() != null) {
			fieldList.add("PAY_HOURLY");
			paramList.add(activity.getPayHourly());
		}
		//
		if (activity.getPayDaily() != null) {
			fieldList.add("PAY_DAILY");
			paramList.add(activity.getPayDaily());
		}
		//
		if (activity.getPayYearly() != null) {
			fieldList.add("PAY_YEARLY");
			paramList.add(activity.getPayYearly());
		}
		//
		if (activity.getRecruiterIdCreator() != null) {
			fieldList.add("RECRUITERID_CREATOR");
			paramList.add(activity.getRecruiterIdCreator());
		}
		//
		if (activity.getDateCreated() != null) {
			fieldList.add("DATECREATED");
			paramList.add(activity.getDateCreated());
		}
		//
		if (activity.getDateEnded() != null) {
			fieldList.add("DATE_ENDED");
			paramList.add(activity.getDateEnded());
		}
		//
		if (activity.getDateUpdated() != null) {
			fieldList.add("DATEUPDATED");
			paramList.add(activity.getDateUpdated());
		}
		//
		if (activity.getRoleId() != null) {
			fieldList.add("ROLEID");
			paramList.add(activity.getRoleId());
		}
		//
		if (activity.getRecordType() != null) {
			fieldList.add("RECORDTYPE");
			paramList.add(activity.getRecordType());
		}
		//
		if (activity.getDateRejected() != null) {
			fieldList.add("DATEREJECTED");
			paramList.add(activity.getDateRejected());
		}
		//
		if (activity.getRejectedBy() != null) {
			fieldList.add("REJECTEDBY");
			paramList.add(activity.getRejectedBy());
		}
		//
		if (activity.getDateExtRejected() != null) {
			fieldList.add("DATEEXTREJECTED");
			paramList.add(activity.getDateExtRejected());
		}
		//
		if (activity.getExtRejectedBy() != null) {
			fieldList.add("EXTREJECTEDBY");
			paramList.add(activity.getExtRejectedBy());
		}
		//
		if (activity.getPayRateUnits() != null) {
			fieldList.add("PAYRATEUNITS");
			paramList.add(activity.getPayRateUnits());
		}
		//
		if (activity.getExtDateRejected() != null) {
			fieldList.add("EXTDATEREJECTED");
			paramList.add(activity.getExtDateRejected());
		}
		//
		if (activity.getExtRejectId() != null) {
			fieldList.add("EXTREJECTID");
			paramList.add(activity.getExtRejectId());
		}
		//
		if (activity.getExtRejectReasonId() != null) {
			fieldList.add("EXTREJECTREASONID");
			paramList.add(activity.getExtRejectReasonId());
		}
		//
		if (activity.getPlacementDate() != null) {
			fieldList.add("PLACEMENTDATE");
			paramList.add(activity.getPlacementDate());
		}
		//
		if (activity.getPresentedTimeZoneId() != null) {
			fieldList.add("PRESENTED_TIMEZONEID");
			paramList.add(activity.getPresentedTimeZoneId());
		}
		//
		if (activity.getInterviewTimeZoneId() != null) {
			fieldList.add("INTERVIEW_TIMEZONEID");
			paramList.add(activity.getInterviewTimeZoneId());
		}
		//
		if (activity.getHiredEndTimeZoneId() != null) {
			fieldList.add("HIRED_END_TIMEZONEID");
			paramList.add(activity.getHiredEndTimeZoneId());
		}
		//
		if (activity.getDateTerminated() != null) {
			fieldList.add("DATETERMINATED");
			paramList.add(activity.getDateTerminated());
		}
		//
		if (activity.getReasonTerminated() != null) {
			fieldList.add("REASONTERMINATED");
			paramList.add(activity.getReasonTerminated());
		}
		//
		if (activity.getTerminatorId() != null) {
			fieldList.add("TERMINATORID");
			paramList.add(activity.getTerminatorId());
		}
		//
		if (activity.getNoteRerminated() != null) {
			fieldList.add("NOTETERMINATED");
			paramList.add(activity.getNoteRerminated());
		}
		//
		if (activity.getPrimarySalesId() != null) {
			fieldList.add("PRIMARYSALESID");
			paramList.add(activity.getPrimarySalesId());
		}
		//
		if (activity.getLastEmployment() != null) {
			fieldList.add("LASTEMPLOYMENT");
			paramList.add(activity.getLastEmployment());
		}
		//
		if (activity.getHourlyCurrency() != null) {
			fieldList.add("HOURLY_CURRENCY");
			paramList.add(activity.getHourlyCurrency());
		}
		//
		if (activity.getDailyCurrency() != null) {
			fieldList.add("DAILY_CURRENCY");
			paramList.add(activity.getDailyCurrency());
		}
		//
		if (activity.getYearlyCurrency() != null) {
			fieldList.add("YEARLY_CURRENCY");
			paramList.add(activity.getYearlyCurrency());
		}
		//
		if (activity.getDateMail2managerSubmit() != null) {
			fieldList.add("DATE_MAIL2MANAGER_SUBMIT");
			paramList.add(activity.getDateMail2managerSubmit());
		}
		//
		if (activity.getDateMail2managerInterview() != null) {
			fieldList.add("DATE_MAIL2MANAGER_INTERVIEW");
			paramList.add(activity.getDateMail2managerInterview());
		}
		//
		if (activity.getPerformanceCode() != null) {
			fieldList.add("PERFORMANCECODE");
			paramList.add(activity.getPerformanceCode());
		}
		//
		if (activity.getEmployType() != null) {
			fieldList.add("EMPLOY_TYPE");
			paramList.add(activity.getEmployType());
		}
		//
		if (activity.getDbFinalBillRateUnit() != null) {
			fieldList.add("FINALBILLRATEUNIT");
			paramList.add(activity.getDbFinalBillRateUnit());
		}
		//
		if (activity.getFinalBillrateCurrency() != null) {
			fieldList.add("FINALBILLRATE_CURRENCY");
			paramList.add(activity.getFinalBillrateCurrency());
		}
		//
		if (activity.getOnboardIngassigned() != null) {
			fieldList.add("ONBOARDINGASSIGNED");
			paramList.add(activity.getOnboardIngassigned());
		}
		//
		if (activity.getOnboardIngassignedBy() != null) {
			fieldList.add("ONBOARDINGASSIGNEDBY");
			paramList.add(activity.getOnboardIngassignedBy());
		}
		//
		if (activity.getOnboardIngassignedOn() != null) {
			fieldList.add("ONBOARDINGASSIGNEDON");
			paramList.add(activity.getOnboardIngassignedOn());
		}
		//
		if (activity.getDimDatePresented() != null) {
			fieldList.add("DIM_DATEPRESENTED");
			paramList.add(activity.getDimDatePresented());
		}
		//
		if (activity.getInterviewScheduleDate() != null) {
			fieldList.add("INTERVIEWSCHEDULEDATE");
			paramList.add(activity.getInterviewScheduleDate());
		}
		//
		if (activity.getContract() != null) {
			fieldList.add("CONTRACT");
			paramList.add(activity.getContract());
		}
		//
		if (activity.getFee() != null) {
			fieldList.add("FEE");
			paramList.add(activity.getFee());
		}
		//
		if (activity.getFeeType() != null) {
			fieldList.add("FEE_TYPE");
			paramList.add(activity.getFeeType());
		}
		//
		if (activity.getSubmittalStatus() != null) {
			fieldList.add("SUBMITTALSTATUS_STR");
			paramList.add(activity.getSubmittalStatus());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		if (activity.getId() == null) {
			// this is the key holder
			String sql = "SELECT INTERVIEWID.nextval  AS activityId  FROM dual";
			List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("activityId");
				}
			});
			Long activityId = 0L;
			if (listLong != null && listLong.size() > 0) {
				activityId = listLong.get(0);
			}
			//
			String sqlInsert = " INSERT INTO TINTERVIEWSCHEDULE (ID," + sqlInsertFields(fieldList) + ")  VALUES (" + activityId + "," + sqlInsertParams(fieldList) + ") ";
			//
			logger.info("ActivityDao : insertOrUpdate :: " + paramList + " /" + jobDivaSession.getRecruiterId());
			//
			jdbcTemplate.update(sqlInsert, paramList.toArray());
			//
			return activityId;
		} else {
			//
			String sqlUpdate = "UPDATE TINTERVIEWSCHEDULE SET " + sqlUpdateFields(fieldList) + " WHERE ID = ? AND RECRUITER_TEAMID = ? ";
			paramList.add(activity.getId());
			paramList.add(jobDivaSession.getTeamId());
			//
			jdbcTemplate.update(sqlUpdate, paramList.toArray());
			//
			return activity.getId();
		}
		//
	}
}
