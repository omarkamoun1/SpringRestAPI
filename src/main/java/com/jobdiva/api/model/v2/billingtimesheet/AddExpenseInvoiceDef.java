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
public class AddExpenseInvoiceDef implements Serializable {
	
	//
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
	
	public Date getWeekendingdate() {
		return weekendingdate;
	}
	
	public Date getInvoicedate() {
		return invoicedate;
	}
	
	public String getFeedback() {
		return feedback;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ExpenseEntry[] getExpenses() {
		return expenses;
	}
	
	public String[] getEmailrecipients() {
		return emailrecipients;
	}
}
