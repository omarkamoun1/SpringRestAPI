package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.job.AddJobNoteDef;

public class JsonToAddJobNoteDefConverter implements Converter<String, AddJobNoteDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddJobNoteDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddJobNoteDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
