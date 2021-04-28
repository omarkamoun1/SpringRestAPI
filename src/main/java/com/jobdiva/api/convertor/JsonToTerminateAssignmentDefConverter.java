package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.start.TerminateAssignmentDef;

public class JsonToTerminateAssignmentDefConverter implements Converter<String, TerminateAssignmentDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public TerminateAssignmentDef convert(String source) {
		try {
			return jsonMapper.readValue(source, TerminateAssignmentDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
