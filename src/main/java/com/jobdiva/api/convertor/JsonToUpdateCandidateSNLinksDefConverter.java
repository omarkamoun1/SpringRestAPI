package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.candidate.UpdateCandidateSNLinksDef;

public class JsonToUpdateCandidateSNLinksDefConverter implements Converter<String, UpdateCandidateSNLinksDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateCandidateSNLinksDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateCandidateSNLinksDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
