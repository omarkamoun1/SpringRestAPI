package com.jobdiva.api.model.chatbot;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotVMSAccount implements java.io.Serializable {
	
	@JsonProperty(value = "site", index = 0)
	public String site;
	//
	@JsonProperty(value = "username", index = 1)
	public String username;
	//
	@JsonProperty(value = "teamid", index = 2)
	public Long teamid;
	//
	@JsonProperty(value = "url", index = 3)
	public String url;
	//
	@JsonProperty(value = "isHalted", index = 4)
	public boolean isHalted;
	//
	@JsonProperty(value = "datelastrun", index = 5)
	public Date datelastrun;
	//
	@JsonProperty(value = "active", index = 6)
	public boolean active;
	//
	@JsonProperty(value = "activeTimesheet", index = 7)
	public boolean activeTimesheet;
	//
	@JsonProperty(value = "activeSumittal", index = 8)
	public boolean activeSumittal;
	//
	@JsonProperty(value = "onClientMachine", index = 9)
	public Boolean onClientMachine;
	//
	@JsonProperty(value = "status", index = 10)
	public String status;
	//
	@JsonProperty(value = "machineDetail", index = 11)
	public String machineDetail;

}
