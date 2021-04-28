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
	
	@JsonProperty(value = "invoiceid", required = true)
	private Integer		invoiceid;
	//
	@JsonProperty(value = "comments", required = false)
	private String		comments;
	//
	@JsonProperty(value = "emailrecipients", required = false)
	private String[]	emailrecipients;
	
	public Integer getInvoiceid() {
		return invoiceid;
	}
	
	public void setInvoiceid(Integer invoiceid) {
		this.invoiceid = invoiceid;
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
}
