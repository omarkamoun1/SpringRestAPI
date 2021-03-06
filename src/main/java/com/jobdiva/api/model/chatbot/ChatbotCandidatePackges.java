package com.jobdiva.api.model.chatbot;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotCandidatePackges implements java.io.Serializable {
    @JsonProperty(value = "candidateName", index = 0)
	public String candidateName;
	//
	@JsonProperty(value = "candidateID", index = 1)
	public Long candidateID;
	//
	@JsonProperty(value = "activepackges", index = 2)
	public Integer activepackges;
	//
	@JsonProperty(value = "jobReference", index = 3)
	public String jobReference;

    @JsonProperty(value = "hadCancelStart", index = 4)
	public Boolean hadCancelStart;

	@JsonProperty(value = "canceledBy", index = 4)
	public String canceledBy;

	@JsonProperty(value = "canceledOn", index = 4)
	public Date cancledOn;


}





	

