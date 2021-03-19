package com.jobdiva.api.dao.company;

import com.jobdiva.api.model.webhook.WebhookCompany;

public class WebhookCompanyRowMapper extends CompanyRowMapper {
	
	protected WebhookCompany createCompany() {
		WebhookCompany company = new WebhookCompany();
		return company;
	}
}
