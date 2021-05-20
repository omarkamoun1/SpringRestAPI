package com.jobdiva.api.dao.notification;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Component
public class NotificationDao extends AbstractJobDivaDao  {

	public Boolean registerNotification(JobDivaSession jobDivaSession, String deviceId, String token, String deviceType, String appVersion) throws Exception {
		
		String sql_log="insert into tapp_loginrecord (recruiterid, recruiteremail, token, devicetype, appversion, teamid, deviceid) values (?, ?, ?, ?, ?, ?, ?) ";
		//
		Object[] params = new Object[] { jobDivaSession.getRecruiterId(),jobDivaSession.getUserName(), token, deviceType, appVersion, jobDivaSession.getTeamId(), deviceId};
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
	
		jdbcTemplate.update(sql_log,params);
		
		String sql_stmt = "update trecruiter_devicetoken set recruiterid=?, recruiteremail=?, token=?, devicetype=?, appversion=?, teamid=? where deviceid=? ";
		//
		int res=jdbcTemplate.update(sql_stmt,params);
		//
		if(res==0) {
			//
			sql_stmt="insert into trecruiter_devicetoken (recruiterid, recruiteremail, token, devicetype, appversion, teamid, deviceid) values (?, ?, ?, ?, ?, ?, ?) ";
			//
			res=jdbcTemplate.update(sql_stmt,params);
		}
		if(res>0){
	
			return true;
		}
		else{
			
			return false;
		}
	}

}
