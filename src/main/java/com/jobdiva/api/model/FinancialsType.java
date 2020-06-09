package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class FinancialsType implements java.io.Serializable {
	
	private ShowOnInvoiceType			showOnInvoiceType;
	private GroupInvoiceByType			groupInvoiceByType;
	private Long						paymentterms;
	private TimeSheetEntryFormatType	timesheetentryformat;
	private FrequencyType				frequency;
	private WeekendingType				weekending;
	private Double						hours;
	private BillingUnitType				billingUnit;
	private String						invoicecomment;
	
	public ShowOnInvoiceType getShowOnInvoiceType() {
		return showOnInvoiceType;
	}
	
	public void setShowOnInvoiceType(ShowOnInvoiceType showOnInvoiceType) {
		this.showOnInvoiceType = showOnInvoiceType;
	}
	
	public GroupInvoiceByType getGroupInvoiceByType() {
		return groupInvoiceByType;
	}
	
	public void setGroupInvoiceByType(GroupInvoiceByType groupInvoiceByType) {
		this.groupInvoiceByType = groupInvoiceByType;
	}
	
	public Long getPaymentterms() {
		return paymentterms;
	}
	
	public void setPaymentterms(Long paymentterms) {
		this.paymentterms = paymentterms;
	}
	
	public TimeSheetEntryFormatType getTimesheetentryformat() {
		return timesheetentryformat;
	}
	
	public void setTimesheetentryformat(TimeSheetEntryFormatType timesheetentryformat) {
		this.timesheetentryformat = timesheetentryformat;
	}
	
	public FrequencyType getFrequency() {
		return frequency;
	}
	
	public void setFrequency(FrequencyType frequency) {
		this.frequency = frequency;
	}
	
	public WeekendingType getWeekending() {
		return weekending;
	}
	
	public void setWeekending(WeekendingType weekending) {
		this.weekending = weekending;
	}
	
	public Double getHours() {
		return hours;
	}
	
	public void setHours(Double hours) {
		this.hours = hours;
	}
	
	public BillingUnitType getBillingUnit() {
		return billingUnit;
	}
	
	public void setBillingUnit(BillingUnitType billingUnit) {
		this.billingUnit = billingUnit;
	}
	
	public String getInvoicecomment() {
		return invoicecomment;
	}
	
	public void setInvoicecomment(String invoicecomment) {
		this.invoicecomment = invoicecomment;
	}
}
