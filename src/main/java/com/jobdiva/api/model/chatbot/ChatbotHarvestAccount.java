package com.jobdiva.api.model.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ChatbotHarvestAccount implements java.io.Serializable {
	
	@JsonProperty(value = "name", index = 0)
	public String 	name;
	//
	@JsonProperty(value = "status", index = 1)
	public String	status;
	//
	@JsonProperty(value = "harvest", index = 2)
	public Long harvest;
	//
	@JsonProperty(value = "hasNotDownloadSessionStarted", index = 3)
	public boolean hasNotDownloadSessionStarted;
	//
	@JsonProperty(value = "CATTest", index = 4)
	public String CATTest;
	//
	@JsonProperty(value = "downloaddLimitPerDay", index = 5)
	public Long downloaddLimitPerDay;
	//
	@JsonProperty(value = "hasJobBoardCriteria", index = 6)
	public boolean hasJobBoardCriteria;
	//
	@JsonProperty(value = "hasOverLappingTime", index = 7)
	public boolean hasOverLappingTime;
	//
	@JsonProperty(value = "downloadStartTime", index = 8)
	public String downloadStartTime;
	//
	@JsonProperty(value = "hasRecentResume", index = 9)
	public boolean hasRecentResume;
	//
	@JsonProperty(value = "webid", index = 10)
	public long webid;
	//
	@JsonProperty(value = "websitename", index = 11)
	public String websitename;
	//
	@JsonProperty(value = "machineNo", index = 11)
	public long machineNO;


}
