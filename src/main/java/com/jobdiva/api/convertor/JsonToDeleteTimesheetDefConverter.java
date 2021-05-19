package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.v2.billingtimesheet.DeleteTimesheetDef;

public class JsonToDeleteTimesheetDefConverter implements Converter<String, DeleteTimesheetDef> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public DeleteTimesheetDef convert(String source) {
		try {
			return jsonMapper.readValue(source, DeleteTimesheetDef.class);
		} catch (IOException e) {
		}
		return null;
	}
}
