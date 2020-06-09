package com.jobdiva.api.dao.candidate;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.qualification.QualificationDao;
import com.jobdiva.api.model.Qualification;
import com.jobdiva.api.model.QualificationOption;
import com.jobdiva.api.model.QualificationType;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CandidateQualificationsDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao		candidateDao;
	//
	@Autowired
	QualificationDao	qualificationDao;
	//
	
	private void deleteCandidateCategories(JobDivaSession jobDivaSession, Long candidateid) {
		String sqlDelete = "DELETE FROM TCANDIDATE_CATEGORY Where CANDIDATEID = ? AND TEAMID = ? ";
		Object[] params = new Object[] { candidateid, jobDivaSession.getTeamId() };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
	
	private void deleteCandidateCategories(JobDivaSession jobDivaSession, Long candidateid, Integer catId) {
		String sqlDelete = "DELETE FROM TCANDIDATE_CATEGORY Where CANDIDATEID = ? AND TEAMID = ? AND CATID = ? ";
		Object[] params = new Object[] { candidateid, jobDivaSession.getTeamId(), catId };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlDelete, params);
	}
	
	private void insertCandidateCateory(JobDivaSession jobDivaSession, Long candidateid, Integer catId, Integer dcatId, int dirty, Timestamp datecreated, Long recruiterid) {
		//
		String sqlInsert = "INSERT INTO TCANDIDATE_CATEGORY " //
				+ " (CANDIDATEID, TEAMID, CATID, DCATID, DIRTY, DATECREATED, RECRUITERID) " //
				+ "VALUES " //
				+ "(?, ?, ?, ?, ?, ?, ?) ";
		Object[] params = new Object[] { candidateid, jobDivaSession.getTeamId(), catId, dcatId, dirty, datecreated, recruiterid };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlInsert, params);
	}
	
	private String[] checkQualificationNOptions(long teamid, QualificationType jdQual, Hashtable<String, Integer> hashDcats, boolean to_update) throws Exception {
		//
		Boolean isMultipelChoice = false;
		//
		int catId = jdQual.getQualificationTypeId();
		// check catId
		Qualification qualification = qualificationDao.getQualificationByCatId(teamid, catId);
		if (qualification == null || qualification.getClosed())
			throw new Exception("Error: Qualification(" + catId + ") is not found. \r\n");
		//
		//
		// unauthorized update
		if (to_update) {
			if (qualification.getAuthRequired() != null && qualification.getAuthRequired())
				throw new Exception("Error: Autherization is needed to update Qualification(" + catId + "). \r\n ");
		}
		isMultipelChoice = qualification.getMultipleChoice() == null ? false : qualification.getMultipleChoice();
		//
		//
		// hash dcatName and dcatId
		List<QualificationOption> qualificationOptions = qualificationDao.getQualificationOptions(teamid, catId);
		if (qualificationOptions == null || qualificationOptions.size() == 0) {
			throw new Exception("Error: No available options for qualification(" + catId + ") is not found. \r\n");
		}
		//
		for (QualificationOption qualOption : qualificationOptions) {
			if (qualOption.getActive() == null || !qualOption.getActive())
				continue;
			hashDcats.put(qualOption.getDcatName(), qualOption.getDcatId());
		}
		// check dcatName
		String[] jdDcats = null;
		String jdQualValue = jdQual.getQualificationValue().trim();
		if (to_update) {
			if (isMultipelChoice) {
				jdDcats = jdQualValue.split(",");
				for (int i = 0; i < jdDcats.length; i++) {
					jdDcats[i] = jdDcats[i].trim();
					if (!hashDcats.containsKey(jdDcats[i]))
						throw new Exception("Error: \'" + jdDcats[i] + "\' is either not an option or not yet activated for multi-selection qualification(" + catId + "). \r\n ");
				}
			} else {
				if (!hashDcats.containsKey(jdQualValue))
					throw new Exception("Error: \'" + jdQualValue + "\' is either not an option or not yet activated for single-selection qualification(" + catId + "). \r\n ");
				jdDcats = new String[1];
				jdDcats[0] = jdQualValue;
			}
		} else {
			jdDcats = jdQualValue.split(",");
			for (int i = 0; i < jdDcats.length; i++) {
				jdDcats[i] = jdDcats[i].trim();
				if (!hashDcats.containsKey(jdDcats[i]))
					throw new Exception("Error: \'" + jdDcats[i] + "\' is either not an option or not yet activated for qualification(" + catId + "). \r\n ");
			}
		}
		return jdDcats;
	}
	
	public Boolean updateCandidateQualifications(JobDivaSession jobDivaSession, Long candidateid, Boolean overwrite, QualificationType[] qualifications) throws Exception {
		//
		//
		if (qualifications != null) {
			for (int i = 0; i < qualifications.length; i++) {
				if (qualifications[i].getQualificationTypeId() < 1)
					throw new Exception("Error: Invalid userfieldId(" + qualifications[i].getQualificationTypeId() + "). \r\n");
			}
		}
		//
		//
		Boolean existCandidate = candidateDao.existCandidate(jobDivaSession, candidateid);
		if (!existCandidate) {
			throw new Exception("Error: Candidate(" + candidateid + ") is not found.");
		}
		if (overwrite) {
			// delete pre-existing records when overwrite is set
			deleteCandidateCategories(jobDivaSession, candidateid);
		}
		//
		for (int i = 0; i < qualifications.length; i++) {
			//
			QualificationType qualificationType = qualifications[i];
			//
			Hashtable<String, Integer> hashDcats = new Hashtable<String, Integer>();
			//
			//
			String[] jdDcats = checkQualificationNOptions(jobDivaSession.getTeamId(), qualificationType, hashDcats, true);
			// updates
			if (!overwrite) { // delete pre-existing qualifications with the
								// same catid of updates
				//
				deleteCandidateCategories(jobDivaSession, candidateid, qualificationType.getQualificationTypeId());
			}
			//
			for (int i1 = 0; i1 < jdDcats.length; i1++) {
				// jdDcats[i] = jdDcats[i].trim();
				insertCandidateCateory(jobDivaSession, candidateid, qualificationType.getQualificationTypeId(), hashDcats.get(jdDcats[i1]), 1, new Timestamp(System.currentTimeMillis()), jobDivaSession.getRecruiterId());
			}
		}
		//
		return true;
	}
}
