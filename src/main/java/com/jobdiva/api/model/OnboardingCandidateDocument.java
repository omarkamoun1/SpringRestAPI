package com.jobdiva.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class OnboardingCandidateDocument implements java.io.Serializable {
	
	@JsonProperty(required = true, index = 0)
	private Long	onboardingId;
	//
	@JsonProperty(required = true, index = 1)
	private String	filename;
	//
	@JsonProperty(required = true, index = 2)
	private byte[]	filecontent;
	
	public Long getOnboardingId() {
		return onboardingId;
	}
	
	public void setOnboardingId(Long onboardingId) {
		this.onboardingId = onboardingId;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public byte[] getFilecontent() {
		return filecontent;
	}
	
	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}
}
