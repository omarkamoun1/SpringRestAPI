package com.jobdiva.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.onboard.OnBoardDao;
import com.jobdiva.api.dao.onboard.SaveOnBoardDao;
import com.jobdiva.api.dao.onboard.UploadOnboardingDocumentDao;
import com.jobdiva.api.model.OnboardingCandidateDocument;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.onboard.HireType;
import com.jobdiva.api.model.onboard.InterviewSchedule;
import com.jobdiva.api.model.onboard.OnBoardDocument;
import com.jobdiva.api.model.onboard.OnBoardLocationPackage;
import com.jobdiva.api.model.onboard.OnBoardPackage;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class OnBoardService {
	
	@Autowired
	OnBoardDao					onBoardDao;
	//
	@Autowired
	SaveOnBoardDao				saveOnBoardDao;
	//
	@Autowired
	UploadOnboardingDocumentDao	uploadOnboardingDocumentDao;
	
	public List<HireType> getPackageList(JobDivaSession jobDivaSession) {
		return this.onBoardDao.getPackageList(jobDivaSession);
	}
	
	public List<OnBoardDocument> getDocumentsByCompany(JobDivaSession jobDivaSession, Long companyId) {
		return this.onBoardDao.getDocumentsByCompany(jobDivaSession, companyId);
	}
	
	public List<OnBoardDocument> getDocumentsByContact(JobDivaSession jobDivaSession, Long clientId) {
		return this.onBoardDao.getDocumentsByContact(jobDivaSession, clientId);
	}
	
	public List<OnBoardPackage> getPackagesDetail(JobDivaSession jobDivaSession) {
		return this.onBoardDao.getPackagesDetail(jobDivaSession);
	}
	
	public List<OnBoardLocationPackage> getJobLocationDocuments(JobDivaSession jobDivaSession, Long jobId) {
		return this.onBoardDao.getJobLocationDocuments(jobDivaSession, jobId);
	}
	
	public Long saveOnboarding(JobDivaSession jobDivaSession, InterviewSchedule interviewSchedule) throws Exception {
		return this.saveOnBoardDao.saveOnboarding(jobDivaSession, interviewSchedule);
	}
	
	public Long uploadCandidateOnboardingDocument(JobDivaSession jobDivaSession, OnboardingCandidateDocument onboardingCandidateDocument) throws Exception {
		//
		return uploadOnboardingDocumentDao.uploadCandidateOnboardingDocument(jobDivaSession, onboardingCandidateDocument);
		//
	}
}
