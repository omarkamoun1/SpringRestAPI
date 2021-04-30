package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.contact.CreateContactNoteDef;

public class JsonToCreateContactNoteDefConverter implements Converter<String, CreateContactNoteDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateContactNoteDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateContactNoteDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
