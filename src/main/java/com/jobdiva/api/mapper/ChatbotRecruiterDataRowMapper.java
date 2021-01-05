package com.jobdiva.api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.chatbot.ChatbotRecruiterData;

public class ChatbotRecruiterDataRowMapper implements RowMapper<ChatbotRecruiterData> {
	
	@Override
	public ChatbotRecruiterData mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChatbotRecruiterData data = new ChatbotRecruiterData();
		data.setId(rs.getInt("id"));
		data.setFirstName(rs.getString("first_name"));
		data.setLastName(rs.getString("last_name"));
		data.setEmail(rs.getString("email"));
		data.setTeamid(rs.getLong("teamid"));
		return data;
	}
}
