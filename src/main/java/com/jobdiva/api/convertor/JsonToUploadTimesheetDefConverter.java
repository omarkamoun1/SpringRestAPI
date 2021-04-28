package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.UploadTimesheetDef;

public class JsonToUploadTimesheetDefConverter implements Converter<String, UploadTimesheetDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public UploadTimesheetDef convert(String source) {
		try {
			return jsonMapper.readValue(source, UploadTimesheetDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
