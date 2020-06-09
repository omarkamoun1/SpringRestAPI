package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.QualificationType;

public class JsonToQualificationTypeConverter implements Converter<String, QualificationType> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public QualificationType convert(String source) {
		try {
			return jsonMapper.readValue(source, QualificationType.class);
		} catch (IOException e) {
		}
		return null;
	}
}
