package com.jobdiva.api.model.webhook;

import java.util.Date;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookUDF implements java.io.Serializable {
	
	private Integer	userFieldId;
	private String	userfieldValue;
	private Date	dateCreated;
	
	public Integer getUserFieldId() {
		return userFieldId;
	}
	
	public void setUserFieldId(Integer userFieldId) {
		this.userFieldId = userFieldId;
	}
	
	public String getUserfieldValue() {
		return userfieldValue;
	}
	
	public void setUserfieldValue(String userfieldValue) {
		this.userfieldValue = userfieldValue;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
