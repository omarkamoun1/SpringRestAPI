package com.jobdiva.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.webhook.WebhookDao;
import com.jobdiva.api.model.webhook.WebhookInfo;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class WebhookService {
	
	@Autowired
	WebhookDao webhookDao;
	
	public Boolean send(Long teamId, String body) {
		return this.webhookDao.send(teamId, body);
	}
	
	public WebhookInfo getWebhookConfiguration(Long teamId) {
		return this.webhookDao.getWebhookConfiguration(teamId);
	}
	
	public Boolean updateWebhookConfiguration(Long teamId, String clientSecret, String clientUrl, Boolean active) {
		return this.webhookDao.updateWebhookConfiguration(teamId, clientSecret, clientUrl, active);
	}
	
	public Boolean deleteWebhookConfiguration(Long teamId) {
		return this.webhookDao.deleteWebhookConfiguration(teamId);
	}
}
