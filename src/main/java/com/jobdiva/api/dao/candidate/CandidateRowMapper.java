package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Candidate;

public class CandidateRowMapper implements RowMapper<Candidate> {
	
	@Override
	public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
		Candidate candidate = new Candidate();
		//
		candidate.setId(rs.getLong("ID"));
		candidate.setTeamId(rs.getLong("TEAMID"));
		candidate.setFirstName(rs.getString("FIRSTNAME"));
		candidate.setLastName(rs.getString("LASTNAME"));
		candidate.setAddress1(rs.getString("ADDRESS1"));
		candidate.setAddress2(rs.getString("ADDRESS2"));
		candidate.setCity(rs.getString("CITY"));
		candidate.setState(rs.getString("STATE"));
		candidate.setZipCode(rs.getString("ZIPCODE"));
		candidate.setProvince(rs.getString("PROVINCE"));
		candidate.setCountry(rs.getString("COUNTRYID"));
		candidate.setHomePhone(rs.getString("HOMEPHONE"));
		candidate.setWorkPhone(rs.getString("WORKPHONE"));
		candidate.setCellPhone(rs.getString("CELLPHONE"));
		candidate.setEmail(rs.getString("EMAIL"));
		candidate.setSysEmail(rs.getString("SYSEMAIL"));
		candidate.setPrivateName(rs.getString("PRIVATENAME"));
		candidate.setPrivateAddress(rs.getString("PRIVATEADDRESS"));
		candidate.setPrivatePhone(rs.getString("PRIVATEPHONE"));
		candidate.setPrivateEmail(rs.getString("PRIVATEEMAIL"));
		candidate.setDateReceived(rs.getDate("DATERECEIVED"));
		candidate.setDateUpdated(rs.getDate("DATEUPDATED"));
		candidate.setAvailable(rs.getBoolean("AVAILABLE"));
		candidate.setDateAvailable(rs.getDate("DATEAVAILABLE"));
		candidate.setCurrentSalary(rs.getBigDecimal("CURRENTSALARY"));
		candidate.setCurrrentSalaryPer(rs.getString("CURRENTSALARYPER"));
		candidate.setPreferredSalaryMin(rs.getBigDecimal("PREFERREDSALARYMIN"));
		candidate.setPreferredSalaryMax(rs.getBigDecimal("PREFERREDSALARYMAX"));
		candidate.setPreferredSalaryPer(rs.getString("PREFERREDSALARYPER"));
		candidate.setPassword(rs.getString("PASSWORD"));
		candidate.setPrivateSalary(rs.getBoolean("PRIVATESALARY"));
		candidate.setMiddleInitial(rs.getString("MIDDLEINITIAL"));
		candidate.setZnoltr(rs.getBoolean("ZNOLTR"));
		candidate.setFax(rs.getString("FAX"));
		candidate.setBeeper(rs.getString("BEEPER"));
		candidate.setRetagRequired(rs.getBoolean("RETAG_REQUIRED"));
		candidate.setRecentDocid(rs.getByte("RECENT_DOCID"));
		candidate.setDateupdatedManual(rs.getDate("DATEUPDATED_MANUAL"));
		candidate.setConfidential(rs.getBoolean("CONFIDENTIAL"));
		candidate.setDateUpdatedCandidate(rs.getDate("DATEUPDATED_CANDIDATE"));
		candidate.setWorkphoneExt(rs.getString("WORKPHONE_EXT"));
		candidate.setAlternateEmail(rs.getString("SYSEMAIL"));
		candidate.setAvailabilityType(rs.getBoolean("AVAILABILITYTYPE"));
		candidate.setEeoSent(rs.getBoolean("EEO_SENT"));
		candidate.setLastemailRcvd(rs.getDate("LASTEMAIL_RCVD"));
		candidate.setLicEmailAlert(rs.getBoolean("LIC_EMAIL_ALERT"));
		// contact.setCurrentSalaryCurrency(rs.getInt("CURRENTSALARY_CURRENCY"));
		// contact.setPreferredSalaryCurrency(rs.getInt("PREFERREDSALARY_CURRENCY"));
		// contact.setPipelineUrlExpired(rs.getBoolean("PIPELINE_URL_EXPIRED"));
		candidate.setEeoSent(rs.getBoolean("EEO_SENT_START"));
		candidate.setHomephoneExt(rs.getString("HOMEPHONE_EXT"));
		candidate.setCellphoneExt(rs.getString("CELLPHONE_EXT"));
		candidate.setFaxExt(rs.getString("FAX_EXT"));
		candidate.setPhoneTypes(rs.getString("PHONE_TYPES"));
		candidate.setTimePwchanged(rs.getDate("TIMEPWCHANGED"));
		//
		return candidate;
	}
}
