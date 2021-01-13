package com.jobdiva.api.dao;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import com.axelon.mail.SMTPServer;
import com.axelon.recruiter.RecruiterObject;
import com.axelon.shared.Syslog;
import com.axelon.util.JDLocale;
import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.config.jwt.CustomAuthenticationToken;
import com.jobdiva.api.dao.def.UserfieldsDef;
import com.jobdiva.api.dao.team.TeamRowMapper;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobUser;
import com.jobdiva.api.model.Owner;
import com.jobdiva.api.model.PhoneType;
import com.jobdiva.api.model.Team;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.StringUtils;

public class AbstractJobDivaDao {
	
	public static String						DIGITS_ONLY				= "[0-9]";
	public static String						NON_DIGITS				= "[^0-9]";
	//
	public static final int						SUCCESSFUL				= 0;
	public static final int						AUTHENTICATION_FAILED	= 1;
	public static final int						ERROR					= 2;
	public static final Integer					UNEXPECTED_ERROR		= -1;
	// UDF FIELDFOR
	public static final int						UDF_FIELDFOR_CONTACT	= 1;
	public static final int						UDF_FIELDFOR_CANDIDATE	= 2;
	public static final int						UDF_FIELDFOR_JOB		= 4;
	public static final int						UDF_FIELDFOR_COMPANY	= 8;
	public static final int						UDF_FIELDFOR_ACTIVITY	= 112;										// 16+32+64;
																													// Submittal,
																													// Interview,
																													// Hire,
																													// Start
	//
	//
	protected final Logger						logger					= LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	protected AppProperties						appProperties;
	//
	//
	@Autowired
	protected JdbcTemplate						_jdbcTemplate;
	//
	protected SimpleDateFormat					simpleDateFormat		= new SimpleDateFormat("MM/dd/yyyy");
	//
	private static final Map<String, String>	hashCountry;
	static {
		hashCountry = new HashMap<String, String>();
		hashCountry.put("aruba", "AW");
		hashCountry.put("australia", "AU");
		hashCountry.put("austria", "AT");
		hashCountry.put("belarus", "BY");
		hashCountry.put("belgium", "BE");
		hashCountry.put("brazil", "BR");
		hashCountry.put("canada", "CA");
		hashCountry.put("can", "CA");
		hashCountry.put("china", "CN");
		hashCountry.put("ote d'ivoire", "CI");
		hashCountry.put("denmark", "DK");
		hashCountry.put("finland", "FI");
		hashCountry.put("france", "FR");
		hashCountry.put("germany", "DE");
		hashCountry.put("greece", "GR");
		hashCountry.put("guinea-bissau", "GW");
		hashCountry.put("holland", "NL");
		hashCountry.put("netherlands", "NL");
		hashCountry.put("hong kong", "HK");
		hashCountry.put("india", "IN");
		hashCountry.put("ireland", "IE");
		hashCountry.put("italy", "IT");
		hashCountry.put("japan", "JP");
		hashCountry.put("latvia", "LV");
		hashCountry.put("luxemburg", "LU");
		hashCountry.put("luxembourg", "LU");
		hashCountry.put("malaysia", "MY");
		hashCountry.put("mali", "ML");
		hashCountry.put("nepal", "NP");
		hashCountry.put("new Zealand", "NZ");
		hashCountry.put("norway", "NO");
		hashCountry.put("oman", "OM");
		hashCountry.put("poland", "PL");
		hashCountry.put("qatar", "QA");
		hashCountry.put("russia", "RU");
		hashCountry.put("singapore", "SG");
		hashCountry.put("south africa", "ZA");
		hashCountry.put("south korea", "KR");
		hashCountry.put("spain", "ES");
		hashCountry.put("sweden", "SE");
		hashCountry.put("switzerland", "CH");
		hashCountry.put("uae", "AE");
		hashCountry.put("united kingdom", "UK");
		hashCountry.put("united states", "US");
		hashCountry.put("usa", "US");
		hashCountry.put("united states of america", "US");
	}
	
	public JdbcTemplate getCentralJdbcTemplate() {
		return _jdbcTemplate;
	}
	
	public String getEnvironmentType() {
		CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return customAuthenticationToken.getJobDivaConnection().getEnvironmentType();
	}
	
	public JdbcTemplate getJdbcTemplate() {
		CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return customAuthenticationToken.getJdbcTemplate();
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return customAuthenticationToken.getNamedParameterJdbcTemplate();
	}
	
	protected String getUtilServlet() {
		return appProperties.getJdHibernateServlet() + "UtilServlet";
	}
	
