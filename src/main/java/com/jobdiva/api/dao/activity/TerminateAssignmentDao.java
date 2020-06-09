package com.jobdiva.api.dao.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class TerminateAssignmentDao extends AbstractActivityDao {
	
	@Autowired
	ActivityDao activityDao;
	
	public Boolean terminateAssignment(JobDivaSession jobDivaSession, Long assignmentid, Long candidateid, Long jobId, Date terminationdate, Integer reason, Integer performancecode, String notes, Boolean markaspastemployee, Boolean markasavailable)
			throws Exception {
		//
		//
		throw new Exception("On Hold Method");
		//
		//
		// return null;
	}
}
