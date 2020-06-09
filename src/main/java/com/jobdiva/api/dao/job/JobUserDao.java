package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.JobUser;

@Component
public class JobUserDao extends AbstractJobDivaDao {
	
	public List<JobUser> getJobUsers(Long jobId, Long teamId) {
		return getJobUsers(jobId, teamId, null);
	}
	
	public List<JobUser> getJobUsers(Long jobId, Long teamId, Long recruiterId) {
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		return getJobUsers(jdbcTemplate, jobId, teamId, recruiterId);
	}
	
	public static List<JobUser> getJobUsers(JdbcTemplate jdbcTemplate, Long jobId, Long teamId, Long recruiterId) {
		String sql = " Select "//
				+ " a.RFQID, "//
				+ " a.RECRUITERID, "//
				+ " a.TEAMID , "//
				+ " a.REC_EMAIL, " //
				+ " a.LEAD_RECRUITER , " //
				+ " a.SALES ," //
				+ " a.LEAD_SALES, "//
				+ " a.RECRUITER , "//
				+ " a.JOBSTATUS ,"//
				//
				+ " b.FIRSTNAME ,"//
				+ " b.LASTNAME, "//
				+ " b.WORKPHONE ,"//
				+ " b.REC_EMAIL_STATUS ,"//
				+ " b.EMAIL " //
				//
				+ " FROM TRECRUITERRFQ a , TRECRUITER b " //
				+ " WHERE " //
				+ " a.RECRUITERID = b.ID  " //
				+ " AND a.RFQID = ?  AND a.TEAMID = ? ";
		//
		if (recruiterId != null)
			sql += " AND a.RECRUITERID = ? ";
		//
		Object[] params = recruiterId != null ? new Object[] { jobId, teamId, recruiterId } : new Object[] { jobId, teamId };
		//
		//
		List<JobUser> list = jdbcTemplate.query(sql, params, new RowMapper<JobUser>() {
			
			@Override
			public JobUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				try {
					JobUser jobUser = new JobUser();
					jobUser.setRfqId(rs.getLong("RFQID"));
					jobUser.setRecruiterId(rs.getLong("RECRUITERID"));
					jobUser.setTeamId(rs.getLong("TEAMID"));
					jobUser.setReceiveEmail(rs.getBoolean("REC_EMAIL"));
					jobUser.setLeadRecruiter(rs.getBoolean("LEAD_RECRUITER"));
					jobUser.setSales(rs.getBoolean("SALES"));
					jobUser.setLeadSales(rs.getBoolean("LEAD_SALES"));
					jobUser.setRecruiter(rs.getBoolean("RECRUITER"));
					jobUser.setJobStatus(rs.getInt("JOBSTATUS"));
					jobUser.setFirstName(rs.getString("FIRSTNAME"));
					jobUser.setLastName(rs.getString("LASTNAME"));
					jobUser.setPhone(rs.getString("WORKPHONE"));
					jobUser.setEmail(rs.getString("EMAIL"));
					jobUser.setRecEmailStatus(rs.getInt("REC_EMAIL_STATUS"));
					return jobUser;
				} catch (Exception e) {
					System.err.println(e);
				}
				return null;
			}
		});
		return list;
	}
	
	public void insertJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "INSERT INTO TRECRUITERRFQ " //
				+ " (RFQID, RECRUITERID, TEAMID, REC_EMAIL, LEAD_RECRUITER, SALES, LEAD_SALES, RECRUITER, JOBSTATUS ) " //
				+ " VALUES " //
				+ "(?, ?, ? ,? ,? , ? ,? ,? , ? ) ";//
		Object[] params = new Object[] { jobid, userId, teamid, rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void updateJobUser(long jobid, Long userId, long teamid, boolean rec_email, Boolean isLeadRecruiter, Boolean isSale, Boolean isLeadSales, Boolean isRecruiter, Integer status) {
		String sqlInsert = "UPDATE  TRECRUITERRFQ " //
				+ " SET REC_EMAIL = ? , LEAD_RECRUITER = ? , SALES = ? , LEAD_SALES = ?, RECRUITER = ? , JOBSTATUS = ?   " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { rec_email, isLeadRecruiter, isSale, isLeadSales, isRecruiter, status, jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
	
	public void deletJobuser(Long jobid, Long userId, Long teamid) {
		String sqlInsert = "DELETE FROM  TRECRUITERRFQ " //
				+ " WHERE RFQID = ? AND  RECRUITERID = ? AND TEAMID = ? ";//
		Object[] params = new Object[] { jobid, userId, teamid };
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
	}
}
