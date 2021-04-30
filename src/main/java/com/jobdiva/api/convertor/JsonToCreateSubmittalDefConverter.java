package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.submittal.CreateSubmittalDef;

public class JsonToCreateSubmittalDefConverter implements Converter<String, CreateSubmittalDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateSubmittalDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateSubmittalDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
