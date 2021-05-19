package com.jobdiva.api.dao.job;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.axelon.mail.SMTPServer;
import com.axelon.recruiter.RecruiterObject;
import com.axelon.shared.Application;
import com.jobdiva.api.config.AppProperties;
import com.jobdiva.api.dao.activity.AbstractActivityDao;
import com.jobdiva.api.dao.company.SearchCompanyDao;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.job.def.UserRoleDef;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Attachment;
import com.jobdiva.api.model.Company;
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.ContactRoleType;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobStatus;
import com.jobdiva.api.model.JobUser;
import com.jobdiva.api.model.Skill;
import com.jobdiva.api.model.TeamRole;
import com.jobdiva.api.model.UserRole;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.ZipInfo;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.utils.StringUtils;

@Component
public class JobDao extends AbstractActivityDao {
	
	@Autowired
	AppProperties		appProperties;
	//
	@Autowired
	SearchCompanyDao	searchCompanyDao;
	//
	@Autowired
	JobUserDao			jobUserDao;
	//
	@Autowired
	JobContactDao		jobContactDao;
	//
	@Autowired
	ContactDao			contactDao;
	//
	@Autowired
	JobNoteDao			jobNoteDao;
	//
	@Autowired
	JobAttachmentDao	jobAttachmentDao;
	//
	@Autowired
	JobUserFieldsDao	jobUserFieldsDao;
	//
	@Autowired
	JobStatusLogDao		jobStatusLogDao;
	
