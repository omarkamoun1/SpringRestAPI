package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateHRRecordDef;

public class JsonToUpdateCandidateHRRecordDefConverter implements Converter<String, UpdateCandidateHRRecordDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCandidateHRRecordDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCandidateHRRecordDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
