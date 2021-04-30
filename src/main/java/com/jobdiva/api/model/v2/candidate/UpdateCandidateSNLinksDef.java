package com.jobdiva.api.model.v2.candidate;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.SocialNetworkType;

/**
 * @author Joseph Chidiac
 *
 *         Apr 12, 2021
 */
@SuppressWarnings("serial")
public class UpdateCandidateSNLinksDef implements Serializable {
	
	@JsonProperty(value = "candidateid", required = true, index = 0)
	private Long				candidateid;
	//
	@JsonProperty(value = "socialnetworks", required = true, index = 1)
	private SocialNetworkType[]	socialnetworks;
	
	public Long getCandidateid() {
		return candidateid;
	}
	
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	
	public SocialNetworkType[] getSocialnetworks() {
		return socialnetworks;
	}
	
	public void setSocialnetworks(SocialNetworkType[] socialnetworks) {
		this.socialnetworks = socialnetworks;
	}
}
