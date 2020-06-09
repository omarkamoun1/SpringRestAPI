package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jobdiva.api.model.JobContact;

public class JobContactRowMapper implements RowMapper<JobContact> {
	
	@Override
	public JobContact mapRow(ResultSet rs, int rowNum) throws SQLException {
		JobContact jobContact = new JobContact();
		jobContact.setCustomerId(rs.getLong("CUSTOMERID"));
		jobContact.setTeamId(rs.getLong("TEAMID"));
		jobContact.setRfqId(rs.getLong("RFQID"));
		jobContact.setShowonJob(rs.getBoolean("SHOWONJOB"));
		jobContact.setRoleId(rs.getInt("ROLEID"));
		return jobContact;
	}
}
