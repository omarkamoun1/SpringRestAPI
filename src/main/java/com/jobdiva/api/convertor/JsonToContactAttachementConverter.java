package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.ContactAttachment;

public class JsonToContactAttachementConverter implements Converter<String, ContactAttachment> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public ContactAttachment convert(String source) {
		try {
			return jsonMapper.readValue(source, ContactAttachment.class);
		} catch (IOException e) {
		}
		return null;
	}
}
