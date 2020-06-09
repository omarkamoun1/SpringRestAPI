package com.jobdiva.api.convertor;

import org.springframework.core.convert.converter.Converter;

import com.jobdiva.api.model.FrequencyType;

public class JsonToFrequencyTypeConverter implements Converter<String, FrequencyType> {
	
	@Override
	public FrequencyType convert(String source) {
		for (FrequencyType FrequencyType : FrequencyType.values()) {
			if (FrequencyType.getValue().equalsIgnoreCase(source)) {
				return FrequencyType;
			}
		}
		return null;
	}
}
