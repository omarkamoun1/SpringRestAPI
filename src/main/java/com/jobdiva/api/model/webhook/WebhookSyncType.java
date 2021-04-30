package com.jobdiva.api.model.webhook;

/**
 * @author Joseph Chidiac
 *
 */
public enum WebhookSyncType {
	
	NONE(""), //
	JOB("job"), //
	CANDIDATE("candidate"), //
	COMPANY("company"), //
	CONTACT("contact"), //
	// WORKORDER("workorder"), //
	BILLING("billing"), //
	SALARY("salary"), //
	TIMESHEET("timesheet"), //
	EXPENSE("expense"),//
	;
	
	private String value;
	
	WebhookSyncType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static WebhookSyncType getWebhookSyncType(String value) {
		for (WebhookSyncType webhookSyncType : WebhookSyncType.values()) {
			if (webhookSyncType.getValue().equals(value)) {
				return webhookSyncType;
			}
		}
		return NONE;
	}
}