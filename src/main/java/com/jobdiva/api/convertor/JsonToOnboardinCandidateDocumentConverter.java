package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.OnboardingCandidateDocument;

public class JsonToOnboardinCandidateDocumentConverter implements Converter<String, OnboardingCandidateDocument> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public OnboardingCandidateDocument convert(String source) {
		try {
			return jsonMapper.readValue(source, OnboardingCandidateDocument.class);
		} catch (IOException e) {
		}
		return null;
	}
}
