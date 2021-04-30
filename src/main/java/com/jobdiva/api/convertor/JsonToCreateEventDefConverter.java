package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.event.CreateEventDef;

public class JsonToCreateEventDefConverter implements Converter<String, CreateEventDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateEventDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateEventDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
