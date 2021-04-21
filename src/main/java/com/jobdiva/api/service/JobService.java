package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.job.JobApplicationDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.dao.job.JobNoteDao;
import com.jobdiva.api.dao.job.JobUserDao;
import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class JobService {
	
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
			// if (jobs != null) {
			// for (Job job : jobs) {
			// //
			// List<JobUser> jobUsers = jobUserDao.getJobUsers(job.getId(),
			// jobDivaSession.getTeamId());
			// job.setJobUsers(jobUsers);
			// //
			// }
			// }
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchJobs", "Search Successful");
			//
			return jobs;
			//
		} catch (Exception e) {
			e.printStackTrace();
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchJobs", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean addJobNote(JobDivaSession jobDivaSession, Long jobId, Integer type, Long recruiterId, Integer shared, String note) throws Exception {
		//
		try {
			//
			Boolean success = jobNoteDao.addJobNote(jobDivaSession, jobId, type, recruiterId, shared, note);
			//
			jobNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addJobNote", "Add Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			jobNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addJobNote", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public List<JobUserSimple> getAllJobUsers(JobDivaSession jobDivaSession, Long jobId) throws Exception {
		//
		try {
			//
			List<JobUserSimple> list = jobUserDao.getAllJobUsers(jobDivaSession, jobId);
			//
			jobUserDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobUsers", "Get Successful");
			//
			return list;
			//
		} catch (Exception e) {
			//
			jobUserDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobUsER", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public List<String> getJobPriority(JobDivaSession jobDivaSession, Long teamId) throws Exception {
		//
		try {
			//
			List<String> list = jobDao.getJobPriority(jobDivaSession, teamId);
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobPriority", "Get Successful");
			//
			return list;
			//
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getJobPriority", "Get Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean createJobApplication(JobDivaSession jobDivaSession, Long candidateid, Long jobid, Date dateapplied, Integer resumesource, String globalid) throws Exception {
		//
		try {
			//
			Boolean result = jobApplicationDao.createJobApplication(jobDivaSession, candidateid, jobid, dateapplied, resumesource, globalid);
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJobApplication", "Create Successful , The candidate is successfully applied to the job");
			//
			return result;
		} catch (Exception e) {
			//
			jobApplicationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJobApplication", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Long createJob(JobDivaSession jobDivaSession, ContactRoleType[] contacts, String contactfirstname, String contactlastname, String title, String description, String department, Long companyid, String priority, Long divisionid,
			UserRole[] users, Integer experience, Integer status, String optionalref, String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, String jobtype, Integer openings,
			Integer fills, Integer maxsubmittals, Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck,
			Boolean securityclearance, String remarks, String submittalinstruction, Double minbillrate, Double maxbillrate, String billratecurrency, String billrateunit, Double minpayrate, Double maxpayrate, String payratecurrency,
			String payrateunit, Skill[] skills, String[] skillstates, String skillzipcode, Integer skillzipcodemiles, Skill[] excludedskills, String harvest, Integer resumes, Attachment[] attachments, Userfield[] userfields) throws Exception {
		//
		try {
			//
			Long jobId = jobDao.createJob(jobDivaSession, contacts, contactfirstname, contactlastname, title, description, department, companyid, priority, divisionid, users, experience, status, optionalref, address1, address2, city, state, zipcode,
					countryid, startdate, enddate, jobtype, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks,
					submittalinstruction, minbillrate, maxbillrate, billratecurrency, billrateunit, minpayrate, maxpayrate, payratecurrency, payrateunit, skills, skillstates, skillzipcode, skillzipcodemiles, excludedskills, harvest, resumes,
					attachments, userfields);
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createJob", "Create Successful");
			//
			return jobId;
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
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateJob(JobDivaSession jobDivaSession, Long jobid, String optionalref, String title, String description, String postingtitle, String postingdescription, Long companyid, ContactRoleType[] contacts, UserRole[] users,
			String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, Integer status, String jobtype, String priority, Integer openings, Integer fills, Integer maxsubmittals,
			Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck, Boolean securityclearance, String remarks,
			String submittalinstruction, Double minbillrate, Double maxbillrate, Double minpayrate, Double maxpayrate, Userfield[] userfields, String harvest, Integer resumes, Long divisionid) throws Exception {
		//
		try {
			//
			Boolean success = jobDao.updateJob(jobDivaSession, jobid, optionalref, title, description, postingtitle, postingdescription, companyid, contacts, users, address1, address2, city, state, zipcode, countryid, startdate, enddate, status,
					jobtype, priority, openings, fills, maxsubmittals, hidemyclient, hidemyclientaddress, hidemeandmycompany, overtime, reference, travel, drugtest, backgroundcheck, securityclearance, remarks, submittalinstruction, minbillrate,
					maxbillrate, minpayrate, maxpayrate, userfields, harvest, resumes, divisionid);
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJob", "Update Successful");
			//
			return success;
		} catch (Exception e) {
			//
			jobDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateJob", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
