package com.jobdiva.api.dao.chatbot;

import java.net.URLEncoder;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.proxy.ProxyAPIDao;
import com.jobdiva.api.mapper.ChatbotQuestionRowMapper;
import com.jobdiva.api.mapper.ChatbotSocialQuestionRowMapper;
import com.jobdiva.api.model.SessionInfo;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotHarvestAccount;
import com.jobdiva.api.model.chatbot.ChatbotHarvestMachine;
import com.jobdiva.api.model.chatbot.ChatbotHarvestMachineStatus;
import com.jobdiva.api.model.chatbot.ChatbotHarvestStatus;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotRecruiterData;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotTag;
import com.jobdiva.api.model.chatbot.ChatbotTagValue;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVMSAccount;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;

@Component
public class ChatbotTrainingDataDao extends AbstractJobDivaDao {
	
	public SessionInfo getSession(JobDivaSession session) throws Exception {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserName(session.getUserName());
		sessionInfo.setFirstName(session.getFirstName());
		sessionInfo.setLastName(session.getLastName());
		return sessionInfo;
	}
	
	public List<ChatbotQuestion> getQuestions(JobDivaSession jobDivaSession) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select a.id, a.text, b.questionid, b.text, " //
				+ " a.ques_ref_id, a.keywords, a.show_as_suggestion, " //
				+ " a.ques_tag, a.substitute, a.lookup_tag, a.confid" //
				+ " from tchatbotsupport_question a, tquevariants_support b " //
				+ " where a.id = b.questionid and a.activate_status = 1" //
				+ " order by a.ques_ref_id";
		//
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] {};
		//
		List<ChatbotQuestion> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotQuestion>() {
			
			@Override
			public ChatbotQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotQuestion q = new ChatbotQuestion();
				q.setQuestion(rs.getString(2));
				q.setReferenceID(rs.getInt(5));
				q.setKeywords(rs.getString(6));
				q.setShowAsSuggestion(rs.getInt(7));
				q.setConfidenceLevel(rs.getInt(11));
				if (rs.getString(8) != null) {
					q.setQuestionWithTag(rs.getString(8));
				} else {
					q.setQuestionWithTag("");
				}
				if (rs.getBoolean(9) == true) {
					q.setSubstituteTag(true);
				} else {
					q.setSubstituteTag(false);
				}
				if (rs.getString(10) != null) {
					q.setSubstituteTagValues(rs.getString(10));
				} else {
					q.setSubstituteTagValues("");
				}
				q.addVariation(rs.getString(4));
				q.setTmpId(rs.getInt(3));
				return q;
			}
		});
		//
		int current_questionid = -1;
		List<ChatbotQuestion> questions = new ArrayList<ChatbotQuestion>();
		ChatbotQuestion q = null;
		//
		for (int i = 0; i < list.size(); i++) {
			if (current_questionid != list.get(i).getTmpId()) {
				if (current_questionid != -1) {
					questions.add(q);
				}
				current_questionid = list.get(i).getTmpId();
				q = new ChatbotQuestion();
				q.setQuestion(list.get(i).getQuestion());
				q.setReferenceID(list.get(i).getReferenceID());
				q.setKeywords(list.get(i).getKeywords());
				q.setShowAsSuggestion(list.get(i).getShowAsSuggestion());
				q.setQuestionWithTag(list.get(i).getQuestionWithTag());
				q.setSubstituteTagValues(list.get(i).getSubstituteTagValues());
				q.setConfidenceLevel(list.get(i).getConfidenceLevel());
			}
			q.addVariation(list.get(i).getVariations().get(0));
			// add last question
			if (i == list.size() - 1) {
				questions.add(q);
			}
		}
		return questions;
	}
	
	public List<ChatbotSocialQuestion> getSocialQuestions(JobDivaSession jobDivaSession) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select a.id, a.question, b.text, a.referenceid, a.answer, b.questionid " //
				+ " from tsocialquestion a, tsocialvariant b " //
				+ " where a.id = b.questionid " //
				+ " order by a.referenceid ";
		//
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] {};
		//
		List<ChatbotSocialQuestion> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotSocialQuestion>() {
			
			@Override
			public ChatbotSocialQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotSocialQuestion q = new ChatbotSocialQuestion();
				q.setQuestion(rs.getString(2));
				q.addVariation(rs.getString(3));
				q.setAnswer(rs.getString(5));
				q.setReferenceID(rs.getInt(4));
				q.setTmpId(rs.getInt(6));
				return q;
			}
		});
		//
		int current_questionid = -1;
		ChatbotSocialQuestion q = null;
		//
		List<ChatbotSocialQuestion> questions = new ArrayList<ChatbotSocialQuestion>();
		//
		for (int i = 0; i < list.size(); i++) {
			if (current_questionid != list.get(i).getTmpId()) {
				if (current_questionid != -1) {
					questions.add(q);
				}
				current_questionid = list.get(i).getTmpId();
				q = new ChatbotSocialQuestion();
				q.setQuestion(list.get(i).getQuestion());
				q.setAnswer(list.get(i).getAnswer());
				q.setReferenceID(list.get(i).getReferenceID());
			}
			q.addVariation(list.get(i).getVariations().get(0));
		}
		//
		return questions;
	}
	
	public ChatbotAnswer getAnswer(JobDivaSession jobDivaSession, Integer refID) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select defaultans, ques_ref_id " //
				+ " from tchatbotsupport_question "//
				+ " where ques_ref_id = ? ";
		//
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] { refID };
		//
		List<ChatbotAnswer> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotAnswer>() {
			
			@Override
			public ChatbotAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotAnswer answer = new ChatbotAnswer();
				answer.setAnswer(rs.getString("defaultans"));
				answer.setQuestionID(rs.getInt("ques_ref_id"));
				//
				return answer;
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public ChatbotAnswer getSocialAnswer(JobDivaSession jobDivaSession, Integer refID) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select answer " //
				+ " from tsocialquestion "//
				+ " where referenceid = ? ";
		//
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] { refID };
		//
		List<ChatbotAnswer> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotAnswer>() {
			
			@Override
			public ChatbotAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotAnswer answer = new ChatbotAnswer();
				answer.setAnswer(rs.getString("answer"));
				return answer;
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public ChatbotQuestion getQuestion(JobDivaSession jobDivaSession, Integer refID) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select id, text " //
				+ " from tchatbotsupport_question "//
				+ " where ques_ref_id = ? ";
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] { refID };
		//
		List<ChatbotQuestion> list = jdbcTemplate.query(sql, params, new ChatbotQuestionRowMapper());
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public ChatbotSocialQuestion getSocialQuestion(JobDivaSession jobDivaSession, Integer refID) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select id, question, answer "//
				+ " from tsocialquestion "//
				+ " where referenceid = ? ";
		//
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = new Object[] { refID };
		//
		List<ChatbotSocialQuestion> list = jdbcTemplate.query(sql, params, new ChatbotSocialQuestionRowMapper());
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public ChatbotRecruiterData getRecruiterData(JobDivaSession jobDivaSession) {
		//
		long recruiterid = jobDivaSession.getRecruiterId();
		long teamid = jobDivaSession.getTeamId();
		//
		String sql = "select id, firstname, lastname, groupid " //
				+ " from trecruiter "//
				+ " where groupid = ? and id = ? ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid, recruiterid };
		//
		logger.info("\n\n IN GET USER DATA\n\n");
		logger.info("teamid: " + teamid);
		logger.info("recruiterid: " + recruiterid);
		//
		List<ChatbotRecruiterData> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotRecruiterData>() {
			
			@Override
			public ChatbotRecruiterData mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotRecruiterData data = new ChatbotRecruiterData();
				data.setId(rs.getLong("id"));
				data.setFirstName(rs.getString("firstname"));
				data.setLastName(rs.getString("lastname"));
				data.setTeamid(rs.getLong("groupid"));
				return data;
			}
		});
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public ChatbotUserData getUserData(JobDivaSession jobDivaSession) {
		//
		long recruiterid = jobDivaSession.getRecruiterId();
		long teamid = jobDivaSession.getTeamId();
		//
		ChatbotUserData data = new ChatbotUserData();
		//
		String sql = "select company " //
				+ " from tteam "//
				+ " where id = ? ";
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid };
		//
		List<ChatbotUserData> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotUserData>() {
			
			@Override
			public ChatbotUserData mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotUserData tmp = new ChatbotUserData();
				tmp.setCompanyName(rs.getString("company"));
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			data.setCompanyName(list.get(0).getCompanyName());
		}
		//
		sql = "select t2.apache_location, t2.environment_type "//
				+ " from tmaindb_teamid t1, tmaindbs t2 "//
				+ " where t1.maindbid = t2.id and t1.teamid = ? ";
		//
		jdbcTemplate = getCentralJdbcTemplate();
		params = new Object[] { teamid };
		//
		list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotUserData>() {
			
			@Override
			public ChatbotUserData mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotUserData tmp = new ChatbotUserData();
				tmp.setApacheLocation(rs.getString(1));
				tmp.setEnvType(rs.getString(2));
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			data.setApacheLocation(list.get(0).getApacheLocation());
			data.setEnvType(list.get(0).getEnvType());
		}
		//
		//
		// get permission
		sql = "select permission, firstname, lastname, leader, permission2_recruiter " //
				+ " from trecruiter "//
				+ " where groupid = ? and ID = ? ";
		//
		jdbcTemplate = getJdbcTemplate();
		params = new Object[] { teamid, recruiterid };
		//
		list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotUserData>() {
			
			@Override
			public ChatbotUserData mapRow(ResultSet rs, int rowNum) throws SQLException {
				System.out.println(recruiterid);
				ChatbotUserData tmp = new ChatbotUserData();
				long permission = rs.getLong(1);
				Boolean isTeamLeader = !((rs.getInt(4) & 16) == 0);
				boolean allowManagingJobBoardsCriteriaAndProfiles = false;
				// if (( 0!=(leadervalue & (1<<(i-1))) || leadervalue==0) &&
				// !(i==2 &&
				// accessControlSet.contains("hide_VMS"))){%>true<%}else{%>false<%}
				// String accessControlSet = "";
				int i = 1;
				if (isTeamLeader && 0 != (permission & (1 << (i - 1)))) {
					allowManagingJobBoardsCriteriaAndProfiles = true;
				}
				boolean displayFourDailyEmailProfileOption = false;
				i = 6;
				if (isTeamLeader && 0 != (permission & (1 << (i - 1)))) {
					displayFourDailyEmailProfileOption = true;
				}
				i = 7;
				boolean allowManagingJobBoardsCriteriaOnly = false;
				if (isTeamLeader && 0 != (permission & (1 << (i - 1)))) {
					allowManagingJobBoardsCriteriaOnly = true;
				}
				String permission2 = rs.getString("permission2_recruiter");
				// private boolean allowAssignOnboardingToNotLinkedJob;//42
				// private boolean allowManagingOnboarding;//13
				// private boolean allowAccessCompletedOnboarding;//6
				// private boolean allowAccessCompletedOnboardingForHires;//7
				// private boolean
				// allowAccessCompletedOnboardingForPrimaryJobs;//8
				// private boolean allowAccessCompletedOnboardingForMyJobs;//9
				// private boolean allowAccessCompletedOnboardingForAllJobs;//10
				// private boolean
				// allowAccessCompletedOnboardingForDivision;//11
				System.out.println(permission);
				System.out.println(permission2);
				if (permission2 != null && !permission2.isEmpty()) {
					if (permission2.charAt(42) == '1')
						tmp.setAllowAssignOnboardingToNotLinkedJob(true);
					if (permission2.charAt(13) == '1')
						tmp.setAllowManagingOnboarding(true);
					if (permission2.charAt(6) == '1')
						tmp.setAllowAccessCompletedOnboarding(true);
					if (permission2.charAt(7) == '1')
						tmp.setAllowAccessCompletedOnboardingForHires(true);
					if (permission2.charAt(8) == '1')
						tmp.setAllowAccessCompletedOnboardingForPrimaryJobs(true);
					if (permission2.charAt(9) == '1')
						tmp.setAllowAccessCompletedOnboardingForMyJobs(true);
					if (permission2.charAt(10) == '1')
						tmp.setAllowAccessCompletedOnboardingForAllJobs(true);
					if (permission2.charAt(11) == '1')
						tmp.setAllowAccessCompletedOnboardingForDivision(true);
					if (permission2.charAt(120) == '1')
						tmp.setAllowUnassignAccessIndividualDocuments(true);
				}
				tmp.setAllowManagingJobBoardsCriteriaAndProfiles(allowManagingJobBoardsCriteriaAndProfiles);
				tmp.setAllowManagingJobBoardsCriteriaOnly(allowManagingJobBoardsCriteriaOnly);
				tmp.setDisplayTheFourDailyEmailProfileOption(displayFourDailyEmailProfileOption);
				tmp.setFirstname(rs.getString("firstname"));
				tmp.setLastname(rs.getString("lastname"));
				tmp.setTeamLeader(isTeamLeader);
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			ChatbotUserData tmp = list.get(0);
			data.setAllowAssignOnboardingToNotLinkedJob(tmp.isAllowAssignOnboardingToNotLinkedJob());
			data.setAllowManagingOnboarding(tmp.isAllowManagingOnboarding());
			data.setAllowAccessCompletedOnboarding(tmp.isAllowAccessCompletedOnboarding());
			data.setAllowAccessCompletedOnboardingForHires(tmp.isAllowAccessCompletedOnboardingForHires());
			data.setAllowAccessCompletedOnboardingForPrimaryJobs(tmp.isAllowAccessCompletedOnboardingForPrimaryJobs());
			data.setAllowAccessCompletedOnboardingForMyJobs(tmp.isAllowAccessCompletedOnboardingForMyJobs());
			data.setAllowAccessCompletedOnboardingForAllJobs(tmp.isAllowAccessCompletedOnboardingForAllJobs());
			data.setAllowAccessCompletedOnboardingForDivision(tmp.isAllowAccessCompletedOnboardingForDivision());
			data.setAllowUnassignAccessIndividualDocuments(tmp.isAllowUnassignAccessIndividualDocuments());
			data.setAllowManagingJobBoardsCriteriaAndProfiles(tmp.isAllowManagingJobBoardsCriteriaAndProfiles());
			data.setAllowManagingJobBoardsCriteriaOnly(tmp.isAllowManagingJobBoardsCriteriaOnly());
			data.setDisplayTheFourDailyEmailProfileOption(tmp.isDisplayTheFourDailyEmailProfileOption());
			data.setFirstname(tmp.getFirstname());
			data.setLastname(tmp.getLastname());
			data.setTeamLeader(tmp.isTeamLeader());
			HashMap<Long, String> notUsedHirePackage = getNotUsedHirePackages(teamid);
			if(notUsedHirePackage.size()>0) {
				data.hasUnusedHirePackage = true;
				data.unusedHirePackagesName = notUsedHirePackage.entrySet().stream().map(e->e.getValue()).collect(Collectors.joining(", ","",""));
			}
		}
		//
		return data;
		//
	}
	
	public ChatbotVisibility checkChatbotVisibility(JobDivaSession jobDivaSession) {
		//
		long teamid = jobDivaSession.getTeamId();
		//
		ChatbotVisibility cv = new ChatbotVisibility();
		//
		cv.setTeamid(teamid);
		cv.setShow(false);
		//
		if (teamid == 1) {
			cv.setShow(true);
		}
		//
		List<ChatbotVisibility> list = new ArrayList<ChatbotVisibility>();
		list.add(cv);
		//
		return list != null && list.size() > 0 ? list.get(0) : null;
		//
	}
	
	public List<ChatbotTag> getChatbotTagList(JobDivaSession jobDivaSession) {
		String sql = "select id, tag, summary, clientsDefineValue, type from tchatbotsupport_tag";
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		//
		List<ChatbotTag> list = jdbcTemplate.query(sql, new RowMapper<ChatbotTag>() {
			
			@Override
			public ChatbotTag mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTag t = new ChatbotTag();
				t.setTagid(rs.getInt(1));
				t.setTag(rs.getString(2));
				t.setTagTitle(rs.getString(3));
				t.setClientDefined(rs.getBoolean(4));
				t.setTagType(rs.getString(5));
				return t;
			}
		});
		return list;
	}
	
	public ChatbotTagValue isMachineDownloadingResumes(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		// Long webid = Long.valueOf(references[0]);
		String tagType = "BINARY";
		String sql = "select count(*) from twebsites_detail a where teamid = ? and harvest =1  and (webid, machine_no) not in (select webid, machine_no from twebdatapersistance where teamid =a.teamid) ";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				tag.setValue("false");
				return tag;
			}
		});
		if (list.size() > 0) {
			tagValue = list.get(0);
		} else {
			tagValue.setValue("trues");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue isMachineInstalled(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		Long machineNo = Long.valueOf(references[0]);
		String tagType = "BINARY";
		String sql = "select 1 from tharvester_alive where teamid=? and machine_no=?";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, machineNo };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				tag.setValue("true");
				return tag;
			}
		});
		if (list.size() > 0) {
			tagValue = list.get(0);
		} else {
			tagValue.setValue("false");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue hasMachineIssue(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		Long machineNo = Long.valueOf(references[0]);
		String tagType = "BINARY";
		String sql = "select 1 from tharvester_alive where teamid=? and machine_no=? and timestampdiff(MINUTE, daterecord, now()) < 90";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, machineNo };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				tag.setValue("false");
				return tag;
			}
		});
		tagValue.setValue("true");
		if (list.size() > 0) {
			tagValue = list.get(0);
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getJobBoardStatus(Long teamid, String tagName, String[] references) {
		// return
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select coalesce(harvest, 0) from twebsites_detail where teamid=? and webid=? and upper(username)=upper(?)";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				Long status_id = rs.getLong(1);
				if (status_id == 0) {
					tag.setValue("INACTIVE");
				} else if (status_id == 1L) {
					tag.setValue("ACTIVE");
				} else if (status_id == 2L) {
					tag.setValue("HALTED");
				}
				return tag;
			}
		});
		if (list.size() > 0) {
			tagValue = list.get(0);
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getDownloadLimitPerDay(Long teamid, String tagName, String[] references) {
		// return
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "NUMBER";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select harvestlimit from twebsites_detail where teamid=? and webid=? and upper(username)=upper(?)";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				tag.setValue(String.valueOf(rs.getLong(1)));
				return tag;
			}
		});
		if (list.size() > 0) {
			tagValue = list.get(0);
		} else {
			tagValue.setValue("0");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public Long getResumeCountsToday(Long teamid, Long webid, String username) {
		long resumeCounts = 0;
		try {
			String sql = "select sum(resumecount) from twebdatapersistance where teamid = ? and webid=? and username=? and indate between str_to_date(?,'%Y-%m-%d %H:%i:%s') and str_to_date(?,'%Y-%m-%d %H:%i:%s')";
			JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
			String strTimeZone = getStrTimeZone(teamid);
			TimeZone tz = TimeZone.getTimeZone(strTimeZone);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(tz);
			sdf2.setTimeZone(tz);
			String dateStartStr = sdf.format(new java.util.Date());
			String dateEndStr = dateStartStr + " 23:59:59";
			java.util.Date dateStart = sdf.parse(dateStartStr);
			java.util.Date dateEnd = sdf2.parse(dateEndStr);
			sdf2.setTimeZone(TimeZone.getTimeZone("America/New_York"));
			dateStartStr = sdf2.format(dateStart);
			dateEndStr = sdf2.format(dateEnd);
			Object[] params = new Object[] { teamid, webid, username, dateStartStr, dateEndStr };
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
			if(list.size()>0) {
				resumeCounts = list.get(0);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return resumeCounts;
		
	}
	
	public ChatbotTagValue hasJobBoardSearchCriteria(Long teamid, String tagName, String[] references) {
		// return
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select 1 from tharvestercriteria where teamid=? and webid=? and upper(username) = upper(?) limit 1";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<ChatbotTagValue> list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotTagValue>() {
			
			@Override
			public ChatbotTagValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotTagValue tag = new ChatbotTagValue();
				tag.setValue("true");
				return tag;
			}
		});
		if (list.size() > 0) {
			tagValue = list.get(0);
		} else {
			tagValue.setValue("false");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public Boolean isTimeOverLapping(Date date1, Date date2) {
		double hour_diff = (date1.getTime() - date2.getTime()) / 3600.0;
		System.out.println(hour_diff);
		if (hour_diff >= 4 || hour_diff <= -4)
			return false;
		else
			return true;
	}
	
	public ChatbotTagValue hasOverLappingTime(Long teamid, String tagName, String[] references) {
		// return
		ChatbotTagValue tagValue = new ChatbotTagValue();
		Boolean hasOverLapping = false;
		String tagType = "BINARY";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select UNIX_TIMESTAMP(time) from tschedule where teamid=? and webid=? and upper(username) = upper(?) and active = 1";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<Date> dateList = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Date(rs.getLong(1));
			}
		});
		if (dateList.size() > 1) {
			for (int i = 0; i < dateList.size() - 1; i++) {
				Date date1 = dateList.get(i);
				for (int j = i + 1; j < dateList.size(); j++) {
					Date date2 = dateList.get(j);
					if (isTimeOverLapping(date1, date2)) {
						hasOverLapping = true;
						break;
					}
				}
				if (hasOverLapping)
					break;
			}
			tagValue.setValue(String.valueOf(hasOverLapping));
		} else {
			tagValue.setValue("false");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getJobBoardName(Long teamid, String tagName, String[] references) {
		// return
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		String JobBoardName = "{";
		String sql = "select distinct a.websitename, a.id from twebsites a, twebsites_detail b where b.teamid=? and a.id = b.webid";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "\"" + rs.getString(1) + "\":" + rs.getLong(2);
			}
		});
		for (int i = 0; i < list.size(); i++) {
			JobBoardName += list.get(i);
			if (i < list.size() - 1) {
				JobBoardName += ", ";
			} else {
				JobBoardName += "}";
			}
		}
		tagValue.setValue(JobBoardName);
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getDownloadStartTime(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String startTime = "";
		String sql = "select date_format(time, '%H:%i') from tschedule where teamid=? and webid=? and upper(username) = upper(?) order by date_format(time, '%H:%i')";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<String> dateList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		for (int i = 0; i < dateList.size(); i++) {
			if(i==0)
				startTime = "[\"";
			String startDate = dateList.get(i);
			startTime = startTime + startDate;
			if (i < dateList.size() - 1) {
				startTime = startTime + "\", \"";
			} else {
				startTime = startTime + "\"]";
			}
		}
		tagValue.setValue(startTime);
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getJobBoardUsername(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		Long webid = Long.valueOf(references[0]);
		String jobBoard = "{";
		String sql = "select username, machine_no from twebsites_detail where teamid=? and webid=?";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid };
		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return "\"" + rs.getString(1) + "\":" + rs.getLong(2);
			}
		});
		for (int i = 0; i < list.size(); i++) {
			String username = list.get(i);
			if (i < list.size() - 1) {
				jobBoard += username + ",";
			} else
				jobBoard += username + "}";
		}
		tagValue.setValue(jobBoard);
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getNumberOfNonDownloadingMachines(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		String sql = "select count(distinct machine_no) from twebsites_detail a where a.teamid=? and a.harvest=1 and (a.webid, a.machine_no) not in (select distinct webid, machine_no from twebdatapersistance where teamid=a.teamid)";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});
		if (list.size() > 0) {
			tagValue.setValue(String.valueOf(list.get(0)));
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public HashMap<Long, String>getNotUsedHirePackages(Long teamid) {
		HashMap<Long, String> unusedPackageList = new HashMap<Long, String>();
		String sql ="select tt.id, tt.name from tonboarding_tab tt where tt.teamid = ?  and tt.deleted = 0 and tt.id<100 and tt.id not in (select t1.tabid from tonboardings_mapping t1, tonboardings t2 "
				+ " where t1.teamid=? and t1.docid=t2.id and t2.teamid = t1.teamid and (-1 = 0 or t2.DELETED = 0) ) order by upper(tt.name) asc";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] {teamid, teamid};
		List<Object[]> listData = jdbcTemplate.query(sql, params, new RowMapper<Object[]>() {
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException{
				Object[] data = new Object[2];
				data[0] = rs.getLong(1);
				data[1] = rs.getString(2);
				return data;
			}
		});
		for(int i=0;i<listData.size();i++) {
			Object[] data = listData.get(i);
			unusedPackageList.put((Long) data[0], (String) data[1]);
		}
		unusedPackageList.entrySet().stream().map(e->"\""+e.getKey()+"\":\""+e.getValue().replace("\"", "\\\"")+"\"").collect(Collectors.joining(",","{","}"));
		return unusedPackageList;
	}
	
	public Boolean hasRecentResume(Long teamid, Long webid, String username) {
		Boolean hasRecentResume = false;
		String sql = "select datecreated from trfqresume where teamid=? and webid=? and upper(username)=upper(?)";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<Date> dateList = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDate(1);
			}
		});
		for (int i = 0; i < dateList.size() && !hasRecentResume; i++) {
			Date createdDate = dateList.get(i);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createdDate);
			calendar.add(Calendar.HOUR_OF_DAY, 4);
			if (calendar.getTime().compareTo(new java.util.Date()) > 0) {
				hasRecentResume = true;
			}
		}
		return hasRecentResume;
	}
	
	public ChatbotTagValue hasNotDownloadSessionStarted(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select UNIX_TIMESTAMP(time) from tschedule where teamid=? and webid=? and upper(username)=upper(?)";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		Object[] params = new Object[] { teamid, webid, username };
		List<Date> dateList = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDate(1);
			}
		});
		int scheduleHour = 0;
		int scheduleMinute = 0;
		Calendar current_calendar = Calendar.getInstance();
		current_calendar.setTime(new java.util.Date());
		int currentHour = current_calendar.get(Calendar.HOUR_OF_DAY);
		int currentMinute = current_calendar.get(Calendar.MINUTE);
		if (dateList.size() > 0) {
			Boolean notWithInSchedule = true;
			for (int i = 0; i < dateList.size() && notWithInSchedule; i++) {
				Date scheduleTime = dateList.get(i);
				Calendar scheduleCalendar = Calendar.getInstance();
				scheduleCalendar.setTime(scheduleTime);
				scheduleHour = scheduleCalendar.get(Calendar.HOUR_OF_DAY);
				scheduleMinute = scheduleCalendar.get(Calendar.MINUTE);
				if (currentHour < scheduleHour || currentHour > scheduleHour + 4 || (currentHour == scheduleHour && currentMinute > scheduleMinute)) {
					notWithInSchedule = true;
				} else {
					notWithInSchedule = false;
				}
			}
			if (notWithInSchedule) {
				tagValue.setValue("true");
			} else {
				sql = "select UNIX_TIMESTAMP(datecreated) from tharvesteractivity where teamid=? and webid=? and upper(username) = upper(?)";
				List<Date> dateList2 = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
					
					@Override
					public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Date(rs.getLong(1));
					}
				});
				if (dateList2.size() > 0) {
					Boolean hasRecentHarvestActivity = false;
					for (int i = 0; i < dateList.size() && !hasRecentHarvestActivity; i++) {
						Date harvestedDate = dateList2.get(i);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(harvestedDate);
						calendar.add(Calendar.HOUR_OF_DAY, 4);
						if (calendar.getTime().compareTo(new java.util.Date()) > 0) {
							hasRecentHarvestActivity = true;
						}
					}
					if (!hasRecentHarvestActivity) {
						tagValue.setValue("false");
					}
				} else {
					if (hasRecentResume(teamid, webid, username))
						tagValue.setValue("false");
					else
						tagValue.setValue("true");
				}
			}
		} else {
			tagValue.setValue("true");
		}
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getCATTest(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		Boolean passCATTest = false;
		Long webid = Long.valueOf(references[0]);
		String username = references[1];
		String sql = "select password from twebsites_detail a, twebsites b where a.teamid=? and a.webid=? and a.webid = b.id and a.username=? and upper(b.url) like '%MONSTER%'";
		Object[] params = new Object[] { teamid, webid, username };
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		List<String> passwordList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		if (passwordList.size() > 0) {
			String password = passwordList.get(0);
			String decoded_password = decode(password, "zhangjintao");
			ProxyAPIDao proxyClient = new ProxyAPIDao();
			ProxyParameter parameter = new ProxyParameter();
			parameter.setName("cat");
			parameter.setName(decoded_password);
			String CAT_URL = "http://rsx.monster.com/query.ashx?q=java&rpcr=10038-50&mdatemaxage=788400&pagesize=20&ver=1.7&cat=" + URLEncoder.encode(decoded_password);
			System.out.println(CAT_URL);
			try {
				Response catResponse = proxyClient.proxyAPI("GET", CAT_URL, null, new ProxyParameter[] { parameter }, null);
				String responseBody = catResponse.getBody();
				if (responseBody.indexOf("Resumes") > 0) {
					passCATTest = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tagValue.setValue(String.valueOf(passCATTest));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue isCoddlerWorking(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		Boolean isWorking = false;
		String sql = "select nvl((datelastrun - DATE'1970-01-01') * 86400, 0) from tspiderswebsites where teamid=? and active = 1 and nvl(deleted,0)=0 ";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<Date> dateList = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Date(rs.getLong(1));
			}
		});
		if (dateList.size() > 0) {
			Date lastRunDate = dateList.get(0);
			System.out.println(lastRunDate.getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastRunDate);
			calendar.add(Calendar.HOUR_OF_DAY, 2);
			if (calendar.getTime().compareTo(new java.util.Date()) > 0) {
				isWorking = true;
			}
		}
		tagValue.setValue(String.valueOf(isWorking));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getNumberOfNonWorkingCoddler(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "NUMBER";
		Long numberOfNonWorkingCoddler = 0L;
		String sql = "select nvl((datelastrun - DATE'1970-01-01') * 86400, 0) from tspiderswebsites where teamid=? and active = 1 and nvl(deleted,0)=0";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<Date> dateList = jdbcTemplate.query(sql, params, new RowMapper<Date>() {
			
			@Override
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Date(rs.getLong(1));
			}
		});
		for (int i = 0; i < dateList.size(); i++) {
			Date lastRunDate = dateList.get(i);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastRunDate);
			calendar.add(Calendar.HOUR_OF_DAY, 2);
			if (calendar.getTime().compareTo(new java.util.Date()) < 0) {
				numberOfNonWorkingCoddler++;
			}
		}
		tagValue.setValue(String.valueOf(numberOfNonWorkingCoddler));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getCoddlerName(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		String site = "[\"";
		String sql = "select site from tspiderswebsites where teamid=? and nvl(deleted,0)=0";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid };
		List<String> siteList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		for (int i = 0; i < siteList.size(); i++) {
			site = site + siteList.get(i);
			if (i != siteList.size() - 1)
				site = site + "\", \"";
			else
				site = site + "\"]";
		}
		tagValue.setValue(site);
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue getCoddlerStatus(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "TEXT";
		String site = references[0];
		// Boolean isWorking = false;
		String sql = "select nvl(active,0), to_char(datelastrun,'YYYY-MM-DD HH:MI:ss'), loginfailures, maxloginattempts from tspiderswebsites where teamid=? and upper(site)=upper(?)";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid, site };
		List<String> dateList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String status = "";
				int isActive = rs.getInt(1);
				String dateLastRun = rs.getString(2);
				long loginfailures = rs.getLong(3);
				long maxloginattemps = rs.getLong(4);
				if (isActive == 0) {
					status = "INACTIVE";
				} else {
					if (loginfailures >= maxloginattemps) {
						status = "HALTED";
					} else {
						if(dateLastRun!=null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							java.util.Date lastRunDate;
							try {
								lastRunDate = sdf.parse(dateLastRun);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(lastRunDate);
								calendar.add(Calendar.HOUR_OF_DAY, 2);
								if (calendar.getTime().compareTo(new java.util.Date()) < 0) {
									status = "NOT_WORKING";
								} else
									status = "WORKING";
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								status = "NOT_WORKING";
								e.printStackTrace();
							}

						}

					}
				}
				return status;
			}
		});
		if (dateList.size() > 0)
			tagValue.setValue(dateList.get(0));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public ChatbotTagValue isMachineAtClient(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		String site = references[0];
		String sql = "select upper(computer_name), ip_address from tspidersmachinestats where teamid=? and upper(site)=upper(?)";
		Object[] params = new Object[] { teamid, site };
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<String> dateList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String atClient = "true";
				String computer_name = rs.getString(1);
				String ip_address = rs.getString(2);
				if (computer_name.startsWith("w10_") && ip_address.startsWith("10.10"))
					atClient = "false";
				return atClient;
			}
		});
		if (dateList.size() > 0)
			tagValue.setValue(dateList.get(0));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);
		return tagValue;
	}
	
	public static String decode(String str_input, String str_key) {
		// String str_key = "zhangjintao";
		if (str_input != null) {
			byte[] src = str_input.getBytes();
			byte[] key = str_key.getBytes();
			int src_len = src.length, key_len = key.length;
			byte[] des = new byte[src_len];
			for (int i = 0; i < src_len; i++) {
				des[i] = (byte) (src[i] ^ key[i % (key_len)]);
			}
			return new String(des);
		} else {
			return str_input;
		}
	}
	
	public ChatbotHarvestMachine getHarvestMachine(Long teamid, Long webid, Long machineNo) {
		ChatbotHarvestMachine harvestMachine = new ChatbotHarvestMachine();
		String[] referecens = {String.valueOf(machineNo)};
		ChatbotTagValue machineIssue = hasMachineIssue(teamid, null, referecens);
		harvestMachine.machineNo = machineNo;
		harvestMachine.hasMachineIssue = machineIssue.getValue().equals("true");
		ChatbotTagValue isMachineInstalled = isMachineInstalled(teamid, null, referecens);
		harvestMachine.isMachineInstalled = isMachineInstalled.getValue().equals("true");
		return harvestMachine;
	}
	
	
	public ChatbotHarvestStatus getChatbotHarvestStatus(JobDivaSession jobDivaSession, Long webid) {
		ChatbotHarvestStatus harvestStatus = new ChatbotHarvestStatus(); 
		Long teamid = jobDivaSession.getTeamId();
		harvestStatus.teamid = teamid;
		String sql =  "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? ";
		Object[] params = new Object[] {teamid};
		if(webid !=null) {
			sql += " and a.webid=?";
			params = new Object[] {teamid, webid};
		}
		sql += " order by machine_no";
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		List<Object[]> dataList = jdbcTemplate.query(sql, params, new RowMapper<Object[]>() {
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] data = new Object[7];
				data[0] = rs.getString(1);
				data[1] = rs.getLong(2);
				data[2] = rs.getLong(3);
				data[3] = rs.getLong(4);
				data[4] = rs.getString(5);
				data[5] = rs.getLong(6);
				data[6] = rs.getLong(7);
				return data;
			}
		});
		Long machineNo = -1L;
		harvestStatus.accounts = new ArrayList<ChatbotHarvestAccount>();
		harvestStatus.machines = new ArrayList<ChatbotHarvestMachine>();
		for(int i=0;i<dataList.size();i++) {
			Object[] data = dataList.get(i);
			webid = (Long)data[6];
			if(i==0) {
				harvestStatus.name = (String) data[0];
				harvestStatus.active = (Long) data[1];
				harvestStatus.harvest = (Long) data[2];		
				harvestStatus.webid = webid;
			}

			Long tmp_machineNo = (Long) data[5];

			if(tmp_machineNo!=machineNo) {
				machineNo = tmp_machineNo;
				ChatbotHarvestMachine harvestMachine = new ChatbotHarvestMachine();
				String[] references = {String.valueOf(machineNo)};
				ChatbotTagValue machineIssue = hasMachineIssue(teamid, null, references);
				harvestMachine.machineNo = machineNo;
				harvestMachine.hasMachineIssue = machineIssue.getValue().equals("true");
				ChatbotTagValue isMachineInstalled = isMachineInstalled(teamid, null, references);
				harvestMachine.isMachineInstalled = isMachineInstalled.getValue().equals("true");
				harvestStatus.machines.add(harvestMachine);
			}
			ChatbotHarvestAccount harvestAccount = new ChatbotHarvestAccount();
			String accountName = (String)data[4];
			harvestAccount.name = accountName;
			harvestAccount.harvest = (Long) data[3];
			String[] references = {String.valueOf(webid), accountName};
			harvestAccount.machineNO = machineNo;
			harvestAccount.webid = webid;
			harvestAccount.websitename = harvestStatus.name;
			harvestAccount.status = getJobBoardStatus(teamid, null, references).getValue();
			harvestAccount.downloaddLimitPerDay = Long.valueOf(getDownloadLimitPerDay(teamid, null, references).getValue());
			harvestAccount.hasRecentResume = hasRecentResume(teamid, webid, accountName);
			harvestAccount.hasNotDownloadSessionStarted = hasNotDownloadSessionStarted(teamid,null, references).getValue().equals("true");
			harvestAccount.downloadStartTime = getDownloadStartTime(teamid, null, references).getValue();
			harvestAccount.hasOverLappingTime = hasOverLappingTime(teamid, null, references).getValue().equals("true");
			harvestAccount.CATTest = getCATTest(teamid, null, references).getValue();
			harvestAccount.hasJobBoardCriteria = hasJobBoardSearchCriteria(teamid, null, references).getValue().equals("true");
			harvestAccount.resumeCountsToday = getResumeCountsToday(teamid, webid, accountName);
			harvestStatus.accounts.add(harvestAccount);
		}
		return harvestStatus;
	}
	
	public List<ChatbotHarvestAccount> getHarvesetAccountStatus(JobDivaSession jobDivaSession, Long webid, Long machineNo, String harvestStatus ){
		List<ChatbotHarvestAccount> accountList = new ArrayList<ChatbotHarvestAccount>();
		String sql =  "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? ";
		if(webid!=null) {
			sql += " and a.webid = ?";
		} 
		if(machineNo!=null) {
			sql += " and a.machine_no = ?";
		}
		if("active".equals(harvestStatus)) {
			sql +=" and coalesce(a.harvest, 0) >0 ";
		}
		Long teamid = jobDivaSession.getTeamId();
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(teamid);
		if(webid!=null) {
			params.add(webid);
		}
		if(machineNo!=null) {
			params.add(machineNo);
		}
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		List<Object[]> dataList = jdbcTemplate.query(sql, params.toArray(), new RowMapper<Object[]>() {
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] data = new Object[7];
				data[0] = rs.getString(1);
				data[1] = rs.getLong(2);
				data[2] = rs.getLong(3);
				data[3] = rs.getLong(4);
				data[4] = rs.getString(5);
				data[5] = rs.getLong(6);
				data[6] = rs.getLong(7);
				return data;
			}
		});

		for(int i=0;i<dataList.size();i++) {
			Object[] data = dataList.get(i);
			ChatbotHarvestAccount harvestAccount = new ChatbotHarvestAccount();
			String accountName = (String)data[4];
			String websitename = (String)data[0];
			machineNo = (Long) data[5];
			webid = (Long) data[6];
			harvestAccount.name = accountName;
			harvestAccount.harvest = (Long) data[3];			
			String[] references = {String.valueOf(webid), accountName};
			harvestAccount.machineNO = machineNo;
			harvestAccount.webid = webid;
			harvestAccount.websitename = websitename;
			harvestAccount.status = getJobBoardStatus(teamid, null, references).getValue();
			harvestAccount.downloaddLimitPerDay = Long.valueOf(getDownloadLimitPerDay(teamid, null, references).getValue());
			harvestAccount.hasRecentResume = hasRecentResume(teamid, webid, accountName);
			harvestAccount.hasNotDownloadSessionStarted = hasNotDownloadSessionStarted(teamid,null, references).getValue().equals("true");
			harvestAccount.downloadStartTime = getDownloadStartTime(teamid, null, references).getValue();
			harvestAccount.hasOverLappingTime = hasOverLappingTime(teamid, null, references).getValue().equals("true");
			harvestAccount.CATTest = getCATTest(teamid, null, references).getValue();
			harvestAccount.hasJobBoardCriteria = hasJobBoardSearchCriteria(teamid, null, references).getValue().equals("true");
			harvestAccount.resumeCountsToday = getResumeCountsToday(teamid, webid, accountName);
			accountList.add(harvestAccount);
		}
		return accountList;
		
	}
	
	public List<ChatbotVMSAccount> getVMSAccount(JobDivaSession jobDivaSession){
		List<ChatbotVMSAccount> VMSAccountList = new ArrayList<ChatbotVMSAccount>();
		Long teamid = jobDivaSession.getTeamId();
		String sql = "select a.site, a.username, a.url, nvl(a.active,0), nvl(a.active_timesheet,0), nvl(a.active_submittal,0), nvl(a.loginfailures,0), nvl(a.maxloginattempts,0), to_char(a.datelastrun,'YYYY-MM-DD HH:MI:ss'), b.computer_name, b.ip_address from tspiderswebsites a, tspidersmachinestats b where a.teamid = b.teamid(+) and a.teamid=1 and a.site = b.site(+) and nvl(a.deleted, 0)=0";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(teamid);
		List<Object[]> dataList = jdbcTemplate.query(sql, params.toArray(), new RowMapper<Object[]>() {
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] data = new Object[11];
				data[0] = rs.getString(1);
				data[1] = rs.getString(2);
				data[2] = rs.getString(3);
				data[3] = rs.getLong(4);
				data[4] = rs.getLong(5);
				data[5] = rs.getLong(6);
				data[6] = rs.getLong(7);
				data[7] = rs.getLong(8);
				data[8] = rs.getString(9);
				data[9] = rs.getString(10);
				data[10] = rs.getString(11);
				return data;
			}
		});
		for(int i=0;i<dataList.size();i++) {
			
		}
		return VMSAccountList;
	}
	
	public List<ChatbotHarvestMachineStatus> getHarvestMachineStatus(JobDivaSession jobDivaSession) {
		List<ChatbotHarvestMachineStatus> machineList = new ArrayList<ChatbotHarvestMachineStatus>();
		Long teamid = jobDivaSession.getTeamId();
		String sql =  "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? order by machine_no, websitename, username";
		Object[] params = new Object[] {teamid};
		JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
		List<Object[]> dataList = jdbcTemplate.query(sql, params, new RowMapper<Object[]>() {
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] data = new Object[7];
				data[0] = rs.getString(1);
				data[1] = rs.getLong(2);
				data[2] = rs.getLong(3);
				data[3] = rs.getLong(4);
				data[4] = rs.getString(5);
				data[5] = rs.getLong(6);
				data[6] = rs.getLong(7);
				return data;
			}
		});
		Long machineNo = -1L;
		ChatbotHarvestMachineStatus machineStatus = null;
		for(int i=0;i<dataList.size();i++) {
			Object[] data = dataList.get(i);
			Long tmp_machineNo = (Long) data[5];
			if(tmp_machineNo!=machineNo) {
				if(machineStatus!=null)
					machineList.add(machineStatus);
				machineNo = tmp_machineNo;
				machineStatus = new ChatbotHarvestMachineStatus();
				machineStatus.teamid = teamid;
//				machineStatus.accounts = new ArrayList<ChatbotHarvestAccount>();
				String[] references = {String.valueOf(machineNo)};
				ChatbotTagValue machineIssue = hasMachineIssue(teamid, null, references);
				machineStatus.machineNo = machineNo;
				machineStatus.hasMachineIssue = machineIssue.getValue().equals("true");
				ChatbotTagValue isMachineInstalled = isMachineInstalled(teamid, null, references);
				machineStatus.isMachineInstalled = isMachineInstalled.getValue().equals("true"); 
			}
//			ChatbotHarvestAccount harvestAccount = new ChatbotHarvestAccount();
//			String accountName = (String)data[4];
//			Long webid = (Long) data[6];
//			String websitename = (String)data[0];
//			harvestAccount.name = accountName;
//			harvestAccount.harvest = (Long) data[3];			
//			String[] references = {String.valueOf(webid), accountName};
//			harvestAccount.machineNO = machineNo;
//			harvestAccount.webid = webid;
//			harvestAccount.websitename = websitename;
//			harvestAccount.status = getJobBoardStatus(teamid, null, references).getValue();
//			harvestAccount.downloaddLimitPerDay = Long.valueOf(getDownloadLimitPerDay(teamid, null, references).getValue());
//			harvestAccount.hasRecentResume = hasRecentResume(teamid, webid, accountName);
//			harvestAccount.hasNotDownloadSessionStarted = hasNotDownloadSessionStarted(teamid,null, references).getValue().equals("true");
//			harvestAccount.downloadStartTime = getDownloadStartTime(teamid, null, references).getValue();
//			harvestAccount.hasOverLappingTime = hasOverLappingTime(teamid, null, references).getValue().equals("true");
//			harvestAccount.CATTest = getCATTest(teamid, null, references).getValue();
//			harvestAccount.hasJobBoardCriteria = hasJobBoardSearchCriteria(teamid, null, references).getValue().equals("true");
//			machineStatus.accounts.add(harvestAccount);
		}
		return machineList;
		
	}
	
	public String getStrTimeZone(Long teamid) {
		String strTimeZone = "America/New_York";
		Object[] params = new Object[] {teamid};
		String sql = "select strtimezone from tteam where id= ?";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<String> dataList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		if(dataList.size()>0) {
			strTimeZone = dataList.get(0);
		}
		return strTimeZone;
	}
	
	public ChatbotTagValue getChatbotTagValue(JobDivaSession jobDivaSession, String tag, String[] references) {
		ChatbotTagValue tagValue = null;
		Long teamid = jobDivaSession.getTeamId();
		if (tag != null && !tag.isEmpty()) {
			switch (tag) {
				case ("MACHINE_DOWNLOADING_RESUMES"):
					tagValue = isMachineDownloadingResumes(teamid, tag, references);
					break;
				case ("MACHINE_INSTALLED"):
					tagValue = isMachineInstalled(teamid, tag, references);
					break;
				case ("MACHINE_ISSUE"):
					tagValue = hasMachineIssue(teamid, tag, references);
					break;
				case ("JOBBOARD_STATUS"):
					tagValue = getJobBoardStatus(teamid, tag, references);
					break;
				case ("DOWNLOAD_LIMIT_PER_DAY"):
					tagValue = getDownloadLimitPerDay(teamid, tag, references);
					break;
				case ("HAS_JOBBOARD_SEARCH_CRITERIA"):
					tagValue = hasJobBoardSearchCriteria(teamid, tag, references);
					break;
				case ("HAS_OVERLAPPING_TIME"):
					tagValue = hasOverLappingTime(teamid, tag, references);
					break;
				case ("JOBBOARD_NAME"):
					tagValue = getJobBoardName(teamid, tag, references);
					break;
				case ("DOWNLOAD_START_TIME"):
					tagValue = getDownloadStartTime(teamid, tag, references);
					break;
				case ("JOBBOARD_USERNAME"):
					tagValue = getJobBoardUsername(teamid, tag, references);
					break;
				case ("NUMBER_OF_NON_DOWNLOADING_MACHINES"):
					tagValue = getNumberOfNonDownloadingMachines(teamid, tag, references);
					break;
				case ("SESSION_NOT_STARTED"):
					tagValue = hasNotDownloadSessionStarted(teamid, tag, references);
					break;
				case ("CAT_TEST"):
					tagValue = getCATTest(teamid, tag, references);
					break;
				case ("CODDLER_WORKING"):
					tagValue = isCoddlerWorking(teamid, tag, references);
					break;
				case ("NUMBER_OF_NON_WORKING_CODDLERS"):
					tagValue = getNumberOfNonWorkingCoddler(teamid, tag, references);
					break;
				case ("CODDLER_STATUS"):
					tagValue = getCoddlerStatus(teamid, tag, references);
					break;
				case ("MACHINE_AT_CLIENT"):
					tagValue = isMachineAtClient(teamid, tag, references);
					break;
				case ("CODDLER_NAME"):
					// tagValue = isMachineAtClient(teamid, tag, references);
					break;
				case ("CODDLER_USERNAME"):
					break;
				default:
					break;
			}
		}
		return tagValue;
	}
}
