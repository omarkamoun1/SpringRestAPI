package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.hotlist.CreateCandidateHoltilstDef;

public class JsonToCreateCandidateHoltilstDefConverter implements Converter<String, CreateCandidateHoltilstDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateCandidateHoltilstDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateCandidateHoltilstDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
