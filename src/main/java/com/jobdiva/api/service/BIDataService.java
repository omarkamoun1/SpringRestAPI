package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.bi.BIDataDao;
import com.jobdiva.api.dao.bi.BIDataDaoOld;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.bidata.IBiData;

@Service
public class BIDataService {
	
	@Autowired
	BIDataDao		biDataDao;
	//
	@Autowired
	BIDataDaoOld	biDataDaoOld;
	
	public IBiData getBIData(JobDivaSession jobDivaSession, String metricName, Date fromDate, Date toDate, String[] parameters, Boolean alternateFormat) {
		// return biDataDao.getBIData(jobDivaSession, metricName, fromDate,
		// toDate, parameters);
		return biDataDaoOld.getBIData(jobDivaSession, metricName, fromDate, toDate, parameters, alternateFormat);
	}
}
