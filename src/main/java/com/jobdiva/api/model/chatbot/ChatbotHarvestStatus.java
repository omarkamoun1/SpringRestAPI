package com.jobdiva.api.model.chatbot;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotHarvestStatus implements java.io.Serializable {
	
	@JsonProperty(value = "webid", index = 0)
	public long webid;
	//
	@JsonProperty(value = "teamid", index = 1)
	public long	teamid;
	//
	@JsonProperty(value = "accounts", index = 2)
	public List<ChatbotHarvestAccount> accounts;
	//
	@JsonProperty(value = "machines", index = 3)
	public List<ChatbotHarvestMachine> machines;
	//
	@JsonProperty(value = "name", index = 4)
	public String name;
	//
	@JsonProperty(value = "active", index = 5)
	public Long active;
	//
	@JsonProperty(value = "harvest", index = 6)
	public Long harvest;
}
