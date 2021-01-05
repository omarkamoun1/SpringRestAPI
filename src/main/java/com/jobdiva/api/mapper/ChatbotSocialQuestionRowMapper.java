package com.jobdiva.api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.chatbot.ChatbotSocialQuestion;

public class ChatbotSocialQuestionRowMapper implements RowMapper<ChatbotSocialQuestion> {
	
	@Override
	public ChatbotSocialQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChatbotSocialQuestion question = new ChatbotSocialQuestion();
		question.setReferenceID(rs.getInt("id"));
		question.setAnswer(rs.getString("answer"));
		question.setQuestion(rs.getString("question"));
		return question;
	}
}
