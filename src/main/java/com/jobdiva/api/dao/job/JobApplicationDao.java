package com.jobdiva.api.dao.job;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.axelon.candidate.CandidateData;
import com.axelon.candidate.DocumentObjectResume;
import com.axelon.oc4j.ServletRequestData;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

@Component
public class JobApplicationDao extends AbstractJobDivaDao {
	
	public Integer createJobApplication(JobDivaSession jobDivaSession, Long candidateid, Long jobid, Date dateapplied, Integer resumesource, String globalid) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		// Data Validation
		if (candidateid < 1)
			message.append(String.format("Invalid candidate ID: %d\n", candidateid));
		if (jobid < 1)
			message.append(String.format("Invalid job ID: %d\n", jobid));
		if (resumesource != null && resumesource < 0) {
			message.append(String.format("Invalid resume source: %d\n", resumesource));
		}
		if (globalid != null && globalid.trim().length() == 0)
			globalid = null;
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n " + message.toString());
		}
		//
		CandidateData candObj = new CandidateData(candidateid);
		candObj.teamid = jobDivaSession.getTeamId();
		candObj.rfqid = jobid;
		DocumentObjectResume dummyResume = new DocumentObjectResume(0, 0, "", "", 0);
		// TODO: change candidate/DocumentObject.ResumeSource to be Integer
		dummyResume.ResumeSource = String.valueOf(resumesource);
		dummyResume.global_id = globalid;
		candObj.resume = dummyResume;
		ServletRequestData data = new ServletRequestData(0L, candObj);
		Object retObj = ServletTransporter.callServlet(getCandidateApplyJobUsingAPI(), data);
		if (retObj instanceof Exception)
			throw (Exception) retObj;
		Integer ret_int = (Integer) retObj;
		///
		///
		return ret_int;
	}
}
