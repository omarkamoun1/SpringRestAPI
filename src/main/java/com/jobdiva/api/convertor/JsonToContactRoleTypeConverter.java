package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.ContactRoleType;

public class JsonToContactRoleTypeConverter implements Converter<String, ContactRoleType> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ContactRoleType convert(String source) {
		try {
			return jsonMapper.readValue(source, ContactRoleType.class);
		} catch (IOException e) {
		}
		return null;
	}
}
