package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Owner;

public class JsonToOwnerConverter implements Converter<String, Owner> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Owner convert(String source) {
		try {
			return jsonMapper.readValue(source, Owner.class);
		} catch (IOException e) {
		}
		return null;
	}
}
