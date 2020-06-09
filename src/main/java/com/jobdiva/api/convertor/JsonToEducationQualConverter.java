package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Education;

public class JsonToEducationQualConverter implements Converter<String, Education> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Education convert(String source) {
		try {
			return jsonMapper.readValue(source, Education.class);
		} catch (IOException e) {
		}
		return null;
	}
}
