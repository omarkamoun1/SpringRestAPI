package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Userfield;

public class JsonToUserFieldConverter implements Converter<String, Userfield> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Userfield convert(String source) {
		try {
			return jsonMapper.readValue(source, Userfield.class);
		} catch (IOException e) {
		}
		return null;
	}
}
