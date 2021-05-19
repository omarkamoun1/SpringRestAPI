package com.jobdiva.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class Applicant implements java.io.Serializable {

	@JsonProperty(value = "id", index = 0)
	private Long			id;
	//
	@JsonProperty(value = "date applied", index = 1)
	private String			dateApplied;
	//
	@JsonProperty(value = "matched", index = 2)
	private Boolean			matched;
	//
	@JsonProperty(value = "resume source", index = 3)
	private String			resumeSource;
	//
	@JsonProperty(value = "first name", index = 4)
	private String			firstName;
	//
	@JsonProperty(value = "last name", index = 5)
	private String			lastName;
	//
	@JsonProperty(value = "email", index = 6)
	private String			email;
	//
	@JsonProperty(value = "rejected", index = 7)
	private String			rejected;
	//
	@JsonProperty(value = "status", index = 8)
	private Integer			status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDateApplied() {
		return dateApplied;
	}
	public void setDateApplied(String dateApplied) {
		this.dateApplied = dateApplied;
	}
	public Boolean getMatched() {
		return matched;
	}
	public void setMatched(Boolean matched) {
		this.matched = matched;
	}
	public String getResumeSource() {
		return resumeSource;
	}
	public void setResumeSource(String resumeSource) {
		this.resumeSource = resumeSource;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRejected() {
		return rejected;
	}
	public void setRejected(String rejected) {
		this.rejected = rejected;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
