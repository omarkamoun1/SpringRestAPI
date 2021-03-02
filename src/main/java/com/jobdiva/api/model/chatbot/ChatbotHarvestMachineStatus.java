package com.jobdiva.api.model.chatbot;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotHarvestMachineStatus implements java.io.Serializable {
	
	@JsonProperty(value = "machineNo", index = 0)
	public long machineNo;
	//
	@JsonProperty(value = "teamid", index = 1)
	public long	teamid;
	//
//	@JsonProperty(value = "accounts", index = 2)
//	public List<ChatbotHarvestAccount> accounts;
	//
	@JsonProperty(value = "isMachineInstalled", index = 3)
	public boolean	isMachineInstalled;
	//
	@JsonProperty(value = "hasMachineIssue", index = 4)
	public boolean	hasMachineIssue;

}
