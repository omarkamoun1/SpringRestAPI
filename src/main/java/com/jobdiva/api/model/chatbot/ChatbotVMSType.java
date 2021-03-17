package com.jobdiva.api.model.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotVMSType implements java.io.Serializable {
	@JsonProperty(value = "vms_name", index = 0)
	public String vms_name;
	//
	@JsonProperty(value = "hasJobCoddler", index = 1)
	public Boolean hasJobCoddler;
	//
	@JsonProperty(value = "hasTimesheetCoddler", index = 2)
	public Boolean hasTimesheetCoddler;
	//
	@JsonProperty(value = "hasSubmittalCoddler", index = 3)
	public Boolean hasSubmittalCoddler;
}
	

