package com.jobdiva.api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.chatbot.ChatbotQuestion;

public class ChatbotQuestionRowMapper implements RowMapper<ChatbotQuestion> {
	
	@Override
	public ChatbotQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChatbotQuestion question = new ChatbotQuestion();
		question.setQuestion(rs.getString("text"));
		question.setKeywords(rs.getString("keywords"));
		question.addVariation(rs.getString("variation"));
		return question;
	}
}
