package com.jobdiva.api.model.chatbot;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotHarvestMachine implements java.io.Serializable {
	
	@JsonProperty(value = "machineNo", index = 0)
	public Long machineNo;
	//
	@JsonProperty(value = "isMachineInstalled", index = 1)
	public boolean isMachineInstalled;
	//
	@JsonProperty(value = "hasMachineIssue", index = 2)
	public boolean hasMachineIssue;
	
}
