package com.jobdiva.api.convertor;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdiva.api.model.TimesheetEntry;

public class JsonToTimesheetEntryConverter implements Converter<String, TimesheetEntry> {
	
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
	public TimesheetEntry convert(String source) {
		try {
			return jsonMapper.readValue(source, TimesheetEntry.class);
		} catch (IOException e) {
		}
		return null;
	}
}
