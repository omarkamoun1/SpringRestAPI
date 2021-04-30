package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.hotlist.CreateContactHotlistDef;

public class JsonToCreateContactHotlistDefConverter implements Converter<String, CreateContactHotlistDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public CreateContactHotlistDef convert(String source) {
		try {
			return jsonMapper.readValue(source, CreateContactHotlistDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
