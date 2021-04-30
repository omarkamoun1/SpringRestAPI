package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.event.UpdateTaskDef;

public class JsonToUpdateTaskDefConverter implements Converter<String, UpdateTaskDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateTaskDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateTaskDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
