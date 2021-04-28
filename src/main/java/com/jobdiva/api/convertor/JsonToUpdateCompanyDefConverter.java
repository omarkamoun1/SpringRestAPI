package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.company.UpdateCompanyDef;

public class JsonToUpdateCompanyDefConverter implements Converter<String, UpdateCompanyDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCompanyDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCompanyDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
