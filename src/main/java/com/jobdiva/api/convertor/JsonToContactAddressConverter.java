package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.ContactAddress;

public class JsonToContactAddressConverter implements Converter<String, ContactAddress> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ContactAddress convert(String source) {
		try {
			return jsonMapper.readValue(source, ContactAddress.class);
		} catch (IOException e) {
		}
		return null;
	}
}
