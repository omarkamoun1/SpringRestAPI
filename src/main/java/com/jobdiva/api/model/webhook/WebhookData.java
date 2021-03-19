package com.jobdiva.api.model.webhook;

import java.io.Serializable;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookData implements Serializable {
	
	private String			type;
	private String			operation;
	private Long			id;
	private Serializable	data;
	//
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Serializable getData() {
		return data;
	}
	
	public void setData(Serializable data) {
		this.data = data;
	}
}
