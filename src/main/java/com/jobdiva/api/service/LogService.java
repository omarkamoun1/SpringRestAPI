package com.jobdiva.api.service;

import org.springframework.stereotype.Service;

import com.jobdiva.log.JobDivaLog;
import com.jobdiva.log.RecordType;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class LogService {
	
	public void log(String type, String entity, Long teamId, Long userId, Long recordId, String message) {
		RecordType recordType = RecordType.Other;
		if (entity != null) {
			if (entity.equalsIgnoreCase("SupportChatbot"))
				recordType = RecordType.SupportChatbot;
			else if (entity.equalsIgnoreCase("ApplicantChatbot"))
				recordType = RecordType.ApplicantChatbot;
			else if (entity.equalsIgnoreCase("DivaChat"))
				recordType = RecordType.Divachat;
		}
		//
		//
		type = type == null ? "info" : type;
		//
		if (type.equalsIgnoreCase("debug")) {
			JobDivaLog.debug(teamId, userId, recordId, recordType, message);
		} else if (type.equalsIgnoreCase("error")) {
			JobDivaLog.error(teamId, userId, recordId, recordType, message);
		} else {
			JobDivaLog.info(teamId, userId, recordId, recordType, message);
		}
		//
	}
}
