package com.jobdiva.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdiva.api.dao.webhook.WebhookDao;
import com.jobdiva.api.model.webhook.WebhookInfo;
import com.jobdiva.api.model.webhook.WebhookRequest;

/**
 * @author Joseph Chidiac
 *
 */
@Service
public class WebhookService {
	
	protected final Logger	logger		= LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	WebhookDao				webhookDao;
	//
	private Object			synObj		= new Object();
	private List<String>	syncList	= Collections.synchronizedList(new ArrayList<String>());
	
	public WebhookInfo getWebhookConfiguration(Long teamId) {
		return this.webhookDao.getWebhookConfiguration(teamId);
	}
	
	public Boolean updateWebhookConfiguration(Long teamId, String clientSecret, String clientUrl, Boolean active) {
		return this.webhookDao.updateWebhookConfiguration(teamId, clientSecret, clientUrl, active);
	}
	
	public Boolean deleteWebhookConfiguration(Long teamId) {
		return this.webhookDao.deleteWebhookConfiguration(teamId);
	}
	
	private String getSyncItem(Long teamId, Integer operation, String id, String syncType) {
		return teamId + id + operation + syncType;
	}
	
	protected boolean canExecute(Long teamId, String id, String syncType, Integer operation) {
		//
		//
		synchronized (synObj) {
			//
			String syncIem = getSyncItem(teamId, operation, id, syncType);
			//
			Boolean value = syncList.contains(syncIem);
			//
			if (value)
				syncList.remove(syncIem);
			//
			return value;
		}
		//
		//
		//
	}
	
	public Boolean syncWebhook(Long teamId, String syncType, Integer operation, String id) throws Exception {
		//
		//
		String syncIem = getSyncItem(teamId, operation, id, syncType);
		//
		synchronized (synObj) {
			if (!syncList.contains(syncIem)) {
				syncList.add(syncIem);
			} else {
				return true;
			}
		}
		//
		//
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				if (canExecute(teamId, id, syncType, operation))
					try {
						logger.info("syncwebhook [" + teamId + "] [" + syncType + "] [" + operation + "] [" + id + "]");
						//
						//
						WebhookRequest webhookRequest = new WebhookRequest();
						webhookRequest.setId(id);
						webhookRequest.setOperation(operation);
						webhookRequest.setSyncType(syncType);
						webhookRequest.setTeamId(teamId);
						//
						webhookDao.syncWebhook(webhookRequest);
					} catch (Exception e) {
						System.err.println(e);
					}
			}
		};
		//
		new Timer().schedule(timerTask, 4 * 1000);
		//
		return true;
	}
}
