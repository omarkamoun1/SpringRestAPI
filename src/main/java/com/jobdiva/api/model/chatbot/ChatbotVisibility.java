package com.jobdiva.api.model.chatbot;

import io.swagger.annotations.ApiModel;

@SuppressWarnings("serial")
@ApiModel
public class ChatbotVisibility implements java.io.Serializable {
	
	private long	teamid;
	private boolean	show;
	
	public long getTeamid() {
		return teamid;
	}
	
	public void setTeamid(long teamid) {
		this.teamid = teamid;
	}
	
	public boolean isShow() {
		return show;
	}
	
	public void setShow(boolean show) {
		this.show = show;
	}
}
