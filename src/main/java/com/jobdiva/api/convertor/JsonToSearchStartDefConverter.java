package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.start.SearchStartDef;

public class JsonToSearchStartDefConverter implements Converter<String, SearchStartDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchStartDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchStartDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
