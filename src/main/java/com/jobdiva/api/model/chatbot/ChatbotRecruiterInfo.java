package com.jobdiva.api.model.chatbot;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotRecruiterInfo implements java.io.Serializable {

    @JsonProperty(value = "name", index = 0)
	public String name;

    @JsonProperty(value = "id", index = 0)
	public long id;

    @JsonProperty(value = "email", index = 0)
	public String email;

}
