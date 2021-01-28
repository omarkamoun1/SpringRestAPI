package com.jobdiva.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class JobBoardResponse implements java.io.Serializable {
	
	@JsonProperty(value = "Status", index = 0)
	private String							status;
	@JsonProperty(value = "Message", index = 1)
	private List<String>					message;
	@JsonProperty(value = "ServerRequest", index = 2)
	private String							serverRequest;
	@JsonProperty(value = "ServerResponse", index = 3)
	private String							serverResponse;
	
	
	public JobBoardResponse() {
	}
	
	public JobBoardResponse(String status, List<String> message, String serverRequest, String serverResponse) {
		this.status = status;
		this.message = message;
		this.serverRequest = serverRequest;
		this.serverResponse = serverResponse;
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
	
	public String getServerRequest() {
		return serverRequest;
	}
	
	public void setServerRequest(String serverRequest) {
		this.serverRequest = serverRequest;
	}
	
	public String getServerResponse() {
		return serverResponse;
	}
	
	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}
}