	protected String getCandidateApplyJobUsingAPI() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/CandidateApplyJobUsingAPI";
	}
	
	protected String getCandidateFinancialServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/CandidateFinancialServlet";
	}
	
	protected String getCandidateBillingRecordsGetServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/profit/servlet/CandidateBillingRecordsGetServlet";
	}
	
	protected String getCandidateAddlRecordInsertServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/profit/servlet/CandidateAddlRecordInsertServlet";
	}
	
	protected String getSaveVMSTimesheets() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/profit/servlet/SaveVMSTimesheets";
	}
	
	protected String getCandidateUploadFull() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/CandidateUploadFull";
	}
	
	protected int countDecimalDigits(Double num) {
		if (num == null)
			return 0;
		String numStr = String.valueOf(num);
		if (!numStr.contains("."))
			return 0;
		return numStr.length() - numStr.indexOf(".") - 1;
	}
	
	public void saveAccessLog(long userid, long leader, long clientid, String methodName, String moreDetail) {
		String msg = null;
		if ((leader & 4096) == 4096) {
			int actioncode = 155;
			//
			if (methodName.equals("createJob"))
				actioncode = 138;
			else if (methodName.equals("searchJob"))
				actioncode = 100;
			else if (methodName.equals("updateJob"))
				actioncode = 113;
			//
			Syslog.log(5, this, "[[RECRUITERID]]" + userid + "[[/RECRUITERID]][[TEAMID]]" + clientid + "[[/TEAMID]][[ACTIONCODE]]" + actioncode + "[[/ACTIONCODE]]" + "[[CRITERIA]]JobDivaAPI method - " + methodName
					+ (moreDetail != null ? (", " + moreDetail) : "") + "[[/CRITERIA]]");
			//
			msg = String.format("[[RECRUITERID]]" + userid + "[[/RECRUITERID]][[TEAMID]]" + clientid + "[[/TEAMID]][[ACTIONCODE]]" + actioncode + "[[/ACTIONCODE]]" + "[[CRITERIA]]JobDivaAPI method - " + methodName
					+ (moreDetail != null ? (", " + moreDetail) : "") + "[[/CRITERIA]]");
			//
			//
		} else {
			Syslog.log(5, this, "[[RECRUITERID]]" + userid + "[[/RECRUITERID]][[TEAMID]]" + clientid + "[[/TEAMID]][[ACTIONCODE]]155[[/ACTIONCODE]]" + "[[CRITERIA]]JobDivaAPI method - " + methodName + (moreDetail != null ? (", " + moreDetail) : "")
					+ "[[/CRITERIA]]");
			//
			msg = "[[RECRUITERID]]" + userid + "[[/RECRUITERID]][[TEAMID]]" + clientid + "[[/TEAMID]][[ACTIONCODE]]155[[/ACTIONCODE]]" + "[[CRITERIA]]JobDivaAPI method - " + methodName + (moreDetail != null ? (", " + moreDetail) : "")
					+ "[[/CRITERIA]]";
			//
		}
		logger.info(msg);
	}
	
	protected void checkPhoneType(PhoneType[] phones, String phoneOwner) throws Exception {
		if (phones != null) {
			for (int i = 0; i < phones.length; i++) {
				PhoneType phone = phones[i];
				String pho = Pattern.compile(NON_DIGITS).matcher(phone.getPhone()).replaceAll("");
				if (pho.length() > 20)
					throw new Exception("Error: Phone # should be no more than 20 numbers. \r\n");
				String ext = null;
				if (isNotEmpty(phone.getExt()))
					ext = Pattern.compile(NON_DIGITS).matcher(phone.getExt()).replaceAll("");
				if (ext != null && ext.length() > 10)
					throw new Exception("Error: Ext should be no more than 10 numbers. \r\n");
				String type = phone.getType().toUpperCase().trim();
				// map phone type to database format
				if (type.equals("W"))
					type = "W0";
				else if (type.equals("H"))
					type = "H1";
				else if (type.equals("C"))
					type = "C2";
				else if (type.equals("X"))
					type = "X3";
				else if (type.equals("F"))
					type = "F4";
				else if (type.equals("P"))
					type = "P5";
				else if (type.equals("M"))
					type = "M6";
				else if (type.equals("D"))
					type = "D7";
				else if (type.equals("O"))
					type = "O8";
				else
					throw new Exception("Error: Phone type(" + phone.getType() + ") is not recognized and cannot be updated. Valid types: " + "W, H, C, F, X, P, M, D, O. \r\n");
				if (phoneOwner.equals("contact")) {
					type = type.substring(0, 1); // Pattern.compile(DIGITS_ONLY).matcher(type).replaceAll("");
					if (!(type.equals("W") || type.equals("P")) && ext != null)
						throw new Exception("Error: Only \'work phone\' or \'pager\' display extension box. Please do not set \'ext\' for other types of numbers. \r\n");
				} else if (phoneOwner.equals("candidate")) {
					type = type.substring(1); // Pattern.compile(NON_DIGITS).matcher(type).replaceAll("");
					if (!type.equals("0") && ext != null)
						throw new Exception("Error: Only \'work phone\' display extension box. Please do not set \'ext\' for other types of numbers" + "(type:" + phone.getType() + ", #:" + phone.getPhone() + "-" + phone.getExt() + "). \r\n");
				}
				//
				phone.setExt(ext);
				phone.setType(type);
				phone.setPhone(pho);
			}
		}
	}
	
	private <T> ArrayList<T> arraytoList(T[] types) {
		ArrayList<T> tpeList = new ArrayList<T>();
		Collections.addAll(tpeList, types);
		return tpeList;
	}
	
	protected boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}
	
	protected boolean isEmpty(String value) {
		return !isNotEmpty(value);
	}
	
	protected String sqlInsertFields(ArrayList<String> fields) {
		String sql = "";
		for (String field : fields) {
			if (sql.length() > 0)
				sql += ", ";
			sql += field;
		}
		return sql;
	}
	
	protected String sqlUpdateFields(ArrayList<String> fieldList) {
		String sql = "";
		for (String field : fieldList) {
			if (sql.length() > 0)
				sql += ", ";
			sql += field + " = ? ";
		}
		return sql;
	}
	
	protected String sqlUpdateparamFields(ArrayList<String> fieldList) {
		String sql = "";
		for (String field : fieldList) {
			if (sql.length() > 0)
				sql += ", ";
			sql += field + " = :" + field + " ";
		}
		return sql;
	}
	
	protected String sqlInsertValues(LinkedHashMap<String, String> fields) {
		String sql = "";
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			if (sql.length() > 0)
				sql += ", ";
			sql += ":" + entry.getValue();
		}
		return sql;
	}
	
	protected String sqlUpdateFields(LinkedHashMap<String, String> fields) {
		String sql = "";
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			if (sql.length() > 0)
				sql += ", ";
			sql += entry.getKey() + " = :" + entry.getValue();
		}
		return sql;
	}
	
	protected String sqlInsertParams(ArrayList<String> fields) {
		String sql = "";
		for (int i = 0; i < fields.size(); i++) {
			if (i > 0)
				sql += ", ";
			sql += "?";
		}
		return sql;
	}
	
	protected String clobToString(Clob clob) throws SQLException {
		if (clob != null) {
			try {
				Reader bodyText = clob.getCharacterStream();
				return org.apache.commons.io.IOUtils.toString(bodyText);
			} catch (IOException e) {
			}
		}
		return null;
	}
	
	protected String lookupState(String stateName, String countryid) {
		//
		Boolean assignedCountry = countryid != null;
		//
		String sql = "Select STATE_CODE from TSTATES state  WHERE upper(STATE_NAME) =  upper(?) ";
		if (assignedCountry)
			sql += " and COUNTRYID = ?";
		//
		Object[] params = assignedCountry ? new Object[] { stateName, countryid } : new Object[] { stateName };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("STATE_CODE");
			}
		});
		//
		if (list.size() > 0) {
			return list.get(0);
		} else {
			sql = "select STATE_CODE FROM TSTATES States WHERE STATE_ABBR = ? ";
			//
			if (assignedCountry)
				sql += " and COUNTRYID	 = ? ";
			//
			list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("STATE_CODE");
				}
			});
			//
			if (list.size() > 0) {
				return list.get(0);
			}
		}
		//
		return null;
	}
	
	protected List<Long> getCompanyIdsByDivision(Long clientId, String division) {
		String sql = "select distinct z.companyid " //
				+ " from trecruiter x, tcustomer_owners y, tcustomer z, tdivision d " //
				+ " where " //
				+ " x.groupid = ? " //
				+ " and x.division = d.id " //
				+ " and x.id = y.recruiterid " //
				+ " and y.customerid = z.id " //
				+ " and d.teamid = x.groupid " //
				+ " and name_index = ?" //
				+ "and z.companyid > 0";
		//
		Object[] params = new Object[] { clientId, division };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getLong("companyid");
			}
		});
		//
		return list;
	}
	
	protected List<Long> getIdsFromCompanyTypeName(Long clientId, String[] types) {
		for (int i = 0; i < types.length; i++) {
			if (isNotEmpty(types[i]))
				types[i] = types[i].trim().toUpperCase();
		}
		//
		String sql = "select id, TYPENAME " //
				+ " from TCUSTOMER_COMPANY_TYPES " //
				+ " where " //
				+ " TEAMID = :teamid " //
				+ " and upper(TYPENAME) in (:typenames) " //
				+ " and ( deleted is null or deleted = 0 )";
		//
		ArrayList<String> tpeList = arraytoList(types);
		//
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("teamid", clientId);
		params.put("typenames", tpeList);
		//
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = getNamedParameterJdbcTemplate();
		//
		//
		List<Long> list = namedParameterJdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				return rs.getLong("id");
			}
		});
		//
		return list;
	}
	
	protected List<Long> getCompanyIdsByName(Long clientId, String parentCompany) {
		//
		//
		String sql = "select id " //
				+ " from TCUSTOMERCOMPANY " //
				+ " where upper(name) = upper( ? ) " //
				+ " and teamid = ? ";
		Object[] params = new Object[] { parentCompany, clientId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.setFetchSize(1000);
		try {
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("id");
				}
			});
			return list;
		} finally {
			jdbcTemplate.setFetchSize(-1);
		}
	}
	
	protected List<UserfieldsDef> getUserFields(Integer userFieldId, Long teamId) {
		String sql = "SELECT * FROM TUSERFIELDS " //
				+ "WHERE "//
				+ "ID = ? AND "//
				+ "TEAMID = ? ";
		Object[] params = new Object[] { userFieldId, teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<UserfieldsDef> list = jdbcTemplate.query(sql, params, new RowMapper<UserfieldsDef>() {
			
			@Override
			public UserfieldsDef mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				UserfieldsDef userfieldsDef = new UserfieldsDef();
				userfieldsDef.setId(rs.getInt("ID"));
				userfieldsDef.setFieldName(rs.getString("FIELDNAME"));
				userfieldsDef.setFieldTypeId(rs.getLong("FIELDTYPEID"));
				userfieldsDef.setFieldSize(rs.getLong("FIELDSIZE"));
				userfieldsDef.setFieldFor(rs.getLong("FIELDFOR"));
				userfieldsDef.setMandatory(rs.getBoolean("MANDATORY"));
				userfieldsDef.setParentUserFieldId(rs.getLong("PARENT_USERFIELD_ID"));
				userfieldsDef.setDisplayOrder(rs.getLong("DISPLAY_ORDER"));
				userfieldsDef.setMask(rs.getBoolean("MASK"));
				userfieldsDef.setTabid(rs.getLong("TABID"));
				userfieldsDef.setNext(rs.getLong("NEXT"));
				userfieldsDef.setEnforceLink(rs.getBoolean("ENFORCELINK"));
				userfieldsDef.setIncludeInUrl(rs.getBoolean("INCLUDE_IN_URL"));
				userfieldsDef.setShowToHm(rs.getBoolean("SHOWTOHM"));
				userfieldsDef.setShowToSupp(rs.getBoolean("SHOWTOSUPP"));
				userfieldsDef.setShowToCand(rs.getBoolean("SHOWTOCAND"));
				userfieldsDef.setShowLastChar(rs.getLong("SHOWLASTCHAR"));
				userfieldsDef.setDefaultToUdfId(rs.getLong("DEFAULTTO_UDFID"));
				//
				return userfieldsDef;
			}
		});
		//
		return list;
	}
	
	protected void validateUserFields(JobDivaSession jobDivaSession, Long id, Userfield[] userfields, int validateType) throws Exception {
		for (Userfield userfield : userfields) {
			//
			Integer userFieldId = userfield.getUserfieldId();
			Long teamId = jobDivaSession.getTeamId();
			//
			List<UserfieldsDef> userfieldsDefs = getUserFields(userFieldId, teamId);
			//
			if (userfieldsDefs == null || userfieldsDefs.isEmpty()) {
				throw new Exception("Error: User-Defined Field(id:" + userFieldId + ") is not found. ");
			}
			//
			UserfieldsDef userfieldsDef = userfieldsDefs.get(0);
			//
			if ((userfieldsDef.getFieldFor() & validateType) == 0)
				throw new Exception("Error: User-Defined Field(id:" + userFieldId + ") is not for its specific type. ");
			//
			long udfTypeId = userfieldsDef.getFieldTypeId();
			Long udfFieldSize = userfieldsDef.getFieldSize();
			String udfName = userfieldsDef.getFieldName();
			Boolean isMandatory = userfieldsDef.getMandatory();
			boolean sizeLimited = true;
			String jdUdfValue = userfield.getUserfieldValue();
			String udfValue = userfield.getUserfieldValue();
			//
			// allow optional UDF has empty string, to clear the value
			if ((isMandatory == null || !isMandatory) && !isNotEmpty(userfield.getUserfieldValue())) {
				continue;
			}
			//
			//
			if (udfTypeId == 2 || udfTypeId == 8) {
				// Number/Currency -- numbers only
				try {
					float floatValue = Float.parseFloat(jdUdfValue);
					if (floatValue < 0)
						throw new Exception("Error: (" + jdUdfValue + ") is not a valid number for " + udfName + "(udfid: " + userFieldId + "). ");
					if (udfTypeId == 8) // round for currency
						udfValue = (float) (Math.round((double) floatValue * 100) / 100.00) + "";
				} catch (NumberFormatException e) {
					throw new Exception("Error: (" + jdUdfValue + ") is not a valid number for " + udfName + "(udfid: " + userFieldId + "). ");
				}
				//
			} else if (udfTypeId == 3 || udfTypeId == 4) {
				// Date -- store milliseconds
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat sdfWithTime = new SimpleDateFormat("MM/dd/yyyy HH:mm");
				try {
					if (udfTypeId == 3)
						udfValue = sdf.parse(jdUdfValue).getTime() + "";
					else if (udfTypeId == 4)
						udfValue = sdfWithTime.parse(jdUdfValue).getTime() + "";
				} catch (ParseException e) {
					throw new Exception("Error: Please make sure the " + udfName + "(udfid: " + userFieldId + ") is in MM/dd/yyyy hh:mm format. ");
				}
				sizeLimited = false;
				//
			} else if (udfTypeId == 5 || udfTypeId == 6) {
				String sqlCheck = "select listvalue from TUSERFIELD_LISTVALUES where teamid = ? and userfield_id = ? ";
				Object[] params = new Object[] { teamId, userFieldId };
				//
				JdbcTemplate jdbcTemplate = getJdbcTemplate();
				//
				List<String> values = jdbcTemplate.query(sqlCheck, params, new RowMapper<String>() {
					
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("listvalue");
					}
				});
				//
				if (values.size() < 1)
					throw new Exception("Error: No selection for " + udfName + "(udfid: " + userFieldId + "). " + "Please set items on the UDF management page. ");
				if (udfTypeId == 5) { // Drop Down (Single Selection)
					if (!values.contains(jdUdfValue))
						throw new Exception("Error: (" + jdUdfValue + ") is not a valid selection for " + udfName + "(udfid: " + userFieldId + "). " + "Valid selections: " + values.toString());
				} else if (udfTypeId == 6) { // List (Multiple Selection)
					String[] jdValues = jdUdfValue.split(",");
					udfValue = "~";
					for (int i = 0; i < jdValues.length; i++) {
						jdValues[i] = jdValues[i].trim();
						if (!values.contains(jdValues[i]))
							throw new Exception(
									"Error: (" + jdValues[i] + ") is not a valid selection for " + udfName + "(udfid: " + userFieldId + "). " + "Valid selections: " + values.toString() + ". Please seperate multiple selections by comma(,)");
						else
							udfValue += jdValues[i] + "~";
					}
				}
				sizeLimited = false;
			} else if (udfTypeId == 7) {
				// URL
				sizeLimited = false;
			} else if (udfTypeId == 9) {
				// Percentage -- numbers smaller than 100
				boolean parseFail = false;
				try {
					float floatValue = Float.parseFloat(jdUdfValue);
					if (floatValue < 0 || floatValue > 100)
						parseFail = true;
					else if (jdUdfValue.length() > udfFieldSize.intValue())
						// truncate number according to fieldsize
						udfValue = (floatValue + "").substring(0, udfFieldSize.intValue());
				} catch (NumberFormatException e) {
					parseFail = true;
				}
				if (parseFail)
					throw new Exception("Error: (" + jdUdfValue + ") is not a valid number for " + udfName + "(udfid: " + userFieldId + "). ");
				sizeLimited = false;
			} else if (udfTypeId == 10) { // Text Area
				sizeLimited = false;
			}
			//
			// data checking according to field size
			if (sizeLimited && udfFieldSize != null && jdUdfValue.length() > udfFieldSize.intValue())
				throw new Exception("Error: (" + jdUdfValue + ") exceeds maximum length(" + udfFieldSize + ") of " + udfName + "(udfid: " + userFieldId + "). ");
			//
			userfield.setUserfieldValue(udfValue);
			//
		}
	}
	
	protected static String getCountryID(String country) {
		if (country == null)
			return null;
		//
		country = country.trim();
		if (country.length() == 2)
			return country.toUpperCase();
		String countryid = "";
		country = country.toLowerCase();
		countryid = hashCountry.get(country);
		if (countryid == null)
			countryid = "not found";
		return countryid;
	}
	
	protected Team getTeamById(long teamId) {
		String sql = "select * from TTEAM WHERE ID = ? ";
		Object[] params = new Object[] { teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Team> list = jdbcTemplate.query(sql, params, new TeamRowMapper());
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	private Integer assignCurrencySymbol(Long teamId, Hashtable<Integer, String> currencySymbolHash, Hashtable<String, Integer> currencyUnitHash) {
		String sql = "select c.NAME, tc.CURRENCYID, tc.DEFAULTCURRENCY, c.SYMBOL " //
				+ " from TTEAM_CURRENCY tc, TCURRENCY c " //
				+ " where teamid = ? and tc.CURRENCYID = c.ID";
		Object[] params = new Object[] { teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
			
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Integer currencyId = rs.getInt("CURRENCYID");
				String name = rs.getString("NAME");
				//
				currencyUnitHash.put(name.toLowerCase(), currencyId);
				String symbol = rs.getString("SYMBOL");
				//
				if (currencyId == 3 || StringUtils.deNull(name).toLowerCase().equals("euro"))
					symbol = new Character((char) 128).toString();
				//
				currencySymbolHash.put(currencyId, symbol);
				//
				if (rs.getBigDecimal("DEFAULTCURRENCY").equals(new BigDecimal(1)))
					return currencyId;
				return null;
			}
		});
		//
		if (list != null && list.size() > 0) {
			for (Integer value : list) {
				if (value != null) {
					return value;
				}
			}
		}
		//
		return null;
	}
	
	protected String getCurrencySymbol(Hashtable<Integer, String> currencySymbolHash, Integer currencyid) {
		//
		String symbol = "$";
		if (currencySymbolHash.containsKey(currencyid))
			symbol = currencySymbolHash.get(currencyid);
		return symbol;
	}
	
	protected String getRatePerName(char rateper) { // should lookup from table!
		String rateName = "";
		rateper = Character.toLowerCase(rateper);
		switch (rateper) {
			case 'h':
				rateName = "Hour";
				break;
			case 'd':
				rateName = "Day";
				break;
			case 'y':
				rateName = "Year";
				break;
		}
		return rateName;
	}
	
	private String getRatesRange(Hashtable<Integer, String> currencySymbolHash, BigDecimal ratemin, BigDecimal ratemax, char rateper, Integer currencyid) {
		//
		String ratesRange = "";
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		String currencySymbol = getCurrencySymbol(currencySymbolHash, currencyid);
		if (ratemin != null && ratemin.doubleValue() > 0.0)
			ratesRange = currencySymbol + df.format(ratemin) + "-";
		if (ratemax != null && ratemax.doubleValue() > 0.0)
			ratesRange += currencySymbol + df.format(ratemax);
		ratesRange += "/" + getRatePerName(rateper);
		return ratesRange;
	}
	
	protected String getContractName(Integer value, Long teamid) {
		if (value == null || value == 0)
			return null;
		String contractName = null;
		String sql = "SELECT id, name FROM trfq_position_types WHERE teamid = ? AND inactive = 0";
		Object[] params = new Object[] { teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Long id = rs.getLong("id");
				if (id.intValue() == value.intValue()) {
					return rs.getString("name");
				}
				return null;
			}
		});
		//
		if (list != null && list.size() > 0) {
			for (String strValue : list) {
				if (strValue != null)
					return strValue;
			}
		} else {
			switch (value) {
				case 1:
					contractName = "direct placement";
					break;
				case 2:
					contractName = "contract";
					break;
				case 3:
					contractName = "right to hire";
					break;
				case 4:
					contractName = "full time/contract";
					break;
				default:
					break;
			}
		}
		//
		return contractName;
	}
	
	public static String localize(JDLocale regionFormat, String key, long teamid) {
		if (key == null || key.length() == 0) {
			return key;
		}
		String label = "";
		if (regionFormat == null) {
			// String TEAM_REGION_CODE = getRegionCodeByTeam(session, teamid);
			// regionFormat = new JDLocale(TEAM_REGION_CODE);
		} else
			label = localize(regionFormat, key);
		return label;
	}
	
	public static String localize(JDLocale regionFormat, String key) {
		String label = "";
		if (key == null)
			return label;
		label = regionFormat.getLabel(key.replace(" ", "").toUpperCase());
		if (label == null || label.length() == 0) {
			label = key;
		}
		return label;
	}
	
	protected void checkOwners(Owner[] owners) throws Exception {
		if (owners != null) {
			int primary_cnt = 0;
			for (Owner jdOwner : owners) {
				//
				boolean hasOwner = false;
				//
				if (jdOwner.getAction() == null) {
					throw new Exception("Have to set action code to update owner");
				} else if (!jdOwner.getAction().equals(1) && !jdOwner.getAction().equals(2)) {
					throw new Exception("Error: Action code should be 1(insert/modify) or 2(delete) ");
				}
				//
				if (jdOwner.getOwnerId() != null) {
					hasOwner = true;
				} else {
					if (isNotEmpty(jdOwner.getFirstName()) && isNotEmpty(jdOwner.getLastName())) {
						jdOwner.setFirstName(jdOwner.getFirstName().toUpperCase().trim());
						jdOwner.setLastName(jdOwner.getLastName().toUpperCase().trim());
						hasOwner = true;
					}
					if (!hasOwner)
						throw new Exception("Error: Please set either owner id or owner name(both first name and last name) if intend to declare the owners for contacts. \r\n");
					//
					if (jdOwner.getPrimary())
						primary_cnt++;
				}
			}
			//
			if (primary_cnt > 1)
				throw new Exception("Error: Only one owner can be set as primary. \r\n");
			//
		}
	}
	
	private String buildEmailBody(Long teamId, Job job, JDLocale regionFormat, String primaryRecName, String primarySaleName, String description, String rfqno_team, String rfq_refno) throws Exception {
		//
		//
		Hashtable<Integer, String> currencySymbolHash = new Hashtable<Integer, String>();
		Hashtable<String, Integer> currencyUnitHash = new Hashtable<String, Integer>(); // name,
		//
		assignCurrencySymbol(teamId, currencySymbolHash, currencyUnitHash);
		//
		//
		//
		// get primaryRecName if null
		if (primaryRecName == null || primarySaleName == null) {
			logger.debug("Need to lookup primaryRecName from trecruiterrfq & trecruiter------------");
		}
		String strContract = localize(regionFormat, getContractName(job.getContract(), job.getTeamid()));
		String JobDescription = description;
		String Remark = "";
		try { // Exception when clob is null or empty
			Remark = job.getInstruction();
		} catch (Exception e) {
		}
		;
		String ContactName = StringUtils.deNull(job.getFirstName()).trim() + " " + StringUtils.deNull(job.getLastName()).trim();
		String emailLocation = "";
		/*
		 * Session session_env1 =
		 * HibernateUtil.getSessionFactory(10).openSession(); Transaction
		 * transaction_env1 = null; try { transaction_env1 =
		 * session_env1.beginTransaction(); Env env1 = (Env)
		 * session_env1.get(Env.class, new Short(env + "")); emailLocation =
		 * StringUtils.deNull(env1.getApacheLocation()); } catch
		 * (HibernateException hiex) { transaction_env1.rollback(); } finally {
		 * session_env1.close(); }
		 */
		String sql = "select "//
				+ " decode(countries.defaultdisplay,1,states.state_name, states.state_abbr) as jobstate, countries.country as countryname " //
				+ " from tstates states, tcountries countries " //
				+ " where countries.id(+) = ? " //
				+ " and states.state_code(+) = ? " //
				+ " and states.countryid(+) = ? "; //
		Object[] params = new Object[] { job.getCountry(), job.getState(), job.getCountry() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<LinkedList<String>> list = jdbcTemplate.query(sql, params, new RowMapper<LinkedList<String>>() {
			
			@Override
			public LinkedList<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				LinkedList<String> list = new LinkedList<String>();
				//
				list.add(rs.getString("jobstate"));
				list.add(rs.getString("countryname"));
				//
				return list;
			}
		});
		//
		String countryname = "";
		String jobstate = "";
		if (list != null && list.size() > 0) {
			countryname = list.get(0).get(1);
			jobstate = list.get(0).get(0);
		}
		//
		String Location = StringUtils.deNull(job.getCity()).trim();
		if (Location.length() == 0)
			Location += StringUtils.deNull(jobstate).trim();
		else if (StringUtils.deNull(jobstate).trim().length() > 0)
			Location += ", " + StringUtils.deNull(jobstate).trim();
		if (Location.length() == 0)
			Location += StringUtils.deNull(countryname).trim();
		else if (StringUtils.deNull(countryname).trim().length() > 0)
			Location += ", " + StringUtils.deNull(countryname).trim();
		String strPayRate = getRatesRange(currencySymbolHash, job.getRateMin(), job.getRateMax(), job.getRateper(), job.getPayrateCurrency()); // rateper
		// are
		// still
		// hardcoded
		String strBillRate = getRatesRange(currencySymbolHash, job.getBillRateMin(), job.getBillRateMax(), job.getBillRatePer(), job.getBillrateCurrency());
		String strJobInfo = "<b>Additional Job Information:</b>" + "<br><b>Title</b>: <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + job.getId() + "'>" + StringUtils.deNull(job.getRfqTitle()) + "</a>" + "<br><b>JobDiva #</b>: " //
				+ StringUtils.deNull(rfqno_team) + "<br><b>Contact</b>: " + ContactName + "<br><b>Company</b>: " + StringUtils.deNull(job.getDepartment()) + "<br><b>Primary Recruiter</b>: " + primaryRecName + "<br><b>Primary Sales</b>: " //
				+ primarySaleName + "<br><b>Pay Rate:</b>: " + strPayRate + "<br><b>Bill Rate:</b>: " + strBillRate + "<br><b>Start Date</b>: " + regionFormat.outputDate(job.getStartDate()) + "<br><b># of Openings</b>: " //
				+ (job.getPositions() != null ? job.getPositions().toString() : "") + "<br><b>Position Type</b>: " + strContract + "<br><b>Location</b>: " + Location + "<br><b>Description</b>: <div style='border:gray 1px solid;padding:5px;'>" //
				+ StringUtils.deNull(JobDescription) + "</div>" + "<br><b>Remarks</b>: <div style='border:gray 1px solid; padding: 5px;'>" + StringUtils.deNull(Remark) + "</div>";
		String emailBody = "This is to inform you that you have just been assigned to Job Reference # <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + job.getId() + "'>" + rfq_refno + "</a> (" + StringUtils.deNull(job.getRfqTitle())
				+ ")" + "<br><br>" + strJobInfo;
		emailBody = "<div style='font-family:arial;font-size:13px;'>" + emailBody + "</div>";
		return emailBody;
	}
	
	private String getJobStatusName(int statusId, long teamid) {
		String statusName = "";
		try {
			logger.debug("JobMethod.getJobStatusName search status name sql start...");
			long sec1 = System.currentTimeMillis();
			String sql = "SELECT name FROM trfq_statuses WHERE id = ? AND (teamid = ? OR teamid = 0)";
			Object[] params = new Object[] { statusId, teamid };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getString("name");
				}
			});
			//
			long sec2 = System.currentTimeMillis();
			logger.debug("JobMethod.getJobStatusName search status name sql end...time used: " + (sec2 - sec1) / 1000 + " sec");
			if (list.size() > 0)
				statusName = list.get(0);
		} catch (Exception e) {
			logger.debug("Exception Occurs...");
			// e.printStackTrace();
		}
		return statusName;
	}
	
	protected void sendAssignNotification(Vector<RecruiterObject> v_assigned, Vector<RecruiterObject> v_deassigned, Job job, long teamid, String primaryRecName, String primarySaleName, String description, String rfqno_team, String rfq_refno)
			throws Exception {
		//
		logger.debug("........................ sendAssignNotification for " + job.getId());
		//
		String teamregion = "en_US";
		Team team = getTeamById(teamid);
		if (team.getRegionCode() != null && team.getRegionCode().length() > 0)
			teamregion = team.getRegionCode();
		//
		JDLocale regionFormat = new JDLocale(teamregion, DateFormat.SHORT, -1, null, 2, 0); // MM/dd/yyyy
		if (v_assigned != null && v_assigned.size() > 0) {
			com.axelon.mail.SMTPServer mailServer = new com.axelon.mail.SMTPServer();
			mailServer.setHost(appProperties.getSmtpServerLocation());
			mailServer.setLogFileName("./email.log"); // ?
			mailServer.setContentType(SMTPServer.CONTENT_TYPE_HTML);
			String CompanyName = StringUtils.deNull(job.getDepartment());
			String emailSubject = "You have just been assigned to Job Reference # " + rfq_refno;
			if (CompanyName.equals("") == false)
				emailSubject += " for " + CompanyName;
			//
			String emailBody = buildEmailBody(teamid, job, regionFormat, primaryRecName, primarySaleName, description, rfqno_team, rfq_refno);
			//
			for (RecruiterObject rec : (Vector<RecruiterObject>) v_assigned) {
				try {
					mailServer.sendMail(rec.email, "JobAssignment@jobdiva.com", emailSubject, emailBody);
					logger.debug("Emails sent to " + rec.email);
				} catch (Exception ex) {
					logger.debug("Could not send assigned email to =" + rec.email);
					// ex.printStackTrace();
				}
			}
		}
		if (v_deassigned != null && v_deassigned.size() > 0) {
			// for (RecruditerObject rec : (Vector<RecruiterObject>)
			// v_deassigned) {
			// // send emails
			// }
		}
	}
	
	protected void sendUpdateJobNotification(Job job, Long teamid, Long recruiterid, Integer jobStatus) {
		//
		List<JobUser> jobJobusers = job.getJobUsers();
		//
		if (jobJobusers == null)
			return;
		//
		com.axelon.mail.SMTPServer mailServer = new com.axelon.mail.SMTPServer();
		//
		mailServer.setHost(appProperties.getSmtpServerLocation());
		mailServer.setLogFileName("./email.log"); // ?
		mailServer.setContentType(SMTPServer.CONTENT_TYPE_HTML);
		//
		String emailLocation = appProperties.getApacheLocation();
		//
		// get recruiter name
		String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL" //
				+ " FROM TRECRUITER " //
				+ " WHERE ID = ? ";
		Object[] params = new Object[] { recruiterid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		// /
		List<List<String>> list = jdbcTemplate.query(sql, params, new RowMapper<List<String>>() {
			
			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				//
				List<String> list = new ArrayList<String>();
				list.add(firstName);
				list.add(lastName);
				return list;
			}
		});
		String updateRecName = StringUtils.deNull(list != null && list.size() > 0 ? list.get(0).get(0) : null)//
				+ " " + //
				StringUtils.deNull(list != null && list.size() > 0 ? list.get(0).get(1) : null);
		String subject = "Job Status changed by " + updateRecName;
		//
		//
		for (JobUser jobJobUser : jobJobusers) {
			//
			if (jobJobUser == null)
				continue; // get user from trecruiterrfq JobUserId juid = new
			//
			List<JobUser> jobusers = getJobUsers(jdbcTemplate, jobJobUser.getRfqId(), teamid, jobJobUser.getRecruiterId());
			JobUser jobUser = jobusers != null && jobusers.size() > 0 ? jobusers.get(0) : null;
			//
			// get user from trecruiter
			//
			int emailPreference = jobUser.getRecEmailStatus() == null ? 0 : (int) jobUser.getRecEmailStatus();
			boolean isLeadrecruiter = jobUser.getLeadRecruiter() == null ? false : (boolean) jobUser.getLeadRecruiter();
			boolean isLeadsales = jobUser.getLeadSales() == null ? false : (boolean) jobUser.getLeadSales();
			if ((emailPreference != 1 && emailPreference != 2) || (emailPreference == 2 && !isLeadrecruiter && !isLeadsales))
				continue;
			else {
				//
				String msg = "This is to inform you that the status of Job Reference # <a href='" //
						+ emailLocation + "/employers/open_rfq.jsp?rfqid=" + job.getId() + "'>" //
						+ job.getRfqRefNo() + "</a> (" + StringUtils.deNull(job.getRfqTitle()) + ")" //
						+ StringUtils.deNull(job.getDepartment()) + " has just been changed by "//
						+ updateRecName + " from " + getJobStatusName(job.getJobStatus(), teamid) //
						+ " to " + getJobStatusName(jobStatus, teamid);
				//
				String to = StringUtils.deNull(jobUser.getEmail());
				String from = "activity@JobDiva.com";
				try {
					mailServer.sendMail(to, from, subject, msg);
					logger.debug("Emails sent to " + to);
				} catch (Exception ex) {
					logger.debug("Could not send assigned email to =" + to);
					// ex.printStackTrace();
				}
			}
		}
	}
	
	protected Short checkNameIndex(JobDivaSession jobDivaSession, String nameIndex, JdbcTemplate jdbcTemplate) throws Exception {
		Short pipelineId = null;
		if (isNotEmpty(nameIndex)) {
			String sql = "SELECT id " //
					+ " FROM tsalespipeline " //
					+ " WHERE nls_upper(name) = nls_upper(?) " //
					+ " AND teamid = ? " //
					+ " AND is_active = 1";
			//
			Object[] params = new Object[] { nameIndex, jobDivaSession.getTeamId() };
			//
			List<Short> listLong = jdbcTemplate.query(sql, params, new RowMapper<Short>() {
				
				@Override
				public Short mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getShort("id");
				}
			});
			//
			if (listLong != null && listLong.size() > 0) {
				pipelineId = listLong.get(0);
			} else {
				throw new Exception("Error: invalid sales pipeline " + nameIndex);
			}
		}
		return pipelineId;
	}
	
	public void addContactNote(Long candidateid, Integer type, Long recruiterid, Long rfqid, Timestamp datecreated, Long teamid, int auto, String note) {
		//
		String sqlInsert = " INSERT INTO TCANDIDATENOTES (NOTEID, CANDIDATEID, TYPE, RECRUITERID, RFQID, DATECREATED, TEAMID, RECRUITER_TEAMID, AUTO, NOTE)" //
				+ "  VALUES " //
				+ " (CANDIDATENOTEID.nextval, ?, ?, ?, ?, ?, ?, ?, ? , ?) ";
		Object[] params = new Object[] { candidateid, type, recruiterid, rfqid, datecreated, teamid, teamid, auto, note };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public List<JobUser> getJobUsers(Long jobId, Long teamId) {
		return getJobUsers(jobId, teamId, null);
	}
	
	public List<JobUser> getJobUsers(Long jobId, Long teamId, Long recruiterId) {
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		return getJobUsers(jdbcTemplate, jobId, teamId, recruiterId);
	}
	
	public List<JobUser> getJobUsers(JdbcTemplate jdbcTemplate, Long jobId, Long teamId, Long recruiterId) {
		String sql = " Select "//
				+ " a.RFQID, "//
				+ " a.RECRUITERID, "//
				+ " a.TEAMID , "//
				+ " a.REC_EMAIL, " //
				+ " a.LEAD_RECRUITER , " //
				+ " a.SALES ," //
				+ " a.LEAD_SALES, "//
				+ " a.RECRUITER , "//
				+ " a.JOBSTATUS ,"//
				//
				+ " b.FIRSTNAME ,"//
				+ " b.LASTNAME, "//
				+ " b.WORKPHONE ,"//
				+ " b.REC_EMAIL_STATUS ,"//
				+ " b.EMAIL " //
				//
				+ " FROM TRECRUITERRFQ a , TRECRUITER b " //
				+ " WHERE " //
				+ " a.RECRUITERID = b.ID  " //
				+ " AND a.RFQID = ?  AND a.TEAMID = ? ";
		//
		if (recruiterId != null)
			sql += " AND a.RECRUITERID = ? ";
		//
		Object[] params = recruiterId != null ? new Object[] { jobId, teamId, recruiterId } : new Object[] { jobId, teamId };
		//
		logger.info("getJobUsers :: " + sql + " [" + params + "]");
		//
		List<JobUser> list = jdbcTemplate.query(sql, params, new RowMapper<JobUser>() {
			
			@Override
			public JobUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					JobUser jobUser = new JobUser();
					jobUser.setRfqId(rs.getLong("RFQID"));
					jobUser.setRecruiterId(rs.getLong("RECRUITERID"));
					jobUser.setTeamId(rs.getLong("TEAMID"));
					jobUser.setReceiveEmail(rs.getBoolean("REC_EMAIL"));
					jobUser.setLeadRecruiter(rs.getBoolean("LEAD_RECRUITER"));
					jobUser.setSales(rs.getBoolean("SALES"));
					jobUser.setLeadSales(rs.getBoolean("LEAD_SALES"));
					jobUser.setRecruiter(rs.getBoolean("RECRUITER"));
					jobUser.setJobStatus(rs.getInt("JOBSTATUS"));
					jobUser.setFirstName(rs.getString("FIRSTNAME"));
					jobUser.setLastName(rs.getString("LASTNAME"));
					jobUser.setPhone(rs.getString("WORKPHONE"));
					jobUser.setEmail(rs.getString("EMAIL"));
					jobUser.setRecEmailStatus(rs.getInt("REC_EMAIL_STATUS"));
					return jobUser;
				} catch (Exception e) {
					logger.info("getJobUsers ERROR :: " + e.getMessage());
				}
				return null;
			}
		});
		if (list != null) {
			String strBuffer = "";
			for (JobUser jobUser : list) {
				if (jobUser != null) {
					strBuffer += "[" + jobUser.getTeamId() + "][" + jobUser.getLeadSales() + "][" + jobUser.getRecruiterId() + "]\r\n";
				} else {
					strBuffer += " EMPTY OBJECT \r\n";
				}
			}
			logger.info("getJobUsers [" + jobId + "] [" + teamId + "] [" + recruiterId + "] " + strBuffer);
		} else {
			logger.info("getJobUsers [" + jobId + "] [" + teamId + "] [" + recruiterId + "] EMPTY LIST");
		}
		return list;
	}
}
