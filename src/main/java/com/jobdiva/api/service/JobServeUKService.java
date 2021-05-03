package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.jobposting.JobServeUKDao;
import com.jobdiva.api.model.JsukResponse;

@Service
public class JobServeUKService {
	
	//
	@Autowired
	JobServeUKDao jobServeUKDao;
	
	public JsukResponse request(String req, Long teamid, String rfqid, String username, String pass) {
		return jobServeUKDao.request(req, teamid, rfqid, username, pass);
	}
}