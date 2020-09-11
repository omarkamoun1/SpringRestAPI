package com.jobdiva.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class JsukResponse implements java.io.Serializable {
	
	@JsonProperty(value = "Status", index = 0)
	private String							status;
	@JsonProperty(value = "Message", index = 1)
	private List<String>					message;
	
	
	public JsukResponse() {
	}
	
	public JsukResponse(String status, List<String> message) {
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getMessage() {
		return message;
	}
	
	public void setMessage(List<String> message) {
		this.message = message;
	}
}