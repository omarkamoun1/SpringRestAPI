package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.jobposting.IrishJobsDao;

@Service
public class IrishJobsService {
	
	//
	@Autowired
	IrishJobsDao irishJobsDao;
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public String request(String username, String password, String action, String rfqid, String recruiterid, String teamid) {
		return irishJobsDao.request(username, password, action, rfqid, recruiterid, teamid);
	} 
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public String getSlots(String username, String password, String recruiterid, String teamid) {
		return irishJobsDao.getSlots(username, password, recruiterid, teamid);
	} 
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public String getAllLists() {
		return irishJobsDao.getAllLists();
	}
}