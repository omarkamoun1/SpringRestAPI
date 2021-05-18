package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class ApproveExpenseEntryDef implements Serializable {
	
	@JsonProperty(value = "expenseId", required = true)
	private Integer		expenseId;
	//
	@JsonProperty(value = "comments", required = false)
	private String		comments;
	//
	@JsonProperty(value = "emailrecipients", required = false)
	private String[]	emailrecipients;
	//
	@JsonProperty(value = "approverId", required = false)
	private Long		approverId;
	
	public Integer getExpenseId() {
		return expenseId;
	}
	
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String[] getEmailrecipients() {
		return emailrecipients;
	}
	
	public void setEmailrecipients(String[] emailrecipients) {
		this.emailrecipients = emailrecipients;
	}
	
	public Long getApproverId() {
		return approverId;
	}
	
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
}
