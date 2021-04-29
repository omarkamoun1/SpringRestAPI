package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.hotlist.AddCandidatesToHotlistDef;

public class JsonToAddCandidatesToHotlistDefConverter implements Converter<String, AddCandidatesToHotlistDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public AddCandidatesToHotlistDef convert(String source) {
		try {
			return jsonMapper.readValue(source, AddCandidatesToHotlistDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
