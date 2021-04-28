package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.event.CreateTaskDef;

public class JsonToCreateTaskDefConverter implements Converter<String, CreateTaskDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateTaskDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateTaskDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
