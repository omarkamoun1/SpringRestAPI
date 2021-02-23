package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.UploadResume;

public class JsonToUplaodResumeConverter implements Converter<String, UploadResume> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UploadResume convert(String source) {
		try {
			return jsonMapper.readValue(source, UploadResume.class);
		} catch (IOException e) {
		}
		return null;
	}
}
