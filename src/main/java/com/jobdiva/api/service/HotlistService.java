package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.hotlist.HotlistDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class HotlistService {
	
	@Autowired
	HotlistDao hotlistDao;
	
	public Long createCandidateHoltilst(JobDivaSession jobDivaSession, String name, Long linkToJobId, Long linkToHiringManagerId, String description, List<Long> userIds, List<Long> groupIds, List<Long> divisionIds) throws Exception {
		//
		//
		try {
			//
			Long id = hotlistDao.createCandidateHoltilst(jobDivaSession, name, linkToJobId, linkToHiringManagerId, description, userIds, groupIds, divisionIds);
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateHoltilst", "Add Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateHoltilst", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean addCandidateToHotlist(JobDivaSession jobDivaSession, Long hotListid, Long candidateId) throws Exception {
		//
		//
		try {
			//
			Boolean success = hotlistDao.addCandidateToHotlist(jobDivaSession, hotListid, candidateId);
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addCandidateToHotlist", "Add Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addCandidateToHotlist", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Long createContactHotlist(JobDivaSession jobDivaSession, String name, Boolean active, Boolean isPrivate, String description, List<Long> sharedWithIds) throws Exception {
		//
		//
		try {
			//
			Long id = hotlistDao.createContactHotlist(jobDivaSession, name, active, isPrivate, description, sharedWithIds);
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContactHotlist", "Add Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createContactHotlist", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Boolean addContactToHotlist(JobDivaSession jobDivaSession, Long hotListid, Long contactId) throws Exception {
		//
		//
		try {
			//
			Boolean success = hotlistDao.addContactToHotlist(jobDivaSession, hotListid, contactId);
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addContactToHotlist", "Add Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			hotlistDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "addContactToHotlist", "Add Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
