package com.jobdiva.api.model;

import java.util.Calendar;

@SuppressWarnings("serial")
public class Event implements java.io.Serializable {
	
	private String eventId;
	private String subject;
	private Calendar eventDate;
	private Calendar eventEndDate;
	private Integer duration;
	private Boolean isPrivate;
	private Integer priority;
	private Long candidateId;
	private String candidateName;
	private String ownerName;
	private Integer leadTime;
	private Integer lagTime;
	private Boolean isWholeDayEvent;
	private String timezone;
	private String location;
	private Integer reminder;
	private Integer reminderRepeat;
	private String description; //notes
	private String dateType;
	private Integer eventType;
	private String eventTypeName;
	private Integer contactNumber;
	private String rDate;
	private String rRule;
	private String exDate;
	private String recruiterId;
	private Integer rSeq; 
	private String activityId; 
	private JobUserSimple[] users;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Calendar getEventDate() {
		return eventDate;
	}
	public void setEventDate(Calendar eventDate) {
		this.eventDate = eventDate;
	}
	public Calendar getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(Calendar eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(Integer leadTime) {
		this.leadTime = leadTime;
	}
	public Integer getLagTime() {
		return lagTime;
	}
	public void setLagTime(Integer lagTime) {
		this.lagTime = lagTime;
	}
	public Boolean getIsWholeDayEvent() {
		return isWholeDayEvent;
	}
	public void setIsWholeDayEvent(Boolean isWholeDayEvent) {
		this.isWholeDayEvent = isWholeDayEvent;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getReminder() {
		return reminder;
	}
	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}
	public Integer getReminderRepeat() {
		return reminderRepeat;
	}
	public void setReminderRepeat(Integer reminderRepeat) {
		this.reminderRepeat = reminderRepeat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	public Integer getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Integer contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	public String getrRule() {
		return rRule;
	}
	public void setrRule(String rRule) {
		this.rRule = rRule;
	}
	public String getExDate() {
		return exDate;
	}
	public void setExDate(String exDate) {
		this.exDate = exDate;
	}
	public String getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(String recruiterId) {
		this.recruiterId = recruiterId;
	}
	public Integer getRSeq() {
		return rSeq;
	}
	public void setRSeq(Integer seq) {
		this.rSeq = seq;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public JobUserSimple[] getUsers() {
		return users;
	}
	public void setUsers(JobUserSimple[] users) {
		this.users = users;
	}
	
	
	
}
