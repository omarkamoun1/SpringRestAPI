package com.jobdiva.api.dao.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;

import org.osaf.cosmo.calendar.Instance;
import org.osaf.cosmo.calendar.InstanceList;
import org.osaf.cosmo.calendar.RecurrenceExpander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.axelon.mail.SMTPServer;
import com.axelon.recruiter.RecruiterObject;
import com.axelon.util.JDLocale;
import com.axelon.util.JDTimeZone;
import com.axelon.util.JDUtils;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.candidate.CandidateDao;
import com.jobdiva.api.model.Candidate;
import com.jobdiva.api.model.EventNotification;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.Event;
import com.jobdiva.api.model.authenticate.JobDivaSession;

import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ExDate;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.RecurrenceId;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

@Component
public class EventDao extends AbstractJobDivaDao {
	
	public static final int	MAINAPP			= 1;
	public static final int	PN_MAIN_TASK	= 7;
	//
	@Autowired
	CandidateDao			candidateDao;
	
	private void parametresCheck(String subject, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid) throws Exception {
		StringBuffer message = new StringBuffer();
		//
		if (subject.length() == 0)
			message.append("subject cannot be empty. ");
		//
		if (assignedtoid <= 0)
			message.append("assignedToId is invalid(" + assignedtoid + "); ");
		//
		if (assignedbyid != null && assignedbyid <= 0)
			message.append("assignedById is invalid(" + assignedbyid + "); ");
		//
		if (tasktype != null && tasktype < 0)
			message.append("taskType is invalid(" + tasktype + "); ");
		//
		if (percentagecompleted != null && percentagecompleted < 0)
			message.append("percentageCompleted is invalid(" + percentagecompleted + "); ");
		//
		if (contactid != null && contactid < 0)
			message.append("contactId is invalid(" + contactid + "); ");
		//
		if (candidateid != null && candidateid < 0)
			message.append("candidateId is invalid(" + candidateid + "); ");
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n Error: " + message.toString());
		}
	}
	
	public Long createTask(JobDivaSession jobDivaSession, String subject, Date duedate, Long assignedtoid, Long assignedbyid, Integer tasktype, //
			Integer percentagecompleted, Long contactid, Long candidateid, String description, Boolean isprivate) throws Exception {
		//
		// Parameters Check
		parametresCheck(subject, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid);
		//
		StringBuffer message = new StringBuffer();
		Date eventDate1;
		Date eventDate2;
		long eventId = 0;
		String candidateName = null;
		String result = null;
		//
		//
		eventDate1 = duedate;
		Integer duration = 45;
		//
		eventDate2 = new Date(eventDate1.getTime() + duration * 60 * 1000);
		//
		// Find Candidate Name and generate RESULT column data
		if (candidateid != null && candidateid > 0) {
			Candidate candidate = candidateDao.getCandidate(jobDivaSession, candidateid);
			if (candidate != null) {
				candidateName = candidate.getFirstName() + " " + candidate.getLastName();
				result = "<can>candidateid=" + candidateid + "&docids=-1&teamid=" + jobDivaSession.getTeamId() + "</can><nam>" + candidateName + "</nam>";
			} else {
				message.append("Warning: invalid candidate ID(" + candidateid + "). Ignored. ");
				candidateid = 0L;
			}
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// Find next available Event ID
		String sql = "SELECT eventid.nextval as EVENTID FROM dual";
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("EVENTID");
			}
		});
		//
		if (list != null && list.size() > 0) {
			eventId = list.get(0);
		} else {
			throw new Exception("Fail to find next event ID. ");
		}
		//
		Integer task_completed;
		Integer optional;
		if (percentagecompleted != null) {
			task_completed = percentagecompleted;
			optional = percentagecompleted == 100 ? 4 : 2;
		} else {
			task_completed = 0;
			optional = 2;
		}
		//
		String sqlInsert = "insert into TEVENT "//
				+ " ( id, recruiterid, eventdate, dtend, eventtype, notes, priority, duration, reminder, status, result, title, " //
				+ " teamid, optional, private, leadtime, lagtime, timezone, creatorid, task_type, completed_pct, candidateid, location) " //
				+ " values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?) ";
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(eventId);
		paramList.add(assignedtoid != null ? assignedtoid : jobDivaSession.getRecruiterId());
		paramList.add(eventDate1);
		paramList.add(eventDate2);
		paramList.add(0);
		paramList.add(description);
		paramList.add(0);
		paramList.add(duration);
		paramList.add(0);
		paramList.add(null);
		paramList.add(result);
		paramList.add(subject);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(optional);
		paramList.add(isprivate != null && isprivate ? 1 : 0);
		paramList.add(null);
		paramList.add(null);
		paramList.add(null);
		paramList.add(assignedbyid != null ? assignedbyid : assignedtoid);
		paramList.add(tasktype != null ? tasktype : 0);
		paramList.add(task_completed);
		paramList.add(candidateid);
		paramList.add(null);
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlInsert, parameters);
		//
		if (contactid != null && contactid > 0) {
			sqlInsert = "insert into TEVENT_CONTACTS(eventid, contactid, teamid) values(?,?,?) ";
			parameters = new Object[] { eventId, contactid, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsert, parameters);
		}
		//
		return eventId;
	}
	
	public Long updateTask(JobDivaSession jobDivaSession, Long taskId, String subject, Date duedate, Integer duration, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid,
			String description, Boolean isprivate) throws Exception {
		//
		// Parameters Check
		parametresCheck(subject, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid);
		//
		StringBuffer message = new StringBuffer();
		Date eventDate1;
		Date eventDate2;
		String candidateName = null;
		String result = null;
		//
		//
		eventDate1 = duedate;
		duration = duration == null || duration.equals(0) ? 45 : duration;
		//
		eventDate2 = new Date(eventDate1.getTime() + duration * 60 * 1000);
		//
		// Find Candidate Name and generate RESULT column data
		if (candidateid != null && candidateid > 0) {
			Candidate candidate = candidateDao.getCandidate(jobDivaSession, candidateid);
			if (candidate != null) {
				candidateName = candidate.getFirstName() + " " + candidate.getLastName();
				result = "<can>candidateid=" + candidateid + "&docids=-1&teamid=" + jobDivaSession.getTeamId() + "</can><nam>" + candidateName + "</nam>";
			} else {
				message.append("Warning: invalid candidate ID(" + candidateid + "). Ignored. ");
				candidateid = 0L;
			}
		}
		//
		Integer task_completed;
		Integer optional;
		if (percentagecompleted != null) {
			task_completed = percentagecompleted;
			optional = percentagecompleted == 100 ? 4 : 2;
		} else {
			task_completed = 0;
			optional = 2;
		}
		//
		String sqlUpdate = "update TEVENT SET "//
				+ " recruiterid = ? , eventdate = ? , dtend = ? , eventtype = ? , " //
				+ " notes = ? , priority = ? , duration = ? , " //
				+ " reminder = ? , status = ? ,  title = ? , " //
				+ " optional = ? , private = ? , leadtime = ? , lagtime = ? , " //
				//
				+ " timezone = ?,  task_type = ?, completed_pct = ?,  result = ? , candidateid = ? , location = ? ," //
				//
				+ " visited = 0,  lastmodified = sysdate, taskdateterminated = (case when ? = 100 then sysdate else null end) " //
				+ " WHERE teamid = ? AND id = ? "; //
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		paramList.add(assignedtoid != null ? assignedtoid : jobDivaSession.getRecruiterId());
		paramList.add(eventDate1);
		paramList.add(eventDate2);
		paramList.add(0);
		paramList.add(description);
		paramList.add(0);
		paramList.add(duration);
		paramList.add(0);
		paramList.add(null);
		paramList.add(subject);
		paramList.add(optional);
		paramList.add(isprivate != null && isprivate ? 1 : 0);
		paramList.add(null);
		paramList.add(null);
		paramList.add(null);
		paramList.add(tasktype != null ? tasktype : 0);
		paramList.add(task_completed);
		paramList.add(result);
		paramList.add(candidateid);
		paramList.add(null);
		paramList.add(task_completed);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(taskId);
		//
		Object[] parameters = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlUpdate, parameters);
		//
		String sqldelete = "delete tevent_contacts where eventid = ? and teamid = ? ";
		Object[] params = new Object[] { taskId, jobDivaSession.getTeamId() };
		jdbcTemplate.update(sqldelete, params);
		//
		if (contactid != null && contactid > 0) {
			String sqlInsert = "INSERT INTO  tevent_contacts (EVENTID, CONTACTID, TEAMID) VALUES( ? , ? , ?)  ";
			params = new Object[] { taskId, contactid, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsert, params);
		}
		//
		return taskId;
	}
	
	public Long createEvent(JobDivaSession jobDivaSession, String title, Integer eventType, Integer optional, Date eventdate, Integer priotity, Integer duration, Long reminder, Long status, EventNotification eventNotification, Timezone timezone,
			Long leadtime, Long lagTime, Boolean privateEvent, Boolean participationOptional, String location, String note, Long customerId, List<Long> opportunityIds, List<Long> recruiterids, Boolean sendMail) throws Exception {
		//
		StringBuffer message = new StringBuffer();
		//
		if (title == null || title.length() == 0)
			message.append("title cannot be empty. ");
		//
		if (eventdate == null)
			message.append("Event Date cannot be enpty.");
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n Error: " + message.toString());
		}
		//
		//
		if (duration == null || duration.equals(0)) {
			duration = 45;
		}
		//
		Date eventDate2 = new Date(eventdate.getTime() + duration * 60 * 1000); //
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		// Find next available Event ID
		String sql = "SELECT eventid.nextval as EVENTID FROM dual";
		List<Long> list = jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("EVENTID");
			}
		});
		//
		Long eventId = (list != null && list.size() > 0) ? list.get(0) : new Long(-1);
		if (eventId == 0) {
			throw new Exception("Fail to find next event ID. ");
		}
		//
		String sqlInsert = "insert into tevent(id, recruiterid, eventdate, dtend, eventtype, notes,priority, " //
				+ " duration, reminder, status, result,title," //
				+ " teamid, optional, private, leadtime,lagtime," //
				+ " timezone, creatorid, task_type,completed_pct,candidateid, location, taskdateterminated) "
				//
				+ " values(?,?,?,?,?, ?,?,    ?,?,?,?,?,     ?,?,?,?,?,    ?,?,?,?,?,?, (case when ? = 100 then sysdate else null end)) ";
		//
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(eventId);
		paramList.add(jobDivaSession.getRecruiterId());
		paramList.add(eventdate);
		paramList.add(eventDate2);
		paramList.add(eventType != null ? eventType : 0);
		paramList.add(note);
		paramList.add(priotity);
		//
		//
		paramList.add(duration);
		paramList.add(reminder);
		paramList.add(status != null ? status : 0);
		paramList.add(null);
		paramList.add(title);
		//
		//
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(optional != null ? optional : 0);
		paramList.add(privateEvent);
		paramList.add(leadtime);
		paramList.add(lagTime);
		//
		//
		paramList.add(timezone != null ? timezone.getValue() : null);
		paramList.add(jobDivaSession.getRecruiterId());
		paramList.add(eventType != null ? eventType : 0);
		paramList.add(0);
		paramList.add(null);
		paramList.add(location);
		paramList.add(0);
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlInsert, parameters);
		//
		if (customerId != null && customerId > 0) {
			long contactId = customerId;
			String sqlStr = "insert into tevent_contacts(eventid, contactid, teamid) values(?,?,?) ";
			Object[] params = new Object[] { eventId, contactId, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlStr, params);
		}
		//
		//
		if (opportunityIds != null && opportunityIds.size() > 0) {
			for (int i = 0; i < opportunityIds.size(); i++) {
				long opportunityid = opportunityIds.get(i);
				String sqlStr = "insert into tevent_opportunity(eventid, opportunityid, teamid) values(?,?,?) ";
				Object[] params = new Object[] { eventId, opportunityid, jobDivaSession.getTeamId() };
				jdbcTemplate.update(sqlStr, params);
				//
				sqlStr = "update topportunity set updatedate = sysdate where id = ? and teamid = ? ";
				params = new Object[] { opportunityid, jobDivaSession.getTeamId() };
				jdbcTemplate.update(sqlStr, params);
			}
		}
		//
		//
		if (recruiterids != null && recruiterids.size() > 0) {
			for (int i = 0; i < recruiterids.size(); i++) {
				long userid = recruiterids.get(i);
				String sqlStr = "insert into trecruiterevent(recruiterid, teamid, eventid) values(?,?,?) ";
				Object[] params = new Object[] { userid, jobDivaSession.getTeamId(), eventId };
				jdbcTemplate.update(sqlStr, params);
				//
			}
		}
		//
		//
		if (eventNotification != null && eventNotification.getValue() != null && eventNotification.getValue() > 0) {
			String sqlInsertNotification = "insert into tevent_notifications " //
					+ "(eventid, notify_id, notify_mins, notify_sent, teamid) " //
					+ "values " //
					+ "( " + eventId + " ,  1, " + eventNotification.getValue() + " , 0, " + jobDivaSession.getTeamId() + "  ) ";
			//
			// notify_id =notification id is from 1 to 12.
			// Object paramNotifications = new Object[] { eventId, 1,
			// eventNotification.getValue(), 0, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsertNotification);// , paramNotifications);
		}
		//
		if (sendMail && optional != null) {
			if (optional != null && optional != 2 && optional != 3) {
				//
				RecruiterObject recs[] = getRecruitersForEventEmail(jobDivaSession, jobDivaSession.getTeamId(), recruiterids);
				//
				RecruiterObject owner = new RecruiterObject(jobDivaSession.getRecruiterId(), jobDivaSession.getTeamId());
				owner.first_name = jobDivaSession.getFirstName();
				owner.email = jobDivaSession.getUserName();
				owner.user_options = jobDivaSession.getUserOptions() != null ? jobDivaSession.getUserOptions() : 0;
				owner.region_code = jobDivaSession.getRegionCode();
				//
				sendMailForCreate(jobDivaSession.getTeamId(), eventdate, title, note, duration, timezone, recs, owner);
				//
			}
			// else if (optional == 2 && event_obj.recruiter_id > 0) {
			// if (recruiter_id != event_obj.recruiter_id) {
			// //
			// RecruiterObject recruiter = getRecruiter(jobDivaSession,
			// recruiter_id, false, jobDivaSession.getTeamId());
			// //
			// sendEmailToAssignedRecruiter(jobDivaSession, status, leadtime,
			// eventDate2, title, note, sql, sendMail, lagTime, customerIds,
			// recruiterids, recruiter.first_name + " " + recruiter.last_name);
			// } else {
			// System.out.println("Owner's id equals to assigned user id. No
			// Email sent.");
			// }
			// }
		}
		//
		return eventId;
	}
	
	public RecruiterObject getRecruiter(JobDivaSession jobDivaSession, long recruiterId, boolean HiringManager, long teamid) {
		//
		String sqlStr = "SELECT  a.firstname, a.lastname, a.address1, a.address2, a.city, a.state, a.zipcode, a.province, a.countryid, ";
		sqlStr += "a.homephone, a.workphone, a.cellphone, a.email, a.sysemail, a.privatename, a.privateaddress, a.privatephone, ";
		sqlStr += "a.privateemail, a.password, b.company, a.department, a.groupid, a.altemail, a.rec_email, a.window_preference, ";
		sqlStr += "a.matchingwindow_preference, a.highlighting_preference,  a.rec_email_apply,a.rec_email_apply_match, a.rec_email_merge, a.rec_email_delete, ";
		sqlStr += "a.division, a.rec_linkedemails, a.rec_email_interview, a.rec_email_merge_response, a.em_caninterested_link, ";
		sqlStr += "a.rec_email_report, a.companyscreen_tab, a.contactscreen_tab, a.imap_emailserver, a.imap_emailserver_port, ";
		sqlStr += "a.imap_username, a.imap_password, a.imap_signature,a.rec_email_start,a.rec_email_status, a.REC_EMAIL_REJECT,a.REC_EMAIL_NOTES,";
		sqlStr += "a.fax, a.workphoneext, a.title, a.user_options, a.m5_password, a.costcenter, a.use_exchange,a.timezone,a.leader,a.REGION_CODE,a.rec_email_termination, "
				+ "a.userbranch, a.rec_email_supplier_submittal, a.rec_email_cancelstart, a.rec_email_approver_submittal ";
		sqlStr += "FROM trecruiter a, tteam b WHERE a.id = ? and a.groupid=b.id ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { recruiterId };
		List<RecruiterObject> list = jdbcTemplate.query(sqlStr, params, new RowMapper<RecruiterObject>() {
			
			@Override
			public RecruiterObject mapRow(ResultSet rs, int rowNum) throws SQLException {
				RecruiterObject recObj = new RecruiterObject(recruiterId, rs.getLong(22));
				recObj.first_name = rs.getString(1);
				recObj.last_name = rs.getString(2);
				recObj.address1 = rs.getString(3);
				recObj.address2 = rs.getString(4);
				recObj.city = rs.getString(5);
				// recObj.state =rs.getString(7);
				String temp_str = rs.getString(6);
				if (temp_str == null || temp_str == "")
					recObj.state = null;
				else
					recObj.state = temp_str.substring(0, 2);
				recObj.zipcode = rs.getString(7);
				recObj.province = rs.getString(8);
				temp_str = rs.getString(9);
				if (temp_str == null || temp_str == "")
					recObj.country_id = null;
				else
					recObj.country_id = temp_str.substring(0, 2);
				recObj.home_phone = rs.getString(10);
				recObj.work_phone = rs.getString(11);
				recObj.cell_phone = rs.getString(12);
				recObj.email = rs.getString(13);
				// recObj.sys_email =rs.getString(15); ///CAREFUL IT WILL BE
				// OVERWRITTEN IN NEXT BLOCK
				recObj.private_name = rs.getInt(15);
				recObj.private_address = rs.getInt(16);
				recObj.private_phone = rs.getInt(17);
				recObj.private_email = rs.getInt(18);
				recObj.password = rs.getString(19);
				recObj.Company = rs.getString(20);
				recObj.department = rs.getString(21);
				// long teamid = rs.getLong(22);
				recObj.setGroupId(rs.getLong(22));
				recObj.altemail = rs.getString(23);
				recObj.receiveEmails = rs.getInt(24);
				recObj.window_preference = rs.getInt(25);
				recObj.matchingwindow_preference = rs.getInt(26);
				recObj.highlighting_preference = rs.getInt(27);
				recObj.receiveApplyEmails = rs.getInt(28);
				recObj.receiveApplyMatchEmail = rs.getInt(29);
				recObj.receiveMergeEmails = rs.getInt(30);
				recObj.receiveDeleteEmails = rs.getInt(31);
				recObj.division = rs.getLong(32);
				recObj.receiveLinkedEmails = rs.getInt(33);
				recObj.receiveInterviewEmails = rs.getInt(34);
				recObj.receiveSupplierSubmittalEmails = rs.getInt("rec_email_supplier_submittal");
				recObj.receiveEmailMergeResponseEmails = rs.getInt(35);
				recObj.EmailMergeCanInterestedLink = rs.getInt(36);
				recObj.receiveReportEmails = rs.getInt(37);
				recObj.companyScreen_defaultTab = rs.getInt(38);
				recObj.contactScreen_defaultTab = rs.getInt(39);
				recObj.email_server_ip = rs.getString(40);
				recObj.email_server_port = rs.getString(41);
				recObj.email_server_username = rs.getString(42);
				recObj.email_server_password = rs.getString(43);
				recObj.extra1 = rs.getString(44);
				recObj.receiveStartEmails = rs.getInt(45);
				recObj.receiveJobStatusChangeEmails = rs.getInt(46);
				recObj.receiveRejectEmails = rs.getInt(47);
				recObj.receiveNotesEmail = rs.getInt("REC_EMAIL_NOTES");
				recObj.fax = rs.getString("fax");
				recObj.work_ext = rs.getString("workphoneext");
				recObj.title = rs.getString("title");
				recObj.user_options = rs.getLong("user_options");
				recObj.m5_password = rs.getString("m5_password");
				recObj.cost_center = rs.getLong("costcenter");
				String cost_center_str = recObj.cost_center + "90909" + rs.getLong("userbranch");
				recObj.cost_center = new Long(cost_center_str).longValue();
				recObj.use_exchange = rs.getInt("use_exchange");
				String timeZoneId = deNull(rs.getString("timezone"));
				if (timeZoneId.equals(""))
					recObj.timezone = null;
				else
					recObj.timezone = TimeZone.getTimeZone(timeZoneId);
				recObj.leader = rs.getInt("leader");
				recObj.region_code = rs.getString("REGION_CODE");
				recObj.receiveTerminationEmails = rs.getInt("rec_email_termination");
				recObj.receiveCancelStartEmails = rs.getInt("rec_email_cancelstart");
				recObj.receiveApproverSubmittalEmails = rs.getInt("rec_email_approver_submittal");
				return recObj;
			}
		});
		//
		RecruiterObject recObj = list != null && list.size() > 0 ? list.get(0) : null;
		if (recObj.division > 0) {
			String sql_stmt = "select NAME from tdivision where id=? and teamid =?";
			params = new Object[] { recObj.division, recObj.getGroupId() };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					if (rs.getString(1) != null && rs.getString(1).length() > 0)
						recObj.sys_email = rs.getString(1);
					return true;
				}
			});
		}
		//
		//
		if (HiringManager == true) {
			String sql_stmt = "select id from tcustomer where teamid =? and ifrecruiterthenid=?";
			params = new Object[] { recObj.getGroupId(), recObj.getID() };
			jdbcTemplate.query(sql_stmt, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					recObj.extra2 = rs.getLong(1) + "";
					return true;
				}
			});
		}
		//
		//
		// get old or new divamail setting flag for this user
		sqlStr = " select a.emailserver from tteam_email_settings a, trecruiter_email_settings b where a.teamid=? and b.teamid=a.teamid and a.id = b.teammailsettingid  and b.recruiterid=? ";
		params = new Object[] { teamid, recruiterId };
		List<String> emails = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("emailserver");
			}
		});
		//
		if (emails != null && emails.size() > 0) {
			String email = emails.get(0);
			if (email == null || email.trim().length() == 0) {
				recObj.mailsetting_OldorNew = 1;
			} else {
				recObj.mailsetting_OldorNew = 2;
			}
		} else {
			recObj.mailsetting_OldorNew = 1;
		}
		//
		return recObj;
	}
	
	private RecruiterObject[] getRecruitersForEventEmail(JobDivaSession jobDivaSession, Long teamid, List<Long> recruiterids) throws Exception {
		//
		if (recruiterids != null && recruiterids.size() > 0) {
			String sqlStr = "SELECT firstname, lastname, groupid, email, id,leader " //
					+ "FROM trecruiter WHERE groupid = :teamId and " //
					+ "id in (:ids)";
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			Hashtable<String, Object> params = new Hashtable<String, Object>();
			params.put("teamId", teamid);
			params.put("ids", recruiterids);
			//
			NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			// Object[] params = new Object[] { teamid, recruiterids };
			Vector<RecruiterObject> v_recs = new Vector<RecruiterObject>();
			jdbcTemplateObject.query(sqlStr, params, new RowMapper<Boolean>() {
				
				@Override
				public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
					RecruiterObject tmpRec = new RecruiterObject(rs.getLong("ID"), rs.getLong("GROUPID"));
					tmpRec.first_name = rs.getString("FIRSTNAME");
					tmpRec.last_name = rs.getString("LASTNAME");
					tmpRec.email = rs.getString("EMAIL");
					tmpRec.leader = rs.getInt("leader");
					v_recs.addElement(tmpRec);
					return true;
				}
			});
			RecruiterObject[] recs = null;
			recs = new RecruiterObject[v_recs.size()];
			v_recs.copyInto(recs);
			return recs;
		} else {
			return new RecruiterObject[0];
		}
	}
	
	private static int[] getHoursMins(int duration) {
		int[] rtnHoursMins = { duration / 60, duration % 60 };
		return rtnHoursMins;
	}
	
	// ------------------------------------------------------------------------------
	private String getEventTimeDesc(Date eventDate, Integer duration, JDLocale regionFormat, TimeZone timeZone) {
		//
		String timeDesc = "";
		try {
			timeDesc = regionFormat.outputDate(eventDate);
			if (timeZone != null)
				timeDesc += " (" + JDTimeZone.getTimeZonePartNameById2(timeZone.getID()).trim() + ") ";
			else
				timeDesc += " ";
		} catch (Exception e) {
		}
		if (duration != null && duration != 0) {
			int[] hourMins = getHoursMins(duration);
			timeDesc += "for ";
			if (hourMins[0] != 0 && hourMins[1] != 0)
				timeDesc += hourMins[0] + " hour(s) and " + hourMins[1] + " mins";
			else if (hourMins[0] != 0 && hourMins[1] == 0)
				timeDesc += hourMins[0] + " hour(s)";
			else if (hourMins[0] == 0 && hourMins[1] != 0)
				timeDesc += hourMins[1] + " mins";
		}
		return timeDesc;
	}
	
	private String getEventInfo(Date eventDate, String title, String notes, RecruiterObject[] recs, JDLocale regionFormat, boolean includeLink, String APACHELOCATION, String userName) throws Exception {
		String strInfo = "";
		if (title != null)
			strInfo += "<BR><BR> The event's title is:<BR>\t" + title;
		if (notes != null && notes.length() > 0)
			strInfo += "<BR><BR> Notes on the event:<BR>\t" + JDUtils.simpleHTMLToText2(notes);
		strInfo += "<br><br> Organizer: " + userName;
		if (recs != null && recs.length > 0) {
			strInfo += "<BR><BR> Attendees:";
			for (int j = 0; j < recs.length; j++) {
				strInfo += "<BR>\t " + recs[j].first_name + " " + recs[j].last_name;
			}
		}
		if (includeLink) {
			regionFormat._timeStyle = -1;
			strInfo += "<BR><BR><a href=\"" + APACHELOCATION + "/index.jsp?FOLLOW=employers/myactivities/contactmgr/contactcalendardaily.jsp}sd=" + regionFormat.outputDate(eventDate) + "\">View details on JobDiva calendar</a>";
			regionFormat._timeStyle = DateFormat.SHORT;
		}
		return strInfo;
	}
	
	private void sendMailForCreate(Long teamid, Date eventDate, String title, String note, Integer duration, Timezone timeZoneParam, RecruiterObject[] recs, RecruiterObject owner) throws Exception {
		String userName = JDUtils.denull(owner.first_name) + " " + JDUtils.denull(owner.last_name);
		String userEmail = owner.email;
		long userOptions = owner.user_options;
		String REGION_CODE = owner.region_code;
		String Msg = "";
		java.text.SimpleDateFormat dft = new java.text.SimpleDateFormat("MM_dd_yyyy_hh_mm_ss");
		TimeZone timeZone = null;
		if (timeZoneParam != null && timeZoneParam.getValue().equals("") == false) {
			timeZone = TimeZone.getTimeZone(timeZoneParam.getValue());
			dft.setTimeZone(timeZone);
		}
		JDLocale regionFormat = new JDLocale(REGION_CODE, DateFormat.SHORT, DateFormat.SHORT, timeZone, 2, 0); // MM/dd/yyyy
																												// HH:mm
		String JDFrom = "events@jobdiva.com";
		String to = "";
		String subject = "";
		if (recs != null) {
			try {
				SMTPServer mailserver = new SMTPServer();
				mailserver.setContentType(SMTPServer.CONTENT_TYPE_HTML);
				mailserver.setHost(appProperties.getSmtpServerLocation());
				subject = "Event Scheduled with " + userName;
				for (int i = 0; i < recs.length; i++) {
					Msg = "Dear " + recs[i].first_name + ", <br> An event has been scheduled by " + userName + " with you at " + getEventTimeDesc(eventDate, duration, regionFormat, timeZone);
					Msg += getEventInfo(eventDate, title, note, recs, regionFormat, true, this.getApacheAddress(teamid), userName);
					to = recs[i].email;
					try {
						mailserver.sendMail(to, JDFrom, subject, Msg);
						System.out.print("----> Sent ");
					} catch (Exception e) {
						System.out.print("\nBad Address=" + to.trim());
						e.printStackTrace(System.out);
					}
				}
				if ((userOptions & 256) != 0) {
					try {
						to = userEmail;
						subject = "Event Scheduled";
						Msg = "Dear " + userName + ",<BR>";
						Msg += "Your event at " + getEventTimeDesc(eventDate, duration, regionFormat, timeZone) + " was created";
						Msg += getEventInfo(eventDate, title, note, recs, regionFormat, true, this.getApacheAddress(teamid), userName);
						mailserver.sendMail(to, JDFrom, subject, Msg);
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}
	
	protected void sendEmailToAssignedRecruiter(JobDivaSession jobDivaSession, Long teamId, Long eventId, Date eventDate, String title, String notes, String result, Boolean task_completed, Long recruiter_id, List<Long> customerIds,
			List<Long> recruiterids, String userName) {
		String to = "", customername = "", taskname = "";
		try {
			// Recruiter Email
			String sqlStr = "SELECT firstname, lastname, email,leader, user_options " + "FROM trecruiter WHERE groupid =? and id =? ";
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			Object[] params = new Object[] { teamId, recruiter_id };
			List<String> list = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("email");
				}
			});
			//
			if (list != null && list.size() > 0) {
				to = list.get(0);
			}
			// Contact Name
			if (customerIds != null) {
				sqlStr = "SELECT firstname||' '||lastname as name " + "FROM tcustomer WHERE teamid =? and id =? ";
				params = new Object[] { teamId, customerIds };
				list = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
					
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("name");
					}
				});
				if (list != null && list.size() > 0) {
					customername = list.get(0);
				}
			}
			//
			// Task Type Name
			sqlStr = "SELECT typename " + "FROM ttask_type_candidate WHERE teamid =? and id =? ";
			params = new Object[] { teamId, customerIds };
			list = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("typename");
				}
			});
			if (list != null && list.size() > 0) {
				taskname = list.get(0);
			}
			//
			if (taskname.length() == 0) {
				sqlStr = "SELECT typename " + "FROM ttask_type WHERE teamid =? and id =? ";
				params = new Object[] { teamId, customerIds };
				list = jdbcTemplate.query(sqlStr, params, new RowMapper<String>() {
					
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("typename");
					}
				});
				if (list != null && list.size() > 0) {
					taskname = list.get(0);
				}
				//
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
		}
		//
		//
		//
		String JDFrom = "events@jobdiva.com";
		String subject = "You have just been assigned the Task '[Task Name]' by [User Name]";
		String msg = "This is to inform you that you have just been assigned the Task '[Task Name]' by [User Name]." + "<br/><br/>Due Date: [Due Date]" + "<br/>Task Type: [Task Type]" + "<br/>% Completed: [Completed Percentage]"
				+ "<br/>Contact: [Contact Name]" + "<br/>Candidate: [Candidate Name]<br/>Description: [Description Content] ";
		subject = subject.replaceFirst("\\[Task Name\\]", title == null ? "" : Matcher.quoteReplacement(title));
		subject = subject.replaceFirst("\\[User Name\\]", userName == null ? "" : userName);
		msg = msg.replaceFirst("\\[Task Name\\]", title == null ? "" : Matcher.quoteReplacement(title));
		msg = msg.replaceFirst("\\[User Name\\]", userName == null ? "" : userName);
		msg = msg.replaceFirst("\\[Due Date\\]", eventDate == null ? "" : eventDate.toString());
		msg = msg.replaceFirst("\\[Task Type\\]", taskname);
		msg = msg.replaceFirst("\\[Completed Percentage\\]", task_completed != null && task_completed ? task_completed + "" : "" + "");
		msg = msg.replaceFirst("\\[Contact Name\\]", customername);
		msg = msg.replaceFirst("\\[Candidate Name\\]", result.indexOf("<nam>") + 5 > result.length() ? "" : result.substring(result.indexOf("<nam>") + 5, result.indexOf("</nam>")));
		msg = msg.replaceFirst("\\[Description Content\\]", notes == null ? "" : Matcher.quoteReplacement(notes));
		SMTPServer mailserver = new SMTPServer();
		mailserver.setContentType(SMTPServer.CONTENT_TYPE_HTML);
		mailserver.setHost(appProperties.getSmtpServerLocation());
		//
		try {
			mailserver.sendMail(to, JDFrom, subject, msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			Map<String, String> values = new HashMap<String, String>();
			values.put("op", "task");
			values.put("taskid", eventId + "");
			String stringmap = "op=task,taskid=" + eventId;
			ArrayList<Long> recruiteridArray = new ArrayList<Long>();
			recruiteridArray.add(recruiter_id);
			//
			boolean isUSProduction = appProperties.getApiServerEnvironment().equalsIgnoreCase("us");
			//
			new NotificationThread(isUSProduction, appProperties.getLoadBalanceServletLocation(), //
					"notification_thread1", //
					MAINAPP, //
					PN_MAIN_TASK, //
					"", //
					appProperties.getApiServerEnvironment().equalsIgnoreCase("int"), //
					teamId, //
					recruiteridArray, //
					subject, //
					1, //
					values, //
					null, //
					"TASK ASSIGNED", //
					subject, //
					eventId, //
					stringmap).start();
			//
			//
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace(System.out);
		}
		// ------------------------------------------------------------------------------
	}
	
	public Boolean updateEvent(JobDivaSession jobDivaSession, Long eventId, String title, Date eventDate, Integer duration, Integer eventType, String notes, Integer priority, Long reminder, Long status, Integer optional, Boolean privateEvent,
			Timezone timezone, Long leadtime, Long lagTime, String location, Long customerId, List<Long> recruiterids, EventNotification eventNotification) {
		//
		//
		//
		Long recruiterId = jobDivaSession.getRecruiterId();
		if (duration == null || duration.equals(0)) {
			duration = 45;
		}
		//
		Date dtEnd = new Date(eventDate.getTime() + duration * 60 * 1000); //
		//
		String sqlUpdate = "update tevent set " //
				+ " recruiterid = ?,  eventdate = ?, dtend = ?, eventtype = ?, " + " notes = ?, priority = ?, duration = ?, reminder= ?," //
				+ " status = ?, title = ?, teamid = ?, optional = ?, private  = ?, "//
				+ " leadtime = ? , lagtime = ? , timezone = ? , task_type = ? ," //
				+ " completed_pct = ?, visited = 0, result = ?, "//
				+ " lastmodified = sysdate, candidateid = ?, location = ?, "//
				+ "taskdateterminated = (case when ? = 100 then sysdate else null end) " //
				+ "where id = ? and teamid = ? ";
		//
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(recruiterId);
		paramList.add(eventDate);
		paramList.add(dtEnd);
		paramList.add(eventType != null ? eventType : 0);
		//
		paramList.add(notes);
		paramList.add(priority);
		paramList.add(duration);
		paramList.add(reminder != null ? reminder : 0);
		//
		paramList.add(status);
		paramList.add(title);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(optional != null ? optional : 0);
		paramList.add(privateEvent);
		//
		paramList.add(leadtime);
		paramList.add(lagTime);
		paramList.add(timezone != null ? timezone.getValue() : null);
		paramList.add(eventType != null ? eventType : 0);
		//
		paramList.add(0);
		paramList.add(null);
		//
		paramList.add(null);
		paramList.add(location);
		//
		paramList.add(0);
		//
		paramList.add(eventId);
		paramList.add(jobDivaSession.getTeamId());
		//
		Object[] parameters = paramList.toArray();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sqlUpdate, parameters);
		//
		//
		//
		String sqlDelete = "delete tevent_notifications where eventid = ? and teamid = ? ";
		Object[] params = new Object[] { eventId, jobDivaSession.getTeamId() };
		jdbcTemplate.update(sqlDelete, params);
		//
		//
		if (eventNotification != null && eventNotification.getValue() != null && eventNotification.getValue() > 0) {
			String sqlInsertNotification = "insert into tevent_notifications " //
					+ "(eventid, notify_id, notify_mins, notify_sent, teamid) " //
					+ "values " //
					+ "( " + eventId + " ,  1, " + eventNotification.getValue() + " , 0, " + jobDivaSession.getTeamId() + "  ) ";
			//
			// notify_id =notification id is from 1 to 12.
			// Object paramNotifications = new Object[] { eventId, 1,
			// eventNotification.getValue(), 0, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsertNotification);// , paramNotifications);
		}
		//
		//
		sqlDelete = "delete from tevent_contacts Where eventid = ? AND teamid = ? ";
		params = new Object[] { eventId, jobDivaSession.getTeamId() };
		jdbcTemplate.update(sqlDelete, params);
		//
		//
		if (customerId != null && customerId > 0) {
			long contactId = customerId;
			String sqlInsert = "insert into tevent_contacts(eventid, contactid, teamid) values(?,?,?) ";
			params = new Object[] { eventId, contactId, jobDivaSession.getTeamId() };
			jdbcTemplate.update(sqlInsert, params);
		}
		//
		//
		modifyUsersOnEvent(jobDivaSession, jdbcTemplate, eventId, recruiterids);
		//
		return true;
	}
	
	private void modifyUsersOnEvent(JobDivaSession jobDivaSession, JdbcTemplate jdbcTemplate, Long eventId, List<Long> recruiterids) {
		//
		String sql = "SELECT RECRUITERID FROM TRECRUITEREVENT WHERE EVENTID = ? AND TEAMID = ? AND recruiterId != 0 ";
		Object[] params = new Object[] { eventId, jobDivaSession.getTeamId() };
		//
		List<Long> dbRecruiterList = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("RECRUITERID");
			}
		});
		//
		//
		List<Long> removeRecruiterList = new ArrayList<Long>();
		for (Long existingRecruiterId : dbRecruiterList) {
			if (recruiterids.contains(existingRecruiterId))
				recruiterids.remove(existingRecruiterId);
			else
				removeRecruiterList.add(existingRecruiterId);
		}
		//
		if (recruiterids != null && recruiterids.size() > 0) {
			//
			String sqlBtachInsert = "INSERT INTO trecruiterevent " //
					+ "(eventId,recruiterId,teamId,joinType,responseStatus,sync_displayName,sync_email,isOrganizer) " //
					+ "VALUES (?,?,?,1,0,'','',0)";
			jdbcTemplate.execute(sqlBtachInsert, new PreparedStatementCallback<Integer>() {
				
				@Override
				public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {
					Connection cxn = stmt.getConnection();
					cxn.setAutoCommit(false);
					//
					for (Long recruiterid : recruiterids) {
						//
						stmt.setLong(1, eventId);
						stmt.setLong(2, recruiterid);
						stmt.setLong(3, jobDivaSession.getTeamId());
						stmt.addBatch();
					}
					stmt.executeBatch();
					cxn.setAutoCommit(true);
					return 0;
				}
			});
			//
		}
		//
		//
		if (removeRecruiterList.size() > 0) {
			String sqlBatchDelete = "DELETE FROM trecruiterevent WHERE eventId = ? AND teamId = ? AND recruiterId = ? ";
			jdbcTemplate.execute(sqlBatchDelete, new PreparedStatementCallback<Integer>() {
				
				@Override
				public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {
					Connection cxn = stmt.getConnection();
					cxn.setAutoCommit(false);
					//
					for (Long recruiterid : removeRecruiterList) {
						//
						stmt.setLong(1, eventId);
						stmt.setLong(3, jobDivaSession.getTeamId());
						stmt.setLong(2, recruiterid);
						stmt.addBatch();
					}
					stmt.executeBatch();
					cxn.setAutoCommit(true);
					return 0;
				}
			});
			//
		}
	}
	
	public Boolean test(JobDivaSession jobDivaSession) throws Exception {
		//
		String sql = " insert into test1(a1,a2) values( ?, ?) ";
		Object[] params = new Object[] { "1", "2" };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update(sql, params);
		//
		return true;
		// throw new Exception("dd");
		//
	}

	public List<Event> searchEvents(JobDivaSession jobDivaSession, Long recruiterId, Date eventDate, Date eventEndDate) throws SQLException, Exception {

		String sql = "SELECT e.ID, e.RECRUITERID, e.EVENTDATE, e.EVENTTYPE, e.PRIORITY, e.DURATION, e.REMINDER, e.REMINDER_FROM, e.REMINDER_REPEAT, e.NOTES, e.STATUS, e.RESULT, e.TITLE, "
				+ "e.DATETYPE, e.TEAMID, e.OPTIONAL, e.PRIVATE, e.LEADTIME, e.LAGTIME, e.DATECREATED, e.ACTIVITYID, e.CANDIDATEID, e.LASTMODIFIED, e.WHOLE_DAY, e.rdate, "
				+ "e.rrule,e.exdate,e.exrule, e.FLOATING, e.TRANSP, e.PRIVACY, e.RULE_DTSTART, e.RULE_FREQ, e.RULE_INTERVAL, e.RULE_BYDAY, e.RULE_BYMONTHDAY, e.EV_ID, "
				+ "e.DTEND, e.LOCATION, e.STATUS_NEW, e.EXCHANGE_UID, e.REMINDER_FROM, e.REMINDER_REPEAT, e.REMINDER_LASTSENT,e.TIMEZONE, nvl(e.EVENTTYPE,-1) AS EVENTTYPE2, "
				+ "case when e.recruiterid > 0 then (select to_char(r.FIRSTNAME)||' '||to_char(r.LASTNAME) from trecruiter r where r.id = e.recruiterid) "
				+ "else (select to_char(nvl(re.sync_displayname,re.sync_email))||' (External)' from trecruiterevent re where re.eventid=e.id and re.isorganizer=1 ) "
				+ "end as ownerName, (select typename from TEVENTTYPE et where et.id = e.eventtype and et.teamid = e.teamid) as eventTypeName, (select COUNT(*) from TEVENT_CONTACTS where EVENTID = e.ID) as contactNumber, "
				+ "(select c.FIRSTNAME||' '||c.LASTNAME from tcustomer c where c.id = e.customerid and c.teamid = e.teamid) as contact_name, (select c.FIRSTNAME||' '||c.LASTNAME "
				+ "from tcandidate c where c.id = e.candidateid and c.teamid = e.teamid) as candidate_name, nvl(e.EVENTTYPE,-1) EVENTTYPE, e.source, e.sync_defaultowner, e.globalid, e.seq, e.recur_id "
				+ "FROM tevent e, trecruiterevent a where e.teamid=? and (e.eventdate between ? and ? ) and e.optional in (0,1,3) "
				+ "AND e.status_new = 'ACTIVE' and a.eventid(+)=e.id and a.recruiterid(+)=? and (e.recruiterid=? or a.recruiterid=? and nvl(a.responsestatus,0) <> 2)";

		Object[] params = new Object[] { jobDivaSession.getTeamId(), eventDate, eventEndDate, recruiterId, recruiterId, recruiterId };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<Event> events = new ArrayList<Event>();
		jdbcTemplate.query(sql, params, new RowMapper<Event>() {

			@Override
			public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
				Event event = new Event();
				event.setEventId(rs.getString("ID"));
				event.setSubject("\"" + rs.getString("TITLE") + "\"");
				Calendar tempc = new GregorianCalendar();
				tempc.setTimeInMillis(rs.getTimestamp("EVENTDATE").getTime());
				event.setEventDate(tempc);
				
				if (rs.getDate("DTEND") != null) {
					tempc=new GregorianCalendar();
					tempc.setTimeInMillis(rs.getTimestamp("DTEND").getTime());
					event.setEventEndDate(tempc);
				}
				
				event.setDuration(rs.getInt("DURATION"));
				event.setIsPrivate(rs.getInt("PRIVATE")==1);
				event.setPriority(rs.getInt("PRIORITY"));
				event.setCandidateId(rs.getLong("CANDIDATEID"));
				event.setCandidateName(rs.getString("CANDIDATE_NAME"));
				event.setOwnerName(rs.getString("OWNERNAME"));
				event.setLeadTime(rs.getInt("LEADTIME"));
				event.setLagTime(rs.getInt("LAGTIME"));
				event.setIsWholeDayEvent(rs.getInt("WHOLE_DAY")==1);
				event.setTimezone("\"" + rs.getString("TIMEZONE") + "\"");
				event.setLocation(rs.getString("LOCATION"));
				event.setReminder(rs.getInt("REMINDER_FROM"));
				event.setReminderRepeat(rs.getInt("REMINDER_REPEAT"));
				event.setDescription(rs.getString("NOTES"));
				event.setDateType(rs.getString("DATETYPE"));
				event.setEventType(rs.getInt("EVENTTYPE"));
				event.setEventTypeName(rs.getString("EVENTTYPENAME"));
				event.setContactNumber(rs.getInt("CONTACTNUMBER"));
				event.setrDate(rs.getString("RDATE"));
				event.setrRule(rs.getString("RRULE"));
				event.setExDate(rs.getString("EXDATE"));
				event.setRecruiterId(rs.getString("RECUR_ID"));
				event.setRSeq(rs.getInt("seq"));
				event.setActivityId(rs.getString("globalid"));
				
				boolean isRule = !isEmpty(rs.getString("RDATE")) || !isEmpty(rs.getString("RRULE")) || !isEmpty(rs.getString("RECUR_ID"));
				if(!isRule || event.getRecruiterId()!=null){
					if(rs.getTimestamp("DTEND") != null ){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(rs.getTimestamp("DTEND"));
						event.setEventEndDate(calendar);
					} else if( rs.getInt("DURATION") > -1 ){
						Date endDate = event.getEventDate().getTime(); 
						int DURATION = rs.getInt("DURATION");
						if(DURATION == 0) DURATION = 45;		
						endDate.setTime( endDate.getTime() + DURATION * 60 * 1000 );
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(endDate);
						event.setEventEndDate(calendar);
					}
				}else{
					if( rs.getInt("DURATION") > -1 ){
						Date endDate = event.getEventDate().getTime(); 
						int DURATION = rs.getInt("DURATION");
						if(DURATION == 0) DURATION = 45;		
						endDate.setTime( endDate.getTime() + DURATION * 60 * 1000 );
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(endDate);
						event.setEventEndDate(calendar);
					}
				}
				
				ArrayList<JobUserSimple> attendees = new ArrayList<JobUserSimple>();
				String sql = "select re.RECRUITERID, r.FIRSTNAME, r.LASTNAME from TRECRUITER r, TRECRUITEREVENT re where re.EVENTID = ? and re.RECRUITERID = r.id and re.teamid = ? and r.active=1 ";
                Object[] params = new Object[] { event.getEventId(), jobDivaSession.getTeamId() };
                jdbcTemplate.query(sql, params, new RowMapper<JobUserSimple>() {

        			@Override
        			public JobUserSimple mapRow(ResultSet rs, int rowNum) throws SQLException {
        				JobUserSimple user = new JobUserSimple();
                        user.setRecruiterId(rs.getLong(1));
                        user.setFirstName(rs.getString(2));
                        user.setLastName(rs.getString(3));
                        attendees.add(user);
        				return user;
        			}
        		});
                
                
                long recid = rs.getLong("RECRUITERID");
                try {
					ArrayList<Event> rendered = multiplyRecurrentEvents(event, eventDate, eventEndDate, recid, jobDivaSession.getTeamId(), 
							rs.getInt("PRIVATE") == 1 && recid != recruiterId, TimeZone.getTimeZone(event.getTimezone() == null ? "America/New_York": event.getTimezone()));
					
					for(Event event1 : rendered){
						event1.setUsers(attendees.toArray(new JobUserSimple[attendees.size()]));
						events.add(event1);
	                }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             
				return event;
			}
		});
		return events;
	}
	
	
	public ArrayList<Event> multiplyRecurrentEvents(Event eventRef, Date from, Date to, Long loggedId, Long TeamId, Boolean isPrivate, TimeZone teamTimeZone) throws Exception {
		ArrayList<Event> rendered = new ArrayList<Event>();	
		
		try {
			boolean isRule = !isEmpty(eventRef.getrDate()) || !isEmpty(eventRef.getrRule()) || !isEmpty(eventRef.getRecruiterId());
			if(!isRule){
				rendered.add(eventRef);
				return rendered;
			}
		
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		net.fortuna.ical4j.model.TimeZone timezone = registry.getTimeZone(eventRef.getTimezone() == null? "America/New_York": eventRef.getTimezone());
		
		if(eventRef.getTimezone()==null||eventRef.getTimezone().length()==0){
			throw new Exception("TimeZone must be specified for recurrence event");
		}
		
		from=fnc_toLocalTime(from, TimeZone.getTimeZone(eventRef.getTimezone() == null? "America/New_York": eventRef.getTimezone()));
		//log("multiplyRecurrentEvents", "come here after from before to: "+from );
		to=fnc_toLocalTime(to, TimeZone.getTimeZone(eventRef.getTimezone() == null? "America/New_York": eventRef.getTimezone()));
		//log("multiplyRecurrentEvents", "from:"+from+"  to:"+to);
		java.util.Calendar queryFrom = java.util.Calendar.getInstance();
		queryFrom.setTimeZone(timezone);
		queryFrom.setTime(from);
		queryFrom.set(java.util.Calendar.MILLISECOND, 0);
		java.util.Calendar queryTo = java.util.Calendar.getInstance();
		queryTo.setTimeZone(timezone);
		queryTo.setTime(to);
		queryTo.set(java.util.Calendar.MILLISECOND, 0);
		Hashtable<Date, Event> overrideEvents = new Hashtable<Date, Event>();
		List<Event> masterEventContainer = new ArrayList<Event>();
		
		net.fortuna.ical4j.model.Calendar calendar = getCalendar(eventRef, overrideEvents, masterEventContainer);
		if(masterEventContainer.size()==0) return rendered;
		Event masterEvent = masterEventContainer.get(0);
		InstanceList insList = new RecurrenceExpander().getOcurrences(calendar, getDate(queryFrom), getDate(queryTo), timezone);
		Set<Map.Entry<String, Instance>> set = insList.entrySet();
		for (Map.Entry<String, Instance> entry : set){
			Instance instance = entry.getValue();
			Event repeatedEvent;
			if(overrideEvents.containsKey(instance.getRid())){
				repeatedEvent = (Event) deepClone(overrideEvents.get((java.util.Date)instance.getRid()));
			}else{
				repeatedEvent = (Event) deepClone(masterEvent);			
			}
			
			java.util.Calendar theCalendar = java.util.Calendar.getInstance();
			theCalendar.setTime(fnc_toServerTime(instance.getStart(), TimeZone.getTimeZone(eventRef.getTimezone() == null? "America/New_York": eventRef.getTimezone())));
			repeatedEvent.setEventDate(theCalendar);
			
			theCalendar = java.util.Calendar.getInstance();
			theCalendar.setTime(fnc_toServerTime(instance.getEnd(), TimeZone.getTimeZone(eventRef.getTimezone() == null? "America/New_York": eventRef.getTimezone())));
			repeatedEvent.setEventEndDate(theCalendar);
			rendered.add(repeatedEvent);
		 }
		}catch(Exception ex) { ex.printStackTrace(); }
		return rendered;
	}
	
	public net.fortuna.ical4j.model.Calendar getCalendar(Event event, Hashtable<Date, Event> overrideEvents, List<Event> masterEventContainer) throws SQLException, Exception{
		String strTimeZone = (event.getTimezone() == null || event.getTimezone().equals("")) ? "America/New_York" : event.getTimezone();
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		net.fortuna.ical4j.model.TimeZone timezone = registry.getTimeZone(strTimeZone);
		VTimeZone tz = timezone.getVTimeZone();
        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//JobDiva//Calendar//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);			  
        calendar.getProperties().add(tz.getTimeZoneId());
        boolean isRule = !isEmpty(event.getrDate()) || !isEmpty(event.getrRule()) || !isEmpty(event.getRecruiterId());
        if(!isRule) return calendar;
		
		
        java.util.Calendar dtStart = java.util.Calendar.getInstance();
		dtStart.setTimeZone(timezone);
		dtStart.setTime(event.getEventDate().getTime());			  
		dtStart.set(java.util.Calendar.MILLISECOND, 0);
		java.util.Calendar dtEnd = java.util.Calendar.getInstance();
		dtEnd.setTimeZone(timezone);
		dtEnd.setTime(event.getEventEndDate().getTime());
		dtEnd.set(java.util.Calendar.MILLISECOND, 0);
		
		Uid uid = new Uid(event.getActivityId());
		Event masterEvent = null;
		if(event.getrRule()!=null&&event.getrRule().length()>0){
			masterEvent = event;
			masterEventContainer.add(masterEvent);
			Recur recur = new Recur(event.getrRule());
			RRule rrule = new RRule(recur);
			VEvent rcurEvent = new VEvent();
			rcurEvent.getProperties().add(tz.getTimeZoneId());
			rcurEvent.getProperties().add(rrule);
			rcurEvent.getProperties().add(new DtStart(getDate(dtStart)));
			rcurEvent.getProperties().add(new DtEnd(getDate(dtEnd)));
			rcurEvent.getProperties().add(uid);
			rcurEvent.getProperties().add(new Sequence(event.getRSeq()));
			
			if(event.getExDate()!=null&&event.getExDate().length()>0){
				ArrayList<Date> exdateList = getDateListFromString(event.getExDate());
				DateList datelist = new DateList();
				for (Date date : exdateList){
					java.util.Calendar exDate = java.util.Calendar.getInstance();
					exDate.setTimeZone(timezone);
					exDate.setTime(date);						  
					datelist.add(getDate(exDate));
				}
				rcurEvent.getProperties().add(new ExDate(datelist));
			}
			calendar.getComponents().add(rcurEvent);
		  }
		else if(event.getRecruiterId()!=null){	
			SimpleDateFormat rfcDateTimeFormatLocal = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			Date recur_id_date = rfcDateTimeFormatLocal.parse(event.getRecruiterId());
			overrideEvents.put(recur_id_date, event);
			  VEvent subEvent = new VEvent();
			  subEvent.getProperties().add(tz.getTimeZoneId());
			  subEvent.getProperties().add(new DtStart(getDate(dtStart)));
			  subEvent.getProperties().add(new DtEnd(getDate(dtEnd)));
			  subEvent.getProperties().add(uid);
			  subEvent.getProperties().add(new Sequence(event.getRSeq()));
			  
			  
			  java.util.Calendar updateInstanceDate = java.util.Calendar.getInstance();
			  updateInstanceDate.setTimeZone(timezone);
			  updateInstanceDate.setTime(recur_id_date);			  
			  updateInstanceDate.set(java.util.Calendar.MILLISECOND, 0);
			  net.fortuna.ical4j.model.Date updateInsDate = getDate(updateInstanceDate);
			  RecurrenceId recurId = new RecurrenceId(timezone);
			  recurId.setDate(updateInsDate);
			  subEvent.getProperties().add(recurId);
			  calendar.getComponents().add(subEvent);
		  }else{ //invalid entries. just discard
		  
		  }	
        return calendar;
    }
}
