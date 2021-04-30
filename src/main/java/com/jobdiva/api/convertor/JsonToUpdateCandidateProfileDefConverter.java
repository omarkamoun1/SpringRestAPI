package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateProfileDef;

public class JsonToUpdateCandidateProfileDefConverter implements Converter<String, UpdateCandidateProfileDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCandidateProfileDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCandidateProfileDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
