package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.Attachment;

public class JsonToAttachmentConverter implements Converter<String, Attachment> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public Attachment convert(String source) {
		try {
			return jsonMapper.readValue(source, Attachment.class);
		} catch (IOException e) {
		}
		return null;
	}
}
