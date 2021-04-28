package com.jobdiva.api.model.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotEmailAlert implements java.io.Serializable {
    @JsonProperty(value = "sendTo", index = 0)
	public String sendTo;
	//
	@JsonProperty(value = "from", index = 1)
	public String from;
	//
	@JsonProperty(value = "cc", index = 2)
	public String cc;
	//
	@JsonProperty(value = "subject", index = 3)
	public String subject;

    @JsonProperty(value = "body", index = 4)
	public String body;



}