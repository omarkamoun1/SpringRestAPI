package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.start.TerminateStartDef;

public class JsonToTerminateStartDefConverter implements Converter<String, TerminateStartDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public TerminateStartDef convert(String source) {
		try {
			return jsonMapper.readValue(source, TerminateStartDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
