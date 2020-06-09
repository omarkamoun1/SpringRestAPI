package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;

@SuppressWarnings("serial")
@ApiModel(discriminator = "type")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRole implements java.io.Serializable {
	
	private Long	userId;
	private String	role;
	private Integer	action;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Integer getAction() {
		return action;
	}
	
	public void setAction(Integer action) {
		this.action = action;
	}
}
