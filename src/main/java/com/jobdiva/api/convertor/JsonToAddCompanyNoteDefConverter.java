package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.company.AddCompanyNoteDef;

public class JsonToAddCompanyNoteDefConverter implements Converter<String, AddCompanyNoteDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddCompanyNoteDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddCompanyNoteDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
