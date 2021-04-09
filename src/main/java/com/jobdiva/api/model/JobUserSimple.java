package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class JobUserSimple implements java.io.Serializable {
	
	private Long	recruiterId;
	private String	firstName;
	private String	lastName;
	
	public Long getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
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
	
	
	

}
