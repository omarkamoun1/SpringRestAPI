package com.jobdiva.api.model;

import java.util.List;

@SuppressWarnings("serial")
public class JobUser implements java.io.Serializable {
	
	private Long	rfqId;
	private Long	recruiterId;
	private String	firstName;
	private String	lastName;
	private Long	teamId;
	private Boolean	receiveEmail;
	private Boolean	leadRecruiter;
	private Boolean	sales;
	private Boolean	leadSales;
	private Boolean	recruiter;
	private Integer	jobStatus;
	private String	phone;
	private String	email;
	private Integer	recEmailStatus;
	private List<Long> roleIds;
	
	
	
	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public Long getRfqId() {
		return rfqId;
	}
	
	public void setRfqId(Long rfqId) {
		this.rfqId = rfqId;
	}
	
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
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Boolean getReceiveEmail() {
		return receiveEmail;
	}
	
	public void setReceiveEmail(Boolean receiveEmail) {
		this.receiveEmail = receiveEmail;
	}
	
	public Boolean getLeadRecruiter() {
		return leadRecruiter;
	}
	
	public void setLeadRecruiter(Boolean leadRecruiter) {
		this.leadRecruiter = leadRecruiter;
	}
	
	public Boolean getSales() {
		return sales;
	}
	
	public void setSales(Boolean sales) {
		this.sales = sales;
	}
	
	public Boolean getLeadSales() {
		return leadSales;
	}
	
	public void setLeadSales(Boolean leadSales) {
		this.leadSales = leadSales;
	}
	
	public Boolean getRecruiter() {
		return recruiter;
	}
	
	public void setRecruiter(Boolean recruiter) {
		this.recruiter = recruiter;
	}
	
	public Integer getJobStatus() {
		return jobStatus;
	}
	
	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getRecEmailStatus() {
		return recEmailStatus;
	}
	
	public void setRecEmailStatus(Integer recEmailStatus) {
		this.recEmailStatus = recEmailStatus;
	}
}
