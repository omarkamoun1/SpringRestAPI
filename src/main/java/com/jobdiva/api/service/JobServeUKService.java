package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.jobposting.JobServeUKDao;

@Service
public class JobServeUKService {
	
	//
	@Autowired
	JobServeUKDao jobServeUKDao;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public String request(String req, Long teamid, String rfqid, String username, String pass) {
		return jobServeUKDao.request(req, teamid, rfqid, username, pass);
	}
	
}