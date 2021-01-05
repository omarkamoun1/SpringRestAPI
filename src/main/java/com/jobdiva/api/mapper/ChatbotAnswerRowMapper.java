package com.jobdiva.api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.chatbot.ChatbotAnswer;

public class ChatbotAnswerRowMapper implements RowMapper<ChatbotAnswer> {
	
	@Override
	public ChatbotAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChatbotAnswer answer = new ChatbotAnswer();
		answer.setAnswer(rs.getString("answer"));
		answer.setQuestionID(rs.getInt("questionid"));
		return answer;
	}
}
