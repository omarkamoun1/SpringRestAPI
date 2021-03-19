package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.CandidateAttachment;

public class JsonToCandidateAttachementConverter implements Converter<String, CandidateAttachment> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CandidateAttachment convert(String source) {
		try {
			return jsonMapper.readValue(source, CandidateAttachment.class);
		} catch (IOException e) {
		}
		return null;
	}
}
