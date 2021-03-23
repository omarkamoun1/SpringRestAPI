package com.jobdiva.api.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Note implements java.io.Serializable {
	
	@JsonProperty(value = "id", index = 0)
	private Long					id;
	
	@JsonProperty(value = "recruiter name", index = 1)
	private String					recruiterName;
	
	@JsonProperty(value = "date", index = 2)
	private Date				 date;
	
	@JsonProperty(value = "recruiter id", index = 3)
	private Long					recruiterId;
	
	@JsonProperty(value = "content", index = 4)
	private String					content;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecruiterName() {
		return recruiterName;
	}

	public void setRecruiterName(String recruiterName) {
		this.recruiterName = recruiterName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}