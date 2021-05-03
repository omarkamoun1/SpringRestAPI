package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.submittal.SubmittalDao;
import com.jobdiva.api.model.Submittal;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class SubmittalService extends AbstractService {
	
	@Autowired
	SubmittalDao submittalDao;
	
	public List<Submittal> searchSubmittal(JobDivaSession jobDivaSession, Long submittalid, Long jobid, String joboptionalref, String companyname, //
			Long candidateid, String candidatefirstname, String candidatelastname, String candidateemail, String candidatephone, String candidatecity, String candidatestate) throws Exception {
		//
		try {
			//
			List<Submittal> activities = submittalDao.searchSubmittal(jobDivaSession, submittalid, jobid, joboptionalref, companyname, candidateid, candidatefirstname, candidatelastname, candidateemail, candidatephone, candidatecity, candidatestate);
			//
			submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchSubmittal", "Search Successful");
			//
			return activities;
			//
		} catch (Exception e) {
			//
			submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchSubmittal", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Long createSubmittal(JobDivaSession jobDivaSession, Long jobid, Long candidateid, String status1, Long submit2id, Date submittaldate, String positiontype, Long recruitedbyid, Long primarysalesid, String internalnotes, Double salary,
			Integer feetype, Double fee, Double quotedbillrate, Double agreedbillrate, Double payrate, String payratecurrency, String payrateunit, Boolean corp2corp, Date agreedon, Userfield[] userfields, String filename, Byte[] filecontent,
			String textfile) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long submittalId = submittalDao.createSubmittal(jobDivaSession, jobid, candidateid, status1, submit2id, submittaldate, positiontype, recruitedbyid, primarysalesid, internalnotes, salary, feetype, fee, quotedbillrate,
								agreedbillrate, payrate, payratecurrency, payrateunit, corp2corp, agreedon, userfields, filename, filecontent, textfile);
						//
						submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createSubmittal", "Create Successful");
						//
						return submittalId;
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
			submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createSubmittal", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean updateSubmittal(JobDivaSession jobDivaSession, Long submittalid, String status1, String internalnotes, Double salary, Integer feetype, Double fee, Double quotedbillrate, Double agreedbillrate, Double payrate,
			String payratecurrency, String payrateunit, Boolean corp2corp, Date agreedon, Date interviewdate, Date internalrejectdate, Date externalrejectdate) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = submittalDao.updateSubmittal(jobDivaSession, submittalid, status1, internalnotes, salary, feetype, fee, quotedbillrate, agreedbillrate, payrate, payratecurrency, payrateunit, corp2corp, agreedon,
								interviewdate, internalrejectdate, externalrejectdate);
						//
						submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateSubmittal", "Update Successful");
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
			submittalDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateSubmittal", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
