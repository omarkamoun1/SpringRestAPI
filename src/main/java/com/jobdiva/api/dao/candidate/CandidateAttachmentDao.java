package com.jobdiva.api.dao.candidate;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.axelon.shared.Zipper;
import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class CandidateAttachmentDao extends AbstractJobDivaDao {
	
	public Long uploadCandidateAttachment(JobDivaSession jobDivaSession, Long candidateid, String name, String filename, byte[] filecontent, Integer attachmenttype, String description) throws Exception {
		//
		// data checking
		StringBuffer message = new StringBuffer();
		if (filename.trim().length() == 0)
			message.append("Error: filename is empty. ");
		//
		if (candidateid == null)
			message.append("Error: Invalid candidate ID" + candidateid + "). ");
		//
		//
		if (message.length() > 0) {
			throw new Exception("Parameter Check Failed \r\n" + message.toString());
		}
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		String qry = "select candattachmentid.nextval as ID from dual";
		List<Long> list = jdbcTemplate.query(qry, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
		});
		long id = list != null && list.size() > 0 ? list.get(0) : -1;
		//
		String sqlInsert = "insert into tcandidate_attachments(id,teamid,candidateid,description,filename,att_type,lc_id,attachment_type, remark,uploadedbyid) " //
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		paramList.add(id);
		paramList.add(jobDivaSession.getTeamId());
		paramList.add(candidateid);
		paramList.add(name);
		paramList.add(filename);
		paramList.add(0);
		paramList.add(0);
		paramList.add(attachmenttype);
		paramList.add(description != null ? description.trim() : "");
		paramList.add(jobDivaSession.getRecruiterId());
		//
		Object[] parameters = paramList.toArray();
		jdbcTemplate.update(sqlInsert, parameters);
		//
		//
		//
		String sqlInsertAtt = "insert into tcandidate_attachments_blob(id, teamid, thefile) values(?,?,?) ";
		JdbcTemplate attachmentJdbcTemplate = jobDivaConnectivity.getAttachmentJdbcTemplate();
		;
		attachmentJdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sqlInsertAtt);
				ps.setLong(1, id);
				ps.setLong(2, jobDivaSession.getTeamId());
				//
				byte[] zipped;
				try {
					zipped = Zipper.zipIt(filecontent);
					ps.setBinaryStream(3, new ByteArrayInputStream(zipped), zipped.length);
				} catch (Exception e) {
				}
				return ps;
			}
		});
		//
		return id;
	}
}
