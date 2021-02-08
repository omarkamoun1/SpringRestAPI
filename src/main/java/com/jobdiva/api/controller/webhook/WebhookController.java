package com.jobdiva.api.controller.webhook;

import java.nio.charset.Charset;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	WebhookService webhookService;
	
	@RequestMapping(value = { "/{teamId}" }, method = { RequestMethod.POST }, produces = { "application/json" })
	public Boolean send(@PathVariable Long teamId, @RequestBody(required = true) String body) throws Exception {
		return this.webhookService.send(teamId, body);
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
		String passedSignature = ((String) headers.get("x-hub-signature")).substring(5);
		Mac hmac = Mac.getInstance("HmacSHA1");
		hmac.init(new SecretKeySpec("jobdiva".getBytes(Charset.forName("UTF-8")), "HmacSHA1"));
		String calculatedSignature = Hex.encodeHexString(hmac.doFinal(json.getBytes(Charset.forName("UTF-8"))));
		System.out.println("Calculated sigSHA1: " + calculatedSignature + " passedSignature: " + passedSignature);
		return calculatedSignature.equals(passedSignature);
	}
}
