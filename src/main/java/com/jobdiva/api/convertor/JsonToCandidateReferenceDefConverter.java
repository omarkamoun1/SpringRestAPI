package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.CandidateReferenceDef;

public class JsonToCandidateReferenceDefConverter implements Converter<String, CandidateReferenceDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CandidateReferenceDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CandidateReferenceDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
