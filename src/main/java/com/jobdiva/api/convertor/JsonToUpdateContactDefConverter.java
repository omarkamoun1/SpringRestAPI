package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.contact.UpdateContactDef;

public class JsonToUpdateContactDefConverter implements Converter<String, UpdateContactDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UpdateContactDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UpdateContactDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
