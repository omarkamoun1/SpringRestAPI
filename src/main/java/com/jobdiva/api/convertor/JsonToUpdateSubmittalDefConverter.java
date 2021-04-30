package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.submittal.UpdateSubmittalDef;

public class JsonToUpdateSubmittalDefConverter implements Converter<String, UpdateSubmittalDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateSubmittalDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateSubmittalDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
