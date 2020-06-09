package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Experience;

public class JsonToExperienceQualConverter implements Converter<String, Experience> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Experience convert(String source) {
		try {
			return jsonMapper.readValue(source, Experience.class);
		} catch (IOException e) {
		}
		return null;
	}
}
