package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.CandidateQual;

public class JsonToCandidateQualConverter implements Converter<String, CandidateQual> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CandidateQual convert(String source) {
		try {
			return jsonMapper.readValue(source, CandidateQual.class);
		} catch (IOException e) {
		}
		return null;
	}
}
