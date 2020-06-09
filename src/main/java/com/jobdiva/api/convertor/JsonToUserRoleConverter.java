package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.UserRole;

public class JsonToUserRoleConverter implements Converter<String, UserRole> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UserRole convert(String source) {
		try {
			return jsonMapper.readValue(source, UserRole.class);
		} catch (IOException e) {
		}
		return null;
	}
}
