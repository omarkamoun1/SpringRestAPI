package com.jobdiva.api.dao.job;

import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;

@Component
public class JobAttachmentDao extends AbstractJobDivaDao {
	
	public void addJobAttachment(long id, long teamid, long jobid, String description, String fileName, byte[] fileData, Long attachmentType) {
		String sqlInsert = "INSERT INTO TRFQATTACHMENTS " //
				+ " (ID, TEAMID, RFQID, DESCRIPTION, FILENAME, THEFILE, DATECREATED , ATTACHMENT_TYPE  ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,? , ?, ?, ? ) ";//
		Timestamp currentdate = new Timestamp(System.currentTimeMillis());
		Object[] params = new Object[] { id, teamid, jobid, description, fileName, fileData, currentdate, attachmentType };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
