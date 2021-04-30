package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateQualificationsDef;

public class JsonToUpdateCandidateQualificationsDefConverter implements Converter<String, UpdateCandidateQualificationsDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCandidateQualificationsDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCandidateQualificationsDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
