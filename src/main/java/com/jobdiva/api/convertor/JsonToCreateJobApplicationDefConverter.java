package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.CreateJobApplicationDef;

public class JsonToCreateJobApplicationDefConverter implements Converter<String, CreateJobApplicationDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateJobApplicationDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateJobApplicationDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
