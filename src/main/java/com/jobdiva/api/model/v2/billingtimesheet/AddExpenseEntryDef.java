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
	
	@JsonProperty(value = "employeeFirstName", required = false)
	private String			employeeFirstName;
	//
	@JsonProperty(value = "employeeLastName", required = false)
	private String			employeeLastName;
	//
	@JsonProperty(value = "employeeId", required = false)
	private Long			employeeId;
	//
	@JsonProperty(value = "vmsEmployeeId", required = false)
	private String			vmsEmployeeId;
	//
	@JsonProperty(value = "expenseExternalId", required = false)
	private String			expenseExternalId;
	//
	@JsonProperty(value = "weekendingDate", required = true)
	private Date			weekendingDate;
	//
	@JsonProperty(value = "invoiceDate", required = false)
	private Date			invoiceDate;
	//
	@JsonProperty(value = "employeeComment", required = false)
	private String			employeeComment;
	//
	@JsonProperty(value = "description", required = false)
	private String			description;
	//
	@JsonProperty(value = "expenses", required = true)
	private ExpenseEntry[]	expenses;
	//
	@JsonProperty(value = "emailEecipients", required = false)
	private String[]		emailRecipients;
	//
	@JsonProperty(value = "jobId", required = false)
	private Long			jobId;
	//
	@JsonProperty(value = "activityId", required = false)
	private Long			activityId;
	
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getVmsEmployeeId() {
		return vmsEmployeeId;
	}
	
	public void setVmsEmployeeId(String vmsEmployeeId) {
		this.vmsEmployeeId = vmsEmployeeId;
	}
	
	public String getExpenseExternalId() {
		return expenseExternalId;
	}
	
	public void setExpenseExternalId(String expenseExternalId) {
		this.expenseExternalId = expenseExternalId;
	}
	
	public Date getWeekendingDate() {
		return weekendingDate;
	}
	
	public void setWeekendingDate(Date weekendingDate) {
		this.weekendingDate = weekendingDate;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public String getEmployeeComment() {
		return employeeComment;
	}
	
	public void setEmployeeComment(String employeeComment) {
		this.employeeComment = employeeComment;
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
	
	public String[] getEmailRecipients() {
		return emailRecipients;
	}
	
	public void setEmailRecipients(String[] emailRecipients) {
		this.emailRecipients = emailRecipients;
	}
	
	public Long getJobId() {
		return jobId;
	}
	
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	public Long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}
