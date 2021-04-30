package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.CreateCandidatesNoteDef;

public class JsonToCreateCandidatesNoteDefConverter implements Converter<String, CreateCandidatesNoteDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateCandidatesNoteDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateCandidatesNoteDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