	public Job getJob(JobDivaSession jobDivaSession, Long jobId) {
		LinkedHashMap<String, Object> paramList = new LinkedHashMap<String, Object>();
		//
		String sql = " SELECT * " //
				+ " FROM  TRFQ  " //
				+ " where " //
				+ "  ID = :id and TEAMID = :teamId ";
		//
		paramList.put("id", jobId);
		paramList.put("teamId", jobDivaSession.getTeamId());
		//
		List<Job> list = getNamedParameterJdbcTemplate().query(sql, paramList, new JobRowMapper());
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public List<Long> getJobIds(JobDivaSession jobDivaSession, String jobdivaref, String optionalref) {
		String sql = " Select "//
				+ " ID " //
				+ " FROM TRFQ " //
				+ " WHERE  TEAMID = ? ";
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(jobDivaSession.getTeamId());
		//
		if (isNotEmpty(jobdivaref)) {
			sql += " and nls_upper(RFQREFNO) like ? ";
			paramList.add(jobdivaref.toUpperCase() + "%");
		}
		//
		if (isNotEmpty(optionalref)) {
			sql += " and nls_upper(RFQNO_TEAM) like ? ";
			paramList.add(optionalref.toUpperCase() + "%");
		}
		//
		// if (paramList.size() == 1) {
		// sql += " AND ROWNUM <= 1000";
		// }
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
	
	private ZipInfo getZipInfo(String zipcode, String countryId) throws Exception {
		/*
		 * Query tzipinfo to get latitude and longitude; in order to search job
		 * by radius
		 */
		if (!isNotEmpty(countryId))
			throw new Exception("Country ID is required when searching by zipcode.");
		switch (countryId.trim().toUpperCase()) {
			case "US": {
				if (zipcode.trim().length() < 5)
					throw new Exception("US zipcode should have at least five digits.");
				zipcode = zipcode.substring(0, 5);
				break;
			}
			case "UK": {
				if (zipcode.contains(" "))
					zipcode = zipcode.substring(0, zipcode.indexOf(" "));
				break;
			}
			case "CA": {
				if (zipcode.trim().length() < 7)
					throw new Exception("CA zipcode should have at least seven digits.");
				zipcode = zipcode.substring(0, 7);
				break;
			}
			default:
				throw new Exception("Error: Country ID only supports US, UK, CA at the moment.");
		}
		String sql = "SELECT * FROM tzipinfo WHERE zipcode = ?";
		Object[] params = new Object[] { zipcode };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		return jdbcTemplate.queryForObject(sql, params, new ZipInfoRowMapper());
	}
	
	private void checkJobStatus(Long teamId, Integer jobStatus) throws Exception {
		//
		if (jobStatus != null) {
			String sql = "SELECT id FROM trfq_statuses WHERE teamid = ? OR teamid = 0";
			Object[] params = new Object[] { teamId };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			// /
			List<BigDecimal> list = jdbcTemplate.query(sql, params, new RowMapper<BigDecimal>() {
				
				@Override
				public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getBigDecimal("id");
				}
			});
			//
			for (BigDecimal rfgStatusId : list) {
				Integer definedStatus = ((BigDecimal) rfgStatusId).intValueExact();
				if (jobStatus.equals(definedStatus)) {
					return;
				}
			}
			throw new Exception(String.format("Error: Invalid Job Status: %d", jobStatus));
		}
	}
	
	private void checkJobPriority(JobDivaSession jobDivaSession, String priority, Integer priority_id) throws Exception {
		logger.info("Verify job priority(" + priority + ")");
		String sql = "SELECT id FROM tjob_priority WHERE teamid = ? and deleteflag = 0 and id = ?";
		Object[] params = new Object[] { jobDivaSession.getTeamId(), priority_id };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Long> userDefinedPriorities = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		boolean isValidPriority = userDefinedPriorities != null && userDefinedPriorities.size() > 0;
		//
		if (!isValidPriority)
			throw new Exception("Error: Invalid Job Priority(" + priority + ")");
	}
	
	private Integer getPriorityId(Long teamId, String priority) throws Exception {
		Integer priority_id = null;
		//
		if (isNotEmpty(priority)) {
			//
			priority = priority.trim();
			//
			try {
				priority_id = Integer.parseInt(priority);
			} catch (NumberFormatException e) {
			}
			//
			Boolean searchByQuery = true;
			//
			if (priority_id != null && priority_id.intValue() > 0) {
				//
				searchByQuery = false;
				//
			} else if (priority.length() == 1) {
				//
				char prio = priority.toUpperCase().charAt(0);
				//
				switch (prio) {
					case 'A':
						searchByQuery = false;
						priority_id = 1;
						break;
					case 'B':
						searchByQuery = false;
						priority_id = 2;
						break;
					case 'C':
						searchByQuery = false;
						priority_id = 3;
						break;
					case 'D':
						searchByQuery = false;
						priority_id = 4;
						break;
					// default:
					// throw new Exception("Error: \'" + prio + "\' is not a
					// valid job priority. \r\n");
				}
				//
			}
			//
			if (searchByQuery) {
				//
				logger.info("Verify Job Id Priority For Custom Job priority(" + priority + ")");
				//
				String sql = "SELECT id FROM tjob_priority WHERE teamid = ? and deleteflag = 0 And upper(Name) = upper(?) ";
				Object[] params = new Object[] { teamId, priority.trim() };
				//
				JdbcTemplate jdbcTemplate = getJdbcTemplate();
				List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
					
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("id");
					}
				});
				//
				if (list != null && list.size() > 0) {
					priority_id = list.get(0);
				} else {
					throw new Exception("Error: \'" + priority + "\' is not a valid job priority. \r\n");
				}
				//
			}
		}
		return priority_id;
	}
	
	private void assignUserDefinedPositionType(Long teamId, HashMap<String, Integer> positionTypeIdMap, HashMap<Integer, String> idPositionTypeMap) {
		// Query user defined position types
		String sql = "SELECT * FROM trfq_position_types   WHERE teamid = ? AND inactive = 0 AND ROWNUM  <= 201 ";
		Object[] params = new Object[] { teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Void> list = jdbcTemplate.query(sql, params, new RowMapper<Void>() {
			
			@Override
			public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Integer id = rs.getBigDecimal(1).intValue();
				String positionType = (rs.getString(2)).toLowerCase();
				//
				positionTypeIdMap.put(positionType, id);
				idPositionTypeMap.put(id, positionType);
				return null;
			}
		});
		//
		//
		if (list == null || list.isEmpty()) {
			// If nothing found, use hard-coded position type to convert
			positionTypeIdMap.put("direct placement", 1);
			positionTypeIdMap.put("contract", 2);
			positionTypeIdMap.put("right to hire", 3);
			positionTypeIdMap.put("full time/contract", 4);
			for (Map.Entry<String, Integer> entry : positionTypeIdMap.entrySet()) {
				idPositionTypeMap.put(entry.getValue(), entry.getKey());
			}
		}
	}
	
	public List<Job> searchJobs(JobDivaSession jobDivaSession, Long jobId) {
		//
		String sql = "select * from trfq where teamid = ?  and id = ? ";
		//
		Object[] params = new Object[] { jobDivaSession.getTeamId(), jobId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		return jdbcTemplate.query(sql, params, new JobRowMapper());
	}
	
	public List<Job> searchJobs(JobDivaSession jobDivaSession, Long jobId, String jobdivaref, String optionalref, String city, String[] states, String title, //
			Long contactid, Long companyId, String companyname, Integer status, String[] jobTypes, Date issuedatefrom, //
			Date issuedateto, Date startdatefrom, Date startdateto, //
			String department, String skill, String zipcode, Integer zipcodeRadius, String countryId, Boolean ismyjob) throws Exception {
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		/* Check if status is user-defined status in this team */
		if (status != null)
			checkJobStatus(jobDivaSession.getTeamId(), status);
		/*
		 * If zipcode and radius exist, get longitude and latitude for search by
		 * zipcode radius
		 */
		String latitude = null;
		String longitude = null;
		if (isNotEmpty(zipcode) && zipcodeRadius != null && zipcodeRadius > 0) {
			ZipInfo zipInfo = getZipInfo(zipcode, countryId);
			latitude = zipInfo.getLatitude();
			longitude = zipInfo.getLongitude();
		}
		//
		//
		if (ismyjob == null)
			ismyjob = false;
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuilder sql_buff = new StringBuilder();
		sql_buff.append("select * from trfq job1_ ");
		if (ismyjob)
			sql_buff.append(", TRECRUITERRFQ jobuser0_ where jobuser0_.RFQID=job1_.ID and ");
		else
			sql_buff.append(" where ");
		sql_buff.append(" job1_.teamid = ? ");
		paramList.add(jobDivaSession.getTeamId());
		//
		if (jobId != null) {
			sql_buff.append(" and job1_.id = ? ");
			paramList.add(jobId);
		}
		//
		if (ismyjob) {
			sql_buff.append(" and jobuser0_.RECRUITERID = ? ");
			paramList.add(jobDivaSession.getRecruiterId());
		}
		//
		if (contactid != null) {
			sql_buff.append(" and job1_.customerid = ? ");
			paramList.add(contactid);
		}
		//
		if (companyId != null) {
			sql_buff.append(" and job1_.companyid = ? ");
			paramList.add(companyId);
		} else if (isNotEmpty(companyname)) {
			List<Long> companies = getCompanyIdsByName(jobDivaSession.getTeamId(), companyname);
			if (companies != null) {
				if (companies.size() == 1) {
					sql_buff.append(" and job1_.companyid = ? ");
					paramList.add(companies.get(0));
				} else if (companies.size() > 1) {
					sql_buff.append(" and job1_.companyid in( ? ) ");
					paramList.add(companies);
				}
			}
		}
		//
		if (isNotEmpty(optionalref)) {
			sql_buff.append(" and job1_.rfqno_team like ? "); // == -> like
			paramList.add(optionalref + "%");
		}
		//
		if (isNotEmpty(jobdivaref)) {
			sql_buff.append(" and upper(job1_.rfqrefno) like upper(?) ");
			paramList.add(jobdivaref + "%");
		}
		//
		if (isNotEmpty(department)) {
			sql_buff.append(" and upper(job1_.department) like upper(?) ");
			paramList.add(department + "%");
		}
		//
		if (isNotEmpty(title)) {
			sql_buff.append(" and nls_upper(job1_.rfqtitle) like ? ");
			paramList.add(title.toUpperCase() + "%");
		}
		//
		if (isNotEmpty(city)) {
			sql_buff.append(" and NLS_UPPER(job1_.city) like NLS_UPPER(?)||'%'");
			paramList.add(city);
		}
		//
		if (isNotEmpty(skill)) {
			sql_buff.append(" and NLS_UPPER(job1_.SKILLS) like NLS_UPPER(?)||'%'");
			paramList.add(skill);
		}
		//
		if (states != null) {
			if (states.length == 1) {
				sql_buff.append(" and upper(job1_.state) = ? ");
				String state = lookupState(states[0], "US");
				if (state != null)
					states[0] = state; // set as it is, if state
										// abbreviation not found along with
										// "US"
				paramList.add(states[0].toUpperCase());
			} else {
				for (int i = 0; i < states.length; i++) {
					String state = lookupState(states[i], "US");
					if (state != null)
						states[i] = state; // set as it is, if state
											// abbreviation not found along
											// with "US"
					// else throw new Exception("Error: State ("+
					// jobObj.getState() +") can not be updated due to the
					// mapping unfound.(with countryid(US)) \r\n");
				}
				sql_buff.append(" and upper(job1_.state) in (select /*+ cardinality(10) */ * from THE (select cast(sf_inlist(?) as sf_inlist_table_type ) from dual ))");
				paramList.add(Arrays.toString(states).replaceAll("\\[|\\]| ", "").toUpperCase());
			}
		}
		//
		if (isNotEmpty(city)) {
			sql_buff.append(" and nls_upper(job1_.city) like ? ");
			paramList.add(city.toUpperCase() + "%");
		}
		if (issuedatefrom != null) {
			sql_buff.append(" and job1_.dateissued >= ?  ");
			paramList.add(issuedatefrom);
		}
		if (issuedateto != null) {
			sql_buff.append(" and job1_.dateissued <= ? ");
			paramList.add(issuedateto);
		}
		if (startdatefrom != null) {
			sql_buff.append(" and job1_.startdate >= ? ");
			paramList.add(startdatefrom);
		}
		if (startdateto != null) {
			sql_buff.append(" and job1_.startdate <= ? ");
			paramList.add(startdateto);
		}
		if (status != null) {
			sql_buff.append(" and job1_.jobstatus = ? ");
			paramList.add(status);
		}
		if (ismyjob) {
		}
		/* Get default and user-defined position type */
		HashMap<String, Integer> positionTypeIdMap = new HashMap<String, Integer>();
		HashMap<Integer, String> idPositionTypeMap = new HashMap<Integer, String>();
		//
		assignUserDefinedPositionType(jobDivaSession.getTeamId(), positionTypeIdMap, idPositionTypeMap);
		//
		if (jobTypes != null) {
			Integer[] contract = new Integer[jobTypes.length];
			for (int i = 0; i < jobTypes.length; i++) {
				if (jobTypes[i] != null) {
					String jobType = jobTypes[i].toLowerCase();
					Integer jobTypeInt = null;
					try {
						jobTypeInt = Integer.parseInt(jobType);
					} catch (Exception e) {
					}
					if (jobTypeInt == null) {
						if (positionTypeIdMap.containsKey(jobType))
							contract[i] = positionTypeIdMap.get(jobType);
						else
							throw new Exception(String.format("Error: Position Type(%s) is invalid", jobTypes[i]));
					} else {
						if (idPositionTypeMap.containsKey(jobTypeInt))
							contract[i] = jobTypeInt;
						else
							throw new Exception(String.format("Error: Position Type(%s) is invalid", jobTypes[i]));
					}
				}
			}
			if (jobTypes.length == 1) {
				sql_buff.append(" and job1_.contract = ? ");
				paramList.add(contract[0]);
			} else {
				sql_buff.append("and job1_.contract in (select /*+ cardinality(10) */ * from THE (select cast(sf_inlist(?) as sf_inlist_table_type ) from dual)) ");
				paramList.add(Arrays.toString(contract).replaceAll("\\[|\\]| ", ""));
			}
		}
		/* Search by zipcode radius */
		if (isNotEmpty(zipcode)) {
			if (zipcodeRadius != null && zipcodeRadius > 0 && isNotEmpty(latitude) && isNotEmpty(longitude)) {
				// search by radius
				sql_buff.append(" AND job1_.zip_lat BETWEEN ? - (? / 111.045) AND ?  + (? / 111.045) AND " + " job1_.zip_lon BETWEEN ? - (? / (111.045 * COS(0.0174532925 * (?)))) AND ? + (? / (111.045 * COS(0.0174532925 * (?)))) ");
				paramList.add(latitude);
				paramList.add(zipcodeRadius);
				paramList.add(latitude);
				paramList.add(zipcodeRadius);
				paramList.add(longitude);
				paramList.add(zipcodeRadius);
				paramList.add(latitude);
				paramList.add(longitude);
				paramList.add(zipcodeRadius);
				paramList.add(latitude);
			} else {
				sql_buff.append(" AND nls_upper(job1_.zipcode) like ? ");
				paramList.add(zipcode.toUpperCase() + "%");
			}
		}
		// for testing
		// sql_buff.append(" AND ROWNUM <= 10");
		sql_buff.append(" order by job1_.dateissued desc");
		//
		String queryString = sql_buff.toString();
		Object[] params = paramList.toArray();
		List<Job> list = jdbcTemplate.query(queryString, params, new JobRowMapper());
		//
		//
		for (Job job : list) {
			//
			//
			//
			if (job.getJobStatus() != null) {
				String str = "";
				try {
					switch (job.getJobStatus()) {
						case 0:
							str = "Open";
							break;
						case 1:
							str = "On Hold";
							break;
						case 2:
							str = "Filled";
							break;
						case 3:
							str = "Cancelled";
							break;
						case 4:
							str = "Closed";
							break;
						case 5:
							str = "Expired";
							break;
						case 6:
							str = "Ignored";
							break;
						default:
							str = String.valueOf(job.getJobStatus());
					}
					job.setStrJobStatus(str);
					//
				} catch (Exception e) {
					logger.info("Assign Job Status for job " + job.getId() + " Error :: " + e.getMessage());
				}
			}
			//
			//
			try {
				job.setJobType(getJobTypeString(job.getContract()));
			} catch (Exception e) {
				logger.info("Assign JobType for job " + job.getId() + " Error :: " + e.getMessage());
			}
			//
			//
			ArrayList<String> jobusers = new ArrayList<String>();
			try {
				List<JobUser> jobUsers = jobUserDao.getJobUsers(job.getId(), jobDivaSession.getTeamId());
				for (JobUser jobUser : jobUsers) {
					String jobuser = "";
					jobuser += jobUser.getLastName();
					jobuser += " | " + jobUser.getFirstName() + " |";
					if (jobUser.getLeadRecruiter())
						jobuser += " (PR)";
					if (jobUser.getLeadSales())
						jobuser += " (PS)";
					if (jobUser.getSales() && !jobUser.getLeadSales())
						jobuser += " (S)";
					if (jobUser.getRecruiter() && !jobUser.getLeadRecruiter())
						jobuser += " (R)";
					jobuser += " | " + jobUser.getRecruiterId() + " |";
					jobuser += jobUser.getRoleIds().toString().replace('[', '(').replace(']', ')');
					jobusers.add(jobuser);
				}
			} catch (Exception e) {
				logger.info("Assign UsersNameRole for job " + job.getId() + " Error :: " + e.getMessage());
			}
			//
			//
			job.setUsersNameRole(jobusers.toString());
			//
		}
		//
		//
		return list;
	}
	
	public String getJobTypeString(Integer typeid) {
		String type = "";
		if (typeid != null) {
			switch (typeid) {
				case 1:
					type = "Direct Placement";
					break;
				case 2:
					type = "contract";
					break;
				case 3:
					type = "Right to Hire";
					break;
				case 4:
					type = "Full Time/contract";
					break;
			}
		}
		return type;
	}
	
	private List<UserRoleDef> gettUsersRoleDef(UserRole[] users) throws Exception {
		int pr_cnt = 0;
		int ps_cnt = 0;
		//
		List<UserRoleDef> userRoleDefs = new ArrayList<UserRoleDef>();
		for (UserRole userRole : users) {
			//
			UserRoleDef userRoleDef = new UserRoleDef();
			userRoleDef.setAction(userRole.getAction());
			userRoleDef.setRole(userRole.getRole());
			userRoleDef.setUserId(userRole.getUserId());
			//
			userRoleDefs.add(userRoleDef);
			String role = userRole.getRole().toLowerCase();
			if (role.indexOf("sales") > -1)
				userRoleDef.setIsSale(true);
			if (role.indexOf("primary sales") > -1) {
				userRoleDef.setIsLeadSales(true);
				ps_cnt++;
			}
			if (role.indexOf("recruiter") > -1)
				userRoleDef.setIsRecruiter(true);
			if (role.indexOf("primary recruiter") > -1) {
				userRoleDef.setIsLeadRecruiter(true);
				pr_cnt++;
			}
		}
		//
		if (pr_cnt > 1)
			throw new Exception("Error: Only one user can be set as primary recruiter. \r\n");
		if (ps_cnt > 1)
			throw new Exception("Error: Only one user can be set as primary sale. \r\n");
		return userRoleDefs;
	}
	
	private void updateHarvesterAssignment(long jobid, long teamid, Long divisionid, Long userId) {
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String sqlInsert = " INSERT INTO twebsites_jobs (rfqid, teamid, webid, username, isharvest, machine_no) "//
				+ " SELECT ? , teamid, webid, username, isharvest, machine_no FROM twebsites_recruiters " //
				+ " WHERE teamid = ?  AND recruiterid = ? ";
		Object[] params = new Object[] { jobid, teamid, userId };
		//
		int howManyInserts = jdbcTemplate.update(sqlInsert, params);
		if (howManyInserts == 0 && divisionid != null && divisionid > 0) {
			sqlInsert = "	INSERT INTO twebsites_jobs (rfqid, teamid, webid, username, isharvest, machine_no) " //
					+ " SELECT ?, teamid, webid, username, isharvest, machine_no FROM twebsites_divisions " //
					+ " WHERE teamid = ? AND divisionid = ?";
			params = new Object[] { jobid, teamid, divisionid };
			jdbcTemplate.update(sqlInsert, params);
		}
	}
	
	private boolean isRecruiter(Long roleid) {
		if (roleid == null)
			return false;
		if (roleid.intValue() == 950 || (roleid.intValue() >= 996 && roleid.intValue() <= 999)) // recruiter
																								// or
																								// sales
																								// roldid
			return true;
		return false;
	}
	
	private String insertAttachments(long teamid, long jobid, Attachment jdAttachment) {
		String msg = "";
		try {
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			// Check attachment type first, case sensitive (same as interface
			// logic)
			Long attachmentType = new Long(0);
			if (isNotEmpty(jdAttachment.getFileType())) {
				String sql = "select ID from TATTACHMENT_TYPE where teamid = ? and typefor = 4 and nvl(deleted, 0) = 0 and TYPENAME = ?  ";
				Object[] params = new Object[] { teamid, jdAttachment.getFileType() };
				//
				//
				List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong("ID");
					}
				});
				if (list != null && list.size() > 0)
					return "Attachment type '" + jdAttachment.getFileType() + "' not found, this file was skipped.";
				//
				attachmentType = list.get(0);
			}
			// Then insert
			String sql = "select PROFILE_RETAGID.nextval from dual";
			List<BigDecimal> nextval = jdbcTemplate.query(sql, new RowMapper<BigDecimal>() {
				
				@Override
				public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getBigDecimal(0);
				}
			});
			//
			if (nextval == null || nextval.size() == 0)
				throw new Exception("Warning: Failed allocate file id from sequence.");
			//
			//
			jobAttachmentDao.addJobAttachment(nextval.get(0).longValue(), teamid, jobid, jdAttachment.getDescription(), jdAttachment.getFileName(), jdAttachment.getFileData(), attachmentType);
		} catch (Exception e) {
			msg = String.format("Warning: Failed to create job attachment '%s' due to: %s\r\n", jdAttachment.getFileName(), e.getMessage());
		}
		return msg;
	}
	
	private Integer getContractValue(String positionType, Long teamid) {
		if (positionType == null || positionType.length() == 0)
			return null;
		Integer positionTypeInt = null;
		Integer contractValue = null;
		boolean customized = false;
		try {
			positionTypeInt = Integer.parseInt(positionType);
		} catch (Exception e) {
		}
		String sql = "SELECT id, name FROM trfq_position_types WHERE teamid = ? AND inactive = 0";
		Object[] params = new Object[] { teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<List<Object>> list = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
			
			@Override
			public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Object> list = new ArrayList<Object>();
				list.add(rs.getLong("id"));
				list.add(rs.getString("name"));
				return list;
			}
		});
		if (list != null) {
			for (List<Object> objList : list) {
				customized = true;
				// Case 1: pass-in positionType string is already a number in
				// String
				// type
				if (positionTypeInt != null) {
					if (((Long) objList.get(0)).intValue() == positionTypeInt)
						contractValue = positionTypeInt;
				}
				// Case 2: pass-in positionType string is in literal
				else {
					if (positionType.toLowerCase().equals(((String) objList.get(1)).toLowerCase()))
						contractValue = ((Long) objList.get(0)).intValue();
				}
			}
		}
		if (!customized) {
			switch (positionType.toLowerCase()) {
				case "direct placement":
					contractValue = 1;
					break;
				case "contract":
					contractValue = 2;
					break;
				case "right to hire":
					contractValue = 3;
					break;
				case "full time/contract":
					contractValue = 4;
					break;
				case "1":
					contractValue = 1;
					break;
				case "2":
					contractValue = 2;
					break;
				case "3":
					contractValue = 3;
					break;
				case "4":
					contractValue = 4;
					break;
				default:
					break;
			}
		}
		return contractValue;
	}
	
	private Long insertJob(JobDivaSession jobDivaSession, Job job) {
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		JobSqlMapping.mapJob(jobDivaSession, job, fields, parameterSource, true);
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// logger.info("insert Job jdbcTemplate " + jdbcTemplate);
		Long jobId = null;
		String sql = "SELECT RFQID.nextval AS jobId FROM dual";
		List<Long> listLong = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("jobId");
			}
		});
		if (listLong != null && listLong.size() > 0) {
			jobId = listLong.get(0);
		}
		//
		// logger.info("insert Job jobId " + jobId);
		//
		String sqlInsert = " INSERT INTO TRFQ (ID, " + sqlInsertFields(new ArrayList<String>(fields.keySet())) + ") VALUES (" + jobId + " ," + sqlInsertValues(fields) + ") ";
		//
		// logger.info("insert Job jobId " + sqlInsert);
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcTemplateObject.update(sqlInsert, parameterSource);
		//
		// logger.info("inserted Job jobId " + jobId);
		// get the value of the generated id
		return jobId;
		//
	}
	
	private void updateJob(JobDivaSession jobDivaSession, Job job) {
		//
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		//
		JobSqlMapping.mapJob(jobDivaSession, job, fields, parameterSource, false);
		//
		//
		String sqlInsert = " UPDATE TRFQ SET " + sqlUpdateparamFields(new ArrayList<String>(fields.keySet())) //
				+ " WHERE ID = :ID and TEAMID = :TEAMID ";
		parameterSource.addValue("ID", job.getId());
		parameterSource.addValue("TEAMID", jobDivaSession.getTeamId());
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		jdbcTemplateObject.update(sqlInsert, parameterSource);
		//
		//
	}
	
	public Long createJob(JobDivaSession jobDivaSession, ContactRoleType[] contacts, String contactfirstname, String contactlastname, String title, String description, String department, Long companyid, String priority, Long divisionid,
			UserRole[] users, Integer experience, Integer status, String optionalref, String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, String jobtype, Integer openings,
			Integer fills, Integer maxsubmittals, Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck,
			Boolean securityclearance, String remarks, String submittalinstruction, Double minbillrate, Double maxbillrate, String billratecurrency, String billRateUnit, Double minpayrate, Double maxpayrate, String payratecurrency,
			String payRateUnit, Skill[] skills, String[] skillstates, String skillzipcode, Integer skillzipcodemiles, Skill[] excludedskills, String harvest, Integer resumes, Attachment[] attachments, Userfield[] userfields) throws Exception {
		//
		//
		if (contacts != null) {
			int cnt_showonjob = 0;
			for (int i = 0; i < contacts.length; i++) {
				ContactRoleType cj_contact = contacts[i];
				if (cj_contact.getContactId() < 1)
					throw new Exception("Error: Contactid should be a positive number. \r\n");
				if (cj_contact.getShowOnJob()) {
					cnt_showonjob++;
				}
			}
			if (cnt_showonjob < 1)
				throw new Exception("Error: Please set one and only one contact shown on job screen. \r\n");
			if (cnt_showonjob > 1)
				throw new Exception("Error: Only one contact can be shown on job screen. \r\n");
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		//
		StringBuffer message = new StringBuffer();
		/*
		 * Check for duplication before creation. Only applies to API calls by
		 * coddler/spider Coddler/spider API accounts have leader value 13328 or
		 * 13584
		 */
		long leader = jobDivaSession.getLeader();
		if (leader == 13328 || leader == 13584) {
			logger.info("This user account is a spider account. Checking job duplication...");
			// Search job by teamid, company ID, optional ref# to make sure
			// no duplication
			ArrayList<Object> paramList = new ArrayList<Object>();
			//
			String sql = "SELECT * FROM trfq WHERE teamid = ? ";
			paramList.add(jobDivaSession.getTeamId());
			//
			if (companyid != null) {
				sql += " AND companyid = ? ";
				paramList.add(companyid);
			}
			//
			if (isNotEmpty(optionalref)) {
				sql += " AND UPPER(rfqrefno) = ? ";
				paramList.add(optionalref.toUpperCase());
			}
			//
			sql += " AND dateissued >= to_date(?, 'MM/DD/YYYY HH24:MI:SS') ";
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -730);
			SimpleDateFormat sdf_sql = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			paramList.add(sdf_sql.format(cal.getTimeInMillis()));
			//
			List<Long> list = jdbcTemplate.query(sql, paramList.toArray(), new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("ID");
				}
			});
			//
			if (list.size() > 0)
				throw new Exception(String.format("Error: Job Duplication. Ref#: %s, Company ID: %d, Team: %d", optionalref, companyid, jobDivaSession.getTeamId()));
			else
				logger.info("Checking job duplication finished. No duplication found. Proceed to creation.");
		}
		//
		//
		/* Check if status is user-defined status in this team */
		if (status != null) {
			logger.info("Job status to be verified: " + status);
			//
			checkJobStatus(jobDivaSession.getTeamId(), status);
		} else {
			status = 0;
		}
		//
		Integer priority_id = getPriorityId(jobDivaSession.getTeamId(), priority);
		//
		/* Check User Defined Job Priority */
		if (priority_id != null) {
			checkJobPriority(jobDivaSession, priority, priority_id);
		}
		//
		//
		long teamid = jobDivaSession.getTeamId();
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		// hash currency units
		Integer defaultCurrencyID = 1;
		//
		Hashtable<String, Integer> currencyUnitHash = new Hashtable<String, Integer>(); // name,
																						// currencyid
		Hashtable<Integer, String> currencySymbolHash = new Hashtable<Integer, String>(); // id,
																							// symbol
		HashMap<String, Character> billRateUnitIdMap = new HashMap<String, Character>(); // NAME
																							// ->
																							// UNITID,
																							// trateunits
		HashMap<String, Character> payRateUnitIdMap = new HashMap<String, Character>();
		//
		String sql = "select c.NAME, tc.CURRENCYID, tc.DEFAULTCURRENCY, c.SYMBOL " //
				+ " from TTEAM_CURRENCY tc, TCURRENCY c " //
				+ " where teamid = ? and tc.CURRENCYID = c.ID";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		List<Integer> userDefinedStatus = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
			
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
				else
					return null;
			}
		});
		//
		if (userDefinedStatus != null && userDefinedStatus.size() > 0) {
			for (Integer value : userDefinedStatus) {
				if (value != null) {
					defaultCurrencyID = value;
					break;
				}
			}
		}
		//
		//
		// hash bill rate and pay rate units
		sql = "select * from TRATEUNITS where TEAMID = ? and DELETED = 0";
		params = new Object[] { jobDivaSession.getTeamId() };
		List<Boolean> list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				Character unitId = rs.getString("UNITID").charAt(0);
				String unitName = rs.getString("NAME");
				Integer rateType = rs.getInt("RATETYPE");
				if (rateType == 1)
					billRateUnitIdMap.put(unitName.trim().toLowerCase(), unitId);
				else if (rateType == 0)
					payRateUnitIdMap.put(unitName.trim().toLowerCase(), unitId);
				//
				return true;
			}
		});
		//
		if (experience != null) {
			if (experience > 10)
				experience = 11;
		} else {
			experience = -1;
		}
		//
		//
		//
		//
		// Create job
		Job job = new Job();
		job.setTeamid(teamid);
		job.setJobPriority(priority_id);
		job.setRecruiterId(jobDivaSession.getRecruiterId());
		job.setTravel(travel);
		job.setOvertime(overtime);
		job.setExperience(experience);
		job.setJobStatus(status);
		job.setPrivateAddress(hidemyclientaddress);
		job.setPrivateCompanyName(hidemeandmycompany);
		job.setPrivateExpiryDate(null); // jobObj.isPrivateexpirydate());
		job.setPrivateJobStartDate(null); // (jobObj.isPrivatejobstartdate());
		job.setPrivateJobEndDate(null); // (jobObj.isPrivatejobenddate());
		job.setPrivateSalary(null); // jobObj.isPrivatesalary());
		job.setCriteriaChanged(true);
		job.setPrivateMyCompanyName(hidemeandmycompany);
		job.setDateIssued(new Timestamp(System.currentTimeMillis()));
		//
		// rate units
		Character payRateChar = null;
		Character billRateChar = null;
		if (isNotEmpty(payRateUnit)) {
			//
			String unit = payRateUnit.toLowerCase();
			if (unit.equals("hourly") || unit.equals("daily") || unit.equals("yearly") || unit.equals("hour") || unit.equals("day") || unit.equals("year"))
				unit = unit.substring(0, 1);
			//
			payRateUnit = unit;
			//
			if (!(payRateUnit.equals("h") || payRateUnit.equals("d") || payRateUnit.equals("y")) && !payRateUnitIdMap.containsKey(payRateUnit)) {
				message.append("Warning: Invalid Pay Rate Unit(" + payRateUnit + "). Set to default \'hourly\'. ");
				payRateChar = 'h';
			} else if (payRateUnitIdMap.containsKey(payRateUnit)) {
				logger.info("hash pay rate unit(" + payRateUnit + ") to its ASCII value");
				payRateChar = payRateUnitIdMap.get(payRateUnit);
				logger.info("ASCII pay rate unit(" + payRateChar + ")");
			} else
				payRateChar = payRateUnit.charAt(0);
		} else {
			payRateChar = 'h';
		}
		//
		if (isNotEmpty(billRateUnit)) {
			//
			String unit = billRateUnit.toLowerCase();
			if (unit.equals("hourly") || unit.equals("daily") || unit.equals("yearly") || unit.equals("hour") || unit.equals("day") || unit.equals("year"))
				unit = unit.substring(0, 1);
			//
			billRateUnit = unit;
			//
			if (!(billRateUnit.equals("h") || billRateUnit.equals("d") || billRateUnit.equals("y")) && !billRateUnitIdMap.containsKey(billRateUnit)) {
				message.append("Warning: Invalid Bill Rate Unit(" + billRateUnit + "). Set to default \'hourly\'. ");
				billRateChar = 'h';
			} else if (billRateUnitIdMap.containsKey(billRateUnit)) {
				logger.info("hash bill rate unit(" + billRateUnit + ") to its ASCII value");
				billRateChar = billRateUnitIdMap.get(billRateUnit);
				logger.info("ASCII bill rate unit(" + billRateChar + ")");
			} else
				billRateChar = billRateUnit.charAt(0);
		} else {
			billRateChar = 'h';
		}
		//
		job.setRateper(payRateChar);
		job.setBillRatePer(billRateChar);
		job.setRfqTitle(title.replaceAll("\r\n|\n|\r|\n\r", ""));
		job.setSyncRequired(true);
		job.setHarvestEnable(true); // Default to ON/NOT Schedule, set
									// 'false' only when harvest == 'NOW'
		job.setDatePriorityUpdated(null); // Default to "ON Schedule", will
											// check team profile below
		//
		//
		if (description == null)
			description = "Job Description";
		//
		if (isNotEmpty(description)) {
			description = description.replaceAll("\r\n|\n|\r|\n\r", "<br>");
			job.setJobDescription(description);
		}
		// must set in order to show records
		job.setTeamid(teamid);
		job.setDateLastUpdated(new Timestamp(System.currentTimeMillis()));
		//
		//
		//
		//
		//
		//
		// set fields related to customerid
		if (contacts != null) {
			for (ContactRoleType contact : contacts) {
				if (contact.getShowOnJob()) {
					sql = " SELECT COMPANYID, COMPANYNAME, FIRSTNAME, LASTNAME FROM TCUSTOMER WHERE ID = ? and teamid = ? ";
					params = new Object[] { contact.getContactId(), jobDivaSession.getTeamId() };
					list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
						
						@Override
						public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							job.setCustomerId(contact.getContactId());
							job.setCompanyId(rs.getLong("COMPANYID"));
							job.setDepartment(rs.getString("COMPANYNAME"));
							job.setFirstName(rs.getString("FIRSTNAME"));
							job.setLastName(rs.getString("LASTNAME"));
							//
							return true;
						}
					});
					//
					if (list == null || list.size() == 0)
						throw new Exception("Error: Contact(" + contact.getContactId() + ") is not found.");
				}
			}
		}
		//
		if (companyid != null) {
			List<Company> companies = searchCompanyDao.searchForCompany(jobDivaSession, companyid);
			if (companies == null || companies.size() == 0)
				message.append("Warning: The companyid is invalid.");
			else {
				job.setDepartment(companies.get(0).getName());
				job.setCompanyId(companyid);
			}
		}
		//
		job.setRfqNoTeam("TEMP-API-REFNOTEAM");
		job.setRfqRefNo("TEMP-API-REFNO");
		//
		//
		// job.setCustomerId();
		//
		//
		//
		//
		//
		// set nullable fields
		//
		//
		if (divisionid != null) {
			sql = "SELECT ID FROM TDIVISION WHERE ID = ? AND TEAMID = ? ";
			params = new Object[] { divisionid, teamid };
			//
			//
			List<Long> divisons = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("ID");
				}
			});
			if (divisons == null || divisons.size() == 0) {
				// throw new Exception("Error: Division(" +
				// jobObj.getDivisionid() + ") is not found. \r\n");
				message.append("Warning: Division not assigned due to invalid division id -- " + divisionid + " \r\n");
				job.setDivisionId(null);
			} else
				job.setDivisionId(divisionid);
		}
		//
		if (isNotEmpty(department)) {
			// overwrite companyname searched by 'shown on job' contact, if
			// department is specified
			job.setDepartment(department);
		}
		//
		job.setAddress1(address1);
		job.setAddress2(address2);
		job.setCity(city);
		job.setZipcode(zipcode);
		String localCountrId = getCountryID(countryid);
		job.setCountry(isNotEmpty(localCountrId) ? localCountrId : "US");
		//
		if (isNotEmpty(state)) {
			state = lookupState(state, job.getCountry());
			if (state != null)
				job.setState(state);
			else
				message.append("State (" + job + ") was not set due to the mapping unfound.(with countryid(" + countryid + ")) \r\n");
		}
		//
		job.setStartDate(startdate != null ? startdate : new Date());
		job.setEndDate(enddate);
		//
		if (isNotEmpty(jobtype)) {
			Integer contract = getContractValue(jobtype, teamid);
			if (contract != null)
				job.setContract(contract);
			else if (leader == 13328 || leader == 13584)
				message.append("Position Type (" + jobtype + ") was not set due to it's invalid. ");
			else
				throw new Exception(String.format("Error: Position Type(%s) is invalid", jobtype));
		}
		//
		if (openings != null && openings > 0)
			job.setPositions(openings.shortValue());
		else
			job.setPositions((short) 1);
		//
		//
		if (fills != null && fills > 0) {
			job.setFills(fills.shortValue());
			if (openings == null || openings == 0)
				job.setPositions(fills.shortValue());
		} else
			job.setFills((short) 0);
		//
		if (maxsubmittals != null)
			job.setMaxSubmitals(maxsubmittals.shortValue());
		job.setRefCheck(reference);
		job.setDrugTest(drugtest);
		job.setBackCheck(backgroundcheck);
		job.setSecClearance(securityclearance);
		job.setInstruction(remarks);
		//
		if (isNotEmpty(submittalinstruction)) {
			submittalinstruction = submittalinstruction.replaceAll("\r\n|\n|\r|\n\r", "<br>");
			job.setSubInstruction(submittalinstruction);
		}
		if (minbillrate != null)
			job.setBillRateMin(new BigDecimal(minbillrate));
		if (maxbillrate != null)
			job.setBillRateMax(new BigDecimal(maxbillrate));
		//
		if (isNotEmpty(billratecurrency))
			job.setBillrateCurrency(getRateCurrencyID(defaultCurrencyID, billratecurrency));
		else
			job.setBillrateCurrency(defaultCurrencyID);
		//
		if (minpayrate != null)
			job.setRateMin(new BigDecimal(minpayrate));
		if (maxpayrate != null)
			job.setRateMax(new BigDecimal(maxpayrate));
		//
		//
		if (isNotEmpty(payratecurrency))
			job.setPayrateCurrency(getRateCurrencyID(defaultCurrencyID, payratecurrency));
		else
			job.setPayrateCurrency(defaultCurrencyID);
		//
		if (skills != null) {
			String str_util = "";
			for (int i = 0; i < skills.length; i++) {
				// parse years of experience
				String[] strArr = skills[i].toString().toUpperCase().split("~");
				if (strArr.length != 5)
					throw new Exception("Error: Invalid SkillType(" + skills[i] + ") format. \r\n");
				try {
					Double years = Math.round(Double.parseDouble(strArr[2]) * 10.0) / 10.0;
					str_util += strArr[0] + "~" + strArr[1] + "~" + years.toString() + "~" + strArr[3] + "~" + strArr[4] + "~";
				} catch (NumberFormatException e) {
					throw new Exception("Error: Invalid skill year(" + strArr[2] + "). Please set a valid number behind third '~'. \r\n");
				}
			}
			job.setSkills(str_util);
		} else {
			job.setSkills("Skills to be assigned~0~1~0~0~");
		}
		//
		//
		if (skillstates != null) {
			String str_util = "";
			for (int i = 0; i < skillstates.length; i++) {
				String state1 = lookupState(skillstates[i], null);
				if (state1 != null)
					str_util += state1 + "~";
				else
					throw new Exception("Error: Invalid state (" + skillstates[i] + "). \r\n");
			}
			job.setCriteriaState(str_util.toUpperCase());
		}
		//
		job.setCriteriaZipCode(skillzipcode);
		if (skillzipcodemiles != null && skillzipcodemiles > 0) {
			if (isEmpty(skillzipcode))
				throw new Exception("Error: Please set zipcode if zipcodemiles is set. \r\n");
			switch (skillzipcodemiles) {
				case 5:
					break;
				case 10:
					break;
				case 20:
					break;
				case 30:
					break;
				case 40:
					break;
				case 50:
					break;
				case 75:
					break;
				case 100:
					break;
				default:
					throw new Exception("Error: Invalid 'skillzipcodemiles'(" + skillzipcodemiles + "). Please choose within(5, 10, 20, 30, 40, 50, 75, 100). \r\n");
			}
			job.setCriteriaZipCodeMiles(skillzipcodemiles.shortValue());
		}
		//
		if (excludedskills != null) {
			String str_util = "";
			for (int i = 0; i < excludedskills.length; i++) {
				// parse years of experience
				String[] strArr = excludedskills[i].getSkillType().toUpperCase().split("~");
				if (strArr.length != 5)
					throw new Exception("Error: Invalid SkillType(" + excludedskills[i].getSkillType() + ") format. \r\n");
				try {
					Double years = Math.round(Double.parseDouble(strArr[2]) * 10.0) / 10.0;
					str_util += strArr[0] + "~" + strArr[1] + "~" + years.toString() + "~" + strArr[3] + "~" + strArr[4] + "~";
				} catch (NumberFormatException e) {
					throw new Exception("Error: Invalid skill year(" + strArr[2] + "). Please set a valid number behind third '~'. \r\n");
				}
			}
			job.setNotSkills(str_util);
		}
		//
		// harvester flag related fields
		Short s = 50; // defaults to 50 as set in
						// employers/myjobs/addjobdone.jsp
		sql = "Select /*+ first_rows index(IDX_FOR_LOADSKILL)*/ max_resumes_no from trfq where teamid = ? and rownum = 1 ";
		params = new Object[] { teamid };
		//
		//
		List<BigDecimal> result_bigD = jdbcTemplate.query(sql, params, new RowMapper<BigDecimal>() {
			
			@Override
			public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBigDecimal("max_resumes_no");
			}
		});
		if (result_bigD != null && result_bigD.size() > 0)
			s = result_bigD.get(0).shortValue(); // set as RfqEJBClass.insertRFQ
		job.setMaxResumesNo(s);
		//
		//
		// Determine jobdiva post based on division ID and team option
		int jobdivapost_division = -1;
		if (divisionid != null && divisionid > 0) {
			sql = "SELECT nvl(POSTTOPORTAL,-1) as max_resumes_no " //
					+ " FROM tdivision " //
					+ " WHERE teamid = ? AND id = ? "; //
			params = new Object[] { teamid, divisionid };
			List<BigDecimal> post_to_portal = jdbcTemplate.query(sql, params, new RowMapper<BigDecimal>() {
				
				@Override
				public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getBigDecimal("max_resumes_no");
				}
			});
			if (post_to_portal != null && !post_to_portal.isEmpty()) {
				jobdivapost_division = ((BigDecimal) post_to_portal.get(0)).intValue();
			}
		}
		sql = "SELECT nvl(DEFAULT_HARVESTER_NOSCHEDULE, 0), nvl(DEFAULT_NOPOST, 0) FROM TTEAM WHERE ID = ? ";
		params = new Object[] { teamid };
		List<List<Integer>> team_options = jdbcTemplate.query(sql, params, new RowMapper<List<Integer>>() {
			
			@Override
			public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Integer> list = new ArrayList<Integer>();
				list.add(rs.getInt(1));
				list.add(rs.getInt(2));
				return list;
			}
		});
		if (team_options != null && team_options.size() > 0) {
			if ((team_options.get(0).get(0)).intValue() == 1) {
				job.setDatePriorityUpdated(new Timestamp(1000)); // NOT
																	// Schedule,
																	// null
																	// means ON
																	// Schedule
			}
		}
		if (jobdivapost_division == 0)
			job.setJobdivaPost(true);
		else if (jobdivapost_division == -2 || jobdivapost_division > 0)
			job.setJobdivaPost(false);
		else if (jobdivapost_division == -1) {
			job.setJobdivaPost(team_options.get(0).get(0).intValue() == 1 ? true : false);
		}
		// HarvestEnable already set 'true' by default, 'false' only when
		// harvest set to 'now';
		job.setResumesNo(new Short("-1"));
		//
		if (isNotEmpty(harvest)) {
			String ha = harvest.trim().toLowerCase();
			if (ha.equals("now")) {
				if (resumes != null) {
					if (resumes < 10)
						throw new Exception("Error: The number of resumes is too low, should be at least 10. \r\n");
					else
						job.setResumesNo(resumes.shortValue());
				} else
					job.setResumesNo(new Short("1"));
				job.setDatePriorityUpdated(new Timestamp(System.currentTimeMillis()));
			} else if (ha.equals("on schedule")) {
				job.setResumesNo(new Short("-1"));
			} else if (ha.equals("not scheduled")) {
				job.setDatePriorityUpdated(new Timestamp(1000));
				job.setResumesNo(new Short("-1"));
			} else
				throw new Exception("Error: Invalid 'harvest' type, please select within('NOW', 'On Schedule', 'Not Scheduled')");
			//
			if (job.getDatePriorityUpdated() != null && job.getDatePriorityUpdated().getTime() > 1000)
				job.setHarvestEnable(false);
		}
		//
		Long jobId = insertJob(jobDivaSession, job);
		//
		// logger.info("After Insert Job jobId " + jobId);
		//
		job.setId(jobId);
		if (jobdivapost_division > 0) {
			String sqlInsert = "insert into trfq_csp (teamid, rfqid, portalid, dateposted) values (?,?,?, sysdate)";
			params = new Object[] { teamid, jobId, jobdivapost_division };
			// ,
			jdbcTemplate.update(sqlInsert, params);
		}
		//
		Long jobid = job.getId();
		//
		// create JobUser && JobContact for recruiters
		Vector<RecruiterObject> v_assigned = new Vector<RecruiterObject>();
		String primaryRecName = "";
		String primarySaleName = "";
		//
		if (users != null) {
			//
			List<UserRoleDef> userRoleDefs = gettUsersRoleDef(users);
			//
			for (UserRoleDef userRoleDef : userRoleDefs) {
				//
				sql = "SELECT * " //
						+ " FROM TRECRUITER " //
						+ " WHERE ID = ? AND ACTIVE = 1 AND GROUPID = ? ";
				params = new Object[] { userRoleDef.getUserId(), jobDivaSession.getTeamId() };
				//
				List<HashMap<String, Object>> userList = jdbcTemplate.query(sql, params, new RowMapper<HashMap<String, Object>>() {
					
					@Override
					public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						HashMap<String, Object> userInfos = new HashMap<String, Object>();
						userInfos.put("REC_EMAIL_DELETE", rs.getInt("REC_EMAIL_DELETE"));
						userInfos.put("REC_EMAIL", rs.getInt("REC_EMAIL"));
						userInfos.put("LEADER", rs.getLong("LEADER"));
						userInfos.put("EMAIL", rs.getString("EMAIL"));
						userInfos.put("REGION_CODE", rs.getString("REGION_CODE"));
						userInfos.put("FIRSTNAME", rs.getString("FIRSTNAME"));
						userInfos.put("LASTNAME", rs.getString("LASTNAME"));
						return userInfos;
					}
				});
				//
				if (userList == null || userList.isEmpty())
					throw new Exception("Error: User(" + userRoleDef.getUserId() + ") is not found. Can not add this user to the job. --1 ");
				//
				HashMap<String, Object> userInfos = userList.get(0);
				//
				Integer default_email = (Integer) userInfos.get("REC_EMAIL");
				if (default_email == null)
					default_email = new Integer(0);
				boolean rec_email = false;
				if (default_email.intValue() == 2) {
					if (userRoleDef.getIsLeadRecruiter() || userRoleDef.getIsLeadSales())
						rec_email = true;
				} else if (default_email.intValue() == 1)
					rec_email = true;
				//
				RecruiterObject rec = new RecruiterObject(0, 0);
				rec.email = (String) userInfos.get("EMAIL");
				rec.region_code = (String) userInfos.get("REGION_CODE");
				Long userLeader = (Long) userInfos.get("LEADER");
				Integer userRecEmailDelete = (Integer) userInfos.get("REC_EMAIL_DELETE");
				// (hiring managers) shouldn't receive the notifications,
				// rec_email_delete == 2 only lead rec/sales receive emails
				if (userLeader != 36L && (userRecEmailDelete == 1 || (userRecEmailDelete == 2 && (userRoleDef.getIsLeadRecruiter() || userRoleDef.getIsLeadSales()))))
					v_assigned.add(rec);
				//
				//
				jobUserDao.insertJobUser(jobid, userRoleDef.getUserId(), teamid, rec_email, userRoleDef.getIsLeadRecruiter(), userRoleDef.getIsSale(), userRoleDef.getIsLeadSales(), userRoleDef.getIsRecruiter(), status);
				if (userRoleDef.getIsLeadRecruiter() != null && userRoleDef.getIsLeadRecruiter())
					primaryRecName = userInfos.get("FIRSTNAME") + " " + userInfos.get("LASTNAME"); // for
				// emails
				if (userRoleDef.getIsLeadSales() != null && userRoleDef.getIsLeadSales())
					primarySaleName = userInfos.get("FIRSTNAME") + " " + userInfos.get("LASTNAME"); // for
				// emails
				//
				sql = "SELECT  * FROM TCUSTOMER WHERE IFRECRUITERTHENID = ? AND TEAMID = ? ";
				params = new Object[] { userRoleDef.getUserId(), jobDivaSession.getTeamId() };
				//
				List<Long> dbContactsList = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						return rs.getLong("ID");
					}
				});
				if (dbContactsList == null || dbContactsList.size() == 0)
					throw new Exception("Error: Recruiter(" + userRoleDef.getUserId() + ") is not found. --2 ");
				// /
				int role = 0;
				if (userRoleDef.getIsSale() != null && userRoleDef.getIsSale())
					role = role | 1;
				if (userRoleDef.getIsLeadSales() != null && userRoleDef.getIsLeadSales())
					role = role | 2;
				if (userRoleDef.getIsRecruiter() != null && userRoleDef.getIsRecruiter())
					role = role | 4;
				if (userRoleDef.getIsLeadRecruiter() != null && userRoleDef.getIsLeadRecruiter())
					role = role | 8;
				int roleid = 0;
				switch (role) {
					case 0:
						roleid = 950;
						break;// nothing
					case 1:
						roleid = 999;
						break;// (S)
					case 3:
						roleid = 996;
						break;// (PS)
					case 4:
						roleid = 997;
						break;// (R)
					case 5:
						roleid = 999;
						break;// (R)(S)
					case 7:
						roleid = 996;
						break;// (PS)(R)
					case 12:
						roleid = 998;
						break;// (PR)
					case 13:
						roleid = 999;
						break;// (S)(PR)
					case 15:
						roleid = 996;
						break;// (PS)(PR)
				}
				//
				jobContactDao.saveJobContact(teamid, jobid, dbContactsList.get(0), false, roleid);
			}
		} else {
			//
			sql = "SELECT * " //
					+ " FROM TRECRUITER " //
					+ " WHERE ID = ? AND ACTIVE = 1 AND GROUPID = ? ";
			params = new Object[] { jobDivaSession.getRecruiterId(), jobDivaSession.getTeamId() };
			//
			List<HashMap<String, Object>> userList = jdbcTemplate.query(sql, params, new RowMapper<HashMap<String, Object>>() {
				
				@Override
				public HashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					HashMap<String, Object> userInfos = new HashMap<String, Object>();
					userInfos.put("REC_EMAIL_DELETE", rs.getInt("REC_EMAIL_DELETE"));
					userInfos.put("REC_EMAIL", rs.getInt("REC_EMAIL"));
					userInfos.put("LEADER", rs.getLong("LEADER"));
					userInfos.put("EMAIL", rs.getString("EMAIL"));
					userInfos.put("REGION_CODE", rs.getString("REGION_CODE"));
					userInfos.put("FIRSTNAME", rs.getString("FIRSTNAME"));
					userInfos.put("LASTNAME", rs.getString("LASTNAME"));
					return userInfos;
				}
			});
			//
			HashMap<String, Object> userInfos = userList.get(0);
			Integer default_email = (Integer) userInfos.get("REC_EMAIL");
			boolean rec_email = false;
			if (default_email.intValue() == 1)
				rec_email = true;
			//
			RecruiterObject rec = new RecruiterObject(0, 0);
			rec.email = ((String) userInfos.get("EMAIL"));
			rec.region_code = ((String) userInfos.get("REGION_CODE"));
			//
			Long userLeader = (Long) userInfos.get("LEADER");
			Integer userRecEmailDelete = (Integer) userInfos.get("REC_EMAIL_DELETE");
			// (hiring managers) shouldn't receive the notifications,
			// rec_email_delete == 2 only lead rec/sales receive emails
			if (userLeader != 36L && userRecEmailDelete == 1)
				v_assigned.add(rec);
			//
			//
			jobUserDao.insertJobUser(jobid, jobDivaSession.getRecruiterId(), teamid, rec_email, false, true, false, true, status);
			//
			sql = "SELECT ID FROM TCUSTOMER WHERE IFRECRUITERTHENID = ? AND TEAMID = ?";
			params = new Object[] { jobDivaSession.getRecruiterId(), jobDivaSession.getTeamId() };
			//
			List<Long> dbContactsList = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("ID");
				}
			});
			if (dbContactsList == null || dbContactsList.size() == 0)
				throw new Exception("Error: Recruiter(" + jobDivaSession.getRecruiterId() + ") is not found. --3\r\n");
			//
			int roleid = 999;
			jobContactDao.saveJobContact(teamid, jobid, dbContactsList.get(0), false, roleid);
		}
		//
		//
		//
		//
		// Update harvester assignment
		if (users != null) {
			List<UserRoleDef> userRoleDefs = gettUsersRoleDef(users);
			for (UserRoleDef userRoleDef : userRoleDefs) {
				if (userRoleDef.getIsLeadRecruiter() != null && userRoleDef.getIsLeadRecruiter())
					updateHarvesterAssignment(jobid, teamid, divisionid, userRoleDef.getUserId());
			}
		}
		// create real JobContact
		if (contacts != null) {
			for (ContactRoleType contactRoleType : contacts) {
				Contact contact = contactDao.getContact(jobDivaSession, contactRoleType.getContactId());
				if (contact == null || contact.getTeamId() != teamid)// ||
					// !con.isActive())
					throw new Exception("Error: Contact(" + contact.getId() + ") is not found. \r\n");
				//
				if (isRecruiter(contactRoleType.getRoleId()))
					throw new Exception("Error: Invalid contact role type(" + contactRoleType.getRoleId() + "). \r\n");
				//
				jobContactDao.saveJobContact(teamid, jobid, contactRoleType.getContactId(), contactRoleType.getShowOnJob(), contactRoleType.getRoleId().intValue());
			}
		}
		// insert a job note
		String str = "New Job is Created";
		jobNoteDao.addJobNote(jobDivaSession, jobid, 5, jobDivaSession.getRecruiterId(), 0, str);
		//
		//
		// Start Transaction => create rfqno_team
		//
		String rfqno_team = null;
		String rfq_refno = null;
		//
		CallableStatement prepareCall = jdbcTemplate.getDataSource().getConnection().prepareCall("{? = call pkg_RFQ.Get_Rfqno_Team(?)}");
		try {
			prepareCall.setLong(2, teamid);
			prepareCall.registerOutParameter(1, Types.VARCHAR);
			prepareCall.execute();
			//
			//
			rfqno_team = prepareCall.getString(1);
			rfq_refno = rfqno_team;
			if (isNotEmpty(optionalref))
				rfq_refno = optionalref;
		} finally {
			prepareCall.close();
		}
		//
		// update reference numbers
		String sqlUpdate = "UPDATE TRFQ SET RFQNO_TEAM = ?, RFQREFNO = ? WHERE TEAMID = ? and ID = ? ";
		params = new Object[] { rfqno_team, rfq_refno, teamid, jobid };
		jdbcTemplate.update(sqlUpdate, params);
		//
		//
		//
		message.append("job(" + rfqno_team + ") is created. \r\n");
		//
		//
		// Start Transaction => Insert attachments
		if (attachments != null) {
			//
			for (Attachment jdAttachment : attachments) {
				if (isEmpty(jdAttachment.getFileName()))
					throw new Exception("Invalid file name -- " + jdAttachment.getFileName());
				if (jdAttachment.getFileData().length == 0)
					throw new Exception("Could not read a valid file");
				if (isNotEmpty(jdAttachment.getDescription()))
					throw new Exception("C -- " + jdAttachment.getDescription());
			}
			//
			logger.info("Has Attachments, trying insert...");
			for (Attachment jdAttachment : attachments) {
				String err = insertAttachments(teamid, jobid, jdAttachment);
				if (err.trim().length() > 0)
					message.append(err);
			}
		}
		//
		// Insert UDFs
		if (userfields != null) {
			try {
				//
				validateUserFields(jobDivaSession, teamid, userfields, UDF_FIELDFOR_JOB);
				//
				for (Userfield userfield : userfields) {
					if (isNotEmpty(userfield.getUserfieldValue())) {
						jobUserFieldsDao.addJobUserFieldsDao(jobid, userfield.getUserfieldId(), teamid, currentDT, userfield.getUserfieldValue());
					}
				}
			} catch (Exception e) {
				message.append("Failed to create UDFs. " + e.getMessage());
			}
		}
		//
		//
		if (v_assigned.size() > 0 && jobid != null) {
			// logger.info("Emails will be sent to " + v_assigned.size() + "
			// users. \r\n");
			sendAssignNotificationInThread(jobDivaSession, description, job, v_assigned, primaryRecName, primarySaleName, rfqno_team, rfq_refno);
		}
		return jobid;
		//
	}
	
	private void sendAssignNotificationInThread(JobDivaSession jobDivaSession, String description, Job job, Vector<RecruiterObject> v_assigned, String primaryRecName, String primarySaleName, String rfqno_team, String rfq_refno) throws Exception {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					sendAssignNotification(jdbcTemplate, v_assigned, null, job, jobDivaSession.getTeamId(), primaryRecName, primarySaleName, description, rfqno_team, rfq_refno);
				} catch (Exception e) {
					logger.info("sendAssignNotificationInThread " + v_assigned.size() + " users. ERROR :: " + e.getMessage());
				}
			}
		}).start();
	}
	
	private String getJobStatusName(Integer status, Long teamid) {
		String statusName = "";
		try {
			String sql = "SELECT name " //
					+ " FROM trfq_statuses "//
					+ " WHERE id = ? " //
					+ " AND (teamid = ? OR teamid = 0)";
			Object[] params = new Object[] { status, teamid };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			//
			List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("name");
				}
			});
			//
			if (list != null && list.size() > 0)
				statusName = list.get(0);
		} catch (Exception e) {
		}
		return statusName;
	}
	
	public Boolean updateJob(JobDivaSession jobDivaSession, Long jobid, String optionalref, String title, String description, String postingtitle, String postingdescription, Long companyid, ContactRoleType[] contacts, UserRole[] users,
			String address1, String address2, String city, String state, String zipcode, String countryid, Date startdate, Date enddate, Integer status, String jobtype, String priority, Integer openings, Integer fills, Integer maxsubmittals,
			Boolean hidemyclient, Boolean hidemyclientaddress, Boolean hidemeandmycompany, Boolean overtime, Boolean reference, Boolean travel, Boolean drugtest, Boolean backgroundcheck, Boolean securityclearance, String remarks,
			String submittalinstruction, Double minbillrate, Double maxbillrate, Double minpayrate, Double maxpayrate, Userfield[] userfields, String harvest, Integer resumes, Long divisionid) throws Exception {
		//
		String userLog = "";
		if (users != null) {
			for (UserRole userRole : users)
				userLog += "[" + userRole.getRole() + "/" + userRole.getAction() + "/" + userRole.getUserId() + "]";
		}
		logger.info("updateJob(" + jobid + "/" + jobDivaSession.getTeamId() + "/) -- users [" + userLog + "]");
		//
		/* Check if status is user-defined status in this team */
		if (status != null) {
			logger.info("Job status to be verified: " + status);
			//
			checkJobStatus(jobDivaSession.getTeamId(), status);
		}
		/* Check User Defined Job Priority */
		Integer priority_id = getPriorityId(jobDivaSession.getTeamId(), priority);
		if (priority_id != null) {
			checkJobPriority(jobDivaSession, priority, priority_id);
		}
		/* Update Job */
		List<Job> jobs = null;
		//
		try {
			jobs = searchJobs(jobDivaSession, jobid, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		} catch (Exception e) {
			logger.info("Error: Job " + jobid + " is not found  ERROR: " + e.getMessage());
		}
		//
		if (jobs == null || jobs.size() == 0) {
			//
			throw new Exception("Error: Job " + jobid + " is not found.");
		} else {
			logger.info("Updating job(" + jobid + "). ");
		}
		//
		try {
			Job job = jobs.get(0);
			//
			//
			if (status == null) {
				status = job.getJobStatus();
			}
			//
			//
			if (isNotEmpty(optionalref))
				job.setRfqRefNo(optionalref);
			//
			if (isNotEmpty(title))
				job.setRfqTitle(title.replaceAll("\r\n|\n|\r|\n\r", ""));
			//
			if (isNotEmpty(description)) {
				job.setJobDescription(description.replaceAll("\r\n|\n|\r|\n\r", "<br>"));
			}
			//
			if (isNotEmpty(postingtitle))
				job.setPostingTitle(postingtitle);
			//
			if (isNotEmpty(postingdescription)) {
				job.setPostingDescription(postingdescription);
			}
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			Long teamid = jobDivaSession.getTeamId();
			// Update contacts
			if (contacts != null) {
				//
				int cnt_showonjob = 0;
				for (ContactRoleType contactRoleType : contacts) {
					if (contactRoleType.getContactId() < 1)
						throw new Exception("Error: Contactid should be a positive number. \r\n");
					if (contactRoleType.getAction() != null && contactRoleType.getAction() != 1 && contactRoleType.getAction() != 2)
						throw new Exception("Error: Action code should be 1(insert/modify) or 2(delete) \r\n");
					if (contactRoleType.getShowOnJob() != null && contactRoleType.getShowOnJob() && contactRoleType.getAction() != null && contactRoleType.getAction() == 1)
						cnt_showonjob++;
				}
				if (cnt_showonjob > 1)
					throw new Exception("Error: Only one contact can be shown on job screen. \r\n");
				//
				//
				String sql = "SELeCT * FROM TRFQ_CUSTOMERS WHERE RFQID = ? and TEAMID = ? ";
				Object[] params = new Object[] { jobid, teamid };
				//
				//
				List<List<Object>> oldcontacts = jdbcTemplate.query(sql, params, new RowMapper<List<Object>>() {
					
					@Override
					public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
						List<Object> pair = new ArrayList<Object>();
						pair.add(rs.getLong("CUSTOMERID"));
						pair.add(rs.getBoolean("SHOWONJOB"));
						pair.add(rs.getLong("ROLEID"));
						return pair;
					}
				});
				//
				Hashtable<Long, Boolean> pairs = new Hashtable<Long, Boolean>(oldcontacts.size());
				for (int i = 0; i < oldcontacts.size(); i++) {
					if (isRecruiter((Long) oldcontacts.get(i).get(2)))
						continue;
					if (oldcontacts.get(i).get(1) == null)
						pairs.put((Long) oldcontacts.get(i).get(0), false);
					else
						pairs.put((Long) oldcontacts.get(i).get(0), (Boolean) oldcontacts.get(i).get(1));
				}
				//
				//
				// Check updated contacts shownonjob setting
				int soj_cnt = 0;
				long customerid = 0L;
				for (ContactRoleType contactRoleType : contacts) {
					if (contactRoleType.getAction() == 1) { // insert or modify
						pairs.put(contactRoleType.getContactId(), contactRoleType.getShowOnJob());
					} else if (contactRoleType.getAction() == 2) { // delete
						pairs.remove(contactRoleType.getContactId());
					}
				}
				//
				for (Map.Entry<Long, Boolean> entry : pairs.entrySet()) {
					if (entry.getValue() != null && entry.getValue()) {
						soj_cnt++;
						customerid = entry.getKey();
					}
				}
				//
				if (soj_cnt != 1)
					throw new Exception("Error: There should be one and only one contact shown on job, please make sure the flags are set correctly. \r\n");
				//
				// Write back updates
				for (ContactRoleType contactRoleType : contacts) {
					Long roleid = contactRoleType.getRoleId();
					if (isRecruiter(roleid))
						throw new Exception("Error: Invalid role type(" + roleid + ") of job contact(" + contactRoleType.getContactId() + "). \r\n");
					//
					// verify contact
					sql = " SELECT ID FROM TCUSTOMER WHERE ID = ? and teamid = ? ";
					params = new Object[] { contactRoleType.getContactId(), jobDivaSession.getTeamId() };
					List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("ID");
						}
					});
					//
					// Contact contact = contactDao.getContact(jobDivaSession,
					// contactRoleType.getContactId());
					if (list == null || list.size() == 0)
						throw new Exception("Error: Contact(" + contactRoleType.getContactId() + ") is not found. Can not add this contact to the job. \r\n");
					//
					//
					// insert or modify or delete a contact from the job
					sql = "SELECT  * FROM TRFQ_CUSTOMERS  where RFQID = ?  and TEAMID = ?  and CUSTOMERID = ? ";
					params = new Object[] { jobid, jobDivaSession.getTeamId(), contactRoleType.getContactId() };
					//
					List<Map<String, Object>> dbJobContactsList = jdbcTemplate.query(sql, params, new RowMapper<Map<String, Object>>() {
						
						@Override
						public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							Map<String, Object> contactIfo = new HashMap<String, Object>();
							contactIfo.put("TEAMID", rs.getLong("TEAMID"));
							contactIfo.put("RFQID", rs.getLong("RFQID"));
							contactIfo.put("CUSTOMERID", rs.getLong("CUSTOMERID"));
							contactIfo.put("SHOWONJOB", rs.getBoolean("SHOWONJOB"));
							contactIfo.put("ROLEID", rs.getInt("ROLEID"));
							return contactIfo;
						}
					});
					//
					Map<String, Object> jobContact = null;
					//
					if (dbJobContactsList != null && dbJobContactsList.size() > 0) {
						for (Map<String, Object> jdContact : dbJobContactsList) {
							Integer roleId = (Integer) jdContact.get("ROLEID");
							if (roleId != null && isRecruiter(roleId.longValue()))
								continue;
							jobContact = jdContact;
						}
					}
					//
					if (contactRoleType.getAction() == 1) {
						if (jobContact == null) { // insert
							jobContactDao.saveJobContact(teamid, jobid, contactRoleType.getContactId(), contactRoleType.getShowOnJob(), roleid != null ? roleid.intValue() : null);
						} else { // modify
							jobContactDao.updateJobContact(teamid, jobid, contactRoleType.getContactId(), contactRoleType.getShowOnJob(), roleid != null ? roleid.intValue() : null);
						}
					} else if (contactRoleType.getAction() == 2) { // delete
						logger.info("Deleting contact(" + contactRoleType.getContactId() + ") to job.");
						if (jobContact == null)
							throw new Exception("Error: Can not delete contact(" + contactRoleType.getContactId() + ") from the job due to either the contact not found or the contact is a recruiter. \r\n");
						jobContactDao.deleteJobContact(teamid, jobid, contactRoleType.getContactId());
					}
				}
				//
				//
				//
				if (job.getCustomerId() != customerid) {
					sql = " SELECT ID, COMPANYID, COMPANYNAME, FIRSTNAME, LASTNAME FROM TCUSTOMER WHERE ID = ? and teamid = ? ";
					params = new Object[] { customerid, jobDivaSession.getTeamId() };
					jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
						
						@Override
						public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							job.setCustomerId(rs.getLong("ID"));
							job.setCompanyId(rs.getLong("COMPANYID"));
							job.setDepartment(rs.getString("COMPANYNAME"));
							job.setFirstName(rs.getString("FIRSTNAME"));
							job.setLastName(rs.getString("LASTNAME"));
							//
							return true;
						}
					});
					//
				}
			}
			// Update job users in both JobUser and JobContact
			if (users != null) {
				HashMap<Long, Integer> userRole = new HashMap<Long, Integer>();// limit
																				// for
																				// primary
																				// sales/recruiter
				// get previous users and their roles
				List<JobUser> prevUsers_it = jobUserDao.getJobUsers(jobid, teamid);
				for (JobUser prevUser : prevUsers_it) {
					int role = 0;
					if (prevUser.getLeadSales())
						role = role | 2;
					if (prevUser.getLeadRecruiter())
						role = role | 8;
					userRole.put(prevUser.getRecruiterId(), role);
				}
				//
				//
				for (UserRole localUuserRole : users) {
					//
					if (localUuserRole.getAction() != 1 && localUuserRole.getAction() != 2)
						throw new Exception("Error: Action code should be 1(insert/modify) or 2(delete) \r\n");
					//
					List<JobUser> jobUsers = jobUserDao.getJobUsers(jobid, teamid, localUuserRole.getUserId());
					JobUser jobuser = jobUsers != null && jobUsers.size() > 0 ? jobUsers.get(0) : null;
					//
					if (jobuser == null) {
						logger.info("getJobUsers(" + jobid + "/" + teamid + "/" + localUuserRole.getUserId() + ") -- NULLABLE");
					}
					//
					String sql = "SELECT  * FROM TCUSTOMER WHERE IFRECRUITERTHENID = ? AND TEAMID = ? ";
					Object[] params = new Object[] { localUuserRole.getUserId(), jobDivaSession.getTeamId() };
					//
					List<Long> cid = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
						
						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							return rs.getLong("ID");
						}
					});
					if (cid == null || cid.size() == 0)
						throw new Exception("Error: Recruiter(" + localUuserRole.getUserId() + ") is not found. Failed in job user update. \r\n");
					//
					Boolean sale = false;
					Boolean leadSale = false;
					Boolean recruiter = false;
					Boolean leadRecruiter = false;
					String strRole = localUuserRole.getRole();
					if (isNotEmpty(strRole)) {
						strRole = strRole.toLowerCase();
						if (strRole.indexOf("sales") > -1)
							sale = true;
						if (strRole.indexOf("primary sales") > -1)
							leadSale = true;
						if (strRole.indexOf("recruiter") > -1)
							recruiter = true;
						if (strRole.indexOf("primary recruiter") > -1)
							leadRecruiter = true;
					}
					//
					int role = 0;
					if (sale)
						role = role | 1;
					if (leadSale)
						role = role | 2;
					if (recruiter)
						role = role | 4;
					if (leadRecruiter)
						role = role | 8;
					int roleid = 0;
					switch (role) {
						case 0:
							roleid = 950;
							break;// nothing
						case 1:
							roleid = 999;
							break;// (S)
						case 3:
							roleid = 996;
							break;// (PS)
						case 4:
							roleid = 997;
							break;// (R)
						case 5:
							roleid = 999;
							break;// (R)(S)
						case 7:
							roleid = 996;
							break;// (PS)(R)
						case 12:
							roleid = 998;
							break;// (PR)
						case 13:
							roleid = 999;
							break;// (S)(PR)
						case 15:
							roleid = 996;
							break;// (PS)(PR)
					}
					//
					// grab the record in TRFQ_CUSTOMERS ready for update
					sql = "SELECT  * FROM TRFQ_CUSTOMERS  where RFQID = ?  and TEAMID = ?  and CUSTOMERID = ? ";
					params = new Object[] { jobid, jobDivaSession.getTeamId(), cid.get(0) };
					//
					List<Map<String, Object>> dbJobContactsList = jdbcTemplate.query(sql, params, new RowMapper<Map<String, Object>>() {
						
						@Override
						public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
							//
							Map<String, Object> contactIfo = new HashMap<String, Object>();
							contactIfo.put("TEAMID", rs.getLong("TEAMID"));
							contactIfo.put("RFQID", rs.getLong("RFQID"));
							contactIfo.put("CUSTOMERID", rs.getLong("CUSTOMERID"));
							contactIfo.put("SHOWONJOB", rs.getBoolean("SHOWONJOB"));
							contactIfo.put("ROLEID", rs.getInt("ROLEID"));
							return contactIfo;
						}
					});
					//
					Map<String, Object> jobContact = null;
					//
					Boolean insertJobContact = false;
					if (dbJobContactsList != null && dbJobContactsList.size() > 0) {
						for (Map<String, Object> jdContact : dbJobContactsList) {
							Integer roleId = (Integer) jdContact.get("ROLEID");
							if (roleId != null && !isRecruiter(roleId.longValue()))
								continue;
							jobContact = jdContact;
							insertJobContact = false;
						}
					}
					//
					if (jobContact == null) {
						insertJobContact = true;
						jobContact = new HashMap<String, Object>();
						jobContact.put("TEAMID", jobDivaSession.getTeamId());
						jobContact.put("RFQID", jobid);
						jobContact.put("CUSTOMERID", cid.get(0));
						jobContact.put("SHOWONJOB", false);
						jobContact.put("ROLEID", roleid);
					}
					logger.info("jobcontact(update users) -- " + jobContact + " roleid: " + roleid);
					if (localUuserRole.getAction() == 1) {
						if (jobuser == null) { // insert JobUser &&
												// JobContact
							logger.info("Adding recruiter(" + localUuserRole.getUserId() + ") to the job.");
							//
							sql = "SELECT * " //
									+ " FROM TRECRUITER " //
									+ " WHERE ID = ? AND ACTIVE = 1 AND GROUPID = ? ";
							params = new Object[] { localUuserRole.getUserId(), jobDivaSession.getTeamId() };
							//
							List<Long> userList = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
								
								@Override
								public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
									//
									return rs.getLong("ID");
								}
							});
							//
							if (userList == null || userList.isEmpty())
								throw new Exception("Error: User(" + localUuserRole.getUserId() + ") is not found. Can not add this user to the job. \r\n");
							//
							jobUserDao.insertJobUser(jobid, localUuserRole.getUserId(), teamid, false, leadRecruiter, sale, leadSale, recruiter, status);
							;
						} else { // modify
							logger.info("Modifying recruiter(" + localUuserRole.getUserId() + ") on the job.");
							jobUserDao.updateJobUser(jobid, localUuserRole.getUserId(), teamid, jobuser.getReceiveEmail(), leadRecruiter, sale, leadSale, recruiter, status);
							//
							jobContact.put("ROLEID", roleid);
							logger.info("roleid: " + roleid + " for contid:" + (jobContact != null ? jobContact.get("CUSTOMERID") : "NULL"));
						}
						userRole.put(localUuserRole.getUserId(), role);// for
																		// check
																		// primary
																		// recruiter/sales
						//
						if (insertJobContact)
							jobContactDao.saveJobContact(teamid, jobid, (Long) jobContact.get("CUSTOMERID"), (Boolean) jobContact.get("SHOWONJOB"), (Integer) jobContact.get("ROLEID"));
						else
							jobContactDao.updateJobContact(teamid, jobid, (Long) jobContact.get("CUSTOMERID"), (Boolean) jobContact.get("SHOWONJOB"), (Integer) jobContact.get("ROLEID"));
					} else if (localUuserRole.getAction() == 2) { // delete
						//
						//
						String strJobUser = "";
						if (jobuser != null) {
							strJobUser = teamid + "//" + jobuser.getTeamId() + "/" + jobuser.getRecruiterId() + "/" + jobuser.getLeadSales();
						}
						//
						//
						//
						logger.info("Deleting recruiter(" + localUuserRole.getUserId() + "[" + strJobUser + "]) from the job.");
						//
						//
						if (jobuser == null)
							throw new Exception("Error: User(" + localUuserRole.getUserId() + ") is not found. Can not delete this user from the job. \r\n");
						//
						//
						userRole.remove(localUuserRole.getUserId());
						//
						jobUserDao.deletJobuser(jobid, localUuserRole.getUserId(), teamid);
						//
						jobContactDao.deleteJobContact(teamid, jobid, (Long) jobContact.get("CUSTOMERID"));
						//
					}
				}
				//
				//
				int count_leadsale = 0;
				int count_leadrec = 0;
				for (long recid : userRole.keySet()) {
					logger.info("recid" + recid + " userRole.get(recid) " + userRole.get(recid));
					if ((userRole.get(recid) & 2) == 2)
						count_leadsale++;
					if ((userRole.get(recid) & 8) == 8)
						count_leadrec++;
				}
				//
				if (count_leadsale > 1)
					throw new Exception("Error: Only one user can be assigned as Primary Sales. ");
				//
				if (count_leadrec > 1)
					throw new Exception("Error: Only one user can be assigned as Primary Recruiter. ");
				//
			}
			//
			//
			// update companyid, if there is a contact, it will be updated
			// based on contact
			if (companyid != null) {
				List<Company> companies = searchCompanyDao.searchForCompany(jobDivaSession, companyid);
				if (companies == null || companies.size() == 0) {
					// message.append("Warning: The companyid is invalid.");
				} else {
					job.setDepartment(companies.get(0).getName());
					job.setCompanyId(companyid);
				}
			}
			//
			if (isNotEmpty(address1))
				job.setAddress1(address1);
			//
			if (isNotEmpty(address2))
				job.setAddress2(address2);
			//
			if (isNotEmpty(city))
				job.setCity(city);
			//
			String localCountrId = "";
			if (countryid != null)
				localCountrId = getCountryID(countryid);
			//
			job.setCountry(isNotEmpty(localCountrId) ? localCountrId : "US");
			//
			if (isNotEmpty(state)) {
				state = lookupState(state, job.getCountry());
				if (state != null)
					job.setState(state);
				else
					throw new Exception("Error: State (" + state + ") can not be updated due to the mapping unfound.(with countryid(" + countryid + ")) \r\n");
			}
			//
			if (isNotEmpty(zipcode))
				job.setZipcode(zipcode);
			//
			if (startdate != null) {
				// Timestamp issuedate = new
				// Timestamp(isujob.getDateissued().getTime());
				// issuedate.setYear(issuedate.getYear() - 1);
				// if (jobObj.getStartdate().before(issuedate))
				// throw new Exception("Error: Start Date(" +
				// jobObj.getStartdate().toString() + ") should not precede
				// Issue Date(" + job.getDateissued().toString() + ") by more
				// than one year.");
				job.setStartDate(startdate);
			}
			//
			if (enddate != null)
				job.setEndDate(enddate);
			//
			if (status != null && status != job.getJobStatus()) {
				// insert a job note
				// SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
				String str = "The Job Status was changed from " //
						+ getJobStatusName(status, teamid) //
						+ " to " //
						+ getJobStatusName(status, teamid);
				//
				//
				//
				jobNoteDao.addJobNote(jobDivaSession, jobid, 5, jobDivaSession.getRecruiterId(), 0, str);
				//
				// insert a job status log
				jobStatusLogDao.addJobStatusLog(teamid, jobid, status, jobDivaSession.getRecruiterId(), status);
				//
				// send job tatus Notification
				// --get the name of the recruiter who modified the status
				try {
					sendUpdateJobNotification(job, teamid, jobDivaSession.getRecruiterId(), status);
				} catch (Exception e) {
					logger.info("Send Update Job Notification [" + jobid + "] Error :: " + e.getMessage());
				}
				// set new job status
				if (status != null)
					job.setJobStatus(status);
			}
			//
			// /
			if (isNotEmpty(jobtype)) {
				Integer contract = getContractValue(jobtype.toLowerCase(), teamid);
				if (contract != null)
					job.setContract(contract);
				else
					throw new Exception(String.format("Error: Position Type(%s) is invalid", jobtype));
			}
			//
			/* Check User Defined Job Priority */
			if (priority_id != null) {
				job.setJobPriority(priority_id);
			}
			//
			if (maxsubmittals != null)
				job.setMaxSubmitals(maxsubmittals.shortValue());
			//
			//
			if (reference != null)
				job.setRefCheck(reference);
			//
			if (drugtest != null)
				job.setDrugTest(drugtest);
			//
			if (backgroundcheck != null)
				job.setBackCheck(backgroundcheck);
			//
			if (securityclearance != null)
				job.setSecClearance(securityclearance);
			//
			if (isNotEmpty(remarks))
				job.setInstruction(remarks);
			//
			if (isNotEmpty(submittalinstruction)) {
				submittalinstruction = submittalinstruction.replaceAll("\r\n|\n|\r|\n\r", "<br>");
				job.setSubInstruction(submittalinstruction);
			}
			//
			if (minbillrate != null)
				job.setBillRateMin(new BigDecimal(minbillrate));
			//
			if (maxbillrate != null)
				job.setBillRateMax(new BigDecimal(maxbillrate));
			//
			if (minpayrate != null)
				job.setRateMin(new BigDecimal(minpayrate));
			//
			if (maxpayrate != null)
				job.setRateMax(new BigDecimal(maxpayrate));
			//
			if (openings != null) {
				short positions = openings.shortValue();
				if (fills != null)
					job.setFills(fills.shortValue());
				else if (positions < job.getFills().shortValue())
					throw new Exception("Error: Positions(" + positions + ") should be larger than fills(" + job.getFills() + ")");
				job.setPositions(openings.shortValue());
				//
			} else if (fills != null) {
				short positions = (openings == null ? (short) 0 : openings.shortValue());
				if (fills.shortValue() > positions)
					throw new Exception("Error: Fills(" + fills + ") should be no larger than positions(" + positions + ")");
				job.setFills(fills.shortValue());
			}
			//
			if (hidemyclientaddress != null)
				job.setPrivateAddress(hidemyclientaddress);
			//
			if (hidemeandmycompany != null)
				job.setPrivateMyCompanyName(hidemeandmycompany);
			//
			if (overtime != null)
				job.setOvertime(overtime);
			//
			if (travel != null)
				job.setTravel(travel);
			//
			if (isNotEmpty(harvest)) {
				Short resumeNo;
				String ha = harvest.toLowerCase();
				if (ha.equals("now")) {
					if (resumes != null) {
						if (resumes < 10)
							throw new Exception("Error: The number of resumes is too low, should be at least 10. \r\n");
						else
							resumeNo = resumes.shortValue();
					} else
						resumeNo = new Short("1");
					job.setDatePriorityUpdated(new Date());
				} else if (ha.equals("on schedule")) {
					job.setDatePriorityUpdated(null);
					resumeNo = new Short("-1");
				} else if (ha.equals("not scheduled")) {
					job.setDatePriorityUpdated(new Timestamp(1000));
					resumeNo = new Short("-1");
				} else
					throw new Exception("Error: Invalid 'harvest' type, please select within('NOW', 'On Schedule', 'Not Scheduled')");
				//
				if (!job.getHarvestEnable())
					throw new Exception("Error: Job(" + job.getId() + ") was scheduled to 'NOW' and unable to change harvest status. \r\n");
				//
				if (resumeNo != null) { // when harvest is set
										// to 'now'
					if (job.getResumesNo() != null && job.getResumesNo().compareTo(job.getMaxResumesNo()) > 0) { // compare
						// to
						// max_resumes_no
						throw new Exception("Error: The number of resumes exceeds the maximum limit of " + job.getMaxResumesNo() + ". \r\n");
					} else
						job.setResumesNo(resumeNo);
				}
				//
				if (job.getDatePriorityUpdated() != null && job.getDatePriorityUpdated().getTime() > 1000)
					job.setHarvestEnable(false);
			}
			/*
			 * Further checking should be added to check the udf_id, values for
			 * listed fields By creating mapping for tuserfields &
			 * tuserfield_listvalues
			 */
			if (userfields != null) {
				if (userfields != null) {
					//
					validateUserFields(jobDivaSession, teamid, userfields, UDF_FIELDFOR_JOB);
					//
					for (Userfield userfield : userfields) {
						//
						if (isEmpty(userfield.getUserfieldValue())) {
							//
							jobUserFieldsDao.deleteJobUserFieldsDao(jobid, userfield.getUserfieldId(), jobDivaSession.getTeamId());
							//
						} else {
							//
							String sql = "SELECT Count(*) as CNT FROM TRFQ_USERFIELDS where RFQID = ? and USERFIELD_ID = ? AND TEAMID = ? ";//
							Object[] params = new Object[] { jobid, userfield.getUserfieldId(), jobDivaSession.getTeamId() };
							//
							List<Integer> list = jdbcTemplate.query(sql, params, new RowMapper<Integer>() {
								
								@Override
								public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
									return rs.getInt("CNT");
								}
							});
							//
							Boolean insertMode = list == null || (list.size() > 0 && list.get(0) <= 0);
							if (insertMode) {
								jobUserFieldsDao.addJobUserFieldsDao(jobid, userfield.getUserfieldId(), teamid, job.getDateIssued(), userfield.getUserfieldValue());
							} else {
								jobUserFieldsDao.updateJobUserFieldsDao(jobid, userfield.getUserfieldId(), teamid, job.getDateIssued(), userfield.getUserfieldValue(), new Date());
							}
						}
					}
				}
			}
			job.setSyncRequired(true);
			//
			if (divisionid != null) {
				//
				String sql = "SELECT ID FROM TDIVISION WHERE ID = ? AND TEAMID = ? ";
				Object[] params = new Object[] { divisionid, teamid };
				//
				//
				List<Long> divisons = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong("ID");
					}
				});
				if (divisons == null || divisons.size() == 0) {
					job.setDivisionId(null);
				} else
					job.setDivisionId(divisionid);
			}
			//
			job.setDateLastUpdated(new Date());
			//
			updateJob(jobDivaSession, job);
			//
			return true;
			//
		} catch (Exception e) {
			logger.info("Error: Job " + jobid + " Exception ::  " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public List<Activity> getJobActivities(JobDivaSession jobDivaSession, Long jobId) throws Exception {
		//
		String sql = "select * from ( " + "select b.id, b.candidateid, (select c.firstname||' '||c.lastname from tcandidate c where b.candidateid=c.id and c.teamid=b.recruiter_teamid), "
				+ "b.customerid, b.managerfirstname||' '||b.managerlastname, b.dateinterview, " + "b.primarysalesid, (select d.firstname||' '||d.lastname from trecruiter d where d.id=b.primarysalesid and d.groupid=b.recruiter_teamid), "
				+ "b.notes, b.recruiterid, (select e.firstname||' '||e.lastname from trecruiter e where e.id=b.recruiterid and e.groupid=b.recruiter_teamid), " + "b.daterejected, b.extdaterejected, b.datehired, b.datepresented, b.ROLEID "
				+ "from tinterviewschedule b where b.rfqid=? and b.recruiter_teamid=? " + " ) where rownum<=200 ";
		//
		Object[] params = new Object[] { jobId, jobDivaSession.getTeamId() };
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Activity> activities = jdbcTemplate.query(sql, params, new RowMapper<Activity>() {
			
			@Override
			public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
				Activity act = new Activity();
				//
				act.setId(rs.getLong(1));
				act.setCandidateId(rs.getLong(2));
				act.setCandidateName(rs.getString(3));
				act.setCustomerId(rs.getLong(4));
				act.setManagerName(rs.getString(5));
				act.setDateInterview(rs.getDate(6));
				act.setJobContactId(rs.getLong(7));
				act.setJobContactName(rs.getString(8));
				act.setNotes(rs.getString(9));
				act.setRecruiterId(rs.getLong(10));
				act.setRecruiterName(rs.getString(11));
				act.setDateRejected((rs.getDate(13) == null) ? rs.getDate(12) : rs.getDate(13));
				act.setDateHired(rs.getDate(14));
				act.setDatePresented(rs.getDate(15));
				act.setIsInternal(rs.getLong(16) > 990);
				//
				return act;
			}
		});
		return activities;
	}
	
	public List<String> getJobPriority(JobDivaSession jobDivaSession, Long teamId) {
		String sql = "Select Name " + "from tjob_priority where teamid=?";
		Object[] params = new Object[] { teamId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> priority = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("Name");
			}
		});
		return priority;
	}
	
	public Boolean updateJobPriority(JobDivaSession jobDivaSession, Integer priority, Long jobId, String priorityName) throws Exception {
		String sql = "update trfq set jobpriority=?, datelastupdated=?, sync_required=4 where teamid=? and id=?";
		Object[] params = new Object[] { priority, new Timestamp(new Date().getTime()), jobDivaSession.getTeamId(), jobId };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jobNoteDao.addJobNote(jobDivaSession, jobId, 5, jobDivaSession.getRecruiterId(), 0, "The Job Priority was changed to " + priorityName);
		//
		jdbcTemplate.update(sql, params);
		return true;
	}
	
	public List<TeamRole> getUserRoles(JobDivaSession jobDivaSession) throws Exception {
		String sql = "select id, Name, Primary from trecruiter_roles where teamid=? and inactive=0";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<TeamRole> roles = jdbcTemplate.query(sql, params, new RowMapper<TeamRole>() {
			
			@Override
			public TeamRole mapRow(ResultSet rs, int rowNum) throws SQLException {
				TeamRole role = new TeamRole();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("Name"));
				role.setPrimary(rs.getString("Primary"));
				return role;
			}
		});
		return roles;
	}
	
	public List<JobStatus> getJobStatus(JobDivaSession jobDivaSession) {
		String sql = "select id, name, color from trfq_statuses where (id<100 and teamid=0) or (id>=100 and teamid=?) order by id";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<JobStatus> status = jdbcTemplate.query(sql, params, new RowMapper<JobStatus>() {
			
			@Override
			public JobStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
				JobStatus jobStatus = new JobStatus();
				jobStatus.setId(rs.getLong("id"));
				jobStatus.setName(rs.getString("name"));
				jobStatus.setColor(rs.getString("color"));
				return jobStatus;
			}
		});
		return status;
	}

	public Boolean updateJobStatus(JobDivaSession jobDivaSession, Long jobId, Long userId, String firstName,String lastName, Integer oldStatus, Integer jobStatus, String oldStatusName, String jobStatusName) throws Exception {
		
		String sql="update trfq set jobstatus=?, datelastupdated=?, sync_required=4 where teamid=? and id=?";
		Object[] params = new Object[] { jobStatus,new Timestamp(new Date().getTime()),jobDivaSession.getTeamId(),jobId};
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sql, params);
		//
		sql = "update trecruiterrfq set jobstatus=? where rfqid=?";
		params=new Object[] {jobStatus,jobId};
		jdbcTemplate.update(sql,params);
		//
		sql = "insert into trfq_statuslog (id, rfqid, prev_status, cur_status, modifier, teamid) "
                + " values (rfq_log_seq.nextval, ?,?,?,?,?)";
		params=new Object[] {jobId,oldStatus,jobStatus,userId,jobDivaSession.getTeamId()};
		jdbcTemplate.update(sql,params);
		//
 	    String str = "The Job Status was changed "+(oldStatus>=0?"from "+oldStatusName+" to "+jobStatusName:"");
		jobNoteDao.addJobNote(jobDivaSession, jobId, 5, jobDivaSession.getRecruiterId(), 0, str);
		//
        if(jobStatus ==0)
			updateHotlistActivate(jdbcTemplate, 1, jobId, jobDivaSession.getTeamId());
		else if(oldStatus == 0 )
			updateHotlistActivate(jdbcTemplate, 0, jobId, jobDivaSession.getTeamId());
        
        sendJobstatusNotification(jdbcTemplate, jobDivaSession.getEnvironment().toString(), jobDivaSession.getTeamId(), jobId, jobStatusName, userId, firstName, lastName, oldStatusName);
		
		return true;
	}

	
	private void sendJobstatusNotification(JdbcTemplate jdbcTemplate,String env, Long teamId, Long jobId, String jobStatusName, Long userId,String firstName, String lastName, String oldStatusName) {
		//get Job Information
    	String companyname="", jobtitle="", refno="";
    	String sql = "SELECT department, rfqtitle, rfqrefno, rfqno_team FROM trfq WHERE teamid = ? AND id = ? ";
    	Object[] params = new Object[] {teamId,jobId};
    	List<List<String>> jobdetails=jdbcTemplate.query(sql, params, new RowMapper<List<String>>() {
			
			@Override
			public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<String> resp=new ArrayList<>();
	 	    	resp.add(deNull(rs.getString(1)));
	 	    	resp.add(deNull(rs.getString(2)));
	 	    	resp.add(deNull(rs.getString(3)));
				return resp;
			}
		});
    	if(jobdetails!=null && jobdetails.size()>0) {
    		List<String> res=jobdetails.get(0);
    		companyname=res.get(0);
 	    	jobtitle=res.get(1);
 	    	refno=res.get(2);
    	}
    	
    	final String fcompanyname=companyname;
    	final String fjobtitle=jobtitle;
    	final String frefno=refno;
    	//get the job owners' preferences and mails
 	    sql = "SELECT rec.email, rec.leader, rec.REC_EMAIL_STATUS, recrfq.LEAD_RECRUITER + recrfq.LEAD_SALES role, rec.id as recid "
	    		  + "FROM trecruiter rec, trecruiterrfq recrfq "
	              + "WHERE recrfq.teamid = ? "
	              + "AND recrfq.rfqid = ? "
	              + "AND rec.id = recrfq.recruiterid ";
 	    params = new Object[] {teamId,jobId};
 	    
 	    String emailLocation="http://www"+env+".jobdiva.com";
	    SMTPServer mailserver = new SMTPServer();
	    mailserver.setHost(Application.getSMTPServerLocation());
	    mailserver.setContentType(SMTPServer.CONTENT_TYPE_HTML);
	    
 	    jdbcTemplate.query(sql,params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				    Boolean sendEmail=true;
				    String subject = "Job Status changed by " + firstName+" "+lastName;
			        if (rs.getLong("leader")!=36) sendEmail=false;
			        String Msg = "This is to inform you that the status of Job Reference # <a href='" + emailLocation + "/employers/open_rfq.jsp?rfqid=" + jobId + "'>" + frefno + "</a> (" + fjobtitle+ ")" + fcompanyname +
			              " has just been changed by " + firstName+" "+lastName + " from " + oldStatusName + " to " +jobStatusName;
			        String to = rs.getString(1);
			        String from = "activity@JobDiva.com";
			        int status=rs.getInt(3);
			    	if(status!=1 && status!=2) sendEmail=false;
			    	else if(status == 2 && rs.getInt(4)<1) sendEmail=false;
			    	if(sendEmail)
						try {
							mailserver.sendMail(to, from, "", "", "", subject, Msg, "");
						} catch (Exception e) {
							e.printStackTrace();
						}
			    	return 0l;
			}
 	    });
	}

	private void updateHotlistActivate(JdbcTemplate jdbcTemplate,int status, Long jobId, Long teamId) {
		String sql= "update tworkbench set active=? where teamid=? and rfqid=? ";
		Object[] params = new Object[] { status,teamId,jobId};
		jdbcTemplate.update(sql,params);
	}
}
