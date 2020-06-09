package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.Timezone;

public class JsonToTimezoneConverter implements Converter<String, Timezone> {
	
	@Override
	public Timezone convert(String source) {
		for (Timezone timezone : Timezone.values()) {
			if (timezone.getValue().equalsIgnoreCase(source)) {
				return timezone;
			}
		}
		return null;
	}
}
