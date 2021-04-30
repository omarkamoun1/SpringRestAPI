package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.CreateCandidateProfileDef;

public class JsonToCreateCandidateProfileDefDocumentConverter implements Converter<String, CreateCandidateProfileDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateCandidateProfileDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateCandidateProfileDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
