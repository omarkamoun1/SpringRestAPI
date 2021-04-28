package com.jobdiva.api.model.v2.start;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Timezone;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class SetStartDef implements Serializable {
	
	@JsonProperty(value = "submittalid", required = false)
	private Long		submittalid;
	//
	@JsonProperty(value = "recruiterid", required = false)
	private Long		recruiterid;
	//
	//
	@JsonProperty(value = "startDate", required = false)
	private Date		startDate;
	//
	//
	@JsonProperty(value = "endDate", required = false)
	private Date		endDate;
	//
	@JsonProperty(value = "timezone", required = true)
	private Timezone	timezone;
	//
	@JsonProperty(value = "internalnotes", required = false)
	private String		internalnotes;
	
	public Long getSubmittalid() {
		return submittalid;
	}
	
	public void setSubmittalid(Long submittalid) {
		this.submittalid = submittalid;
	}
	
	public Long getRecruiterid() {
		return recruiterid;
	}
	
	public void setRecruiterid(Long recruiterid) {
		this.recruiterid = recruiterid;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Timezone getTimezone() {
		return timezone;
	}
	
	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}
	
	public String getInternalnotes() {
		return internalnotes;
	}
	
	public void setInternalnotes(String internalnotes) {
		this.internalnotes = internalnotes;
	}
}
