package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.notification.NotificationDao;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class NotificationService extends AbstractService {

	
	@Autowired
	NotificationDao		notificationDao;

	
	public Boolean registerNotification(JobDivaSession jobDivaSession, String deviceId, String token, String deviceType,String appVersion) throws Exception {
		//
		try {
			Boolean result = notificationDao.registerNotification(jobDivaSession, deviceId, token, deviceType, appVersion);
			//
			notificationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "registerNotification", "Register Successful ");
			//
			return result;
			//
		} catch (Exception e) {
			//
			notificationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "registerNotification", "Register Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
		//
	}


	public Boolean unregisterNotification(JobDivaSession jobDivaSession, String deviceId)throws Exception {
		//
		try {
			Boolean result = notificationDao.unregisterNotification(jobDivaSession, deviceId);
			//
			notificationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "unregisterNotification", "Unregister Successful ");
			//
			return result;
			//
		} catch (Exception e) {
			//
			notificationDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "unregisterNotification", "Unregister Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
			//
		}
		//
	}
}
