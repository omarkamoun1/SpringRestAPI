package com.jobdiva.api.dao.chatbot;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.MethodInvocationRecorder.Recorded.ToCollectionConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.mail.SMTPServer;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.dao.proxy.ProxyAPIDao;
import com.jobdiva.api.mapper.ChatbotQuestionRowMapper;
import com.jobdiva.api.mapper.ChatbotSocialQuestionRowMapper;
import com.jobdiva.api.model.SessionInfo;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotCandidatePackges;
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
import com.jobdiva.api.model.chatbot.ChatbotVMSType;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;
import com.jobdiva.api.model.chatbot.ChatbotEmailAlert;
import com.jobdiva.api.model.proxy.ProxyParameter;
import com.jobdiva.api.model.proxy.Response;
import com.jobdiva.api.service.LogService;
import com.jobdiva.log.JobDivaLog;

@Component
public class ChatbotTrainingDataDao extends AbstractJobDivaDao {
	
	@Autowired
	LogService logService;
	
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
				q.setSubstituteTag(list.get(i).isSubstituteTag());
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
				tmp.companyName = rs.getString("company");
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			data.companyName = list.get(0).companyName;
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
				tmp.apacheLocation = rs.getString(1);
				tmp.envType = rs.getString(2);
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			data.apacheLocation = (list.get(0).apacheLocation);
			data.envType = (list.get(0).envType);
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
				Boolean isSuperUser = !((rs.getInt(4) & 256) == 0);
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
						tmp.allowAssignOnboardingToNotLinkedJob = true;
					if (permission2.charAt(13) == '1')
						tmp.allowManagingOnboarding = true;
					if (permission2.charAt(6) == '1')
						tmp.allowAccessCompletedOnboarding = true;
					if (permission2.charAt(7) == '1')
						tmp.allowAccessCompletedOnboardingForHires = true;
					if (permission2.charAt(8) == '1')
						tmp.allowAccessCompletedOnboardingForPrimaryJobs = true;
					if (permission2.charAt(9) == '1')
						tmp.allowAccessCompletedOnboardingForMyJobs = true;
					if (permission2.charAt(10) == '1')
						tmp.allowAccessCompletedOnboardingForAllJobs = true;
					if (permission2.charAt(11) == '1')
						tmp.allowAccessCompletedOnboardingForDivision = true;
					if (permission2.charAt(120) == '1')
						tmp.allowUnassignIndividualDocuments = true;
				}
				tmp.allowManagingJobBoardsCriteriaAndProfiles = (allowManagingJobBoardsCriteriaAndProfiles);
				tmp.allowManagingJobBoardsCriteriaOnly = (allowManagingJobBoardsCriteriaOnly);
				tmp.displayTheFourDailyEmailProfileOption = (displayFourDailyEmailProfileOption);
				tmp.firstname = (rs.getString("firstname"));
				tmp.lastname = (rs.getString("lastname"));
				tmp.isTeamLeader = (isTeamLeader);
				tmp.isSuperUser = isSuperUser;
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			ChatbotUserData tmp = list.get(0);
			data.allowAssignOnboardingToNotLinkedJob = (tmp.allowAssignOnboardingToNotLinkedJob);
			data.allowManagingOnboarding = (tmp.allowAccessCompletedOnboarding);
			data.allowAccessCompletedOnboarding = (tmp.allowAccessCompletedOnboarding);
			data.allowAccessCompletedOnboardingForHires = (tmp.allowAccessCompletedOnboardingForHires);
			data.allowAccessCompletedOnboardingForPrimaryJobs = (tmp.allowAccessCompletedOnboardingForPrimaryJobs);
			data.allowAccessCompletedOnboardingForMyJobs = (tmp.allowAccessCompletedOnboardingForMyJobs);
			data.allowAccessCompletedOnboardingForAllJobs = (tmp.allowAccessCompletedOnboardingForAllJobs);
			data.allowAccessCompletedOnboardingForDivision = (tmp.allowAccessCompletedOnboardingForDivision);
			data.allowUnassignIndividualDocuments = (tmp.allowUnassignIndividualDocuments);
			data.allowManagingJobBoardsCriteriaAndProfiles = (tmp.allowManagingJobBoardsCriteriaAndProfiles);
			data.allowManagingJobBoardsCriteriaOnly = (tmp.allowManagingJobBoardsCriteriaOnly);
			data.displayTheFourDailyEmailProfileOption = (tmp.displayTheFourDailyEmailProfileOption);
			data.firstname = (tmp.firstname);
			data.lastname = (tmp.lastname);
			data.isTeamLeader = (tmp.isTeamLeader);
			data.isSuperUser = tmp.isSuperUser;
			HashMap<Long, String> notUsedHirePackage = getNotUsedHirePackages(teamid);
			if (notUsedHirePackage.size() > 0) {
				data.hasUnusedHirePackage = true;
				data.unusedHirePackagesName = notUsedHirePackage.entrySet().stream().map(e -> e.getValue()).collect(Collectors.joining(", ", "", ""));
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
//			String strTimeZone = getStrTimeZone(teamid);
//			TimeZone tz = TimeZone.getTimeZone(strTimeZone);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			sdf.setTimeZone(tz);
//			sdf2.setTimeZone(tz);
//			String dateStartStr = sdf.format(new java.util.Date());
//			String dateEndStr = dateStartStr + " 23:59:59";
//			java.util.Date dateStart = sdf.parse(dateStartStr);
//			java.util.Date dateEnd = sdf2.parse(dateEndStr);
//			sdf2.setTimeZone(TimeZone.getTimeZone("America/New_York"));
//			dateStartStr = sdf2.format(dateStart);
//			dateEndStr = sdf2.format(dateEnd);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStartStr = sdf.format(new java.util.Date());
			String dateEndStr = dateStartStr + " 23:59:59";
			dateStartStr = dateStartStr + " 00:00:00";
			Object[] params = new Object[] { teamid, webid, username, dateStartStr, dateEndStr };
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
			System.out.println(list.get(0));
			if (list.size() > 0) {
				resumeCounts = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resumeCounts;
	}
	
	public Long getSearchCountsToday(Long teamid, Long webid, String username) {
		long resumeCounts = 0;
		try {
			String sql = "select count(*) from tharvesteractivity where teamid = ? and webid=? and username=? and datecreated between str_to_date(?,'%Y-%m-%d %H:%i:%s') and str_to_date(?,'%Y-%m-%d %H:%i:%s')";
			JdbcTemplate jdbcTemplate = getMinerJdbcTemplate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStartStr = sdf.format(new java.util.Date());
			String dateEndStr = dateStartStr + " 23:59:59";
			dateStartStr = dateStartStr + " 00:00:00";
			System.out.println(dateStartStr);
			System.out.println(dateEndStr);
			Object[] params = new Object[] { teamid, webid, username, dateStartStr, dateEndStr };
			List<Long> list = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
				
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getLong(1);
				}
			});
			System.out.println(list.get(0));
			if (list.size() > 0) {
				resumeCounts = list.get(0);
			}
		} catch (Exception e) {
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
			if (i == 0)
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
	
	public HashMap<Long, String> getNotUsedHirePackages(Long teamid) {
		HashMap<Long, String> unusedPackageList = new HashMap<Long, String>();
		String sql = "select tt.id, tt.name from tonboarding_tab tt where tt.teamid = ?  and tt.deleted = 0 and tt.id<100 and tt.id not in (select t1.tabid from tonboardings_mapping t1, tonboardings t2 "
				+ " where t1.teamid=? and t1.docid=t2.id and t2.teamid = t1.teamid and (-1 = 0 or t2.DELETED = 0) ) order by upper(tt.name) asc";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid, teamid };
		List<Object[]> listData = jdbcTemplate.query(sql, params, new RowMapper<Object[]>() {
			
			@Override
			public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object[] data = new Object[2];
				data[0] = rs.getLong(1);
				data[1] = rs.getString(2);
				return data;
			}
		});
		for (int i = 0; i < listData.size(); i++) {
			Object[] data = listData.get(i);
			unusedPackageList.put((Long) data[0], (String) data[1]);
		}
		unusedPackageList.entrySet().stream().map(e -> "\"" + e.getKey() + "\":\"" + e.getValue().replace("\"", "\\\"") + "\"").collect(Collectors.joining(",", "{", "}"));
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
	
	
	@SuppressWarnings("deprecation")
	public ChatbotTagValue getCATTest(Long teamid, String tagName, String[] references) {
		ChatbotTagValue tagValue = new ChatbotTagValue();
		String tagType = "BINARY";
		Boolean passCATTest = null;

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
			String CAT_URL = "https://rsx.monster.com/query.ashx?q=java&rpcr=10038-50&mdatemaxage=788400&pagesize=20&ver=1.7&cat=" + URLEncoder.encode(decoded_password);
			try {
				Response catResponse = proxyClient.proxyAPI("GET", CAT_URL, null, new ProxyParameter[] { parameter }, null);
				String responseBody = catResponse.getBody();
				if (responseBody.indexOf("Resumes") > 0) {
					passCATTest = true;
				}
				else {
					passCATTest = false;
				}
			} catch (Exception e) {
				logService.log("debug", "SupportChatbot", teamid, 0L, null, e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(passCATTest!=null)
			tagValue.setValue(String.valueOf(passCATTest));
		tagValue.setTag(tagName);
		tagValue.setTagType(tagType);

		return tagValue;
	}
	
	public Boolean isRsxMonsterAlive(JobDivaSession jobDivaSession) {
		Long teamid = jobDivaSession.getTeamId();
		Long recruiterid = jobDivaSession.getRecruiterId();
		Boolean isRsxMonsterAlive = false;
		try {
			String CAT_URL = "https://rsx.monster.com/query.ashx";
			ProxyAPIDao proxyClient = new ProxyAPIDao();
			Response catResponse = proxyClient.proxyAPI("GET", CAT_URL, null, null, null);
			String responseBody = catResponse.getBody();
			System.out.println(responseBody);
			if(responseBody.indexOf("Monster")>0) {
				isRsxMonsterAlive = true;
			}
		}catch(Exception e) {
			logService.log("debug", "SupportChatbot", teamid, recruiterid, null, e.getMessage());
		}
		return isRsxMonsterAlive;
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
		String sql = "select nvl(active,0), nvl((datelastrun - DATE'1970-01-01') * 86400, 0), loginfailures, maxloginattempts from tspiderswebsites where teamid=? and upper(site)=upper(?)";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] { teamid, site };
		List<String> dateList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String status = "";
				int isActive = rs.getInt(1);
				long dateLastRun = rs.getLong(2);
				long loginfailures = rs.getLong(3);
				long maxloginattemps = rs.getLong(4);
				if (isActive == 0) {
					status = "INACTIVE";
				} else {
					if (loginfailures >= maxloginattemps) {
						status = "HALTED";
					} else {
						Date lastRunDate = new Date(dateLastRun);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(lastRunDate);
						calendar.add(Calendar.HOUR_OF_DAY, 2);
						if (calendar.getTime().compareTo(new java.util.Date()) < 0) {
							status = "NOT_WORKING";
						} else
							status = "WORKING";
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
		String[] referecens = { String.valueOf(machineNo) };
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
		String sql = "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? ";
		Object[] params = new Object[] { teamid };
		if (webid != null) {
			sql += " and a.webid=?";
			params = new Object[] { teamid, webid };
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
		Boolean isRsxMonsterAlive= false;
//		isRsxMonsterAlive = isRsxMonsterAlive(jobDivaSession);
		isRsxMonsterAlive = false;
		for (int i = 0; i < dataList.size(); i++) {
			Object[] data = dataList.get(i);
			webid = (Long) data[6];
			if (i == 0) {
				harvestStatus.name = (String) data[0];
				harvestStatus.active = (Long) data[1];
				harvestStatus.harvest = (Long) data[2];
				harvestStatus.webid = webid;
			}
			Long tmp_machineNo = (Long) data[5];
			if (tmp_machineNo != machineNo) {
				machineNo = tmp_machineNo;
				ChatbotHarvestMachine harvestMachine = new ChatbotHarvestMachine();
				String[] references = { String.valueOf(machineNo) };
				ChatbotTagValue machineIssue = hasMachineIssue(teamid, null, references);
				harvestMachine.machineNo = machineNo;
				harvestMachine.hasMachineIssue = machineIssue.getValue().equals("true");
				ChatbotTagValue isMachineInstalled = isMachineInstalled(teamid, null, references);
				harvestMachine.isMachineInstalled = isMachineInstalled.getValue().equals("true");
				harvestStatus.machines.add(harvestMachine);
			}
			ChatbotHarvestAccount harvestAccount = new ChatbotHarvestAccount();
			String accountName = (String) data[4];
			harvestAccount.name = accountName;
			harvestAccount.harvest = (Long) data[3];
			String[] references = { String.valueOf(webid), accountName };
			harvestAccount.machineNO = machineNo;
			harvestAccount.webid = webid;
			harvestAccount.websitename = harvestStatus.name;
			harvestAccount.status = getJobBoardStatus(teamid, null, references).getValue();
			if(harvestAccount.status!="INACTIVE") {
				harvestAccount.downloaddLimitPerDay = Long.valueOf(getDownloadLimitPerDay(teamid, null, references).getValue());
				harvestAccount.hasRecentResume = hasRecentResume(teamid, webid, accountName);
				harvestAccount.hasNotDownloadSessionStarted = hasNotDownloadSessionStarted(teamid, null, references).getValue().equals("true");
				harvestAccount.downloadStartTime = getDownloadStartTime(teamid, null, references).getValue();
				harvestAccount.hasOverLappingTime = hasOverLappingTime(teamid, null, references).getValue().equals("true");
				if(isRsxMonsterAlive) {
					harvestAccount.CATTest = getCATTest(teamid, null, references).getValue();
				} else {
					harvestAccount.CATTest = null;
				}
				harvestAccount.hasJobBoardCriteria = hasJobBoardSearchCriteria(teamid, null, references).getValue().equals("true");
				harvestAccount.resumeCountsToday = getResumeCountsToday(teamid, webid, accountName);
				harvestAccount.resumeSearchsToday = getSearchCountsToday(teamid, webid, accountName);
			}
			harvestStatus.accounts.add(harvestAccount);
		}
		return harvestStatus;
	}
	
	public List<ChatbotHarvestAccount> getHarvesetAccountStatus(JobDivaSession jobDivaSession, Long webid, Long machineNo) {
		List<ChatbotHarvestAccount> accountList = new ArrayList<ChatbotHarvestAccount>();
		String sql = "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? ";
		if (webid != null) {
			sql += " and a.webid = ?";
		}
		if (machineNo != null) {
			sql += " and a.machine_no = ?";
		}
		Long teamid = jobDivaSession.getTeamId();
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(teamid);
		if (webid != null) {
			params.add(webid);
		}
		if (machineNo != null) {
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
		Boolean isRsxMonsterAlive = false;
//		isRsxMonsterAlive = isRsxMonsterAlive(jobDivaSession);
		for (int i = 0; i < dataList.size(); i++) {
			Object[] data = dataList.get(i);
			ChatbotHarvestAccount harvestAccount = new ChatbotHarvestAccount();
			String accountName = (String) data[4];
			String websitename = (String) data[0];
			machineNo = (Long) data[5];
			webid = (Long) data[6];
			harvestAccount.name = accountName;
			harvestAccount.harvest = (Long) data[3];
			String[] references = { String.valueOf(webid), accountName };
			harvestAccount.machineNO = machineNo;
			harvestAccount.webid = webid;
			harvestAccount.websitename = websitename;
			harvestAccount.status = getJobBoardStatus(teamid, null, references).getValue();
			if(harvestAccount.status!="INACTIVE") {
				harvestAccount.downloaddLimitPerDay = Long.valueOf(getDownloadLimitPerDay(teamid, null, references).getValue());
				harvestAccount.hasRecentResume = hasRecentResume(teamid, webid, accountName);
				harvestAccount.hasNotDownloadSessionStarted = hasNotDownloadSessionStarted(teamid, null, references).getValue().equals("true");
				harvestAccount.downloadStartTime = getDownloadStartTime(teamid, null, references).getValue();
				harvestAccount.hasOverLappingTime = hasOverLappingTime(teamid, null, references).getValue().equals("true");
				if(isRsxMonsterAlive)
					harvestAccount.CATTest = getCATTest(teamid, null, references).getValue();
				else
					harvestAccount.CATTest = null;
				harvestAccount.hasJobBoardCriteria = hasJobBoardSearchCriteria(teamid, null, references).getValue().equals("true");
				harvestAccount.resumeCountsToday = getResumeCountsToday(teamid, webid, accountName);
				harvestAccount.resumeSearchsToday = getSearchCountsToday(teamid, webid, accountName);
			}
			accountList.add(harvestAccount);
		}
		return accountList;
	}
	
	public List<ChatbotVMSAccount> getVMSAccountsStatus(JobDivaSession jobDivaSession) {
		List<ChatbotVMSAccount> VMSAccountList = new ArrayList<ChatbotVMSAccount>();
		Long teamid = jobDivaSession.getTeamId();
		String sql = "select a.site, a.username, a.url, nvl(a.active,0), nvl(a.active_timesheet,0), nvl(a.active_submittal,0), nvl(a.loginfailures,0), nvl(a.maxloginattempts,0), to_char(a.datelastrun,'YYYY-MM-DD HH24:MI:ss'), b.computer_name, b.ip_address "
				+ " from tspiderswebsites a, tspidersmachinestats b where a.teamid = b.teamid(+) and a.teamid=? and a.site = b.site(+) and nvl(a.deleted, 0)=0 order by upper(a.site)";
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
		for (int i = 0; i < dataList.size(); i++) {
			Object[] data = dataList.get(i);
			String site = (String) data[0];
			String username = (String) data[1];
			String url = (String) data[2];
			Long isActive = (Long) data[3];
			Long isActiveTimesheet = (Long) data[4];
			Long isActiveSubmittal = (Long) data[5];
			Long loginfailures = (Long) data[6];
			Long maxloginattemps = (Long) data[7];
			String datelastrun = (String) data[8];
			String computer_name = (String) data[9];
			String ip_address = (String) data[10];
			if(computer_name==null)
				computer_name = "";
			if(ip_address==null)
				ip_address = "";
			ChatbotVMSAccount vmsAccount = new ChatbotVMSAccount();
			vmsAccount.site = site;
			vmsAccount.username = username;
			vmsAccount.active = isActive != 0;
			vmsAccount.activeTimesheet = isActiveTimesheet != 0;
			vmsAccount.activeSumittal = isActiveSubmittal != 0;
			vmsAccount.isHalted = loginfailures != 0 && (loginfailures >= maxloginattemps);
			vmsAccount.url = url;
			vmsAccount.teamid = teamid;
			vmsAccount.machineDetail = computer_name+ "(IP address:"+ip_address+")";
			if (datelastrun != null && !datelastrun.isEmpty()) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
					vmsAccount.datelastrun = sdf.parse(datelastrun);
				} catch (Exception e) {
				}
			}
			if (computer_name != null && ip_address != null) {
				if (computer_name.startsWith("w10_") && ip_address.startsWith("10.10")) {
					vmsAccount.onClientMachine = false;
				} else {
					vmsAccount.onClientMachine = true;
				}
			} else {
				vmsAccount.onClientMachine = null;
			}
			if (!vmsAccount.active) {
				vmsAccount.status = "inactive";
			} else {
				if (vmsAccount.isHalted) {
					vmsAccount.status = "halted";
				} else if (vmsAccount.datelastrun != null) {
					Date dateLastRun = vmsAccount.datelastrun;
					Date currentLocalDate = new Date();
					Calendar currentCalendar = Calendar.getInstance();
					currentCalendar.setFirstDayOfWeek(Calendar.MONDAY);
					currentCalendar.setTimeZone(TimeZone.getTimeZone(getStrTimeZone(teamid)));
					currentCalendar.setTime(currentLocalDate);
					int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
					// Coddler runs on weekdays from 9am to 9pm in local time
					if (currentHour < 9) {
						// make currentCalendar 9 pm yesterday
						currentCalendar.add(Calendar.DATE, -1);
						currentCalendar.set(Calendar.HOUR_OF_DAY, 21);
						currentCalendar.set(Calendar.MINUTE, 0);
					} else if (currentHour >= 21) {
						// make currentCalendar 9 pm
						currentCalendar.set(Calendar.HOUR_OF_DAY, 21);
						currentCalendar.set(Calendar.MINUTE, 0);
					}
					if (currentCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || currentCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
						currentCalendar.set(Calendar.HOUR_OF_DAY, 21);
						currentCalendar.set(Calendar.MINUTE, 0);
					}
					currentCalendar.add(Calendar.HOUR_OF_DAY, -2);
					if (currentCalendar.getTime().after(dateLastRun)) {
						vmsAccount.status = "not working";
					} else {
						vmsAccount.status = "working";
					}
				} else {
					vmsAccount.status = "never run";
				}
			}
			VMSAccountList.add(vmsAccount);
		}
		return VMSAccountList;
	}
	
	public List<ChatbotHarvestMachineStatus> getHarvestMachineStatus(JobDivaSession jobDivaSession) {
		List<ChatbotHarvestMachineStatus> machineList = new ArrayList<ChatbotHarvestMachineStatus>();
		Long teamid = jobDivaSession.getTeamId();
		String sql = "SELECT b.websitename, b.active, b.harvest, a.harvest, a.username, a.machine_no, a.webid FROM twebsites_detail a, twebsites b where a.webid = b.id and coalesce(deleted,0) = 0 and a.teamid=? order by machine_no, websitename, username";
		Object[] params = new Object[] { teamid };
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
		for (int i = 0; i < dataList.size(); i++) {
			Object[] data = dataList.get(i);
			Long tmp_machineNo = (Long) data[5];
			if (tmp_machineNo != machineNo) {
				if (machineStatus != null)
					machineList.add(machineStatus);
				machineNo = tmp_machineNo;
				machineStatus = new ChatbotHarvestMachineStatus();
				machineStatus.teamid = teamid;
				// machineStatus.accounts = new
				// ArrayList<ChatbotHarvestAccount>();
				String[] references = { String.valueOf(machineNo) };
				ChatbotTagValue machineIssue = hasMachineIssue(teamid, null, references);
				machineStatus.machineNo = machineNo;
				machineStatus.hasMachineIssue = machineIssue.getValue().equals("true");
				ChatbotTagValue isMachineInstalled = isMachineInstalled(teamid, null, references);
				machineStatus.isMachineInstalled = isMachineInstalled.getValue().equals("true");
			}
		}
		return machineList;
	}
	
	public List<ChatbotVMSType> getChatbotVMSTypes(JobDivaSession jobDivaSession) {
		String sql = "select vms_name, coalesce(job,0), coalesce(timesheet,0), coalesce(submittal,0) from tchatbotsupport_vms_types";
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = null;
		List<ChatbotVMSType> datalist = jdbcTemplate.query(sql, params, new RowMapper<ChatbotVMSType>() {
			
			@Override
			public ChatbotVMSType mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotVMSType vms_type = new ChatbotVMSType();
				vms_type.vms_name = rs.getString(1);
				vms_type.hasJobCoddler = rs.getLong(2) == 1L;
				vms_type.hasTimesheetCoddler = rs.getLong(3) == 1L;
				vms_type.hasSubmittalCoddler = rs.getLong(4) == 1L;
				return vms_type;
			}
		});
		return datalist;
	}
	
	public boolean setChatbotVMSType(JobDivaSession jobDivaSession, String vms_name, Boolean hasJobCoddler, Boolean hasTimesheetCoddler, Boolean hasSubmittalCoddler) {
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		ArrayList<Object> params = new ArrayList<Object>();
		String sqlSt1 = "insert into tchatbotsupport_vms_types (vms_name ";
		String sqlSt2 = " values (? ";
		String sqlSt3 = " on Duplicate key update  ";
		params.add(vms_name);
		if (hasJobCoddler != null) {
			params.add(hasJobCoddler);
			sqlSt1 += ", job ";
			sqlSt2 += ", ? ";
			sqlSt3 += " job = ? ";
		}
		if (hasTimesheetCoddler != null) {
			params.add(hasTimesheetCoddler);
			sqlSt1 += ", timesheet ";
			sqlSt2 += ", ? ";
			if (hasJobCoddler != null)
				sqlSt3 += " , ";
			sqlSt3 += " timesheet = ? ";
		}
		if (hasSubmittalCoddler != null) {
			params.add(hasSubmittalCoddler);
			sqlSt1 += ", submittal ";
			sqlSt2 += ", ? ";
			if (hasJobCoddler != null || hasTimesheetCoddler != null)
				sqlSt3 += " , ";
			sqlSt3 += " submittal = ? ";
		}
		if (hasJobCoddler != null) {
			params.add(hasJobCoddler);
		}
		if (hasTimesheetCoddler != null) {
			params.add(hasTimesheetCoddler);
		}
		if (hasSubmittalCoddler != null) {
			params.add(hasSubmittalCoddler);
		}
		String sql = sqlSt1 + ")" + sqlSt2 + ")" + sqlSt3;
		jdbcTemplate.update(sql, params.toArray());
		return true;
	}
	
	public boolean deleteChatbotVMSType(JobDivaSession jobDivaSession, String vms_name) {
		String sql = "delete from tchatbotsupport_vms_types where vms_name = ?";
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = { vms_name };
		jdbcTemplate.update(sql, params);
		return true;
	}
	
	public boolean insertChatbotVMSType(JobDivaSession jobDivaSession) {
		String sql = "select vms_name, coalesce(job,0), coalesce(timesheet,0), coalesce(submittal,0) from tchatbotsupport_vms_types order by upper(vms_name)";
		JdbcTemplate jdbcTemplate = getCentralJdbcTemplate();
		Object[] params = null;
		jdbcTemplate.query(sql, params, new RowMapper<ChatbotVMSType>() {
			
			@Override
			public ChatbotVMSType mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotVMSType vms_type = new ChatbotVMSType();
				vms_type.vms_name = rs.getString(1);
				vms_type.hasJobCoddler = rs.getLong(2) == 1L;
				vms_type.hasSubmittalCoddler = rs.getLong(3) == 1L;
				vms_type.hasTimesheetCoddler = rs.getLong(4) == 1L;
				return vms_type;
			}
		});
		return true;
	}
	
	public String getStrTimeZone(Long teamid) {
		String strTimeZone = "America/New_York";
		Object[] params = new Object[] { teamid };
		String sql = "select strtimezone from tteam where id= ?";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		List<String> dataList = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		if (dataList.size() > 0) {
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
				default:
					break;
			}
		}
		return tagValue;
	}

	public ChatbotCandidatePackges getCandidatePackges(JobDivaSession jobDivaSession,String email) {
		long teamid = jobDivaSession.getTeamId();
		String sql = "select id , firstname ,lastname from tcandidate where upper(email) = ? and teamid = ? ";
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Object[] params = new Object[] {email.toUpperCase(),teamid};
		List<ChatbotCandidatePackges> candidates = jdbcTemplate.query(sql, params, new RowMapper<ChatbotCandidatePackges>() {
			
			@Override
			public ChatbotCandidatePackges mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotCandidatePackges candidate = new ChatbotCandidatePackges();
				candidate.candidateID = rs.getLong(1);
				candidate.candidateName = rs.getString(2)+ " "+ rs.getString(3);

				return candidate;
			}
		});
		//no candidate with this email
		if (candidates.size() ==0)
			return new ChatbotCandidatePackges();
		
		//candidate exist count the packges
		ChatbotCandidatePackges candidate = candidates.get(0);
		Long candId = candidate.candidateID;

		String s2 = "select count(*) from tpreonboardings where candidateid = ? and teamid = ? and nvl(deleted,0)=0";
		jdbcTemplate = getJdbcTemplate();
		Object[] params1 = new Object[] {candId,teamid};

		candidates = jdbcTemplate.query(s2, params1, new RowMapper<ChatbotCandidatePackges>() {
			
			@Override
			public ChatbotCandidatePackges mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotCandidatePackges candidate = new ChatbotCandidatePackges();
				candidate.activepackges = rs.getInt(1);
				return candidate;
			}
		});
		
		candidate.activepackges = candidates.get(0).activepackges;
		return candidate;		

	}

	public boolean emailAlert(JobDivaSession jobDivaSession, ChatbotEmailAlert email){

	
		String [] emails = email.sendTo.split(",");

		for(int i = 0 ; i < emails.length; i++ ){
			String to = emails[i].trim();
			SMTPServer mailserver = new SMTPServer();
			mailserver.setContentType(SMTPServer.CONTENT_TYPE_HTML);
			mailserver.setHost(appProperties.getSmtpServerLocation());
			System.out.println((email.body));
			try{
				mailserver.sendMail(to, email.from, email.cc, email.subject,email.body);
			}
			catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace(System.out);
				return false;
			}
		}

		return true;

		
	}
}
