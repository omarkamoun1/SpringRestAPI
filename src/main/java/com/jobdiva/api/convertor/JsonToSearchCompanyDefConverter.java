package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.company.SearchCompanyDef;

public class JsonToSearchCompanyDefConverter implements Converter<String, SearchCompanyDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchCompanyDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchCompanyDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
