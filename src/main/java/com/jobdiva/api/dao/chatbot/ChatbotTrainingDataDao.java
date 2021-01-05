package com.jobdiva.api.dao.chatbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.mapper.ChatbotQuestionRowMapper;
import com.jobdiva.api.mapper.ChatbotSocialQuestionRowMapper;
import com.jobdiva.api.model.authenticate.JobDivaSession;
import com.jobdiva.api.model.chatbot.ChatbotAnswer;
import com.jobdiva.api.model.chatbot.ChatbotQuestion;
import com.jobdiva.api.model.chatbot.ChatbotRecruiterData;
import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;
import com.jobdiva.api.model.chatbot.ChatbotUserData;
import com.jobdiva.api.model.chatbot.ChatbotVisibility;

@Component
public class ChatbotTrainingDataDao extends AbstractJobDivaDao {
	
	public List<ChatbotQuestion> getQuestions(JobDivaSession jobDivaSession) {
		//
		logger.info("\n\n IN GET QUESTIONS\n\n");
		//
		String sql = "select a.id, a.text, b.questionid, b.text, " //
				+ " a.ques_ref_id, a.keywords, a.show_as_suggestion, " //
				+ " a.ques_tag, a.substitute, a.lookup_tag " //
				+ " from tchatbotsupport_question a, tquevariants_support b " //
				+ " where a.id = b.questionid " //
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
			}
			q.addVariation(list.get(i).getVariations().get(0));
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
		sql = "select permission, firstname, lastname, leader " //
				+ " from trecruiter "//
				+ " where groupid = ? and ID = ? ";
		//
		jdbcTemplate = getJdbcTemplate();
		params = new Object[] { teamid, recruiterid };
		//
		list = jdbcTemplate.query(sql, params, new RowMapper<ChatbotUserData>() {
			
			@Override
			public ChatbotUserData mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatbotUserData tmp = new ChatbotUserData();
				long leadervalue = rs.getLong(1);
				boolean allowManagingJobBoardsCriteriaAndProfiles = false;
				// if (( 0!=(leadervalue & (1<<(i-1))) || leadervalue==0) &&
				// !(i==2 &&
				// accessControlSet.contains("hide_VMS"))){%>true<%}else{%>false<%}
				String accessControlSet = "";
				int i = 1;
				if ((0 != (leadervalue & (1 << (i - 1))) || leadervalue == 0) && !(i == 2 && accessControlSet.contains("hide_VMS"))) {
					allowManagingJobBoardsCriteriaAndProfiles = true;
				}
				i = 2;
				boolean allowManagingJobBoardsCriteriaOnly = false;
				if ((0 != (leadervalue & (1 << (i - 1))) || leadervalue == 0) && !(i == 2 && accessControlSet.contains("hide_VMS"))) {
					allowManagingJobBoardsCriteriaOnly = true;
				}
				tmp.setAllowManagingJobBoardsCriteriaAndProfiles(allowManagingJobBoardsCriteriaAndProfiles);
				tmp.setAllowManagingJobBoardsCriteriaOnly(allowManagingJobBoardsCriteriaOnly);
				tmp.setFirstname(rs.getString("firstname"));
				tmp.setLastname(rs.getString("lastname"));
				if (rs.getLong("leader") == 1296L) {
					tmp.setTeamLeader(true);
				} else {
					tmp.setTeamLeader(false);
				}
				return tmp;
			}
		});
		//
		if (list.size() > 0) {
			data.setAllowManagingJobBoardsCriteriaAndProfiles(list.get(0).isAllowManagingJobBoardsCriteriaAndProfiles());
			data.setAllowManagingJobBoardsCriteriaOnly(list.get(0).isAllowManagingJobBoardsCriteriaOnly());
			data.setFirstname(list.get(0).getFirstname());
			data.setLastname(list.get(0).getLastname());
			data.setTeamLeader(list.get(0).isTeamLeader());
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
}
