package com.jobdiva.api.model.webhook;

import java.io.Serializable;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookInfo implements Serializable {
	
	private Long	id;
	private Long	teamId;
	private String	clientSecret;
	private String	clientUrl;
	private Boolean	active			= false;
	private Boolean	enableJob		= false;
	private Boolean	enableCandidate	= false;
	private Boolean	enableCompany	= false;
	private Boolean	enableContact	= false;
	private Boolean	enableBilling	= false;
	private Boolean	enableSalary	= false;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getClientUrl() {
		return this.clientUrl;
	}
	
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
	
	public Boolean getActive() {
		return this.active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getEnableJob() {
		return enableJob;
	}
	
	public void setEnableJob(Boolean enableJob) {
		this.enableJob = enableJob;
	}
	
	public Boolean getEnableCandidate() {
		return enableCandidate;
	}
	
	public void setEnableCandidate(Boolean enableCandidate) {
		this.enableCandidate = enableCandidate;
	}
	
	public Boolean getEnableCompany() {
		return enableCompany;
	}
	
	public void setEnableCompany(Boolean enableCompany) {
		this.enableCompany = enableCompany;
	}
	
	public Boolean getEnableContact() {
		return enableContact;
	}
	
	public void setEnableContact(Boolean enableContact) {
		this.enableContact = enableContact;
	}
	
	public Boolean getEnableBilling() {
		return enableBilling;
	}
	
	public void setEnableBilling(Boolean enableBilling) {
		this.enableBilling = enableBilling;
	}
	
	public Boolean getEnableSalary() {
		return enableSalary;
	}
	
	public void setEnableSalary(Boolean enableSalary) {
		this.enableSalary = enableSalary;
	}
}
