package com.jobdiva.api.dao.activity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.Activity;

public class ActivityRowMapper implements RowMapper<Activity> {
	
	private AbstractActivityDao abstractActivityDao;
	
	public ActivityRowMapper(AbstractActivityDao abstractActivityDao) {
		super();
		this.abstractActivityDao = abstractActivityDao;
	}
	
	@Override
	public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
		Activity activity = new Activity();
		//
		activity.setId(rs.getLong("ID"));
		activity.setJobId(rs.getLong("RFQID"));
		activity.setCandidateId(rs.getLong("CANDIDATEID"));
		activity.setRecruiterId(rs.getLong("RECRUITERID"));
		activity.setRecruiterTeamId(rs.getLong("RECRUITER_TEAMID"));
		activity.setCandidateTeamId(rs.getLong("CANDIDATE_TEAMID"));
		//
		// CANDIDATE
		String str = "";
		if (rs.getString("FIRSTNAME") != null)
			str = rs.getString("FIRSTNAME");
		//
		if (rs.getString("LASTNAME") != null)
			str += " " + rs.getString("LASTNAME");
		activity.setCandidateName(str);
		//
		activity.setCandidateEmail(rs.getString("EMAIL"));
		//
		String address1 = rs.getString("ADDRESS1");
		String address2 = rs.getString("ADDRESS2");
		String city = rs.getString("CITY");
		String state = rs.getString("STATE");
		String zipCode = rs.getString("ZIPCODE");
		str = abstractActivityDao.formatCandidateAddress(address1, address2, city, state, zipCode);
		activity.setCandidateAddress(str);
		//
		//
		String phoneTypes = rs.getString("PHONE_TYPES");
		if (phoneTypes == null)
			phoneTypes = "0123";
		//
		if (phoneTypes.length() >= 4) {
			String hompePhone = rs.getString("HOMEPHONE");
			String hompePhoneExt = rs.getString("HOMEPHONE");
			String workPhone = rs.getString("WORKPHONE");
			String workPhoneExt = rs.getString("WORKPHONE");
			String cellPhone = rs.getString("CELLPHONE");
			String cellPhoneExt = rs.getString("CELLPHONE");
			String fax = rs.getString("FAX");
			String faxExt = rs.getString("FAX");
			str = abstractActivityDao.formatCandidatePhones(hompePhone, hompePhoneExt, workPhone, workPhoneExt, cellPhone, cellPhoneExt, fax, faxExt, phoneTypes);
			activity.setCandidatePhones(str);
		}
		//
		//
		activity.setDatePresented(rs.getDate("DATEPRESENTED"));
		activity.setDateInterview(rs.getDate("DATEINTERVIEW"));
		activity.setDateHired(rs.getDate("DATEHIRED"));
		activity.setDateEnded(rs.getDate("DATE_ENDED"));
		activity.setContract(rs.getInt("CONTRACT"));
		//
		// RECURUTIERNAME
		String recruiterFirstName = rs.getString("RecruiterFIRSTNAME");
		String recruiterlastName = rs.getString("RecruiterLASTNAME");
		if (recruiterFirstName != null || recruiterlastName != null) {
			//
			String name = recruiterFirstName != null ? recruiterFirstName : "";
			if (recruiterlastName != null)
				name += " " + recruiterlastName;
			//
			activity.setRecruiterName(name);
		}
		//
		//
		activity.setManagerFirstName(rs.getString("MANAGERFIRSTNAME"));
		activity.setManagerLastName(rs.getString("MANAGERLASTNAME"));
		activity.setNotes(rs.getString("NOTES"));
		activity.setPayHourly(rs.getBigDecimal("PAY_HOURLY"));
		//
		String rateUnit = "";
		String currencyUnit = "";
		//
		if (rs.getInt("FINALBILLRATE_CURRENCY") == 0) {
			currencyUnit = "USD";
		} else {
			currencyUnit = abstractActivityDao.getCurrencyString(rs.getInt("FINALBILLRATE_CURRENCY"));
		}
		//
		activity.setDbFinalBillRateUnit(rs.getString("FINALBILLRATEUNIT"));
		//
		if (rs.getString("FINALBILLRATEUNIT") == null || rs.getString("FINALBILLRATEUNIT").length() == 0) {
			rateUnit = "Hour";
		} else {
			rateUnit = abstractActivityDao.getRateUnitString(rs.getString("FINALBILLRATEUNIT").toLowerCase().charAt(0), false);
		}
		//
		activity.setFinalBillRateUnit(currencyUnit + "/" + rateUnit);
		//
		activity.setHourly(activity.getHourly());
		//
		// formated along with HOURLY_CURRENCY
		if (rs.getInt("HOURLY_CURRENCY") == 0) {
			currencyUnit = "USD";
		} else {
			currencyUnit = abstractActivityDao.getCurrencyString(rs.getInt("HOURLY_CURRENCY"));
		}
		//
		if (rs.getString("PAYRATEUNITS") == null || rs.getString("PAYRATEUNITS").length() == 0) {
			rateUnit = "Hour";
		} else {
			rateUnit = abstractActivityDao.getRateUnitString(rs.getString("PAYRATEUNITS").toLowerCase().charAt(0), false);
		}
		//
		activity.setPayRateUnits(currencyUnit + "/" + rateUnit);
		//
		return activity;
	}
}
