package com.jobdiva.api.model.v2.hotlist;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 *         Apr 28, 2021
 */
@SuppressWarnings("serial")
public class AddCandidatesToHotlistDef implements Serializable {
	
	//
	@JsonProperty(value = "hotListid", required = true)
	private Long		hotListid;
	//
	@JsonProperty(value = "candidateIds", required = true)
	private List<Long>	candidateIds;
	
	public Long getHotListid() {
		return hotListid;
	}
	
	public void setHotListid(Long hotListid) {
		this.hotListid = hotListid;
	}
	
	public List<Long> getCandidateIds() {
		return candidateIds;
	}
	
	public void setCandidateIds(List<Long> candidateIds) {
		this.candidateIds = candidateIds;
	}
}
