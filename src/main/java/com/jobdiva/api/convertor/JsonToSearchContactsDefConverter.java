package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.contact.SearchContactsDef;

public class JsonToSearchContactsDefConverter implements Converter<String, SearchContactsDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SearchContactsDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SearchContactsDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
