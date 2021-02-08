package com.jobdiva.api.model.webhook;

/**
 * @author Joseph Chidiac
 *
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class WebhookInfo implements Serializable {
	
	private Long	id;
	private String	clientSecret;
	private String	clientUrl;
	private Boolean	active;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String getClientUrl() {
		return this.clientUrl;
	}
	
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
	
	public Boolean getActive() {
		return this.active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
}
