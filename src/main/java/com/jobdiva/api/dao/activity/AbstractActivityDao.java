package com.jobdiva.api.dao.activity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.axelon.mail.SMTPServer;
import com.axelon.oc4j.ServletRequestData;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.candidate.CandidateNoteDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.CandidateNote;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.servlet.ServletTransporter;

public class AbstractActivityDao extends AbstractJobDivaDao {
	
	@Autowired
	CandidateNoteDao						candidateNoteDao;
	//
	//
	public static final int					OPEN_JOB_STATUS					= 0;
	public static final int					ACTIVITY_TYPE_CREATE_SUBMITTAL	= 1;
	public static final int					ACTIVITY_TYPE_UPDATE_SUBMITTAL	= 2;
	//
	protected HashMap<Integer, String>		currencyUnitHash				= new HashMap<Integer, String>();
	protected HashMap<Character, String>	payUnitHash						= new HashMap<Character, String>();
	protected HashMap<Character, String>	billUnitHash					= new HashMap<Character, String>();
	
	protected String getRfqServletLocation() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/rfq/servlet/APIAssignCurrentEmployeeServlet";
	}
	
	protected String getUnreachableCandidateServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/UnreachableCandidateServlet";
	}
	
	protected String getSaveUnreachabilityServlet() {
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		return LOADBALANCERSERVLETLOCATION + "/candidate/servlet/SaveUnreachabilityServlet";
	}
	
	protected Integer getRateCurrencyID(Integer defaultCurrencyID, String currName) throws Exception {
		try {
			return getCurrencyCode(currName);
		} catch (Exception e) {
			return defaultCurrencyID;
		}
	}
	
	protected void assignCurrencyRate(JobDivaSession jobDivaSession) {
		String sql = "SELECT ID, NAME FROM TCURRENCY ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				currencyUnitHash.put(rs.getInt("ID"), rs.getString("NAME"));
				//
				return rs.getLong("ID");
			}
		});
		//
		sql = "SELECT *  FROM TRATEUNITS where DELETED <> 1 and TEAMID = ? ";
		Object[] params = new Object[] { jobDivaSession.getTeamId() };
		//
		//
		jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				int rateType = rs.getInt("RATETYPE");
				if (rateType == 0) // ratetype == 0 --> pay unit
					payUnitHash.put(rs.getString("UNITID").toCharArray()[0], rs.getString("NAME"));
				else // ratetype = 1 --> bill unit
					billUnitHash.put(rs.getString("UNITID").toCharArray()[0], rs.getString("NAME"));
				return rs.getLong("RATETYPE");
			}
		});
	}
	
	public String getCurrencyString(Integer currId) {
		if (currencyUnitHash != null && currencyUnitHash.containsKey(currId))
			return currencyUnitHash.get(currId);
		else {
			return "";
		}
	}
	
	public String getRateUnitString(char c, boolean payrate) { // take
																// lower
																// case
																// only
		if (payUnitHash != null && billUnitHash != null) {
			switch (c) {
				case 'h':
					return "Hour";
				case 'd':
					return "Day";
				case 'y':
					return "Year";
				default: { // user-defined rate units
					if (payrate && payUnitHash.containsKey(c)) {
						return payUnitHash.get(c);
					} else if (billUnitHash.containsKey(c)) {
						return billUnitHash.get(c);
					} else
						return "";
				}
			}
		}
		return "";
	}
	
	public String getPhoneType(char id) {
		switch (id) {
			case '0':
				return "W";
			case '1':
				return "H";
			case '2':
				return "C";
			case '3':
				return "HF";
			case '4':
				return "WF";
			case '5':
				return "P";
			case '6':
				return "Ma";
			case '7':
				return "D";
			case '8':
				return "O";
			default:
				return "";
		}
	}
	
	public String formatCandidatePhones(String homePhone, String homePhoneExt, String workPhone, String workPhoneExt, String cellPhone, String cellPhoneExt, String fax, String faxExt, String phoneTypes) {
		String candidatePhones = "";
		String type = "";
		if (workPhone != null) {
			candidatePhones += workPhone;
			type = getPhoneType(phoneTypes.charAt(0));
			if (type.equals("W") && workPhoneExt != null)
				candidatePhones += "(" + workPhoneExt + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (homePhone != null) {
			candidatePhones += homePhone;
			type = getPhoneType(phoneTypes.charAt(1));
			if (type.equals("W") && homePhoneExt != null)
				candidatePhones += "(" + homePhoneExt + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (cellPhone != null) {
			candidatePhones += cellPhone;
			type = getPhoneType(phoneTypes.charAt(2));
			if (type.equals("W") && cellPhoneExt != null)
				candidatePhones += "(" + cellPhoneExt + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (fax != null) {
			candidatePhones += fax;
			type = getPhoneType(phoneTypes.charAt(3));
			if (type.equals("W") && faxExt != null)
				candidatePhones += "(" + faxExt + ")";
			candidatePhones += " (" + type + ")|";
		}
		return candidatePhones;
	}
	
	public String formatCandidateAddress(String address1, String address2, String city, String state, String zipCode) {
		String candidateAddress = "";
		if (address1 != null)
			candidateAddress = address1;
		if (address2 != null)
			candidateAddress += " " + address2;
		if (city != null)
			candidateAddress += ", " + city;
		if (state != null)
			candidateAddress += " " + state;
		if (zipCode != null)
			candidateAddress += " " + zipCode;
		return candidateAddress;
	}
	
	protected String getRateUnitCode(HashMap<Character, String> hash, String name) throws Exception {
		if (name == null || name.trim().length() == 0)
			return "";
		if (name.equalsIgnoreCase("Year"))
			return "y";
		else if (name.equalsIgnoreCase("Day"))
			return "d";
		else if (name.equalsIgnoreCase("Hour"))
			return "h";
		else if (hash.size() > 0) {
			for (Character key : hash.keySet()) {
				if (hash.get(key).equalsIgnoreCase(name))
					return key.toString();
			}
		}
		throw new Exception("Error: Rate unit(" + name + ") is not valid. Please enable this unit on 'Manage Rate Units' page.");
	}
	
	protected Integer getCurrencyCode(String name) throws Exception {
		if (name == null || name.trim().length() == 0)
			return 0;
		for (Integer key : currencyUnitHash.keySet()) {
			if (currencyUnitHash.get(key).equals(name))
				return key;
		}
		throw new Exception("Error: Currency(" + name + ") is not valid. Please enable this unit on 'Manage Rate Units' page.");
	}
	
	protected int getContractId(String name) throws Exception {
		if (name != null) {
			name = name.toLowerCase();
			if (name.equals("direct placement"))
				return 1;
			else if (name.equals("contract"))
				return 2;
			else if (name.equals("right to hire"))
				return 3;
			else if (name.equals("full time/contract"))
				return 4;
			else
				throw new Exception("Error: Position type(" + name + ") is invalid. Please choose among 'direct placement', 'contract', " + "'right to hire', 'full time/contract'(case insensitive).");
		}
		return 0;
	}
	
	protected void updateEnddateAction(JobDivaSession jobDivaSession, Date endDate, Long teamid, Long recruiterid, Long candidateid) throws Exception {
		if (endDate == null)
			return; // temp fix! needs to be checked for overwrite == true
		String sql = "SELECT * FROM TCANDIDATE_UNREACHABLE WHERE TEAMID = ?  AND CANDIDATEID = ?  ";
		Object[] params = new Object[] { teamid, candidateid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Boolean> list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
			
			@Override
			public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
				return true;
			}
		});
		Boolean insertMode = list == null || list.size() == 0;
		if (insertMode) {
			String sqlInsert = "INSERT INTO  TCANDIDATE_UNREACHABLE (TEAMID, CANDIDATEID, DATEAVAILABLE, DATE_CREATED) " + "VALUES " + "(?, ?, ?, ?) ";
			params = new Object[] { teamid, candidateid, endDate, new Date() };
			jdbcTemplate.update(sqlInsert, params);
		} else {
			String sqlUpdate = "UPDATE TCANDIDATE_UNREACHABLE SET FLAG = ? , DATEAVAILABLE = ? , DATE_CREATED  = ? WHERE  TEAMID = ?  AND CANDIDATEID = ?  ";
			params = new Object[] { 4, endDate, new Date(), teamid, candidateid };
			jdbcTemplate.update(sqlUpdate, params);
		}
		//
		//
		// update TCANDIDATEDOCUMENT_HEADER
		// logger.debug("update resumes...");
		String sqlUpdate = "UPDATE TCANDIDATEDOCUMENT_HEADER SET DATECREATED = ? WHERE CANDIDATEID = ? AND TEAMID = ?";
		params = new Object[] { endDate, candidateid, teamid };
		jdbcTemplate.update(sqlUpdate, params);
		//
		CandidateNote note = new CandidateNote();
		note.setCandidateId(candidateid);
		note.setType(0L);
		note.setRecruiterId(recruiterid);
		note.setRfqId(0L);
		note.setDateCreated(new Date());
		note.setTeamId(teamid);
		note.setRecruiterTeamId(teamid);
		note.setAuto(3);
		note.setShared(false);
		String[] date = simpleDateFormat.format(endDate).split(" ");
		Timestamp currentdate = new Timestamp(System.currentTimeMillis());
		String notestr = "";
		if (endDate.before(currentdate))
			notestr = "Marked candidate available as of " + date[0] + "\r\n Reason: Assignment End Date \r\n";
		else
			notestr = "Marked candidate unavailable until " + date[0] + "\r\n Reason: Assignment End Date \r\n";
		note.setNote(notestr);
		note.setDimDateCreate(new Date());
		//
		//
		Long noteid = candidateNoteDao.saveCandidateNote(jobDivaSession, note);
		note.setNoteId(noteid);
	}
	
	protected void assignCurrentEmployee(String url, Activity act, int contract) throws Exception {
		Object[] objs = new Object[5];
		objs[0] = act.getRecruiterTeamId(); // teamid
		objs[1] = act.getCandidateId(); // candidateid
		objs[2] = act.getJobId();// rfqid
		//
		String sql = "SELECT COMPANY FROM TTEAM WHERE ID = ? ";
		Object[] params = new Object[] { act.getRecruiterTeamId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("COMPANY");
			}
		});
		if (list == null || list.size() == 0)
			throw new Exception("Error: Invalid teamid(" + act.getRecruiterTeamId() + ").");
		objs[3] = list.get(0); // TEAM_NAME(string)
		objs[4] = contract; // interview.contract (integer)
		Object reqData = new ServletRequestData(0, null, objs);
		try {
			ServletTransporter.callServlet(url, reqData);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		;
	}
	
	public boolean syncCalendar(long teamid, long act_id, long session_id, int env, String username, String password) throws Exception {
		boolean success = true;
		long eventid = 0;
		eventid = lookupEventID(teamid, act_id, "H");
		//
		if (eventid <= 0)
			return success;
		String LOADBALANCERSERVLETLOCATION = appProperties.getLoadBalanceServletLocation();
		String servlet_url = LOADBALANCERSERVLETLOCATION + "/sync/servlet/CalendarSyncManager";
		Object[] obj_arr = new Object[2];
		obj_arr[0] = "create/update event";
		obj_arr[1] = new Long(eventid);
		String env_type = "PRODUCTION";
		if (env == 2)
			env_type += "_2";
		else if (!appProperties.getJobDivaEnvironmentProduction())
			env_type = "INTEGRATION";
		//
		Object reqData = new ServletRequestData(session_id, env_type, obj_arr);
		//
		Object rspData = ServletTransporter.callServlet(servlet_url, reqData);
		if (rspData != null && rspData instanceof String) {
			//
			if (((String) rspData).toLowerCase().indexOf("exception") > -1)
				success = false;
		} else {
		}
		//
		return success;
	}
	
	public long lookupEventID(long teamid, long activity_id, String date_type) {
		long event_id = 0;
		String sql = "select id "//
				+ " from TEVENT "//
				+ " where TEAMID = :teamid " //
				+ " AND ACTIVITYID = :activity_id "//
				+ " AND DATETYPE = :date_type";
		Object[] params = new Object[] { teamid, activity_id, date_type };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<BigDecimal> list = jdbcTemplate.query(sql, params, new RowMapper<BigDecimal>() {
			
			@Override
			public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBigDecimal("ID");
			}
		});
		if (list != null && list.size() > 0) {
			if (list.get(0) != null && list.get(0).longValue() > 0)
				event_id = list.get(0).longValue();
		}
		return event_id;
	}
	
	public boolean updateLastEmployee(JobDivaSession jobDivaSession, Activity activity) throws Exception {
		boolean update = false;
		long candidate_teamid = activity.getCandidateTeamId();
		int lastemployment_updated = 0;
		//
		// clear LASTEMPLOYMENT flag
		String sqlUpdate = "UPDATE TINTERVIEWSCHEDULE SET " //
				+ " LASTEMPLOYMENT = 0 " //
				+ " WHERE RECRUITER_TEAMID = ? " //
				+ " AND CANDIDATEID = ? " //
				+ " AND LASTEMPLOYMENT = 1";
		Object[] params = new Object[] { activity.getRecruiterTeamId(), activity.getCandidateId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, params);
		//
		//
		// special case
		if (candidate_teamid == 219 || candidate_teamid == 22 || candidate_teamid == 161) {
			sqlUpdate = "UPDATE TINTERVIEWSCHEDULE " //
					+ " set LASTEMPLOYMENT = 1 " //
					+ " where RECRUITER_TEAMID = ? " //
					+ " AND CANDIDATEID = ? "//
					+ " AND DATEHIRED is not null " //
					+ "  AND DATETERMINATED is null " //
					+ " AND (REASONTERMINATED is null or REASONTERMINATED = 0)";
			//
			if (candidate_teamid == 22) {
				sqlUpdate += "and RFQID in (select ID from TRFQ where TEAMID = ? and DIVISIONID = 3461 )";
				params = new Object[] { activity.getRecruiterTeamId(), activity.getCandidateId(), candidate_teamid };
			} else {
				params = new Object[] { activity.getRecruiterTeamId(), activity.getCandidateId() };
			}
			jdbcTemplate.update(sqlUpdate, params);
			//
			//
			String sql = "select LASTEMPLOYMENT " //
					+ " from TINTERVIEWSCHEDULE " //
					+ " where RECRUITER_TEAMID = :RECRUITER_TEAMID " //
					+ " AND CANDIDATEID = :CANDIDATEID " //
					+ " and lastemployment = 1 ";
			params = new Object[] { activity.getRecruiterTeamId(), activity.getCandidateId() };
			List<Boolean> list = jdbcTemplate.query(sql, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getBoolean("LASTEMPLOYMENT");
				}
			});
			//
			if (list != null && list.size() > 0) {
				lastemployment_updated = 1;
			}
		}
		// set LASTEMPLOYMENT flag for the latest employment
		if (lastemployment_updated == 0) {
			String sql = "select ID "//
					+ " from TINTERVIEWSCHEDULE "//
					+ " where RECRUITER_TEAMID = ? " //
					+ " AND CANDIDATEID = ?  "//
					+ " AND DATEHIRED is not null "//
					+ " order by DATEHIRED desc, ID desc ";
			//
			params = new Object[] { activity.getRecruiterTeamId(), activity.getCandidateId() };
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong("ID");
				}
			});
			//
			long lastemployied = 0L;
			if (list != null && list.size() > 0)
				lastemployied = list.get(0);
			//
			sqlUpdate = "update TINTERVIEWSCHEDULE " //
					+ " set LASTEMPLOYMENT = 1 " //
					+ " where ID = ? and RECRUITER_TEAMID = ? ";
			params = new Object[] { lastemployied, activity.getRecruiterTeamId() };
			jdbcTemplate.update(sqlUpdate, params);
			//
			update = true;
		}
		return update;
	}
	
	public void sendActivityEmailToUsers(JobDivaSession jobDivaSession, Long candidateTeamId, Long activityId, Long rfqId, Long candidateId, Long recruiterId, String submittalStatus, String note, int activityType) {
		//
		Integer env = jobDivaSession.getEnvironment();
		logger.debug("begins...");
		ArrayList<String> userEmails = new ArrayList<String>();
		Long teamId = candidateTeamId;
		String jobRefNo = null;
		String jobTitle = null;
		String candidateName = null;
		String recruiterName = null;
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		try {
			if (activityType == ACTIVITY_TYPE_CREATE_SUBMITTAL) {
				// get basic job info. etc.
				String sql = "SELECT i.rfqid, j.rfqrefno, j.rfqtitle, " //
						+ "	(SELECT firstname||' '||lastname cn FROM tcandidate x WHERE x.id = i.candidateid AND x.teamid = i.recruiter_teamid) as candidateName , "//
						+ "	(SELECT firstname||' '||lastname rn FROM trecruiter x WHERE x.id = ? and x.groupid = ?) as recruiterName " //
						+ "FROM tinterviewschedule i, trfq j " //
						+ "WHERE i.id = ? AND i.recruiter_teamid = ? AND j.id = i.rfqid ";
				//
				Object[] params = new Object[] { recruiterId, teamId, activityId, teamId };
				List<String[]> list = jdbcTemplate.query(sql, params, new RowMapper<String[]>() {
					
					@Override
					public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
						String jobRefNo = rs.getString("rfqrefno");
						String jobTitle = rs.getString("rfqtitle");
						String candidateName = rs.getString("candidateName");
						String recruiterName = rs.getString("recruiterName");
						//
						return new String[] { jobRefNo, jobTitle, candidateName, recruiterName };
					}
				});
				//
				if (list != null && list.size() > 0) {
					jobRefNo = list.get(0)[0];
					jobTitle = list.get(0)[1];
					candidateName = list.get(0)[2];
					recruiterName = list.get(0)[3];
				}
				// find email address for whom on this job and allow receiving
				// interview email.
				sql = "SELECT r.email, r.REC_EMAIL_INTERVIEW, rj.lead_recruiter "//
						+ "	   FROM tinterviewschedule i, trecruiter r, trecruiterrfq rj " //
						+ "	   WHERE i.id = ? and i.recruiter_teamid = ? AND i.rfqid = rj.rfqid AND rj.recruiterid = r.id AND r.active = 1";
				//
				params = new Object[] { activityId, teamId };
				jdbcTemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						String recruiter_email = rs.getString("email");
						Integer rec_email_interview = rs.getInt("REC_EMAIL_INTERVIEW");
						Integer lead_recruiter = rs.getInt("lead_recruiter");
						if (rec_email_interview == 1 || (rec_email_interview == 2 && lead_recruiter == 1)) {
							userEmails.add(recruiter_email);
						}
						//
						return null;
					}
				});
			} else if (activityType == ACTIVITY_TYPE_UPDATE_SUBMITTAL) {
				// get basic job info. etc.
				String sql = " SELECT i.rfqid, j.rfqrefno, j.rfqtitle, " //
						+ "	(SELECT firstname||' '||lastname cn FROM tcandidate tc WHERE tc.id = i.candidateid AND tc.teamid = i.recruiter_teamid) as jName " //
						+ " FROM tinterviewschedule i, trfq j " + " WHERE i.id = ? AND i.recruiter_teamid = ? AND j.id = i.rfqid ";
				Object[] params = new Object[] { activityId, teamId };
				List<String[]> list = jdbcTemplate.query(sql, params, new RowMapper<String[]>() {
					
					@Override
					public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
						String jobRefNo = rs.getString("rfqrefno");
						String jobTitle = rs.getString("rfqtitle");
						String candidateName = rs.getString("jName");
						//
						String[] result = new String[] { jobRefNo, jobTitle, candidateName };
						return result;
					}
				});
				//
				if (list != null && list.size() > 0) {
					jobRefNo = list.get(0)[0];
					jobTitle = list.get(0)[1];
					candidateName = list.get(0)[2];
				}
				//
				// find email address for whom on this job and allow receiving
				// interview email.
				sql = "SELECT r.email, r.REC_EMAIL_INTERVIEW, rj.lead_recruiter, rj.lead_sales " //
						+ "	   FROM tinterviewschedule i, trecruiter r, trecruiterrfq rj " //
						+ "	   WHERE i.id = ? and i.recruiter_teamid = ? AND i.rfqid = rj.rfqid AND rj.recruiterid = r.id AND r.active = 1";
				params = new Object[] { activityId, teamId };
				jdbcTemplate.query(sql, params, new RowMapper<Long>() {
					
					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						String recruiter_email = rs.getString("email");
						Integer rec_email_interview = rs.getInt("REC_EMAIL_INTERVIEW");
						Integer lead_recruiter = rs.getInt("lead_recruiter");
						Integer lead_sales = rs.getInt("lead_sales");
						if (rec_email_interview == 1 || (rec_email_interview == 2 && ((lead_recruiter | lead_sales) == 1))) {
							userEmails.add(recruiter_email);
						}
						//
						return null;
					}
				});
			}
			//
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (userEmails.size() > 0) {
			try {
				SMTPServer mailserver = new SMTPServer();
				mailserver.setHost(appProperties.getSmtpServerLocation());
				mailserver.setIgnoreSPF(true);
				mailserver.setContentType(SMTPServer.CONTENT_TYPE_HTML);
				String subject = "Job Activity Notice on your job.";
				String msg = "";
				if (activityType == ACTIVITY_TYPE_CREATE_SUBMITTAL) {
					msg = "[Candidate] has just been submitted to Job Reference #: [JobReference] (" + jobTitle + ") by " + recruiterName + ". <br/><br/><small>This acitivity brought to you by JobDiva.</small>";
					msg = msg.replace("[Candidate]", "<a href='http://www" + env + ".jobdiva.com/employers/open_candidate.jsp?canid=" + candidateId + "&InterviewID=" + activityId + "'>" + candidateName + "</a>");
					msg = msg.replace("[JobReference]", "<a href='http://www" + env + ".jobdiva.com/employers/open_rfq.jsp?rfqid=" + rfqId + "'>" + jobRefNo + "</a>");
					subject = jobRefNo == null ? "Candidate submitted on your job" : "Candidate submitted on Job Reference #" + jobRefNo + "(" + jobTitle + ") by " + recruiterName;
				} else if (activityType == ACTIVITY_TYPE_UPDATE_SUBMITTAL) {
					msg = "Submittal for [Candidate] on Job Reference # [JobReference] (" + jobTitle + ") has been Updated. <br/>";
					if (isNotEmpty(submittalStatus))
						msg += "New Submittal Status: " + submittalStatus + "<br/>";
					if (isNotEmpty(note))
						msg += "New Internal Notes: " + note + "<br/>";
					msg += "<br/><br/><small>This acitivity brought to you by JobDiva.</small>";
					msg = msg.replace("[Candidate]", candidateName);
					msg = msg.replace("[JobReference]", jobRefNo != null ? jobRefNo : "");
					subject = jobRefNo != null ? "Submitted on Job Reference #" + jobRefNo + "(" + jobTitle + ")  has been updated" : "Submittal on your job has been updated";
				}
				for (String email : userEmails) {
					mailserver.sendMail(email, "activity@JobDiva.com", subject, msg);
				}
			} catch (Exception e) {
				logger.debug("Execption occurs when sending emails.");
				// e.printStackTrace();
			}
		}
	}
}
