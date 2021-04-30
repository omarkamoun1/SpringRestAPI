package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.start.SetStartDef;

public class JsonToSetStartDefConverter implements Converter<String, SetStartDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public SetStartDef convert(String source) {
		try {
			return jsonMapper.readValue(source, SetStartDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
