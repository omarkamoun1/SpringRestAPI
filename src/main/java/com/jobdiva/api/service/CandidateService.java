package com.jobdiva.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.candidate.CandidateAvailabilityDao;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.dao.candidate.CandidateHRRecordDao;
import com.jobdiva.api.dao.candidate.CandidateNoteDao;
import com.jobdiva.api.dao.candidate.CandidateQualificationsDao;
import com.jobdiva.api.dao.candidate.CandidateUDFDao;
import com.jobdiva.api.dao.candidate.CandidateUserFieldsDao;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.NoteType;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.SocialNetworkType;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.v2.candidate.CreateCandidateProfileDef;

@Service
public class CandidateService extends AbstractService {
	
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
	
	public Long createCandidate(JobDivaSession jobDivaSession, String firstName, String lastName, String email, String alternateemail, String address1, String address2, String city, String state, String zipCode, String countryid, String homephone,
			String workphone, String cellphone, String fax, Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit, String narrative, //
			TitleSkillCertification[] titleskillcertifications, String titleskillcertification, Date startdate, Date enddate, Integer years, Integer resumeSource) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long candidateId = candidateDao.createCandidate(jobDivaSession, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, homephone, workphone, cellphone, fax, currentsalary,
								currentsalaryunit, preferredsalary, preferredsalaryunit, narrative, titleskillcertifications, titleskillcertification, startdate, enddate, years, resumeSource);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidate", "Search Successful");
						//
						return candidateId;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidate", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean createCandidates(JobDivaSession jobDivaSession, List<CreateCandidateProfileDef> createCandidateProfileDefs) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean value = candidateDao.createCandidates(jobDivaSession, createCandidateProfileDefs);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidates", "Search Successful");
						//
						return value;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidates", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateProfile(JobDivaSession jobDivaSession, Long candidateid, String firstName, String lastName, String email, String alternateemail, String address1, String address2, String city, String state, String zipCode,
			String countryid, PhoneType[] phones, Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateDao.updateCandidateProfile(jobDivaSession, candidateid, firstName, lastName, email, alternateemail, address1, address2, city, state, zipCode, countryid, phones, currentsalary, currentsalaryunit,
								preferredsalary, preferredsalaryunit);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateProfile", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateProfile", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateUserfields(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, Userfield[] userfields) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateUserFieldsDao.updateCandidateUserfields(jobDivaSession, candidateid, overwrite, userfields);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateUserfields", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateUserfields", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateQualifications(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, QualificationType[] qualifications) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateQualificationsDao.updateCandidateQualifications(jobDivaSession, candidateid, overwrite, qualifications);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateQualifications", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateQualifications", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateAvailability(JobDivaSession jobDivaSession, Long candidateid, Boolean availablenow, Boolean unavailableindef, Boolean unavailableuntil, Date unavailableuntildate, String reason) throws Exception {
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateAvailabilityDao.updateCandidateAvailability(jobDivaSession, candidateid, availablenow, unavailableindef, unavailableuntil, unavailableuntildate, reason);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateAvailability", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateAvailability", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Long createCandidateNote(JobDivaSession jobDivaSession, Long candidateid, String note, Long recruiterid, String action, Date actionDate, Long link2AnOpenJob, Long link2aContact, Boolean setAsAuto, Date createDate) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long noteID = candidateNoteDao.createCandidateNote(jobDivaSession, candidateid, note, recruiterid, action, actionDate, link2AnOpenJob, link2aContact, setAsAuto, createDate);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateNote", "Create Successful");
						//
						return noteID;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateNote", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean createCandidatesNote(JobDivaSession jobDivaSession, List<Long> candidateids, String note, Long recruiterid, String action, Date actionDate, Long link2AnOpenJob, Long link2aContact, Boolean setAsAuto, Date createDate)
			throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean value = candidateNoteDao.createCandidatesNote(jobDivaSession, candidateids, note, recruiterid, action, actionDate, link2AnOpenJob, link2aContact, setAsAuto, createDate);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidatesNote", "Create Successful");
						//
						return value;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidatesNote", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateHRRecord(JobDivaSession jobDivaSession, Long candidateid, Date dateofbirth, String legalfirstname, String legallastname, String legalmiddlename, String suffix, Integer maritalstatus, String ssn, String visastatus)
			throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateHRRecordDao.updateCandidateHRRecord(jobDivaSession, candidateid, dateofbirth, legalfirstname, legallastname, legalmiddlename, suffix, maritalstatus, ssn, visastatus);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateHRRecord", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateHRRecord", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * @param jobDivaSession
	 * @param candidateid
	 * @param contactid
	 * @param createdByRecruiterid
	 * @param checkedByRecruiterid
	 * @param dateChecked
	 * @param notes
	 * @param standardQuestions
	 * @return
	 * @throws Exception
	 */
	public Boolean createCandidateReference(JobDivaSession jobDivaSession, Long candidateid, Long contactid, Long createdByRecruiterid, Long checkedByRecruiterid, Date dateChecked, String notes, String standardQuestions) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateDao.createCandidateReference(jobDivaSession, candidateid, contactid, createdByRecruiterid, checkedByRecruiterid, dateChecked, notes, standardQuestions);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateReference", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createCandidateReference", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * @param jobDivaSession
	 * @param candidateid
	 * @param backonemailmerge
	 * @param requestoffemailindef
	 * @param requestoffemailuntil
	 * @param requestoffemailuntildate
	 * @param reason
	 * @return
	 * @throws Exception
	 */
	public Boolean updateCandidateEmailMerge(JobDivaSession jobDivaSession, Long candidateid, Boolean backonemailmerge, Boolean requestoffemailindef, Boolean requestoffemailuntil, Date requestoffemailuntildate, String reason) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateDao.updateCandidateEmailMerge(jobDivaSession, candidateid, backonemailmerge, requestoffemailindef, requestoffemailuntil, requestoffemailuntildate, reason);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateEmailMerge", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateEmailMerge", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateCandidateSNLinks(JobDivaSession jobDivaSession, Long candidateid, SocialNetworkType[] socialNetworkType) throws Exception {
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean success = candidateDao.updateCandidateSNLinks(jobDivaSession, candidateid, socialNetworkType);
						//
						candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateSNLinks", "Update Successful");
						//
						return success;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			candidateDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateCandidateSNLinks", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	//
	public List<NoteType> getCandidateNoteTypes(JobDivaSession jobDivaSession) throws Exception {
		//
		try {
			//
			List<NoteType> contactNoteTypes = candidateNoteDao.getCandidateNoteTypes(jobDivaSession);
			//
			candidateNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCandidateNoteTypes", "Get Candidate Note Types Successful ");
			//
			return contactNoteTypes;
			//
		} catch (Exception e) {
			//
			candidateNoteDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "getCandidateNoteTypes", "Get Candidate Note Types Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
	}
}
