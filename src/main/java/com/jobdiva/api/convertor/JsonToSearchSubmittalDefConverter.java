package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.submittal.SearchSubmittalDef;

public class JsonToSearchSubmittalDefConverter implements Converter<String, SearchSubmittalDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchSubmittalDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchSubmittalDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
