package com.jobdiva.api.dao.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.JobUserSimple;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class JobUserDao extends AbstractJobDivaDao {
	
	
	public List<JobUserSimple> getAllJobUsers(JobDivaSession jobDivaSession, Long jobId) {
		
			String sql = "Select ID,NVL(T1.Recruiterid,0) as rfqRecruiter,a.Firstname,a.Lastname,a.email," +
			    	"a.rec_email, a.workphone, a.leader, NVL(T1.lead_recruiter,0), NVL(T1.sales,0), " +
			    	"NVL(T1.lead_sales,0),NVL(T1.recruiter,0),  nvl(a.user_options, 0), a.active  " +
			    	" From tRecruiter a," +
			    	" (select * From trecruiterrfq where rfqid= ? ) T1" +
			    	" Where Groupid= ? and active=1 and id<>688 and bitand(leader, 4096+16384+2097152+32768)=0 " +
			    	"   And T1.RecruiterId(+)=ID " +
			    	" Order by upper(Lastname),upper(Firstname)";
			Object[] params = new Object[] { jobId, jobDivaSession.getTeamId() };
			//
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			//
			//
			List<JobUserSimple> users = jdbcTemplate.query(sql, params, new RowMapper<JobUserSimple>() {
				
				@Override
				public JobUserSimple mapRow(ResultSet rs, int rowNum) throws SQLException {
					JobUserSimple jobUser = new JobUserSimple();
				    jobUser.setRecruiterId(rs.getLong("ID"));	 
				    jobUser.setFirstName(rs.getString("Firstname"));
		    		jobUser.setLastName(rs.getString("Lastname"));
					return jobUser;
				}
			});
			//
			return users;
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
