package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jobdiva.api.dao.candidate.CandidateAvailabilityDao;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.dao.candidate.CandidateHRRecordDao;
import com.jobdiva.api.dao.candidate.CandidateNoteDao;
import com.jobdiva.api.dao.candidate.CandidateQualificationsDao;
import com.jobdiva.api.dao.candidate.CandidateUDFDao;
import com.jobdiva.api.dao.candidate.CandidateUserFieldsDao;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class CandidateService {
	
	@Autowired
	CandidateDao				candidateDao;
	//
	@Autowired
	CandidateUDFDao				candidateUDFDao;
	//
	@Autowired
	CandidateUserFieldsDao		candidateUserFieldsDao;
	//
	@Autowired
	CandidateQualificationsDao	candidateQualificationsDao;
	//
	@Autowired
	CandidateAvailabilityDao	candidateAvailabilityDao;
	//
	@Autowired
	CandidateNoteDao			candidateNoteDao;
	//
	@Autowired
	CandidateHRRecordDao		candidateHRRecordDao;
	
	public List<Candidate> searchCandidateProfile(JobDivaSession jobDivaSession, String firstName, String lastName, String address, String city, String state, String zipCode, String country, String email, String phone, CandidateQual[] candidateQuals,
			Integer rowLimit) throws Exception {
		try {
			//
			List<Candidate> candidates = candidateDao.searchCandidates(jobDivaSession, firstName, lastName, address, city, state, zipCode, country, email, phone, candidateQuals, rowLimit);
			//
			// if (candidates != null) {
			// for (Candidate candidate : candidates) {
			// List<CandidateUDF> candidateUDFs =
			// candidateUDFDao.getContactUDF(candidate.getId(),
			// candidate.getTeamId());
			// candidate.setCandidateUDFs(candidateUDFs);
			// }
			// }
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchCandidateProfile", "Search Successful");
			//
			return candidates;
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "searchCandidateProfile", "Search Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Long createCandidate(JobDivaSession jobDivaSession, String firstName, String lastName, String email, String alternateemail, String address1, String address2, String city, String state, String zipCode, String countryid, String homephone,
			String workphone, String cellphone, String fax, Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit, String narrative, //
			TitleSkillCertification[] titleskillcertifications, String titleskillcertification, Date startdate, Date enddate, Integer years) throws Exception {
		//
		try {
			//
			Long candidateId = candidateDao.createCandidate(jobDivaSession, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, homephone, workphone, cellphone, fax, currentsalary, currentsalaryunit,
					preferredsalary, preferredsalaryunit, narrative, titleskillcertifications, titleskillcertification, startdate, enddate, years);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidate", "Search Successful");
			//
			return candidateId;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidate", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCandidateProfile(JobDivaSession jobDivaSession, Long candidateid, String firstName, String lastName, String email, String alternateemail, String address1, String address2, String city, String state, String zipCode,
			String countryid, PhoneType[] phones, Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit) throws Exception {
		//
		try {
			//
			Boolean success = candidateDao.updateCandidateProfile(jobDivaSession, candidateid, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, phones, currentsalary, currentsalaryunit, preferredsalary,
					preferredsalaryunit);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateProfile", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateProfile", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCandidateUserfields(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, Userfield[] userfields) throws Exception {
		//
		try {
			//
			Boolean success = candidateUserFieldsDao.updateCandidateUserfields(jobDivaSession, candidateid, overwrite, userfields);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateUserfields", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateUserfields", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCandidateQualifications(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, QualificationType[] qualifications) throws Exception {
		//
		try {
			Boolean success = candidateQualificationsDao.updateCandidateQualifications(jobDivaSession, candidateid, overwrite, qualifications);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateQualifications", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateQualifications", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCandidateAvailability(JobDivaSession jobDivaSession, Long candidateid, Boolean availablenow, Boolean unavailableindef, Boolean unavailableuntil, Date unavailableuntildate, String reason) throws Exception {
		try {
			//
			Boolean success = candidateAvailabilityDao.updateCandidateAvailability(jobDivaSession, candidateid, availablenow, unavailableindef, unavailableuntil, unavailableuntildate, reason);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateAvailability", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateAvailability", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Long createCandidateNote(JobDivaSession jobDivaSession, Long candidateid, String note, Long recruiterid, String action, Date actionDate, Long link2AnOpenJob, Long link2aContact, Boolean setAsAuto) throws Exception {
		//
		try {
			Long noteID = candidateNoteDao.createCandidateNote(jobDivaSession, candidateid, note, recruiterid, action, actionDate, link2AnOpenJob, link2aContact, setAsAuto);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateNote", "Create Successful");
			//
			return noteID;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateNote", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCandidateHRRecord(JobDivaSession jobDivaSession, Long candidateid, Date dateofbirth, String legalfirstname, String legallastname, String legalmiddlename, String suffix, Integer maritalstatus, String ssn, String visastatus)
			throws Exception {
		//
		try {
			Boolean success = candidateHRRecordDao.updateCandidateHRRecord(jobDivaSession, candidateid, dateofbirth, legalfirstname, legallastname, legalmiddlename, suffix, maritalstatus, ssn, visastatus);
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateHRRecord", "Update Successful");
			//
			return success;
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateHRRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
}
