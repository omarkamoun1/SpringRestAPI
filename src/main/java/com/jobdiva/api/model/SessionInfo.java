package com.jobdiva.api.model;

import io.swagger.annotations.ApiModel;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
@ApiModel()
public class SessionInfo implements java.io.Serializable {
	
	private String	userName;
	private String	firstName;
	private String	lastName;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
