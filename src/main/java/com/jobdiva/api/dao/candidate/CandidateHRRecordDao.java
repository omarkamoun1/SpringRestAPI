package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.model.CandidateHRRecord;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CandidateHRRecordDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateDao	candidateDao;
	//
	@Autowired
	JobDao			jobDao;
	//
	@Autowired
	ContactDao		contactDao;
	
	private void saveCandidateHRRecord(JobDivaSession jobDivaSession, CandidateHRRecord candidateHRRecord) {
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("CANDIDATEID");
		paramList.add(candidateHRRecord.getCandidateId());
		//
		fields.add("TEAMID");
		paramList.add(candidateHRRecord.getTeamId());
		//
		if (candidateHRRecord.getDateOfBirth() != null) {
			fields.add("DATEOFBIRTH");
			paramList.add(candidateHRRecord.getDateOfBirth());
		}
		//
		if (candidateHRRecord.getDobMonth() != null) {
			fields.add("DATEOFBIRTH_MONTH");
			paramList.add(candidateHRRecord.getDobMonth());
		}
		//
		if (candidateHRRecord.getDobDay() != null) {
			fields.add("DATEOFBIRTH_DAY");
			paramList.add(candidateHRRecord.getDobDay());
		}
		//
		if (candidateHRRecord.getDobYear() != null) {
			fields.add("DATEOFBIRTH_YEAR");
			paramList.add(candidateHRRecord.getDobYear());
		}
		//
		if (isNotEmpty(candidateHRRecord.getSuffix())) {
			fields.add("GENERATIONSUFFIX");
			paramList.add(candidateHRRecord.getSuffix());
		}
		//
		if (isNotEmpty(candidateHRRecord.getLegalFirstName())) {
			fields.add("LEGAL_FIRSTNAME");
			paramList.add(candidateHRRecord.getLegalFirstName());
		}
		//
		if (isNotEmpty(candidateHRRecord.getLegalLastName())) {
			fields.add("LEGAL_LASTNAME");
			paramList.add(candidateHRRecord.getLegalLastName());
		}
		//
		if (isNotEmpty(candidateHRRecord.getLegalMiddleName())) {
			fields.add("LEGAL_MIDDLENAME");
			paramList.add(candidateHRRecord.getLegalMiddleName());
		}
		//
		if (candidateHRRecord.getMaritalStatus() != null) {
			fields.add("MARITALSTATUS");
			paramList.add(candidateHRRecord.getMaritalStatus());
		}
		//
		if (isNotEmpty(candidateHRRecord.getSsn())) {
			fields.add("SSN");
			paramList.add(candidateHRRecord.getSsn());
		}
		//
		if (isNotEmpty(candidateHRRecord.getVisaStatus())) {
			fields.add("VISASTATUS");
			paramList.add(candidateHRRecord.getVisaStatus());
		}
		//
		//
		String sqlExists = " SELECT CANDIDATEID FROM TCANDIDATE_HR WHERE TEAMID = ? and CANDIDATEID = ? ";
		Object[] params = new Object[] { candidateHRRecord.getTeamId(), candidateHRRecord.getCandidateId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sqlExists, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("CANDIDATEID");
			}
		});
		//
		//
		Boolean insertMode = list == null || list.size() == 0;
		//
		if (insertMode) {
			//
			String sqlInsert = "INSERT INTO TCANDIDATE_HR (" + sqlInsertFields(fields) + ") VALUES (" + sqlInsertParams(fields) + ") ";
			//
			Object[] parameters = paramList.toArray();
			jdbcTemplate.update(sqlInsert, parameters);
		} else {
			//
			String sqlUpdate = "UPDATE TCANDIDATE_HR SET  " + sqlUpdateFields(fields) + " WHERE TEAMID = ? and CANDIDATEID = ? ";
			paramList.add(candidateHRRecord.getTeamId());
			paramList.add(candidateHRRecord.getCandidateId());
			//
			Object[] parameters = paramList.toArray();
			jdbcTemplate.update(sqlUpdate, parameters);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Boolean updateCandidateHRRecord(JobDivaSession jobDivaSession, Long candidateid, Date dateofbirth, String legalfirstname, String legallastname, //
			String legalmiddlename, String suffix, Integer maritalstatus, String ssn, String visastatus) throws Exception {
		//
		//
		StringBuffer message = new StringBuffer();
		//
		if (isNotEmpty(ssn)) {
			ssn = ssn.trim().replaceAll("[^0-9]", "");
		}
		//
		if (candidateid < 1) {
			message.append("Error: invalid candidate ID(" + candidateid + "). ");
		}
		//
		if (legalfirstname != null && legalfirstname.length() > 500)
			message.append("Error: legal first name should be shorter than 500 chars. ");
		//
		if (legallastname != null && legallastname.length() > 500)
			message.append("Error: legal last name should be shorter than 500 chars. ");
		//
		if (legalmiddlename != null && legalmiddlename.length() > 500)
			message.append("Error: legal middle name should be shorter than 500 chars. ");
		//
		if (suffix != null && suffix.length() > 10)
			message.append("Error: suffix should be shorter than 10 chars. ");
		//
		// if (maritalstatus != null && (maritalstatus < 0 || maritalstatus >
		// 8))
		// Modif By Sarah Chahine
		if (maritalstatus != null && (maritalstatus < 0 || maritalstatus > 9))
			message.append("Error: invalid marital status(" + maritalstatus + "). ");
		//
		if (ssn != null && ssn.length() != 9)
			message.append("Error: SSN should have night(9) digits. ");
		//
		if (visastatus != null && visastatus.length() > 100)
			message.append("Error: visa status should be shorter than 100 chars. ");
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed " + message.toString());
		}
		//
		CandidateHRRecord candidateHRRecord = new CandidateHRRecord();
		candidateHRRecord.setCandidateId(candidateid);
		candidateHRRecord.setTeamId(jobDivaSession.getTeamId());
		candidateHRRecord.setSuffix(suffix);
		candidateHRRecord.setLegalFirstName(legalfirstname);
		candidateHRRecord.setLegalLastName(legallastname);
		candidateHRRecord.setLegalMiddleName(legalmiddlename);
		if (maritalstatus != null && maritalstatus > 0) {
			candidateHRRecord.setMaritalStatus(maritalstatus);
		}
		candidateHRRecord.setSsn(ssn);
		candidateHRRecord.setVisaStatus(visastatus);
		//
		if (dateofbirth != null) {
			candidateHRRecord.setDateOfBirth(dateofbirth);
			candidateHRRecord.setDobYear(dateofbirth.getYear());
			candidateHRRecord.setDobMonth(dateofbirth.getMonth());
			candidateHRRecord.setDobDay(dateofbirth.getDate());
		}
		//
		saveCandidateHRRecord(jobDivaSession, candidateHRRecord);
		//
		return true;
	}
}
