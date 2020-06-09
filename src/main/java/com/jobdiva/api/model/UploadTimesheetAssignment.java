package com.jobdiva.api.model;

@SuppressWarnings("serial")
public class UploadTimesheetAssignment implements java.io.Serializable {
	
	private String	status;
	private String	message;
	private Long	timecardid;
	private String	addexpensestatus;
	private String	addexpensemessage;
	private Integer	invoiceId;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getTimecardid() {
		return timecardid;
	}
	
	public void setTimecardid(Long timecardid) {
		this.timecardid = timecardid;
	}
	
	public String getAddexpensestatus() {
		return addexpensestatus;
	}
	
	public void setAddexpensestatus(String addexpensestatus) {
		this.addexpensestatus = addexpensestatus;
	}
	
	public String getAddexpensemessage() {
		return addexpensemessage;
	}
	
	public void setAddexpensemessage(String addexpensemessage) {
		this.addexpensemessage = addexpensemessage;
	}
	
	public Integer getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
}
