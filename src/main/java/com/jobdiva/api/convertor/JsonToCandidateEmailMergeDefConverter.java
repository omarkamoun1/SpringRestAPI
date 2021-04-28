package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.CandidateEmailMergeDef;

public class JsonToCandidateEmailMergeDefConverter implements Converter<String, CandidateEmailMergeDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CandidateEmailMergeDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CandidateEmailMergeDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
