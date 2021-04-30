package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.CreateCandidateNoteDef;

public class JsonToCreateCandidateNoteDefConverter implements Converter<String, CreateCandidateNoteDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateCandidateNoteDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateCandidateNoteDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
