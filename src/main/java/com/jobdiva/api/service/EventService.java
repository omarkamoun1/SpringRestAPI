package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.event.EventDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class EventService {
	
	@Autowired
	EventDao eventDao;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Long createTask(JobDivaSession jobDivaSession, String subject, Date duedate, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid, String description, Boolean isprivate)
			throws Exception {
		//
		try {
			//
			Long eventId = eventDao.createTask(jobDivaSession, subject, duedate, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createTask", "Create Successful Event Id(" + eventId + ") ");
			//
			return eventId;
			//
		} catch (Exception e) {
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createTask", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
}
