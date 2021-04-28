package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.ExpenseEntry;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class AddExpenseEntryDef implements Serializable {
	
	@JsonProperty(value = "employeeid", required = true)
	private Long			employeeid;
	//
	@JsonProperty(value = "weekendingdate", required = true)
	private Date			weekendingdate;
	//
	@JsonProperty(value = "invoicedate", required = false)
	private Date			invoicedate;
	//
	@JsonProperty(value = "feedback", required = false)
	private String			feedback;
	//
	@JsonProperty(value = "description", required = false)
	private String			description;
	//
	@JsonProperty(value = "expenses", required = true)
	private ExpenseEntry[]	expenses;
	//
	@JsonProperty(value = "emailrecipients", required = false)
	private String[]		emailrecipients;
	
	public Long getEmployeeid() {
		return employeeid;
	}
	
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}
	
	public Date getWeekendingdate() {
		return weekendingdate;
	}
	
	public void setWeekendingdate(Date weekendingdate) {
		this.weekendingdate = weekendingdate;
	}
	
	public Date getInvoicedate() {
		return invoicedate;
	}
	
	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}
	
	public String getFeedback() {
		return feedback;
	}
	
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ExpenseEntry[] getExpenses() {
		return expenses;
	}
	
	public void setExpenses(ExpenseEntry[] expenses) {
		this.expenses = expenses;
	}
	
	public String[] getEmailrecipients() {
		return emailrecipients;
	}
	
	public void setEmailrecipients(String[] emailrecipients) {
		this.emailrecipients = emailrecipients;
	}
}
