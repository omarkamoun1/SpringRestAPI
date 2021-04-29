package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.hotlist.AddContactToHotlistDef;

public class JsonToAddContactToHotlistDefConverter implements Converter<String, AddContactToHotlistDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddContactToHotlistDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddContactToHotlistDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
