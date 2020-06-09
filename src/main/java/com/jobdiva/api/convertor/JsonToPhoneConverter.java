package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.PhoneType;

public class JsonToPhoneConverter implements Converter<String, PhoneType> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public PhoneType convert(String source) {
		try {
			return jsonMapper.readValue(source, PhoneType.class);
		} catch (IOException e) {
		}
		return null;
	}
}
