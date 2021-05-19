package com.jobdiva.api.model.v2.billingtimesheet;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 22, 2021
 */
@SuppressWarnings("serial")
public class DeleteTimesheetDef implements Serializable {
	
	@JsonProperty(required = true)
	private Long	timesheetId;
	//
	@JsonProperty(required = false)
	private String	externalId;
	
	public Long getTimesheetId() {
		return timesheetId;
	}
	
	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
