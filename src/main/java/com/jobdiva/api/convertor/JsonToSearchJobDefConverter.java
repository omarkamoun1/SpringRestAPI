package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.job.SearchJobDef;

public class JsonToSearchJobDefConverter implements Converter<String, SearchJobDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchJobDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchJobDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
