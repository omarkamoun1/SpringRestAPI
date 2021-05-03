package com.jobdiva.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.jobdiva.api.dao.event.EventDao;
import com.jobdiva.api.model.EventNotification;
import com.jobdiva.api.model.Timezone;
import com.jobdiva.api.model.authenticate.JobDivaSession;

@Service
public class EventService extends AbstractService {
	
	@Autowired
	EventDao eventDao;
	
	public Long createTask(JobDivaSession jobDivaSession, String subject, Date duedate, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid, String description, Boolean isprivate)
			throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long eventId = eventDao.createTask(jobDivaSession, subject, duedate, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
						//
						eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createTask", "Create Successful Task Id(" + eventId + ") ");
						//
						return eventId;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createTask", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Long updateTask(JobDivaSession jobDivaSession, Long taskId, String subject, Date duedate, Integer duration, Long assignedtoid, Long assignedbyid, Integer tasktype, Integer percentagecompleted, Long contactid, Long candidateid,
			String description, Boolean isprivate) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long eventId = eventDao.updateTask(jobDivaSession, taskId, subject, duedate, duration, assignedtoid, assignedbyid, tasktype, percentagecompleted, contactid, candidateid, description, isprivate);
						//
						eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateTask", "Update Successful Task Id(" + eventId + ") ");
						//
						return eventId;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateTask", "update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Long createEvent(JobDivaSession jobDivaSession, String title, Integer eventType, Integer optional, Date eventdate, Integer priotity, Integer duration, Long reminder, Long status1, EventNotification eventNotification, Timezone timezone,
			Long leadtime, Long lagTime, Boolean privateEvent, Integer repeatTimes, Boolean participationOptional, String location, String note, Long customerId, List<Long> opportunityIds, List<Long> recruiterids) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Long>() {
				
				@Override
				public Long doInTransaction(TransactionStatus status) {
					try {
						//
						Long eventId = eventDao.createEvent(jobDivaSession, title, eventType, optional, eventdate, priotity, duration, reminder, status1, eventNotification, timezone, leadtime, lagTime, privateEvent, participationOptional, location,
								note, customerId, opportunityIds, recruiterids, true);
						//
						//
						if (repeatTimes != null) {
							for (int i = 0; i < repeatTimes; i++) {
								//
								//
								GregorianCalendar cal = new GregorianCalendar();
								cal.setTimeInMillis(eventdate.getTime());
								cal.add(Calendar.DATE, i + 1);
								Date newEventdate = new Date(cal.getTime().getTime());
								//
								eventDao.createEvent(jobDivaSession, title, eventType, optional, newEventdate, priotity, duration, reminder, status1, eventNotification, timezone, leadtime, lagTime, privateEvent, participationOptional, location, note,
										customerId, opportunityIds, recruiterids, false);
								//
							}
						}
						//
						eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createEvent", "Create Successful Event Id(" + eventId + ") ");
						//
						return eventId;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "createEvent", "Create Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean updateEvent(JobDivaSession jobDivaSession, Long eventId, String title, Date eventDate, Integer duration, Integer eventType, String notes, Integer priority, Long reminder, Long status1, Integer optional, Boolean privateEvent,
			Timezone timezone, Long leadtime, Long lagTime, String location, Long customerId, List<Long> recruiterids, EventNotification eventNotification) throws Exception {
		//
		try {
			//
			return inTransaction(new TransactionCallback<Boolean>() {
				
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					try {
						//
						Boolean updatedEvent = eventDao.updateEvent(jobDivaSession, eventId, title, eventDate, duration, eventType, notes, priority, reminder, status1, optional, privateEvent, timezone, leadtime, lagTime, location, customerId,
								recruiterids, eventNotification);
						//
						eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateEvent", "Update Successful Event Id(" + eventId + ") ");
						//
						return updatedEvent;
						//
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				//
			});
			//
		} catch (Exception e) {
			//
			eventDao.saveAccessLog(jobDivaSession.getRecruiterId(), jobDivaSession.getLeader(), jobDivaSession.getTeamId(), "updateEvent", "Update Failed, " + e.getMessage());
			//
			throw new Exception(e.getMessage());
		}
	}
	
	public Boolean test(JobDivaSession jobDivaSession) throws Exception {
		//
		return eventDao.test(jobDivaSession);
	}
}
