package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.company.CreateCompanyDef;

public class JsonToCreateCompanyDefConverter implements Converter<String, CreateCompanyDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateCompanyDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateCompanyDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
