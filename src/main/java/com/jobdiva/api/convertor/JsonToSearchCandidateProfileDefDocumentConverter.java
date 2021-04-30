package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.SearchCandidateProfileDef;

public class JsonToSearchCandidateProfileDefDocumentConverter implements Converter<String, SearchCandidateProfileDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchCandidateProfileDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchCandidateProfileDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
