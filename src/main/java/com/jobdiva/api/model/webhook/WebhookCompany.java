package com.jobdiva.api.model.webhook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Company;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookCompany extends Company {
	
	@JsonProperty(value = "udfs", index = 18)
	private List<WebhookUDF> udfs;
	
	public List<WebhookUDF> getUdfs() {
		return udfs;
	}
	
	public void setUdfs(List<WebhookUDF> udfs) {
		this.udfs = udfs;
	}
}
