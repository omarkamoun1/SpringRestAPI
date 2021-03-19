package com.jobdiva.api.model.webhook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Candidate;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookCandidate extends Candidate {
	
	@JsonProperty(value = "udfs", index = 16)
	private List<WebhookUDF> udfs;
	
	public List<WebhookUDF> getUdfs() {
		return udfs;
	}
	
	public void setUdfs(List<WebhookUDF> udfs) {
		this.udfs = udfs;
	}
}
