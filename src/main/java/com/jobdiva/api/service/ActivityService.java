package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.activity.ActivityDao;
import com.jobdiva.api.dao.activity.SetStartDao;
import com.jobdiva.api.dao.activity.TerminateAssignmentDao;
import com.jobdiva.api.dao.activity.TerminateStartDao;
import com.jobdiva.api.dao.activity.UpdateActivityDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class ActivityService extends AbstractService {
	
	@Autowired
	ActivityDao				activityDao;
	//
	@Autowired
	UpdateActivityDao		updateActivityDao;
	//
	@Autowired
	SetStartDao				setStartDao;
	//
	@Autowired
	TerminateStartDao		terminateStartDao;
	//
	@Autowired
	TerminateAssignmentDao	terminateAssignmentDao;
	
	public List<Activity> searchStart(JobDivaSession jobDivaSession, Long jobId, String optionalref, String jobdivaref, Long candidateid, String candidatefirstname, String candidatelastname, String candidateemail) throws Exception {
		try {
			//
			List<Activity> activities = activityDao.searchStart(jobDivaSession, jobId, optionalref, jobdivaref, candidateid, candidatefirstname, candidatelastname, candidateemail);
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchStart", "Search Successful");
			//
			return activities;
			//
		} catch (Exception e) {
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchStart", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean updateStart(JobDivaSession jobDivaSession, Long startid, Boolean overwrite, Date startDate, Date endDate, String positiontype, Double billrate, String billratecurrency, String billrateunit, Double payrate, String payratecurrency,
			String payrateunit, Userfield[] userfields) throws Exception {
		//
		try {
			//
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						Boolean success = updateActivityDao.updateStart(jobDivaSession, startid, overwrite, startDate, endDate, positiontype, billrate, billratecurrency, billrateunit, payrate, payratecurrency, payrateunit, userfields);
						//
						activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateStart", "Update Successful");
						//
						return success;
						//
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateStart", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean setStart(JobDivaSession jobDivaSession, Long submittalid, Long recruiterid, Date startDate, Date endDate, Timezone timezone, String internalnotes) throws Exception {
		//
		try {
			//
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = setStartDao.setStart(jobDivaSession, submittalid, recruiterid, startDate, endDate, timezone, internalnotes);
						//
						activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "setStart", "Set Start Successful");
						//
						return success;
						//
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "setStart", "Set Start Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean terminateStart(JobDivaSession jobDivaSession, Long startid, Long candidateid, Long jobId, Date terminationdate, Integer reason, Integer performancecode, String notes, Boolean markaspastemployee, Boolean markasavailable)
			throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = terminateStartDao.terminateStart(jobDivaSession, startid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
						//
						terminateStartDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "terminateStart", "Terminate Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "terminateStart", "Terminate Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean terminateAssignment(JobDivaSession jobDivaSession, Long assignmentid, Long candidateid, Long jobId, Date terminationdate, Integer reason, Integer performancecode, String notes, Boolean markaspastemployee, Boolean markasavailable)
			throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = terminateAssignmentDao.terminateAssignment(jobDivaSession, assignmentid, candidateid, jobId, terminationdate, reason, performancecode, notes, markaspastemployee, markasavailable);
						//
						terminateAssignmentDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "terminateAssignment", "Terminate Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			activityDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "terminateAssignment", "Terminate Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
