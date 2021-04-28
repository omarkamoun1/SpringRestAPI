package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.event.UpdateEventDef;

public class JsonToUpdateEventDefConverter implements Converter<String, UpdateEventDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateEventDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateEventDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
