package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.EventNotification;

public class JsonToEventNotificationConverter implements Converter<String, EventNotification> {
	
	@Override
	public EventNotification convert(String source) {
		for (EventNotification eventNotification : EventNotification.values()) {
			if (eventNotification.getName().equalsIgnoreCase(source)) {
				return eventNotification;
			}
		}
		return null;
	}
}
