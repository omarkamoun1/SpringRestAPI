package com.jobdiva.api.model;

import java.util.Calendar;


@SuppressWarnings("serial")
public class Notification implements java.io.Serializable  {
	
	private String	id;
	
	private String	type;
	
	private String	content;
	
	private Calendar Date;
	
	private Boolean	read;
	
	private String	relatedid;
	
	private String	email;
	
	private Long	recuiterid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getDate() {
		return Date;
	}

	public void setDate(Calendar date) {
		Date = date;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public String getRelatedid() {
		return relatedid;
	}

	public void setRelatedid(String relatedid) {
		this.relatedid = relatedid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRecuiterid() {
		return recuiterid;
	}

	public void setRecuiterid(Long recuiterid) {
		this.recuiterid = recuiterid;
	}
	
	
	
}
