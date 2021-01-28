package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.jobposting.NIJobsDao;
import com.jobdiva.api.model.JobBoardResponse;

@Service
public class NIJobsService {
	
	//
	@Autowired
	NIJobsDao niJobsDao;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public JobBoardResponse request(String req, Long teamid, String rfqid, String username, String pass) {
		return niJobsDao.request(req, teamid, rfqid, username, pass);
	}
	
}