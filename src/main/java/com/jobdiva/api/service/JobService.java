package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.job.JobApplicationDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.dao.job.JobNoteDao;
import com.jobdiva.api.dao.job.JobUserDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobStatus;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.Note;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.TeamRole;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class JobService extends AbstractService {
	
	protected final Logger	logger	= LoggerFactory.getLogger(this.getClass());
	//
	//
	@Autowired
	JobDao					jobDao;
	//
	@Autowired
	JobUserDao				jobUserDao;
	//
	@Autowired
	JobNoteDao				jobNoteDao;
	//
	@Autowired
	JobApplicationDao		jobApplicationDao;
	
	public List<Job> searchJobs(JobDivaSession jobDivaSession, Long jobId, String jobdivaref, String optionalref, String city, String[] state, String title, Long contactid, Long companyId, String companyname, Integer status, String[] jobtype,
			Date issuedatefrom, Date issuedateto, Date startdatefrom, Date startdateto, String zipcode, Integer zipcodeRadius, String countryId, Boolean ismyjob) throws Exception {
		//
		try {
			List<Job> jobs = jobDao.searchJobs(jobDivaSession, jobId, jobdivaref, optionalref, city, state, title, contactid, companyId, companyname, status, jobtype, issuedatefrom, issuedateto, startdatefrom, startdateto, null, null, zipcode,
					zipcodeRadius, countryId, ismyjob);
			//
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchJobs", "Search Successful");
			//
			return jobs;
			//
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchJobs", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean addJobNote(JobDivaSession jobDivaSession, Long jobId, Integer type, Long recruiterId, Integer shared, String note) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = jobNoteDao.addJobNote(jobDivaSession, jobId, type, recruiterId, shared, note);
						//
						jobNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addJobNote", "Add Successful");
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
			jobNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addJobNote", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<JobUserSimple> getAllJobUsers(JobDivaSession jobDivaSession, Long jobId) throws Exception {
		//
		try {
			//
			List<JobUserSimple> jobUsers = jobUserDao.getAllJobUsers(jobDivaSession, jobId);
			//
			jobUserDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobUsers", "Get Successful");
			//
			return jobUsers;
			//
		} catch (Exception e) {
			//
			jobUserDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobUsER", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<String> getJobPriority(JobDivaSession jobDivaSession, Long teamId) throws Exception {
		//
		try {
			//
			List<String> jobPriority = jobDao.getJobPriority(jobDivaSession, teamId);
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobPriority", "Get Successful");
			//
			return jobPriority;
			//
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobPriority", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean updateJobPriority(JobDivaSession jobDivaSession, Integer priority, Long jobId, String priorityName) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobDao.updateJobPriority(jobDivaSession, priority, jobId, priorityName);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJobPriority", "Update Successful");
						//
						return result;
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJobPriority", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<TeamRole> getUserRoles(JobDivaSession jobDivaSession) throws Exception {
		//
		try {
			//
			List<TeamRole> userRoles = jobDao.getUserRoles(jobDivaSession);
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getUserRoles", "Get Successful");
			//
			return userRoles;
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getUserRoles", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean assignUserToJob(JobDivaSession jobDivaSession, Long rfqid, Long recruiterid, List<Long> roleIds, Integer jobstatus) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobUserDao.assignUserToJob(jobDivaSession, rfqid, recruiterid, roleIds, jobstatus);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "assignUserToJob", "assign Successful");
						//
						return result;
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "assignUserToJob", "assign Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean createJobApplication(JobDivaSession jobDivaSession, Long candidateid, Long jobid, Date dateapplied, Integer resumesource, String globalid) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobApplicationDao.createJobApplication(jobDivaSession, candidateid, jobid, dateapplied, resumesource, globalid);
						//
						jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJobApplication", "Create Successful , The candidate is successfully applied to the job");
						//
						return result;
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
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJobApplication", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<Note> getJobNotes(JobDivaSession jobDivaSession, Long jobid) throws Exception {
		//
		try {
			//
			List<Note> notes = jobNoteDao.getJobNotes(jobDivaSession, jobid);
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobNotes", "Get Job Notes Successful");
			//
			return notes;
		} catch (Exception e) {
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobNotes", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public List<Activity> getJobActivities(JobDivaSession jobDivaSession, Long jobid) throws Exception {
		//
		try {
			//
			List<Activity> activities = jobDao.getJobActivities(jobDivaSession, jobid);
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobActivities", "Get Job Activities Successful");
			//
			return activities;
		} catch (Exception e) {
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobActivities", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Long createJob(JobDivaSession jobDivaSession, ContactRoleType[] contacts, String contactfirstname, String contactlastname, String title, String description, String department, Long companyid, String priority, Long divisionid,
			UserRole[] users, Integer experience, Integer status1, String optionalref, String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, String jobtype, Integer openings,
			Integer fills, Integer maxsubmittals, Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck,
			Boolean securityclearance, String remarks, String submittalinstruction, Double minbillrate, Double maxbillrate, String billratecurrency, String billrateunit, Double minpayrate, Double maxpayrate, String payratecurrency,
			String payrateunit, Skill[] skills, String[] skillstates, String skillzipcode, Integer skillzipcodemiles, Skill[] excludedskills, String harvest, Integer resumes, Attachment[] attachments, Userfield[] userfields) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long jobId = jobDao.createJob(jobDivaSession, contacts, contactfirstname, contactlastname, title, description, department, companyid, priority, divisionid, users, experience, status1, optionalref, address1, address2, city,
								state, zipcode, countryid, startdate, enddate, jobtype, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance,
								remarks, submittalinstruction, minbillrate, maxbillrate, billratecurrency, billrateunit, minpayrate, maxpayrate, payratecurrency, payrateunit, skills, skillstates, skillzipcode, skillzipcodemiles, excludedskills,
								harvest, resumes, attachments, userfields);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJob", "Create Successful");
						//
						return jobId;
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
			logger.info("Exception When  Insert Job  " + e.getMessage());
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJob", "Create Failed, " + e.getMessage());
			//
			e.printStackTrace();
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean updateJob(JobDivaSession jobDivaSession, Long jobid, String optionalref, String title, String description, String postingtitle, String postingdescription, Long companyid, ContactRoleType[] contacts, UserRole[] users,
			String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, Integer status1, String jobtype, String priority, Integer openings, Integer fills, Integer maxsubmittals,
			Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck, Boolean securityclearance, String remarks,
			String submittalinstruction, Double minbillrate, Double maxbillrate, Double minpayrate, Double maxpayrate, Userfield[] userfields, String harvest, Integer resumes, Long divisionid) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = jobDao.updateJob(jobDivaSession, jobid, optionalref, title, description, postingtitle, postingdescription, companyid, contacts, users, address1, address2, city, state, zipcode, countryid, startdate, enddate,
								status1, jobtype, priority, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks,
								submittalinstruction, minbillrate, maxbillrate, minpayrate, maxpayrate, userfields, harvest, resumes, divisionid);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJob", "Update Successful");
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJob", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}

	public Boolean unassignUserFromJob(JobDivaSession jobDivaSession, Long jobId, Long recruiterid) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobUserDao.unassignUserFromJob(jobDivaSession, jobId, recruiterid);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "unassignUserFromJob", "unassign Successful");
						//
						return result;
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "unassignUserFromJob", "unassign Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}

	public Boolean updateUserRoleForJob(JobDivaSession jobDivaSession, Long jobId, Long recruiterid, List<Long> roleIds) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobUserDao.updateUserRoleForJob(jobDivaSession, jobId, recruiterid, roleIds);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateUserRoleForJob", "update Successful");
						//
						return result;
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateUserRoleForJob", "update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}

	public List<JobStatus> getJobStatus(JobDivaSession jobDivaSession) throws Exception {
		
		//
		try {
			List<JobStatus> status = jobDao.getJobStatus(jobDivaSession);
			//
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobStatus", "Get Successful");
			//
			return status;
			//
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobStatus", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}

	public Boolean updateJobStatus(JobDivaSession jobDivaSession, Long jobId, Long userId, String firstName,String lastName, Integer oldStatus, Integer jobStatus, String oldStatusName, String jobStatusName) throws Exception{
	
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean result = jobDao.updateJobStatus(jobDivaSession, jobId, userId, firstName, lastName, oldStatus, jobStatus, oldStatusName, jobStatusName);
						//
						jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJobStatus", "update Successful");
						//
						return result;
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
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJobStatus", "update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
