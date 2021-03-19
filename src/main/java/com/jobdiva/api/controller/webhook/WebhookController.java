package com.jobdiva.api.controller.webhook;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jobdiva.api.controller.AbstractJobDivaController;
import com.jobdiva.api.model.webhook.WebhookInfo;
import com.jobdiva.api.service.WebhookService;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Joseph Chidiac
 *
 */
@CrossOrigin
@RestController
@RequestMapping({ "/api/webhook/" })
@Api(value = "Webhook API", description = "REST API Used For Webhook")
@ApiIgnore
public class WebhookController extends AbstractJobDivaController {
	
	protected final Logger	logger	= LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	WebhookService			webhookService;
	
	// syncType = job
	// operation = 1 [Insert] / 2 [Update] / 3 [Delete]
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/syncWebhook", produces = "application/json")
	public Boolean syncWebhook(Long teamId, String syncType, Integer operation, String id) throws Exception {
		//
		return webhookService.syncWebhook(teamId, syncType, operation, id);
		//
	}
	
	@RequestMapping(value = { "/getWebhookConfiguration" }, method = { RequestMethod.GET }, produces = { "application/json" })
	public WebhookInfo getWebhookConfiguration(Long teamId) throws Exception {
		return this.webhookService.getWebhookConfiguration(teamId);
	}
	
	@RequestMapping(value = { "/updateWebhookConfiguration" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean updateWebhookConfiguration(Long teamId, String clientSecret, String clientUrl, Boolean active) throws Exception {
		return this.webhookService.updateWebhookConfiguration(teamId, clientSecret, clientUrl, active);
	}
	
	@RequestMapping(value = { "/deleteWebhookConfiguration" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean deleteWebhookConfiguration(Long teamId) throws Exception {
		return this.webhookService.deleteWebhookConfiguration(teamId);
	}
	
	@RequestMapping(value = { "/welcome" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean welcome(@RequestHeader Map<String, String> headers, @RequestBody String json) throws Exception {
		//
		logger.info(" WEBHOOK :: RECIEVED REQUEST ");
		//
		return true;
	}
}
