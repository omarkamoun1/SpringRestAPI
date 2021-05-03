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
	
	public Boolean assignUserToJob(JobDivaSession jobDivaSession, Long rfqid, Long recruiterid, List<Long> roleIds, Integer jobstatus) throws Exception{
		
		//
		String sqlInsert = "INSERT INTO tRecruiterRFQ (rfqid, recruiterid,teamid,recruiter,lead_recruiter,sales,lead_sales,rec_email,jobstatus) VALUES (?,?,?,?,?,?,?,?,?)";
		//
		Object[] params = new Object[] { rfqid, recruiterid, jobDivaSession.getTeamId(),roleIds.contains(997l)?1:0,roleIds.contains(998l)?1:0,roleIds.contains(999l)?1:0,roleIds.contains(996l)?1:0,0,jobstatus};
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		jdbcTemplate.update(sqlInsert, params);
		//
		long customerID = 0;
		//
        String sqlStr = "select id from tcustomer where teamid=? and ifrecruiterthenid=?";
        //
        params = new Object[] { jobDivaSession.getTeamId(), recruiterid};
        //
        List<Long> customerIDs = jdbcTemplate.query(sqlStr, params, new RowMapper<Long>() {
			
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id");
			}
		});
        if (customerIDs!=null && customerIDs.size()>0) {
          customerID = customerIDs.get(0);
        }
        
        if(customerID>0){
        	int RoleID = 950;
	        if (roleIds.contains(996l)) RoleID = 996; //lead sales
	        else if (roleIds.contains(999l)) RoleID = 999; //sales
	        else if (roleIds.contains(998l)) RoleID = 998; //lead recruiter
	        else if (roleIds.contains(997l)) RoleID = 997; //recruiter
	        
	        sqlInsert = "INSERT INTO trfq_customers (teamid,rfqid,customerid,roleID,showonjob) VALUES (?,?,?,?,0)";
    	    //
	        params = new Object[] {jobDivaSession.getTeamId(),rfqid,customerID,RoleID};
            //
	        jdbcTemplate.update(sqlInsert, params);
	        //
        }
        
        // Sync
        sqlInsert = "UPDATE trfq SET sync_required=2 Where id=?";
        params = new Object[] {rfqid};
        jdbcTemplate.update(sqlInsert, params);
        //
        roleIds.remove(996l); //lead sales
        roleIds.remove(999l); //sales
        roleIds.remove(998l); //lead recruiter
        roleIds.remove(997l); //recruiter
        if(roleIds.size()>0) { // flexible user roles
            for(int i=0; i < roleIds.size();i++) {	
            sqlInsert = "insert into TRECRUITERRFQ_ROLES values(?,?,?,?,sysdate)";				
			//
			params = new Object[] {rfqid,jobDivaSession.getTeamId(),recruiterid,roleIds.get(i)};
			//
			jdbcTemplate.update(sqlInsert, params);
			//
          }
        }
       return true;
	}
}
