package com.jobdiva.api.dao.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jobdiva.api.dao.AbstractJobDivaDao;
import com.jobdiva.api.model.Notification;
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

	public Boolean unregisterNotification(JobDivaSession jobDivaSession, String deviceId) throws Exception {
		
		String sql_stmt = "delete from trecruiter_devicetoken where recruiterid=? ";
		if(deviceId!=null && deviceId.trim().length()>0){
			sql_stmt+=" and deviceid=? ";
		}
		//
		ArrayList<Object> paramList = new ArrayList<Object>();
		//
		paramList.add(jobDivaSession.getRecruiterId());
		//
		if(deviceId!=null && deviceId.trim().length()>0){
			paramList.add(deviceId);
		}
		//
		Object[] params = paramList.toArray();
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		//
		int res=jdbcTemplate.update(sql_stmt,params);
		
		if (res>0) {
			
			return true;
			
		}else{
			
			return false;
		} 
	}

	public List<Notification> getUserNotifications(JobDivaSession jobDivaSession) throws Exception {
	
		String sql="select * from (select ntype, ncontent, dateissued, isread, relatedid, id from tapp_notifications where (recruiterid=? or email=?) and id is not null order by dateissued desc) where rownum<21";
		//
		Object[] params = new Object[] { jobDivaSession.getRecruiterId(),jobDivaSession.getUserName()};
		//
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
	    //
		List<Notification> notifications = jdbcTemplate.query(sql, params, new RowMapper<Notification>() {
			
			@Override
			public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
				Notification notification=new Notification();
				notification.setType(rs.getString(1));
				notification.setContent(rs.getString(2));
				if(rs.getTimestamp(3)!=null){
					Calendar myCal = new GregorianCalendar();
					myCal.setTime(rs.getTimestamp(3));
					notification.setDate(myCal);
				}
				notification.setRead(rs.getBoolean(4));
				notification.setRelatedid(rs.getString(5));
				notification.setId(rs.getString(6));
				notification.setEmail(jobDivaSession.getUserName());
				notification.setRecuiterid(jobDivaSession.getRecruiterId());
				return notification;
			}
		});
		//
		return notifications;
		
	}

}
