package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.jobposting.VolcanicDao;

@Service
public class VolcanicService {
	
	//
	@Autowired
	VolcanicDao volcanicDao;
	
	public String request(String req, Long teamid, String rfqid, String refNo, String website, String apiKey) {
		return volcanicDao.request(req, teamid, rfqid, refNo, website, apiKey);
	}
	
	public String getLists(String website, String apiKey) {
		return volcanicDao.getLists(website, apiKey);
	}
}