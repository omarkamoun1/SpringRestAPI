package com.jobdiva.api.model.webhook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jobdiva.api.model.Contact;

/**
 * @author Joseph Chidiac
 *
 */
@SuppressWarnings("serial")
public class WebhookContact extends Contact {
	
	@JsonProperty(value = "udfs", index = 23)
	private List<WebhookUDF> udfs;
	
	public List<WebhookUDF> getUdfs() {
		return udfs;
	}
	
	public void setUdfs(List<WebhookUDF> udfs) {
		this.udfs = udfs;
	}
}
