package com.jobdiva.api.model.v2.event;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class UpdateTaskDef implements Serializable {
	
	@JsonProperty(value = "taskId", required = true)
	private Long	taskId;
	//
	@JsonProperty(value = "subject", required = true)
	private String	subject;
	//
	@JsonProperty(value = "duedate", required = true)
	private Date	duedate;
	//
	@JsonProperty(value = "duration", required = true)
	private Integer	duration;
	//
	@JsonProperty(value = "assignedtoid", required = true)
	private Long	assignedtoid;
	//
	@JsonProperty(value = "assignedbyid", required = false)
	private Long	assignedbyid;
	//
	@JsonProperty(value = "tasktype", required = false)
	private Integer	tasktype;
	//
	@JsonProperty(value = "percentagecompleted", required = false)
	private Integer	percentagecompleted;
	//
	@JsonProperty(value = "contactid", required = false)
	private Long	contactid;
	//
	@JsonProperty(value = "candidateid", required = false)
	private Long	candidateid;
	//
	@JsonProperty(value = "description", required = false)
	private String	description;
	//
	@JsonProperty(value = "isprivate", required = false)
	private Boolean	isprivate;
	
	public Long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Date getDuedate() {
		return duedate;
	}
	
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Long getAssignedtoid() {
		return assignedtoid;
	}
	
	public void setAssignedtoid(Long assignedtoid) {
		this.assignedtoid = assignedtoid;
	}
	
	public Long getAssignedbyid() {
		return assignedbyid;
	}
	
	public void setAssignedbyid(Long assignedbyid) {
		this.assignedbyid = assignedbyid;
	}
	
	public Integer getTasktype() {
		return tasktype;
	}
	
	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}
	
	public Integer getPercentagecompleted() {
		return percentagecompleted;
	}
	
	public void setPercentagecompleted(Integer percentagecompleted) {
		this.percentagecompleted = percentagecompleted;
	}
	
	public Long getContactid() {
		return contactid;
	}
	
	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getIsprivate() {
		return isprivate;
	}
	
	public void setIsprivate(Boolean isprivate) {
		this.isprivate = isprivate;
	}
}
