package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateAvailabilityDef;

public class JsonToUpdateCandidateAvailabilityDefConverter implements Converter<String, UpdateCandidateAvailabilityDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCandidateAvailabilityDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCandidateAvailabilityDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
