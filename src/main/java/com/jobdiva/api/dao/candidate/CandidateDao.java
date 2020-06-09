package com.jobdiva.api.dao.candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.candidate.CandidateData;
import com.axelon.candidate.DocumentObjectResume;
import com.axelon.candidate.WorkExperience;
import com.axelon.oc4j.ServletRequestData;
import com.axelon.shared.Zipper;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.qualification.QualificationDao;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.CandidateQual;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Qualification;
import com.jobdiva.api.model.QualificationOption;
import com.jobdiva.api.model.TitleSkillCertification;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;
import com.jobdiva.api.utils.StringUtils;

@Component
public class CandidateDao extends AbstractJobDivaDao {
	
	@Autowired
	QualificationDao qualificationDao;
	
	public Candidate getCandidate(JobDivaSession jobDivaSession, Long candidateId) {
		String sql = " Select * "//
				+ " FROM TCANDIDATE " //
				+ " WHERE ID = ?  AND TEAMID = ? ";
		//
		Object[] params = new Object[] { candidateId, jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Candidate> list = jdbcTemplate.query(sql, params, new CandidateRowMapper());
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	public Boolean existCandidate(JobDivaSession jobDivaSession, Long candidateId) {
		String sql = " Select "//
				+ " ID " //
				+ " FROM TCANDIDATE " //
				+ " WHERE ID = ?  AND TEAMID = ? ";
		//
		Object[] params = new Object[] { candidateId, jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		return list != null && list.size() > 0;
	}
	
	public List<Long> getCandidateIds(JobDivaSession jobDivaSession, String candidatefirstname, String candidatelastname, String candidateemail) {
		String sql = " Select "//
				+ " ID " //
				+ " FROM TCANDIDATE " //
				+ " WHERE  TEAMID = ? AND ROWNUM <= 5";
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(jobDivaSession.getTeamId());
		//
		if (isNotEmpty(candidatefirstname)) {
			sql += " and nls_upper(FIRSTNAME) like ? ";
			paramList.add(candidatefirstname.toUpperCase() + "%");
		}
		//
		if (isNotEmpty(candidatelastname)) {
			sql += " and nls_upper(LASTNAME) like ? ";
			paramList.add(candidatelastname.toUpperCase() + "%");
		}
		//
		if (isNotEmpty(candidateemail)) {
			sql += " and nls_upper(EMAIL) like ? ";
			paramList.add(candidateemail.toUpperCase() + "%");
		}
		//
		Object[] params = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		return list;
	}
	
	private String[] checkQualificationNOptions(Long teamid, CandidateQual candidateQual, Hashtable<String, Integer> hashDcats, boolean to_update) throws Exception {
		//
		//
		Boolean isMultipelChoice = false;
		int catId = candidateQual.getCatId();
		//
		// check catId
		Qualification qualification = qualificationDao.getQualificationByCatId(catId);
		if (qualification == null || qualification.getClosed())
			throw new Exception("Error: Qualification(" + catId + ") is not found. \r\n");
		//
		//
		// unauthorized update
		if (to_update) {
			if (qualification.getAuthRequired() != null && qualification.getAuthRequired())
				throw new Exception("Error: Autherization is needed to update Qualification(" + catId + "). \r\n ");
		}
		//
		isMultipelChoice = qualification.getMultipleChoice() == null ? false : qualification.getMultipleChoice();
		//
		List<QualificationOption> qualificationOptions = qualificationDao.getQualificationOptions(teamid, catId);
		//
		//
		if (qualificationOptions == null || qualificationOptions.isEmpty())
			throw new Exception("Error: No available options for qualification(" + catId + ") is not found. \r\n");
		//
		//
		for (QualificationOption qualOption : qualificationOptions) {
			if (qualOption.getActive() == null || !qualOption.getActive())
				continue;
			hashDcats.put(qualOption.getDcatName(), qualOption.getDcatId());
		}
		//
		// check dcatName
		String[] jdDcats = null;
		String jdQualValue = candidateQual.getDcatNames().trim();
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
	
	public List<Candidate> searchCandidates(JobDivaSession jobDivaSession, String firstName, String lastName, String address, String city, String state, String zipCode, //
			String country, String email, String phone, CandidateQual[] qualifications, Integer rowLimit) throws Exception {
		//
		if (isNotEmpty(phone)) {
			phone = Pattern.compile(NON_DIGITS).matcher(phone).replaceAll("");
			if (phone.length() > 20)
				throw new Exception("Error: 'phone' should be no more than 20 numbers. \r\n");
			else if (phone.length() > 0) {
			} else
				phone = null;
		}
		//
		if (isNotEmpty(country)) {
			country = getCountryID(country.trim());
			if (country.equals("not found"))
				throw new Exception("Error: 'country' parameter mapping unfound. \r\n");
		}
		//
		if (qualifications != null) {
			for (int i = 0; i < qualifications.length; i++) {
				if (qualifications[i].getCatId() < 1 || isNotEmpty(qualifications[i].getDcatNames()))
					throw new Exception(String.format("Error: Invalid userfield id(%d) or userfield value(%s). \r\n", qualifications[i].getCatId(), qualifications[i].getDcatNames()));
			}
		}
		//
		ArrayList<Object> parameters = new ArrayList<Object>();
		boolean has_email = false;
		boolean has_firstname = false;
		boolean has_lastname = false;
		//
		/*----------------------- Query Candidate ----------------------------------*/
		StringBuffer queryString = new StringBuffer(" * FROM TCANDIDATE WHERE TEAMID = ? ");
		parameters.add(jobDivaSession.getTeamId());
		//
		if (isNotEmpty(firstName) && firstName.length() > 2) {
			queryString.append(" AND NLS_UPPER(FIRSTNAME) LIKE ?");
			parameters.add(firstName.toUpperCase() + "%");
			has_firstname = true;
		}
		//
		//
		if (isNotEmpty(lastName) && lastName.length() > 2) {
			queryString.append(" AND NLS_UPPER(LASTNAME) LIKE ?");
			parameters.add(lastName.toUpperCase() + "%");
			has_lastname = true;
		}
		//
		//
		if (isNotEmpty(email) && email.length() > 2) {
			queryString.append(" AND (NLS_UPPER(EMAIL) LIKE ? escape '\\' OR NLS_UPPER(SYSEMAIL) LIKE ? escape '\\')");
			String emailparam = email.toUpperCase().replace("_", "\\_") + "%";
			parameters.add(emailparam);
			parameters.add(emailparam);
			has_email = true;
		}
		//
		if (isNotEmpty(phone) && phone.length() > 2) {
			queryString.append(" and (workphone like ? or homephone like ? or cellphone like ? or fax like ?)");
			parameters.add(phone + "%");
			parameters.add(phone + "%");
			parameters.add(phone + "%");
			parameters.add(phone + "%");
		}
		//
		if (isNotEmpty(address)) {
			queryString.append(" and (NLS_UPPER(address1) like '%'||NLS_UPPER(?)||'%' or NLS_UPPER(address2) like '%'||NLS_UPPER(?)||'%')");
			parameters.add(address);
			parameters.add(address);
		}
		//
		//
		//
		if (isNotEmpty(city)) {
			queryString.append(" and NLS_UPPER(city) like NLS_UPPER(?)||'%'");
			parameters.add(city);
		}
		//
		if (isNotEmpty(state)) {
			String stateid = lookupState(state, country);
			if (stateid != null) {
				queryString.append(" and NLS_UPPER(state) = NLS_UPPER(?)");
				parameters.add(stateid);
			} else
				throw new Exception("Error: State (" + state + ") can not be identified.(with countryid(" + country + ")) \r\n");
		}
		//
		if (isNotEmpty(zipCode)) {
			queryString.append(" and NLS_UPPER(zipcode) like NLS_UPPER(?)||'%'");
			parameters.add(zipCode);
		}
		//
		if (isNotEmpty(country)) {
			queryString.append(" and NLS_UPPER(countryid) like NLS_UPPER(?)||'%'");
			parameters.add(country);
		}
		//
		//
		//
		if (qualifications != null && qualifications.length > 0) {
			for (int j = 0; j < qualifications.length; j++) {
				CandidateQual qualificationType = qualifications[j];
				//
				queryString.append(" and id in (select candidateid from tCandidate_Category where teamid=? ");
				parameters.add(jobDivaSession.getTeamId());
				//
				Integer catId = qualificationType.getCatId();
				//
				Hashtable<String, Integer> hashDcats = new Hashtable<String, Integer>();
				String[] jdDcats = checkQualificationNOptions(jobDivaSession.getTeamId(), qualificationType, hashDcats, false);
				queryString.append(" and (catid=? and (");
				parameters.add(catId);
				//
				for (int i = 0; i < jdDcats.length; i++) {
					jdDcats[i] = jdDcats[i].trim();
					if (i > 0)
						queryString.append(" or dcatid=? ");
					else
						queryString.append(" dcatid=? ");
					parameters.add(hashDcats.get(jdDcats[i]));
				}
				queryString.append(")) AND dirty<>2)");
			}
		}
		//
		queryString.append(" AND ROWNUM <= ? ORDER BY FIRSTNAME, LASTNAME"); // default
																				// to
																				// 201
		//
		if (rowLimit == null)
			rowLimit = 201;
		//
		parameters.add(rowLimit);
		//
		//
		String queryHint = "";
		if (has_email)
			queryHint = " /*+ INDEX (TCANDIDATE IDX_TCANDIDATE_EMAIL) INDEX(TCANDIDATE IDX_TCANDIDATE_SYSEMAIL) */ "; // email
																														// index
																														// is
																														// highest
																														// priority
		else if (has_firstname) {
			if (has_lastname) {
				if (firstName.length() > lastName.length())
					queryHint = " /*+ INDEX (TCANDIDATE IDX_TCANDIDATE_NAME_1)*/ ";
				else
					queryHint = " /*+ INDEX (TCANDIDATE IDX_TCANDIDATE_LASTNAME_1)*/ ";
			} else
				queryHint = " /*+ INDEX (TCANDIDATE IDX_TCANDIDATE_FIRSTNAME_1)*/ ";
		} else if (has_lastname) {
			queryHint = " /*+ INDEX (TCANDIDATE IDX_TCANDIDATE_LASTNAME_1)*/ ";
		}
		//
		//
		//
		String queryStringFinal = "SELECT " + queryHint + queryString.toString();
		//
		Object[] params = parameters.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Candidate> list = jdbcTemplate.query(queryStringFinal, params, new CandidateRowMapper());
		//
		return list;
		//
	}
	
	private void assignCondidatePhones(PhoneType[] phoneList, Candidate candidate, ArrayList<String> fields, ArrayList<Object> paramList) throws Exception {
		if (phoneList != null) {
			// Check phones & actions
			String[] numbers = { candidate.getWorkPhone(), candidate.getHomePhone(), candidate.getCellPhone(), candidate.getFax() };
			String[] exts = { candidate.getWorkphoneExt(), candidate.getHomephoneExt(), candidate.getCellphoneExt(), candidate.getFaxExt() };
			String typeStr = candidate.getPhoneTypes();
			if (typeStr == null)
				typeStr = "0123";
			String[] types = { typeStr.substring(0, 1), typeStr.substring(1, 2), typeStr.substring(2, 3), typeStr.substring(3, 4) };
			ArrayList<String> phones = new ArrayList<String>();
			for (int i = 0; i < 4; i++) {
				if (numbers[i] == null && exts[i] == null)
					continue;
				else if (types[i].equals("0"))
					phones.add(numbers[i] + "~" + exts[i] + "~" + types[i]);
				else if (numbers[i] == null)
					continue;
				else
					phones.add(numbers[i] + "~null~" + types[i]);
			}
			//
			for (int i = 0; i < phoneList.length; i++) {
				PhoneType phoneObj = phoneList[i];
				//
				String phone = phoneObj.getPhone() + "~" + phoneObj.getExt() + "~" + phoneObj.getType();
				if (phoneObj.getAction() == 1) {
					if (phones.contains(phone)) {
						// msg = "Phone(" + phoneObj.getPhone() + "), ext(" +
						// phoneObj.getExt() + ") & type(" + phoneObj.getType()
						// + ") already exists and wasn't inserted. \r\n";
					} else {
						phones.add(phone);
					}
				} else if (phoneObj.getAction() == 2) {
					if (phones.contains(phone))
						phones.remove(phones.indexOf(phone));
					else
						throw new Exception("Error: Phone(" + phoneObj.getPhone() + "), ext(" + phoneObj.getExt() + ") & type(" + phoneObj.getType() + ") doesn't exist and can't be deleted. \r\n");
				}
			}
			if (phones.size() > 4)
				throw new Exception("Error: # of Phones out of bound. 4 can be displayed at most.");
			//
			// Write back updates
			for (int i = 0; i < 4; i++) {
				numbers[i] = "";
				exts[i] = "";
			}
			//
			String phoneType = "";
			for (int i = 0; i < phones.size(); i++) {
				String[] str_arr = phones.get(i).split("~");
				if (!str_arr[0].equals("null"))
					numbers[i] = str_arr[0];
				if (!str_arr[1].equals("null"))
					exts[i] = str_arr[1];
				if (!str_arr[2].equals("null"))
					phoneType += str_arr[2];
			}
			//
			if (phoneType.length() < 4)
				phoneType += "    ";
			//
			fields.add("WORKPHONE");
			paramList.add(numbers[0]);
			//
			fields.add("WORKPHONE_EXT");
			paramList.add(exts[0]);
			//
			fields.add("HOMEPHONE");
			paramList.add(numbers[1]);
			//
			fields.add("HOMEPHONE_EXT");
			paramList.add(exts[1]);
			//
			fields.add("CELLPHONE");
			paramList.add(numbers[2]);
			//
			fields.add("CELLPHONE_EXT");
			paramList.add(exts[2]);
			//
			fields.add("FAX");
			paramList.add(numbers[3]);
			//
			fields.add("FAX_EXT");
			paramList.add(exts[3]);
			//
			fields.add("PHONE_TYPES");
			paramList.add(phoneType);
			//
		}
	}
	
	public Boolean updateCandidateProfile(JobDivaSession jobDivaSession, Long candidateid, String firstName, String lastName, String email, String alternateemail, //
			String address1, String address2, String city, String state, String zipCode, String countryid, PhoneType[] phoneList, //
			Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit) throws Exception {
		//
		//
		if (isNotEmpty(countryid)) {
			countryid = getCountryID(countryid);
			if (countryid.equals("not found"))
				throw new Exception("Error: Address cannot be updated due to the 'country' parameter mapping unfound. \r\n");
		}
		//
		checkPhoneType(phoneList, "candidate");
		//
		//
		Candidate candidate = getCandidate(jobDivaSession, candidateid);
		if (candidate == null) {
			throw new Exception("Error: Candidate(" + candidateid + ") is not found.");
		}
		//
		//
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		fields.add("TEAMID");
		paramList.add(jobDivaSession.getTeamId());
		//
		// set updated fields
		if (isNotEmpty(firstName)) {
			fields.add("FIRSTNAME");
			paramList.add(firstName.trim());
		}
		//
		if (isNotEmpty(lastName)) {
			fields.add("LASTNAME");
			paramList.add(lastName.trim());
		}
		//
		if (isNotEmpty(email)) {
			fields.add("EMAIL");
			paramList.add(email);
		}
		//
		if (isNotEmpty(alternateemail)) {
			fields.add("SYSEMAIL");
			paramList.add(alternateemail);
		}
		//
		if (isNotEmpty(address1)) {
			fields.add("ADDRESS1");
			paramList.add(address1);
		}
		//
		if (isNotEmpty(address2)) {
			fields.add("ADDRESS2");
			paramList.add(address2);
		}
		//
		if (isNotEmpty(city)) {
			fields.add("CITY");
			paramList.add(city);
		}
		//
		if (isNotEmpty(zipCode)) {
			fields.add("ZIPCODE");
			paramList.add(zipCode);
		}
		//
		if (isNotEmpty(state)) {
			String lookUpState = lookupState(state, countryid);
			if (lookUpState != null) {
				fields.add("STATE");
				paramList.add(lookUpState);
			} else {
				throw new Exception("Error: State (" + state + ") can not be updated due to the mapping unfound.(with countryid(" + countryid + ")) ");
			}
		}
		//
		if (isNotEmpty(countryid)) {
			fields.add("COUNTRYID");
			paramList.add(countryid);
		}
		//
		//
		//
		assignCondidatePhones(phoneList, candidate, fields, paramList);
		//
		//
		if (currentsalary != null) {
			fields.add("CURRENTSALARY");
			paramList.add(currentsalary);
		}
		if (isNotEmpty(currentsalaryunit)) {
			fields.add("CURRENTSALARYPER");
			paramList.add(currentsalaryunit);
		}
		//
		if (preferredsalary != null) {
			fields.add("PREFERREDSALARYMIN");
			paramList.add(preferredsalary);
		}
		if (isNotEmpty(preferredsalaryunit)) {
			fields.add("PREFERREDSALARYPER");
			paramList.add(preferredsalaryunit);
		}
		//
		fields.add("DATEUPDATED_MANUAL");
		paramList.add(new Timestamp(System.currentTimeMillis()));
		//
		//
		//
		String sqlUpdate = "UPDATE TCANDIDATE SET  " + sqlUpdateFields(fields) + " WHERE ID = ? ";
		paramList.add(candidateid);
		//
		Object[] parameters = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, parameters);
		return true;
	}
	
	public String checkRateUnit2(String unit) { // with month
		unit = unit.toLowerCase();
		if (!(unit.equals("h") || unit.equals("hour") || unit.equals("hourly") || //
				unit.equals("d") || unit.equals("day") || unit.equals("daily") || //
				unit.equals("m") || unit.equals("month") || unit.equals("monthly") || //
				unit.equals("y") || unit.equals("year") || unit.equals("yearly")))
			return "Error: Rate Unit(" + unit + ") should be 'h', 'd', 'm', 'y', 'hour', 'day', 'month', 'year', 'hourly', 'daily', 'monthly' or 'yearly'";
		else
			return "";
	}
	
	public static String phoneConversion(String phone) {
		if (phone.length() == 10) {
			String newPhone = "(" + phone.substring(0, 3) + ")" + phone.substring(3, 6) + "-" + phone.substring(6);
			return newPhone;
		} else
			return phone;
	}
	
	public Long createCandidate(JobDivaSession jobDivaSession, String firstName, String lastName, String email, String alternateemail, String address1, String address2, String city, String state, String zipCode, String countryid, String homephone,
			String workphone, String cellphone, String fax, Double currentsalary, String currentsalaryunit, Double preferredsalary, String preferredsalaryunit, String narrative, //
			TitleSkillCertification[] titleskillcertifications, String titleskillcertification, Date startdate, Date enddate, Integer years) throws Exception {
		//
		//
		//
		StringBuffer message = new StringBuffer();
		StringBuffer warnings = new StringBuffer();
		//
		if (isEmpty(firstName) || isEmpty(lastName))
			message.append("Error: Please set both firstname and lastname. \r\n");
		//
		if (isEmpty(firstName))
			message.append("Error: First name should be less than 50 characters. \r\n");
		//
		if (isEmpty(lastName))
			message.append("Error: Last name should be less than 50 characters. \r\n");
		//
		if (!isEmpty(email) && email.length() > 100)
			message.append("Error: Email should be less than 100 characters. \r\n");
		else if (isEmpty(email))
			email = null;
		//
		if (!isEmpty(address1) && address1.length() > 100)
			message.append("Error: Address1 should be less than 100 characters. \r\n");
		else if (isEmpty(address1))
			address1 = null;
		//
		if (!isEmpty(address2) && address2.length() > 100)
			message.append("Error: Address2 should be less than 100 characters. \r\n");
		else if (isEmpty(address2))
			address2 = null;
		//
		if (!isEmpty(city) && city.length() > 100)
			message.append("Error: City should be less than 100 characters. \r\n");
		else if (isEmpty(city))
			city = null;
		//
		if (!isEmpty(zipCode) && zipCode.length() > 100)
			message.append("Error: Zipcode should be less than 20 characters. \r\n");
		else if (isEmpty(zipCode))
			zipCode = null;
		//
		//
		// state and countryid pair is checked at the backend
		String country = "";
		if (isNotEmpty(countryid)) {
			country = getCountryID(countryid);
			if (country.equals("not found")) {
				country = "US"; // country defaults to "US" if not set
				warnings.append("Warning: 'country' is set to 'US' due to the mapping unfound. \r\n");
			}
		} else {
			country = "US";
			warnings.append("Warning: 'country' defaults to 'US' if it is not set. \r\n");
		}
		//
		//
		String stateId = null;
		if (isNotEmpty(state)) {
			stateId = lookupState(state, country);
			if (stateId == null)
				message.append("Error: Invalid state(" + state + ") and country(" + countryid + "). \r\n");
		}
		//
		//
		// phone, fax length are checked in RecordMaker
		if (currentsalary != null && currentsalary < 0)
			message.append("Error: 'currentsalary' should be a positive number. \r\n");
		//
		if (isNotEmpty(currentsalaryunit))
			message.append(checkRateUnit2(currentsalaryunit));
		//
		if (preferredsalary != null && preferredsalary < 0)
			message.append("Error: 'preferredsalary' should be a positive number. \r\n");
		//
		if (isNotEmpty(preferredsalaryunit))
			message.append(checkRateUnit2(preferredsalaryunit));
		//
		if (isNotEmpty(narrative) && narrative.length() > 400) // sync
																// with
																// searchcandidatenew.jsp
			message.append("Error: 'narrative' should be less than 400 characters. \r\n");
		//
		// titleskillcertifications checked in RecordMaker
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		//
		CandidateData candData = new CandidateData(0);
		try {
			//
			// candData.recruiterid = jobDivaSession.getRecruiterId();
			//
			candData.teamid = jobDivaSession.getTeamId();
			//
			candData.first_name = firstName != null ? firstName.trim() : null;
			//
			candData.last_name = lastName != null ? lastName.trim() : null;
			//
			if (isNotEmpty(email))
				candData.email = email;
			else
				candData.email = "";
			//
			if (isNotEmpty(alternateemail))
				candData.sys_email = alternateemail;
			//
			if (isNotEmpty(address1))
				candData.address1 = address1;
			//
			if (isNotEmpty(address2))
				candData.address2 = address2;
			//
			if (isNotEmpty(city))
				candData.city = city;
			//
			if (isNotEmpty(stateId)) { // get state from JDHibernate
				candData.state = stateId;
			}
			//
			if (isNotEmpty(zipCode))
				candData.zipcode = zipCode;
			//
			candData.country_id = country;
			String number = "";
			//
			if (isNotEmpty(homephone)) {
				number = Pattern.compile(NON_DIGITS).matcher(homephone.trim()).replaceAll("");
				if (number.length() > 20)
					throw new Exception("Error: 'homephone' should be no more than 20 characters. \r\n");
				else if (number.length() > 0)
					candData.home_phone = number;
			}
			//
			if (isNotEmpty(workphone)) {
				number = Pattern.compile(NON_DIGITS).matcher(workphone.trim()).replaceAll("");
				if (number.length() > 20)
					throw new Exception("Error: 'workphone' should be no more than 20 characters. \r\n");
				else if (number.length() > 0)
					candData.work_phone = number;
			}
			//
			if (isNotEmpty(cellphone)) {
				number = Pattern.compile(NON_DIGITS).matcher(cellphone.trim()).replaceAll("");
				if (number.length() > 20)
					throw new Exception("Error: 'cellphone' should be no more than 20 characters. \r\n");
				else if (number.length() > 0)
					candData.cell_phone = number;
			}
			//
			if (isNotEmpty(fax)) {
				number = Pattern.compile(NON_DIGITS).matcher(fax.trim()).replaceAll("");
				if (number.length() > 20)
					throw new Exception("Error: 'fax' should be no more than 20 characters. \r\n");
				else if (number.length() > 0)
					candData.fax = number;
			}
			//
			if (currentsalary != null) {
				if (currentsalary > Float.MAX_VALUE) // guaranteed
														// positive
					throw new Exception("Error: 'currentsalaty' is not a valid float number. \r\n");
				candData.current_salary = Float.parseFloat(currentsalary + "");
			}
			if (isNotEmpty(currentsalaryunit))
				candData.current_salary_per = currentsalaryunit.toLowerCase().charAt(0);
			//
			if (preferredsalary != null) {
				if (preferredsalary > Float.MAX_VALUE) // guaranteed
														// positive
					throw new Exception("Error: 'preferredsalaty' is not a valid float number. \r\n");
				candData.preferred_salary_min = Float.parseFloat(preferredsalary + "");
			}
			//
			if (isNotEmpty(preferredsalaryunit))
				candData.preferred_salary_per = preferredsalaryunit.charAt(0);
			//
			if (isEmpty(narrative))
				narrative = "";
			//
			//
			//
			String experience = "";
			candData.exp_tag = " ";
			//
			if (titleskillcertifications != null) {
				WorkExperience[] wrkexp = new WorkExperience[titleskillcertifications.length];
				for (int i = 0; i < titleskillcertifications.length; i++) {
					String title = titleskillcertifications[i].getTitleskillcertification();
					if (title.length() == 0)
						continue; // ignore if title is null
					Timestamp startdt = null;
					Timestamp enddt = null;
					Timestamp currentdt = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					int lodaYears = 0;
					if (titleskillcertifications[i].getStartDate() != null)
						startdt = new Timestamp(titleskillcertifications[i].getStartDate().getTime());
					if (titleskillcertifications[i].getEndDate() != null)
						enddt = new Timestamp(titleskillcertifications[i].getEndDate().getTime());
					if (titleskillcertifications[i].getYears() != null)
						lodaYears = titleskillcertifications[i].getYears();
					wrkexp[i] = new WorkExperience(title, StringUtils.get50(title), lodaYears * 12);
					if (startdt != null) { // ignore years when dates are set
						if (enddt == null)
							enddt = currentdt; // set end date to current date
												// if end date is not set
						wrkexp[i].fromDate = startdt.getTime();
						wrkexp[i].toDate = enddt.getTime();
						experience += sdf.format(startdt) + " - " + sdf.format(enddt) + "\n";
						experience += title + "\n\n";
					} else if (lodaYears > 0) {
						wrkexp[i].fromDate = currentdt.getTime() - ((long) lodaYears) * 365 * 24 * 60 * 60 * 1000;
						wrkexp[i].toDate = currentdt.getTime();
						experience += sdf.format(new Date(wrkexp[i].fromDate)) + " - " + sdf.format(new Date(currentdt.getTime())) + "\n";
						experience += title + "\n\n";
					} else {
						wrkexp[i] = null;
						continue;
					}
					if (i == 0)
						candData.exp_tag = title;
				}
				candData.work_experience = wrkexp;
			}
			// build resume
			StringBuffer txtData = new StringBuffer();
			txtData.append(candData.first_name + " "); // length guaranteed > 0
			txtData.append(candData.last_name + "\n"); // length guaranteed > 0
			if (isNotEmpty(candData.email) && candData.email.length() > 0)
				txtData.append(candData.email + "\n");
			if (isNotEmpty(candData.address1) && candData.address1.length() > 0)
				txtData.append(candData.address1 + "\n");
			if (isNotEmpty(candData.address2) && candData.address2.length() > 0)
				txtData.append(candData.address2 + "\n");
			if (isNotEmpty(candData.city) && candData.city.length() > 0)
				txtData.append(candData.city);
			if (isNotEmpty(candData.state) && candData.state.length() > 0)
				txtData.append(", " + candData.state);
			if (isNotEmpty(candData.zipcode) && candData.zipcode.length() > 0)
				txtData.append(", " + candData.zipcode + "\n");
			else
				txtData.append("\n");
			if (candData.home_phone != null && candData.home_phone.length() > 0)
				txtData.append(phoneConversion(candData.home_phone) + "\n");
			if (narrative.length() > 0)
				txtData.append("\n " + narrative + "\n");
			if (experience.length() > 0)
				txtData.append("\nExperience\n " + experience + "\n");
			//
			DocumentObjectResume doc = new DocumentObjectResume(0, 1, txtData.toString(), "A 'Quick' Candidate", (new Date()).getTime());
			//
			doc.PlainText = txtData.toString();
			try {
				doc.ZIP_RTFDocument = Zipper.zipIt(txtData.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new Exception("Error: " + e1.getMessage());
			}
			candData.resume = doc;
			candData.resume.recruiterid = jobDivaSession.getRecruiterId();
			candData.resume.ResumeSource = "998";
			candData.resume.ResumeSourceFlag = 3;
		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}
		//
		// sent to servlet
		Object reqData = new ServletRequestData(0, null, candData);
		String url = getCandidateUploadFull();
		Object retObj = null;
		try {
			retObj = ServletTransporter.callServlet(url, reqData);
		} catch (Exception e2) {
			throw new Exception(e2.getMessage());
		}
		// System.out.println("step 4");
		if (retObj != null) {
			String s = retObj.getClass().getName();
			if (s.indexOf("Exception") >= 0) {
				Exception e = (Exception) retObj;
				message.append("Error: " + e.getMessage() + "\r\n");
			} else {
				CandidateData CanDataRet = (CandidateData) retObj;
				int msgcode = CanDataRet.action_code;
				//
				if (msgcode == 0) {
					return CanDataRet.getID();
				} else {
					message.append("Error: Email(" + candData.email + ") Already used" + "\r\n"); // "EMAILUSED";
				}
			}
		}
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		return null;
	}
}
