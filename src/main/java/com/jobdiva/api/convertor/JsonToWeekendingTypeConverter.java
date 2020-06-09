package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.WeekendingType;

public class JsonToWeekendingTypeConverter implements Converter<String, WeekendingType> {
	
	@Override
	public WeekendingType convert(String source) {
		for (WeekendingType WeekendingType : WeekendingType.values()) {
			if (WeekendingType.getValue().equalsIgnoreCase(source)) {
				return WeekendingType;
			}
		}
		return null;
	}
}
