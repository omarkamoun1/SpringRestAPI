package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.session.JobDivaSessionDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class JobDivaSessionService {
	
	@Autowired
	JobDivaSessionDao jobDivaSessionDao;
	
	public String refreshToken(JobDivaSession jobDivaSession) {
		//
		return jobDivaSessionDao.refreshToken(jobDivaSession);
		//
	}
}
