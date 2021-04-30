package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.job.UpdateJobDef;

public class JsonToUpdateJobDefConverter implements Converter<String, UpdateJobDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateJobDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateJobDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
