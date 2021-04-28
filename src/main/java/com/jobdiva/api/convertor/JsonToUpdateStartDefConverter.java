package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.start.UpdateStartDef;

public class JsonToUpdateStartDefConverter implements Converter<String, UpdateStartDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateStartDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateStartDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
