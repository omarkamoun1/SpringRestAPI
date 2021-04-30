package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.job.CreateJobDef;

public class JsonToCreateJobDefConverter implements Converter<String, CreateJobDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateJobDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateJobDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
