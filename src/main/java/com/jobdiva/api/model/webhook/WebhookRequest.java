package com.jobdiva.api.model.webhook;

/**
 * @author Joseph Chidiac
 *
 */
public class WebhookRequest {
	
	private String	id;
	private Long	teamId;
	private String	syncType;
	private Integer	operation;
	private Long	faultId;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public String getSyncType() {
		return syncType;
	}
	
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	
	public Integer getOperation() {
		return operation;
	}
	
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	
	public Long getFaultId() {
		return faultId;
	}
	
	public void setFaultId(Long faultId) {
		this.faultId = faultId;
	}
}
