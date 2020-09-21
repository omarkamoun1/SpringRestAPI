package com.jobdiva.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.candidate.CandidateAttachmentDao;
import com.jobdiva.api.dao.resume.ResumeDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class ResumeService {
	
	@Autowired
	ResumeDao				resumeDao;
	//
	@Autowired
	CandidateAttachmentDao	candidateAttachmentDao;
	
	public Long uploadResume(JobDivaSession jobDivaSession, String filename, byte[] filecontent, String textfile, Long candidateid, Integer resumesource, Long recruiterId, Date resumeDate) throws Exception {
		//
		try {
			//
			Long id = resumeDao.uploadResume(jobDivaSession, filename, filecontent, textfile, candidateid, resumesource, recruiterId, resumeDate);
			//
			resumeDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadResume", "Upload Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			resumeDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadResume", "Upload Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
	
	public Long uploadCandidateAttachment(JobDivaSession jobDivaSession, Long candidateid, String name, String filename, byte[] filecontent, Integer attachmenttype, String description) throws Exception {
		//
		try {
			//
			Long id = candidateAttachmentDao.uploadCandidateAttachment(jobDivaSession, candidateid, name, filename, filecontent, attachmenttype, description);
			//
			candidateAttachmentDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadCandidateAttachment", "Upload Successful");
			//
			return id;
			//
		} catch (Exception e) {
			//
			candidateAttachmentDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "uploadCandidateAttachment", "Upload Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
