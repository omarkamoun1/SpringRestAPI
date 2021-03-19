package com.jobdiva.api.dao.contact;

import com.jobdiva.api.model.Contact;
import com.jobdiva.api.model.webhook.WebhookContact;

public class WebhookContactRowMapper extends ContactRowMapper {
	
	@Override
	protected Contact createContact() {
		WebhookContact contact = new WebhookContact();
		return contact;
	}
}
