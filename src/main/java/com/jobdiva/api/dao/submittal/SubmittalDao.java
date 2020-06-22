package com.jobdiva.api.dao.submittal;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.candidate.DocumentObjectResume;
import com.jobdiva.api.dao.activity.AbstractActivityDao;
import com.jobdiva.api.dao.activity.ActivityDao;
import com.jobdiva.api.dao.activity.ActivityRowMapper;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.dao.candidate.CandidateRowMapper;
import com.jobdiva.api.dao.contact.ContactDao;
import com.jobdiva.api.dao.job.JobContactRowMapper;
import com.jobdiva.api.dao.job.JobDao;
import com.jobdiva.api.dao.job.JobRowMapper;
import com.jobdiva.api.dao.job.JobUserDao;
import com.jobdiva.api.model.Activity;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.Job;
import com.jobdiva.api.model.JobContact;
import com.jobdiva.api.model.JobUser;
import com.jobdiva.api.model.Submittal;
import com.jobdiva.api.model.Userfield;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class SubmittalDao extends AbstractActivityDao {
	
	@Autowired
	ActivityDao		activityDao;
	//
	@Autowired
	CandidateDao	candidateDao;
	// /
	@Autowired
	JobDao			jobDao;
	//
	@Autowired
	JobUserDao		jobUserDao;
	//
	@Autowired
	ContactDao		contactDao;
	
	public String formatCandidateAddress(Candidate candidate) {
		String candidateAddress = "";
		if (candidate.getAddress1() != null)
			candidateAddress = candidate.getAddress1();
		if (candidate.getAddress2() != null)
			candidateAddress += " " + candidate.getAddress2();
		if (candidate.getCity() != null)
			candidateAddress += ", " + candidate.getCity();
		if (candidate.getState() != null)
			candidateAddress += " " + candidate.getState();
		if (candidate.getZipCode() != null)
			candidateAddress += " " + candidate.getZipCode();
		return candidateAddress;
	}
	
	public String formatCandidatePhones(Candidate candidate, String phoneTypes) {
		String candidatePhones = "";
		String type = "";
		if (candidate.getWorkPhone() != null) {
			candidatePhones += candidate.getWorkPhone();
			type = getPhoneType(phoneTypes.charAt(0));
			if (type.equals("W") && candidate.getWorkphoneExt() != null)
				candidatePhones += "(" + candidate.getWorkphoneExt() + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (candidate.getHomePhone() != null) {
			candidatePhones += candidate.getHomePhone();
			type = getPhoneType(phoneTypes.charAt(1));
			if (type.equals("W") && candidate.getHomephoneExt() != null)
				candidatePhones += "(" + candidate.getHomephoneExt() + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (candidate.getCellPhone() != null) {
			candidatePhones += candidate.getCellPhone();
			type = getPhoneType(phoneTypes.charAt(2));
			if (type.equals("W") && candidate.getCellphoneExt() != null)
				candidatePhones += "(" + candidate.getCellphoneExt() + ")";
			candidatePhones += " (" + type + ")|";
		}
		if (candidate.getFax() != null) {
			candidatePhones += candidate.getFax();
			type = getPhoneType(phoneTypes.charAt(3));
			if (type.equals("W") && candidate.getFaxExt() != null)
				candidatePhones += "(" + candidate.getFaxExt() + ")";
			candidatePhones += " (" + type + ")|";
		}
		return candidatePhones;
	}
	
	private Submittal getActivity(JobDivaSession jobDivaSession, Long submittalid, Long jobid, Long candidateid) throws Exception {
		Activity dbActivity = activityDao.getActivity(jobDivaSession, submittalid);
		if (dbActivity == null)
			throw new Exception("Error: Submittal " + submittalid + " is not found.");
		//
		Candidate candidate = candidateDao.getCandidate(jobDivaSession, dbActivity.getCandidateId());
		//
		Job job = jobDao.getJob(jobDivaSession, dbActivity.getJobId());
		//
		Submittal submittal = new Submittal();
		submittal.setId(dbActivity.getId());
		submittal.setJobId(dbActivity.getJobId());
		submittal.setCandidateId(dbActivity.getCandidateId());
		// get candidate name, email, address and phones
		if (candidate.getFirstName() != null)
			submittal.setCandidateFirstName(candidate.getFirstName());
		//
		if (candidate.getLastName() != null)
			submittal.setCandidateLastName(candidate.getLastName());
		//
		submittal.setCandidateEmail(candidate.getEmail());
		String str = formatCandidateAddress(candidate);
		submittal.setCandidateAddress(str);
		String phoneTypes = null;
		if (candidate.getPhoneTypes() == null)
			phoneTypes = "0123";
		else
			phoneTypes = candidate.getPhoneTypes();
		if (phoneTypes.length() >= 4) {
			str = formatCandidatePhones(candidate, phoneTypes);
			submittal.setCandidatePhones(str);
		}
		submittal.setCustomerId(job.getCustomerId());
		submittal.setDatePresented(dbActivity.getDatePresented());
		submittal.setDateInterview(dbActivity.getDateInterview());
		submittal.setDateHired(dbActivity.getDateHired());
		submittal.setDateEnded(dbActivity.getDateEnded());
		submittal.setContract(dbActivity.getContract());
		submittal.setRecruiterId(dbActivity.getRecruiterId());
		// get recruiter name
		String sql = "SELECT FIRSTNAME, LASTNAME, EMAIL" //
				+ " FROM TRECRUITER " //
				+ " WHERE ID = ? ";
		Object[] params = new Object[] { dbActivity.getRecruiterId() };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				//
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				String str = "";
				if (firstName != null)
					str = firstName;
				if (lastName != null)
					str += " " + lastName;
				submittal.setRecruiterName(str);
				submittal.setRecruiterEmail(rs.getString("EMAIL"));
				return null;
			}
		});
		if (dbActivity.getSubmittalStatus() != null)
			submittal.setSubmittalStatus(dbActivity.getSubmittalStatus());
		submittal.setManagerFirstName(dbActivity.getManagerFirstName());
		submittal.setManagerLastName(dbActivity.getManagerLastName());
		submittal.setNotes(dbActivity.getNotes());
		submittal.setPayHourly(dbActivity.getPayHourly());
		// formated along with FINALBILLRATE_CURRENCY
		String currencyUnit = "";
		String rateUnit = "";
		if (dbActivity.getFinalBillrateCurrency() == null)
			currencyUnit = "USD";
		else
			currencyUnit = getCurrencyString(dbActivity.getFinalBillrateCurrency());
		if (dbActivity.getFinalBillRateUnit() == null)
			rateUnit = "Hour";
		else
			rateUnit = getRateUnitString(dbActivity.getFinalBillRateUnit().toLowerCase().charAt(0), false);
		submittal.setFinalBillRateUnit(currencyUnit + "/" + rateUnit);
		submittal.setHourly(dbActivity.getHourly());
		// formated along with HOURLY_CURRENCY
		if (dbActivity.getHourlyCurrency() == null)
			currencyUnit = "USD";
		else
			currencyUnit = getCurrencyString(dbActivity.getHourlyCurrency());
		if (dbActivity.getPayRateUnits() == null)
			rateUnit = "Hour";
		else
			rateUnit = getRateUnitString(dbActivity.getPayRateUnits().toLowerCase().charAt(0), true);
		submittal.setPayRateUnits(currencyUnit + "/" + rateUnit);
		return submittal;
	}
	
	private List<Job> searchJobs(Long jobid, String joboptionalref, String companyname, long teamid) {
		String sql = "SELECT * " //
				+ " FROM TRFQ "//
				+ " WHERE TEAMID = ? ";
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(teamid);
		//
		if (jobid != null) {
			sql += " AND ID = ?";
			paramList.add(jobid);
		}
		//
		if (isNotEmpty(joboptionalref)) {
			sql += " AND upper(RFQREFNO) like ? ";
			paramList.add(joboptionalref.toUpperCase() + "%");
		}
		//
		if (isNotEmpty(companyname)) {
			sql += " AND upper(DEPARTMENT) like ? ";
			paramList.add(companyname.toUpperCase() + "%");
		}
		//
		sql += " AND ROWNUM <= 201 ";
		//
		sql += " ORDER BY DATEISSUED DESC";
		Object[] params = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// TO LIMIT THE RESULT FOR 201 RECORDS
		List<Job> jobs = jdbcTemplate.query(sql, params, new JobRowMapper());
		//
		//
		ArrayList<Job> openJobs = new ArrayList<Job>();
		for (Job job : jobs) {
			if (job.getJobStatus() == OPEN_JOB_STATUS)
				openJobs.add(job);
		}
		//
		if (!openJobs.isEmpty())
			jobs = openJobs;
		return jobs;
	}
	
	private List<Candidate> searchCandidates(JobDivaSession jobDivaSession, Long candidateid, String candidatefirstname, String candidatelastname, String candidateemail, String candidatephone, String candidatecity, String candidatestate,
			long teamid) {
		String sql = " SELECT * FROM TCANDIDATE " //
				+ " WHERE TEAMID = ? ";
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(teamid);
		if (candidateid != null) {
			sql += " AND ID = ? ";
			paramList.add(candidateid);
		}
		if (isNotEmpty(candidatefirstname)) {
			sql += " AND (nls_upper(FIRSTNAME) like ?  OR nls_upper(FIRSTNAME) like ?)";
			paramList.add(candidatefirstname + "%");
			paramList.add(candidatefirstname.toUpperCase().split(" ")[0] + "%");
		}
		if (isNotEmpty(candidatelastname)) {
			sql += " AND nls_upper(LASTNAME) like ? ";
			paramList.add(candidatelastname.toUpperCase() + "%");
		}
		if (isNotEmpty(candidateemail)) {
			sql += " AND nls_upper(EMAIL) like ? ";
			paramList.add(candidateemail.toUpperCase() + "%");
		}
		if (isNotEmpty(candidatephone)) {
			sql += " AND (HOMEPHONE like ? OR WORKPHONE like ? OR CELLPHONE like ? )";
			paramList.add(candidatephone + "%");
			paramList.add(candidatephone + "%");
			paramList.add(candidatephone + "%");
		}
		if (isNotEmpty(candidatecity)) {
			sql += " AND nls_upper(CITY) like ? ";
			paramList.add(candidatecity.toUpperCase() + "%");
		}
		if (isNotEmpty(candidatestate)) {
			sql += " AND nls_upper(STATE) like ? ";
			paramList.add(candidatestate.toUpperCase() + "%");
		}
		//
		sql += " AND ROWNUM <=  50 ";
		Object[] params = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// TO LIMIT THE RESULT FOR 50 RECORDS
		List<Candidate> candidates = jdbcTemplate.query(sql, params, new CandidateRowMapper());
		//
		// If no candidate found, lookup by legal first and last name
		if (candidates == null || candidates.isEmpty()) {
			// searchSubmittal search by legal name
			sql = "SELECT candidateid " //
					+ " FROM tcandidate_hr "//
					+ " WHERE teamid = ? ";
			paramList = new ArrayList<Object>();
			paramList.add(teamid);
			//
			if (isNotEmpty(candidatefirstname)) {
				sql += " AND upper(legal_firstname) like ? ";
				paramList.add(candidatefirstname.toUpperCase() + "%");
			}
			if (isNotEmpty(candidatelastname)) {
				sql += " AND upper(legal_lastname) like ? ";
				paramList.add(candidatelastname.toUpperCase() + "%");
			}
			//
			params = paramList.toArray();
			//
			List<Long> candidateIds = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("candidateid");
				}
			});
			//
			candidates = new ArrayList<Candidate>();
			//
			for (Long id : candidateIds) {
				Candidate cand = candidateDao.getCandidate(jobDivaSession, id);
				candidates.add(cand);
			}
			//
		}
		return candidates;
	}
	
	public List<Submittal> searchSubmittal(JobDivaSession jobDivaSession, Long submittalid, Long jobid, String joboptionalref, String companyname, //
			Long candidateid, String candidatefirstname, String candidatelastname, String candidateemail, String candidatephone, String candidatecity, String candidatestate) throws Exception {
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		List<Submittal> jdActivities = new ArrayList<Submittal>();
		//
		long teamid = jobDivaSession.getTeamId();
		//
		assignCurrencyRate(jobDivaSession);
		//
		if (submittalid != null && submittalid > 0) {
			//
			Submittal activity = getActivity(jobDivaSession, submittalid, jobid, candidateid);
			jdActivities.add(activity);
			//
		} else {
			// Search Jobs
			List<Job> jobs = searchJobs(jobid, joboptionalref, companyname, teamid);
			//
			/*** Search Candidates ***/
			List<Candidate> candidates = searchCandidates(jobDivaSession, candidateid, candidatefirstname, candidatelastname, candidateemail, candidatephone, candidatecity, candidatestate, teamid);
			//
			// For each job, narrow down candidates by lookup internal
			// submittal and candidate notes; seek to retain recruiter ID
			ArrayList<Candidate> matchedCandidates = new ArrayList<Candidate>();
			//
			HashMap<Long, Long> candidateIdRecruiterIdMap = new HashMap<Long, Long>();
			for (Job job : jobs) {
				long jobId = job.getId();
				// Find internal submittal linking the job and the candidate
				String sql = "SELECT CANDIDATEID, RECRUITERID " //
						+ " FROM TINTERVIEWSCHEDULE "//
						+ " WHERE CANDIDATE_TEAMID = ? "//
						+ " AND RFQID = ? "//
						+ " AND nvl(ROLEID, 0) > 900";
				//
				ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(teamid);
				paramList.add(jobId);
				//
				Set<Long> candidatesWithInternalSubmittals = new HashSet<Long>();
				//
				jdbcTemplate.query(sql, paramList.toArray(), new RowMapper<Void>() {
					
					@Override
					public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						candidateIdRecruiterIdMap.put(rs.getLong("CANDIDATEID"), rs.getLong("RECRUITERID"));
						candidatesWithInternalSubmittals.add(rs.getLong("CANDIDATEID"));
						return null;
					}
				});
				//
				for (Candidate candidate : candidates) {
					if (candidatesWithInternalSubmittals.contains(candidate.getId())) {
						matchedCandidates.add(candidate);
					}
				}
				if (!matchedCandidates.isEmpty())
					continue;
				// Not found in internal submittal. Find in candidate notes
				sql = " SELECT CANDIDATEID, RECRUITERID " //
						+ " FROM TCANDIDATENOTES " //
						+ " WHERE RECRUITER_TEAMID = ? " //
						+ " AND RFQID = :rfqid " //
						+ " AND nvl(DELETED, 0) != 1 " //
						+ " ORDER BY DATECREATED ASC";
				//
				paramList = new ArrayList<Object>();
				paramList.add(teamid);
				paramList.add(jobId);
				//
				Set<Long> candidatesWithNotes = new HashSet<Long>();
				jdbcTemplate.query(sql, paramList.toArray(), new RowMapper<Void>() {
					
					@Override
					public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
						//
						candidateIdRecruiterIdMap.put(rs.getLong("CANDIDATEID"), rs.getLong("RECRUITERID"));
						candidatesWithNotes.add(rs.getLong("CANDIDATEID"));
						return null;
					}
				});
				for (Candidate candidate : candidates) {
					if (candidatesWithNotes.contains(candidate.getId())) {
						matchedCandidates.add(candidate);
					}
				}
			}
			//
			if (!matchedCandidates.isEmpty())
				candidates = matchedCandidates;
			//
			//
			// For each job and each candidate, search external submittals
			for (Job job : jobs) {
				for (Candidate candidate : candidates) {
					String sql = " SELECT a.*, " //
							+ " b.FIRSTNAME, b.LASTNAME, b.EMAIL, b.PHONE_TYPES, b.HOMEPHONE, b.HOMEPHONE_EXT, b.WORKPHONE, b.WORKPHONE_EXT, b.CELLPHONE, b.CELLPHONE_EXT, b.FAX, b.FAX_EXT ," //
							+ " b.ADDRESS1, b.ADDRESS2, b.CITY, b.STATE, b.ZIPCODE, " //
							+ " c.FIRSTNAME as RecruiterFIRSTNAME, c.LASTNAME as RecruiterLASTNAME " //
							+ " FROM  TINTERVIEWSCHEDULE a " //
							+ " LEFT JOIN TCANDIDATE b ON b.ID = a.CANDIDATEID " //
							+ " LEFT JOIN TRECRUITER c ON c.ID = a.RECRUITERID " //
							+ " WHERE a.CANDIDATE_TEAMID = ? " //
							+ " AND a.RECRUITER_TEAMID = ? " //
							+ " AND a.RFQID = ? " //
							+ " and a.CANDIDATEID = ? " //
							+ " AND nvl(a.ROLEID, 0) < 900";
					//
					ArrayList<Object> paramList = new ArrayList<Object>();
					paramList.add(teamid);
					paramList.add(teamid);
					paramList.add(job.getId());
					paramList.add(candidate.getId());
					//
					List<Activity> activities = jdbcTemplate.query(sql, paramList.toArray(), new ActivityRowMapper(this));
					if (activities != null && !activities.isEmpty()) {
						//
						for (Activity activity : activities) {
							Submittal jda = new Submittal();
							jda.setId(activity.getId());
							jda.setJobId(activity.getJobId());
							jda.setCandidateId(activity.getCandidateId());
							// get candidate name, email, address and phones
							if (candidate.getFirstName() != null)
								jda.setCandidateFirstName(candidate.getFirstName());
							if (candidate.getLastName() != null)
								jda.setCandidateLastName(candidate.getLastName());
							jda.setCandidateEmail(candidate.getEmail());
							String str = formatCandidateAddress(candidate);
							jda.setCandidateAddress(str);
							String phoneTypes = null;
							if (candidate.getPhoneTypes() == null)
								phoneTypes = "0123";
							else
								phoneTypes = candidate.getPhoneTypes();
							if (phoneTypes.length() >= 4) {
								str = formatCandidatePhones(candidate, phoneTypes);
								jda.setCandidatePhones(str);
							}
							jda.setCustomerId(job.getCustomerId());
							jda.setDatePresented(activity.getDatePresented());
							//
							jda.setDateInterview(activity.getDateInterview());
							//
							jda.setDateHired(activity.getDateHired());
							jda.setDateEnded(activity.getDateEnded());
							//
							jda.setContract(activity.getContract());
							jda.setRecruiterId(activity.getRecruiterId());
							// get recruiter name
							sql = "SELECT FIRSTNAME, LASTNAME, EMAIL" //
									+ " FROM TRECRUITER " //
									+ " WHERE ID = ? ";
							Object[] params = new Object[] { activity.getRecruiterId() };
							//
							jdbcTemplate.query(sql, params, new RowMapper<Long>() {
								
								@Override
								public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
									//
									String firstName = rs.getString("FIRSTNAME");
									String lastName = rs.getString("LASTNAME");
									String str = "";
									if (firstName != null)
										str = firstName;
									if (lastName != null)
										str += " " + lastName;
									jda.setRecruiterName(str);
									jda.setRecruiterEmail(rs.getString("EMAIL"));
									return null;
								}
							});
							//
							if (activity.getSubmittalStatus() != null)
								jda.setSubmittalStatus(activity.getSubmittalStatus());
							jda.setManagerFirstName(activity.getManagerFirstName());
							jda.setManagerLastName(activity.getManagerLastName());
							jda.setNotes(activity.getNotes());
							jda.setPayHourly(activity.getPayHourly());
							// formated along with FINALBILLRATE_CURRENCY
							String currencyUnit = "";
							String rateUnit = "";
							if (activity.getFinalBillrateCurrency() == null)
								currencyUnit = "USD";
							else
								currencyUnit = getCurrencyString(activity.getFinalBillrateCurrency());
							if (activity.getFinalBillRateUnit() == null)
								rateUnit = "Hour";
							else
								rateUnit = getRateUnitString(activity.getFinalBillRateUnit().toLowerCase().charAt(0), false);
							jda.setFinalBillRateUnit(currencyUnit + "/" + rateUnit);
							jda.setHourly(activity.getHourly());
							// formated along with HOURLY_CURRENCY
							if (activity.getHourlyCurrency() == null)
								currencyUnit = "USD";
							else
								currencyUnit = getCurrencyString(activity.getHourlyCurrency());
							if (activity.getPayRateUnits() == null)
								rateUnit = "Hour";
							else
								rateUnit = getRateUnitString(activity.getPayRateUnits().toLowerCase().charAt(0), true);
							jda.setPayRateUnits(currencyUnit + "/" + rateUnit);
							jdActivities.add(jda);
						}
					}
					if (!jdActivities.isEmpty())
						break;
				}
				if (!jdActivities.isEmpty())
					break;
			}
			//
			//
			//
			if (jdActivities.isEmpty()) {
				/*
				 * No submittal found. May be: 1. 1 job, 1 candidate (for
				 * creation) 2. Multiple jobs, 1 candidate (create on newest
				 * job) 3. Other cases, invalid search. Return message
				 */
				if (jobs.size() >= 1 && candidates.size() == 1) {
					// 1 job, 1 candidate (for creation)
					Submittal jda = new Submittal();
					Job job = jobs.get(0);
					Candidate candidate = candidates.get(0);
					long candidateId = candidate.getId();
					jda.setJobId(job.getId());
					jda.setCandidateId(candidateId);
					jda.setCustomerId(job.getCustomerId()); // submit2id
					//
					List<JobUser> jobUsers = jobUserDao.getJobUsers(job.getId(), jobDivaSession.getTeamId());
					if (jobUsers != null) {
						for (JobUser jobUser : jobUsers) {
							//
							if (jobUser.getLeadRecruiter() != null && jobUser.getLeadRecruiter()) {
								jda.setRecruiterId(jobUser.getRecruiterId());
								jda.setRecruiterEmail(jobUser.getEmail());
							}
							if (jobUser.getLeadSales() != null && jobUser.getLeadSales()) {
								jda.setPrimarySalesId(jobUser.getRecruiterId());
							}
						}
					}
					if (candidateIdRecruiterIdMap.containsKey(candidateId)) {
						// Find primary recruiter ID from job
						jda.setRecruiterId(candidateIdRecruiterIdMap.get(candidateId));
						// Query recruiter email by recruiter ID (in param
						// jdactivity)
						// sec1 = System.currentTimeMillis();
						// UtilMethod.printLog("search recruiter email
						// starts...");
						String sql = "SELECT  EMAIL" //
								+ " FROM TRECRUITER " //
								+ " WHERE ID = ? AND GROUPID = ? ";
						Object[] params = new Object[] { jda.getRecruiterId(), teamid };
						//
						jdbcTemplate.query(sql, params, new RowMapper<Long>() {
							
							@Override
							public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
								//
								jda.setRecruiterEmail(rs.getString("EMAIL"));
								return null;
							}
						});
					}
					//
					if (job.getContract() != null)
						jda.setContract(job.getContract());
					//
					if (candidate.getEmail() != null)
						jda.setCandidateEmail(candidate.getEmail());
					//
					if (candidate.getHomePhone() != null)
						jda.setCandidatePhones(candidate.getHomePhone());
					//
					if (candidate.getCity() != null)
						jda.setCandidateCity(candidate.getCity());
					//
					if (candidate.getState() != null)
						jda.setCandidateState(candidate.getState());
					//
					jdActivities.add(jda);
				} else {
					// Other cases, invalid search. Return message
					// message.append(String.format("Unable to find job(%d) or
					// candidate(%d).", jobs.size(), candidates.size()));
				}
			}
		}
		//
		return jdActivities;
		//
	}
	
	public Long createSubmittal(JobDivaSession jobDivaSession, Long jobid, Long candidateid, String status, Long submit2id, Date submittaldate, String positiontype, //
			Long recruitedbyid, Long primarysalesid, String internalnotes, Double salary, Integer feetype, Double fee, Double quotedbillrate, Double agreedbillrate, //
			Double payrate, String payratecurrency, String payrateunit, Boolean corp2corp, Date agreedon, Userfield[] userfields, //
			String filename, Byte[] filecontent, String textfile) throws Exception {
		//
		// Only one of the below should be set
		if (salary != null && salary.doubleValue() > 0.0) {
			if (isEmpty(positiontype) || getContractId(positiontype) != 1)
				throw new Exception("Please set 'positiontype' to 'Direct Placement' if intend to set 'salary'. " + //
						"('salary', 'fee' & 'feetype' are eligible for 'Direct Placement' only)");
			if (payrate != null && payrate > 0.0)
				throw new Exception("Please do not set both 'salary' and 'payrate' at the same time. " + //
						"('salary', 'fee' & 'feetype' are eligible for 'Direct Placement' only)");
			//
		} else if (isNotEmpty(positiontype) && getContractId(positiontype) == 1 && (payrate != null && payrate > 0)) {
			throw new Exception("Please use positiontype other than 'Direct Placement' (or left empty) if intend to set payrate. " + //
					"('salary', 'fee' & 'feetype' are eligible for 'Direct Placement' only)");
		}
		//
		Long recruiterid = jobDivaSession.getRecruiterId();
		Long teamid = jobDivaSession.getTeamId();
		//
		Job job = jobDao.getJob(jobDivaSession, jobid);
		if (job == null || job.getTeamid() != teamid) {
			throw new Exception("Error: Job " + jobid + " is not found.");
		}
		//
		Candidate candidate = candidateDao.getCandidate(jobDivaSession, candidateid);
		if (candidate == null || candidate.getTeamId() != teamid) {
			throw new Exception("Error: Candidate " + candidateid + " is not found.");
		}
		//
		//
		loadResume(jobDivaSession, filecontent, textfile);
		//
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// Check contact assigned to the job, and get its roleid
		JobContact jobContact = null;
		if (submit2id != null && submit2id.longValue() > 0) {
			String sql = "SELECT * " //
					+ " FROM TRFQ_CUSTOMERS "//
					+ " WHERE RFQID = ? "//
					+ " AND TEAMID = ? "//
					+ " AND CUSTOMERID = ? ";
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(job.getId());
			paramList.add(teamid);
			paramList.add(submit2id);
			//
			//
			List<JobContact> jobContacts = jdbcTemplate.query(sql, paramList.toArray(), new JobContactRowMapper());
			//
			if (jobContacts == null || jobContacts.isEmpty()) {
				throw new Exception(String.format("Error: Contact(%d) on job(%d) is not found. Please assign this contact to the job first.", submit2id, job.getId()));
			} else {
				jobContact = jobContacts.get(0);
			}
			//
		} else { // no customerid assigned, take the default from the job
					// (showonjob=1)
			String sql = "SELECT * " //
					+ " FROM TRFQ_CUSTOMERS " //
					+ " WHERE RFQID = ? " //
					+ " AND TEAMID = ? " //
					+ " AND SHOWONJOB = 1 ";
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(job.getId());
			paramList.add(teamid);
			//
			List<JobContact> jobContacts = jdbcTemplate.query(sql, paramList.toArray(), new JobContactRowMapper());
			if (jobContacts != null && !jobContacts.isEmpty()) {
				jobContact = jobContacts.get(0);
			}
		}
		//
		Contact contact = null;
		if (submit2id != null && submit2id > 0) {
			contact = contactDao.getContact(jobDivaSession, submit2id);
			if (contact == null || contact.getTeamId() != teamid) {
				throw new Exception("Error: Contact " + submit2id + " is not found.");
			}
		}
		// Data validation: Check if any pre-existing submittals by job,
		// candidate & contact
		String sql = "SELECT * from TINTERVIEWSCHEDULE " //
				+ " where RFQID = ?  " //
				+ " and CANDIDATE_TEAMID = ?  " //
				+ " and CUSTOMERID = ?  " //
				+ " and CANDIDATEID = ? ";
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(job.getId());
		paramList.add(teamid);
		paramList.add(submit2id);
		paramList.add(candidateid);
		//
		//
		List<Activity> activities = jdbcTemplate.query(sql, paramList.toArray(), new ActivityRowMapper(this));
		if (activities != null && !activities.isEmpty()) {
			throw new Exception(String.format("Error: Your team has presented candidate #%d for contact #%d on job #%d, please choose a different candidate or contact.", candidateid, submit2id, job.getId()));
		}
		//
		//
		// Check if there is any internal submittal exist. If does, use this
		// recruiter ID for creation.
		sql = "SELECT * from TINTERVIEWSCHEDULE  " //
				+ " where RFQID = ? " //
				+ " and CANDIDATE_TEAMID = ?  " //
				+ " and CANDIDATEID = ? " //
				+ " and nvl(ROLEID, 0) > 900";
		//
		paramList = new ArrayList<Object>();
		paramList.add(job.getId());
		paramList.add(teamid);
		paramList.add(candidateid);
		//
		//
		activities = jdbcTemplate.query(sql, paramList.toArray(), new ActivityRowMapper(this));
		long internalSubmittalRecruiterId = -1L;
		if (activities != null && activities.size() > 0) {
			internalSubmittalRecruiterId = activities.get(0).getRecruiterId();
		}
		//
		//
		Activity activity = new Activity();
		activity.setJobId(job.getId());
		activity.setCandidateId(candidateid);
		activity.setRecruiterId(jobDivaSession.getTeamId());
		activity.setCandidateTeamId(teamid);
		activity.setRecruiterTeamId(teamid);
		activity.setSubmittalStatus(status);
		activity.setCustomerId(submit2id);
		activity.setDatePresented(submittaldate);
		//
		if (isNotEmpty(positiontype))
			activity.setContract(getContractId(positiontype));
		//
		// Set recruiter id if having one matching internal submittal
		// otherwise use recruiter ID sent in
		if (internalSubmittalRecruiterId > 0)
			activity.setRecruiterId(internalSubmittalRecruiterId);
		else
			activity.setRecruiterId(recruitedbyid);
		//
		if (primarysalesid != null && primarysalesid > 0) {
			activity.setPrimarySalesId(primarysalesid);
		} else {
			sql = "SELECT recruiterid " + " FROM trecruiterrfq " + " WHERE rfqid = ?  " + " AND teamid = ?  " + " AND lead_sales = 1 ";
			//
			Object[] params = new Object[] { job.getId(), teamid };
			//
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					return rs.getLong("recruiterid");
				}
			});
			if (list != null && !list.isEmpty()) {
				activity.setPrimarySalesId(list.get(0));
			} else
				activity.setPrimarySalesId(recruitedbyid);
		}
		//
		//
		// Setting fields
		Date currentDate = new Date();
		if (isNotEmpty(internalnotes))
			activity.setNotes(internalnotes);
		//
		// Setting Bill/Pay rates
		//
		if (activity.getContract() != null && activity.getContract().intValue() == 1) {
			if (salary != null) {
				//
				activity.setYearly(new BigDecimal(salary));
				//
				if (agreedon != null)
					activity.setYearlyDatemodified(agreedon); //
				else
					activity.setYearlyDatemodified(currentDate);
				//
				activity.setPayRateUnits("  y");
				activity.setHourlyCurrency(1);
				activity.setDailyCurrency(1);
				activity.setYearlyCurrency(1);
			}
			if (fee != null)
				activity.setFee(new BigDecimal(fee));
			//
			if (feetype != null)
				activity.setFeeType(feetype);
			//
		} else {
			//
			assignCurrencyRate(jobDivaSession);
			//
			if (quotedbillrate != null) // quoted bill rate
				activity.setBillHourly(new BigDecimal(quotedbillrate));
			//
			if (agreedbillrate != null) // agreed bill rate
				activity.setPayHourly(new BigDecimal(agreedbillrate));
			//
			if (payrate != null)
				activity.setHourly(new BigDecimal(payrate)); // pay rate
			//
			if (isNotEmpty(payratecurrency))
				activity.setHourlyCurrency(getCurrencyCode(payratecurrency));
			//
			if (isNotEmpty(payrateunit))
				activity.setPayRateUnits(getRateUnitCode(payUnitHash, payrateunit));
		}
		// corp2corp checkbox && agree on date
		if (corp2corp != null && corp2corp && (activity.getContract() == null || activity.getContract().intValue() != 1))
			activity.setHourlyCorporate(corp2corp);
		else
			activity.setHourlyCorporate(false);
		//
		if (agreedon != null)
			activity.setHourlyDateModified(agreedon); //
		else if (activity.getContract() == null || activity.getContract().intValue() != 1)
			activity.setHourlyDateModified(currentDate);
		else
			activity.setHourlyDateModified(new Date());
		//
		// extra fields -- !!!TODO!!!
		activity.setManagerFirstName(contact != null ? contact.getFirstName() : "");
		activity.setManagerLastName(contact != null ? contact.getLastName() : "");
		//
		activity.setRecruiterIdCreator(recruiterid);
		activity.setHourlyRecruiterId(recruiterid);
		//
		if (agreedon == null)
			activity.setHourlyDateModified(new Timestamp(0));
		//
		activity.setDailyRecruiterId(recruiterid);
		//
		if (activity.getDailyDateModified() == null)
			activity.setDailyDateModified(new Timestamp(0));
		//
		activity.setYearlyRecruiterId(recruiterid);
		//
		if (activity.getYearlyDatemodified() == null)
			activity.setYearlyDatemodified(new Timestamp(0));
		//
		activity.setDateCreated(currentDate);
		activity.setDateUpdated(currentDate);
		//
		if (jobContact != null && jobContact.getRoleId() != null)
			activity.setRoleId(jobContact.getRoleId().longValue());
		//
		activity.setPresentedTimeZoneId("America/New_York");
		activity.setOnboardIngassigned(false);
		activity.setRecordType(1);
		activity.setDirty(true);
		//
		Long submittal_id = activityDao.insertOrUpdate(jobDivaSession, activity);
		//
		// Create UDFs
		if (userfields != null) {
			//
			for (Userfield userfield : userfields) {
				if (userfield.getUserfieldId() <= 0)
					throw new Exception("Invalid UDF ID " + userfield.getUserfieldId());
			}
			//
			validateUserFields(jobDivaSession, teamid, userfields, UDF_FIELDFOR_ACTIVITY);
			//
			for (Userfield userfield : userfields) {
				String udf_val = userfield.getUserfieldValue();
				if (isNotEmpty(udf_val)) {
					String sqlInsert = " INSERT INTO TSTARTRECORD_USERFIELDS " //
							+ " (STARTID, USERFIELD_ID, TEAMID, USERFIELD_VALUE, DATECREATED) " //
							+ " VALUES " //
							+ " (?, ?, ?, ? , ?) ";//
					//
					Object[] params = new Object[] { submittal_id, userfield.getUserfieldId(), jobDivaSession.getTeamId(), userfield.getUserfieldValue(), currentDate };
					jdbcTemplate.update(sqlInsert, params);
				}
			}
		}
		//
		sendActivityEmailToUsers(jobDivaSession, jobDivaSession.getTeamId(), jobid, jobid, candidateid, recruiterid, status, activity.getNotes(), ACTIVITY_TYPE_CREATE_SUBMITTAL);
		//
		return submittal_id;
		//
	}
	
	private DocumentObjectResume loadResume(JobDivaSession jobDivaSession, Byte[] filecontent, String textfile) {
		return null;
		//
		/*
		 * //
		 * printLog("Resume Presents. Convert Resume, Robot Parsing Resume, and Upload..."
		 * ); DocumentObjectResume doc = new DocumentObjectResume(1, "",
		 * "Resume Uploaded by JobDivaAPI", null); try { // Convert resume
		 * doc.setDateCreated(System.currentTimeMillis()); doc.ResumeSource =
		 * "999"; doc.ResumeSourceFlag = 3; boolean converterneeded = true;
		 * FileBundle fb = new FileBundle(); if(filecontent != null) {
		 * if(filename.toLowerCase().endsWith(".rtf") ||
		 * filename.toLowerCase().endsWith(".pdf") ||
		 * filename.toLowerCase().endsWith(".doc") ||
		 * filename.toLowerCase().endsWith(".docx")) { //
		 * printLog("resume upload rtf/pdf/doc/docx file"); Object[] sendObj =
		 * new Object[3]; sendObj[0] = new Integer(0); // extract text content
		 * from resume sendObj[1] = filecontent; sendObj[2] =
		 * filename.substring(filename.lastIndexOf(".") + 1); Object[] retObj =
		 * new Object[2]; ServletTransporter transporter_resume = new
		 * ServletTransporter(LOADBALANCERSERVLETLOCATION +
		 * "/candidate/servlet/CandidateResumeConvertServlet"); try { //
		 * printLog("Resume uploaded to converter..."); retObj = (Object[])
		 * transporter_resume.transport(sendObj); //
		 * printLog("Resume converter done..."); converterneeded = ((Boolean)
		 * retObj[0]).booleanValue(); fb.txtFile = (String)retObj[1]; } catch
		 * (Exception e) { e.printStackTrace(); } } if(converterneeded) { String
		 * tempname = new SimpleDateFormat("MM/dd/yyyy").format(new
		 * java.util.Date()) + filename; try { FileDistributor_Stub fd =
		 * (FileDistributor_Stub) Naming.lookup("//" +
		 * Application.getFileDistributorLocation() + "/FileDistributor");
		 * String WSURL = fd.getAvailableWordServerAddress(); RMIWordServer_Stub
		 * converter = (RMIWordServer_Stub) Naming.lookup(WSURL); fb =
		 * converter.convert(filecontent, tempname); } catch (Exception e) {
		 * e.printStackTrace(); } } doc.OriginalFilename = filename;
		 * doc.ZIP_OriginalDocument = Zipper.zipIt(filecontent); doc.PlainText =
		 * fb.txtFile; } else if (textfile != null && textfile.length() > 0) {
		 * if(filename.toLowerCase().endsWith(".txt")) doc.OriginalFilename =
		 * filename; else doc.OriginalFilename = "Resume.txt";
		 * doc.ZIP_OriginalDocument = Zipper.zipIt(textfile); doc.PlainText =
		 * textfile; }
		 * 
		 * // Robot parsing CandidateData canData = null; CandidateData
		 * dbprofile = null; CandidateData canDataFinal = null;
		 * 
		 * ServletTransporter candidateRobotServlet = new
		 * ServletTransporter(LOADBALANCERSERVLETLOCATION +
		 * "/candidate/servlet/CandidateRobot"); String[] args = new String[4];
		 * args[0] = doc.PlainText; args[1] =
		 * String.valueOf(System.currentTimeMillis()); args[2] = "16511"; //
		 * Robot.PARSE_ALL args[3] = String.valueOf(clientid); Object
		 * robotReturnObject = candidateRobotServlet.transport(args); //
		 * printLog("CandidateRobot returned data type: " +
		 * robotReturnObject.getClass().getName()); if(robotReturnObject
		 * instanceof Exception) throw (Exception)robotReturnObject; canData =
		 * (CandidateData) robotReturnObject; doc.rfqtitles =
		 * canData.resume.rfqtitles; canData.resume = doc; canData.teamid =
		 * clientid; canData.password = "password";
		 * 
		 * // Check Candidate Profile ServletTransporter
		 * candidateCheckProfileServlet = new
		 * ServletTransporter(LOADBALANCERSERVLETLOCATION +
		 * "/candidate/servlet/CandidateCheckProfile");
		 * canData.setID(candidateId); canData.action_code = 50; // check
		 * existing candidate by candidate ID and team ID Object
		 * checkProfileReturnedObject =
		 * candidateCheckProfileServlet.transport(canData); dbprofile =
		 * (CandidateData) checkProfileReturnedObject;
		 * 
		 * canDataFinal = new CandidateData(dbprofile.getID());
		 * canDataFinal.action_code = -1; canDataFinal.email = dbprofile.email;
		 * canDataFinal.categories = dbprofile.categories;
		 * canDataFinal.confidential = dbprofile.confidential;
		 * 
		 * canDataFinal.first_name = dbprofile.first_name;
		 * canDataFinal.last_name = dbprofile.last_name; String candidatename =
		 * canDataFinal.first_name + " " + canDataFinal.last_name;
		 * canDataFinal.address1 = dbprofile.address1; canDataFinal.address2 =
		 * dbprofile.address2; canDataFinal.city = dbprofile.city;
		 * canDataFinal.state = dbprofile.state; canDataFinal.zipcode =
		 * dbprofile.zipcode;
		 * canDataFinal.home_phone=getCorrectValue(dbprofile.home_phone,canData.
		 * home_phone); //adopt the former first
		 * canDataFinal.work_phone=getCorrectValue(dbprofile.work_phone,canData.
		 * work_phone);
		 * canDataFinal.cell_phone=getCorrectValue(dbprofile.cell_phone,canData.
		 * cell_phone);
		 * canDataFinal.fax=getCorrectValue(dbprofile.fax,canData.fax);
		 * canDataFinal.preferred_salary_max=dbprofile.preferred_salary_max;
		 * canDataFinal.preferred_salary_min=dbprofile.preferred_salary_min;
		 * canDataFinal.preferred_salary_per=dbprofile.preferred_salary_per;
		 * canDataFinal.current_salary=dbprofile.current_salary;
		 * canDataFinal.current_salary_per=dbprofile.current_salary_per;
		 * canDataFinal.province=dbprofile.province;
		 * canDataFinal.country_id=dbprofile.country_id;
		 * 
		 * // handle work experience & education. Missing WorkExperience &
		 * Education type if ((canData.work_experience!=null) &&
		 * (canData.work_experience.length > 0)) { WorkExperience w_exps[] = new
		 * WorkExperience[canData.work_experience.length]; for (int i=0;
		 * i<canData.work_experience.length; i++) { w_exps[i] = new
		 * WorkExperience(canData.work_experience[i].getText(),
		 * canData.work_experience[i].getTag() ,
		 * canData.work_experience[i].getMonths()); w_exps[i].fromDate =
		 * canData.work_experience[i].fromDate; w_exps[i].toDate =
		 * canData.work_experience[i].toDate; } canDataFinal.work_experience =
		 * w_exps; }
		 * 
		 * if ((canData.education!=null) && (canData.education.length > 0)) {
		 * Education edus[] = new Education[canData.education.length]; for (int
		 * i=0; i<canData.education.length; i++) { edus[i] = new Education
		 * (canData.education[i].degree, canData.education[i].school,
		 * canData.education[i].major, canData.education[i].year); }
		 * canDataFinal.education = edus; }
		 * 
		 * canDataFinal.resume = canData.resume;
		 * canDataFinal.resume.setContent("");
		 * //canDataFinal.resume.ResumeSource = "999";
		 * canDataFinal.resume.ResumeSourceFlag = 3;
		 * canDataFinal.resume.recruiterid = candidateRecruiterId;
		 * canDataFinal.teamid = clientid;
		 * 
		 * // Upload resume ServletTransporter uploadServlet = new
		 * ServletTransporter(LOADBALANCERSERVLETLOCATION +
		 * "/candidate/servlet/CandidateUpload"); Long uploadReturnObject =
		 * (Long)uploadServlet.transport(canData); //
		 * printLog(String.format("Resume Upload Successful. Candidate ID: %d",
		 * uploadReturnObject.longValue())); } catch (Exception e){
		 * e.printStackTrace(); cs_rsp.setStatus("Create Failed");
		 * cs_rsp.setMessage("Upload Resume Exception: "+ e.getMessage());
		 * saveAccessLog(recruiterid, leader, clientid, "createSubmittal",
		 * cs_rsp.localStatus + ", " + "Upload Resume Exception."); return
		 * cs_rsp; }
		 */
	}
	
	public Boolean updateSubmittal(JobDivaSession jobDivaSession, Long submittalid, String status, String internalnotes, Double salary, Integer feetype, Double fee, Double quotedbillrate, Double agreedbillrate, Double payrate, String payratecurrency,
			String payrateunit, Boolean corp2corp, Date agreedon, Date interviewdate, Date internalrejectdate, Date externalrejectdate) throws Exception {
		//
		//
		Activity activity = activityDao.getActivity(jobDivaSession, submittalid);
		if (activity == null)
			throw new Exception("Error: Submittal " + submittalid + " is not found.");
		//
		//
		// String originalNote = null;
		// String replacement = null;
		// String newNote = null;
		//
		// no note paramters based on the document
		// if (jdactivity.localInternalnotesTracker &&
		// jdactivity.getNotes().length() > 0) {
		// replacement = jdactivity.getNotes();
		// originalNote = activity.getNotes();
		// final String NOTES_WRAPPER = "---";
		// if (originalNote == null || originalNote.length() == 0)
		// newNote = NOTES_WRAPPER + replacement + NOTES_WRAPPER;
		// else {
		// // Has old notes; can be either legal format with three
		// // dashes wrapped up or not
		// String[] tokens = originalNote.split(NOTES_WRAPPER, -1);
		// if (tokens.length == 3) {
		// tokens[1] = replacement;
		// newNote = String.join(NOTES_WRAPPER, tokens);
		// } else
		// newNote = originalNote + NOTES_WRAPPER + replacement +
		// NOTES_WRAPPER;
		// }
		// }
		activity.setSubmittalStatus(status);
		// activity.setNotes(newNote);
		Date currentDate = new Date();
		//
		if (activity.getContract() != null && activity.getContract().intValue() == 1) {
			if (salary != null) {
				activity.setYearly(new BigDecimal(salary));
				//
				if (agreedon != null)
					activity.setYearlyDatemodified(agreedon);
				else
					activity.setYearlyDatemodified(currentDate);
			}
			//
			if (feetype != null)
				activity.setFeeType(feetype);
			//
			if (fee != null)
				activity.setFee(new BigDecimal(fee));
			//
		} else {
			//
			assignCurrencyRate(jobDivaSession);
			//
			if (quotedbillrate != null)
				activity.setBillHourly(new BigDecimal(quotedbillrate));
			//
			if (agreedbillrate != null)
				activity.setPayHourly(new BigDecimal(agreedbillrate));
			//
			if (payrate != null)
				activity.setHourly(new BigDecimal(payrate));
			//
			if (isNotEmpty(payratecurrency))
				activity.setHourlyCurrency(getCurrencyCode(payratecurrency));
			//
			if (isNotEmpty(payrateunit))
				activity.setPayRateUnits(getRateUnitCode(payUnitHash, payrateunit));
			//
			if (corp2corp != null)
				activity.setHourlyCorporate(corp2corp);
			//
			if (agreedon != null)
				activity.setHourlyDateModified(agreedon);
			else if (quotedbillrate != null || agreedbillrate != null || payrate != null)
				activity.setYearlyDatemodified(currentDate);
			//
		}
		//
		if (interviewdate != null)
			activity.setDateInterview(interviewdate);
		//
		if (internalrejectdate != null) {
			activity.setDateRejected(internalrejectdate);
			activity.setRejectedBy(jobDivaSession.getRecruiterId());
		}
		//
		if (externalrejectdate != null) {
			activity.setExtDateRejected(externalrejectdate);
			activity.setExtRejectedBy(jobDivaSession.getRecruiterId());
		}
		//
		activityDao.insertOrUpdate(jobDivaSession, activity);
		//
		sendActivityEmailToUsers(jobDivaSession, jobDivaSession.getTeamId(), submittalid, activity.getJobId(), activity.getCandidateId(), activity.getRecruiterId(), status, activity.getNotes(), ACTIVITY_TYPE_UPDATE_SUBMITTAL);
		//
		return true;
	}
}
