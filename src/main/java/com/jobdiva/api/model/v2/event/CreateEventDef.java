package com.jobdiva.api.model.v2.event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.EventNotification;
import com.jobdiva.api.model.Timezone;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class CreateEventDef implements Serializable {
	
	@JsonProperty(value = "title", required = true)
	private String				title;
	//
	@JsonProperty(value = "eventType", required = false)
	private Integer				eventType;
	//
	@JsonProperty(value = "optional.", required = false)
	private Integer				optional;
	//
	@JsonProperty(value = "eventDate", required = true)
	private Date				eventDate;
	//
	@JsonProperty(value = "priority", required = true)
	private Integer				priority;
	//
	@JsonProperty(value = "duration", required = false)
	private Integer				duration;
	//
	@JsonProperty(value = "reminder", required = false)
	private Long				reminder;
	//
	@JsonProperty(value = "status", required = false)
	private Long				status;
	//
	@JsonProperty(value = "eventNotification")
	private EventNotification	eventNotification;
	//
	@JsonProperty(value = "timezone")
	private Timezone			timezone;
	//
	@JsonProperty(value = "leadtime", required = false)
	private Long				leadtime;
	//
	@JsonProperty(value = "lagTime", required = false)
	private Long				lagTime;
	//
	@JsonProperty(value = "privateEvent", required = false)
	private Boolean				privateEvent;
	//
	@JsonProperty(value = "repeatTimes", required = false)
	private Integer				repeatTimes;
	//
	@JsonProperty(value = "participationOptional", required = false)
	private Boolean				participationOptional;
	//
	@JsonProperty(value = "location.", required = false)
	private String				location;
	//
	@JsonProperty(value = "notes", required = false)
	private String				notes;
	//
	@JsonProperty(value = "customerId", required = false)
	private Long				customerId;
	//
	@JsonProperty(value = "opportunityIds", required = false)
	private List<Long>			opportunityIds;
	//
	@JsonProperty(value = "recruiterids", required = false)
	private List<Long>			recruiterids;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getEventType() {
		return eventType;
	}
	
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	
	public Integer getOptional() {
		return optional;
	}
	
	public void setOptional(Integer optional) {
		this.optional = optional;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Long getReminder() {
		return reminder;
	}
	
	public void setReminder(Long reminder) {
		this.reminder = reminder;
	}
	
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public EventNotification getEventNotification() {
		return eventNotification;
	}
	
	public void setEventNotification(EventNotification eventNotification) {
		this.eventNotification = eventNotification;
	}
	
	public Timezone getTimezone() {
		return timezone;
	}
	
	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}
	
	public Long getLeadtime() {
		return leadtime;
	}
	
	public void setLeadtime(Long leadtime) {
		this.leadtime = leadtime;
	}
	
	public Long getLagTime() {
		return lagTime;
	}
	
	public void setLagTime(Long lagTime) {
		this.lagTime = lagTime;
	}
	
	public Boolean getPrivateEvent() {
		return privateEvent;
	}
	
	public void setPrivateEvent(Boolean privateEvent) {
		this.privateEvent = privateEvent;
	}
	
	public Integer getRepeatTimes() {
		return repeatTimes;
	}
	
	public void setRepeatTimes(Integer repeatTimes) {
		this.repeatTimes = repeatTimes;
	}
	
	public Boolean getParticipationOptional() {
		return participationOptional;
	}
	
	public void setParticipationOptional(Boolean participationOptional) {
		this.participationOptional = participationOptional;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public List<Long> getOpportunityIds() {
		return opportunityIds;
	}
	
	public void setOpportunityIds(List<Long> opportunityIds) {
		this.opportunityIds = opportunityIds;
	}
	
	public List<Long> getRecruiterids() {
		return recruiterids;
	}
	
	public void setRecruiterids(List<Long> recruiterids) {
		this.recruiterids = recruiterids;
	}
}
